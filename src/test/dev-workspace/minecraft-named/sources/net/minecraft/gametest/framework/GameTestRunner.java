package net.minecraft.gametest.framework;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.LongArraySet;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.TestInstanceBlockEntity;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestRunner.class */
public class GameTestRunner {
    public static final int DEFAULT_TESTS_PER_ROW = 8;
    private static final Logger LOGGER = LogUtils.getLogger();
    final ServerLevel level;
    private final GameTestTicker testTicker;
    private final List<GameTestInfo> allTestInfos;
    private ImmutableList<GameTestBatch> batches;
    private final GameTestBatcher testBatcher;
    private Holder<TestEnvironmentDefinition> currentEnvironment;
    private final StructureSpawner existingStructureSpawner;
    private final StructureSpawner newStructureSpawner;
    final boolean haltOnError;
    private final boolean clearBetweenBatches;
    final List<GameTestBatchListener> batchListeners = Lists.newArrayList();
    private final List<GameTestInfo> scheduledForRerun = Lists.newArrayList();
    private boolean stopped = true;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestRunner$GameTestBatcher.class */
    public interface GameTestBatcher {
        Collection<GameTestBatch> batch(Collection<GameTestInfo> collection);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestRunner$StructureSpawner.class */
    public interface StructureSpawner {
        public static final StructureSpawner IN_PLACE = $$0 -> {
            return Optional.ofNullable($$0.prepareTestStructure()).map($$0 -> {
                return $$0.startExecution(1);
            });
        };
        public static final StructureSpawner NOT_SET = $$0 -> {
            return Optional.empty();
        };

        Optional<GameTestInfo> spawnStructure(GameTestInfo gameTestInfo);

        default void onBatchStart(ServerLevel $$0) {
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestRunner$Builder.class */
    public static class Builder {
        private final ServerLevel level;
        private final Collection<GameTestBatch> batches;
        private final GameTestTicker testTicker = GameTestTicker.SINGLETON;
        private GameTestBatcher batcher = GameTestBatchFactory.fromGameTestInfo();
        private StructureSpawner existingStructureSpawner = StructureSpawner.IN_PLACE;
        private StructureSpawner newStructureSpawner = StructureSpawner.NOT_SET;
        private boolean haltOnError = false;
        private boolean clearBetweenBatches = false;

        private Builder(Collection<GameTestBatch> $$0, ServerLevel $$1) {
            this.batches = $$0;
            this.level = $$1;
        }

        public static Builder fromBatches(Collection<GameTestBatch> $$0, ServerLevel $$1) {
            return new Builder($$0, $$1);
        }

        public static Builder fromInfo(Collection<GameTestInfo> $$0, ServerLevel $$1) {
            return fromBatches(GameTestBatchFactory.fromGameTestInfo().batch($$0), $$1);
        }

        public Builder haltOnError() {
            this.haltOnError = true;
            return this;
        }

        public Builder clearBetweenBatches() {
            this.clearBetweenBatches = true;
            return this;
        }

        public Builder newStructureSpawner(StructureSpawner $$0) {
            this.newStructureSpawner = $$0;
            return this;
        }

        public Builder existingStructureSpawner(StructureGridSpawner $$0) {
            this.existingStructureSpawner = $$0;
            return this;
        }

        public Builder batcher(GameTestBatcher $$0) {
            this.batcher = $$0;
            return this;
        }

        public GameTestRunner build() {
            return new GameTestRunner(this.batcher, this.batches, this.level, this.testTicker, this.existingStructureSpawner, this.newStructureSpawner, this.haltOnError, this.clearBetweenBatches);
        }
    }

    protected GameTestRunner(GameTestBatcher $$0, Collection<GameTestBatch> $$1, ServerLevel $$2, GameTestTicker $$3, StructureSpawner $$4, StructureSpawner $$5, boolean $$6, boolean $$7) {
        this.level = $$2;
        this.testTicker = $$3;
        this.testBatcher = $$0;
        this.existingStructureSpawner = $$4;
        this.newStructureSpawner = $$5;
        this.batches = ImmutableList.copyOf($$1);
        this.haltOnError = $$6;
        this.clearBetweenBatches = $$7;
        this.allTestInfos = (List) this.batches.stream().flatMap($$02 -> {
            return $$02.gameTestInfos().stream();
        }).collect(Util.toMutableList());
        $$3.setRunner(this);
        this.allTestInfos.forEach($$03 -> {
            $$03.addListener(new ReportGameListener());
        });
    }

    public List<GameTestInfo> getTestInfos() {
        return this.allTestInfos;
    }

    public void start() {
        this.stopped = false;
        runBatch(0);
    }

    public void stop() {
        this.stopped = true;
        if (this.currentEnvironment != null) {
            endCurrentEnvironment();
        }
    }

    public void rerunTest(GameTestInfo $$0) {
        GameTestInfo $$1 = $$0.copyReset();
        $$0.getListeners().forEach($$2 -> {
            $$2.testAddedForRerun($$0, $$1, this);
        });
        this.allTestInfos.add($$1);
        this.scheduledForRerun.add($$1);
        if (this.stopped) {
            runScheduledRerunTests();
        }
    }

    void runBatch(final int $$0) {
        if ($$0 >= this.batches.size()) {
            endCurrentEnvironment();
            runScheduledRerunTests();
            return;
        }
        if ($$0 > 0 && this.clearBetweenBatches) {
            GameTestBatch $$1 = (GameTestBatch) this.batches.get($$0 - 1);
            $$1.gameTestInfos().forEach($$02 -> {
                TestInstanceBlockEntity $$12 = $$02.getTestInstanceBlockEntity();
                StructureUtils.clearSpaceForStructure($$12.getStructureBoundingBox(), this.level);
                this.level.destroyBlock($$12.getBlockPos(), false);
            });
        }
        final GameTestBatch $$2 = (GameTestBatch) this.batches.get($$0);
        this.existingStructureSpawner.onBatchStart(this.level);
        this.newStructureSpawner.onBatchStart(this.level);
        Collection<GameTestInfo> $$3 = createStructuresForBatch($$2.gameTestInfos());
        LOGGER.info("Running test environment '{}' batch {} ({} tests)...", new Object[]{$$2.environment().getRegisteredName(), Integer.valueOf($$2.index()), Integer.valueOf($$3.size())});
        endCurrentEnvironment();
        this.currentEnvironment = $$2.environment();
        this.currentEnvironment.value().setup(this.level);
        this.batchListeners.forEach($$12 -> {
            $$12.testBatchStarting($$2);
        });
        final MultipleTestTracker $$4 = new MultipleTestTracker();
        Objects.requireNonNull($$4);
        $$3.forEach($$4::addTestToTrack);
        $$4.addListener(new GameTestListener() { // from class: net.minecraft.gametest.framework.GameTestRunner.1
            private void testCompleted(GameTestInfo $$03) {
                $$03.getTestInstanceBlockEntity().removeBarriers();
                if ($$4.isDone()) {
                    List<GameTestBatchListener> list = GameTestRunner.this.batchListeners;
                    GameTestBatch gameTestBatch = $$2;
                    list.forEach($$13 -> {
                        $$13.testBatchFinished(gameTestBatch);
                    });
                    new LongArraySet(GameTestRunner.this.level.getForceLoadedChunks()).forEach($$04 -> {
                        GameTestRunner.this.level.setChunkForced(ChunkPos.getX($$04), ChunkPos.getZ($$04), false);
                    });
                    GameTestRunner.this.runBatch($$0 + 1);
                }
            }

            @Override // net.minecraft.gametest.framework.GameTestListener
            public void testStructureLoaded(GameTestInfo $$03) {
            }

            @Override // net.minecraft.gametest.framework.GameTestListener
            public void testPassed(GameTestInfo $$03, GameTestRunner $$13) {
                testCompleted($$03);
            }

            @Override // net.minecraft.gametest.framework.GameTestListener
            public void testFailed(GameTestInfo $$03, GameTestRunner $$13) {
                if (GameTestRunner.this.haltOnError) {
                    GameTestRunner.this.endCurrentEnvironment();
                    new LongArraySet(GameTestRunner.this.level.getForceLoadedChunks()).forEach($$04 -> {
                        GameTestRunner.this.level.setChunkForced(ChunkPos.getX($$04), ChunkPos.getZ($$04), false);
                    });
                    GameTestTicker.SINGLETON.clear();
                    $$03.getTestInstanceBlockEntity().removeBarriers();
                    return;
                }
                testCompleted($$03);
            }

            @Override // net.minecraft.gametest.framework.GameTestListener
            public void testAddedForRerun(GameTestInfo $$03, GameTestInfo $$13, GameTestRunner $$22) {
            }
        });
        GameTestTicker gameTestTicker = this.testTicker;
        Objects.requireNonNull(gameTestTicker);
        $$3.forEach(gameTestTicker::add);
    }

    void endCurrentEnvironment() {
        if (this.currentEnvironment != null) {
            this.currentEnvironment.value().teardown(this.level);
            this.currentEnvironment = null;
        }
    }

    private void runScheduledRerunTests() {
        if (!this.scheduledForRerun.isEmpty()) {
            LOGGER.info("Starting re-run of tests: {}", this.scheduledForRerun.stream().map($$0 -> {
                return $$0.id().toString();
            }).collect(Collectors.joining(ComponentUtils.DEFAULT_SEPARATOR_TEXT)));
            this.batches = ImmutableList.copyOf(this.testBatcher.batch(this.scheduledForRerun));
            this.scheduledForRerun.clear();
            this.stopped = false;
            runBatch(0);
            return;
        }
        this.batches = ImmutableList.of();
        this.stopped = true;
    }

    public void addListener(GameTestBatchListener $$0) {
        this.batchListeners.add($$0);
    }

    private Collection<GameTestInfo> createStructuresForBatch(Collection<GameTestInfo> $$0) {
        return $$0.stream().map(this::spawn).flatMap((v0) -> {
            return v0.stream();
        }).toList();
    }

    private Optional<GameTestInfo> spawn(GameTestInfo $$0) {
        if ($$0.getTestBlockPos() == null) {
            return this.newStructureSpawner.spawnStructure($$0);
        }
        return this.existingStructureSpawner.spawnStructure($$0);
    }
}

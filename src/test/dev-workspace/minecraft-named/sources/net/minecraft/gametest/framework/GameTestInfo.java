package net.minecraft.gametest.framework;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TestInstanceBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestInfo.class */
public class GameTestInfo {
    private final Holder.Reference<GameTestInstance> test;
    private BlockPos testBlockPos;
    private final ServerLevel level;
    private final int timeoutTicks;
    private boolean placedStructure;
    private boolean chunksLoaded;
    private int tickCount;
    private boolean started;
    private final RetryOptions retryOptions;
    private boolean done;
    private final Rotation extraRotation;
    private GameTestException error;
    private TestInstanceBlockEntity testInstanceBlockEntity;
    private final Collection<GameTestListener> listeners = Lists.newArrayList();
    private final Collection<GameTestSequence> sequences = Lists.newCopyOnWriteArrayList();
    private final Object2LongMap<Runnable> runAtTickTimeMap = new Object2LongOpenHashMap();
    private final Stopwatch timer = Stopwatch.createUnstarted();

    public GameTestInfo(Holder.Reference<GameTestInstance> $$0, Rotation $$1, ServerLevel $$2, RetryOptions $$3) {
        this.test = $$0;
        this.level = $$2;
        this.retryOptions = $$3;
        this.timeoutTicks = $$0.value().maxTicks();
        this.extraRotation = $$1;
    }

    public void setTestBlockPos(BlockPos $$0) {
        this.testBlockPos = $$0;
    }

    public GameTestInfo startExecution(int $$0) {
        this.tickCount = -(this.test.value().setupTicks() + $$0 + 1);
        return this;
    }

    public void placeStructure() {
        if (this.placedStructure) {
            return;
        }
        TestInstanceBlockEntity $$0 = getTestInstanceBlockEntity();
        if (!$$0.placeStructure()) {
            fail(Component.translatable("test.error.structure.failure", $$0.getTestName().getString()));
        }
        this.placedStructure = true;
        $$0.encaseStructure();
        BoundingBox $$1 = $$0.getStructureBoundingBox();
        this.level.getBlockTicks().clearArea($$1);
        this.level.clearBlockEvents($$1);
        this.listeners.forEach($$02 -> {
            $$02.testStructureLoaded(this);
        });
    }

    public void tick(GameTestRunner $$0) {
        if (isDone()) {
            return;
        }
        if (!this.placedStructure) {
            fail(Component.translatable("test.error.ticking_without_structure"));
        }
        if (this.testInstanceBlockEntity == null) {
            fail(Component.translatable("test.error.missing_block_entity"));
        }
        if (this.error != null) {
            finish();
        }
        if (!this.chunksLoaded) {
            Stream<ChunkPos> streamIntersectingChunks = this.testInstanceBlockEntity.getStructureBoundingBox().intersectingChunks();
            ServerLevel serverLevel = this.level;
            Objects.requireNonNull(serverLevel);
            if (!streamIntersectingChunks.allMatch(serverLevel::areEntitiesActuallyLoadedAndTicking)) {
                return;
            }
        }
        this.chunksLoaded = true;
        tickInternal();
        if (isDone()) {
            if (this.error != null) {
                this.listeners.forEach($$1 -> {
                    $$1.testFailed(this, $$0);
                });
            } else {
                this.listeners.forEach($$12 -> {
                    $$12.testPassed(this, $$0);
                });
            }
        }
    }

    private void tickInternal() {
        this.tickCount++;
        if (this.tickCount < 0) {
            return;
        }
        if (!this.started) {
            startTest();
        }
        ObjectIterator<Object2LongMap.Entry<Runnable>> $$0 = this.runAtTickTimeMap.object2LongEntrySet().iterator();
        while ($$0.hasNext()) {
            Object2LongMap.Entry<Runnable> $$1 = (Object2LongMap.Entry) $$0.next();
            if ($$1.getLongValue() <= this.tickCount) {
                try {
                    ((Runnable) $$1.getKey()).run();
                } catch (GameTestException $$2) {
                    fail($$2);
                } catch (Exception $$3) {
                    fail(new UnknownGameTestException($$3));
                }
                $$0.remove();
            }
        }
        if (this.tickCount > this.timeoutTicks) {
            if (this.sequences.isEmpty()) {
                fail(new GameTestTimeoutException(Component.translatable("test.error.timeout.no_result", Integer.valueOf(this.test.value().maxTicks()))));
                return;
            }
            this.sequences.forEach($$02 -> {
                $$02.tickAndFailIfNotComplete(this.tickCount);
            });
            if (this.error == null) {
                fail(new GameTestTimeoutException(Component.translatable("test.error.timeout.no_sequences_finished", Integer.valueOf(this.test.value().maxTicks()))));
                return;
            }
            return;
        }
        this.sequences.forEach($$03 -> {
            $$03.tickAndContinue(this.tickCount);
        });
    }

    private void startTest() {
        if (this.started) {
            return;
        }
        this.started = true;
        this.timer.start();
        getTestInstanceBlockEntity().setRunning();
        try {
            this.test.value().run(new GameTestHelper(this));
        } catch (GameTestException $$0) {
            fail($$0);
        } catch (Exception $$1) {
            fail(new UnknownGameTestException($$1));
        }
    }

    public void setRunAtTickTime(long $$0, Runnable $$1) {
        this.runAtTickTimeMap.put($$1, $$0);
    }

    public Identifier id() {
        return this.test.key().identifier();
    }

    public BlockPos getTestBlockPos() {
        return this.testBlockPos;
    }

    public BlockPos getTestOrigin() {
        return this.testInstanceBlockEntity.getStartCorner();
    }

    public AABB getStructureBounds() {
        TestInstanceBlockEntity $$0 = getTestInstanceBlockEntity();
        return $$0.getStructureBounds();
    }

    public TestInstanceBlockEntity getTestInstanceBlockEntity() {
        if (this.testInstanceBlockEntity == null) {
            if (this.testBlockPos == null) {
                throw new IllegalStateException("This GameTestInfo has no position");
            }
            BlockEntity blockEntity = this.level.getBlockEntity(this.testBlockPos);
            if (blockEntity instanceof TestInstanceBlockEntity) {
                TestInstanceBlockEntity $$0 = (TestInstanceBlockEntity) blockEntity;
                this.testInstanceBlockEntity = $$0;
            }
            if (this.testInstanceBlockEntity == null) {
                throw new IllegalStateException("Could not find a test instance block entity at the given coordinate " + String.valueOf(this.testBlockPos));
            }
        }
        return this.testInstanceBlockEntity;
    }

    public ServerLevel getLevel() {
        return this.level;
    }

    public boolean hasSucceeded() {
        return this.done && this.error == null;
    }

    public boolean hasFailed() {
        return this.error != null;
    }

    public boolean hasStarted() {
        return this.started;
    }

    public boolean isDone() {
        return this.done;
    }

    public long getRunTime() {
        return this.timer.elapsed(TimeUnit.MILLISECONDS);
    }

    private void finish() {
        if (!this.done) {
            this.done = true;
            if (this.timer.isRunning()) {
                this.timer.stop();
            }
        }
    }

    public void succeed() {
        if (this.error == null) {
            finish();
            AABB $$0 = getStructureBounds();
            List<Entity> $$1 = getLevel().getEntitiesOfClass(Entity.class, $$0.inflate(1.0d), $$02 -> {
                return !($$02 instanceof Player);
            });
            $$1.forEach($$03 -> {
                $$03.remove(Entity.RemovalReason.DISCARDED);
            });
        }
    }

    public void fail(Component $$0) {
        fail(new GameTestAssertException($$0, this.tickCount));
    }

    public void fail(GameTestException $$0) {
        this.error = $$0;
    }

    public GameTestException getError() {
        return this.error;
    }

    public String toString() {
        return id().toString();
    }

    public void addListener(GameTestListener $$0) {
        this.listeners.add($$0);
    }

    public GameTestInfo prepareTestStructure() {
        TestInstanceBlockEntity $$0 = createTestInstanceBlock((BlockPos) Objects.requireNonNull(this.testBlockPos), this.extraRotation, this.level);
        if ($$0 != null) {
            this.testInstanceBlockEntity = $$0;
            placeStructure();
            return this;
        }
        return null;
    }

    private TestInstanceBlockEntity createTestInstanceBlock(BlockPos $$0, Rotation $$1, ServerLevel $$2) {
        $$2.setBlockAndUpdate($$0, Blocks.TEST_INSTANCE_BLOCK.defaultBlockState());
        BlockEntity blockEntity = $$2.getBlockEntity($$0);
        if (blockEntity instanceof TestInstanceBlockEntity) {
            TestInstanceBlockEntity $$3 = (TestInstanceBlockEntity) blockEntity;
            ResourceKey<GameTestInstance> $$4 = getTestHolder().key();
            Vec3i $$5 = TestInstanceBlockEntity.getStructureSize($$2, $$4).orElse(new Vec3i(1, 1, 1));
            $$3.set(new TestInstanceBlockEntity.Data(Optional.of($$4), $$5, $$1, false, TestInstanceBlockEntity.Status.CLEARED, Optional.empty()));
            return $$3;
        }
        return null;
    }

    int getTick() {
        return this.tickCount;
    }

    GameTestSequence createSequence() {
        GameTestSequence $$0 = new GameTestSequence(this);
        this.sequences.add($$0);
        return $$0;
    }

    public boolean isRequired() {
        return this.test.value().required();
    }

    public boolean isOptional() {
        return !this.test.value().required();
    }

    public Identifier getStructure() {
        return this.test.value().structure();
    }

    public Rotation getRotation() {
        return this.test.value().info().rotation().getRotated(this.extraRotation);
    }

    public GameTestInstance getTest() {
        return this.test.value();
    }

    public Holder.Reference<GameTestInstance> getTestHolder() {
        return this.test;
    }

    public int getTimeoutTicks() {
        return this.timeoutTicks;
    }

    public boolean isFlaky() {
        return this.test.value().maxAttempts() > 1;
    }

    public int maxAttempts() {
        return this.test.value().maxAttempts();
    }

    public int requiredSuccesses() {
        return this.test.value().requiredSuccesses();
    }

    public RetryOptions retryOptions() {
        return this.retryOptions;
    }

    public Stream<GameTestListener> getListeners() {
        return this.listeners.stream();
    }

    public GameTestInfo copyReset() {
        GameTestInfo $$0 = new GameTestInfo(this.test, this.extraRotation, this.level, retryOptions());
        if (this.testBlockPos != null) {
            $$0.setTestBlockPos(this.testBlockPos);
        }
        return $$0;
    }
}

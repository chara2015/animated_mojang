package net.minecraft.world.entity.ai.village.poi;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.SectionTracker;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.debug.DebugPoiInfo;
import net.minecraft.world.entity.ai.village.poi.PoiSection;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.chunk.storage.ChunkIOErrorReporter;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;
import net.minecraft.world.level.chunk.storage.SectionStorage;
import net.minecraft.world.level.chunk.storage.SimpleRegionStorage;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/village/poi/PoiManager.class */
public class PoiManager extends SectionStorage<PoiSection, PoiSection.Packed> {
    public static final int MAX_VILLAGE_DISTANCE = 6;
    public static final int VILLAGE_SECTION_SIZE = 1;
    private final DistanceTracker distanceTracker;
    private final LongSet loadedChunks;

    public PoiManager(RegionStorageInfo $$0, Path $$1, DataFixer $$2, boolean $$3, RegistryAccess $$4, ChunkIOErrorReporter $$5, LevelHeightAccessor $$6) {
        super(new SimpleRegionStorage($$0, $$1, $$2, $$3, DataFixTypes.POI_CHUNK), PoiSection.Packed.CODEC, (v0) -> {
            return v0.pack();
        }, (v0, v1) -> {
            return v0.unpack(v1);
        }, PoiSection::new, $$4, $$5, $$6);
        this.loadedChunks = new LongOpenHashSet();
        this.distanceTracker = new DistanceTracker();
    }

    public PoiRecord add(BlockPos $$0, Holder<PoiType> $$1) {
        return getOrCreate(SectionPos.asLong($$0)).add($$0, $$1);
    }

    public void remove(BlockPos $$0) {
        getOrLoad(SectionPos.asLong($$0)).ifPresent($$1 -> {
            $$1.remove($$0);
        });
    }

    public long getCountInRange(Predicate<Holder<PoiType>> $$0, BlockPos $$1, int $$2, Occupancy $$3) {
        return getInRange($$0, $$1, $$2, $$3).count();
    }

    public boolean existsAtPosition(ResourceKey<PoiType> $$0, BlockPos $$1) {
        return exists($$1, $$12 -> {
            return $$12.is($$0);
        });
    }

    public Stream<PoiRecord> getInSquare(Predicate<Holder<PoiType>> $$0, BlockPos $$1, int $$2, Occupancy $$3) {
        int $$4 = Math.floorDiv($$2, 16) + 1;
        return ChunkPos.rangeClosed(new ChunkPos($$1), $$4).flatMap($$22 -> {
            return getInChunk($$0, $$22, $$3);
        }).filter($$23 -> {
            BlockPos $$32 = $$23.getPos();
            return Math.abs($$32.getX() - $$1.getX()) <= $$2 && Math.abs($$32.getZ() - $$1.getZ()) <= $$2;
        });
    }

    public Stream<PoiRecord> getInRange(Predicate<Holder<PoiType>> $$0, BlockPos $$1, int $$2, Occupancy $$3) {
        int $$4 = $$2 * $$2;
        return getInSquare($$0, $$1, $$2, $$3).filter($$22 -> {
            return $$22.getPos().distSqr($$1) <= ((double) $$4);
        });
    }

    @VisibleForDebug
    public Stream<PoiRecord> getInChunk(Predicate<Holder<PoiType>> $$0, ChunkPos $$1, Occupancy $$2) {
        return IntStream.rangeClosed(this.levelHeightAccessor.getMinSectionY(), this.levelHeightAccessor.getMaxSectionY()).boxed().map($$12 -> {
            return getOrLoad(SectionPos.of($$1, $$12.intValue()).asLong());
        }).filter((v0) -> {
            return v0.isPresent();
        }).flatMap($$22 -> {
            return ((PoiSection) $$22.get()).getRecords($$0, $$2);
        });
    }

    public Stream<BlockPos> findAll(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, BlockPos $$2, int $$3, Occupancy $$4) {
        return getInRange($$0, $$2, $$3, $$4).map((v0) -> {
            return v0.getPos();
        }).filter($$1);
    }

    public Stream<Pair<Holder<PoiType>, BlockPos>> findAllWithType(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, BlockPos $$2, int $$3, Occupancy $$4) {
        return getInRange($$0, $$2, $$3, $$4).filter($$12 -> {
            return $$1.test($$12.getPos());
        }).map($$02 -> {
            return Pair.of($$02.getPoiType(), $$02.getPos());
        });
    }

    public Stream<Pair<Holder<PoiType>, BlockPos>> findAllClosestFirstWithType(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, BlockPos $$2, int $$3, Occupancy $$4) {
        return findAllWithType($$0, $$1, $$2, $$3, $$4).sorted(Comparator.comparingDouble($$12 -> {
            return ((BlockPos) $$12.getSecond()).distSqr($$2);
        }));
    }

    public Optional<BlockPos> find(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, BlockPos $$2, int $$3, Occupancy $$4) {
        return findAll($$0, $$1, $$2, $$3, $$4).findFirst();
    }

    public Optional<BlockPos> findClosest(Predicate<Holder<PoiType>> $$0, BlockPos $$1, int $$2, Occupancy $$3) {
        return getInRange($$0, $$1, $$2, $$3).map((v0) -> {
            return v0.getPos();
        }).min(Comparator.comparingDouble($$12 -> {
            return $$12.distSqr($$1);
        }));
    }

    public Optional<Pair<Holder<PoiType>, BlockPos>> findClosestWithType(Predicate<Holder<PoiType>> $$0, BlockPos $$1, int $$2, Occupancy $$3) {
        return getInRange($$0, $$1, $$2, $$3).min(Comparator.comparingDouble($$12 -> {
            return $$12.getPos().distSqr($$1);
        })).map($$02 -> {
            return Pair.of($$02.getPoiType(), $$02.getPos());
        });
    }

    public Optional<BlockPos> findClosest(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, BlockPos $$2, int $$3, Occupancy $$4) {
        return getInRange($$0, $$2, $$3, $$4).map((v0) -> {
            return v0.getPos();
        }).filter($$1).min(Comparator.comparingDouble($$12 -> {
            return $$12.distSqr($$2);
        }));
    }

    public Optional<BlockPos> take(Predicate<Holder<PoiType>> $$0, BiPredicate<Holder<PoiType>, BlockPos> $$1, BlockPos $$2, int $$3) {
        return getInRange($$0, $$2, $$3, Occupancy.HAS_SPACE).filter($$12 -> {
            return $$1.test($$12.getPoiType(), $$12.getPos());
        }).findFirst().map($$02 -> {
            $$02.acquireTicket();
            return $$02.getPos();
        });
    }

    public Optional<BlockPos> getRandom(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, Occupancy $$2, BlockPos $$3, int $$4, RandomSource $$5) {
        List<PoiRecord> $$6 = Util.toShuffledList(getInRange($$0, $$3, $$4, $$2), $$5);
        return $$6.stream().filter($$12 -> {
            return $$1.test($$12.getPos());
        }).findFirst().map((v0) -> {
            return v0.getPos();
        });
    }

    public boolean release(BlockPos $$0) {
        return ((Boolean) getOrLoad(SectionPos.asLong($$0)).map($$1 -> {
            return Boolean.valueOf($$1.release($$0));
        }).orElseThrow(() -> {
            return (IllegalStateException) Util.pauseInIde(new IllegalStateException("POI never registered at " + String.valueOf($$0)));
        })).booleanValue();
    }

    public boolean exists(BlockPos $$0, Predicate<Holder<PoiType>> $$1) {
        return ((Boolean) getOrLoad(SectionPos.asLong($$0)).map($$2 -> {
            return Boolean.valueOf($$2.exists($$0, $$1));
        }).orElse(false)).booleanValue();
    }

    public Optional<Holder<PoiType>> getType(BlockPos $$0) {
        return getOrLoad(SectionPos.asLong($$0)).flatMap($$1 -> {
            return $$1.getType($$0);
        });
    }

    @VisibleForDebug
    public DebugPoiInfo getDebugPoiInfo(BlockPos $$0) {
        return (DebugPoiInfo) getOrLoad(SectionPos.asLong($$0)).flatMap($$1 -> {
            return $$1.getDebugPoiInfo($$0);
        }).orElse(null);
    }

    public int sectionsToVillage(SectionPos $$0) {
        this.distanceTracker.runAllUpdates();
        return this.distanceTracker.getLevel($$0.asLong());
    }

    boolean isVillageCenter(long $$0) {
        Optional<PoiSection> $$1 = get($$0);
        if ($$1 == null) {
            return false;
        }
        return ((Boolean) $$1.map($$02 -> {
            return Boolean.valueOf($$02.getRecords($$02 -> {
                return $$02.is(PoiTypeTags.VILLAGE);
            }, Occupancy.IS_OCCUPIED).findAny().isPresent());
        }).orElse(false)).booleanValue();
    }

    @Override // net.minecraft.world.level.chunk.storage.SectionStorage
    public void tick(BooleanSupplier $$0) {
        super.tick($$0);
        this.distanceTracker.runAllUpdates();
    }

    @Override // net.minecraft.world.level.chunk.storage.SectionStorage
    protected void setDirty(long $$0) {
        super.setDirty($$0);
        this.distanceTracker.update($$0, this.distanceTracker.getLevelFromSource($$0), false);
    }

    @Override // net.minecraft.world.level.chunk.storage.SectionStorage
    protected void onSectionLoad(long $$0) {
        this.distanceTracker.update($$0, this.distanceTracker.getLevelFromSource($$0), false);
    }

    public void checkConsistencyWithBlocks(SectionPos $$0, LevelChunkSection $$1) {
        Util.ifElse(getOrLoad($$0.asLong()), $$2 -> {
            $$2.refresh($$2 -> {
                if (mayHavePoi($$1)) {
                    updateFromSection($$1, $$0, $$2);
                }
            });
        }, () -> {
            if (mayHavePoi($$1)) {
                PoiSection $$22 = getOrCreate($$0.asLong());
                Objects.requireNonNull($$22);
                updateFromSection($$1, $$0, $$22::add);
            }
        });
    }

    private static boolean mayHavePoi(LevelChunkSection $$0) {
        return $$0.maybeHas(PoiTypes::hasPoi);
    }

    private void updateFromSection(LevelChunkSection $$0, SectionPos $$1, BiConsumer<BlockPos, Holder<PoiType>> $$2) {
        $$1.blocksInside().forEach($$22 -> {
            BlockState $$3 = $$0.getBlockState(SectionPos.sectionRelative($$22.getX()), SectionPos.sectionRelative($$22.getY()), SectionPos.sectionRelative($$22.getZ()));
            PoiTypes.forState($$3).ifPresent($$22 -> {
                $$2.accept($$22, $$22);
            });
        });
    }

    public void ensureLoadedAndValid(LevelReader $$0, BlockPos $$1, int $$2) {
        SectionPos.aroundChunk(new ChunkPos($$1), Math.floorDiv($$2, 16), this.levelHeightAccessor.getMinSectionY(), this.levelHeightAccessor.getMaxSectionY()).map($$02 -> {
            return Pair.of($$02, getOrLoad($$02.asLong()));
        }).filter($$03 -> {
            return !((Boolean) ((Optional) $$03.getSecond()).map((v0) -> {
                return v0.isValid();
            }).orElse(false)).booleanValue();
        }).map($$04 -> {
            return ((SectionPos) $$04.getFirst()).chunk();
        }).filter($$05 -> {
            return this.loadedChunks.add($$05.toLong());
        }).forEach($$12 -> {
            $$0.getChunk($$12.x, $$12.z, ChunkStatus.EMPTY);
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/village/poi/PoiManager$Occupancy.class */
    public enum Occupancy {
        HAS_SPACE((v0) -> {
            return v0.hasSpace();
        }),
        IS_OCCUPIED((v0) -> {
            return v0.isOccupied();
        }),
        ANY($$0 -> {
            return true;
        });

        private final Predicate<? super PoiRecord> test;

        Occupancy(Predicate predicate) {
            this.test = predicate;
        }

        public Predicate<? super PoiRecord> getTest() {
            return this.test;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/village/poi/PoiManager$DistanceTracker.class */
    final class DistanceTracker extends SectionTracker {
        private final Long2ByteMap levels;

        protected DistanceTracker() {
            super(7, 16, 256);
            this.levels = new Long2ByteOpenHashMap();
            this.levels.defaultReturnValue((byte) 7);
        }

        @Override // net.minecraft.server.level.SectionTracker
        protected int getLevelFromSource(long $$0) {
            return PoiManager.this.isVillageCenter($$0) ? 0 : 7;
        }

        @Override // net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint
        protected int getLevel(long $$0) {
            return this.levels.get($$0);
        }

        @Override // net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint
        protected void setLevel(long $$0, int $$1) {
            if ($$1 > 6) {
                this.levels.remove($$0);
            } else {
                this.levels.put($$0, (byte) $$1);
            }
        }

        public void runAllUpdates() {
            super.runUpdates(Integer.MAX_VALUE);
        }
    }
}

package net.minecraft.world.level.levelgen.feature;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
import net.minecraft.world.phys.AABB;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/SpikeFeature.class */
public class SpikeFeature extends Feature<SpikeConfiguration> {
    public static final int NUMBER_OF_SPIKES = 10;
    private static final int SPIKE_DISTANCE = 42;
    private static final LoadingCache<Long, List<EndSpike>> SPIKE_CACHE = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build(new SpikeCacheLoader());

    public SpikeFeature(Codec<SpikeConfiguration> $$0) {
        super($$0);
    }

    public static List<EndSpike> getSpikesForLevel(WorldGenLevel $$0) {
        RandomSource $$1 = RandomSource.create($$0.getSeed());
        long $$2 = $$1.nextLong() & 65535;
        return (List) SPIKE_CACHE.getUnchecked(Long.valueOf($$2));
    }

    @Override // net.minecraft.world.level.levelgen.feature.Feature
    public boolean place(FeaturePlaceContext<SpikeConfiguration> $$0) {
        SpikeConfiguration $$1 = (SpikeConfiguration) $$0.config();
        WorldGenLevel $$2 = $$0.level();
        RandomSource $$3 = $$0.random();
        BlockPos $$4 = $$0.origin();
        List<EndSpike> $$5 = $$1.getSpikes();
        if ($$5.isEmpty()) {
            $$5 = getSpikesForLevel($$2);
        }
        for (EndSpike $$6 : $$5) {
            if ($$6.isCenterWithinChunk($$4)) {
                placeSpike($$2, $$3, $$1, $$6);
            }
        }
        return true;
    }

    private void placeSpike(ServerLevelAccessor $$0, RandomSource $$1, SpikeConfiguration $$2, EndSpike $$3) {
        int $$4 = $$3.getRadius();
        for (BlockPos $$5 : BlockPos.betweenClosed(new BlockPos($$3.getCenterX() - $$4, $$0.getMinY(), $$3.getCenterZ() - $$4), new BlockPos($$3.getCenterX() + $$4, $$3.getHeight() + 10, $$3.getCenterZ() + $$4))) {
            if ($$5.distToLowCornerSqr($$3.getCenterX(), $$5.getY(), $$3.getCenterZ()) <= ($$4 * $$4) + 1 && $$5.getY() < $$3.getHeight()) {
                setBlock($$0, $$5, Blocks.OBSIDIAN.defaultBlockState());
            } else if ($$5.getY() > 65) {
                setBlock($$0, $$5, Blocks.AIR.defaultBlockState());
            }
        }
        if ($$3.isGuarded()) {
            BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
            int $$10 = -2;
            while ($$10 <= 2) {
                int $$11 = -2;
                while ($$11 <= 2) {
                    int $$12 = 0;
                    while ($$12 <= 3) {
                        boolean $$13 = Mth.abs($$10) == 2;
                        boolean $$14 = Mth.abs($$11) == 2;
                        boolean $$15 = $$12 == 3;
                        if ($$13 || $$14 || $$15) {
                            boolean $$16 = $$10 == -2 || $$10 == 2 || $$15;
                            boolean $$17 = $$11 == -2 || $$11 == 2 || $$15;
                            BlockState $$18 = (BlockState) ((BlockState) ((BlockState) ((BlockState) Blocks.IRON_BARS.defaultBlockState().setValue(IronBarsBlock.NORTH, Boolean.valueOf($$16 && $$11 != -2))).setValue(IronBarsBlock.SOUTH, Boolean.valueOf($$16 && $$11 != 2))).setValue(IronBarsBlock.WEST, Boolean.valueOf($$17 && $$10 != -2))).setValue(IronBarsBlock.EAST, Boolean.valueOf($$17 && $$10 != 2));
                            setBlock($$0, $$9.set($$3.getCenterX() + $$10, $$3.getHeight() + $$12, $$3.getCenterZ() + $$11), $$18);
                        }
                        $$12++;
                    }
                    $$11++;
                }
                $$10++;
            }
        }
        EndCrystal $$19 = (EndCrystal) EntityType.END_CRYSTAL.create($$0.getLevel(), EntitySpawnReason.STRUCTURE);
        if ($$19 != null) {
            $$19.setBeamTarget($$2.getCrystalBeamTarget());
            $$19.setInvulnerable($$2.isCrystalInvulnerable());
            $$19.snapTo(((double) $$3.getCenterX()) + 0.5d, $$3.getHeight() + 1, ((double) $$3.getCenterZ()) + 0.5d, $$1.nextFloat() * 360.0f, 0.0f);
            $$0.addFreshEntity($$19);
            BlockPos $$20 = $$19.blockPosition();
            setBlock($$0, $$20.below(), Blocks.BEDROCK.defaultBlockState());
            setBlock($$0, $$20, FireBlock.getState($$0, $$20));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/SpikeFeature$EndSpike.class */
    public static class EndSpike {
        public static final Codec<EndSpike> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(Codec.INT.fieldOf("centerX").orElse(0).forGetter($$0 -> {
                return Integer.valueOf($$0.centerX);
            }), Codec.INT.fieldOf("centerZ").orElse(0).forGetter($$02 -> {
                return Integer.valueOf($$02.centerZ);
            }), Codec.INT.fieldOf("radius").orElse(0).forGetter($$03 -> {
                return Integer.valueOf($$03.radius);
            }), Codec.INT.fieldOf(Display.TAG_HEIGHT).orElse(0).forGetter($$04 -> {
                return Integer.valueOf($$04.height);
            }), Codec.BOOL.fieldOf("guarded").orElse(false).forGetter($$05 -> {
                return Boolean.valueOf($$05.guarded);
            })).apply($$0, (v1, v2, v3, v4, v5) -> {
                return new EndSpike(v1, v2, v3, v4, v5);
            });
        });
        private final int centerX;
        private final int centerZ;
        private final int radius;
        private final int height;
        private final boolean guarded;
        private final AABB topBoundingBox;

        public EndSpike(int $$0, int $$1, int $$2, int $$3, boolean $$4) {
            this.centerX = $$0;
            this.centerZ = $$1;
            this.radius = $$2;
            this.height = $$3;
            this.guarded = $$4;
            this.topBoundingBox = new AABB($$0 - $$2, DimensionType.MIN_Y, $$1 - $$2, $$0 + $$2, DimensionType.MAX_Y, $$1 + $$2);
        }

        public boolean isCenterWithinChunk(BlockPos $$0) {
            return SectionPos.blockToSectionCoord($$0.getX()) == SectionPos.blockToSectionCoord(this.centerX) && SectionPos.blockToSectionCoord($$0.getZ()) == SectionPos.blockToSectionCoord(this.centerZ);
        }

        public int getCenterX() {
            return this.centerX;
        }

        public int getCenterZ() {
            return this.centerZ;
        }

        public int getRadius() {
            return this.radius;
        }

        public int getHeight() {
            return this.height;
        }

        public boolean isGuarded() {
            return this.guarded;
        }

        public AABB getTopBoundingBox() {
            return this.topBoundingBox;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/SpikeFeature$SpikeCacheLoader.class */
    static class SpikeCacheLoader extends CacheLoader<Long, List<EndSpike>> {
        SpikeCacheLoader() {
        }

        public List<EndSpike> load(Long $$0) {
            IntArrayList $$1 = Util.toShuffledList(IntStream.range(0, 10), RandomSource.create($$0.longValue()));
            List<EndSpike> $$2 = Lists.newArrayList();
            for (int $$3 = 0; $$3 < 10; $$3++) {
                int $$4 = Mth.floor(42.0d * Math.cos(2.0d * ((-3.141592653589793d) + (0.3141592653589793d * ((double) $$3)))));
                int $$5 = Mth.floor(42.0d * Math.sin(2.0d * ((-3.141592653589793d) + (0.3141592653589793d * ((double) $$3)))));
                int $$6 = $$1.get($$3).intValue();
                int $$7 = 2 + ($$6 / 3);
                int $$8 = 76 + ($$6 * 3);
                boolean $$9 = $$6 == 1 || $$6 == 2;
                $$2.add(new EndSpike($$4, $$5, $$7, $$8, $$9));
            }
            return $$2;
        }
    }
}

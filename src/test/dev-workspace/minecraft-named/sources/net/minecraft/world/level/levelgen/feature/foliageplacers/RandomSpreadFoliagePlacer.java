package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/foliageplacers/RandomSpreadFoliagePlacer.class */
public class RandomSpreadFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<RandomSpreadFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return foliagePlacerParts($$0).and($$0.group(IntProvider.codec(1, 512).fieldOf("foliage_height").forGetter($$0 -> {
            return $$0.foliageHeight;
        }), Codec.intRange(0, 256).fieldOf("leaf_placement_attempts").forGetter($$02 -> {
            return Integer.valueOf($$02.leafPlacementAttempts);
        }))).apply($$0, (v1, v2, v3, v4) -> {
            return new RandomSpreadFoliagePlacer(v1, v2, v3, v4);
        });
    });
    private final IntProvider foliageHeight;
    private final int leafPlacementAttempts;

    public RandomSpreadFoliagePlacer(IntProvider $$0, IntProvider $$1, IntProvider $$2, int $$3) {
        super($$0, $$1);
        this.foliageHeight = $$2;
        this.leafPlacementAttempts = $$3;
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected FoliagePlacerType<?> type() {
        return FoliagePlacerType.RANDOM_SPREAD_FOLIAGE_PLACER;
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
        BlockPos $$9 = $$5.pos();
        BlockPos.MutableBlockPos $$10 = $$9.mutable();
        for (int $$11 = 0; $$11 < this.leafPlacementAttempts; $$11++) {
            $$10.setWithOffset($$9, $$2.nextInt($$7) - $$2.nextInt($$7), $$2.nextInt($$6) - $$2.nextInt($$6), $$2.nextInt($$7) - $$2.nextInt($$7));
            tryPlaceLeaf($$0, $$1, $$2, $$3, $$10);
        }
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
        return this.foliageHeight.sample($$0);
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
        return false;
    }
}

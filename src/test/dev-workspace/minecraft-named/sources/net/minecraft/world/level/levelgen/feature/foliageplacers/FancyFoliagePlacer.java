package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/foliageplacers/FancyFoliagePlacer.class */
public class FancyFoliagePlacer extends BlobFoliagePlacer {
    public static final MapCodec<FancyFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return blobParts($$0).apply($$0, (v1, v2, v3) -> {
            return new FancyFoliagePlacer(v1, v2, v3);
        });
    });

    public FancyFoliagePlacer(IntProvider $$0, IntProvider $$1, int $$2) {
        super($$0, $$1, $$2);
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer, net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected FoliagePlacerType<?> type() {
        return FoliagePlacerType.FANCY_FOLIAGE_PLACER;
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer, net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
        int $$9 = $$8;
        while ($$9 >= $$8 - $$6) {
            int $$10 = $$7 + (($$9 == $$8 || $$9 == $$8 - $$6) ? 0 : 1);
            placeLeavesRow($$0, $$1, $$2, $$3, $$5.pos(), $$10, $$9, $$5.doubleTrunk());
            $$9--;
        }
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer, net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
        return Mth.square(((float) $$1) + 0.5f) + Mth.square(((float) $$3) + 0.5f) > ((float) ($$4 * $$4));
    }
}

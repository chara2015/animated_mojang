package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/foliageplacers/MegaPineFoliagePlacer.class */
public class MegaPineFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<MegaPineFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return foliagePlacerParts($$0).and(IntProvider.codec(0, 24).fieldOf("crown_height").forGetter($$0 -> {
            return $$0.crownHeight;
        })).apply($$0, MegaPineFoliagePlacer::new);
    });
    private final IntProvider crownHeight;

    public MegaPineFoliagePlacer(IntProvider $$0, IntProvider $$1, IntProvider $$2) {
        super($$0, $$1);
        this.crownHeight = $$2;
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected FoliagePlacerType<?> type() {
        return FoliagePlacerType.MEGA_PINE_FOLIAGE_PLACER;
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
        int i;
        BlockPos $$9 = $$5.pos();
        int $$10 = 0;
        for (int $$11 = ($$9.getY() - $$6) + $$8; $$11 <= $$9.getY() + $$8; $$11++) {
            int $$12 = $$9.getY() - $$11;
            int $$13 = $$7 + $$5.radiusOffset() + Mth.floor(($$12 / $$6) * 3.5f);
            if ($$12 > 0 && $$13 == $$10 && ($$11 & 1) == 0) {
                i = $$13 + 1;
            } else {
                i = $$13;
            }
            int $$15 = i;
            placeLeavesRow($$0, $$1, $$2, $$3, new BlockPos($$9.getX(), $$11, $$9.getZ()), $$15, 0, $$5.doubleTrunk());
            $$10 = $$13;
        }
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
        return this.crownHeight.sample($$0);
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
        return $$1 + $$3 >= 7 || ($$1 * $$1) + ($$3 * $$3) > $$4 * $$4;
    }
}

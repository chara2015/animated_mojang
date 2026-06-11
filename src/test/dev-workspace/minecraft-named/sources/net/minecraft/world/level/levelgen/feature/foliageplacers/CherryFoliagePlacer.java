package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Display;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/foliageplacers/CherryFoliagePlacer.class */
public class CherryFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<CherryFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return foliagePlacerParts($$0).and($$0.group(IntProvider.codec(4, 16).fieldOf(Display.TAG_HEIGHT).forGetter($$0 -> {
            return $$0.height;
        }), Codec.floatRange(0.0f, 1.0f).fieldOf("wide_bottom_layer_hole_chance").forGetter($$02 -> {
            return Float.valueOf($$02.wideBottomLayerHoleChance);
        }), Codec.floatRange(0.0f, 1.0f).fieldOf("corner_hole_chance").forGetter($$03 -> {
            return Float.valueOf($$03.wideBottomLayerHoleChance);
        }), Codec.floatRange(0.0f, 1.0f).fieldOf("hanging_leaves_chance").forGetter($$04 -> {
            return Float.valueOf($$04.hangingLeavesChance);
        }), Codec.floatRange(0.0f, 1.0f).fieldOf("hanging_leaves_extension_chance").forGetter($$05 -> {
            return Float.valueOf($$05.hangingLeavesExtensionChance);
        }))).apply($$0, (v1, v2, v3, v4, v5, v6, v7) -> {
            return new CherryFoliagePlacer(v1, v2, v3, v4, v5, v6, v7);
        });
    });
    private final IntProvider height;
    private final float wideBottomLayerHoleChance;
    private final float cornerHoleChance;
    private final float hangingLeavesChance;
    private final float hangingLeavesExtensionChance;

    public CherryFoliagePlacer(IntProvider $$0, IntProvider $$1, IntProvider $$2, float $$3, float $$4, float $$5, float $$6) {
        super($$0, $$1);
        this.height = $$2;
        this.wideBottomLayerHoleChance = $$3;
        this.cornerHoleChance = $$4;
        this.hangingLeavesChance = $$5;
        this.hangingLeavesExtensionChance = $$6;
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected FoliagePlacerType<?> type() {
        return FoliagePlacerType.CHERRY_FOLIAGE_PLACER;
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
        boolean $$9 = $$5.doubleTrunk();
        BlockPos $$10 = $$5.pos().above($$8);
        int $$11 = ($$7 + $$5.radiusOffset()) - 1;
        placeLeavesRow($$0, $$1, $$2, $$3, $$10, $$11 - 2, $$6 - 3, $$9);
        placeLeavesRow($$0, $$1, $$2, $$3, $$10, $$11 - 1, $$6 - 4, $$9);
        for (int $$12 = $$6 - 5; $$12 >= 0; $$12--) {
            placeLeavesRow($$0, $$1, $$2, $$3, $$10, $$11, $$12, $$9);
        }
        placeLeavesRowWithHangingLeavesBelow($$0, $$1, $$2, $$3, $$10, $$11, -1, $$9, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
        placeLeavesRowWithHangingLeavesBelow($$0, $$1, $$2, $$3, $$10, $$11 - 1, -2, $$9, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
        return this.height.sample($$0);
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
        if ($$2 == -1 && (($$1 == $$4 || $$3 == $$4) && $$0.nextFloat() < this.wideBottomLayerHoleChance)) {
            return true;
        }
        boolean $$6 = $$1 == $$4 && $$3 == $$4;
        boolean $$7 = $$4 > 2;
        return $$7 ? $$6 || ($$1 + $$3 > ($$4 * 2) - 2 && $$0.nextFloat() < this.cornerHoleChance) : $$6 && $$0.nextFloat() < this.cornerHoleChance;
    }
}

package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Display;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/foliageplacers/BlobFoliagePlacer.class */
public class BlobFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<BlobFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return blobParts($$0).apply($$0, (v1, v2, v3) -> {
            return new BlobFoliagePlacer(v1, v2, v3);
        });
    });
    protected final int height;

    protected static <P extends BlobFoliagePlacer> Products.P3<RecordCodecBuilder.Mu<P>, IntProvider, IntProvider, Integer> blobParts(RecordCodecBuilder.Instance<P> $$0) {
        return foliagePlacerParts($$0).and(Codec.intRange(0, 16).fieldOf(Display.TAG_HEIGHT).forGetter($$02 -> {
            return Integer.valueOf($$02.height);
        }));
    }

    public BlobFoliagePlacer(IntProvider $$0, IntProvider $$1, int $$2) {
        super($$0, $$1);
        this.height = $$2;
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected FoliagePlacerType<?> type() {
        return FoliagePlacerType.BLOB_FOLIAGE_PLACER;
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
        for (int $$9 = $$8; $$9 >= $$8 - $$6; $$9--) {
            int $$10 = Math.max((($$7 + $$5.radiusOffset()) - 1) - ($$9 / 2), 0);
            placeLeavesRow($$0, $$1, $$2, $$3, $$5.pos(), $$10, $$9, $$5.doubleTrunk());
        }
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
        return this.height;
    }

    @Override // net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
    protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
        return $$1 == $$4 && $$3 == $$4 && ($$0.nextInt(2) == 0 || $$2 == 0);
    }
}

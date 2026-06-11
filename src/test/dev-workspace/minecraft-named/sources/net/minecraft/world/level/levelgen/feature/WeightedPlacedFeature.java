package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/WeightedPlacedFeature.class */
public class WeightedPlacedFeature {
    public static final Codec<WeightedPlacedFeature> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(PlacedFeature.CODEC.fieldOf("feature").forGetter($$0 -> {
            return $$0.feature;
        }), Codec.floatRange(0.0f, 1.0f).fieldOf("chance").forGetter($$02 -> {
            return Float.valueOf($$02.chance);
        })).apply($$0, (v1, v2) -> {
            return new WeightedPlacedFeature(v1, v2);
        });
    });
    public final Holder<PlacedFeature> feature;
    public final float chance;

    public WeightedPlacedFeature(Holder<PlacedFeature> $$0, float $$1) {
        this.feature = $$0;
        this.chance = $$1;
    }

    public boolean place(WorldGenLevel $$0, ChunkGenerator $$1, RandomSource $$2, BlockPos $$3) {
        return this.feature.value().place($$0, $$1, $$2, $$3);
    }
}

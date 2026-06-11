package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/placement/NoiseBasedCountPlacement.class */
public class NoiseBasedCountPlacement extends RepeatingPlacement {
    public static final MapCodec<NoiseBasedCountPlacement> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.INT.fieldOf("noise_to_count_ratio").forGetter($$0 -> {
            return Integer.valueOf($$0.noiseToCountRatio);
        }), Codec.DOUBLE.fieldOf("noise_factor").forGetter($$02 -> {
            return Double.valueOf($$02.noiseFactor);
        }), Codec.DOUBLE.fieldOf("noise_offset").orElse(Double.valueOf(Density.SURFACE)).forGetter($$03 -> {
            return Double.valueOf($$03.noiseOffset);
        })).apply($$0, (v1, v2, v3) -> {
            return new NoiseBasedCountPlacement(v1, v2, v3);
        });
    });
    private final int noiseToCountRatio;
    private final double noiseFactor;
    private final double noiseOffset;

    private NoiseBasedCountPlacement(int $$0, double $$1, double $$2) {
        this.noiseToCountRatio = $$0;
        this.noiseFactor = $$1;
        this.noiseOffset = $$2;
    }

    public static NoiseBasedCountPlacement of(int $$0, double $$1, double $$2) {
        return new NoiseBasedCountPlacement($$0, $$1, $$2);
    }

    @Override // net.minecraft.world.level.levelgen.placement.RepeatingPlacement
    protected int count(RandomSource $$0, BlockPos $$1) {
        double $$2 = Biome.BIOME_INFO_NOISE.getValue(((double) $$1.getX()) / this.noiseFactor, ((double) $$1.getZ()) / this.noiseFactor, false);
        return (int) Math.ceil(($$2 + this.noiseOffset) * ((double) this.noiseToCountRatio));
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public PlacementModifierType<?> type() {
        return PlacementModifierType.NOISE_BASED_COUNT;
    }
}

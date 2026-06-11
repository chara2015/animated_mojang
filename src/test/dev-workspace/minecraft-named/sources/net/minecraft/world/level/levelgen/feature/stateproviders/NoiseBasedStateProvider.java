package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/stateproviders/NoiseBasedStateProvider.class */
public abstract class NoiseBasedStateProvider extends BlockStateProvider {
    protected final long seed;
    protected final NormalNoise.NoiseParameters parameters;
    protected final float scale;
    protected final NormalNoise noise;

    protected static <P extends NoiseBasedStateProvider> Products.P3<RecordCodecBuilder.Mu<P>, Long, NormalNoise.NoiseParameters, Float> noiseCodec(RecordCodecBuilder.Instance<P> $$0) {
        return $$0.group(Codec.LONG.fieldOf("seed").forGetter($$02 -> {
            return Long.valueOf($$02.seed);
        }), NormalNoise.NoiseParameters.DIRECT_CODEC.fieldOf("noise").forGetter($$03 -> {
            return $$03.parameters;
        }), ExtraCodecs.POSITIVE_FLOAT.fieldOf("scale").forGetter($$04 -> {
            return Float.valueOf($$04.scale);
        }));
    }

    protected NoiseBasedStateProvider(long $$0, NormalNoise.NoiseParameters $$1, float $$2) {
        this.seed = $$0;
        this.parameters = $$1;
        this.scale = $$2;
        this.noise = NormalNoise.create(new WorldgenRandom(new LegacyRandomSource($$0)), $$1);
    }

    protected double getNoiseValue(BlockPos $$0, double $$1) {
        return this.noise.getValue(((double) $$0.getX()) * $$1, ((double) $$0.getY()) * $$1, ((double) $$0.getZ()) * $$1);
    }
}

package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.datafixers.Products;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/stateproviders/NoiseProvider.class */
public class NoiseProvider extends NoiseBasedStateProvider {
    public static final MapCodec<NoiseProvider> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return noiseProviderCodec($$0).apply($$0, (v1, v2, v3, v4) -> {
            return new NoiseProvider(v1, v2, v3, v4);
        });
    });
    protected final List<BlockState> states;

    protected static <P extends NoiseProvider> Products.P4<RecordCodecBuilder.Mu<P>, Long, NormalNoise.NoiseParameters, Float, List<BlockState>> noiseProviderCodec(RecordCodecBuilder.Instance<P> $$0) {
        return noiseCodec($$0).and(ExtraCodecs.nonEmptyList(BlockState.CODEC.listOf()).fieldOf("states").forGetter($$02 -> {
            return $$02.states;
        }));
    }

    public NoiseProvider(long $$0, NormalNoise.NoiseParameters $$1, float $$2, List<BlockState> $$3) {
        super($$0, $$1, $$2);
        this.states = $$3;
    }

    @Override // net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
    protected BlockStateProviderType<?> type() {
        return BlockStateProviderType.NOISE_PROVIDER;
    }

    @Override // net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
    public BlockState getState(RandomSource $$0, BlockPos $$1) {
        return getRandomState(this.states, $$1, this.scale);
    }

    protected BlockState getRandomState(List<BlockState> $$0, BlockPos $$1, double $$2) {
        double $$3 = getNoiseValue($$1, $$2);
        return getRandomState($$0, $$3);
    }

    protected BlockState getRandomState(List<BlockState> $$0, double $$1) {
        double $$2 = Mth.clamp((1.0d + $$1) / 2.0d, Density.SURFACE, 0.9999d);
        return $$0.get((int) ($$2 * ((double) $$0.size())));
    }
}

package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/stateproviders/DualNoiseProvider.class */
public class DualNoiseProvider extends NoiseProvider {
    public static final MapCodec<DualNoiseProvider> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(InclusiveRange.codec(Codec.INT, 1, 64).fieldOf("variety").forGetter($$0 -> {
            return $$0.variety;
        }), NormalNoise.NoiseParameters.DIRECT_CODEC.fieldOf("slow_noise").forGetter($$02 -> {
            return $$02.slowNoiseParameters;
        }), ExtraCodecs.POSITIVE_FLOAT.fieldOf("slow_scale").forGetter($$03 -> {
            return Float.valueOf($$03.slowScale);
        })).and(noiseProviderCodec($$0)).apply($$0, (v1, v2, v3, v4, v5, v6, v7) -> {
            return new DualNoiseProvider(v1, v2, v3, v4, v5, v6, v7);
        });
    });
    private final InclusiveRange<Integer> variety;
    private final NormalNoise.NoiseParameters slowNoiseParameters;
    private final float slowScale;
    private final NormalNoise slowNoise;

    public DualNoiseProvider(InclusiveRange<Integer> $$0, NormalNoise.NoiseParameters $$1, float $$2, long $$3, NormalNoise.NoiseParameters $$4, float $$5, List<BlockState> $$6) {
        super($$3, $$4, $$5, $$6);
        this.variety = $$0;
        this.slowNoiseParameters = $$1;
        this.slowScale = $$2;
        this.slowNoise = NormalNoise.create(new WorldgenRandom(new LegacyRandomSource($$3)), $$1);
    }

    @Override // net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider, net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
    protected BlockStateProviderType<?> type() {
        return BlockStateProviderType.DUAL_NOISE_PROVIDER;
    }

    @Override // net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider, net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
    public BlockState getState(RandomSource $$0, BlockPos $$1) {
        double $$2 = getSlowNoiseValue($$1);
        int $$3 = (int) Mth.clampedMap($$2, -1.0d, 1.0d, ((Integer) this.variety.minInclusive()).intValue(), ((Integer) this.variety.maxInclusive()).intValue() + 1);
        List<BlockState> $$4 = Lists.newArrayListWithCapacity($$3);
        for (int $$5 = 0; $$5 < $$3; $$5++) {
            $$4.add(getRandomState(this.states, getSlowNoiseValue($$1.offset($$5 * 54545, 0, $$5 * 34234))));
        }
        return getRandomState($$4, $$1, this.scale);
    }

    protected double getSlowNoiseValue(BlockPos $$0) {
        return this.slowNoise.getValue($$0.getX() * this.slowScale, $$0.getY() * this.slowScale, $$0.getZ() * this.slowScale);
    }
}

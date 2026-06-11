package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/stateproviders/NoiseThresholdProvider.class */
public class NoiseThresholdProvider extends NoiseBasedStateProvider {
    public static final MapCodec<NoiseThresholdProvider> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return noiseCodec($$0).and($$0.group(Codec.floatRange(-1.0f, 1.0f).fieldOf("threshold").forGetter($$0 -> {
            return Float.valueOf($$0.threshold);
        }), Codec.floatRange(0.0f, 1.0f).fieldOf("high_chance").forGetter($$02 -> {
            return Float.valueOf($$02.highChance);
        }), BlockState.CODEC.fieldOf("default_state").forGetter($$03 -> {
            return $$03.defaultState;
        }), ExtraCodecs.nonEmptyList(BlockState.CODEC.listOf()).fieldOf("low_states").forGetter($$04 -> {
            return $$04.lowStates;
        }), ExtraCodecs.nonEmptyList(BlockState.CODEC.listOf()).fieldOf("high_states").forGetter($$05 -> {
            return $$05.highStates;
        }))).apply($$0, (v1, v2, v3, v4, v5, v6, v7, v8) -> {
            return new NoiseThresholdProvider(v1, v2, v3, v4, v5, v6, v7, v8);
        });
    });
    private final float threshold;
    private final float highChance;
    private final BlockState defaultState;
    private final List<BlockState> lowStates;
    private final List<BlockState> highStates;

    public NoiseThresholdProvider(long $$0, NormalNoise.NoiseParameters $$1, float $$2, float $$3, float $$4, BlockState $$5, List<BlockState> $$6, List<BlockState> $$7) {
        super($$0, $$1, $$2);
        this.threshold = $$3;
        this.highChance = $$4;
        this.defaultState = $$5;
        this.lowStates = $$6;
        this.highStates = $$7;
    }

    @Override // net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
    protected BlockStateProviderType<?> type() {
        return BlockStateProviderType.NOISE_THRESHOLD_PROVIDER;
    }

    @Override // net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
    public BlockState getState(RandomSource $$0, BlockPos $$1) {
        double $$2 = getNoiseValue($$1, this.scale);
        if ($$2 < this.threshold) {
            return (BlockState) Util.getRandom(this.lowStates, $$0);
        }
        if ($$0.nextFloat() < this.highChance) {
            return (BlockState) Util.getRandom(this.highStates, $$0);
        }
        return this.defaultState;
    }
}

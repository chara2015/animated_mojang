package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/stateproviders/BlockStateProvider.class */
public abstract class BlockStateProvider {
    public static final Codec<BlockStateProvider> CODEC = BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.codec();
    });

    protected abstract BlockStateProviderType<?> type();

    public abstract BlockState getState(RandomSource randomSource, BlockPos blockPos);

    public static SimpleStateProvider simple(BlockState $$0) {
        return new SimpleStateProvider($$0);
    }

    public static SimpleStateProvider simple(Block $$0) {
        return new SimpleStateProvider($$0.defaultBlockState());
    }
}

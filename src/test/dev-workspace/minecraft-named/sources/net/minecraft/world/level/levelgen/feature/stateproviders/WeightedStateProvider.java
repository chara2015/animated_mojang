package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/stateproviders/WeightedStateProvider.class */
public class WeightedStateProvider extends BlockStateProvider {
    public static final MapCodec<WeightedStateProvider> CODEC = WeightedList.nonEmptyCodec(BlockState.CODEC).comapFlatMap(WeightedStateProvider::create, $$0 -> {
        return $$0.weightedList;
    }).fieldOf("entries");
    private final WeightedList<BlockState> weightedList;

    private static DataResult<WeightedStateProvider> create(WeightedList<BlockState> $$0) {
        if ($$0.isEmpty()) {
            return DataResult.error(() -> {
                return "WeightedStateProvider with no states";
            });
        }
        return DataResult.success(new WeightedStateProvider($$0));
    }

    public WeightedStateProvider(WeightedList<BlockState> $$0) {
        this.weightedList = $$0;
    }

    public WeightedStateProvider(WeightedList.Builder<BlockState> $$0) {
        this($$0.build());
    }

    @Override // net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
    protected BlockStateProviderType<?> type() {
        return BlockStateProviderType.WEIGHTED_STATE_PROVIDER;
    }

    @Override // net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
    public BlockState getState(RandomSource $$0, BlockPos $$1) {
        return this.weightedList.getRandomOrThrow($$0);
    }
}

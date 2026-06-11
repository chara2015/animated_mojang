package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/blockpredicates/TrueBlockPredicate.class */
class TrueBlockPredicate implements BlockPredicate {
    public static TrueBlockPredicate INSTANCE = new TrueBlockPredicate();
    public static final MapCodec<TrueBlockPredicate> CODEC = MapCodec.unit(() -> {
        return INSTANCE;
    });

    private TrueBlockPredicate() {
    }

    @Override // java.util.function.BiPredicate
    public boolean test(WorldGenLevel $$0, BlockPos $$1) {
        return true;
    }

    @Override // net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
    public BlockPredicateType<?> type() {
        return BlockPredicateType.TRUE;
    }
}

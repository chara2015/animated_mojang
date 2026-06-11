package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/blockpredicates/AllOfPredicate.class */
class AllOfPredicate extends CombiningPredicate {
    public static final MapCodec<AllOfPredicate> CODEC = codec(AllOfPredicate::new);

    public AllOfPredicate(List<BlockPredicate> $$0) {
        super($$0);
    }

    @Override // java.util.function.BiPredicate
    public boolean test(WorldGenLevel $$0, BlockPos $$1) {
        for (BlockPredicate $$2 : this.predicates) {
            if (!$$2.test($$0, $$1)) {
                return false;
            }
        }
        return true;
    }

    @Override // net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
    public BlockPredicateType<?> type() {
        return BlockPredicateType.ALL_OF;
    }
}

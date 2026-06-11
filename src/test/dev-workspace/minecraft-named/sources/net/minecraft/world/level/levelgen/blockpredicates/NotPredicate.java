package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/blockpredicates/NotPredicate.class */
public class NotPredicate implements BlockPredicate {
    public static final MapCodec<NotPredicate> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(BlockPredicate.CODEC.fieldOf("predicate").forGetter($$0 -> {
            return $$0.predicate;
        })).apply($$0, NotPredicate::new);
    });
    private final BlockPredicate predicate;

    public NotPredicate(BlockPredicate $$0) {
        this.predicate = $$0;
    }

    @Override // java.util.function.BiPredicate
    public boolean test(WorldGenLevel $$0, BlockPos $$1) {
        return !this.predicate.test($$0, $$1);
    }

    @Override // net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
    public BlockPredicateType<?> type() {
        return BlockPredicateType.NOT;
    }
}

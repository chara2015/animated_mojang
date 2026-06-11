package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/placement/BlockPredicateFilter.class */
public class BlockPredicateFilter extends PlacementFilter {
    public static final MapCodec<BlockPredicateFilter> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(BlockPredicate.CODEC.fieldOf("predicate").forGetter($$0 -> {
            return $$0.predicate;
        })).apply($$0, BlockPredicateFilter::new);
    });
    private final BlockPredicate predicate;

    private BlockPredicateFilter(BlockPredicate $$0) {
        this.predicate = $$0;
    }

    public static BlockPredicateFilter forPredicate(BlockPredicate $$0) {
        return new BlockPredicateFilter($$0);
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementFilter
    protected boolean shouldPlace(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
        return this.predicate.test($$0.getLevel(), $$2);
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public PlacementModifierType<?> type() {
        return PlacementModifierType.BLOCK_PREDICATE_FILTER;
    }
}

package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.SnbtOperations;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/blockpredicates/BlockPredicateType.class */
public interface BlockPredicateType<P extends BlockPredicate> {
    public static final BlockPredicateType<MatchingBlocksPredicate> MATCHING_BLOCKS = register("matching_blocks", MatchingBlocksPredicate.CODEC);
    public static final BlockPredicateType<MatchingBlockTagPredicate> MATCHING_BLOCK_TAG = register("matching_block_tag", MatchingBlockTagPredicate.CODEC);
    public static final BlockPredicateType<MatchingFluidsPredicate> MATCHING_FLUIDS = register("matching_fluids", MatchingFluidsPredicate.CODEC);
    public static final BlockPredicateType<HasSturdyFacePredicate> HAS_STURDY_FACE = register("has_sturdy_face", HasSturdyFacePredicate.CODEC);
    public static final BlockPredicateType<SolidPredicate> SOLID = register("solid", SolidPredicate.CODEC);
    public static final BlockPredicateType<ReplaceablePredicate> REPLACEABLE = register("replaceable", ReplaceablePredicate.CODEC);
    public static final BlockPredicateType<WouldSurvivePredicate> WOULD_SURVIVE = register("would_survive", WouldSurvivePredicate.CODEC);
    public static final BlockPredicateType<InsideWorldBoundsPredicate> INSIDE_WORLD_BOUNDS = register("inside_world_bounds", InsideWorldBoundsPredicate.CODEC);
    public static final BlockPredicateType<AnyOfPredicate> ANY_OF = register("any_of", AnyOfPredicate.CODEC);
    public static final BlockPredicateType<AllOfPredicate> ALL_OF = register("all_of", AllOfPredicate.CODEC);
    public static final BlockPredicateType<NotPredicate> NOT = register("not", NotPredicate.CODEC);
    public static final BlockPredicateType<TrueBlockPredicate> TRUE = register(SnbtOperations.BUILTIN_TRUE, TrueBlockPredicate.CODEC);
    public static final BlockPredicateType<UnobstructedPredicate> UNOBSTRUCTED = register("unobstructed", UnobstructedPredicate.CODEC);

    MapCodec<P> codec();

    private static <P extends BlockPredicate> BlockPredicateType<P> register(String $$0, MapCodec<P> $$1) {
        return (BlockPredicateType) Registry.register(BuiltInRegistries.BLOCK_PREDICATE_TYPE, $$0, () -> {
            return $$1;
        });
    }
}

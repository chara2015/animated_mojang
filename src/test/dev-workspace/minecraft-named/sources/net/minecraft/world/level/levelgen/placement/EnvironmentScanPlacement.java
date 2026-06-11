package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/placement/EnvironmentScanPlacement.class */
public class EnvironmentScanPlacement extends PlacementModifier {
    private final Direction directionOfSearch;
    private final BlockPredicate targetCondition;
    private final BlockPredicate allowedSearchCondition;
    private final int maxSteps;
    public static final MapCodec<EnvironmentScanPlacement> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Direction.VERTICAL_CODEC.fieldOf("direction_of_search").forGetter($$0 -> {
            return $$0.directionOfSearch;
        }), BlockPredicate.CODEC.fieldOf("target_condition").forGetter($$02 -> {
            return $$02.targetCondition;
        }), BlockPredicate.CODEC.optionalFieldOf("allowed_search_condition", BlockPredicate.alwaysTrue()).forGetter($$03 -> {
            return $$03.allowedSearchCondition;
        }), Codec.intRange(1, 32).fieldOf("max_steps").forGetter($$04 -> {
            return Integer.valueOf($$04.maxSteps);
        })).apply($$0, (v1, v2, v3, v4) -> {
            return new EnvironmentScanPlacement(v1, v2, v3, v4);
        });
    });

    private EnvironmentScanPlacement(Direction $$0, BlockPredicate $$1, BlockPredicate $$2, int $$3) {
        this.directionOfSearch = $$0;
        this.targetCondition = $$1;
        this.allowedSearchCondition = $$2;
        this.maxSteps = $$3;
    }

    public static EnvironmentScanPlacement scanningFor(Direction $$0, BlockPredicate $$1, BlockPredicate $$2, int $$3) {
        return new EnvironmentScanPlacement($$0, $$1, $$2, $$3);
    }

    public static EnvironmentScanPlacement scanningFor(Direction $$0, BlockPredicate $$1, int $$2) {
        return scanningFor($$0, $$1, BlockPredicate.alwaysTrue(), $$2);
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
        BlockPos.MutableBlockPos $$3 = $$2.mutable();
        WorldGenLevel $$4 = $$0.getLevel();
        if (!this.allowedSearchCondition.test($$4, $$3)) {
            return Stream.of((Object[]) new BlockPos[0]);
        }
        for (int $$5 = 0; $$5 < this.maxSteps; $$5++) {
            if (this.targetCondition.test($$4, $$3)) {
                return Stream.of($$3);
            }
            $$3.move(this.directionOfSearch);
            if ($$4.isOutsideBuildHeight($$3.getY())) {
                return Stream.of((Object[]) new BlockPos[0]);
            }
            if (!this.allowedSearchCondition.test($$4, $$3)) {
                break;
            }
        }
        if (this.targetCondition.test($$4, $$3)) {
            return Stream.of($$3);
        }
        return Stream.of((Object[]) new BlockPos[0]);
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public PlacementModifierType<?> type() {
        return PlacementModifierType.ENVIRONMENT_SCAN;
    }
}

package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/CoralFeature.class */
public abstract class CoralFeature extends Feature<NoneFeatureConfiguration> {
    protected abstract boolean placeFeature(LevelAccessor levelAccessor, RandomSource randomSource, BlockPos blockPos, BlockState blockState);

    public CoralFeature(Codec<NoneFeatureConfiguration> $$0) {
        super($$0);
    }

    @Override // net.minecraft.world.level.levelgen.feature.Feature
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
        RandomSource $$1 = $$0.random();
        WorldGenLevel $$2 = $$0.level();
        BlockPos $$3 = $$0.origin();
        Optional<U> map = BuiltInRegistries.BLOCK.getRandomElementOf(BlockTags.CORAL_BLOCKS, $$1).map((v0) -> {
            return v0.value();
        });
        if (map.isEmpty()) {
            return false;
        }
        return placeFeature($$2, $$1, $$3, ((Block) map.get()).defaultBlockState());
    }

    protected boolean placeCoralBlock(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
        BlockPos $$4 = $$2.above();
        BlockState $$5 = $$0.getBlockState($$2);
        if ((!$$5.is(Blocks.WATER) && !$$5.is(BlockTags.CORALS)) || !$$0.getBlockState($$4).is(Blocks.WATER)) {
            return false;
        }
        $$0.setBlock($$2, $$3, 3);
        if ($$1.nextFloat() < 0.25f) {
            BuiltInRegistries.BLOCK.getRandomElementOf(BlockTags.CORALS, $$1).map((v0) -> {
                return v0.value();
            }).ifPresent($$22 -> {
                $$0.setBlock($$4, $$22.defaultBlockState(), 2);
            });
        } else if ($$1.nextFloat() < 0.05f) {
            $$0.setBlock($$4, (BlockState) Blocks.SEA_PICKLE.defaultBlockState().setValue(SeaPickleBlock.PICKLES, Integer.valueOf($$1.nextInt(4) + 1)), 2);
        }
        for (Direction $$6 : Direction.Plane.HORIZONTAL) {
            if ($$1.nextFloat() < 0.2f) {
                BlockPos $$7 = $$2.relative($$6);
                if ($$0.getBlockState($$7).is(Blocks.WATER)) {
                    BuiltInRegistries.BLOCK.getRandomElementOf(BlockTags.WALL_CORALS, $$1).map((v0) -> {
                        return v0.value();
                    }).ifPresent($$32 -> {
                        BlockState $$42 = $$32.defaultBlockState();
                        if ($$42.hasProperty(BaseCoralWallFanBlock.FACING)) {
                            $$42 = (BlockState) $$42.setValue(BaseCoralWallFanBlock.FACING, $$6);
                        }
                        $$0.setBlock($$7, $$42, 2);
                    });
                }
            }
        }
        return true;
    }
}

package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/CoralTreeFeature.class */
public class CoralTreeFeature extends CoralFeature {
    public CoralTreeFeature(Codec<NoneFeatureConfiguration> $$0) {
        super($$0);
    }

    @Override // net.minecraft.world.level.levelgen.feature.CoralFeature
    protected boolean placeFeature(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
        BlockPos.MutableBlockPos $$4 = $$2.mutable();
        int $$5 = $$1.nextInt(3) + 1;
        for (int $$6 = 0; $$6 < $$5; $$6++) {
            if (!placeCoralBlock($$0, $$1, $$4, $$3)) {
                return true;
            }
            $$4.move(Direction.UP);
        }
        BlockPos $$7 = $$4.immutable();
        int $$8 = $$1.nextInt(3) + 2;
        List<Direction> $$9 = Direction.Plane.HORIZONTAL.shuffledCopy($$1);
        List<Direction> $$10 = $$9.subList(0, $$8);
        for (Direction $$11 : $$10) {
            $$4.set($$7);
            $$4.move($$11);
            int $$12 = $$1.nextInt(5) + 2;
            int $$13 = 0;
            for (int $$14 = 0; $$14 < $$12 && placeCoralBlock($$0, $$1, $$4, $$3); $$14++) {
                $$13++;
                $$4.move(Direction.UP);
                if ($$14 == 0 || ($$13 >= 2 && $$1.nextFloat() < 0.25f)) {
                    $$4.move($$11);
                    $$13 = 0;
                }
            }
        }
        return true;
    }
}

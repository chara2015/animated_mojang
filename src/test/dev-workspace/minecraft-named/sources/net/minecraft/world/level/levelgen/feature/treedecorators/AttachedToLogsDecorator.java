package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/treedecorators/AttachedToLogsDecorator.class */
public class AttachedToLogsDecorator extends TreeDecorator {
    public static final MapCodec<AttachedToLogsDecorator> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.floatRange(0.0f, 1.0f).fieldOf("probability").forGetter($$0 -> {
            return Float.valueOf($$0.probability);
        }), BlockStateProvider.CODEC.fieldOf("block_provider").forGetter($$02 -> {
            return $$02.blockProvider;
        }), ExtraCodecs.nonEmptyList(Direction.CODEC.listOf()).fieldOf("directions").forGetter($$03 -> {
            return $$03.directions;
        })).apply($$0, (v1, v2, v3) -> {
            return new AttachedToLogsDecorator(v1, v2, v3);
        });
    });
    private final float probability;
    private final BlockStateProvider blockProvider;
    private final List<Direction> directions;

    public AttachedToLogsDecorator(float $$0, BlockStateProvider $$1, List<Direction> $$2) {
        this.probability = $$0;
        this.blockProvider = $$1;
        this.directions = $$2;
    }

    @Override // net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator
    public void place(TreeDecorator.Context $$0) {
        RandomSource $$1 = $$0.random();
        for (BlockPos $$2 : Util.shuffledCopy($$0.logs(), $$1)) {
            Direction $$3 = (Direction) Util.getRandom(this.directions, $$1);
            BlockPos $$4 = $$2.relative($$3);
            if ($$1.nextFloat() <= this.probability && $$0.isAir($$4)) {
                $$0.setBlock($$4, this.blockProvider.getState($$1, $$4));
            }
        }
    }

    @Override // net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator
    protected TreeDecoratorType<?> type() {
        return TreeDecoratorType.ATTACHED_TO_LOGS;
    }
}

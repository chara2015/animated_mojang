package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/trunkplacers/TrunkPlacer.class */
public abstract class TrunkPlacer {
    public static final Codec<TrunkPlacer> CODEC = BuiltInRegistries.TRUNK_PLACER_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.codec();
    });
    private static final int MAX_BASE_HEIGHT = 32;
    private static final int MAX_RAND = 24;
    public static final int MAX_HEIGHT = 80;
    protected final int baseHeight;
    protected final int heightRandA;
    protected final int heightRandB;

    protected abstract TrunkPlacerType<?> type();

    public abstract List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int i, BlockPos blockPos, TreeConfiguration treeConfiguration);

    protected static <P extends TrunkPlacer> Products.P3<RecordCodecBuilder.Mu<P>, Integer, Integer, Integer> trunkPlacerParts(RecordCodecBuilder.Instance<P> $$0) {
        return $$0.group(Codec.intRange(0, 32).fieldOf("base_height").forGetter($$02 -> {
            return Integer.valueOf($$02.baseHeight);
        }), Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter($$03 -> {
            return Integer.valueOf($$03.heightRandA);
        }), Codec.intRange(0, 24).fieldOf("height_rand_b").forGetter($$04 -> {
            return Integer.valueOf($$04.heightRandB);
        }));
    }

    public TrunkPlacer(int $$0, int $$1, int $$2) {
        this.baseHeight = $$0;
        this.heightRandA = $$1;
        this.heightRandB = $$2;
    }

    public int getTreeHeight(RandomSource $$0) {
        return this.baseHeight + $$0.nextInt(this.heightRandA + 1) + $$0.nextInt(this.heightRandB + 1);
    }

    private static boolean isDirt(LevelSimulatedReader $$0, BlockPos $$1) {
        return $$0.isStateAtPosition($$1, $$02 -> {
            return (!Feature.isDirt($$02) || $$02.is(Blocks.GRASS_BLOCK) || $$02.is(Blocks.MYCELIUM)) ? false : true;
        });
    }

    protected static void setDirtAt(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos $$3, TreeConfiguration $$4) {
        if ($$4.forceDirt || !isDirt($$0, $$3)) {
            $$1.accept($$3, $$4.dirtProvider.getState($$2, $$3));
        }
    }

    protected boolean placeLog(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos $$3, TreeConfiguration $$4) {
        return placeLog($$0, $$1, $$2, $$3, $$4, Function.identity());
    }

    protected boolean placeLog(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos $$3, TreeConfiguration $$4, Function<BlockState, BlockState> $$5) {
        if (validTreePos($$0, $$3)) {
            $$1.accept($$3, $$5.apply($$4.trunkProvider.getState($$2, $$3)));
            return true;
        }
        return false;
    }

    protected void placeLogIfFree(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos.MutableBlockPos $$3, TreeConfiguration $$4) {
        if (isFree($$0, $$3)) {
            placeLog($$0, $$1, $$2, $$3, $$4);
        }
    }

    protected boolean validTreePos(LevelSimulatedReader $$0, BlockPos $$1) {
        return TreeFeature.validTreePos($$0, $$1);
    }

    public boolean isFree(LevelSimulatedReader $$0, BlockPos $$1) {
        return validTreePos($$0, $$1) || $$0.isStateAtPosition($$1, $$02 -> {
            return $$02.is(BlockTags.LOGS);
        });
    }
}

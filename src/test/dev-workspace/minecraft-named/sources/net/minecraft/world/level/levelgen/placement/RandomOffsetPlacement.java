package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/placement/RandomOffsetPlacement.class */
public class RandomOffsetPlacement extends PlacementModifier {
    public static final MapCodec<RandomOffsetPlacement> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(IntProvider.codec(-16, 16).fieldOf("xz_spread").forGetter($$0 -> {
            return $$0.xzSpread;
        }), IntProvider.codec(-16, 16).fieldOf("y_spread").forGetter($$02 -> {
            return $$02.ySpread;
        })).apply($$0, RandomOffsetPlacement::new);
    });
    private final IntProvider xzSpread;
    private final IntProvider ySpread;

    public static RandomOffsetPlacement of(IntProvider $$0, IntProvider $$1) {
        return new RandomOffsetPlacement($$0, $$1);
    }

    public static RandomOffsetPlacement vertical(IntProvider $$0) {
        return new RandomOffsetPlacement(ConstantInt.of(0), $$0);
    }

    public static RandomOffsetPlacement horizontal(IntProvider $$0) {
        return new RandomOffsetPlacement($$0, ConstantInt.of(0));
    }

    private RandomOffsetPlacement(IntProvider $$0, IntProvider $$1) {
        this.xzSpread = $$0;
        this.ySpread = $$1;
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
        int $$3 = $$2.getX() + this.xzSpread.sample($$1);
        int $$4 = $$2.getY() + this.ySpread.sample($$1);
        int $$5 = $$2.getZ() + this.xzSpread.sample($$1);
        return Stream.of(new BlockPos($$3, $$4, $$5));
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public PlacementModifierType<?> type() {
        return PlacementModifierType.RANDOM_OFFSET;
    }
}

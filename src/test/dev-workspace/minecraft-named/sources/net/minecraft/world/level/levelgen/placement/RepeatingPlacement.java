package net.minecraft.world.level.levelgen.placement;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/placement/RepeatingPlacement.class */
public abstract class RepeatingPlacement extends PlacementModifier {
    protected abstract int count(RandomSource randomSource, BlockPos blockPos);

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
        return IntStream.range(0, count($$1, $$2)).mapToObj($$12 -> {
            return $$2;
        });
    }
}

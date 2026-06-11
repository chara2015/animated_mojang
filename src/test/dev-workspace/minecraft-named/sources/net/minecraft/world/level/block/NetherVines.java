package net.minecraft.world.level.block;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/NetherVines.class */
public class NetherVines {
    private static final double BONEMEAL_GROW_PROBABILITY_DECREASE_RATE = 0.826d;
    public static final double GROW_PER_TICK_PROBABILITY = 0.1d;

    public static boolean isValidGrowthState(BlockState $$0) {
        return $$0.isAir();
    }

    public static int getBlocksToGrowWhenBonemealed(RandomSource $$0) {
        double $$1 = 1.0d;
        int $$2 = 0;
        while ($$0.nextDouble() < $$1) {
            $$1 *= BONEMEAL_GROW_PROBABILITY_DECREASE_RATE;
            $$2++;
        }
        return $$2;
    }
}

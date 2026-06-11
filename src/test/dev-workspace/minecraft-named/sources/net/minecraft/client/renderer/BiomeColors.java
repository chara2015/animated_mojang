package net.minecraft.client.renderer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/BiomeColors.class */
public class BiomeColors {
    public static final ColorResolver GRASS_COLOR_RESOLVER = (v0, v1, v2) -> {
        return v0.getGrassColor(v1, v2);
    };
    public static final ColorResolver FOLIAGE_COLOR_RESOLVER = ($$0, $$1, $$2) -> {
        return $$0.getFoliageColor();
    };
    public static final ColorResolver DRY_FOLIAGE_COLOR_RESOLVER = ($$0, $$1, $$2) -> {
        return $$0.getDryFoliageColor();
    };
    public static final ColorResolver WATER_COLOR_RESOLVER = ($$0, $$1, $$2) -> {
        return $$0.getWaterColor();
    };

    private static int getAverageColor(BlockAndTintGetter $$0, BlockPos $$1, ColorResolver $$2) {
        return $$0.getBlockTint($$1, $$2);
    }

    public static int getAverageGrassColor(BlockAndTintGetter $$0, BlockPos $$1) {
        return getAverageColor($$0, $$1, GRASS_COLOR_RESOLVER);
    }

    public static int getAverageFoliageColor(BlockAndTintGetter $$0, BlockPos $$1) {
        return getAverageColor($$0, $$1, FOLIAGE_COLOR_RESOLVER);
    }

    public static int getAverageDryFoliageColor(BlockAndTintGetter $$0, BlockPos $$1) {
        return getAverageColor($$0, $$1, DRY_FOLIAGE_COLOR_RESOLVER);
    }

    public static int getAverageWaterColor(BlockAndTintGetter $$0, BlockPos $$1) {
        return getAverageColor($$0, $$1, WATER_COLOR_RESOLVER);
    }
}

package net.labymod.v1_21_11.client.world;

import net.labymod.api.util.color.format.ColorFormat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/world/LabyBiomeBlender.class */
public class LabyBiomeBlender implements BlockAndTintGetter {
    private static final int RED_INDEX = 0;
    private static final int GREEN_INDEX = 1;
    private static final int BLUE_INDEX = 2;
    private static final int COMPONENTS = 3;
    private Level level;
    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;

    public LabyBiomeBlender setLevel(Level level) {
        this.level = level;
        return this;
    }

    public LabyBiomeBlender setArea(int minX, int minZ, int maxX, int maxZ) {
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
        return this;
    }

    public float getShade(@NotNull Direction direction, boolean shade) {
        return this.level.getShade(direction, shade);
    }

    @NotNull
    public LevelLightEngine getLightEngine() {
        return this.level.getLightEngine();
    }

    @Nullable
    public BlockEntity getBlockEntity(@NotNull BlockPos blockPos) {
        return this.level.getBlockEntity(blockPos);
    }

    @NotNull
    public BlockState getBlockState(@NotNull BlockPos blockPos) {
        return this.level.getBlockState(blockPos);
    }

    @NotNull
    public FluidState getFluidState(@NotNull BlockPos blockPos) {
        return this.level.getFluidState(blockPos);
    }

    public int getHeight() {
        return this.level.getHeight();
    }

    public int getBlockTint(@NotNull BlockPos blockPos, @NotNull ColorResolver resolver) {
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        int[] rgb = new int[COMPONENTS];
        int total = RED_INDEX;
        BlockPos.MutableBlockPos currentPos = new BlockPos.MutableBlockPos();
        for (int xIndex = this.minX; xIndex <= this.maxX; xIndex += GREEN_INDEX) {
            total = processZAxis(x, y, z, currentPos, resolver, total, rgb, xIndex);
        }
        if (total <= 0) {
            return -1;
        }
        return ColorFormat.ARGB32.pack(rgb[RED_INDEX] / total, rgb[GREEN_INDEX] / total, rgb[BLUE_INDEX] / total);
    }

    public int getMinY() {
        return this.level.getMinY();
    }

    private int processZAxis(int x, int y, int z, BlockPos.MutableBlockPos currentPos, ColorResolver resolver, int total, int[] rgb, int xIndex) {
        for (int zIndex = this.minZ; zIndex < this.maxZ; zIndex += GREEN_INDEX) {
            if (xIndex == 0 || zIndex == 0) {
                currentPos.set(x + xIndex, y, z + zIndex);
                total = blendColors(currentPos, resolver, total, rgb);
            }
        }
        return total;
    }

    private int blendColors(BlockPos.MutableBlockPos currentPos, ColorResolver resolver, int total, int[] rgb) {
        Holder<Biome> holder = this.level.getBiome(currentPos);
        Biome biome = holder == null ? null : (Biome) holder.value();
        if (biome != null) {
            int color = resolver.getColor(biome, currentPos.getX(), currentPos.getZ());
            rgb[RED_INDEX] = rgb[RED_INDEX] + ColorFormat.ARGB32.red(color);
            rgb[GREEN_INDEX] = rgb[GREEN_INDEX] + ColorFormat.ARGB32.green(color);
            rgb[BLUE_INDEX] = rgb[BLUE_INDEX] + ColorFormat.ARGB32.blue(color);
            total += GREEN_INDEX;
        }
        return total;
    }
}

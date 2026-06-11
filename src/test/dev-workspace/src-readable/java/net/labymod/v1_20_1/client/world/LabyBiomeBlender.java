package net.labymod.v1_20_1.client.world;

import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/world/LabyBiomeBlender.class */
public class LabyBiomeBlender implements clp {
    private static final int RED_INDEX = 0;
    private static final int GREEN_INDEX = 1;
    private static final int BLUE_INDEX = 2;
    private static final int COMPONENTS = 3;
    private cmm level;
    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;

    public LabyBiomeBlender setLevel(cmm level) {
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

    public float a(@NotNull ha direction, boolean shade) {
        return this.level.a(direction, shade);
    }

    @NotNull
    public dwt s_() {
        return this.level.s_();
    }

    @Nullable
    public czn c_(@NotNull gu blockPos) {
        return this.level.c_(blockPos);
    }

    @NotNull
    public dcb a_(@NotNull gu blockPos) {
        return this.level.a_(blockPos);
    }

    @NotNull
    public dxe b_(@NotNull gu blockPos) {
        return this.level.b_(blockPos);
    }

    public int D_() {
        return this.level.D_();
    }

    public int C_() {
        return this.level.C_();
    }

    public int a(@NotNull gu blockPos, @NotNull clx resolver) {
        int x = blockPos.u();
        int y = blockPos.v();
        int z = blockPos.w();
        int[] rgb = new int[3];
        int total = 0;
        a currentPos = new a();
        for (int xIndex = this.minX; xIndex <= this.maxX; xIndex++) {
            total = processZAxis(x, y, z, currentPos, resolver, total, rgb, xIndex);
        }
        if (total <= 0) {
            return -1;
        }
        return ColorFormat.ARGB32.pack(rgb[0] / total, rgb[1] / total, rgb[2] / total);
    }

    private int processZAxis(int x, int y, int z, a currentPos, clx resolver, int total, int[] rgb, int xIndex) {
        for (int zIndex = this.minZ; zIndex < this.maxZ; zIndex++) {
            if (xIndex == 0 || zIndex == 0) {
                currentPos.d(x + xIndex, y, z + zIndex);
                total = blendColors(currentPos, resolver, total, rgb);
            }
        }
        return total;
    }

    private int blendColors(a currentPos, clx resolver, int total, int[] rgb) {
        he<cnk> holder = this.level.s(currentPos);
        cnk biome = holder == null ? null : (cnk) holder.a();
        if (biome != null) {
            int color = resolver.getColor(biome, currentPos.u(), currentPos.w());
            rgb[0] = rgb[0] + ColorFormat.ARGB32.red(color);
            rgb[1] = rgb[1] + ColorFormat.ARGB32.green(color);
            rgb[2] = rgb[2] + ColorFormat.ARGB32.blue(color);
            total++;
        }
        return total;
    }
}

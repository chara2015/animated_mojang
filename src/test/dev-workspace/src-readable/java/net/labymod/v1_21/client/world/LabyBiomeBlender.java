package net.labymod.v1_21.client.world;

import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/client/world/LabyBiomeBlender.class */
public class LabyBiomeBlender implements dbz {
    private static final int RED_INDEX = 0;
    private static final int GREEN_INDEX = 1;
    private static final int BLUE_INDEX = 2;
    private static final int COMPONENTS = 3;
    private dcw level;
    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;

    public LabyBiomeBlender setLevel(dcw level) {
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

    public float a(@NotNull ji direction, boolean shade) {
        return this.level.a(direction, shade);
    }

    @NotNull
    public eot y_() {
        return this.level.y_();
    }

    @Nullable
    public dqh c_(@NotNull jd blockPos) {
        return this.level.c_(blockPos);
    }

    @NotNull
    public dtc a_(@NotNull jd blockPos) {
        return this.level.a_(blockPos);
    }

    @NotNull
    public epe b_(@NotNull jd blockPos) {
        return this.level.b_(blockPos);
    }

    public int J_() {
        return this.level.J_();
    }

    public int I_() {
        return this.level.I_();
    }

    public int a(@NotNull jd blockPos, @NotNull dch resolver) {
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

    private int processZAxis(int x, int y, int z, a currentPos, dch resolver, int total, int[] rgb, int xIndex) {
        for (int zIndex = this.minZ; zIndex < this.maxZ; zIndex++) {
            if (xIndex == 0 || zIndex == 0) {
                currentPos.d(x + xIndex, y, z + zIndex);
                total = blendColors(currentPos, resolver, total, rgb);
            }
        }
        return total;
    }

    private int blendColors(a currentPos, dch resolver, int total, int[] rgb) {
        jm<ddw> holder = this.level.t(currentPos);
        ddw biome = holder == null ? null : (ddw) holder.a();
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

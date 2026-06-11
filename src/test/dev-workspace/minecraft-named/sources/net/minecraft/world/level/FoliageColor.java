package net.minecraft.world.level;

import net.minecraft.network.codec.ByteBufCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/FoliageColor.class */
public class FoliageColor {
    public static final int FOLIAGE_EVERGREEN = -10380959;
    public static final int FOLIAGE_BIRCH = -8345771;
    public static final int FOLIAGE_DEFAULT = -12012264;
    public static final int FOLIAGE_MANGROVE = -7158200;
    private static int[] pixels = new int[ByteBufCodecs.MAX_INITIAL_COLLECTION_SIZE];

    public static void init(int[] $$0) {
        pixels = $$0;
    }

    public static int get(double $$0, double $$1) {
        return ColorMapColorUtil.get($$0, $$1, pixels, FOLIAGE_DEFAULT);
    }
}

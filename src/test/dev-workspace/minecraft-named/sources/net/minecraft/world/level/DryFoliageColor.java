package net.minecraft.world.level;

import net.minecraft.network.codec.ByteBufCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/DryFoliageColor.class */
public class DryFoliageColor {
    public static final int FOLIAGE_DRY_DEFAULT = -10732494;
    private static int[] pixels = new int[ByteBufCodecs.MAX_INITIAL_COLLECTION_SIZE];

    public static void init(int[] $$0) {
        pixels = $$0;
    }

    public static int get(double $$0, double $$1) {
        return ColorMapColorUtil.get($$0, $$1, pixels, FOLIAGE_DRY_DEFAULT);
    }
}

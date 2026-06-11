package net.minecraft.world.level;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/ColorMapColorUtil.class */
public interface ColorMapColorUtil {
    static int get(double $$0, double $$1, int[] $$2, int $$3) {
        int $$4 = (int) ((1.0d - $$0) * 255.0d);
        int $$5 = (int) ((1.0d - ($$1 * $$0)) * 255.0d);
        int $$6 = ($$5 << 8) | $$4;
        if ($$6 >= $$2.length) {
            return $$3;
        }
        return $$2[$$6];
    }
}

package net.minecraft.world.level.levelgen.synth;

import java.util.Locale;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/synth/NoiseUtils.class */
public class NoiseUtils {
    public static double biasTowardsExtreme(double $$0, double $$1) {
        return $$0 + ((Math.sin(3.141592653589793d * $$0) * $$1) / 3.141592653589793d);
    }

    public static void parityNoiseOctaveConfigString(StringBuilder $$0, double $$1, double $$2, double $$3, byte[] $$4) {
        $$0.append(String.format(Locale.ROOT, "xo=%.3f, yo=%.3f, zo=%.3f, p0=%d, p255=%d", Float.valueOf((float) $$1), Float.valueOf((float) $$2), Float.valueOf((float) $$3), Byte.valueOf($$4[0]), Byte.valueOf($$4[255])));
    }

    public static void parityNoiseOctaveConfigString(StringBuilder $$0, double $$1, double $$2, double $$3, int[] $$4) {
        $$0.append(String.format(Locale.ROOT, "xo=%.3f, yo=%.3f, zo=%.3f, p0=%d, p255=%d", Float.valueOf((float) $$1), Float.valueOf((float) $$2), Float.valueOf((float) $$3), Integer.valueOf($$4[0]), Integer.valueOf($$4[255])));
    }
}

package net.minecraft.util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/LinearCongruentialGenerator.class */
public class LinearCongruentialGenerator {
    private static final long MULTIPLIER = 6364136223846793005L;
    private static final long INCREMENT = 1442695040888963407L;

    public static long next(long $$0, long $$1) {
        return ($$0 * (($$0 * MULTIPLIER) + INCREMENT)) + $$1;
    }
}

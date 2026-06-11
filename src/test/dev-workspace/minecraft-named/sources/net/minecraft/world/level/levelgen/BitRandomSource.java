package net.minecraft.world.level.levelgen;

import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/BitRandomSource.class */
public interface BitRandomSource extends RandomSource {
    public static final float FLOAT_MULTIPLIER = 5.9604645E-8f;
    public static final double DOUBLE_MULTIPLIER = 1.1102230246251565E-16d;

    int next(int i);

    @Override // net.minecraft.util.RandomSource
    default int nextInt() {
        return next(32);
    }

    @Override // net.minecraft.util.RandomSource
    default int nextInt(int $$0) {
        int $$1;
        int $$2;
        if ($$0 <= 0) {
            throw new IllegalArgumentException("Bound must be positive");
        }
        if (($$0 & ($$0 - 1)) == 0) {
            return (int) ((((long) $$0) * ((long) next(31))) >> 31);
        }
        do {
            $$1 = next(31);
            $$2 = $$1 % $$0;
        } while (($$1 - $$2) + ($$0 - 1) < 0);
        return $$2;
    }

    @Override // net.minecraft.util.RandomSource
    default long nextLong() {
        int $$0 = next(32);
        int $$1 = next(32);
        long $$2 = ((long) $$0) << 32;
        return $$2 + ((long) $$1);
    }

    @Override // net.minecraft.util.RandomSource
    default boolean nextBoolean() {
        return next(1) != 0;
    }

    @Override // net.minecraft.util.RandomSource
    default float nextFloat() {
        return next(24) * 5.9604645E-8f;
    }

    @Override // net.minecraft.util.RandomSource
    default double nextDouble() {
        int $$0 = next(26);
        int $$1 = next(27);
        long $$2 = (((long) $$0) << 27) + ((long) $$1);
        return $$2 * 1.1102230246251565E-16d;
    }
}

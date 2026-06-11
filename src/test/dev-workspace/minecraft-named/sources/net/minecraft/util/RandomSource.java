package net.minecraft.util;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.level.levelgen.ThreadSafeLegacyRandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/RandomSource.class */
public interface RandomSource {

    @Deprecated
    public static final double GAUSSIAN_SPREAD_FACTOR = 2.297d;

    RandomSource fork();

    PositionalRandomFactory forkPositional();

    void setSeed(long j);

    int nextInt();

    int nextInt(int i);

    long nextLong();

    boolean nextBoolean();

    float nextFloat();

    double nextDouble();

    double nextGaussian();

    static RandomSource create() {
        return create(RandomSupport.generateUniqueSeed());
    }

    @Deprecated
    static RandomSource createThreadSafe() {
        return new ThreadSafeLegacyRandomSource(RandomSupport.generateUniqueSeed());
    }

    static RandomSource create(long $$0) {
        return new LegacyRandomSource($$0);
    }

    static RandomSource createNewThreadLocalInstance() {
        return new SingleThreadedRandomSource(ThreadLocalRandom.current().nextLong());
    }

    default int nextIntBetweenInclusive(int $$0, int $$1) {
        return nextInt(($$1 - $$0) + 1) + $$0;
    }

    default double triangle(double $$0, double $$1) {
        return $$0 + ($$1 * (nextDouble() - nextDouble()));
    }

    default float triangle(float $$0, float $$1) {
        return $$0 + ($$1 * (nextFloat() - nextFloat()));
    }

    default void consumeCount(int $$0) {
        for (int $$1 = 0; $$1 < $$0; $$1++) {
            nextInt();
        }
    }

    default int nextInt(int $$0, int $$1) {
        if ($$0 >= $$1) {
            throw new IllegalArgumentException("bound - origin is non positive");
        }
        return $$0 + nextInt($$1 - $$0);
    }
}

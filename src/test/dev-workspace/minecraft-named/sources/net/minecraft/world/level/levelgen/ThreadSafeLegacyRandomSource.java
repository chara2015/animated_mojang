package net.minecraft.world.level.levelgen;

import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/ThreadSafeLegacyRandomSource.class */
@Deprecated
public class ThreadSafeLegacyRandomSource implements BitRandomSource {
    private static final int MODULUS_BITS = 48;
    private static final long MODULUS_MASK = 281474976710655L;
    private static final long MULTIPLIER = 25214903917L;
    private static final long INCREMENT = 11;
    private final AtomicLong seed = new AtomicLong();
    private final MarsagliaPolarGaussian gaussianSource = new MarsagliaPolarGaussian(this);

    public ThreadSafeLegacyRandomSource(long $$0) {
        setSeed($$0);
    }

    @Override // net.minecraft.util.RandomSource
    public RandomSource fork() {
        return new ThreadSafeLegacyRandomSource(nextLong());
    }

    @Override // net.minecraft.util.RandomSource
    public PositionalRandomFactory forkPositional() {
        return new LegacyRandomSource.LegacyPositionalRandomFactory(nextLong());
    }

    @Override // net.minecraft.util.RandomSource
    public void setSeed(long $$0) {
        this.seed.set(($$0 ^ MULTIPLIER) & MODULUS_MASK);
    }

    @Override // net.minecraft.world.level.levelgen.BitRandomSource
    public int next(int $$0) {
        long $$1;
        long $$2;
        do {
            $$1 = this.seed.get();
            $$2 = (($$1 * MULTIPLIER) + INCREMENT) & MODULUS_MASK;
        } while (!this.seed.compareAndSet($$1, $$2));
        return (int) ($$2 >>> (48 - $$0));
    }

    @Override // net.minecraft.util.RandomSource
    public double nextGaussian() {
        return this.gaussianSource.nextGaussian();
    }
}

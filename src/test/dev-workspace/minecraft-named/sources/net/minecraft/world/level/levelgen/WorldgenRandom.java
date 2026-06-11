package net.minecraft.world.level.levelgen;

import java.util.function.LongFunction;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/WorldgenRandom.class */
public class WorldgenRandom extends LegacyRandomSource {
    private final RandomSource randomSource;
    private int count;

    public WorldgenRandom(RandomSource $$0) {
        super(0L);
        this.randomSource = $$0;
    }

    public int getCount() {
        return this.count;
    }

    @Override // net.minecraft.world.level.levelgen.LegacyRandomSource, net.minecraft.util.RandomSource
    public RandomSource fork() {
        return this.randomSource.fork();
    }

    @Override // net.minecraft.world.level.levelgen.LegacyRandomSource, net.minecraft.util.RandomSource
    public PositionalRandomFactory forkPositional() {
        return this.randomSource.forkPositional();
    }

    @Override // net.minecraft.world.level.levelgen.LegacyRandomSource, net.minecraft.world.level.levelgen.BitRandomSource
    public int next(int $$0) {
        this.count++;
        RandomSource randomSource = this.randomSource;
        if (randomSource instanceof LegacyRandomSource) {
            LegacyRandomSource $$1 = (LegacyRandomSource) randomSource;
            return $$1.next($$0);
        }
        return (int) (this.randomSource.nextLong() >>> (64 - $$0));
    }

    @Override // net.minecraft.world.level.levelgen.LegacyRandomSource, net.minecraft.util.RandomSource
    public synchronized void setSeed(long $$0) {
        if (this.randomSource == null) {
            return;
        }
        this.randomSource.setSeed($$0);
    }

    public long setDecorationSeed(long $$0, int $$1, int $$2) {
        setSeed($$0);
        long $$3 = nextLong() | 1;
        long $$4 = nextLong() | 1;
        long $$5 = ((((long) $$1) * $$3) + (((long) $$2) * $$4)) ^ $$0;
        setSeed($$5);
        return $$5;
    }

    public void setFeatureSeed(long $$0, int $$1, int $$2) {
        long $$3 = $$0 + ((long) $$1) + ((long) (10000 * $$2));
        setSeed($$3);
    }

    public void setLargeFeatureSeed(long $$0, int $$1, int $$2) {
        setSeed($$0);
        long $$3 = nextLong();
        long $$4 = nextLong();
        long $$5 = ((((long) $$1) * $$3) ^ (((long) $$2) * $$4)) ^ $$0;
        setSeed($$5);
    }

    public void setLargeFeatureWithSalt(long $$0, int $$1, int $$2, int $$3) {
        long $$4 = (((long) $$1) * 341873128712L) + (((long) $$2) * 132897987541L) + $$0 + ((long) $$3);
        setSeed($$4);
    }

    public static RandomSource seedSlimeChunk(int $$0, int $$1, long $$2, long $$3) {
        return RandomSource.create((((($$2 + ((long) (($$0 * $$0) * 4987142))) + ((long) ($$0 * 5947611))) + (((long) ($$1 * $$1)) * 4392871)) + ((long) ($$1 * 389711))) ^ $$3);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/WorldgenRandom$Algorithm.class */
    public enum Algorithm {
        LEGACY(LegacyRandomSource::new),
        XOROSHIRO(XoroshiroRandomSource::new);

        private final LongFunction<RandomSource> constructor;

        Algorithm(LongFunction longFunction) {
            this.constructor = longFunction;
        }

        public RandomSource newInstance(long $$0) {
            return this.constructor.apply($$0);
        }
    }
}

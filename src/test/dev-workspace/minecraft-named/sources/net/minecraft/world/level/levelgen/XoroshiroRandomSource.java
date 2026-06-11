package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.RandomSupport;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/XoroshiroRandomSource.class */
public class XoroshiroRandomSource implements RandomSource {
    private static final float FLOAT_UNIT = 5.9604645E-8f;
    private static final double DOUBLE_UNIT = 1.1102230246251565E-16d;
    public static final Codec<XoroshiroRandomSource> CODEC = Xoroshiro128PlusPlus.CODEC.xmap($$0 -> {
        return new XoroshiroRandomSource($$0);
    }, $$02 -> {
        return $$02.randomNumberGenerator;
    });
    private Xoroshiro128PlusPlus randomNumberGenerator;
    private final MarsagliaPolarGaussian gaussianSource = new MarsagliaPolarGaussian(this);

    public XoroshiroRandomSource(long $$0) {
        this.randomNumberGenerator = new Xoroshiro128PlusPlus(RandomSupport.upgradeSeedTo128bit($$0));
    }

    public XoroshiroRandomSource(RandomSupport.Seed128bit $$0) {
        this.randomNumberGenerator = new Xoroshiro128PlusPlus($$0);
    }

    public XoroshiroRandomSource(long $$0, long $$1) {
        this.randomNumberGenerator = new Xoroshiro128PlusPlus($$0, $$1);
    }

    private XoroshiroRandomSource(Xoroshiro128PlusPlus $$0) {
        this.randomNumberGenerator = $$0;
    }

    @Override // net.minecraft.util.RandomSource
    public RandomSource fork() {
        return new XoroshiroRandomSource(this.randomNumberGenerator.nextLong(), this.randomNumberGenerator.nextLong());
    }

    @Override // net.minecraft.util.RandomSource
    public PositionalRandomFactory forkPositional() {
        return new XoroshiroPositionalRandomFactory(this.randomNumberGenerator.nextLong(), this.randomNumberGenerator.nextLong());
    }

    @Override // net.minecraft.util.RandomSource
    public void setSeed(long $$0) {
        this.randomNumberGenerator = new Xoroshiro128PlusPlus(RandomSupport.upgradeSeedTo128bit($$0));
        this.gaussianSource.reset();
    }

    @Override // net.minecraft.util.RandomSource
    public int nextInt() {
        return (int) this.randomNumberGenerator.nextLong();
    }

    @Override // net.minecraft.util.RandomSource
    public int nextInt(int $$0) {
        if ($$0 <= 0) {
            throw new IllegalArgumentException("Bound must be positive");
        }
        long $$1 = Integer.toUnsignedLong(nextInt());
        long $$2 = $$1 * ((long) $$0);
        long $$3 = $$2 & 4294967295L;
        if ($$3 < $$0) {
            int $$4 = Integer.remainderUnsigned(($$0 ^ (-1)) + 1, $$0);
            while ($$3 < $$4) {
                long $$12 = Integer.toUnsignedLong(nextInt());
                $$2 = $$12 * ((long) $$0);
                $$3 = $$2 & 4294967295L;
            }
        }
        long $$5 = $$2 >> 32;
        return (int) $$5;
    }

    @Override // net.minecraft.util.RandomSource
    public long nextLong() {
        return this.randomNumberGenerator.nextLong();
    }

    @Override // net.minecraft.util.RandomSource
    public boolean nextBoolean() {
        return (this.randomNumberGenerator.nextLong() & 1) != 0;
    }

    @Override // net.minecraft.util.RandomSource
    public float nextFloat() {
        return nextBits(24) * 5.9604645E-8f;
    }

    @Override // net.minecraft.util.RandomSource
    public double nextDouble() {
        return nextBits(53) * 1.1102230246251565E-16d;
    }

    @Override // net.minecraft.util.RandomSource
    public double nextGaussian() {
        return this.gaussianSource.nextGaussian();
    }

    @Override // net.minecraft.util.RandomSource
    public void consumeCount(int $$0) {
        for (int $$1 = 0; $$1 < $$0; $$1++) {
            this.randomNumberGenerator.nextLong();
        }
    }

    private long nextBits(int $$0) {
        return this.randomNumberGenerator.nextLong() >>> (64 - $$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/XoroshiroRandomSource$XoroshiroPositionalRandomFactory.class */
    public static class XoroshiroPositionalRandomFactory implements PositionalRandomFactory {
        private final long seedLo;
        private final long seedHi;

        public XoroshiroPositionalRandomFactory(long $$0, long $$1) {
            this.seedLo = $$0;
            this.seedHi = $$1;
        }

        @Override // net.minecraft.world.level.levelgen.PositionalRandomFactory
        public RandomSource at(int $$0, int $$1, int $$2) {
            long $$3 = Mth.getSeed($$0, $$1, $$2);
            long $$4 = $$3 ^ this.seedLo;
            return new XoroshiroRandomSource($$4, this.seedHi);
        }

        @Override // net.minecraft.world.level.levelgen.PositionalRandomFactory
        public RandomSource fromHashOf(String $$0) {
            RandomSupport.Seed128bit $$1 = RandomSupport.seedFromHashOf($$0);
            return new XoroshiroRandomSource($$1.xor(this.seedLo, this.seedHi));
        }

        @Override // net.minecraft.world.level.levelgen.PositionalRandomFactory
        public RandomSource fromSeed(long $$0) {
            return new XoroshiroRandomSource($$0 ^ this.seedLo, $$0 ^ this.seedHi);
        }

        @Override // net.minecraft.world.level.levelgen.PositionalRandomFactory
        @VisibleForTesting
        public void parityConfigString(StringBuilder $$0) {
            $$0.append("seedLo: ").append(this.seedLo).append(", seedHi: ").append(this.seedHi);
        }
    }
}

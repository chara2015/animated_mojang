package net.minecraft.world.level.levelgen.synth;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.IntStream;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/synth/PerlinNoise.class */
public class PerlinNoise {
    private static final int ROUND_OFF = 33554432;
    private final ImprovedNoise[] noiseLevels;
    private final int firstOctave;
    private final DoubleList amplitudes;
    private final double lowestFreqValueFactor;
    private final double lowestFreqInputFactor;
    private final double maxValue;

    @Deprecated
    public static PerlinNoise createLegacyForBlendedNoise(RandomSource $$0, IntStream $$1) {
        return new PerlinNoise($$0, makeAmplitudes(new IntRBTreeSet((Collection) $$1.boxed().collect(ImmutableList.toImmutableList()))), false);
    }

    @Deprecated
    public static PerlinNoise createLegacyForLegacyNetherBiome(RandomSource $$0, int $$1, DoubleList $$2) {
        return new PerlinNoise($$0, Pair.of(Integer.valueOf($$1), $$2), false);
    }

    public static PerlinNoise create(RandomSource $$0, IntStream $$1) {
        return create($$0, (List<Integer>) $$1.boxed().collect(ImmutableList.toImmutableList()));
    }

    public static PerlinNoise create(RandomSource $$0, List<Integer> $$1) {
        return new PerlinNoise($$0, makeAmplitudes(new IntRBTreeSet($$1)), true);
    }

    public static PerlinNoise create(RandomSource $$0, int $$1, double $$2, double... $$3) {
        DoubleArrayList $$4 = new DoubleArrayList($$3);
        $$4.add(0, $$2);
        return new PerlinNoise($$0, Pair.of(Integer.valueOf($$1), $$4), true);
    }

    public static PerlinNoise create(RandomSource $$0, int $$1, DoubleList $$2) {
        return new PerlinNoise($$0, Pair.of(Integer.valueOf($$1), $$2), true);
    }

    private static Pair<Integer, DoubleList> makeAmplitudes(IntSortedSet $$0) {
        if ($$0.isEmpty()) {
            throw new IllegalArgumentException("Need some octaves!");
        }
        int $$1 = -$$0.firstInt();
        int $$2 = $$0.lastInt();
        int $$3 = $$1 + $$2 + 1;
        if ($$3 < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
        }
        DoubleArrayList doubleArrayList = new DoubleArrayList(new double[$$3]);
        IntBidirectionalIterator $$5 = $$0.iterator();
        while ($$5.hasNext()) {
            int $$6 = $$5.nextInt();
            doubleArrayList.set($$6 + $$1, 1.0d);
        }
        return Pair.of(Integer.valueOf(-$$1), doubleArrayList);
    }

    protected PerlinNoise(RandomSource $$0, Pair<Integer, DoubleList> $$1, boolean $$2) {
        this.firstOctave = ((Integer) $$1.getFirst()).intValue();
        this.amplitudes = (DoubleList) $$1.getSecond();
        int $$3 = this.amplitudes.size();
        int $$4 = -this.firstOctave;
        this.noiseLevels = new ImprovedNoise[$$3];
        if ($$2) {
            PositionalRandomFactory $$5 = $$0.forkPositional();
            for (int $$6 = 0; $$6 < $$3; $$6++) {
                if (this.amplitudes.getDouble($$6) != Density.SURFACE) {
                    int $$7 = this.firstOctave + $$6;
                    this.noiseLevels[$$6] = new ImprovedNoise($$5.fromHashOf("octave_" + $$7));
                }
            }
        } else {
            ImprovedNoise $$8 = new ImprovedNoise($$0);
            if ($$4 >= 0 && $$4 < $$3) {
                double $$9 = this.amplitudes.getDouble($$4);
                if ($$9 != Density.SURFACE) {
                    this.noiseLevels[$$4] = $$8;
                }
            }
            for (int $$10 = $$4 - 1; $$10 >= 0; $$10--) {
                if ($$10 < $$3) {
                    double $$11 = this.amplitudes.getDouble($$10);
                    if ($$11 != Density.SURFACE) {
                        this.noiseLevels[$$10] = new ImprovedNoise($$0);
                    } else {
                        skipOctave($$0);
                    }
                } else {
                    skipOctave($$0);
                }
            }
            if (Arrays.stream(this.noiseLevels).filter((v0) -> {
                return Objects.nonNull(v0);
            }).count() != this.amplitudes.stream().filter($$02 -> {
                return $$02.doubleValue() != Density.SURFACE;
            }).count()) {
                throw new IllegalStateException("Failed to create correct number of noise levels for given non-zero amplitudes");
            }
            if ($$4 < $$3 - 1) {
                throw new IllegalArgumentException("Positive octaves are temporarily disabled");
            }
        }
        this.lowestFreqInputFactor = Math.pow(2.0d, -$$4);
        this.lowestFreqValueFactor = Math.pow(2.0d, $$3 - 1) / (Math.pow(2.0d, $$3) - 1.0d);
        this.maxValue = edgeValue(2.0d);
    }

    protected double maxValue() {
        return this.maxValue;
    }

    private static void skipOctave(RandomSource $$0) {
        $$0.consumeCount(InputConstants.KEY_RIGHT);
    }

    public double getValue(double $$0, double $$1, double $$2) {
        return getValue($$0, $$1, $$2, Density.SURFACE, Density.SURFACE, false);
    }

    @Deprecated
    public double getValue(double $$0, double $$1, double $$2, double $$3, double $$4, boolean $$5) {
        double $$6 = 0.0d;
        double $$7 = this.lowestFreqInputFactor;
        double $$8 = this.lowestFreqValueFactor;
        for (int $$9 = 0; $$9 < this.noiseLevels.length; $$9++) {
            ImprovedNoise $$10 = this.noiseLevels[$$9];
            if ($$10 != null) {
                double $$11 = $$10.noise(wrap($$0 * $$7), $$5 ? -$$10.yo : wrap($$1 * $$7), wrap($$2 * $$7), $$3 * $$7, $$4 * $$7);
                $$6 += this.amplitudes.getDouble($$9) * $$11 * $$8;
            }
            $$7 *= 2.0d;
            $$8 /= 2.0d;
        }
        return $$6;
    }

    public double maxBrokenValue(double $$0) {
        return edgeValue($$0 + 2.0d);
    }

    private double edgeValue(double $$0) {
        double $$1 = 0.0d;
        double $$2 = this.lowestFreqValueFactor;
        for (int $$3 = 0; $$3 < this.noiseLevels.length; $$3++) {
            ImprovedNoise $$4 = this.noiseLevels[$$3];
            if ($$4 != null) {
                $$1 += this.amplitudes.getDouble($$3) * $$0 * $$2;
            }
            $$2 /= 2.0d;
        }
        return $$1;
    }

    public ImprovedNoise getOctaveNoise(int $$0) {
        return this.noiseLevels[(this.noiseLevels.length - 1) - $$0];
    }

    public static double wrap(double $$0) {
        return $$0 - (Mth.lfloor(($$0 / 3.3554432E7d) + 0.5d) * 3.3554432E7d);
    }

    protected int firstOctave() {
        return this.firstOctave;
    }

    protected DoubleList amplitudes() {
        return this.amplitudes;
    }

    @VisibleForTesting
    public void parityConfigString(StringBuilder $$0) {
        $$0.append("PerlinNoise{");
        List<String> $$1 = this.amplitudes.stream().map($$02 -> {
            return String.format(Locale.ROOT, "%.2f", $$02);
        }).toList();
        $$0.append("first octave: ").append(this.firstOctave).append(", amplitudes: ").append($$1).append(", noise levels: [");
        for (int $$2 = 0; $$2 < this.noiseLevels.length; $$2++) {
            $$0.append($$2).append(": ");
            ImprovedNoise $$3 = this.noiseLevels[$$2];
            if ($$3 == null) {
                $$0.append("null");
            } else {
                $$3.parityConfigString($$0);
            }
            $$0.append(ComponentUtils.DEFAULT_SEPARATOR_TEXT);
        }
        $$0.append("]");
        $$0.append("}");
    }
}

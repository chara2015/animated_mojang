package net.minecraft.world.level.levelgen.synth;

import com.mojang.blaze3d.platform.InputConstants;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/synth/PerlinSimplexNoise.class */
public class PerlinSimplexNoise {
    private final SimplexNoise[] noiseLevels;
    private final double highestFreqValueFactor;
    private final double highestFreqInputFactor;

    public PerlinSimplexNoise(RandomSource $$0, List<Integer> $$1) {
        this($$0, (IntSortedSet) new IntRBTreeSet($$1));
    }

    private PerlinSimplexNoise(RandomSource $$0, IntSortedSet $$1) {
        if ($$1.isEmpty()) {
            throw new IllegalArgumentException("Need some octaves!");
        }
        int $$2 = -$$1.firstInt();
        int $$3 = $$1.lastInt();
        int $$4 = $$2 + $$3 + 1;
        if ($$4 < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
        }
        SimplexNoise $$5 = new SimplexNoise($$0);
        this.noiseLevels = new SimplexNoise[$$4];
        if ($$3 >= 0 && $$3 < $$4 && $$1.contains(0)) {
            this.noiseLevels[$$3] = $$5;
        }
        for (int $$7 = $$3 + 1; $$7 < $$4; $$7++) {
            if ($$7 >= 0 && $$1.contains($$3 - $$7)) {
                this.noiseLevels[$$7] = new SimplexNoise($$0);
            } else {
                $$0.consumeCount(InputConstants.KEY_RIGHT);
            }
        }
        if ($$3 > 0) {
            long $$8 = (long) ($$5.getValue($$5.xo, $$5.yo, $$5.zo) * 9.223372036854776E18d);
            RandomSource $$9 = new WorldgenRandom(new LegacyRandomSource($$8));
            for (int $$10 = $$3 - 1; $$10 >= 0; $$10--) {
                if ($$10 < $$4 && $$1.contains($$3 - $$10)) {
                    this.noiseLevels[$$10] = new SimplexNoise($$9);
                } else {
                    $$9.consumeCount(InputConstants.KEY_RIGHT);
                }
            }
        }
        this.highestFreqInputFactor = Math.pow(2.0d, $$3);
        this.highestFreqValueFactor = 1.0d / (Math.pow(2.0d, $$4) - 1.0d);
    }

    public double getValue(double $$0, double $$1, boolean $$2) {
        double $$3 = 0.0d;
        double $$4 = this.highestFreqInputFactor;
        double $$5 = this.highestFreqValueFactor;
        for (SimplexNoise $$6 : this.noiseLevels) {
            if ($$6 != null) {
                $$3 += $$6.getValue(($$0 * $$4) + ($$2 ? $$6.xo : Density.SURFACE), ($$1 * $$4) + ($$2 ? $$6.yo : Density.SURFACE)) * $$5;
            }
            $$4 /= 2.0d;
            $$5 *= 2.0d;
        }
        return $$3;
    }
}

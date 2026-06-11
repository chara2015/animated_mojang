package net.minecraft.world.level.levelgen.synth;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/synth/SimplexNoise.class */
public class SimplexNoise {
    protected static final int[][] GRADIENT = {new int[]{1, 1, 0}, new int[]{-1, 1, 0}, new int[]{1, -1, 0}, new int[]{-1, -1, 0}, new int[]{1, 0, 1}, new int[]{-1, 0, 1}, new int[]{1, 0, -1}, new int[]{-1, 0, -1}, new int[]{0, 1, 1}, new int[]{0, -1, 1}, new int[]{0, 1, -1}, new int[]{0, -1, -1}, new int[]{1, 1, 0}, new int[]{0, -1, 1}, new int[]{-1, 1, 0}, new int[]{0, -1, -1}};
    private static final double SQRT_3 = Math.sqrt(3.0d);
    private static final double F2 = 0.5d * (SQRT_3 - 1.0d);
    private static final double G2 = (3.0d - SQRT_3) / 6.0d;
    private final int[] p = new int[512];
    public final double xo;
    public final double yo;
    public final double zo;

    public SimplexNoise(RandomSource $$0) {
        this.xo = $$0.nextDouble() * 256.0d;
        this.yo = $$0.nextDouble() * 256.0d;
        this.zo = $$0.nextDouble() * 256.0d;
        for (int $$1 = 0; $$1 < 256; $$1++) {
            this.p[$$1] = $$1;
        }
        for (int $$2 = 0; $$2 < 256; $$2++) {
            int $$3 = $$0.nextInt(256 - $$2);
            int $$4 = this.p[$$2];
            this.p[$$2] = this.p[$$3 + $$2];
            this.p[$$3 + $$2] = $$4;
        }
    }

    private int p(int $$0) {
        return this.p[$$0 & 255];
    }

    protected static double dot(int[] $$0, double $$1, double $$2, double $$3) {
        return (((double) $$0[0]) * $$1) + (((double) $$0[1]) * $$2) + (((double) $$0[2]) * $$3);
    }

    private double getCornerNoise3D(int $$0, double $$1, double $$2, double $$3, double $$4) {
        double $$7;
        double $$5 = (($$4 - ($$1 * $$1)) - ($$2 * $$2)) - ($$3 * $$3);
        if ($$5 < Density.SURFACE) {
            $$7 = 0.0d;
        } else {
            double $$52 = $$5 * $$5;
            $$7 = $$52 * $$52 * dot(GRADIENT[$$0], $$1, $$2, $$3);
        }
        return $$7;
    }

    public double getValue(double $$0, double $$1) {
        int $$12;
        int $$13;
        double $$2 = ($$0 + $$1) * F2;
        int $$3 = Mth.floor($$0 + $$2);
        int $$4 = Mth.floor($$1 + $$2);
        double $$5 = ((double) ($$3 + $$4)) * G2;
        double $$6 = ((double) $$3) - $$5;
        double $$7 = ((double) $$4) - $$5;
        double $$8 = $$0 - $$6;
        double $$9 = $$1 - $$7;
        if ($$8 > $$9) {
            $$12 = 1;
            $$13 = 0;
        } else {
            $$12 = 0;
            $$13 = 1;
        }
        double $$14 = ($$8 - ((double) $$12)) + G2;
        double $$15 = ($$9 - ((double) $$13)) + G2;
        double $$16 = ($$8 - 1.0d) + (2.0d * G2);
        double $$17 = ($$9 - 1.0d) + (2.0d * G2);
        int $$18 = $$3 & 255;
        int $$19 = $$4 & 255;
        int $$20 = p($$18 + p($$19)) % 12;
        int $$21 = p(($$18 + $$12) + p($$19 + $$13)) % 12;
        int $$22 = p(($$18 + 1) + p($$19 + 1)) % 12;
        double $$23 = getCornerNoise3D($$20, $$8, $$9, Density.SURFACE, 0.5d);
        double $$24 = getCornerNoise3D($$21, $$14, $$15, Density.SURFACE, 0.5d);
        double $$25 = getCornerNoise3D($$22, $$16, $$17, Density.SURFACE, 0.5d);
        return 70.0d * ($$23 + $$24 + $$25);
    }

    public double getValue(double $$0, double $$1, double $$2) {
        int $$46;
        int $$47;
        int $$48;
        int $$49;
        int $$50;
        int $$51;
        double $$4 = ($$0 + $$1 + $$2) * 0.3333333333333333d;
        int $$5 = Mth.floor($$0 + $$4);
        int $$6 = Mth.floor($$1 + $$4);
        int $$7 = Mth.floor($$2 + $$4);
        double $$9 = ((double) ($$5 + $$6 + $$7)) * 0.16666666666666666d;
        double $$10 = ((double) $$5) - $$9;
        double $$11 = ((double) $$6) - $$9;
        double $$12 = ((double) $$7) - $$9;
        double $$13 = $$0 - $$10;
        double $$14 = $$1 - $$11;
        double $$15 = $$2 - $$12;
        if ($$13 >= $$14) {
            if ($$14 >= $$15) {
                $$46 = 1;
                $$47 = 0;
                $$48 = 0;
                $$49 = 1;
                $$50 = 1;
                $$51 = 0;
            } else if ($$13 >= $$15) {
                $$46 = 1;
                $$47 = 0;
                $$48 = 0;
                $$49 = 1;
                $$50 = 0;
                $$51 = 1;
            } else {
                $$46 = 0;
                $$47 = 0;
                $$48 = 1;
                $$49 = 1;
                $$50 = 0;
                $$51 = 1;
            }
        } else if ($$14 < $$15) {
            $$46 = 0;
            $$47 = 0;
            $$48 = 1;
            $$49 = 0;
            $$50 = 1;
            $$51 = 1;
        } else if ($$13 < $$15) {
            $$46 = 0;
            $$47 = 1;
            $$48 = 0;
            $$49 = 0;
            $$50 = 1;
            $$51 = 1;
        } else {
            $$46 = 0;
            $$47 = 1;
            $$48 = 0;
            $$49 = 1;
            $$50 = 1;
            $$51 = 0;
        }
        double $$52 = ($$13 - ((double) $$46)) + 0.16666666666666666d;
        double $$53 = ($$14 - ((double) $$47)) + 0.16666666666666666d;
        double $$54 = ($$15 - ((double) $$48)) + 0.16666666666666666d;
        double $$55 = ($$13 - ((double) $$49)) + 0.3333333333333333d;
        double $$56 = ($$14 - ((double) $$50)) + 0.3333333333333333d;
        double $$57 = ($$15 - ((double) $$51)) + 0.3333333333333333d;
        double $$58 = ($$13 - 1.0d) + 0.5d;
        double $$59 = ($$14 - 1.0d) + 0.5d;
        double $$60 = ($$15 - 1.0d) + 0.5d;
        int $$61 = $$5 & 255;
        int $$62 = $$6 & 255;
        int $$63 = $$7 & 255;
        int $$64 = p($$61 + p($$62 + p($$63))) % 12;
        int $$65 = p(($$61 + $$46) + p(($$62 + $$47) + p($$63 + $$48))) % 12;
        int $$66 = p(($$61 + $$49) + p(($$62 + $$50) + p($$63 + $$51))) % 12;
        int $$67 = p(($$61 + 1) + p(($$62 + 1) + p($$63 + 1))) % 12;
        double $$68 = getCornerNoise3D($$64, $$13, $$14, $$15, 0.6d);
        double $$69 = getCornerNoise3D($$65, $$52, $$53, $$54, 0.6d);
        double $$70 = getCornerNoise3D($$66, $$55, $$56, $$57, 0.6d);
        double $$71 = getCornerNoise3D($$67, $$58, $$59, $$60, 0.6d);
        return 32.0d * ($$68 + $$69 + $$70 + $$71);
    }
}

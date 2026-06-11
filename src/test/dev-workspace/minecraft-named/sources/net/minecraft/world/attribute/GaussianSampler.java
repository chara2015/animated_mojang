package net.minecraft.world.attribute;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/GaussianSampler.class */
public class GaussianSampler {
    private static final int GAUSSIAN_SAMPLE_RADIUS = 2;
    private static final int GAUSSIAN_SAMPLE_BREADTH = 6;
    private static final double[] GAUSSIAN_SAMPLE_KERNEL = {Density.SURFACE, 1.0d, 4.0d, 6.0d, 4.0d, 1.0d, Density.SURFACE};

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/GaussianSampler$Accumulator.class */
    @FunctionalInterface
    public interface Accumulator<V> {
        void accumulate(double d, V v);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/GaussianSampler$Sampler.class */
    @FunctionalInterface
    public interface Sampler<V> {
        V get(int i, int i2, int i3);
    }

    public static <V> void sample(Vec3 $$0, Sampler<V> $$1, Accumulator<V> $$2) {
        Vec3 $$02 = $$0.subtract(0.5d, 0.5d, 0.5d);
        int $$3 = Mth.floor($$02.x());
        int $$4 = Mth.floor($$02.y());
        int $$5 = Mth.floor($$02.z());
        double $$6 = $$02.x() - ((double) $$3);
        double $$7 = $$02.y() - ((double) $$4);
        double $$8 = $$02.z() - ((double) $$5);
        for (int $$9 = 0; $$9 < 6; $$9++) {
            double $$10 = Mth.lerp($$8, GAUSSIAN_SAMPLE_KERNEL[$$9 + 1], GAUSSIAN_SAMPLE_KERNEL[$$9]);
            int $$11 = ($$5 - 2) + $$9;
            for (int $$12 = 0; $$12 < 6; $$12++) {
                double $$13 = Mth.lerp($$6, GAUSSIAN_SAMPLE_KERNEL[$$12 + 1], GAUSSIAN_SAMPLE_KERNEL[$$12]);
                int $$14 = ($$3 - 2) + $$12;
                for (int $$15 = 0; $$15 < 6; $$15++) {
                    double $$16 = Mth.lerp($$7, GAUSSIAN_SAMPLE_KERNEL[$$15 + 1], GAUSSIAN_SAMPLE_KERNEL[$$15]);
                    int $$17 = ($$4 - 2) + $$15;
                    double $$18 = $$13 * $$16 * $$10;
                    V $$19 = $$1.get($$14, $$17, $$11);
                    $$2.accumulate($$18, $$19);
                }
            }
        }
    }
}

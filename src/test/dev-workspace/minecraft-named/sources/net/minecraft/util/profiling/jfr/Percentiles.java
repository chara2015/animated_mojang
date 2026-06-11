package net.minecraft.util.profiling.jfr;

import com.google.common.math.Quantiles;
import it.unimi.dsi.fastutil.ints.Int2DoubleRBTreeMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleSortedMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleSortedMaps;
import java.util.Comparator;
import java.util.Map;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/Percentiles.class */
public class Percentiles {
    public static final Quantiles.ScaleAndIndexes DEFAULT_INDEXES = Quantiles.scale(100).indexes(new int[]{50, 75, 90, 99});

    private Percentiles() {
    }

    public static Map<Integer, Double> evaluate(long[] $$0) {
        return $$0.length == 0 ? Map.of() : sorted(DEFAULT_INDEXES.compute($$0));
    }

    public static Map<Integer, Double> evaluate(int[] $$0) {
        return $$0.length == 0 ? Map.of() : sorted(DEFAULT_INDEXES.compute($$0));
    }

    public static Map<Integer, Double> evaluate(double[] $$0) {
        return $$0.length == 0 ? Map.of() : sorted(DEFAULT_INDEXES.compute($$0));
    }

    private static Map<Integer, Double> sorted(Map<Integer, Double> $$0) {
        Int2DoubleSortedMap $$1 = (Int2DoubleSortedMap) Util.make(new Int2DoubleRBTreeMap(Comparator.reverseOrder()), $$12 -> {
            $$12.putAll($$0);
        });
        return Int2DoubleSortedMaps.unmodifiable($$1);
    }
}

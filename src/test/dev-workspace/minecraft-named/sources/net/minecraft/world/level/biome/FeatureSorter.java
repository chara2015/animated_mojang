package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.Graph;
import net.minecraft.util.Util;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.commons.lang3.mutable.MutableInt;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/FeatureSorter.class */
public class FeatureSorter {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/FeatureSorter$StepFeatureData.class */
    public static final class StepFeatureData extends Record {
        private final List<PlacedFeature> features;
        private final ToIntFunction<PlacedFeature> indexMapping;

        public StepFeatureData(List<PlacedFeature> $$0, ToIntFunction<PlacedFeature> $$1) {
            this.features = $$0;
            this.indexMapping = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StepFeatureData.class), StepFeatureData.class, "features;indexMapping", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;->features:Ljava/util/List;", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;->indexMapping:Ljava/util/function/ToIntFunction;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StepFeatureData.class), StepFeatureData.class, "features;indexMapping", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;->features:Ljava/util/List;", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;->indexMapping:Ljava/util/function/ToIntFunction;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StepFeatureData.class, Object.class), StepFeatureData.class, "features;indexMapping", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;->features:Ljava/util/List;", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;->indexMapping:Ljava/util/function/ToIntFunction;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public List<PlacedFeature> features() {
            return this.features;
        }

        public ToIntFunction<PlacedFeature> indexMapping() {
            return this.indexMapping;
        }

        StepFeatureData(List<PlacedFeature> $$0) {
            this($$0, Util.createIndexIdentityLookup($$0));
        }
    }

    public static <T> List<StepFeatureData> buildFeaturesPerStep(List<T> $$0, Function<T, List<HolderSet<PlacedFeature>>> $$1, boolean $$2) {
        int $$21;
        Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
        MutableInt $$4 = new MutableInt(0);
        Comparator<T> comparatorThenComparingInt = Comparator.comparingInt((v0) -> {
            return v0.step();
        }).thenComparingInt((v0) -> {
            return v0.featureIndex();
        });
        Map<C1FeatureData, Set<C1FeatureData>> $$6 = new TreeMap<>((Comparator<? super C1FeatureData>) comparatorThenComparingInt);
        int $$7 = 0;
        for (T $$8 : $$0) {
            List<C1FeatureData> $$9 = Lists.newArrayList();
            List<HolderSet<PlacedFeature>> $$10 = $$1.apply($$8);
            $$7 = Math.max($$7, $$10.size());
            for (int $$11 = 0; $$11 < $$10.size(); $$11++) {
                Iterator<PlacedFeature> it = $$10.get($$11).iterator();
                while (it.hasNext()) {
                    Holder<PlacedFeature> $$12 = (Holder) it.next();
                    PlacedFeature $$13 = $$12.value();
                    $$9.add(new C1FeatureData(object2IntOpenHashMap.computeIfAbsent($$13, $$14 -> {
                        return $$4.getAndIncrement();
                    }), $$11, $$13));
                }
            }
            for (int $$142 = 0; $$142 < $$9.size(); $$142++) {
                Set<C1FeatureData> $$15 = $$6.computeIfAbsent($$9.get($$142), $$16 -> {
                    return new TreeSet(comparatorThenComparingInt);
                });
                if ($$142 < $$9.size() - 1) {
                    $$15.add($$9.get($$142 + 1));
                }
            }
        }
        Set<C1FeatureData> $$162 = new TreeSet<>((Comparator<? super C1FeatureData>) comparatorThenComparingInt);
        Set<C1FeatureData> $$17 = new TreeSet<>((Comparator<? super C1FeatureData>) comparatorThenComparingInt);
        List<C1FeatureData> $$18 = Lists.newArrayList();
        for (C1FeatureData $$19 : $$6.keySet()) {
            if (!$$17.isEmpty()) {
                throw new IllegalStateException("You somehow broke the universe; DFS bork (iteration finished with non-empty in-progress vertex set");
            }
            if (!$$162.contains($$19)) {
                Objects.requireNonNull($$18);
                if (Graph.depthFirstSearch($$6, $$162, $$17, (v1) -> {
                    r3.add(v1);
                }, $$19)) {
                    if ($$2) {
                        List<T> $$20 = new ArrayList<>($$0);
                        do {
                            $$21 = $$20.size();
                            ListIterator<T> $$22 = $$20.listIterator();
                            while ($$22.hasNext()) {
                                T $$23 = $$22.next();
                                $$22.remove();
                                try {
                                    buildFeaturesPerStep($$20, $$1, false);
                                    $$22.add($$23);
                                } catch (IllegalStateException e) {
                                }
                            }
                        } while ($$21 != $$20.size());
                        throw new IllegalStateException("Feature order cycle found, involved sources: " + String.valueOf($$20));
                    }
                    throw new IllegalStateException("Feature order cycle found");
                }
            }
        }
        Collections.reverse($$18);
        ImmutableList.Builder<StepFeatureData> $$25 = ImmutableList.builder();
        for (int $$26 = 0; $$26 < $$7; $$26++) {
            int $$27 = $$26;
            List<PlacedFeature> $$28 = (List) $$18.stream().filter($$110 -> {
                return $$110.step() == $$27;
            }).map((v0) -> {
                return v0.feature();
            }).collect(Collectors.toList());
            $$25.add(new StepFeatureData($$28));
        }
        return $$25.build();
    }

    /* JADX INFO: renamed from: net.minecraft.world.level.biome.FeatureSorter$1FeatureData, reason: invalid class name */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/FeatureSorter$1FeatureData.class */
    static final class C1FeatureData extends Record {
        private final int featureIndex;
        private final int step;
        private final PlacedFeature feature;

        C1FeatureData(int $$0, int $$1, PlacedFeature $$2) {
            this.featureIndex = $$0;
            this.step = $$1;
            this.feature = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, C1FeatureData.class), C1FeatureData.class, "featureIndex;step;feature", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;->featureIndex:I", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;->step:I", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;->feature:Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, C1FeatureData.class), C1FeatureData.class, "featureIndex;step;feature", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;->featureIndex:I", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;->step:I", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;->feature:Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, C1FeatureData.class, Object.class), C1FeatureData.class, "featureIndex;step;feature", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;->featureIndex:I", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;->step:I", "FIELD:Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;->feature:Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int featureIndex() {
            return this.featureIndex;
        }

        public int step() {
            return this.step;
        }

        public PlacedFeature feature() {
            return this.feature;
        }
    }
}

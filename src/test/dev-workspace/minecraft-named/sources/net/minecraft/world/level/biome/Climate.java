package net.minecraft.world.level.biome;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate.class */
public class Climate {
    private static final boolean DEBUG_SLOW_BIOME_SEARCH = false;
    private static final float QUANTIZATION_FACTOR = 10000.0f;

    @VisibleForTesting
    protected static final int PARAMETER_COUNT = 7;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$DistanceMetric.class */
    interface DistanceMetric<T> {
        long distance(RTree.Node<T> node, long[] jArr);
    }

    public static TargetPoint target(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
        return new TargetPoint(quantizeCoord($$0), quantizeCoord($$1), quantizeCoord($$2), quantizeCoord($$3), quantizeCoord($$4), quantizeCoord($$5));
    }

    public static ParameterPoint parameters(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6) {
        return new ParameterPoint(Parameter.point($$0), Parameter.point($$1), Parameter.point($$2), Parameter.point($$3), Parameter.point($$4), Parameter.point($$5), quantizeCoord($$6));
    }

    public static ParameterPoint parameters(Parameter $$0, Parameter $$1, Parameter $$2, Parameter $$3, Parameter $$4, Parameter $$5, float $$6) {
        return new ParameterPoint($$0, $$1, $$2, $$3, $$4, $$5, quantizeCoord($$6));
    }

    public static long quantizeCoord(float $$0) {
        return (long) ($$0 * QUANTIZATION_FACTOR);
    }

    public static float unquantizeCoord(long $$0) {
        return $$0 / QUANTIZATION_FACTOR;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$RTree.class */
    protected static final class RTree<T> {
        private static final int CHILDREN_PER_NODE = 6;
        private final Node<T> root;
        private final ThreadLocal<Leaf<T>> lastResult = new ThreadLocal<>();

        private RTree(Node<T> $$0) {
            this.root = $$0;
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$RTree$Node.class */
        static abstract class Node<T> {
            protected final Parameter[] parameterSpace;

            protected abstract Leaf<T> search(long[] jArr, Leaf<T> leaf, DistanceMetric<T> distanceMetric);

            protected Node(List<Parameter> $$0) {
                this.parameterSpace = (Parameter[]) $$0.toArray(new Parameter[0]);
            }

            protected long distance(long[] $$0) {
                long $$1 = 0;
                for (int $$2 = 0; $$2 < 7; $$2++) {
                    $$1 += Mth.square(this.parameterSpace[$$2].distance($$0[$$2]));
                }
                return $$1;
            }

            public String toString() {
                return Arrays.toString(this.parameterSpace);
            }
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$RTree$Leaf.class */
        static final class Leaf<T> extends Node<T> {
            final T value;

            Leaf(ParameterPoint $$0, T $$1) {
                super($$0.parameterSpace());
                this.value = $$1;
            }

            @Override // net.minecraft.world.level.biome.Climate.RTree.Node
            protected Leaf<T> search(long[] $$0, Leaf<T> $$1, DistanceMetric<T> $$2) {
                return this;
            }
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$RTree$SubTree.class */
        static final class SubTree<T> extends Node<T> {
            final Node<T>[] children;

            protected SubTree(List<? extends Node<T>> $$0) {
                this(RTree.buildParameterSpace($$0), $$0);
            }

            protected SubTree(List<Parameter> $$0, List<? extends Node<T>> $$1) {
                super($$0);
                this.children = (Node[]) $$1.toArray(new Node[0]);
            }

            @Override // net.minecraft.world.level.biome.Climate.RTree.Node
            protected Leaf<T> search(long[] $$0, Leaf<T> $$1, DistanceMetric<T> $$2) {
                long $$3 = $$1 == null ? DynamicGraphMinFixedPoint.SOURCE : $$2.distance($$1, $$0);
                Leaf<T> $$4 = $$1;
                Node<T>[] nodeArr = this.children;
                int length = nodeArr.length;
                for (int i = 0; i < length; i++) {
                    Node<T> $$5 = nodeArr[i];
                    long $$6 = $$2.distance($$5, $$0);
                    if ($$3 > $$6) {
                        Leaf<T> $$7 = $$5.search($$0, $$4, $$2);
                        long $$8 = $$5 == $$7 ? $$6 : $$2.distance($$7, $$0);
                        if ($$3 > $$8) {
                            $$3 = $$8;
                            $$4 = $$7;
                        }
                    }
                }
                return $$4;
            }
        }

        public static <T> RTree<T> create(List<Pair<ParameterPoint, T>> $$0) {
            if ($$0.isEmpty()) {
                throw new IllegalArgumentException("Need at least one value to build the search tree.");
            }
            int $$1 = ((ParameterPoint) $$0.get(0).getFirst()).parameterSpace().size();
            if ($$1 != 7) {
                throw new IllegalStateException("Expecting parameter space to be 7, got " + $$1);
            }
            List<Leaf<T>> $$2 = (List) $$0.stream().map($$02 -> {
                return new Leaf((ParameterPoint) $$02.getFirst(), $$02.getSecond());
            }).collect(Collectors.toCollection(ArrayList::new));
            return new RTree<>(build($$1, $$2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T> Node<T> build(int $$0, List<? extends Node<T>> $$1) {
            if ($$1.isEmpty()) {
                throw new IllegalStateException("Need at least one child to build a node");
            }
            if ($$1.size() == 1) {
                return $$1.get(0);
            }
            if ($$1.size() <= 6) {
                $$1.sort(Comparator.comparingLong($$12 -> {
                    long $$2 = 0;
                    for (int $$3 = 0; $$3 < $$0; $$3++) {
                        Parameter $$4 = $$12.parameterSpace[$$3];
                        $$2 += Math.abs(($$4.min() + $$4.max()) / 2);
                    }
                    return $$2;
                }));
                return new SubTree($$1);
            }
            long $$2 = Long.MAX_VALUE;
            int $$3 = -1;
            List<SubTree<T>> $$4 = null;
            for (int $$5 = 0; $$5 < $$0; $$5++) {
                sort($$1, $$0, $$5, false);
                List<SubTree<T>> $$6 = bucketize($$1);
                long $$7 = 0;
                for (SubTree<T> $$8 : $$6) {
                    $$7 += cost($$8.parameterSpace);
                }
                if ($$2 > $$7) {
                    $$2 = $$7;
                    $$3 = $$5;
                    $$4 = $$6;
                }
            }
            sort($$4, $$0, $$3, true);
            return new SubTree((List) $$4.stream().map($$13 -> {
                return build($$0, Arrays.asList($$13.children));
            }).collect(Collectors.toList()));
        }

        private static <T> void sort(List<? extends Node<T>> $$0, int $$1, int $$2, boolean $$3) {
            Comparator<? super Object> comparator = comparator($$2, $$3);
            for (int $$5 = 1; $$5 < $$1; $$5++) {
                comparator = comparator.thenComparing(comparator(($$2 + $$5) % $$1, $$3));
            }
            $$0.sort(comparator);
        }

        private static <T> Comparator<Node<T>> comparator(int $$0, boolean $$1) {
            return Comparator.comparingLong($$2 -> {
                Parameter $$3 = $$2.parameterSpace[$$0];
                long $$4 = ($$3.min() + $$3.max()) / 2;
                return $$1 ? Math.abs($$4) : $$4;
            });
        }

        private static <T> List<SubTree<T>> bucketize(List<? extends Node<T>> $$0) {
            List<SubTree<T>> $$1 = Lists.newArrayList();
            List<Node<T>> $$2 = Lists.newArrayList();
            int $$3 = (int) Math.pow(6.0d, Math.floor(Math.log(((double) $$0.size()) - 0.01d) / Math.log(6.0d)));
            for (Node<T> $$4 : $$0) {
                $$2.add($$4);
                if ($$2.size() >= $$3) {
                    $$1.add(new SubTree<>($$2));
                    $$2 = Lists.newArrayList();
                }
            }
            if (!$$2.isEmpty()) {
                $$1.add(new SubTree<>($$2));
            }
            return $$1;
        }

        private static long cost(Parameter[] $$0) {
            long $$1 = 0;
            for (Parameter $$2 : $$0) {
                $$1 += Math.abs($$2.max() - $$2.min());
            }
            return $$1;
        }

        static <T> List<Parameter> buildParameterSpace(List<? extends Node<T>> $$0) {
            if ($$0.isEmpty()) {
                throw new IllegalArgumentException("SubTree needs at least one child");
            }
            List<Parameter> $$2 = Lists.newArrayList();
            for (int $$3 = 0; $$3 < 7; $$3++) {
                $$2.add(null);
            }
            for (Node<T> $$4 : $$0) {
                for (int $$5 = 0; $$5 < 7; $$5++) {
                    $$2.set($$5, $$4.parameterSpace[$$5].span($$2.get($$5)));
                }
            }
            return $$2;
        }

        public T search(TargetPoint $$0, DistanceMetric<T> $$1) {
            long[] $$2 = $$0.toParameterArray();
            Leaf<T> $$3 = this.root.search($$2, this.lastResult.get(), $$1);
            this.lastResult.set($$3);
            return $$3.value;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$ParameterList.class */
    public static class ParameterList<T> {
        private final List<Pair<ParameterPoint, T>> values;
        private final RTree<T> index;

        public static <T> Codec<ParameterList<T>> codec(MapCodec<T> $$0) {
            return ExtraCodecs.nonEmptyList(RecordCodecBuilder.create($$1 -> {
                return $$1.group(ParameterPoint.CODEC.fieldOf("parameters").forGetter((v0) -> {
                    return v0.getFirst();
                }), $$0.forGetter((v0) -> {
                    return v0.getSecond();
                })).apply($$1, (v0, v1) -> {
                    return Pair.of(v0, v1);
                });
            }).listOf()).xmap(ParameterList::new, (v0) -> {
                return v0.values();
            });
        }

        public ParameterList(List<Pair<ParameterPoint, T>> $$0) {
            this.values = $$0;
            this.index = RTree.create($$0);
        }

        public List<Pair<ParameterPoint, T>> values() {
            return this.values;
        }

        public T findValue(TargetPoint $$0) {
            return findValueIndex($$0);
        }

        @VisibleForTesting
        public T findValueBruteForce(TargetPoint targetPoint) {
            Iterator<Pair<ParameterPoint, T>> it = values().iterator();
            Pair<ParameterPoint, T> next = it.next();
            long jFitness = ((ParameterPoint) next.getFirst()).fitness(targetPoint);
            Object second = next.getSecond();
            while (it.hasNext()) {
                Pair<ParameterPoint, T> next2 = it.next();
                long jFitness2 = ((ParameterPoint) next2.getFirst()).fitness(targetPoint);
                if (jFitness2 < jFitness) {
                    jFitness = jFitness2;
                    second = next2.getSecond();
                }
            }
            return (T) second;
        }

        public T findValueIndex(TargetPoint $$0) {
            return findValueIndex($$0, (v0, v1) -> {
                return v0.distance(v1);
            });
        }

        protected T findValueIndex(TargetPoint $$0, DistanceMetric<T> $$1) {
            return this.index.search($$0, $$1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$TargetPoint.class */
    public static final class TargetPoint extends Record {
        private final long temperature;
        private final long humidity;
        private final long continentalness;
        private final long erosion;
        private final long depth;
        private final long weirdness;

        public TargetPoint(long $$0, long $$1, long $$2, long $$3, long $$4, long $$5) {
            this.temperature = $$0;
            this.humidity = $$1;
            this.continentalness = $$2;
            this.erosion = $$3;
            this.depth = $$4;
            this.weirdness = $$5;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TargetPoint.class), TargetPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->temperature:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->humidity:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->continentalness:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->erosion:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->depth:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->weirdness:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TargetPoint.class), TargetPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->temperature:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->humidity:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->continentalness:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->erosion:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->depth:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->weirdness:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TargetPoint.class, Object.class), TargetPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->temperature:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->humidity:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->continentalness:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->erosion:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->depth:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$TargetPoint;->weirdness:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public long temperature() {
            return this.temperature;
        }

        public long humidity() {
            return this.humidity;
        }

        public long continentalness() {
            return this.continentalness;
        }

        public long erosion() {
            return this.erosion;
        }

        public long depth() {
            return this.depth;
        }

        public long weirdness() {
            return this.weirdness;
        }

        @VisibleForTesting
        protected long[] toParameterArray() {
            return new long[]{this.temperature, this.humidity, this.continentalness, this.erosion, this.depth, this.weirdness, 0};
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$ParameterPoint.class */
    public static final class ParameterPoint extends Record {
        private final Parameter temperature;
        private final Parameter humidity;
        private final Parameter continentalness;
        private final Parameter erosion;
        private final Parameter depth;
        private final Parameter weirdness;
        private final long offset;
        public static final Codec<ParameterPoint> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(Parameter.CODEC.fieldOf("temperature").forGetter($$0 -> {
                return $$0.temperature;
            }), Parameter.CODEC.fieldOf("humidity").forGetter($$02 -> {
                return $$02.humidity;
            }), Parameter.CODEC.fieldOf("continentalness").forGetter($$03 -> {
                return $$03.continentalness;
            }), Parameter.CODEC.fieldOf("erosion").forGetter($$04 -> {
                return $$04.erosion;
            }), Parameter.CODEC.fieldOf("depth").forGetter($$05 -> {
                return $$05.depth;
            }), Parameter.CODEC.fieldOf("weirdness").forGetter($$06 -> {
                return $$06.weirdness;
            }), Codec.floatRange(0.0f, 1.0f).fieldOf("offset").xmap((v0) -> {
                return Climate.quantizeCoord(v0);
            }, (v0) -> {
                return Climate.unquantizeCoord(v0);
            }).forGetter($$07 -> {
                return Long.valueOf($$07.offset);
            })).apply($$0, (v1, v2, v3, v4, v5, v6, v7) -> {
                return new ParameterPoint(v1, v2, v3, v4, v5, v6, v7);
            });
        });

        public ParameterPoint(Parameter $$0, Parameter $$1, Parameter $$2, Parameter $$3, Parameter $$4, Parameter $$5, long $$6) {
            this.temperature = $$0;
            this.humidity = $$1;
            this.continentalness = $$2;
            this.erosion = $$3;
            this.depth = $$4;
            this.weirdness = $$5;
            this.offset = $$6;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ParameterPoint.class), ParameterPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness;offset", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->temperature:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->humidity:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->continentalness:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->erosion:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->depth:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->weirdness:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->offset:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ParameterPoint.class), ParameterPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness;offset", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->temperature:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->humidity:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->continentalness:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->erosion:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->depth:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->weirdness:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->offset:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ParameterPoint.class, Object.class), ParameterPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness;offset", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->temperature:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->humidity:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->continentalness:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->erosion:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->depth:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->weirdness:Lnet/minecraft/world/level/biome/Climate$Parameter;", "FIELD:Lnet/minecraft/world/level/biome/Climate$ParameterPoint;->offset:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Parameter temperature() {
            return this.temperature;
        }

        public Parameter humidity() {
            return this.humidity;
        }

        public Parameter continentalness() {
            return this.continentalness;
        }

        public Parameter erosion() {
            return this.erosion;
        }

        public Parameter depth() {
            return this.depth;
        }

        public Parameter weirdness() {
            return this.weirdness;
        }

        public long offset() {
            return this.offset;
        }

        long fitness(TargetPoint $$0) {
            return Mth.square(this.temperature.distance($$0.temperature)) + Mth.square(this.humidity.distance($$0.humidity)) + Mth.square(this.continentalness.distance($$0.continentalness)) + Mth.square(this.erosion.distance($$0.erosion)) + Mth.square(this.depth.distance($$0.depth)) + Mth.square(this.weirdness.distance($$0.weirdness)) + Mth.square(this.offset);
        }

        protected List<Parameter> parameterSpace() {
            return ImmutableList.of(this.temperature, this.humidity, this.continentalness, this.erosion, this.depth, this.weirdness, new Parameter(this.offset, this.offset));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$Parameter.class */
    public static final class Parameter extends Record {
        private final long min;
        private final long max;
        public static final Codec<Parameter> CODEC = ExtraCodecs.intervalCodec(Codec.floatRange(-2.0f, 2.0f), "min", "max", ($$0, $$1) -> {
            if ($$0.compareTo($$1) > 0) {
                return DataResult.error(() -> {
                    return "Cannon construct interval, min > max (" + $$0 + " > " + $$1 + ")";
                });
            }
            return DataResult.success(new Parameter(Climate.quantizeCoord($$0.floatValue()), Climate.quantizeCoord($$1.floatValue())));
        }, $$02 -> {
            return Float.valueOf(Climate.unquantizeCoord($$02.min()));
        }, $$03 -> {
            return Float.valueOf(Climate.unquantizeCoord($$03.max()));
        });

        public Parameter(long $$0, long $$1) {
            this.min = $$0;
            this.max = $$1;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Parameter.class), Parameter.class, "min;max", "FIELD:Lnet/minecraft/world/level/biome/Climate$Parameter;->min:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$Parameter;->max:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Parameter.class, Object.class), Parameter.class, "min;max", "FIELD:Lnet/minecraft/world/level/biome/Climate$Parameter;->min:J", "FIELD:Lnet/minecraft/world/level/biome/Climate$Parameter;->max:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public long min() {
            return this.min;
        }

        public long max() {
            return this.max;
        }

        public static Parameter point(float $$0) {
            return span($$0, $$0);
        }

        public static Parameter span(float $$0, float $$1) {
            if ($$0 > $$1) {
                throw new IllegalArgumentException("min > max: " + $$0 + " " + $$1);
            }
            return new Parameter(Climate.quantizeCoord($$0), Climate.quantizeCoord($$1));
        }

        public static Parameter span(Parameter $$0, Parameter $$1) {
            if ($$0.min() > $$1.max()) {
                throw new IllegalArgumentException("min > max: " + String.valueOf($$0) + " " + String.valueOf($$1));
            }
            return new Parameter($$0.min(), $$1.max());
        }

        @Override // java.lang.Record
        public String toString() {
            return this.min == this.max ? String.format(Locale.ROOT, "%d", Long.valueOf(this.min)) : String.format(Locale.ROOT, "[%d-%d]", Long.valueOf(this.min), Long.valueOf(this.max));
        }

        public long distance(long $$0) {
            long $$1 = $$0 - this.max;
            long $$2 = this.min - $$0;
            if ($$1 > 0) {
                return $$1;
            }
            return Math.max($$2, 0L);
        }

        public long distance(Parameter $$0) {
            long $$1 = $$0.min() - this.max;
            long $$2 = this.min - $$0.max();
            if ($$1 > 0) {
                return $$1;
            }
            return Math.max($$2, 0L);
        }

        public Parameter span(Parameter $$0) {
            return $$0 == null ? this : new Parameter(Math.min(this.min, $$0.min()), Math.max(this.max, $$0.max()));
        }
    }

    public static Sampler empty() {
        DensityFunction $$0 = DensityFunctions.zero();
        return new Sampler($$0, $$0, $$0, $$0, $$0, $$0, List.of());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$Sampler.class */
    public static final class Sampler extends Record {
        private final DensityFunction temperature;
        private final DensityFunction humidity;
        private final DensityFunction continentalness;
        private final DensityFunction erosion;
        private final DensityFunction depth;
        private final DensityFunction weirdness;
        private final List<ParameterPoint> spawnTarget;

        public Sampler(DensityFunction $$0, DensityFunction $$1, DensityFunction $$2, DensityFunction $$3, DensityFunction $$4, DensityFunction $$5, List<ParameterPoint> $$6) {
            this.temperature = $$0;
            this.humidity = $$1;
            this.continentalness = $$2;
            this.erosion = $$3;
            this.depth = $$4;
            this.weirdness = $$5;
            this.spawnTarget = $$6;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Sampler.class), Sampler.class, "temperature;humidity;continentalness;erosion;depth;weirdness;spawnTarget", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->temperature:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->humidity:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->continentalness:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->erosion:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->depth:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->weirdness:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->spawnTarget:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Sampler.class), Sampler.class, "temperature;humidity;continentalness;erosion;depth;weirdness;spawnTarget", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->temperature:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->humidity:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->continentalness:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->erosion:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->depth:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->weirdness:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->spawnTarget:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Sampler.class, Object.class), Sampler.class, "temperature;humidity;continentalness;erosion;depth;weirdness;spawnTarget", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->temperature:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->humidity:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->continentalness:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->erosion:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->depth:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->weirdness:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/biome/Climate$Sampler;->spawnTarget:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public DensityFunction temperature() {
            return this.temperature;
        }

        public DensityFunction humidity() {
            return this.humidity;
        }

        public DensityFunction continentalness() {
            return this.continentalness;
        }

        public DensityFunction erosion() {
            return this.erosion;
        }

        public DensityFunction depth() {
            return this.depth;
        }

        public DensityFunction weirdness() {
            return this.weirdness;
        }

        public List<ParameterPoint> spawnTarget() {
            return this.spawnTarget;
        }

        public TargetPoint sample(int $$0, int $$1, int $$2) {
            int $$3 = QuartPos.toBlock($$0);
            int $$4 = QuartPos.toBlock($$1);
            int $$5 = QuartPos.toBlock($$2);
            DensityFunction.SinglePointContext $$6 = new DensityFunction.SinglePointContext($$3, $$4, $$5);
            return Climate.target((float) this.temperature.compute($$6), (float) this.humidity.compute($$6), (float) this.continentalness.compute($$6), (float) this.erosion.compute($$6), (float) this.depth.compute($$6), (float) this.weirdness.compute($$6));
        }

        public BlockPos findSpawnPosition() {
            if (this.spawnTarget.isEmpty()) {
                return BlockPos.ZERO;
            }
            return Climate.findSpawnPosition(this.spawnTarget, this);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$SpawnFinder.class */
    static class SpawnFinder {
        private static final long MAX_RADIUS = 2048;
        Result result;

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Climate$SpawnFinder$Result.class */
        static final class Result extends Record {
            private final BlockPos location;
            private final long fitness;

            Result(BlockPos $$0, long $$1) {
                this.location = $$0;
                this.fitness = $$1;
            }

            @Override // java.lang.Record
            public final String toString() {
                return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Result.class), Result.class, "location;fitness", "FIELD:Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;->location:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;->fitness:J").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final int hashCode() {
                return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Result.class), Result.class, "location;fitness", "FIELD:Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;->location:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;->fitness:J").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final boolean equals(Object $$0) {
                return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Result.class, Object.class), Result.class, "location;fitness", "FIELD:Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;->location:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;->fitness:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
            }

            public BlockPos location() {
                return this.location;
            }

            public long fitness() {
                return this.fitness;
            }
        }

        SpawnFinder(List<ParameterPoint> $$0, Sampler $$1) {
            this.result = getSpawnPositionAndFitness($$0, $$1, 0, 0);
            radialSearch($$0, $$1, 2048.0f, 512.0f);
            radialSearch($$0, $$1, 512.0f, 32.0f);
        }

        private void radialSearch(List<ParameterPoint> $$0, Sampler $$1, float $$2, float $$3) {
            float $$4 = 0.0f;
            float $$5 = $$3;
            BlockPos $$6 = this.result.location();
            while ($$5 <= $$2) {
                int $$7 = $$6.getX() + ((int) (Math.sin($$4) * ((double) $$5)));
                int $$8 = $$6.getZ() + ((int) (Math.cos($$4) * ((double) $$5)));
                Result $$9 = getSpawnPositionAndFitness($$0, $$1, $$7, $$8);
                if ($$9.fitness() < this.result.fitness()) {
                    this.result = $$9;
                }
                $$4 += $$3 / $$5;
                if ($$4 > 6.283185307179586d) {
                    $$4 = 0.0f;
                    $$5 += $$3;
                }
            }
        }

        private static Result getSpawnPositionAndFitness(List<ParameterPoint> $$0, Sampler $$1, int $$2, int $$3) {
            TargetPoint $$4 = $$1.sample(QuartPos.fromBlock($$2), 0, QuartPos.fromBlock($$3));
            TargetPoint $$5 = new TargetPoint($$4.temperature(), $$4.humidity(), $$4.continentalness(), $$4.erosion(), 0L, $$4.weirdness());
            long $$6 = Long.MAX_VALUE;
            for (ParameterPoint $$7 : $$0) {
                $$6 = Math.min($$6, $$7.fitness($$5));
            }
            long $$8 = Mth.square($$2) + Mth.square($$3);
            long $$9 = ($$6 * Mth.square(MAX_RADIUS)) + $$8;
            return new Result(new BlockPos($$2, 0, $$3), $$9);
        }
    }

    public static BlockPos findSpawnPosition(List<ParameterPoint> $$0, Sampler $$1) {
        return new SpawnFinder($$0, $$1).result.location();
    }
}

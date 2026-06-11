package net.minecraft.world.entity.variant;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.entity.variant.PriorityProvider.SelectorCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/variant/PriorityProvider.class */
public interface PriorityProvider<Context, Condition extends SelectorCondition<Context>> {
    List<Selector<Context, Condition>> selectors();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/variant/PriorityProvider$Selector.class */
    public static final class Selector<Context, Condition extends SelectorCondition<Context>> extends Record {
        private final Optional<Condition> condition;
        private final int priority;

        public Selector(Optional<Condition> $$0, int $$1) {
            this.condition = $$0;
            this.priority = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Selector.class), Selector.class, "condition;priority", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$Selector;->condition:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$Selector;->priority:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Selector.class), Selector.class, "condition;priority", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$Selector;->condition:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$Selector;->priority:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Selector.class, Object.class), Selector.class, "condition;priority", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$Selector;->condition:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$Selector;->priority:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Optional<Condition> condition() {
            return this.condition;
        }

        public int priority() {
            return this.priority;
        }

        public Selector(Condition $$0, int $$1) {
            this(Optional.of($$0), $$1);
        }

        public Selector(int $$0) {
            this(Optional.empty(), $$0);
        }

        public static <Context, Condition extends SelectorCondition<Context>> Codec<Selector<Context, Condition>> codec(Codec<Condition> $$0) {
            return RecordCodecBuilder.create($$1 -> {
                return $$1.group($$0.optionalFieldOf("condition").forGetter((v0) -> {
                    return v0.condition();
                }), Codec.INT.fieldOf("priority").forGetter((v0) -> {
                    return v0.priority();
                })).apply($$1, (v1, v2) -> {
                    return new Selector(v1, v2);
                });
            });
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/variant/PriorityProvider$SelectorCondition.class */
    @FunctionalInterface
    public interface SelectorCondition<C> extends Predicate<C> {
        static <C> SelectorCondition<C> alwaysTrue() {
            return $$0 -> {
                return true;
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/variant/PriorityProvider$UnpackedEntry.class */
    public static final class UnpackedEntry<C, T> extends Record {
        private final T entry;
        private final int priority;
        private final SelectorCondition<C> condition;
        public static final Comparator<UnpackedEntry<?, ?>> HIGHEST_PRIORITY_FIRST = Comparator.comparingInt((v0) -> {
            return v0.priority();
        }).reversed();

        public UnpackedEntry(T $$0, int $$1, SelectorCondition<C> $$2) {
            this.entry = $$0;
            this.priority = $$1;
            this.condition = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UnpackedEntry.class), UnpackedEntry.class, "entry;priority;condition", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$UnpackedEntry;->entry:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$UnpackedEntry;->priority:I", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$UnpackedEntry;->condition:Lnet/minecraft/world/entity/variant/PriorityProvider$SelectorCondition;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UnpackedEntry.class), UnpackedEntry.class, "entry;priority;condition", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$UnpackedEntry;->entry:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$UnpackedEntry;->priority:I", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$UnpackedEntry;->condition:Lnet/minecraft/world/entity/variant/PriorityProvider$SelectorCondition;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UnpackedEntry.class, Object.class), UnpackedEntry.class, "entry;priority;condition", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$UnpackedEntry;->entry:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$UnpackedEntry;->priority:I", "FIELD:Lnet/minecraft/world/entity/variant/PriorityProvider$UnpackedEntry;->condition:Lnet/minecraft/world/entity/variant/PriorityProvider$SelectorCondition;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public T entry() {
            return this.entry;
        }

        public int priority() {
            return this.priority;
        }

        public SelectorCondition<C> condition() {
            return this.condition;
        }
    }

    static <C, T> Stream<T> select(Stream<T> $$0, Function<T, PriorityProvider<C, ?>> $$1, C $$2) {
        List<UnpackedEntry<C, T>> $$3 = new ArrayList<>();
        $$0.forEach($$22 -> {
            for (Selector<Context, Condition> selector : ((PriorityProvider) $$1.apply($$22)).selectors()) {
                $$3.add(new UnpackedEntry($$22, selector.priority(), (SelectorCondition) DataFixUtils.orElseGet(selector.condition(), SelectorCondition::alwaysTrue)));
            }
        });
        $$3.sort(UnpackedEntry.HIGHEST_PRIORITY_FIRST);
        Iterator<UnpackedEntry<C, T>> $$4 = $$3.iterator();
        int $$5 = Integer.MIN_VALUE;
        while ($$4.hasNext()) {
            UnpackedEntry<C, T> $$6 = $$4.next();
            if (((UnpackedEntry) $$6).priority < $$5) {
                $$4.remove();
            } else if (((UnpackedEntry) $$6).condition.test($$2)) {
                $$5 = ((UnpackedEntry) $$6).priority;
            } else {
                $$4.remove();
            }
        }
        return $$3.stream().map((v0) -> {
            return v0.entry();
        });
    }

    static <C, T> Optional<T> pick(Stream<T> $$0, Function<T, PriorityProvider<C, ?>> $$1, RandomSource $$2, C $$3) {
        List<T> $$4 = select($$0, $$1, $$3).toList();
        return Util.getRandomSafe($$4, $$2);
    }

    static <Context, Condition extends SelectorCondition<Context>> List<Selector<Context, Condition>> single(Condition $$0, int $$1) {
        return List.of(new Selector($$0, $$1));
    }

    static <Context, Condition extends SelectorCondition<Context>> List<Selector<Context, Condition>> alwaysTrue(int $$0) {
        return List.of(new Selector(Optional.empty(), $$0));
    }
}

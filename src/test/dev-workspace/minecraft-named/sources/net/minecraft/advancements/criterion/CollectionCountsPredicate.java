package net.minecraft.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.advancements.criterion.MinMaxBounds;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/criterion/CollectionCountsPredicate.class */
public interface CollectionCountsPredicate<T, P extends Predicate<T>> extends Predicate<Iterable<T>> {
    List<Entry<T, P>> unpack();

    static <T, P extends Predicate<T>> Codec<CollectionCountsPredicate<T, P>> codec(Codec<P> $$0) {
        return Entry.codec($$0).listOf().xmap(CollectionCountsPredicate::of, (v0) -> {
            return v0.unpack();
        });
    }

    @SafeVarargs
    static <T, P extends Predicate<T>> CollectionCountsPredicate<T, P> of(Entry<T, P>... $$0) {
        return of(List.of((Object[]) $$0));
    }

    static <T, P extends Predicate<T>> CollectionCountsPredicate<T, P> of(List<Entry<T, P>> $$0) {
        switch ($$0.size()) {
            case 0:
                return new Zero();
            case 1:
                return new Single((Entry) $$0.getFirst());
            default:
                return new Multiple($$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/criterion/CollectionCountsPredicate$Zero.class */
    public static class Zero<T, P extends Predicate<T>> implements CollectionCountsPredicate<T, P> {
        @Override // java.util.function.Predicate
        public boolean test(Iterable<T> $$0) {
            return true;
        }

        @Override // net.minecraft.advancements.criterion.CollectionCountsPredicate
        public List<Entry<T, P>> unpack() {
            return List.of();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/criterion/CollectionCountsPredicate$Single.class */
    public static final class Single<T, P extends Predicate<T>> extends Record implements CollectionCountsPredicate<T, P> {
        private final Entry<T, P> entry;

        public Single(Entry<T, P> $$0) {
            this.entry = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Single.class), Single.class, "entry", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Single;->entry:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Entry;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Single.class), Single.class, "entry", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Single;->entry:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Entry;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Single.class, Object.class), Single.class, "entry", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Single;->entry:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Entry;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Entry<T, P> entry() {
            return this.entry;
        }

        @Override // java.util.function.Predicate
        public boolean test(Iterable<T> $$0) {
            return this.entry.test($$0);
        }

        @Override // net.minecraft.advancements.criterion.CollectionCountsPredicate
        public List<Entry<T, P>> unpack() {
            return List.of(this.entry);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/criterion/CollectionCountsPredicate$Multiple.class */
    public static final class Multiple<T, P extends Predicate<T>> extends Record implements CollectionCountsPredicate<T, P> {
        private final List<Entry<T, P>> entries;

        public Multiple(List<Entry<T, P>> $$0) {
            this.entries = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Multiple.class), Multiple.class, "entries", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Multiple;->entries:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Multiple.class), Multiple.class, "entries", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Multiple;->entries:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Multiple.class, Object.class), Multiple.class, "entries", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Multiple;->entries:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public List<Entry<T, P>> entries() {
            return this.entries;
        }

        @Override // java.util.function.Predicate
        public boolean test(Iterable<T> $$0) {
            for (Entry<T, P> $$1 : this.entries) {
                if (!$$1.test($$0)) {
                    return false;
                }
            }
            return true;
        }

        @Override // net.minecraft.advancements.criterion.CollectionCountsPredicate
        public List<Entry<T, P>> unpack() {
            return this.entries;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/criterion/CollectionCountsPredicate$Entry.class */
    public static final class Entry<T, P extends Predicate<T>> extends Record {
        private final P test;
        private final MinMaxBounds.Ints count;

        public Entry(P $$0, MinMaxBounds.Ints $$1) {
            this.test = $$0;
            this.count = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Entry.class), Entry.class, "test;count", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Entry;->test:Ljava/util/function/Predicate;", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Entry;->count:Lnet/minecraft/advancements/criterion/MinMaxBounds$Ints;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Entry.class), Entry.class, "test;count", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Entry;->test:Ljava/util/function/Predicate;", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Entry;->count:Lnet/minecraft/advancements/criterion/MinMaxBounds$Ints;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Entry.class, Object.class), Entry.class, "test;count", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Entry;->test:Ljava/util/function/Predicate;", "FIELD:Lnet/minecraft/advancements/criterion/CollectionCountsPredicate$Entry;->count:Lnet/minecraft/advancements/criterion/MinMaxBounds$Ints;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public P test() {
            return this.test;
        }

        public MinMaxBounds.Ints count() {
            return this.count;
        }

        public static <T, P extends Predicate<T>> Codec<Entry<T, P>> codec(Codec<P> $$0) {
            return RecordCodecBuilder.create($$1 -> {
                return $$1.group($$0.fieldOf("test").forGetter((v0) -> {
                    return v0.test();
                }), MinMaxBounds.Ints.CODEC.fieldOf("count").forGetter((v0) -> {
                    return v0.count();
                })).apply($$1, Entry::new);
            });
        }

        public boolean test(Iterable<T> $$0) {
            int $$1 = 0;
            for (T $$2 : $$0) {
                if (this.test.test($$2)) {
                    $$1++;
                }
            }
            return this.count.matches($$1);
        }
    }
}

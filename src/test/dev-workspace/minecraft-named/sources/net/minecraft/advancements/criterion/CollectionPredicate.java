package net.minecraft.advancements.criterion;

import com.google.common.collect.Iterables;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/criterion/CollectionPredicate.class */
public final class CollectionPredicate<T, P extends Predicate<T>> extends Record implements Predicate<Iterable<T>> {
    private final Optional<CollectionContentsPredicate<T, P>> contains;
    private final Optional<CollectionCountsPredicate<T, P>> counts;
    private final Optional<MinMaxBounds.Ints> size;

    public CollectionPredicate(Optional<CollectionContentsPredicate<T, P>> $$0, Optional<CollectionCountsPredicate<T, P>> $$1, Optional<MinMaxBounds.Ints> $$2) {
        this.contains = $$0;
        this.counts = $$1;
        this.size = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CollectionPredicate.class), CollectionPredicate.class, "contains;counts;size", "FIELD:Lnet/minecraft/advancements/criterion/CollectionPredicate;->contains:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/CollectionPredicate;->counts:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/CollectionPredicate;->size:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CollectionPredicate.class), CollectionPredicate.class, "contains;counts;size", "FIELD:Lnet/minecraft/advancements/criterion/CollectionPredicate;->contains:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/CollectionPredicate;->counts:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/CollectionPredicate;->size:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CollectionPredicate.class, Object.class), CollectionPredicate.class, "contains;counts;size", "FIELD:Lnet/minecraft/advancements/criterion/CollectionPredicate;->contains:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/CollectionPredicate;->counts:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/CollectionPredicate;->size:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Optional<CollectionContentsPredicate<T, P>> contains() {
        return this.contains;
    }

    public Optional<CollectionCountsPredicate<T, P>> counts() {
        return this.counts;
    }

    public Optional<MinMaxBounds.Ints> size() {
        return this.size;
    }

    public static <T, P extends Predicate<T>> Codec<CollectionPredicate<T, P>> codec(Codec<P> $$0) {
        return RecordCodecBuilder.create($$1 -> {
            return $$1.group(CollectionContentsPredicate.codec($$0).optionalFieldOf("contains").forGetter((v0) -> {
                return v0.contains();
            }), CollectionCountsPredicate.codec($$0).optionalFieldOf("count").forGetter((v0) -> {
                return v0.counts();
            }), MinMaxBounds.Ints.CODEC.optionalFieldOf(StructureTemplate.SIZE_TAG).forGetter((v0) -> {
                return v0.size();
            })).apply($$1, CollectionPredicate::new);
        });
    }

    @Override // java.util.function.Predicate
    public boolean test(Iterable<T> $$0) {
        if (this.contains.isPresent() && !this.contains.get().test($$0)) {
            return false;
        }
        if (this.counts.isPresent() && !this.counts.get().test($$0)) {
            return false;
        }
        if (this.size.isPresent() && !this.size.get().matches(Iterables.size($$0))) {
            return false;
        }
        return true;
    }
}

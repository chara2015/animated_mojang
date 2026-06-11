package net.minecraft.world.item.slot;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/slot/SlotCollection.class */
public interface SlotCollection {
    public static final SlotCollection EMPTY = Stream::empty;

    Stream<ItemStack> itemCopies();

    default SlotCollection filter(Predicate<ItemStack> $$0) {
        return new Filtered(this, $$0);
    }

    default SlotCollection flatMap(Function<ItemStack, ? extends SlotCollection> $$0) {
        return new FlatMapped(this, $$0);
    }

    default SlotCollection limit(int $$0) {
        return new Limited(this, $$0);
    }

    static SlotCollection of(SlotAccess $$0) {
        return () -> {
            return Stream.of($$0.get().copy());
        };
    }

    static SlotCollection of(Collection<? extends SlotAccess> $$0) {
        switch ($$0.size()) {
            case 0:
                return EMPTY;
            case 1:
                return of($$0.iterator().next());
            default:
                return () -> {
                    return $$0.stream().map((v0) -> {
                        return v0.get();
                    }).map((v0) -> {
                        return v0.copy();
                    });
                };
        }
    }

    static SlotCollection concat(SlotCollection $$0, SlotCollection $$1) {
        return () -> {
            return Stream.concat($$0.itemCopies(), $$1.itemCopies());
        };
    }

    static SlotCollection concat(List<? extends SlotCollection> $$0) {
        switch ($$0.size()) {
            case 0:
                return EMPTY;
            case 1:
                return (SlotCollection) $$0.getFirst();
            case 2:
                return concat($$0.get(0), $$0.get(1));
            default:
                return () -> {
                    return $$0.stream().flatMap((v0) -> {
                        return v0.itemCopies();
                    });
                };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/slot/SlotCollection$Filtered.class */
    public static final class Filtered extends Record implements SlotCollection {
        private final SlotCollection slots;
        private final Predicate<ItemStack> filter;

        public Filtered(SlotCollection $$0, Predicate<ItemStack> $$1) {
            this.slots = $$0;
            this.filter = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Filtered.class), Filtered.class, "slots;filter", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Filtered;->slots:Lnet/minecraft/world/item/slot/SlotCollection;", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Filtered;->filter:Ljava/util/function/Predicate;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Filtered.class), Filtered.class, "slots;filter", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Filtered;->slots:Lnet/minecraft/world/item/slot/SlotCollection;", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Filtered;->filter:Ljava/util/function/Predicate;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Filtered.class, Object.class), Filtered.class, "slots;filter", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Filtered;->slots:Lnet/minecraft/world/item/slot/SlotCollection;", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Filtered;->filter:Ljava/util/function/Predicate;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public SlotCollection slots() {
            return this.slots;
        }

        public Predicate<ItemStack> filter() {
            return this.filter;
        }

        @Override // net.minecraft.world.item.slot.SlotCollection
        public Stream<ItemStack> itemCopies() {
            return this.slots.itemCopies().filter(this.filter);
        }

        @Override // net.minecraft.world.item.slot.SlotCollection
        public SlotCollection filter(Predicate<ItemStack> $$0) {
            return new Filtered(this.slots, this.filter.and($$0));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/slot/SlotCollection$FlatMapped.class */
    public static final class FlatMapped extends Record implements SlotCollection {
        private final SlotCollection slots;
        private final Function<ItemStack, ? extends SlotCollection> mapper;

        public FlatMapped(SlotCollection $$0, Function<ItemStack, ? extends SlotCollection> $$1) {
            this.slots = $$0;
            this.mapper = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FlatMapped.class), FlatMapped.class, "slots;mapper", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$FlatMapped;->slots:Lnet/minecraft/world/item/slot/SlotCollection;", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$FlatMapped;->mapper:Ljava/util/function/Function;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FlatMapped.class), FlatMapped.class, "slots;mapper", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$FlatMapped;->slots:Lnet/minecraft/world/item/slot/SlotCollection;", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$FlatMapped;->mapper:Ljava/util/function/Function;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FlatMapped.class, Object.class), FlatMapped.class, "slots;mapper", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$FlatMapped;->slots:Lnet/minecraft/world/item/slot/SlotCollection;", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$FlatMapped;->mapper:Ljava/util/function/Function;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public SlotCollection slots() {
            return this.slots;
        }

        public Function<ItemStack, ? extends SlotCollection> mapper() {
            return this.mapper;
        }

        @Override // net.minecraft.world.item.slot.SlotCollection
        public Stream<ItemStack> itemCopies() {
            return this.slots.itemCopies().map(this.mapper).flatMap((v0) -> {
                return v0.itemCopies();
            });
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/slot/SlotCollection$Limited.class */
    public static final class Limited extends Record implements SlotCollection {
        private final SlotCollection slots;
        private final int limit;

        public Limited(SlotCollection $$0, int $$1) {
            this.slots = $$0;
            this.limit = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Limited.class), Limited.class, "slots;limit", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Limited;->slots:Lnet/minecraft/world/item/slot/SlotCollection;", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Limited;->limit:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Limited.class), Limited.class, "slots;limit", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Limited;->slots:Lnet/minecraft/world/item/slot/SlotCollection;", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Limited;->limit:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Limited.class, Object.class), Limited.class, "slots;limit", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Limited;->slots:Lnet/minecraft/world/item/slot/SlotCollection;", "FIELD:Lnet/minecraft/world/item/slot/SlotCollection$Limited;->limit:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public SlotCollection slots() {
            return this.slots;
        }

        public int limit() {
            return this.limit;
        }

        @Override // net.minecraft.world.item.slot.SlotCollection
        public Stream<ItemStack> itemCopies() {
            return this.slots.itemCopies().limit(this.limit);
        }

        @Override // net.minecraft.world.item.slot.SlotCollection
        public SlotCollection limit(int $$0) {
            return new Limited(this.slots, Math.min(this.limit, $$0));
        }
    }
}

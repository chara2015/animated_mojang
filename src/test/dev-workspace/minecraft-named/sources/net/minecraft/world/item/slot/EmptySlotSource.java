package net.minecraft.world.item.slot;

import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.level.storage.loot.LootContext;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/slot/EmptySlotSource.class */
public final class EmptySlotSource extends Record implements SlotSource {
    public static final MapCodec<EmptySlotSource> MAP_CODEC = MapCodec.unit(new EmptySlotSource());

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EmptySlotSource.class), EmptySlotSource.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EmptySlotSource.class), EmptySlotSource.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EmptySlotSource.class, Object.class), EmptySlotSource.class, "").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.world.item.slot.SlotSource
    public MapCodec<EmptySlotSource> codec() {
        return MAP_CODEC;
    }

    @Override // net.minecraft.world.item.slot.SlotSource
    public SlotCollection provide(LootContext $$0) {
        return SlotCollection.EMPTY;
    }
}

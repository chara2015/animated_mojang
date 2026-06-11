package net.minecraft.world.level.storage.loot.entries;

import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/entries/LootPoolEntryType.class */
public final class LootPoolEntryType extends Record {
    private final MapCodec<? extends LootPoolEntryContainer> codec;

    public LootPoolEntryType(MapCodec<? extends LootPoolEntryContainer> $$0) {
        this.codec = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LootPoolEntryType.class), LootPoolEntryType.class, "codec", "FIELD:Lnet/minecraft/world/level/storage/loot/entries/LootPoolEntryType;->codec:Lcom/mojang/serialization/MapCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LootPoolEntryType.class), LootPoolEntryType.class, "codec", "FIELD:Lnet/minecraft/world/level/storage/loot/entries/LootPoolEntryType;->codec:Lcom/mojang/serialization/MapCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LootPoolEntryType.class, Object.class), LootPoolEntryType.class, "codec", "FIELD:Lnet/minecraft/world/level/storage/loot/entries/LootPoolEntryType;->codec:Lcom/mojang/serialization/MapCodec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public MapCodec<? extends LootPoolEntryContainer> codec() {
        return this.codec;
    }
}

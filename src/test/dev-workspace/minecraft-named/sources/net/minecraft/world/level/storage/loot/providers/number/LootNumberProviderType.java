package net.minecraft.world.level.storage.loot.providers.number;

import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/providers/number/LootNumberProviderType.class */
public final class LootNumberProviderType extends Record {
    private final MapCodec<? extends NumberProvider> codec;

    public LootNumberProviderType(MapCodec<? extends NumberProvider> $$0) {
        this.codec = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LootNumberProviderType.class), LootNumberProviderType.class, "codec", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/LootNumberProviderType;->codec:Lcom/mojang/serialization/MapCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LootNumberProviderType.class), LootNumberProviderType.class, "codec", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/LootNumberProviderType;->codec:Lcom/mojang/serialization/MapCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LootNumberProviderType.class, Object.class), LootNumberProviderType.class, "codec", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/LootNumberProviderType;->codec:Lcom/mojang/serialization/MapCodec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public MapCodec<? extends NumberProvider> codec() {
        return this.codec;
    }
}

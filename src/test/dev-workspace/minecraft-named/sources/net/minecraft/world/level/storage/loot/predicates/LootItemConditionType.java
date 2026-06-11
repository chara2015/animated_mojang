package net.minecraft.world.level.storage.loot.predicates;

import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/LootItemConditionType.class */
public final class LootItemConditionType extends Record {
    private final MapCodec<? extends LootItemCondition> codec;

    public LootItemConditionType(MapCodec<? extends LootItemCondition> $$0) {
        this.codec = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LootItemConditionType.class), LootItemConditionType.class, "codec", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/LootItemConditionType;->codec:Lcom/mojang/serialization/MapCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LootItemConditionType.class), LootItemConditionType.class, "codec", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/LootItemConditionType;->codec:Lcom/mojang/serialization/MapCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LootItemConditionType.class, Object.class), LootItemConditionType.class, "codec", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/LootItemConditionType;->codec:Lcom/mojang/serialization/MapCodec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public MapCodec<? extends LootItemCondition> codec() {
        return this.codec;
    }
}

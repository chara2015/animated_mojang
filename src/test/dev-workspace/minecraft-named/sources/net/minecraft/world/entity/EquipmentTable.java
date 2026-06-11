package net.minecraft.world.entity;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/EquipmentTable.class */
public final class EquipmentTable extends Record {
    private final ResourceKey<LootTable> lootTable;
    private final Map<EquipmentSlot, Float> slotDropChances;
    public static final Codec<Map<EquipmentSlot, Float>> DROP_CHANCES_CODEC = Codec.either(Codec.FLOAT, Codec.unboundedMap(EquipmentSlot.CODEC, Codec.FLOAT)).xmap($$0 -> {
        return (Map) $$0.map((v0) -> {
            return createForAllSlots(v0);
        }, Function.identity());
    }, $$02 -> {
        boolean $$1 = $$02.values().stream().distinct().count() == 1;
        boolean $$2 = $$02.keySet().containsAll(EquipmentSlot.VALUES);
        if ($$1 && $$2) {
            return Either.left((Float) $$02.values().stream().findFirst().orElse(Float.valueOf(0.0f)));
        }
        return Either.right($$02);
    });
    public static final Codec<EquipmentTable> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(LootTable.KEY_CODEC.fieldOf("loot_table").forGetter((v0) -> {
            return v0.lootTable();
        }), DROP_CHANCES_CODEC.optionalFieldOf("slot_drop_chances", Map.of()).forGetter((v0) -> {
            return v0.slotDropChances();
        })).apply($$0, EquipmentTable::new);
    });

    public EquipmentTable(ResourceKey<LootTable> $$0, Map<EquipmentSlot, Float> $$1) {
        this.lootTable = $$0;
        this.slotDropChances = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EquipmentTable.class), EquipmentTable.class, "lootTable;slotDropChances", "FIELD:Lnet/minecraft/world/entity/EquipmentTable;->lootTable:Lnet/minecraft/resources/ResourceKey;", "FIELD:Lnet/minecraft/world/entity/EquipmentTable;->slotDropChances:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EquipmentTable.class), EquipmentTable.class, "lootTable;slotDropChances", "FIELD:Lnet/minecraft/world/entity/EquipmentTable;->lootTable:Lnet/minecraft/resources/ResourceKey;", "FIELD:Lnet/minecraft/world/entity/EquipmentTable;->slotDropChances:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EquipmentTable.class, Object.class), EquipmentTable.class, "lootTable;slotDropChances", "FIELD:Lnet/minecraft/world/entity/EquipmentTable;->lootTable:Lnet/minecraft/resources/ResourceKey;", "FIELD:Lnet/minecraft/world/entity/EquipmentTable;->slotDropChances:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ResourceKey<LootTable> lootTable() {
        return this.lootTable;
    }

    public Map<EquipmentSlot, Float> slotDropChances() {
        return this.slotDropChances;
    }

    public EquipmentTable(ResourceKey<LootTable> $$0, float $$1) {
        this($$0, createForAllSlots($$1));
    }

    private static Map<EquipmentSlot, Float> createForAllSlots(float $$0) {
        return createForAllSlots(List.of((Object[]) EquipmentSlot.values()), $$0);
    }

    private static Map<EquipmentSlot, Float> createForAllSlots(List<EquipmentSlot> $$0, float $$1) {
        Map<EquipmentSlot, Float> $$2 = Maps.newHashMap();
        for (EquipmentSlot $$3 : $$0) {
            $$2.put($$3, Float.valueOf($$1));
        }
        return $$2;
    }
}

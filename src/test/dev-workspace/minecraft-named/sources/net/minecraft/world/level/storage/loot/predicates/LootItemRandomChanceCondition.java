package net.minecraft.world.level.storage.loot.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceCondition.class */
public final class LootItemRandomChanceCondition extends Record implements LootItemCondition {
    private final NumberProvider chance;
    public static final MapCodec<LootItemRandomChanceCondition> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(NumberProviders.CODEC.fieldOf("chance").forGetter((v0) -> {
            return v0.chance();
        })).apply($$0, LootItemRandomChanceCondition::new);
    });

    public LootItemRandomChanceCondition(NumberProvider $$0) {
        this.chance = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LootItemRandomChanceCondition.class), LootItemRandomChanceCondition.class, "chance", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceCondition;->chance:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LootItemRandomChanceCondition.class), LootItemRandomChanceCondition.class, "chance", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceCondition;->chance:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LootItemRandomChanceCondition.class, Object.class), LootItemRandomChanceCondition.class, "chance", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceCondition;->chance:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public NumberProvider chance() {
        return this.chance;
    }

    @Override // net.minecraft.world.level.storage.loot.predicates.LootItemCondition
    public LootItemConditionType getType() {
        return LootItemConditions.RANDOM_CHANCE;
    }

    @Override // java.util.function.Predicate
    public boolean test(LootContext $$0) {
        float $$1 = this.chance.getFloat($$0);
        return $$0.getRandom().nextFloat() < $$1;
    }

    public static LootItemCondition.Builder randomChance(float $$0) {
        return () -> {
            return new LootItemRandomChanceCondition(ConstantValue.exactly($$0));
        };
    }

    public static LootItemCondition.Builder randomChance(NumberProvider $$0) {
        return () -> {
            return new LootItemRandomChanceCondition($$0);
        };
    }
}

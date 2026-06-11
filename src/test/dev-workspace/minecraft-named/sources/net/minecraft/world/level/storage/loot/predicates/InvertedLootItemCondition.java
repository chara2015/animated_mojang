package net.minecraft.world.level.storage.loot.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Set;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/InvertedLootItemCondition.class */
public final class InvertedLootItemCondition extends Record implements LootItemCondition {
    private final LootItemCondition term;
    public static final MapCodec<InvertedLootItemCondition> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(LootItemCondition.DIRECT_CODEC.fieldOf("term").forGetter((v0) -> {
            return v0.term();
        })).apply($$0, InvertedLootItemCondition::new);
    });

    public InvertedLootItemCondition(LootItemCondition $$0) {
        this.term = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, InvertedLootItemCondition.class), InvertedLootItemCondition.class, "term", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/InvertedLootItemCondition;->term:Lnet/minecraft/world/level/storage/loot/predicates/LootItemCondition;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, InvertedLootItemCondition.class), InvertedLootItemCondition.class, "term", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/InvertedLootItemCondition;->term:Lnet/minecraft/world/level/storage/loot/predicates/LootItemCondition;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, InvertedLootItemCondition.class, Object.class), InvertedLootItemCondition.class, "term", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/InvertedLootItemCondition;->term:Lnet/minecraft/world/level/storage/loot/predicates/LootItemCondition;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public LootItemCondition term() {
        return this.term;
    }

    @Override // net.minecraft.world.level.storage.loot.predicates.LootItemCondition
    public LootItemConditionType getType() {
        return LootItemConditions.INVERTED;
    }

    @Override // java.util.function.Predicate
    public boolean test(LootContext $$0) {
        return !this.term.test($$0);
    }

    @Override // net.minecraft.world.level.storage.loot.LootContextUser
    public Set<ContextKey<?>> getReferencedContextParams() {
        return this.term.getReferencedContextParams();
    }

    @Override // net.minecraft.world.level.storage.loot.LootContextUser
    public void validate(ValidationContext $$0) {
        super.validate($$0);
        this.term.validate($$0);
    }

    public static LootItemCondition.Builder invert(LootItemCondition.Builder $$0) {
        InvertedLootItemCondition $$1 = new InvertedLootItemCondition($$0.build());
        return () -> {
            return $$1;
        };
    }
}

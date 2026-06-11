package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Set;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/ValueCheckCondition.class */
public final class ValueCheckCondition extends Record implements LootItemCondition {
    private final NumberProvider provider;
    private final IntRange range;
    public static final MapCodec<ValueCheckCondition> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(NumberProviders.CODEC.fieldOf("value").forGetter((v0) -> {
            return v0.provider();
        }), IntRange.CODEC.fieldOf("range").forGetter((v0) -> {
            return v0.range();
        })).apply($$0, ValueCheckCondition::new);
    });

    public ValueCheckCondition(NumberProvider $$0, IntRange $$1) {
        this.provider = $$0;
        this.range = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ValueCheckCondition.class), ValueCheckCondition.class, "provider;range", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/ValueCheckCondition;->provider:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/ValueCheckCondition;->range:Lnet/minecraft/world/level/storage/loot/IntRange;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ValueCheckCondition.class), ValueCheckCondition.class, "provider;range", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/ValueCheckCondition;->provider:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/ValueCheckCondition;->range:Lnet/minecraft/world/level/storage/loot/IntRange;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ValueCheckCondition.class, Object.class), ValueCheckCondition.class, "provider;range", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/ValueCheckCondition;->provider:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;", "FIELD:Lnet/minecraft/world/level/storage/loot/predicates/ValueCheckCondition;->range:Lnet/minecraft/world/level/storage/loot/IntRange;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public NumberProvider provider() {
        return this.provider;
    }

    public IntRange range() {
        return this.range;
    }

    @Override // net.minecraft.world.level.storage.loot.predicates.LootItemCondition
    public LootItemConditionType getType() {
        return LootItemConditions.VALUE_CHECK;
    }

    @Override // net.minecraft.world.level.storage.loot.LootContextUser
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Sets.union(this.provider.getReferencedContextParams(), this.range.getReferencedContextParams());
    }

    @Override // java.util.function.Predicate
    public boolean test(LootContext $$0) {
        return this.range.test($$0, this.provider.getInt($$0));
    }

    public static LootItemCondition.Builder hasValue(NumberProvider $$0, IntRange $$1) {
        return () -> {
            return new ValueCheckCondition($$0, $$1);
        };
    }
}

package net.minecraft.world.item.enchantment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/ConditionalEffect.class */
public final class ConditionalEffect<T> extends Record {
    private final T effect;
    private final Optional<LootItemCondition> requirements;

    public ConditionalEffect(T $$0, Optional<LootItemCondition> $$1) {
        this.effect = $$0;
        this.requirements = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ConditionalEffect.class), ConditionalEffect.class, "effect;requirements", "FIELD:Lnet/minecraft/world/item/enchantment/ConditionalEffect;->effect:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/item/enchantment/ConditionalEffect;->requirements:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ConditionalEffect.class), ConditionalEffect.class, "effect;requirements", "FIELD:Lnet/minecraft/world/item/enchantment/ConditionalEffect;->effect:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/item/enchantment/ConditionalEffect;->requirements:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ConditionalEffect.class, Object.class), ConditionalEffect.class, "effect;requirements", "FIELD:Lnet/minecraft/world/item/enchantment/ConditionalEffect;->effect:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/item/enchantment/ConditionalEffect;->requirements:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public T effect() {
        return this.effect;
    }

    public Optional<LootItemCondition> requirements() {
        return this.requirements;
    }

    public static Codec<LootItemCondition> conditionCodec(ContextKeySet $$0) {
        return LootItemCondition.DIRECT_CODEC.validate($$1 -> {
            ProblemReporter.Collector $$2 = new ProblemReporter.Collector();
            ValidationContext $$3 = new ValidationContext($$2, $$0);
            $$1.validate($$3);
            if (!$$2.isEmpty()) {
                return DataResult.error(() -> {
                    return "Validation error in enchantment effect condition: " + $$2.getReport();
                });
            }
            return DataResult.success($$1);
        });
    }

    public static <T> Codec<ConditionalEffect<T>> codec(Codec<T> $$0, ContextKeySet $$1) {
        return RecordCodecBuilder.create($$2 -> {
            return $$2.group($$0.fieldOf("effect").forGetter((v0) -> {
                return v0.effect();
            }), conditionCodec($$1).optionalFieldOf("requirements").forGetter((v0) -> {
                return v0.requirements();
            })).apply($$2, ConditionalEffect::new);
        });
    }

    public boolean matches(LootContext $$0) {
        if (this.requirements.isEmpty()) {
            return true;
        }
        return this.requirements.get().test($$0);
    }
}

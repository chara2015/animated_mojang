package net.minecraft.world.item.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.LevelBasedValue;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/effects/MultiplyValue.class */
public final class MultiplyValue extends Record implements EnchantmentValueEffect {
    private final LevelBasedValue factor;
    public static final MapCodec<MultiplyValue> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(LevelBasedValue.CODEC.fieldOf("factor").forGetter((v0) -> {
            return v0.factor();
        })).apply($$0, MultiplyValue::new);
    });

    public MultiplyValue(LevelBasedValue $$0) {
        this.factor = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MultiplyValue.class), MultiplyValue.class, "factor", "FIELD:Lnet/minecraft/world/item/enchantment/effects/MultiplyValue;->factor:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MultiplyValue.class), MultiplyValue.class, "factor", "FIELD:Lnet/minecraft/world/item/enchantment/effects/MultiplyValue;->factor:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MultiplyValue.class, Object.class), MultiplyValue.class, "factor", "FIELD:Lnet/minecraft/world/item/enchantment/effects/MultiplyValue;->factor:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public LevelBasedValue factor() {
        return this.factor;
    }

    @Override // net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect
    public float process(int $$0, RandomSource $$1, float $$2) {
        return $$2 * this.factor.calculate($$0);
    }

    @Override // net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect
    public MapCodec<MultiplyValue> codec() {
        return CODEC;
    }
}

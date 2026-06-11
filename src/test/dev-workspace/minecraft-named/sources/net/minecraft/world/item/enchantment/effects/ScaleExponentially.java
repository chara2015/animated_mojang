package net.minecraft.world.item.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.LevelBasedValue;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/effects/ScaleExponentially.class */
public final class ScaleExponentially extends Record implements EnchantmentValueEffect {
    private final LevelBasedValue base;
    private final LevelBasedValue exponent;
    public static final MapCodec<ScaleExponentially> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(LevelBasedValue.CODEC.fieldOf("base").forGetter((v0) -> {
            return v0.base();
        }), LevelBasedValue.CODEC.fieldOf("exponent").forGetter((v0) -> {
            return v0.exponent();
        })).apply($$0, ScaleExponentially::new);
    });

    public ScaleExponentially(LevelBasedValue $$0, LevelBasedValue $$1) {
        this.base = $$0;
        this.exponent = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ScaleExponentially.class), ScaleExponentially.class, "base;exponent", "FIELD:Lnet/minecraft/world/item/enchantment/effects/ScaleExponentially;->base:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/effects/ScaleExponentially;->exponent:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ScaleExponentially.class), ScaleExponentially.class, "base;exponent", "FIELD:Lnet/minecraft/world/item/enchantment/effects/ScaleExponentially;->base:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/effects/ScaleExponentially;->exponent:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ScaleExponentially.class, Object.class), ScaleExponentially.class, "base;exponent", "FIELD:Lnet/minecraft/world/item/enchantment/effects/ScaleExponentially;->base:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/effects/ScaleExponentially;->exponent:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public LevelBasedValue base() {
        return this.base;
    }

    public LevelBasedValue exponent() {
        return this.exponent;
    }

    @Override // net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect
    public float process(int $$0, RandomSource $$1, float $$2) {
        return (float) (((double) $$2) * Math.pow(this.base.calculate($$0), this.exponent.calculate($$0)));
    }

    @Override // net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect
    public MapCodec<ScaleExponentially> codec() {
        return CODEC;
    }
}

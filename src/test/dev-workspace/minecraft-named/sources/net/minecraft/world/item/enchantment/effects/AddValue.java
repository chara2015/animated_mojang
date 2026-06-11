package net.minecraft.world.item.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.LevelBasedValue;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/effects/AddValue.class */
public final class AddValue extends Record implements EnchantmentValueEffect {
    private final LevelBasedValue value;
    public static final MapCodec<AddValue> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(LevelBasedValue.CODEC.fieldOf("value").forGetter((v0) -> {
            return v0.value();
        })).apply($$0, AddValue::new);
    });

    public AddValue(LevelBasedValue $$0) {
        this.value = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AddValue.class), AddValue.class, "value", "FIELD:Lnet/minecraft/world/item/enchantment/effects/AddValue;->value:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AddValue.class), AddValue.class, "value", "FIELD:Lnet/minecraft/world/item/enchantment/effects/AddValue;->value:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AddValue.class, Object.class), AddValue.class, "value", "FIELD:Lnet/minecraft/world/item/enchantment/effects/AddValue;->value:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public LevelBasedValue value() {
        return this.value;
    }

    @Override // net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect
    public float process(int $$0, RandomSource $$1, float $$2) {
        return $$2 + this.value.calculate($$0);
    }

    @Override // net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect
    public MapCodec<AddValue> codec() {
        return CODEC;
    }
}

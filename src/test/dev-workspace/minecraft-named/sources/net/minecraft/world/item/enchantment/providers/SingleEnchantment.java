package net.minecraft.world.item.enchantment.providers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/providers/SingleEnchantment.class */
public final class SingleEnchantment extends Record implements EnchantmentProvider {
    private final Holder<Enchantment> enchantment;
    private final IntProvider level;
    public static final MapCodec<SingleEnchantment> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Enchantment.CODEC.fieldOf("enchantment").forGetter((v0) -> {
            return v0.enchantment();
        }), IntProvider.CODEC.fieldOf("level").forGetter((v0) -> {
            return v0.level();
        })).apply($$0, SingleEnchantment::new);
    });

    public SingleEnchantment(Holder<Enchantment> $$0, IntProvider $$1) {
        this.enchantment = $$0;
        this.level = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SingleEnchantment.class), SingleEnchantment.class, "enchantment;level", "FIELD:Lnet/minecraft/world/item/enchantment/providers/SingleEnchantment;->enchantment:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/item/enchantment/providers/SingleEnchantment;->level:Lnet/minecraft/util/valueproviders/IntProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SingleEnchantment.class), SingleEnchantment.class, "enchantment;level", "FIELD:Lnet/minecraft/world/item/enchantment/providers/SingleEnchantment;->enchantment:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/item/enchantment/providers/SingleEnchantment;->level:Lnet/minecraft/util/valueproviders/IntProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SingleEnchantment.class, Object.class), SingleEnchantment.class, "enchantment;level", "FIELD:Lnet/minecraft/world/item/enchantment/providers/SingleEnchantment;->enchantment:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/item/enchantment/providers/SingleEnchantment;->level:Lnet/minecraft/util/valueproviders/IntProvider;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Holder<Enchantment> enchantment() {
        return this.enchantment;
    }

    public IntProvider level() {
        return this.level;
    }

    @Override // net.minecraft.world.item.enchantment.providers.EnchantmentProvider
    public void enchant(ItemStack $$0, ItemEnchantments.Mutable $$1, RandomSource $$2, DifficultyInstance $$3) {
        $$1.upgrade(this.enchantment, Mth.clamp(this.level.sample($$2), this.enchantment.value().getMinLevel(), this.enchantment.value().getMaxLevel()));
    }

    @Override // net.minecraft.world.item.enchantment.providers.EnchantmentProvider
    public MapCodec<SingleEnchantment> codec() {
        return CODEC;
    }
}

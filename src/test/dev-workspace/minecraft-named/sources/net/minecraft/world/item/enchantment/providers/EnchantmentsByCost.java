package net.minecraft.world.item.enchantment.providers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/providers/EnchantmentsByCost.class */
public final class EnchantmentsByCost extends Record implements EnchantmentProvider {
    private final HolderSet<Enchantment> enchantments;
    private final IntProvider cost;
    public static final MapCodec<EnchantmentsByCost> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(RegistryCodecs.homogeneousList(Registries.ENCHANTMENT).fieldOf("enchantments").forGetter((v0) -> {
            return v0.enchantments();
        }), IntProvider.CODEC.fieldOf("cost").forGetter((v0) -> {
            return v0.cost();
        })).apply($$0, EnchantmentsByCost::new);
    });

    public EnchantmentsByCost(HolderSet<Enchantment> $$0, IntProvider $$1) {
        this.enchantments = $$0;
        this.cost = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EnchantmentsByCost.class), EnchantmentsByCost.class, "enchantments;cost", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCost;->enchantments:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCost;->cost:Lnet/minecraft/util/valueproviders/IntProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EnchantmentsByCost.class), EnchantmentsByCost.class, "enchantments;cost", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCost;->enchantments:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCost;->cost:Lnet/minecraft/util/valueproviders/IntProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EnchantmentsByCost.class, Object.class), EnchantmentsByCost.class, "enchantments;cost", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCost;->enchantments:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCost;->cost:Lnet/minecraft/util/valueproviders/IntProvider;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public HolderSet<Enchantment> enchantments() {
        return this.enchantments;
    }

    public IntProvider cost() {
        return this.cost;
    }

    @Override // net.minecraft.world.item.enchantment.providers.EnchantmentProvider
    public void enchant(ItemStack $$0, ItemEnchantments.Mutable $$1, RandomSource $$2, DifficultyInstance $$3) {
        List<EnchantmentInstance> $$4 = EnchantmentHelper.selectEnchantment($$2, $$0, this.cost.sample($$2), this.enchantments.stream());
        for (EnchantmentInstance $$5 : $$4) {
            $$1.upgrade($$5.enchantment(), $$5.level());
        }
    }

    @Override // net.minecraft.world.item.enchantment.providers.EnchantmentProvider
    public MapCodec<EnchantmentsByCost> codec() {
        return CODEC;
    }
}

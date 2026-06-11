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
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/providers/EnchantmentsByCostWithDifficulty.class */
public final class EnchantmentsByCostWithDifficulty extends Record implements EnchantmentProvider {
    private final HolderSet<Enchantment> enchantments;
    private final int minCost;
    private final int maxCostSpan;
    public static final int MAX_ALLOWED_VALUE_PART = 10000;
    public static final MapCodec<EnchantmentsByCostWithDifficulty> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(RegistryCodecs.homogeneousList(Registries.ENCHANTMENT).fieldOf("enchantments").forGetter((v0) -> {
            return v0.enchantments();
        }), ExtraCodecs.intRange(1, 10000).fieldOf("min_cost").forGetter((v0) -> {
            return v0.minCost();
        }), ExtraCodecs.intRange(0, 10000).fieldOf("max_cost_span").forGetter((v0) -> {
            return v0.maxCostSpan();
        })).apply($$0, (v1, v2, v3) -> {
            return new EnchantmentsByCostWithDifficulty(v1, v2, v3);
        });
    });

    public EnchantmentsByCostWithDifficulty(HolderSet<Enchantment> $$0, int $$1, int $$2) {
        this.enchantments = $$0;
        this.minCost = $$1;
        this.maxCostSpan = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EnchantmentsByCostWithDifficulty.class), EnchantmentsByCostWithDifficulty.class, "enchantments;minCost;maxCostSpan", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCostWithDifficulty;->enchantments:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCostWithDifficulty;->minCost:I", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCostWithDifficulty;->maxCostSpan:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EnchantmentsByCostWithDifficulty.class), EnchantmentsByCostWithDifficulty.class, "enchantments;minCost;maxCostSpan", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCostWithDifficulty;->enchantments:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCostWithDifficulty;->minCost:I", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCostWithDifficulty;->maxCostSpan:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EnchantmentsByCostWithDifficulty.class, Object.class), EnchantmentsByCostWithDifficulty.class, "enchantments;minCost;maxCostSpan", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCostWithDifficulty;->enchantments:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCostWithDifficulty;->minCost:I", "FIELD:Lnet/minecraft/world/item/enchantment/providers/EnchantmentsByCostWithDifficulty;->maxCostSpan:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public HolderSet<Enchantment> enchantments() {
        return this.enchantments;
    }

    public int minCost() {
        return this.minCost;
    }

    public int maxCostSpan() {
        return this.maxCostSpan;
    }

    @Override // net.minecraft.world.item.enchantment.providers.EnchantmentProvider
    public void enchant(ItemStack $$0, ItemEnchantments.Mutable $$1, RandomSource $$2, DifficultyInstance $$3) {
        float $$4 = $$3.getSpecialMultiplier();
        int $$5 = Mth.randomBetweenInclusive($$2, this.minCost, this.minCost + ((int) ($$4 * this.maxCostSpan)));
        List<EnchantmentInstance> $$6 = EnchantmentHelper.selectEnchantment($$2, $$0, $$5, this.enchantments.stream());
        for (EnchantmentInstance $$7 : $$6) {
            $$1.upgrade($$7.enchantment(), $$7.level());
        }
    }

    @Override // net.minecraft.world.item.enchantment.providers.EnchantmentProvider
    public MapCodec<EnchantmentsByCostWithDifficulty> codec() {
        return CODEC;
    }
}

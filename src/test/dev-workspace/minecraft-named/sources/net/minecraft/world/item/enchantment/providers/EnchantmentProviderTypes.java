package net.minecraft.world.item.enchantment.providers;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/providers/EnchantmentProviderTypes.class */
public interface EnchantmentProviderTypes {
    static MapCodec<? extends EnchantmentProvider> bootstrap(Registry<MapCodec<? extends EnchantmentProvider>> $$0) {
        Registry.register((Registry<? super MapCodec<EnchantmentsByCost>>) $$0, "by_cost", EnchantmentsByCost.CODEC);
        Registry.register((Registry<? super MapCodec<EnchantmentsByCostWithDifficulty>>) $$0, "by_cost_with_difficulty", EnchantmentsByCostWithDifficulty.CODEC);
        return (MapCodec) Registry.register((Registry<? super MapCodec<SingleEnchantment>>) $$0, "single", SingleEnchantment.CODEC);
    }
}

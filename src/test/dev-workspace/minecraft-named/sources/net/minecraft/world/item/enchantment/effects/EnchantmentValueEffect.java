package net.minecraft.world.item.enchantment.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.effects.AllOf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/effects/EnchantmentValueEffect.class */
public interface EnchantmentValueEffect {
    public static final Codec<EnchantmentValueEffect> CODEC = BuiltInRegistries.ENCHANTMENT_VALUE_EFFECT_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.codec();
    }, Function.identity());

    float process(int i, RandomSource randomSource, float f);

    MapCodec<? extends EnchantmentValueEffect> codec();

    static MapCodec<? extends EnchantmentValueEffect> bootstrap(Registry<MapCodec<? extends EnchantmentValueEffect>> $$0) {
        Registry.register((Registry<? super MapCodec<AddValue>>) $$0, "add", AddValue.CODEC);
        Registry.register((Registry<? super MapCodec<AllOf.ValueEffects>>) $$0, "all_of", AllOf.ValueEffects.CODEC);
        Registry.register((Registry<? super MapCodec<MultiplyValue>>) $$0, "multiply", MultiplyValue.CODEC);
        Registry.register((Registry<? super MapCodec<RemoveBinomial>>) $$0, "remove_binomial", RemoveBinomial.CODEC);
        Registry.register((Registry<? super MapCodec<ScaleExponentially>>) $$0, "exponential", ScaleExponentially.CODEC);
        return (MapCodec) Registry.register((Registry<? super MapCodec<SetValue>>) $$0, "set", SetValue.CODEC);
    }
}

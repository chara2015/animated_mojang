package net.minecraft.world.level.storage.loot.providers.number;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/providers/number/NumberProviders.class */
public class NumberProviders {
    private static final Codec<NumberProvider> TYPED_CODEC = BuiltInRegistries.LOOT_NUMBER_PROVIDER_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.getType();
    }, (v0) -> {
        return v0.codec();
    });
    public static final Codec<NumberProvider> CODEC = Codec.lazyInitialized(() -> {
        Codec<NumberProvider> $$0 = Codec.withAlternative(TYPED_CODEC, UniformGenerator.CODEC.codec());
        return Codec.either(ConstantValue.INLINE_CODEC, $$0).xmap(Either::unwrap, $$02 -> {
            if (!($$02 instanceof ConstantValue)) {
                return Either.right($$02);
            }
            ConstantValue $$1 = (ConstantValue) $$02;
            return Either.left($$1);
        });
    });
    public static final LootNumberProviderType CONSTANT = register("constant", ConstantValue.CODEC);
    public static final LootNumberProviderType UNIFORM = register("uniform", UniformGenerator.CODEC);
    public static final LootNumberProviderType BINOMIAL = register("binomial", BinomialDistributionGenerator.CODEC);
    public static final LootNumberProviderType SCORE = register("score", ScoreboardValue.CODEC);
    public static final LootNumberProviderType STORAGE = register("storage", StorageValue.CODEC);
    public static final LootNumberProviderType ENCHANTMENT_LEVEL = register("enchantment_level", EnchantmentLevelProvider.CODEC);

    private static LootNumberProviderType register(String $$0, MapCodec<? extends NumberProvider> $$1) {
        return (LootNumberProviderType) Registry.register(BuiltInRegistries.LOOT_NUMBER_PROVIDER_TYPE, Identifier.withDefaultNamespace($$0), new LootNumberProviderType($$1));
    }
}

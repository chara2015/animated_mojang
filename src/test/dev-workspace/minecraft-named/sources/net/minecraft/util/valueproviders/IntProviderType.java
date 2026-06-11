package net.minecraft.util.valueproviders;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.IntProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/valueproviders/IntProviderType.class */
public interface IntProviderType<P extends IntProvider> {
    public static final IntProviderType<ConstantInt> CONSTANT = register("constant", ConstantInt.CODEC);
    public static final IntProviderType<UniformInt> UNIFORM = register("uniform", UniformInt.CODEC);
    public static final IntProviderType<BiasedToBottomInt> BIASED_TO_BOTTOM = register("biased_to_bottom", BiasedToBottomInt.CODEC);
    public static final IntProviderType<ClampedInt> CLAMPED = register("clamped", ClampedInt.CODEC);
    public static final IntProviderType<WeightedListInt> WEIGHTED_LIST = register("weighted_list", WeightedListInt.CODEC);
    public static final IntProviderType<ClampedNormalInt> CLAMPED_NORMAL = register("clamped_normal", ClampedNormalInt.CODEC);

    MapCodec<P> codec();

    static <P extends IntProvider> IntProviderType<P> register(String $$0, MapCodec<P> $$1) {
        return (IntProviderType) Registry.register(BuiltInRegistries.INT_PROVIDER_TYPE, $$0, () -> {
            return $$1;
        });
    }
}

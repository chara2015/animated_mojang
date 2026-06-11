package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/heightproviders/HeightProviderType.class */
public interface HeightProviderType<P extends HeightProvider> {
    public static final HeightProviderType<ConstantHeight> CONSTANT = register("constant", ConstantHeight.CODEC);
    public static final HeightProviderType<UniformHeight> UNIFORM = register("uniform", UniformHeight.CODEC);
    public static final HeightProviderType<BiasedToBottomHeight> BIASED_TO_BOTTOM = register("biased_to_bottom", BiasedToBottomHeight.CODEC);
    public static final HeightProviderType<VeryBiasedToBottomHeight> VERY_BIASED_TO_BOTTOM = register("very_biased_to_bottom", VeryBiasedToBottomHeight.CODEC);
    public static final HeightProviderType<TrapezoidHeight> TRAPEZOID = register("trapezoid", TrapezoidHeight.CODEC);
    public static final HeightProviderType<WeightedListHeight> WEIGHTED_LIST = register("weighted_list", WeightedListHeight.CODEC);

    MapCodec<P> codec();

    private static <P extends HeightProvider> HeightProviderType<P> register(String $$0, MapCodec<P> $$1) {
        return (HeightProviderType) Registry.register(BuiltInRegistries.HEIGHT_PROVIDER_TYPE, $$0, () -> {
            return $$1;
        });
    }
}

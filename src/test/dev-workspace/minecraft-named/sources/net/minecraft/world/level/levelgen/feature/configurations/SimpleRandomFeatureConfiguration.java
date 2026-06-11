package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import java.util.stream.Stream;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/SimpleRandomFeatureConfiguration.class */
public class SimpleRandomFeatureConfiguration implements FeatureConfiguration {
    public static final Codec<SimpleRandomFeatureConfiguration> CODEC = ExtraCodecs.nonEmptyHolderSet(PlacedFeature.LIST_CODEC).fieldOf("features").xmap(SimpleRandomFeatureConfiguration::new, $$0 -> {
        return $$0.features;
    }).codec();
    public final HolderSet<PlacedFeature> features;

    public SimpleRandomFeatureConfiguration(HolderSet<PlacedFeature> $$0) {
        this.features = $$0;
    }

    @Override // net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
    public Stream<ConfiguredFeature<?, ?>> getFeatures() {
        return this.features.stream().flatMap($$0 -> {
            return ((PlacedFeature) $$0.value()).getFeatures();
        });
    }
}

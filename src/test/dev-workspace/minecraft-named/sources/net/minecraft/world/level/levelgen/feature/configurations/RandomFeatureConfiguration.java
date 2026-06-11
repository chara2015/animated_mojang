package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/RandomFeatureConfiguration.class */
public class RandomFeatureConfiguration implements FeatureConfiguration {
    public static final Codec<RandomFeatureConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.apply2(RandomFeatureConfiguration::new, WeightedPlacedFeature.CODEC.listOf().fieldOf("features").forGetter($$0 -> {
            return $$0.features;
        }), PlacedFeature.CODEC.fieldOf(GameTestEnvironments.DEFAULT).forGetter($$02 -> {
            return $$02.defaultFeature;
        }));
    });
    public final List<WeightedPlacedFeature> features;
    public final Holder<PlacedFeature> defaultFeature;

    public RandomFeatureConfiguration(List<WeightedPlacedFeature> $$0, Holder<PlacedFeature> $$1) {
        this.features = $$0;
        this.defaultFeature = $$1;
    }

    @Override // net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
    public Stream<ConfiguredFeature<?, ?>> getFeatures() {
        return Stream.concat(this.features.stream().flatMap($$0 -> {
            return $$0.feature.value().getFeatures();
        }), this.defaultFeature.value().getFeatures());
    }
}

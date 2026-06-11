package net.minecraft.world.level.levelgen.feature.configurations;

import java.util.stream.Stream;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/FeatureConfiguration.class */
public interface FeatureConfiguration {
    public static final NoneFeatureConfiguration NONE = NoneFeatureConfiguration.INSTANCE;

    default Stream<ConfiguredFeature<?, ?>> getFeatures() {
        return Stream.empty();
    }
}

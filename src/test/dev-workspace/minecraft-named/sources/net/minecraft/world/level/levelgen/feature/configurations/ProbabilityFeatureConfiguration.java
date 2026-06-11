package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/ProbabilityFeatureConfiguration.class */
public class ProbabilityFeatureConfiguration implements FeatureConfiguration {
    public static final Codec<ProbabilityFeatureConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.floatRange(0.0f, 1.0f).fieldOf("probability").forGetter($$0 -> {
            return Float.valueOf($$0.probability);
        })).apply($$0, (v1) -> {
            return new ProbabilityFeatureConfiguration(v1);
        });
    });
    public final float probability;

    public ProbabilityFeatureConfiguration(float $$0) {
        this.probability = $$0;
    }
}

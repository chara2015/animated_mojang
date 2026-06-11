package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/UnderwaterMagmaConfiguration.class */
public class UnderwaterMagmaConfiguration implements FeatureConfiguration {
    public static final Codec<UnderwaterMagmaConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.intRange(0, 512).fieldOf("floor_search_range").forGetter($$0 -> {
            return Integer.valueOf($$0.floorSearchRange);
        }), Codec.intRange(0, 64).fieldOf("placement_radius_around_floor").forGetter($$02 -> {
            return Integer.valueOf($$02.placementRadiusAroundFloor);
        }), Codec.floatRange(0.0f, 1.0f).fieldOf("placement_probability_per_valid_position").forGetter($$03 -> {
            return Float.valueOf($$03.placementProbabilityPerValidPosition);
        })).apply($$0, (v1, v2, v3) -> {
            return new UnderwaterMagmaConfiguration(v1, v2, v3);
        });
    });
    public final int floorSearchRange;
    public final int placementRadiusAroundFloor;
    public final float placementProbabilityPerValidPosition;

    public UnderwaterMagmaConfiguration(int $$0, int $$1, float $$2) {
        this.floorSearchRange = $$0;
        this.placementRadiusAroundFloor = $$1;
        this.placementProbabilityPerValidPosition = $$2;
    }
}

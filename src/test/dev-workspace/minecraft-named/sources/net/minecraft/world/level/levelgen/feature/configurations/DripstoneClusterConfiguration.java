package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/DripstoneClusterConfiguration.class */
public class DripstoneClusterConfiguration implements FeatureConfiguration {
    public static final Codec<DripstoneClusterConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").forGetter($$0 -> {
            return Integer.valueOf($$0.floorToCeilingSearchRange);
        }), IntProvider.codec(1, 128).fieldOf(Display.TAG_HEIGHT).forGetter($$02 -> {
            return $$02.height;
        }), IntProvider.codec(1, 128).fieldOf("radius").forGetter($$03 -> {
            return $$03.radius;
        }), Codec.intRange(0, 64).fieldOf("max_stalagmite_stalactite_height_diff").forGetter($$04 -> {
            return Integer.valueOf($$04.maxStalagmiteStalactiteHeightDiff);
        }), Codec.intRange(1, 64).fieldOf("height_deviation").forGetter($$05 -> {
            return Integer.valueOf($$05.heightDeviation);
        }), IntProvider.codec(0, 128).fieldOf("dripstone_block_layer_thickness").forGetter($$06 -> {
            return $$06.dripstoneBlockLayerThickness;
        }), FloatProvider.codec(0.0f, 2.0f).fieldOf("density").forGetter($$07 -> {
            return $$07.density;
        }), FloatProvider.codec(0.0f, 2.0f).fieldOf("wetness").forGetter($$08 -> {
            return $$08.wetness;
        }), Codec.floatRange(0.0f, 1.0f).fieldOf("chance_of_dripstone_column_at_max_distance_from_center").forGetter($$09 -> {
            return Float.valueOf($$09.chanceOfDripstoneColumnAtMaxDistanceFromCenter);
        }), Codec.intRange(1, 64).fieldOf("max_distance_from_edge_affecting_chance_of_dripstone_column").forGetter($$010 -> {
            return Integer.valueOf($$010.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn);
        }), Codec.intRange(1, 64).fieldOf("max_distance_from_center_affecting_height_bias").forGetter($$011 -> {
            return Integer.valueOf($$011.maxDistanceFromCenterAffectingHeightBias);
        })).apply($$0, (v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11) -> {
            return new DripstoneClusterConfiguration(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11);
        });
    });
    public final int floorToCeilingSearchRange;
    public final IntProvider height;
    public final IntProvider radius;
    public final int maxStalagmiteStalactiteHeightDiff;
    public final int heightDeviation;
    public final IntProvider dripstoneBlockLayerThickness;
    public final FloatProvider density;
    public final FloatProvider wetness;
    public final float chanceOfDripstoneColumnAtMaxDistanceFromCenter;
    public final int maxDistanceFromEdgeAffectingChanceOfDripstoneColumn;
    public final int maxDistanceFromCenterAffectingHeightBias;

    public DripstoneClusterConfiguration(int $$0, IntProvider $$1, IntProvider $$2, int $$3, int $$4, IntProvider $$5, FloatProvider $$6, FloatProvider $$7, float $$8, int $$9, int $$10) {
        this.floorToCeilingSearchRange = $$0;
        this.height = $$1;
        this.radius = $$2;
        this.maxStalagmiteStalactiteHeightDiff = $$3;
        this.heightDeviation = $$4;
        this.dripstoneBlockLayerThickness = $$5;
        this.density = $$6;
        this.wetness = $$7;
        this.chanceOfDripstoneColumnAtMaxDistanceFromCenter = $$8;
        this.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn = $$9;
        this.maxDistanceFromCenterAffectingHeightBias = $$10;
    }
}

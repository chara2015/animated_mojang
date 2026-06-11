package net.minecraft.data.worldgen;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/worldgen/NoiseData.class */
public class NoiseData {

    @Deprecated
    public static final NormalNoise.NoiseParameters DEFAULT_SHIFT = new NormalNoise.NoiseParameters(-3, 1.0d, 1.0d, 1.0d, Density.SURFACE);

    public static void bootstrap(BootstrapContext<NormalNoise.NoiseParameters> $$0) {
        registerBiomeNoises($$0, 0, Noises.TEMPERATURE, Noises.VEGETATION, Noises.CONTINENTALNESS, Noises.EROSION);
        registerBiomeNoises($$0, -2, Noises.TEMPERATURE_LARGE, Noises.VEGETATION_LARGE, Noises.CONTINENTALNESS_LARGE, Noises.EROSION_LARGE);
        register($$0, Noises.RIDGE, -7, 1.0d, 2.0d, 1.0d, Density.SURFACE, Density.SURFACE, Density.SURFACE);
        $$0.register(Noises.SHIFT, DEFAULT_SHIFT);
        register($$0, Noises.AQUIFER_BARRIER, -3, 1.0d, new double[0]);
        register($$0, Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS, -7, 1.0d, new double[0]);
        register($$0, Noises.AQUIFER_LAVA, -1, 1.0d, new double[0]);
        register($$0, Noises.AQUIFER_FLUID_LEVEL_SPREAD, -5, 1.0d, new double[0]);
        register($$0, Noises.PILLAR, -7, 1.0d, 1.0d);
        register($$0, Noises.PILLAR_RARENESS, -8, 1.0d, new double[0]);
        register($$0, Noises.PILLAR_THICKNESS, -8, 1.0d, new double[0]);
        register($$0, Noises.SPAGHETTI_2D, -7, 1.0d, new double[0]);
        register($$0, Noises.SPAGHETTI_2D_ELEVATION, -8, 1.0d, new double[0]);
        register($$0, Noises.SPAGHETTI_2D_MODULATOR, -11, 1.0d, new double[0]);
        register($$0, Noises.SPAGHETTI_2D_THICKNESS, -11, 1.0d, new double[0]);
        register($$0, Noises.SPAGHETTI_3D_1, -7, 1.0d, new double[0]);
        register($$0, Noises.SPAGHETTI_3D_2, -7, 1.0d, new double[0]);
        register($$0, Noises.SPAGHETTI_3D_RARITY, -11, 1.0d, new double[0]);
        register($$0, Noises.SPAGHETTI_3D_THICKNESS, -8, 1.0d, new double[0]);
        register($$0, Noises.SPAGHETTI_ROUGHNESS, -5, 1.0d, new double[0]);
        register($$0, Noises.SPAGHETTI_ROUGHNESS_MODULATOR, -8, 1.0d, new double[0]);
        register($$0, Noises.CAVE_ENTRANCE, -7, 0.4d, 0.5d, 1.0d);
        register($$0, Noises.CAVE_LAYER, -8, 1.0d, new double[0]);
        register($$0, Noises.CAVE_CHEESE, -8, 0.5d, 1.0d, 2.0d, 1.0d, 2.0d, 1.0d, Density.SURFACE, 2.0d, Density.SURFACE);
        register($$0, Noises.ORE_VEININESS, -8, 1.0d, new double[0]);
        register($$0, Noises.ORE_VEIN_A, -7, 1.0d, new double[0]);
        register($$0, Noises.ORE_VEIN_B, -7, 1.0d, new double[0]);
        register($$0, Noises.ORE_GAP, -5, 1.0d, new double[0]);
        register($$0, Noises.NOODLE, -8, 1.0d, new double[0]);
        register($$0, Noises.NOODLE_THICKNESS, -8, 1.0d, new double[0]);
        register($$0, Noises.NOODLE_RIDGE_A, -7, 1.0d, new double[0]);
        register($$0, Noises.NOODLE_RIDGE_B, -7, 1.0d, new double[0]);
        register($$0, Noises.JAGGED, -16, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d);
        register($$0, Noises.SURFACE, -6, 1.0d, 1.0d, 1.0d);
        register($$0, Noises.SURFACE_SECONDARY, -6, 1.0d, 1.0d, Density.SURFACE, 1.0d);
        register($$0, Noises.CLAY_BANDS_OFFSET, -8, 1.0d, new double[0]);
        register($$0, Noises.BADLANDS_PILLAR, -2, 1.0d, 1.0d, 1.0d, 1.0d);
        register($$0, Noises.BADLANDS_PILLAR_ROOF, -8, 1.0d, new double[0]);
        register($$0, Noises.BADLANDS_SURFACE, -6, 1.0d, 1.0d, 1.0d);
        register($$0, Noises.ICEBERG_PILLAR, -6, 1.0d, 1.0d, 1.0d, 1.0d);
        register($$0, Noises.ICEBERG_PILLAR_ROOF, -3, 1.0d, new double[0]);
        register($$0, Noises.ICEBERG_SURFACE, -6, 1.0d, 1.0d, 1.0d);
        register($$0, Noises.SWAMP, -2, 1.0d, new double[0]);
        register($$0, Noises.CALCITE, -9, 1.0d, 1.0d, 1.0d, 1.0d);
        register($$0, Noises.GRAVEL, -8, 1.0d, 1.0d, 1.0d, 1.0d);
        register($$0, Noises.POWDER_SNOW, -6, 1.0d, 1.0d, 1.0d, 1.0d);
        register($$0, Noises.PACKED_ICE, -7, 1.0d, 1.0d, 1.0d, 1.0d);
        register($$0, Noises.ICE, -4, 1.0d, 1.0d, 1.0d, 1.0d);
        register($$0, Noises.SOUL_SAND_LAYER, -8, 1.0d, 1.0d, 1.0d, 1.0d, Density.SURFACE, Density.SURFACE, Density.SURFACE, Density.SURFACE, 0.013333333333333334d);
        register($$0, Noises.GRAVEL_LAYER, -8, 1.0d, 1.0d, 1.0d, 1.0d, Density.SURFACE, Density.SURFACE, Density.SURFACE, Density.SURFACE, 0.013333333333333334d);
        register($$0, Noises.PATCH, -5, 1.0d, Density.SURFACE, Density.SURFACE, Density.SURFACE, Density.SURFACE, 0.013333333333333334d);
        register($$0, Noises.NETHERRACK, -3, 1.0d, Density.SURFACE, Density.SURFACE, 0.35d);
        register($$0, Noises.NETHER_WART, -3, 1.0d, Density.SURFACE, Density.SURFACE, 0.9d);
        register($$0, Noises.NETHER_STATE_SELECTOR, -4, 1.0d, new double[0]);
    }

    private static void registerBiomeNoises(BootstrapContext<NormalNoise.NoiseParameters> $$0, int $$1, ResourceKey<NormalNoise.NoiseParameters> $$2, ResourceKey<NormalNoise.NoiseParameters> $$3, ResourceKey<NormalNoise.NoiseParameters> $$4, ResourceKey<NormalNoise.NoiseParameters> $$5) {
        register($$0, $$2, (-10) + $$1, 1.5d, Density.SURFACE, 1.0d, Density.SURFACE, Density.SURFACE, Density.SURFACE);
        register($$0, $$3, (-8) + $$1, 1.0d, 1.0d, Density.SURFACE, Density.SURFACE, Density.SURFACE, Density.SURFACE);
        register($$0, $$4, (-9) + $$1, 1.0d, 1.0d, 2.0d, 2.0d, 2.0d, 1.0d, 1.0d, 1.0d, 1.0d);
        register($$0, $$5, (-9) + $$1, 1.0d, 1.0d, Density.SURFACE, 1.0d, 1.0d);
    }

    private static void register(BootstrapContext<NormalNoise.NoiseParameters> $$0, ResourceKey<NormalNoise.NoiseParameters> $$1, int $$2, double $$3, double... $$4) {
        $$0.register($$1, new NormalNoise.NoiseParameters($$2, $$3, $$4));
    }
}

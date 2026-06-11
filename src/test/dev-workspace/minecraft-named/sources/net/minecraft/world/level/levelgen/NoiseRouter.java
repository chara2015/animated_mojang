package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;
import net.minecraft.world.level.levelgen.DensityFunction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/NoiseRouter.class */
public final class NoiseRouter extends Record {
    private final DensityFunction barrierNoise;
    private final DensityFunction fluidLevelFloodednessNoise;
    private final DensityFunction fluidLevelSpreadNoise;
    private final DensityFunction lavaNoise;
    private final DensityFunction temperature;
    private final DensityFunction vegetation;
    private final DensityFunction continents;
    private final DensityFunction erosion;
    private final DensityFunction depth;
    private final DensityFunction ridges;
    private final DensityFunction preliminarySurfaceLevel;
    private final DensityFunction finalDensity;
    private final DensityFunction veinToggle;
    private final DensityFunction veinRidged;
    private final DensityFunction veinGap;
    public static final Codec<NoiseRouter> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(field("barrier", (v0) -> {
            return v0.barrierNoise();
        }), field("fluid_level_floodedness", (v0) -> {
            return v0.fluidLevelFloodednessNoise();
        }), field("fluid_level_spread", (v0) -> {
            return v0.fluidLevelSpreadNoise();
        }), field("lava", (v0) -> {
            return v0.lavaNoise();
        }), field("temperature", (v0) -> {
            return v0.temperature();
        }), field("vegetation", (v0) -> {
            return v0.vegetation();
        }), field("continents", (v0) -> {
            return v0.continents();
        }), field("erosion", (v0) -> {
            return v0.erosion();
        }), field("depth", (v0) -> {
            return v0.depth();
        }), field("ridges", (v0) -> {
            return v0.ridges();
        }), field("preliminary_surface_level", (v0) -> {
            return v0.preliminarySurfaceLevel();
        }), field("final_density", (v0) -> {
            return v0.finalDensity();
        }), field("vein_toggle", (v0) -> {
            return v0.veinToggle();
        }), field("vein_ridged", (v0) -> {
            return v0.veinRidged();
        }), field("vein_gap", (v0) -> {
            return v0.veinGap();
        })).apply($$0, NoiseRouter::new);
    });

    public NoiseRouter(DensityFunction $$0, DensityFunction $$1, DensityFunction $$2, DensityFunction $$3, DensityFunction $$4, DensityFunction $$5, DensityFunction $$6, DensityFunction $$7, DensityFunction $$8, DensityFunction $$9, DensityFunction $$10, DensityFunction $$11, DensityFunction $$12, DensityFunction $$13, DensityFunction $$14) {
        this.barrierNoise = $$0;
        this.fluidLevelFloodednessNoise = $$1;
        this.fluidLevelSpreadNoise = $$2;
        this.lavaNoise = $$3;
        this.temperature = $$4;
        this.vegetation = $$5;
        this.continents = $$6;
        this.erosion = $$7;
        this.depth = $$8;
        this.ridges = $$9;
        this.preliminarySurfaceLevel = $$10;
        this.finalDensity = $$11;
        this.veinToggle = $$12;
        this.veinRidged = $$13;
        this.veinGap = $$14;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, NoiseRouter.class), NoiseRouter.class, "barrierNoise;fluidLevelFloodednessNoise;fluidLevelSpreadNoise;lavaNoise;temperature;vegetation;continents;erosion;depth;ridges;preliminarySurfaceLevel;finalDensity;veinToggle;veinRidged;veinGap", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->barrierNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->fluidLevelFloodednessNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->fluidLevelSpreadNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->lavaNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->temperature:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->vegetation:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->continents:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->erosion:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->depth:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->ridges:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->preliminarySurfaceLevel:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->finalDensity:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->veinToggle:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->veinRidged:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->veinGap:Lnet/minecraft/world/level/levelgen/DensityFunction;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, NoiseRouter.class), NoiseRouter.class, "barrierNoise;fluidLevelFloodednessNoise;fluidLevelSpreadNoise;lavaNoise;temperature;vegetation;continents;erosion;depth;ridges;preliminarySurfaceLevel;finalDensity;veinToggle;veinRidged;veinGap", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->barrierNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->fluidLevelFloodednessNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->fluidLevelSpreadNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->lavaNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->temperature:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->vegetation:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->continents:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->erosion:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->depth:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->ridges:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->preliminarySurfaceLevel:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->finalDensity:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->veinToggle:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->veinRidged:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->veinGap:Lnet/minecraft/world/level/levelgen/DensityFunction;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, NoiseRouter.class, Object.class), NoiseRouter.class, "barrierNoise;fluidLevelFloodednessNoise;fluidLevelSpreadNoise;lavaNoise;temperature;vegetation;continents;erosion;depth;ridges;preliminarySurfaceLevel;finalDensity;veinToggle;veinRidged;veinGap", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->barrierNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->fluidLevelFloodednessNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->fluidLevelSpreadNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->lavaNoise:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->temperature:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->vegetation:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->continents:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->erosion:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->depth:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->ridges:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->preliminarySurfaceLevel:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->finalDensity:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->veinToggle:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->veinRidged:Lnet/minecraft/world/level/levelgen/DensityFunction;", "FIELD:Lnet/minecraft/world/level/levelgen/NoiseRouter;->veinGap:Lnet/minecraft/world/level/levelgen/DensityFunction;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public DensityFunction barrierNoise() {
        return this.barrierNoise;
    }

    public DensityFunction fluidLevelFloodednessNoise() {
        return this.fluidLevelFloodednessNoise;
    }

    public DensityFunction fluidLevelSpreadNoise() {
        return this.fluidLevelSpreadNoise;
    }

    public DensityFunction lavaNoise() {
        return this.lavaNoise;
    }

    public DensityFunction temperature() {
        return this.temperature;
    }

    public DensityFunction vegetation() {
        return this.vegetation;
    }

    public DensityFunction continents() {
        return this.continents;
    }

    public DensityFunction erosion() {
        return this.erosion;
    }

    public DensityFunction depth() {
        return this.depth;
    }

    public DensityFunction ridges() {
        return this.ridges;
    }

    public DensityFunction preliminarySurfaceLevel() {
        return this.preliminarySurfaceLevel;
    }

    public DensityFunction finalDensity() {
        return this.finalDensity;
    }

    public DensityFunction veinToggle() {
        return this.veinToggle;
    }

    public DensityFunction veinRidged() {
        return this.veinRidged;
    }

    public DensityFunction veinGap() {
        return this.veinGap;
    }

    private static RecordCodecBuilder<NoiseRouter, DensityFunction> field(String $$0, Function<NoiseRouter, DensityFunction> $$1) {
        return DensityFunction.HOLDER_HELPER_CODEC.fieldOf($$0).forGetter($$1);
    }

    public NoiseRouter mapAll(DensityFunction.Visitor $$0) {
        return new NoiseRouter(this.barrierNoise.mapAll($$0), this.fluidLevelFloodednessNoise.mapAll($$0), this.fluidLevelSpreadNoise.mapAll($$0), this.lavaNoise.mapAll($$0), this.temperature.mapAll($$0), this.vegetation.mapAll($$0), this.continents.mapAll($$0), this.erosion.mapAll($$0), this.depth.mapAll($$0), this.ridges.mapAll($$0), this.preliminarySurfaceLevel.mapAll($$0), this.finalDensity.mapAll($$0), this.veinToggle.mapAll($$0), this.veinRidged.mapAll($$0), this.veinGap.mapAll($$0));
    }
}

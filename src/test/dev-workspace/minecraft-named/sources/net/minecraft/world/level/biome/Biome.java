package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.DryFoliageColor;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Biome.class */
public final class Biome {
    public static final Codec<Biome> DIRECT_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ClimateSettings.CODEC.forGetter($$0 -> {
            return $$0.climateSettings;
        }), EnvironmentAttributeMap.CODEC_ONLY_POSITIONAL.optionalFieldOf(LivingEntity.TAG_ATTRIBUTES, EnvironmentAttributeMap.EMPTY).forGetter($$02 -> {
            return $$02.attributes;
        }), BiomeSpecialEffects.CODEC.fieldOf("effects").forGetter($$03 -> {
            return $$03.specialEffects;
        }), BiomeGenerationSettings.CODEC.forGetter($$04 -> {
            return $$04.generationSettings;
        }), MobSpawnSettings.CODEC.forGetter($$05 -> {
            return $$05.mobSettings;
        })).apply($$0, Biome::new);
    });
    public static final Codec<Biome> NETWORK_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ClimateSettings.CODEC.forGetter($$0 -> {
            return $$0.climateSettings;
        }), EnvironmentAttributeMap.NETWORK_CODEC.optionalFieldOf(LivingEntity.TAG_ATTRIBUTES, EnvironmentAttributeMap.EMPTY).forGetter($$02 -> {
            return $$02.attributes;
        }), BiomeSpecialEffects.CODEC.fieldOf("effects").forGetter($$03 -> {
            return $$03.specialEffects;
        })).apply($$0, ($$04, $$1, $$2) -> {
            return new Biome($$04, $$1, $$2, BiomeGenerationSettings.EMPTY, MobSpawnSettings.EMPTY);
        });
    });
    public static final Codec<Holder<Biome>> CODEC = RegistryFileCodec.create(Registries.BIOME, DIRECT_CODEC);
    public static final Codec<HolderSet<Biome>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.BIOME, DIRECT_CODEC);
    private static final PerlinSimplexNoise TEMPERATURE_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234)), (List<Integer>) ImmutableList.of(0));
    static final PerlinSimplexNoise FROZEN_TEMPERATURE_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(3456)), (List<Integer>) ImmutableList.of(-2, -1, 0));

    @Deprecated(forRemoval = true)
    public static final PerlinSimplexNoise BIOME_INFO_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345)), (List<Integer>) ImmutableList.of(0));
    private static final int TEMPERATURE_CACHE_SIZE = 1024;
    private final ClimateSettings climateSettings;
    private final BiomeGenerationSettings generationSettings;
    private final MobSpawnSettings mobSettings;
    private final EnvironmentAttributeMap attributes;
    private final BiomeSpecialEffects specialEffects;
    private final ThreadLocal<Long2FloatLinkedOpenHashMap> temperatureCache = ThreadLocal.withInitial(() -> {
        Long2FloatLinkedOpenHashMap $$0 = new Long2FloatLinkedOpenHashMap(1024, 0.25f) { // from class: net.minecraft.world.level.biome.Biome.1
            protected void rehash(int $$02) {
            }
        };
        $$0.defaultReturnValue(Float.NaN);
        return $$0;
    });

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Biome$Precipitation.class */
    public enum Precipitation implements StringRepresentable {
        NONE("none"),
        RAIN("rain"),
        SNOW("snow");

        public static final Codec<Precipitation> CODEC = StringRepresentable.fromEnum(Precipitation::values);
        private final String name;

        Precipitation(String $$0) {
            this.name = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Biome$TemperatureModifier.class */
    public enum TemperatureModifier implements StringRepresentable {
        NONE("none") { // from class: net.minecraft.world.level.biome.Biome.TemperatureModifier.1
            @Override // net.minecraft.world.level.biome.Biome.TemperatureModifier
            public float modifyTemperature(BlockPos $$0, float $$1) {
                return $$1;
            }
        },
        FROZEN("frozen") { // from class: net.minecraft.world.level.biome.Biome.TemperatureModifier.2
            @Override // net.minecraft.world.level.biome.Biome.TemperatureModifier
            public float modifyTemperature(BlockPos $$0, float $$1) {
                double $$2 = Biome.FROZEN_TEMPERATURE_NOISE.getValue(((double) $$0.getX()) * 0.05d, ((double) $$0.getZ()) * 0.05d, false) * 7.0d;
                double $$3 = Biome.BIOME_INFO_NOISE.getValue(((double) $$0.getX()) * 0.2d, ((double) $$0.getZ()) * 0.2d, false);
                double $$4 = $$2 + $$3;
                if ($$4 < 0.3d) {
                    double $$5 = Biome.BIOME_INFO_NOISE.getValue(((double) $$0.getX()) * 0.09d, ((double) $$0.getZ()) * 0.09d, false);
                    if ($$5 < 0.8d) {
                        return 0.2f;
                    }
                }
                return $$1;
            }
        };

        private final String name;
        public static final Codec<TemperatureModifier> CODEC = StringRepresentable.fromEnum(TemperatureModifier::values);

        public abstract float modifyTemperature(BlockPos blockPos, float f);

        TemperatureModifier(String $$0) {
            this.name = $$0;
        }

        public String getName() {
            return this.name;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }

    Biome(ClimateSettings $$0, EnvironmentAttributeMap $$1, BiomeSpecialEffects $$2, BiomeGenerationSettings $$3, MobSpawnSettings $$4) {
        this.climateSettings = $$0;
        this.generationSettings = $$3;
        this.mobSettings = $$4;
        this.attributes = $$1;
        this.specialEffects = $$2;
    }

    public MobSpawnSettings getMobSettings() {
        return this.mobSettings;
    }

    public boolean hasPrecipitation() {
        return this.climateSettings.hasPrecipitation();
    }

    public Precipitation getPrecipitationAt(BlockPos $$0, int $$1) {
        if (hasPrecipitation()) {
            return coldEnoughToSnow($$0, $$1) ? Precipitation.SNOW : Precipitation.RAIN;
        }
        return Precipitation.NONE;
    }

    private float getHeightAdjustedTemperature(BlockPos $$0, int $$1) {
        float $$2 = this.climateSettings.temperatureModifier.modifyTemperature($$0, getBaseTemperature());
        int $$3 = $$1 + 17;
        if ($$0.getY() > $$3) {
            float $$4 = (float) (TEMPERATURE_NOISE.getValue($$0.getX() / 8.0f, $$0.getZ() / 8.0f, false) * 8.0d);
            return $$2 - (((($$4 + $$0.getY()) - $$3) * 0.05f) / 40.0f);
        }
        return $$2;
    }

    @Deprecated
    private float getTemperature(BlockPos $$0, int $$1) {
        long $$2 = $$0.asLong();
        Long2FloatLinkedOpenHashMap $$3 = this.temperatureCache.get();
        float $$4 = $$3.get($$2);
        if (!Float.isNaN($$4)) {
            return $$4;
        }
        float $$5 = getHeightAdjustedTemperature($$0, $$1);
        if ($$3.size() == 1024) {
            $$3.removeFirstFloat();
        }
        $$3.put($$2, $$5);
        return $$5;
    }

    public boolean shouldFreeze(LevelReader $$0, BlockPos $$1) {
        return shouldFreeze($$0, $$1, true);
    }

    public boolean shouldFreeze(LevelReader $$0, BlockPos $$1, boolean $$2) {
        if (!warmEnoughToRain($$1, $$0.getSeaLevel()) && $$0.isInsideBuildHeight($$1.getY()) && $$0.getBrightness(LightLayer.BLOCK, $$1) < 10) {
            BlockState $$3 = $$0.getBlockState($$1);
            FluidState $$4 = $$0.getFluidState($$1);
            if ($$4.getType() == Fluids.WATER && ($$3.getBlock() instanceof LiquidBlock)) {
                if (!$$2) {
                    return true;
                }
                boolean $$5 = $$0.isWaterAt($$1.west()) && $$0.isWaterAt($$1.east()) && $$0.isWaterAt($$1.north()) && $$0.isWaterAt($$1.south());
                if (!$$5) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public boolean coldEnoughToSnow(BlockPos $$0, int $$1) {
        return !warmEnoughToRain($$0, $$1);
    }

    public boolean warmEnoughToRain(BlockPos $$0, int $$1) {
        return getTemperature($$0, $$1) >= 0.15f;
    }

    public boolean shouldMeltFrozenOceanIcebergSlightly(BlockPos $$0, int $$1) {
        return getTemperature($$0, $$1) > 0.1f;
    }

    public boolean shouldSnow(LevelReader $$0, BlockPos $$1) {
        if (getPrecipitationAt($$1, $$0.getSeaLevel()) == Precipitation.SNOW && $$0.isInsideBuildHeight($$1.getY()) && $$0.getBrightness(LightLayer.BLOCK, $$1) < 10) {
            BlockState $$2 = $$0.getBlockState($$1);
            if (($$2.isAir() || $$2.is(Blocks.SNOW)) && Blocks.SNOW.defaultBlockState().canSurvive($$0, $$1)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public BiomeGenerationSettings getGenerationSettings() {
        return this.generationSettings;
    }

    public int getGrassColor(double $$0, double $$1) {
        int $$2 = getBaseGrassColor();
        return this.specialEffects.grassColorModifier().modifyColor($$0, $$1, $$2);
    }

    private int getBaseGrassColor() {
        Optional<Integer> $$0 = this.specialEffects.grassColorOverride();
        if ($$0.isPresent()) {
            return $$0.get().intValue();
        }
        return getGrassColorFromTexture();
    }

    private int getGrassColorFromTexture() {
        double $$0 = Mth.clamp(this.climateSettings.temperature, 0.0f, 1.0f);
        double $$1 = Mth.clamp(this.climateSettings.downfall, 0.0f, 1.0f);
        return GrassColor.get($$0, $$1);
    }

    public int getFoliageColor() {
        return this.specialEffects.foliageColorOverride().orElseGet(this::getFoliageColorFromTexture).intValue();
    }

    private int getFoliageColorFromTexture() {
        double $$0 = Mth.clamp(this.climateSettings.temperature, 0.0f, 1.0f);
        double $$1 = Mth.clamp(this.climateSettings.downfall, 0.0f, 1.0f);
        return FoliageColor.get($$0, $$1);
    }

    public int getDryFoliageColor() {
        return this.specialEffects.dryFoliageColorOverride().orElseGet(this::getDryFoliageColorFromTexture).intValue();
    }

    private int getDryFoliageColorFromTexture() {
        double $$0 = Mth.clamp(this.climateSettings.temperature, 0.0f, 1.0f);
        double $$1 = Mth.clamp(this.climateSettings.downfall, 0.0f, 1.0f);
        return DryFoliageColor.get($$0, $$1);
    }

    public float getBaseTemperature() {
        return this.climateSettings.temperature;
    }

    public EnvironmentAttributeMap getAttributes() {
        return this.attributes;
    }

    public BiomeSpecialEffects getSpecialEffects() {
        return this.specialEffects;
    }

    public int getWaterColor() {
        return this.specialEffects.waterColor();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Biome$BiomeBuilder.class */
    public static class BiomeBuilder {
        private Float temperature;
        private Float downfall;
        private BiomeSpecialEffects specialEffects;
        private MobSpawnSettings mobSpawnSettings;
        private BiomeGenerationSettings generationSettings;
        private boolean hasPrecipitation = true;
        private TemperatureModifier temperatureModifier = TemperatureModifier.NONE;
        private final EnvironmentAttributeMap.Builder attributes = EnvironmentAttributeMap.builder();

        public BiomeBuilder hasPrecipitation(boolean $$0) {
            this.hasPrecipitation = $$0;
            return this;
        }

        public BiomeBuilder temperature(float $$0) {
            this.temperature = Float.valueOf($$0);
            return this;
        }

        public BiomeBuilder downfall(float $$0) {
            this.downfall = Float.valueOf($$0);
            return this;
        }

        public BiomeBuilder putAttributes(EnvironmentAttributeMap $$0) {
            this.attributes.putAll($$0);
            return this;
        }

        public BiomeBuilder putAttributes(EnvironmentAttributeMap.Builder $$0) {
            return putAttributes($$0.build());
        }

        public <Value> BiomeBuilder setAttribute(EnvironmentAttribute<Value> $$0, Value $$1) {
            this.attributes.set($$0, $$1);
            return this;
        }

        public <Value, Parameter> BiomeBuilder modifyAttribute(EnvironmentAttribute<Value> $$0, AttributeModifier<Value, Parameter> $$1, Parameter $$2) {
            this.attributes.modify($$0, $$1, $$2);
            return this;
        }

        public BiomeBuilder specialEffects(BiomeSpecialEffects $$0) {
            this.specialEffects = $$0;
            return this;
        }

        public BiomeBuilder mobSpawnSettings(MobSpawnSettings $$0) {
            this.mobSpawnSettings = $$0;
            return this;
        }

        public BiomeBuilder generationSettings(BiomeGenerationSettings $$0) {
            this.generationSettings = $$0;
            return this;
        }

        public BiomeBuilder temperatureAdjustment(TemperatureModifier $$0) {
            this.temperatureModifier = $$0;
            return this;
        }

        public Biome build() {
            if (this.temperature == null || this.downfall == null || this.specialEffects == null || this.mobSpawnSettings == null || this.generationSettings == null) {
                throw new IllegalStateException("You are missing parameters to build a proper biome\n" + String.valueOf(this));
            }
            return new Biome(new ClimateSettings(this.hasPrecipitation, this.temperature.floatValue(), this.temperatureModifier, this.downfall.floatValue()), this.attributes.build(), this.specialEffects, this.generationSettings, this.mobSpawnSettings);
        }

        public String toString() {
            return "BiomeBuilder{\nhasPrecipitation=" + this.hasPrecipitation + ",\ntemperature=" + this.temperature + ",\ntemperatureModifier=" + String.valueOf(this.temperatureModifier) + ",\ndownfall=" + this.downfall + ",\nspecialEffects=" + String.valueOf(this.specialEffects) + ",\nmobSpawnSettings=" + String.valueOf(this.mobSpawnSettings) + ",\ngenerationSettings=" + String.valueOf(this.generationSettings) + ",\n}";
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/Biome$ClimateSettings.class */
    static final class ClimateSettings extends Record {
        private final boolean hasPrecipitation;
        private final float temperature;
        private final TemperatureModifier temperatureModifier;
        private final float downfall;
        public static final MapCodec<ClimateSettings> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.BOOL.fieldOf("has_precipitation").forGetter($$0 -> {
                return Boolean.valueOf($$0.hasPrecipitation);
            }), Codec.FLOAT.fieldOf("temperature").forGetter($$02 -> {
                return Float.valueOf($$02.temperature);
            }), TemperatureModifier.CODEC.optionalFieldOf("temperature_modifier", TemperatureModifier.NONE).forGetter($$03 -> {
                return $$03.temperatureModifier;
            }), Codec.FLOAT.fieldOf("downfall").forGetter($$04 -> {
                return Float.valueOf($$04.downfall);
            })).apply($$0, (v1, v2, v3, v4) -> {
                return new ClimateSettings(v1, v2, v3, v4);
            });
        });

        ClimateSettings(boolean $$0, float $$1, TemperatureModifier $$2, float $$3) {
            this.hasPrecipitation = $$0;
            this.temperature = $$1;
            this.temperatureModifier = $$2;
            this.downfall = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClimateSettings.class), ClimateSettings.class, "hasPrecipitation;temperature;temperatureModifier;downfall", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->hasPrecipitation:Z", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->temperature:F", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->temperatureModifier:Lnet/minecraft/world/level/biome/Biome$TemperatureModifier;", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->downfall:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClimateSettings.class), ClimateSettings.class, "hasPrecipitation;temperature;temperatureModifier;downfall", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->hasPrecipitation:Z", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->temperature:F", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->temperatureModifier:Lnet/minecraft/world/level/biome/Biome$TemperatureModifier;", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->downfall:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClimateSettings.class, Object.class), ClimateSettings.class, "hasPrecipitation;temperature;temperatureModifier;downfall", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->hasPrecipitation:Z", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->temperature:F", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->temperatureModifier:Lnet/minecraft/world/level/biome/Biome$TemperatureModifier;", "FIELD:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;->downfall:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public boolean hasPrecipitation() {
            return this.hasPrecipitation;
        }

        public float temperature() {
            return this.temperature;
        }

        public TemperatureModifier temperatureModifier() {
            return this.temperatureModifier;
        }

        public float downfall() {
            return this.downfall;
        }
    }
}

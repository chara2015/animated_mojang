package net.minecraft.world.attribute;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.TriState;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.MoonPhase;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/EnvironmentAttributes.class */
public interface EnvironmentAttributes {
    public static final EnvironmentAttribute<Integer> FOG_COLOR = register("visual/fog_color", EnvironmentAttribute.builder(AttributeTypes.RGB_COLOR).defaultValue(0).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Float> FOG_START_DISTANCE = register("visual/fog_start_distance", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(0.0f)).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Float> FOG_END_DISTANCE = register("visual/fog_end_distance", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(1024.0f)).valueRange(AttributeRange.NON_NEGATIVE_FLOAT).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Float> SKY_FOG_END_DISTANCE = register("visual/sky_fog_end_distance", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(512.0f)).valueRange(AttributeRange.NON_NEGATIVE_FLOAT).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Float> CLOUD_FOG_END_DISTANCE = register("visual/cloud_fog_end_distance", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(2048.0f)).valueRange(AttributeRange.NON_NEGATIVE_FLOAT).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Integer> WATER_FOG_COLOR = register("visual/water_fog_color", EnvironmentAttribute.builder(AttributeTypes.RGB_COLOR).defaultValue(-16448205).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Float> WATER_FOG_START_DISTANCE = register("visual/water_fog_start_distance", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(-8.0f)).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Float> WATER_FOG_END_DISTANCE = register("visual/water_fog_end_distance", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(96.0f)).valueRange(AttributeRange.NON_NEGATIVE_FLOAT).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Integer> SKY_COLOR = register("visual/sky_color", EnvironmentAttribute.builder(AttributeTypes.RGB_COLOR).defaultValue(0).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Integer> SUNRISE_SUNSET_COLOR = register("visual/sunrise_sunset_color", EnvironmentAttribute.builder(AttributeTypes.ARGB_COLOR).defaultValue(0).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Integer> CLOUD_COLOR = register("visual/cloud_color", EnvironmentAttribute.builder(AttributeTypes.ARGB_COLOR).defaultValue(0).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Float> CLOUD_HEIGHT = register("visual/cloud_height", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(192.33f)).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Float> SUN_ANGLE = register("visual/sun_angle", EnvironmentAttribute.builder(AttributeTypes.ANGLE_DEGREES).defaultValue(Float.valueOf(0.0f)).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Float> MOON_ANGLE = register("visual/moon_angle", EnvironmentAttribute.builder(AttributeTypes.ANGLE_DEGREES).defaultValue(Float.valueOf(0.0f)).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Float> STAR_ANGLE = register("visual/star_angle", EnvironmentAttribute.builder(AttributeTypes.ANGLE_DEGREES).defaultValue(Float.valueOf(0.0f)).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<MoonPhase> MOON_PHASE = register("visual/moon_phase", EnvironmentAttribute.builder(AttributeTypes.MOON_PHASE).defaultValue(MoonPhase.FULL_MOON).syncable());
    public static final EnvironmentAttribute<Float> STAR_BRIGHTNESS = register("visual/star_brightness", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(0.0f)).valueRange(AttributeRange.UNIT_FLOAT).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Integer> SKY_LIGHT_COLOR = register("visual/sky_light_color", EnvironmentAttribute.builder(AttributeTypes.RGB_COLOR).defaultValue(-1).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<Float> SKY_LIGHT_FACTOR = register("visual/sky_light_factor", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(1.0f)).valueRange(AttributeRange.UNIT_FLOAT).spatiallyInterpolated().syncable());
    public static final EnvironmentAttribute<ParticleOptions> DEFAULT_DRIPSTONE_PARTICLE = register("visual/default_dripstone_particle", EnvironmentAttribute.builder(AttributeTypes.PARTICLE).defaultValue(ParticleTypes.DRIPPING_DRIPSTONE_WATER).syncable());
    public static final EnvironmentAttribute<List<AmbientParticle>> AMBIENT_PARTICLES = register("visual/ambient_particles", EnvironmentAttribute.builder(AttributeTypes.AMBIENT_PARTICLES).defaultValue(List.of()).syncable());
    public static final EnvironmentAttribute<BackgroundMusic> BACKGROUND_MUSIC = register("audio/background_music", EnvironmentAttribute.builder(AttributeTypes.BACKGROUND_MUSIC).defaultValue(BackgroundMusic.EMPTY).syncable());
    public static final EnvironmentAttribute<Float> MUSIC_VOLUME = register("audio/music_volume", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(1.0f)).valueRange(AttributeRange.UNIT_FLOAT).syncable());
    public static final EnvironmentAttribute<AmbientSounds> AMBIENT_SOUNDS = register("audio/ambient_sounds", EnvironmentAttribute.builder(AttributeTypes.AMBIENT_SOUNDS).defaultValue(AmbientSounds.EMPTY).syncable());
    public static final EnvironmentAttribute<Boolean> FIREFLY_BUSH_SOUNDS = register("audio/firefly_bush_sounds", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false).syncable());
    public static final EnvironmentAttribute<Float> SKY_LIGHT_LEVEL = register("gameplay/sky_light_level", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(15.0f)).valueRange(AttributeRange.ofFloat(0.0f, 15.0f)).notPositional().syncable());
    public static final EnvironmentAttribute<Boolean> CAN_START_RAID = register("gameplay/can_start_raid", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(true));
    public static final EnvironmentAttribute<Boolean> WATER_EVAPORATES = register("gameplay/water_evaporates", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false).syncable());
    public static final EnvironmentAttribute<BedRule> BED_RULE = register("gameplay/bed_rule", EnvironmentAttribute.builder(AttributeTypes.BED_RULE).defaultValue(BedRule.CAN_SLEEP_WHEN_DARK));
    public static final EnvironmentAttribute<Boolean> RESPAWN_ANCHOR_WORKS = register("gameplay/respawn_anchor_works", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false));
    public static final EnvironmentAttribute<Boolean> NETHER_PORTAL_SPAWNS_PIGLINS = register("gameplay/nether_portal_spawns_piglin", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false));
    public static final EnvironmentAttribute<Boolean> FAST_LAVA = register("gameplay/fast_lava", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false).notPositional().syncable());
    public static final EnvironmentAttribute<Boolean> INCREASED_FIRE_BURNOUT = register("gameplay/increased_fire_burnout", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false));
    public static final EnvironmentAttribute<TriState> EYEBLOSSOM_OPEN = register("gameplay/eyeblossom_open", EnvironmentAttribute.builder(AttributeTypes.TRI_STATE).defaultValue(TriState.DEFAULT));
    public static final EnvironmentAttribute<Float> TURTLE_EGG_HATCH_CHANCE = register("gameplay/turtle_egg_hatch_chance", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(0.0f)).valueRange(AttributeRange.UNIT_FLOAT));
    public static final EnvironmentAttribute<Boolean> PIGLINS_ZOMBIFY = register("gameplay/piglins_zombify", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(true).syncable());
    public static final EnvironmentAttribute<Boolean> SNOW_GOLEM_MELTS = register("gameplay/snow_golem_melts", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false));
    public static final EnvironmentAttribute<Boolean> CREAKING_ACTIVE = register("gameplay/creaking_active", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false).syncable());
    public static final EnvironmentAttribute<Float> SURFACE_SLIME_SPAWN_CHANCE = register("gameplay/surface_slime_spawn_chance", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(0.0f)).valueRange(AttributeRange.UNIT_FLOAT));
    public static final EnvironmentAttribute<Float> CAT_WAKING_UP_GIFT_CHANCE = register("gameplay/cat_waking_up_gift_chance", EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(Float.valueOf(0.0f)).valueRange(AttributeRange.UNIT_FLOAT));
    public static final EnvironmentAttribute<Boolean> BEES_STAY_IN_HIVE = register("gameplay/bees_stay_in_hive", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false));
    public static final EnvironmentAttribute<Boolean> MONSTERS_BURN = register("gameplay/monsters_burn", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false));
    public static final EnvironmentAttribute<Boolean> CAN_PILLAGER_PATROL_SPAWN = register("gameplay/can_pillager_patrol_spawn", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(true));
    public static final EnvironmentAttribute<Activity> VILLAGER_ACTIVITY = register("gameplay/villager_activity", EnvironmentAttribute.builder(AttributeTypes.ACTIVITY).defaultValue(Activity.IDLE));
    public static final EnvironmentAttribute<Activity> BABY_VILLAGER_ACTIVITY = register("gameplay/baby_villager_activity", EnvironmentAttribute.builder(AttributeTypes.ACTIVITY).defaultValue(Activity.IDLE));
    public static final Codec<EnvironmentAttribute<?>> CODEC = BuiltInRegistries.ENVIRONMENT_ATTRIBUTE.byNameCodec();

    static EnvironmentAttribute<?> bootstrap(Registry<EnvironmentAttribute<?>> $$0) {
        return RESPAWN_ANCHOR_WORKS;
    }

    private static <Value> EnvironmentAttribute<Value> register(String $$0, EnvironmentAttribute.Builder<Value> $$1) {
        EnvironmentAttribute<Value> $$2 = $$1.build();
        Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, Identifier.withDefaultNamespace($$0), $$2);
        return $$2;
    }
}

package net.minecraft.world.level.gamerules;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import java.util.Objects;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import net.minecraft.SharedConstants;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gamerules/GameRules.class */
public class GameRules {
    public static final GameRule<Boolean> ADVANCE_TIME;
    public static final GameRule<Boolean> ADVANCE_WEATHER;
    public static final GameRule<Boolean> ALLOW_ENTERING_NETHER_USING_PORTALS;
    public static final GameRule<Boolean> BLOCK_DROPS;
    public static final GameRule<Boolean> BLOCK_EXPLOSION_DROP_DECAY;
    public static final GameRule<Boolean> COMMAND_BLOCKS_WORK;
    public static final GameRule<Boolean> COMMAND_BLOCK_OUTPUT;
    public static final GameRule<Boolean> DROWNING_DAMAGE;
    public static final GameRule<Boolean> ELYTRA_MOVEMENT_CHECK;
    public static final GameRule<Boolean> ENDER_PEARLS_VANISH_ON_DEATH;
    public static final GameRule<Boolean> ENTITY_DROPS;
    public static final GameRule<Boolean> FALL_DAMAGE;
    public static final GameRule<Boolean> FIRE_DAMAGE;
    public static final GameRule<Integer> FIRE_SPREAD_RADIUS_AROUND_PLAYER;
    public static final GameRule<Boolean> FORGIVE_DEAD_PLAYERS;
    public static final GameRule<Boolean> FREEZE_DAMAGE;
    public static final GameRule<Boolean> GLOBAL_SOUND_EVENTS;
    public static final GameRule<Boolean> IMMEDIATE_RESPAWN;
    public static final GameRule<Boolean> KEEP_INVENTORY;
    public static final GameRule<Boolean> LAVA_SOURCE_CONVERSION;
    public static final GameRule<Boolean> LIMITED_CRAFTING;
    public static final GameRule<Boolean> LOCATOR_BAR;
    public static final GameRule<Boolean> LOG_ADMIN_COMMANDS;
    public static final GameRule<Integer> MAX_BLOCK_MODIFICATIONS;
    public static final GameRule<Integer> MAX_COMMAND_FORKS;
    public static final GameRule<Integer> MAX_COMMAND_SEQUENCE_LENGTH;
    public static final GameRule<Integer> MAX_ENTITY_CRAMMING;
    public static final GameRule<Integer> MAX_MINECART_SPEED;
    public static final GameRule<Integer> MAX_SNOW_ACCUMULATION_HEIGHT;
    public static final GameRule<Boolean> MOB_DROPS;
    public static final GameRule<Boolean> MOB_EXPLOSION_DROP_DECAY;
    public static final GameRule<Boolean> MOB_GRIEFING;
    public static final GameRule<Boolean> NATURAL_HEALTH_REGENERATION;
    public static final GameRule<Boolean> PLAYER_MOVEMENT_CHECK;
    public static final GameRule<Integer> PLAYERS_NETHER_PORTAL_CREATIVE_DELAY;
    public static final GameRule<Integer> PLAYERS_NETHER_PORTAL_DEFAULT_DELAY;
    public static final GameRule<Integer> PLAYERS_SLEEPING_PERCENTAGE;
    public static final GameRule<Boolean> PROJECTILES_CAN_BREAK_BLOCKS;
    public static final GameRule<Boolean> PVP;
    public static final GameRule<Boolean> RAIDS;
    public static final GameRule<Integer> RANDOM_TICK_SPEED;
    public static final GameRule<Boolean> REDUCED_DEBUG_INFO;
    public static final GameRule<Integer> RESPAWN_RADIUS;
    public static final GameRule<Boolean> SEND_COMMAND_FEEDBACK;
    public static final GameRule<Boolean> SHOW_ADVANCEMENT_MESSAGES;
    public static final GameRule<Boolean> SHOW_DEATH_MESSAGES;
    public static final GameRule<Boolean> SPAWNER_BLOCKS_WORK;
    public static final GameRule<Boolean> SPAWN_MOBS;
    public static final GameRule<Boolean> SPAWN_MONSTERS;
    public static final GameRule<Boolean> SPAWN_PATROLS;
    public static final GameRule<Boolean> SPAWN_PHANTOMS;
    public static final GameRule<Boolean> SPAWN_WANDERING_TRADERS;
    public static final GameRule<Boolean> SPAWN_WARDENS;
    public static final GameRule<Boolean> SPECTATORS_GENERATE_CHUNKS;
    public static final GameRule<Boolean> SPREAD_VINES;
    public static final GameRule<Boolean> TNT_EXPLODES;
    public static final GameRule<Boolean> TNT_EXPLOSION_DROP_DECAY;
    public static final GameRule<Boolean> UNIVERSAL_ANGER;
    public static final GameRule<Boolean> WATER_SOURCE_CONVERSION;
    private final GameRuleMap rules;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gamerules/GameRules$VisitorCaller.class */
    public interface VisitorCaller<T> {
        void call(GameRuleTypeVisitor gameRuleTypeVisitor, GameRule<T> gameRule);
    }

    public static Codec<GameRules> codec(FeatureFlagSet $$0) {
        return GameRuleMap.CODEC.xmap($$1 -> {
            return new GameRules($$0, $$1);
        }, $$02 -> {
            return $$02.rules;
        });
    }

    static {
        ADVANCE_TIME = registerBoolean("advance_time", GameRuleCategory.UPDATES, !SharedConstants.DEBUG_WORLD_RECREATE);
        ADVANCE_WEATHER = registerBoolean("advance_weather", GameRuleCategory.UPDATES, !SharedConstants.DEBUG_WORLD_RECREATE);
        ALLOW_ENTERING_NETHER_USING_PORTALS = registerBoolean("allow_entering_nether_using_portals", GameRuleCategory.MISC, true);
        BLOCK_DROPS = registerBoolean("block_drops", GameRuleCategory.DROPS, true);
        BLOCK_EXPLOSION_DROP_DECAY = registerBoolean("block_explosion_drop_decay", GameRuleCategory.DROPS, true);
        COMMAND_BLOCKS_WORK = registerBoolean("command_blocks_work", GameRuleCategory.MISC, true);
        COMMAND_BLOCK_OUTPUT = registerBoolean("command_block_output", GameRuleCategory.CHAT, true);
        DROWNING_DAMAGE = registerBoolean("drowning_damage", GameRuleCategory.PLAYER, true);
        ELYTRA_MOVEMENT_CHECK = registerBoolean("elytra_movement_check", GameRuleCategory.PLAYER, true);
        ENDER_PEARLS_VANISH_ON_DEATH = registerBoolean("ender_pearls_vanish_on_death", GameRuleCategory.PLAYER, true);
        ENTITY_DROPS = registerBoolean("entity_drops", GameRuleCategory.DROPS, true);
        FALL_DAMAGE = registerBoolean("fall_damage", GameRuleCategory.PLAYER, true);
        FIRE_DAMAGE = registerBoolean("fire_damage", GameRuleCategory.PLAYER, true);
        FIRE_SPREAD_RADIUS_AROUND_PLAYER = registerInteger("fire_spread_radius_around_player", GameRuleCategory.UPDATES, 128, -1);
        FORGIVE_DEAD_PLAYERS = registerBoolean("forgive_dead_players", GameRuleCategory.MOBS, true);
        FREEZE_DAMAGE = registerBoolean("freeze_damage", GameRuleCategory.PLAYER, true);
        GLOBAL_SOUND_EVENTS = registerBoolean("global_sound_events", GameRuleCategory.MISC, true);
        IMMEDIATE_RESPAWN = registerBoolean("immediate_respawn", GameRuleCategory.PLAYER, false);
        KEEP_INVENTORY = registerBoolean("keep_inventory", GameRuleCategory.PLAYER, false);
        LAVA_SOURCE_CONVERSION = registerBoolean("lava_source_conversion", GameRuleCategory.UPDATES, false);
        LIMITED_CRAFTING = registerBoolean("limited_crafting", GameRuleCategory.PLAYER, false);
        LOCATOR_BAR = registerBoolean("locator_bar", GameRuleCategory.PLAYER, true);
        LOG_ADMIN_COMMANDS = registerBoolean("log_admin_commands", GameRuleCategory.CHAT, true);
        MAX_BLOCK_MODIFICATIONS = registerInteger("max_block_modifications", GameRuleCategory.MISC, ShaderManager.MAX_LOG_LENGTH, 1);
        MAX_COMMAND_FORKS = registerInteger("max_command_forks", GameRuleCategory.MISC, ByteBufCodecs.MAX_INITIAL_COLLECTION_SIZE, 0);
        MAX_COMMAND_SEQUENCE_LENGTH = registerInteger("max_command_sequence_length", GameRuleCategory.MISC, ByteBufCodecs.MAX_INITIAL_COLLECTION_SIZE, 0);
        MAX_ENTITY_CRAMMING = registerInteger("max_entity_cramming", GameRuleCategory.MOBS, 24, 0);
        MAX_MINECART_SPEED = registerInteger("max_minecart_speed", GameRuleCategory.MISC, 8, 1, 1000, FeatureFlagSet.of(FeatureFlags.MINECART_IMPROVEMENTS));
        MAX_SNOW_ACCUMULATION_HEIGHT = registerInteger("max_snow_accumulation_height", GameRuleCategory.UPDATES, 1, 0, 8);
        MOB_DROPS = registerBoolean("mob_drops", GameRuleCategory.DROPS, true);
        MOB_EXPLOSION_DROP_DECAY = registerBoolean("mob_explosion_drop_decay", GameRuleCategory.DROPS, true);
        MOB_GRIEFING = registerBoolean("mob_griefing", GameRuleCategory.MOBS, true);
        NATURAL_HEALTH_REGENERATION = registerBoolean("natural_health_regeneration", GameRuleCategory.PLAYER, true);
        PLAYER_MOVEMENT_CHECK = registerBoolean("player_movement_check", GameRuleCategory.PLAYER, true);
        PLAYERS_NETHER_PORTAL_CREATIVE_DELAY = registerInteger("players_nether_portal_creative_delay", GameRuleCategory.PLAYER, 0, 0);
        PLAYERS_NETHER_PORTAL_DEFAULT_DELAY = registerInteger("players_nether_portal_default_delay", GameRuleCategory.PLAYER, 80, 0);
        PLAYERS_SLEEPING_PERCENTAGE = registerInteger("players_sleeping_percentage", GameRuleCategory.PLAYER, 100, 0);
        PROJECTILES_CAN_BREAK_BLOCKS = registerBoolean("projectiles_can_break_blocks", GameRuleCategory.DROPS, true);
        PVP = registerBoolean("pvp", GameRuleCategory.PLAYER, true);
        RAIDS = registerBoolean("raids", GameRuleCategory.MOBS, true);
        RANDOM_TICK_SPEED = registerInteger("random_tick_speed", GameRuleCategory.UPDATES, 3, 0);
        REDUCED_DEBUG_INFO = registerBoolean("reduced_debug_info", GameRuleCategory.MISC, false);
        RESPAWN_RADIUS = registerInteger("respawn_radius", GameRuleCategory.PLAYER, 10, 0);
        SEND_COMMAND_FEEDBACK = registerBoolean("send_command_feedback", GameRuleCategory.CHAT, true);
        SHOW_ADVANCEMENT_MESSAGES = registerBoolean("show_advancement_messages", GameRuleCategory.CHAT, true);
        SHOW_DEATH_MESSAGES = registerBoolean("show_death_messages", GameRuleCategory.CHAT, true);
        SPAWNER_BLOCKS_WORK = registerBoolean("spawner_blocks_work", GameRuleCategory.MISC, true);
        SPAWN_MOBS = registerBoolean("spawn_mobs", GameRuleCategory.SPAWNING, true);
        SPAWN_MONSTERS = registerBoolean("spawn_monsters", GameRuleCategory.SPAWNING, true);
        SPAWN_PATROLS = registerBoolean("spawn_patrols", GameRuleCategory.SPAWNING, true);
        SPAWN_PHANTOMS = registerBoolean("spawn_phantoms", GameRuleCategory.SPAWNING, true);
        SPAWN_WANDERING_TRADERS = registerBoolean("spawn_wandering_traders", GameRuleCategory.SPAWNING, true);
        SPAWN_WARDENS = registerBoolean("spawn_wardens", GameRuleCategory.SPAWNING, true);
        SPECTATORS_GENERATE_CHUNKS = registerBoolean("spectators_generate_chunks", GameRuleCategory.PLAYER, true);
        SPREAD_VINES = registerBoolean("spread_vines", GameRuleCategory.UPDATES, true);
        TNT_EXPLODES = registerBoolean("tnt_explodes", GameRuleCategory.MISC, true);
        TNT_EXPLOSION_DROP_DECAY = registerBoolean("tnt_explosion_drop_decay", GameRuleCategory.DROPS, false);
        UNIVERSAL_ANGER = registerBoolean("universal_anger", GameRuleCategory.MOBS, false);
        WATER_SOURCE_CONVERSION = registerBoolean("water_source_conversion", GameRuleCategory.UPDATES, true);
    }

    public GameRules(FeatureFlagSet $$0, GameRuleMap $$1) {
        this($$0);
        GameRuleMap gameRuleMap = this.rules;
        GameRuleMap gameRuleMap2 = this.rules;
        Objects.requireNonNull(gameRuleMap2);
        gameRuleMap.setFromIf($$1, gameRuleMap2::has);
    }

    public GameRules(FeatureFlagSet $$0) {
        this.rules = GameRuleMap.of(BuiltInRegistries.GAME_RULE.filterFeatures($$0).listElements().map((v0) -> {
            return v0.value();
        }));
    }

    public Stream<GameRule<?>> availableRules() {
        return this.rules.keySet().stream();
    }

    public <T> T get(GameRule<T> gameRule) {
        T t = (T) this.rules.get(gameRule);
        if (t == null) {
            throw new IllegalArgumentException("Tried to access invalid game rule");
        }
        return t;
    }

    public <T> void set(GameRule<T> $$0, T $$1, MinecraftServer $$2) {
        if (!this.rules.has($$0)) {
            throw new IllegalArgumentException("Tried to set invalid game rule");
        }
        this.rules.set($$0, $$1);
        if ($$2 != null) {
            $$2.onGameRuleChanged($$0, $$1);
        }
    }

    public GameRules copy(FeatureFlagSet $$0) {
        return new GameRules($$0, this.rules);
    }

    public void setAll(GameRules $$0, MinecraftServer $$1) {
        setAll($$0.rules, $$1);
    }

    public void setAll(GameRuleMap $$0, MinecraftServer $$1) {
        $$0.keySet().forEach($$2 -> {
            setFromOther($$0, $$2, $$1);
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void setFromOther(GameRuleMap $$0, GameRule<T> $$1, MinecraftServer $$2) {
        set($$1, Objects.requireNonNull($$0.get($$1)), $$2);
    }

    public void visitGameRuleTypes(GameRuleTypeVisitor $$0) {
        this.rules.keySet().forEach($$1 -> {
            $$0.visit($$1);
            $$1.callVisitor($$0);
        });
    }

    private static GameRule<Boolean> registerBoolean(String $$0, GameRuleCategory $$1, boolean $$2) {
        return register($$0, $$1, GameRuleType.BOOL, BoolArgumentType.bool(), Codec.BOOL, Boolean.valueOf($$2), FeatureFlagSet.of(), (v0, v1) -> {
            v0.visitBoolean(v1);
        }, $$02 -> {
            return $$02.booleanValue() ? 1 : 0;
        });
    }

    private static GameRule<Integer> registerInteger(String $$0, GameRuleCategory $$1, int $$2, int $$3) {
        return registerInteger($$0, $$1, $$2, $$3, Integer.MAX_VALUE, FeatureFlagSet.of());
    }

    private static GameRule<Integer> registerInteger(String $$0, GameRuleCategory $$1, int $$2, int $$3, int $$4) {
        return registerInteger($$0, $$1, $$2, $$3, $$4, FeatureFlagSet.of());
    }

    private static GameRule<Integer> registerInteger(String $$0, GameRuleCategory $$1, int $$2, int $$3, int $$4, FeatureFlagSet $$5) {
        return register($$0, $$1, GameRuleType.INT, IntegerArgumentType.integer($$3, $$4), Codec.intRange($$3, $$4), Integer.valueOf($$2), $$5, (v0, v1) -> {
            v0.visitInteger(v1);
        }, $$02 -> {
            return $$02.intValue();
        });
    }

    private static <T> GameRule<T> register(String $$0, GameRuleCategory $$1, GameRuleType $$2, ArgumentType<T> $$3, Codec<T> $$4, T $$5, FeatureFlagSet $$6, VisitorCaller<T> $$7, ToIntFunction<T> $$8) {
        return (GameRule) Registry.register(BuiltInRegistries.GAME_RULE, $$0, new GameRule($$1, $$2, $$3, $$7, $$4, $$8, $$5, $$6));
    }

    public static GameRule<?> bootstrap(Registry<GameRule<?>> $$0) {
        return ADVANCE_TIME;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> String getAsString(GameRule<T> gameRule) {
        return gameRule.serialize(get(gameRule));
    }
}

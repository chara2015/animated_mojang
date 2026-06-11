package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.schemas.V1451_6;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/StatsCounterFix.class */
public class StatsCounterFix extends DataFix {
    private static final String BLOCK_KEY = "stat.mineBlock";
    private static final String NEW_BLOCK_KEY = "minecraft:mined";
    private static final String NEW_CUSTOM_KEY = "minecraft:custom";
    private static final Set<String> SPECIAL_OBJECTIVE_CRITERIA = Set.of((Object[]) new String[]{"dummy", "trigger", "deathCount", "playerKillCount", "totalKillCount", "health", "food", "air", "armor", "xp", "level", "killedByTeam.aqua", "killedByTeam.black", "killedByTeam.blue", "killedByTeam.dark_aqua", "killedByTeam.dark_blue", "killedByTeam.dark_gray", "killedByTeam.dark_green", "killedByTeam.dark_purple", "killedByTeam.dark_red", "killedByTeam.gold", "killedByTeam.gray", "killedByTeam.green", "killedByTeam.light_purple", "killedByTeam.red", "killedByTeam.white", "killedByTeam.yellow", "teamkill.aqua", "teamkill.black", "teamkill.blue", "teamkill.dark_aqua", "teamkill.dark_blue", "teamkill.dark_gray", "teamkill.dark_green", "teamkill.dark_purple", "teamkill.dark_red", "teamkill.gold", "teamkill.gray", "teamkill.green", "teamkill.light_purple", "teamkill.red", "teamkill.white", "teamkill.yellow"});
    private static final Set<String> SKIP = ImmutableSet.builder().add("stat.craftItem.minecraft.spawn_egg").add("stat.useItem.minecraft.spawn_egg").add("stat.breakItem.minecraft.spawn_egg").add("stat.pickup.minecraft.spawn_egg").add("stat.drop.minecraft.spawn_egg").build();
    private static final Map<String, String> CUSTOM_MAP = ImmutableMap.builder().put("stat.leaveGame", "minecraft:leave_game").put("stat.playOneMinute", "minecraft:play_one_minute").put("stat.timeSinceDeath", "minecraft:time_since_death").put("stat.sneakTime", "minecraft:sneak_time").put("stat.walkOneCm", "minecraft:walk_one_cm").put("stat.crouchOneCm", "minecraft:crouch_one_cm").put("stat.sprintOneCm", "minecraft:sprint_one_cm").put("stat.swimOneCm", "minecraft:swim_one_cm").put("stat.fallOneCm", "minecraft:fall_one_cm").put("stat.climbOneCm", "minecraft:climb_one_cm").put("stat.flyOneCm", "minecraft:fly_one_cm").put("stat.diveOneCm", "minecraft:dive_one_cm").put("stat.minecartOneCm", "minecraft:minecart_one_cm").put("stat.boatOneCm", "minecraft:boat_one_cm").put("stat.pigOneCm", "minecraft:pig_one_cm").put("stat.horseOneCm", "minecraft:horse_one_cm").put("stat.aviateOneCm", "minecraft:aviate_one_cm").put("stat.jump", "minecraft:jump").put("stat.drop", "minecraft:drop").put("stat.damageDealt", "minecraft:damage_dealt").put("stat.damageTaken", "minecraft:damage_taken").put("stat.deaths", "minecraft:deaths").put("stat.mobKills", "minecraft:mob_kills").put("stat.animalsBred", "minecraft:animals_bred").put("stat.playerKills", "minecraft:player_kills").put("stat.fishCaught", "minecraft:fish_caught").put("stat.talkedToVillager", "minecraft:talked_to_villager").put("stat.tradedWithVillager", "minecraft:traded_with_villager").put("stat.cakeSlicesEaten", "minecraft:eat_cake_slice").put("stat.cauldronFilled", "minecraft:fill_cauldron").put("stat.cauldronUsed", "minecraft:use_cauldron").put("stat.armorCleaned", "minecraft:clean_armor").put("stat.bannerCleaned", "minecraft:clean_banner").put("stat.brewingstandInteraction", "minecraft:interact_with_brewingstand").put("stat.beaconInteraction", "minecraft:interact_with_beacon").put("stat.dropperInspected", "minecraft:inspect_dropper").put("stat.hopperInspected", "minecraft:inspect_hopper").put("stat.dispenserInspected", "minecraft:inspect_dispenser").put("stat.noteblockPlayed", "minecraft:play_noteblock").put("stat.noteblockTuned", "minecraft:tune_noteblock").put("stat.flowerPotted", "minecraft:pot_flower").put("stat.trappedChestTriggered", "minecraft:trigger_trapped_chest").put("stat.enderchestOpened", "minecraft:open_enderchest").put("stat.itemEnchanted", "minecraft:enchant_item").put("stat.recordPlayed", "minecraft:play_record").put("stat.furnaceInteraction", "minecraft:interact_with_furnace").put("stat.craftingTableInteraction", "minecraft:interact_with_crafting_table").put("stat.chestOpened", "minecraft:open_chest").put("stat.sleepInBed", "minecraft:sleep_in_bed").put("stat.shulkerBoxOpened", "minecraft:open_shulker_box").build();
    private static final Map<String, String> ITEM_KEYS = ImmutableMap.builder().put("stat.craftItem", "minecraft:crafted").put("stat.useItem", "minecraft:used").put("stat.breakItem", "minecraft:broken").put("stat.pickup", "minecraft:picked_up").put("stat.drop", "minecraft:dropped").build();
    private static final Map<String, String> ENTITY_KEYS = ImmutableMap.builder().put("stat.entityKilledBy", "minecraft:killed_by").put("stat.killEntity", "minecraft:killed").build();
    private static final Map<String, String> ENTITIES = ImmutableMap.builder().put("Bat", "minecraft:bat").put("Blaze", "minecraft:blaze").put("CaveSpider", "minecraft:cave_spider").put("Chicken", "minecraft:chicken").put("Cow", "minecraft:cow").put("Creeper", "minecraft:creeper").put("Donkey", "minecraft:donkey").put("ElderGuardian", "minecraft:elder_guardian").put("Enderman", "minecraft:enderman").put("Endermite", "minecraft:endermite").put("EvocationIllager", "minecraft:evocation_illager").put("Ghast", "minecraft:ghast").put("Guardian", "minecraft:guardian").put("Horse", "minecraft:horse").put("Husk", "minecraft:husk").put("Llama", "minecraft:llama").put("LavaSlime", "minecraft:magma_cube").put("MushroomCow", "minecraft:mooshroom").put("Mule", "minecraft:mule").put("Ozelot", "minecraft:ocelot").put("Parrot", "minecraft:parrot").put("Pig", "minecraft:pig").put("PolarBear", "minecraft:polar_bear").put("Rabbit", "minecraft:rabbit").put("Sheep", "minecraft:sheep").put("Shulker", "minecraft:shulker").put("Silverfish", "minecraft:silverfish").put("SkeletonHorse", "minecraft:skeleton_horse").put("Skeleton", "minecraft:skeleton").put("Slime", "minecraft:slime").put("Spider", "minecraft:spider").put("Squid", "minecraft:squid").put("Stray", "minecraft:stray").put("Vex", "minecraft:vex").put("Villager", "minecraft:villager").put("VindicationIllager", "minecraft:vindication_illager").put("Witch", "minecraft:witch").put("WitherSkeleton", "minecraft:wither_skeleton").put("Wolf", "minecraft:wolf").put("ZombieHorse", "minecraft:zombie_horse").put("PigZombie", "minecraft:zombie_pigman").put("ZombieVillager", "minecraft:zombie_villager").put("Zombie", "minecraft:zombie").build();

    public StatsCounterFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/StatsCounterFix$StatType.class */
    static final class StatType extends Record {
        private final String type;
        private final String typeKey;

        StatType(String $$0, String $$1) {
            this.type = $$0;
            this.typeKey = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StatType.class), StatType.class, "type;typeKey", "FIELD:Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType;->type:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType;->typeKey:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StatType.class), StatType.class, "type;typeKey", "FIELD:Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType;->type:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType;->typeKey:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StatType.class, Object.class), StatType.class, "type;typeKey", "FIELD:Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType;->type:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType;->typeKey:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String type() {
            return this.type;
        }

        public String typeKey() {
            return this.typeKey;
        }
    }

    private static StatType unpackLegacyKey(String $$0) {
        if (SKIP.contains($$0)) {
            return null;
        }
        String $$1 = CUSTOM_MAP.get($$0);
        if ($$1 != null) {
            return new StatType(NEW_CUSTOM_KEY, $$1);
        }
        int $$2 = StringUtils.ordinalIndexOf($$0, ".", 2);
        if ($$2 < 0) {
            return null;
        }
        String $$3 = $$0.substring(0, $$2);
        if (BLOCK_KEY.equals($$3)) {
            String $$4 = upgradeBlock($$0.substring($$2 + 1).replace('.', ':'));
            return new StatType(NEW_BLOCK_KEY, $$4);
        }
        String $$5 = ITEM_KEYS.get($$3);
        if ($$5 != null) {
            String $$6 = $$0.substring($$2 + 1).replace('.', ':');
            String $$7 = upgradeItem($$6);
            String $$8 = $$7 == null ? $$6 : $$7;
            return new StatType($$5, $$8);
        }
        String $$9 = ENTITY_KEYS.get($$3);
        if ($$9 != null) {
            String $$10 = $$0.substring($$2 + 1).replace('.', ':');
            String $$11 = ENTITIES.getOrDefault($$10, $$10);
            return new StatType($$9, $$11);
        }
        return null;
    }

    public TypeRewriteRule makeRule() {
        return TypeRewriteRule.seq(makeStatFixer(), makeObjectiveFixer());
    }

    private TypeRewriteRule makeStatFixer() {
        Type<?> $$0 = getInputSchema().getType(References.STATS);
        Type<?> $$1 = getOutputSchema().getType(References.STATS);
        return fixTypeEverywhereTyped("StatsCounterFix", $$0, $$1, $$12 -> {
            Dynamic<?> $$2 = (Dynamic) $$12.get(DSL.remainderFinder());
            Map<Dynamic<?>, Dynamic<?>> $$3 = Maps.newHashMap();
            Optional<? extends Map<? extends Dynamic<?>, ? extends Dynamic<?>>> $$4 = $$2.getMapValues().result();
            if ($$4.isPresent()) {
                for (Map.Entry<? extends Dynamic<?>, ? extends Dynamic<?>> $$5 : ((Map) $$4.get()).entrySet()) {
                    if (((Dynamic) $$5.getValue()).asNumber().result().isPresent()) {
                        String $$6 = ((Dynamic) $$5.getKey()).asString("");
                        StatType $$7 = unpackLegacyKey($$6);
                        if ($$7 != null) {
                            Dynamic<?> $$8 = $$2.createString($$7.type());
                            Dynamic<?> $$9 = $$3.computeIfAbsent($$8, $$12 -> {
                                return $$2.emptyMap();
                            });
                            $$3.put($$8, $$9.set($$7.typeKey(), (Dynamic) $$5.getValue()));
                        }
                    }
                }
            }
            return Util.readTypedOrThrow($$1, $$2.emptyMap().set("stats", $$2.createMap($$3)));
        });
    }

    private TypeRewriteRule makeObjectiveFixer() {
        Type<?> $$0 = getInputSchema().getType(References.OBJECTIVE);
        Type<?> $$1 = getOutputSchema().getType(References.OBJECTIVE);
        return fixTypeEverywhereTyped("ObjectiveStatFix", $$0, $$1, $$12 -> {
            Dynamic<?> $$2 = (Dynamic) $$12.get(DSL.remainderFinder());
            Dynamic<?> $$3 = $$2.update("CriteriaName", $$02 -> {
                Optional map = $$02.asString().result().map($$02 -> {
                    if (SPECIAL_OBJECTIVE_CRITERIA.contains($$02)) {
                        return $$02;
                    }
                    StatType $$12 = unpackLegacyKey($$02);
                    if ($$12 == null) {
                        return "dummy";
                    }
                    return V1451_6.packNamespacedWithDot($$12.type) + ":" + V1451_6.packNamespacedWithDot($$12.typeKey);
                });
                Objects.requireNonNull($$02);
                return (Dynamic) DataFixUtils.orElse(map.map($$02::createString), $$02);
            });
            return Util.readTypedOrThrow($$1, $$3);
        });
    }

    private static String upgradeItem(String $$0) {
        return ItemStackTheFlatteningFix.updateItem($$0, 0);
    }

    private static String upgradeBlock(String $$0) {
        return BlockStateData.upgradeBlock($$0);
    }
}

package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import java.util.Map;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityIdFix.class */
public class EntityIdFix extends DataFix {
    private static final Map<String, String> ID_MAP = (Map) DataFixUtils.make(Maps.newHashMap(), $$0 -> {
        $$0.put("AreaEffectCloud", "minecraft:area_effect_cloud");
        $$0.put("ArmorStand", "minecraft:armor_stand");
        $$0.put("Arrow", "minecraft:arrow");
        $$0.put("Bat", "minecraft:bat");
        $$0.put("Blaze", "minecraft:blaze");
        $$0.put("Boat", "minecraft:boat");
        $$0.put("CaveSpider", "minecraft:cave_spider");
        $$0.put("Chicken", "minecraft:chicken");
        $$0.put("Cow", "minecraft:cow");
        $$0.put("Creeper", "minecraft:creeper");
        $$0.put("Donkey", "minecraft:donkey");
        $$0.put("DragonFireball", "minecraft:dragon_fireball");
        $$0.put("ElderGuardian", "minecraft:elder_guardian");
        $$0.put("EnderCrystal", "minecraft:ender_crystal");
        $$0.put("EnderDragon", "minecraft:ender_dragon");
        $$0.put("Enderman", "minecraft:enderman");
        $$0.put("Endermite", "minecraft:endermite");
        $$0.put("EyeOfEnderSignal", "minecraft:eye_of_ender_signal");
        $$0.put("FallingSand", "minecraft:falling_block");
        $$0.put("Fireball", "minecraft:fireball");
        $$0.put("FireworksRocketEntity", "minecraft:fireworks_rocket");
        $$0.put("Ghast", "minecraft:ghast");
        $$0.put("Giant", "minecraft:giant");
        $$0.put("Guardian", "minecraft:guardian");
        $$0.put("Horse", "minecraft:horse");
        $$0.put("Husk", "minecraft:husk");
        $$0.put("Item", "minecraft:item");
        $$0.put("ItemFrame", "minecraft:item_frame");
        $$0.put("LavaSlime", "minecraft:magma_cube");
        $$0.put("LeashKnot", "minecraft:leash_knot");
        $$0.put("MinecartChest", "minecraft:chest_minecart");
        $$0.put("MinecartCommandBlock", "minecraft:commandblock_minecart");
        $$0.put("MinecartFurnace", "minecraft:furnace_minecart");
        $$0.put("MinecartHopper", "minecraft:hopper_minecart");
        $$0.put("MinecartRideable", "minecraft:minecart");
        $$0.put("MinecartSpawner", "minecraft:spawner_minecart");
        $$0.put("MinecartTNT", "minecraft:tnt_minecart");
        $$0.put("Mule", "minecraft:mule");
        $$0.put("MushroomCow", "minecraft:mooshroom");
        $$0.put("Ozelot", "minecraft:ocelot");
        $$0.put("Painting", "minecraft:painting");
        $$0.put("Pig", "minecraft:pig");
        $$0.put("PigZombie", "minecraft:zombie_pigman");
        $$0.put("PolarBear", "minecraft:polar_bear");
        $$0.put("PrimedTnt", "minecraft:tnt");
        $$0.put("Rabbit", "minecraft:rabbit");
        $$0.put("Sheep", "minecraft:sheep");
        $$0.put("Shulker", "minecraft:shulker");
        $$0.put("ShulkerBullet", "minecraft:shulker_bullet");
        $$0.put("Silverfish", "minecraft:silverfish");
        $$0.put("Skeleton", "minecraft:skeleton");
        $$0.put("SkeletonHorse", "minecraft:skeleton_horse");
        $$0.put("Slime", "minecraft:slime");
        $$0.put("SmallFireball", "minecraft:small_fireball");
        $$0.put("SnowMan", "minecraft:snowman");
        $$0.put("Snowball", "minecraft:snowball");
        $$0.put("SpectralArrow", "minecraft:spectral_arrow");
        $$0.put("Spider", "minecraft:spider");
        $$0.put("Squid", "minecraft:squid");
        $$0.put("Stray", "minecraft:stray");
        $$0.put("ThrownEgg", "minecraft:egg");
        $$0.put("ThrownEnderpearl", "minecraft:ender_pearl");
        $$0.put("ThrownExpBottle", "minecraft:xp_bottle");
        $$0.put("ThrownPotion", "minecraft:potion");
        $$0.put("Villager", "minecraft:villager");
        $$0.put("VillagerGolem", "minecraft:villager_golem");
        $$0.put("Witch", "minecraft:witch");
        $$0.put("WitherBoss", "minecraft:wither");
        $$0.put("WitherSkeleton", "minecraft:wither_skeleton");
        $$0.put("WitherSkull", "minecraft:wither_skull");
        $$0.put("Wolf", "minecraft:wolf");
        $$0.put("XPOrb", "minecraft:xp_orb");
        $$0.put("Zombie", "minecraft:zombie");
        $$0.put("ZombieHorse", "minecraft:zombie_horse");
        $$0.put("ZombieVillager", "minecraft:zombie_villager");
    });

    public EntityIdFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType<String> $$0 = getInputSchema().findChoiceType(References.ENTITY);
        TaggedChoice.TaggedChoiceType<String> $$1 = getOutputSchema().findChoiceType(References.ENTITY);
        Type<?> $$2 = getInputSchema().getType(References.ITEM_STACK);
        Type<?> $$3 = getOutputSchema().getType(References.ITEM_STACK);
        return TypeRewriteRule.seq(convertUnchecked("item stack entity name hook converter", $$2, $$3), fixTypeEverywhere("EntityIdFix", $$0, $$1, $$02 -> {
            return $$02 -> {
                return $$02.mapFirst($$02 -> {
                    return ID_MAP.getOrDefault($$02, $$02);
                });
            };
        }));
    }
}

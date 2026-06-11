package net.minecraft.util.datafix.schemas;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.BlockEntityIdFix;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V704.class */
public class V704 extends Schema {
    protected static final Map<String, String> ITEM_TO_BLOCKENTITY = (Map) DataFixUtils.make(() -> {
        Map<String, String> $$0 = Maps.newHashMap();
        $$0.put("minecraft:furnace", "minecraft:furnace");
        $$0.put("minecraft:lit_furnace", "minecraft:furnace");
        $$0.put("minecraft:chest", "minecraft:chest");
        $$0.put("minecraft:trapped_chest", "minecraft:chest");
        $$0.put("minecraft:ender_chest", "minecraft:ender_chest");
        $$0.put("minecraft:jukebox", "minecraft:jukebox");
        $$0.put("minecraft:dispenser", "minecraft:dispenser");
        $$0.put("minecraft:dropper", "minecraft:dropper");
        $$0.put("minecraft:sign", "minecraft:sign");
        $$0.put("minecraft:mob_spawner", "minecraft:mob_spawner");
        $$0.put("minecraft:spawner", "minecraft:mob_spawner");
        $$0.put("minecraft:noteblock", "minecraft:noteblock");
        $$0.put("minecraft:brewing_stand", "minecraft:brewing_stand");
        $$0.put("minecraft:enhanting_table", "minecraft:enchanting_table");
        $$0.put("minecraft:command_block", "minecraft:command_block");
        $$0.put("minecraft:beacon", "minecraft:beacon");
        $$0.put("minecraft:skull", "minecraft:skull");
        $$0.put("minecraft:daylight_detector", "minecraft:daylight_detector");
        $$0.put("minecraft:hopper", "minecraft:hopper");
        $$0.put("minecraft:banner", "minecraft:banner");
        $$0.put("minecraft:flower_pot", "minecraft:flower_pot");
        $$0.put("minecraft:repeating_command_block", "minecraft:command_block");
        $$0.put("minecraft:chain_command_block", "minecraft:command_block");
        $$0.put("minecraft:shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:white_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:orange_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:magenta_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:light_blue_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:yellow_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:lime_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:pink_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:gray_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:silver_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:cyan_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:purple_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:blue_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:brown_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:green_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:red_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:black_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:bed", "minecraft:bed");
        $$0.put("minecraft:light_gray_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:banner", "minecraft:banner");
        $$0.put("minecraft:white_banner", "minecraft:banner");
        $$0.put("minecraft:orange_banner", "minecraft:banner");
        $$0.put("minecraft:magenta_banner", "minecraft:banner");
        $$0.put("minecraft:light_blue_banner", "minecraft:banner");
        $$0.put("minecraft:yellow_banner", "minecraft:banner");
        $$0.put("minecraft:lime_banner", "minecraft:banner");
        $$0.put("minecraft:pink_banner", "minecraft:banner");
        $$0.put("minecraft:gray_banner", "minecraft:banner");
        $$0.put("minecraft:silver_banner", "minecraft:banner");
        $$0.put("minecraft:light_gray_banner", "minecraft:banner");
        $$0.put("minecraft:cyan_banner", "minecraft:banner");
        $$0.put("minecraft:purple_banner", "minecraft:banner");
        $$0.put("minecraft:blue_banner", "minecraft:banner");
        $$0.put("minecraft:brown_banner", "minecraft:banner");
        $$0.put("minecraft:green_banner", "minecraft:banner");
        $$0.put("minecraft:red_banner", "minecraft:banner");
        $$0.put("minecraft:black_banner", "minecraft:banner");
        $$0.put("minecraft:standing_sign", "minecraft:sign");
        $$0.put("minecraft:wall_sign", "minecraft:sign");
        $$0.put("minecraft:piston_head", "minecraft:piston");
        $$0.put("minecraft:daylight_detector_inverted", "minecraft:daylight_detector");
        $$0.put("minecraft:unpowered_comparator", "minecraft:comparator");
        $$0.put("minecraft:powered_comparator", "minecraft:comparator");
        $$0.put("minecraft:wall_banner", "minecraft:banner");
        $$0.put("minecraft:standing_banner", "minecraft:banner");
        $$0.put("minecraft:structure_block", "minecraft:structure_block");
        $$0.put("minecraft:end_portal", "minecraft:end_portal");
        $$0.put("minecraft:end_gateway", "minecraft:end_gateway");
        $$0.put("minecraft:sign", "minecraft:sign");
        $$0.put("minecraft:shield", "minecraft:banner");
        $$0.put("minecraft:white_bed", "minecraft:bed");
        $$0.put("minecraft:orange_bed", "minecraft:bed");
        $$0.put("minecraft:magenta_bed", "minecraft:bed");
        $$0.put("minecraft:light_blue_bed", "minecraft:bed");
        $$0.put("minecraft:yellow_bed", "minecraft:bed");
        $$0.put("minecraft:lime_bed", "minecraft:bed");
        $$0.put("minecraft:pink_bed", "minecraft:bed");
        $$0.put("minecraft:gray_bed", "minecraft:bed");
        $$0.put("minecraft:silver_bed", "minecraft:bed");
        $$0.put("minecraft:light_gray_bed", "minecraft:bed");
        $$0.put("minecraft:cyan_bed", "minecraft:bed");
        $$0.put("minecraft:purple_bed", "minecraft:bed");
        $$0.put("minecraft:blue_bed", "minecraft:bed");
        $$0.put("minecraft:brown_bed", "minecraft:bed");
        $$0.put("minecraft:green_bed", "minecraft:bed");
        $$0.put("minecraft:red_bed", "minecraft:bed");
        $$0.put("minecraft:black_bed", "minecraft:bed");
        $$0.put("minecraft:oak_sign", "minecraft:sign");
        $$0.put("minecraft:spruce_sign", "minecraft:sign");
        $$0.put("minecraft:birch_sign", "minecraft:sign");
        $$0.put("minecraft:jungle_sign", "minecraft:sign");
        $$0.put("minecraft:acacia_sign", "minecraft:sign");
        $$0.put("minecraft:dark_oak_sign", "minecraft:sign");
        $$0.put("minecraft:crimson_sign", "minecraft:sign");
        $$0.put("minecraft:warped_sign", "minecraft:sign");
        $$0.put("minecraft:skeleton_skull", "minecraft:skull");
        $$0.put("minecraft:wither_skeleton_skull", "minecraft:skull");
        $$0.put("minecraft:zombie_head", "minecraft:skull");
        $$0.put("minecraft:player_head", "minecraft:skull");
        $$0.put("minecraft:creeper_head", "minecraft:skull");
        $$0.put("minecraft:dragon_head", "minecraft:skull");
        $$0.put("minecraft:barrel", "minecraft:barrel");
        $$0.put("minecraft:conduit", "minecraft:conduit");
        $$0.put("minecraft:smoker", "minecraft:smoker");
        $$0.put("minecraft:blast_furnace", "minecraft:blast_furnace");
        $$0.put("minecraft:lectern", "minecraft:lectern");
        $$0.put("minecraft:bell", "minecraft:bell");
        $$0.put("minecraft:jigsaw", "minecraft:jigsaw");
        $$0.put("minecraft:campfire", "minecraft:campfire");
        $$0.put("minecraft:bee_nest", "minecraft:beehive");
        $$0.put("minecraft:beehive", "minecraft:beehive");
        $$0.put("minecraft:sculk_sensor", "minecraft:sculk_sensor");
        $$0.put("minecraft:decorated_pot", "minecraft:decorated_pot");
        $$0.put("minecraft:crafter", "minecraft:crafter");
        return ImmutableMap.copyOf($$0);
    });
    protected static final Hook.HookFunction ADD_NAMES = new Hook.HookFunction() { // from class: net.minecraft.util.datafix.schemas.V704.1
        public <T> T apply(DynamicOps<T> dynamicOps, T t) {
            return (T) V99.addNames(new Dynamic(dynamicOps, t), V704.ITEM_TO_BLOCKENTITY, V99.ITEM_TO_ENTITY);
        }
    };

    public V704(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Type<?> getChoiceType(DSL.TypeReference $$0, String $$1) {
        if (Objects.equals($$0.typeName(), References.BLOCK_ENTITY.typeName())) {
            return super.getChoiceType($$0, NamespacedSchema.ensureNamespaced($$1));
        }
        return super.getChoiceType($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
        BlockEntityIdFix.ID_MAP.forEach(($$12, $$2) -> {
            $$1.put($$2, (Supplier) Objects.requireNonNull((Supplier) $$1.remove($$12), (Supplier<String>) () -> {
                return "Didn't find " + $$12 + " in schema";
            }));
        });
        return $$1;
    }

    public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
        super.registerTypes($$0, $$1, $$2);
        $$0.registerType(true, References.BLOCK_ENTITY, () -> {
            return DSL.optionalFields("components", References.DATA_COMPONENTS.in($$0), DSL.taggedChoiceLazy(Entity.TAG_ID, NamespacedSchema.namespacedString(), $$2));
        });
        $$0.registerType(true, References.ITEM_STACK, () -> {
            return DSL.hook(DSL.optionalFields(Entity.TAG_ID, References.ITEM_NAME.in($$0), "tag", V99.itemStackTag($$0)), ADD_NAMES, Hook.HookFunction.IDENTITY);
        });
    }
}

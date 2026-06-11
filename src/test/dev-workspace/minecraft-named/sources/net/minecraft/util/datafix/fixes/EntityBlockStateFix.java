package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Tag;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.storage.LevelStorageSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityBlockStateFix.class */
public class EntityBlockStateFix extends DataFix {
    private static final Map<String, Integer> MAP = (Map) DataFixUtils.make(Maps.newHashMap(), $$0 -> {
        $$0.put(JigsawBlockEntity.DEFAULT_FINAL_STATE, 0);
        $$0.put("minecraft:stone", 1);
        $$0.put("minecraft:grass", 2);
        $$0.put("minecraft:dirt", 3);
        $$0.put("minecraft:cobblestone", 4);
        $$0.put("minecraft:planks", 5);
        $$0.put("minecraft:sapling", 6);
        $$0.put("minecraft:bedrock", 7);
        $$0.put("minecraft:flowing_water", 8);
        $$0.put(ItemPotionFix.DEFAULT, 9);
        $$0.put("minecraft:flowing_lava", 10);
        $$0.put("minecraft:lava", 11);
        $$0.put("minecraft:sand", 12);
        $$0.put("minecraft:gravel", 13);
        $$0.put("minecraft:gold_ore", 14);
        $$0.put("minecraft:iron_ore", 15);
        $$0.put("minecraft:coal_ore", 16);
        $$0.put("minecraft:log", 17);
        $$0.put("minecraft:leaves", 18);
        $$0.put("minecraft:sponge", 19);
        $$0.put("minecraft:glass", 20);
        $$0.put("minecraft:lapis_ore", 21);
        $$0.put("minecraft:lapis_block", 22);
        $$0.put("minecraft:dispenser", 23);
        $$0.put("minecraft:sandstone", 24);
        $$0.put("minecraft:noteblock", 25);
        $$0.put("minecraft:bed", 26);
        $$0.put("minecraft:golden_rail", 27);
        $$0.put("minecraft:detector_rail", 28);
        $$0.put("minecraft:sticky_piston", 29);
        $$0.put("minecraft:web", 30);
        $$0.put("minecraft:tallgrass", 31);
        $$0.put("minecraft:deadbush", 32);
        $$0.put("minecraft:piston", 33);
        $$0.put("minecraft:piston_head", 34);
        $$0.put("minecraft:wool", 35);
        $$0.put("minecraft:piston_extension", 36);
        $$0.put("minecraft:yellow_flower", 37);
        $$0.put("minecraft:red_flower", 38);
        $$0.put("minecraft:brown_mushroom", 39);
        $$0.put("minecraft:red_mushroom", 40);
        $$0.put("minecraft:gold_block", 41);
        $$0.put("minecraft:iron_block", 42);
        $$0.put("minecraft:double_stone_slab", 43);
        $$0.put("minecraft:stone_slab", 44);
        $$0.put("minecraft:brick_block", 45);
        $$0.put("minecraft:tnt", 46);
        $$0.put("minecraft:bookshelf", 47);
        $$0.put("minecraft:mossy_cobblestone", 48);
        $$0.put("minecraft:obsidian", 49);
        $$0.put("minecraft:torch", 50);
        $$0.put("minecraft:fire", 51);
        $$0.put("minecraft:mob_spawner", 52);
        $$0.put("minecraft:oak_stairs", 53);
        $$0.put("minecraft:chest", 54);
        $$0.put("minecraft:redstone_wire", 55);
        $$0.put("minecraft:diamond_ore", 56);
        $$0.put("minecraft:diamond_block", 57);
        $$0.put("minecraft:crafting_table", 58);
        $$0.put("minecraft:wheat", 59);
        $$0.put("minecraft:farmland", 60);
        $$0.put("minecraft:furnace", 61);
        $$0.put("minecraft:lit_furnace", 62);
        $$0.put("minecraft:standing_sign", 63);
        $$0.put("minecraft:wooden_door", 64);
        $$0.put("minecraft:ladder", 65);
        $$0.put("minecraft:rail", 66);
        $$0.put("minecraft:stone_stairs", 67);
        $$0.put("minecraft:wall_sign", 68);
        $$0.put("minecraft:lever", 69);
        $$0.put("minecraft:stone_pressure_plate", 70);
        $$0.put("minecraft:iron_door", 71);
        $$0.put("minecraft:wooden_pressure_plate", 72);
        $$0.put("minecraft:redstone_ore", 73);
        $$0.put("minecraft:lit_redstone_ore", 74);
        $$0.put("minecraft:unlit_redstone_torch", 75);
        $$0.put("minecraft:redstone_torch", 76);
        $$0.put("minecraft:stone_button", 77);
        $$0.put("minecraft:snow_layer", 78);
        $$0.put("minecraft:ice", 79);
        $$0.put("minecraft:snow", 80);
        $$0.put("minecraft:cactus", 81);
        $$0.put("minecraft:clay", 82);
        $$0.put("minecraft:reeds", 83);
        $$0.put("minecraft:jukebox", 84);
        $$0.put("minecraft:fence", 85);
        $$0.put("minecraft:pumpkin", 86);
        $$0.put("minecraft:netherrack", 87);
        $$0.put("minecraft:soul_sand", 88);
        $$0.put("minecraft:glowstone", 89);
        $$0.put("minecraft:portal", 90);
        $$0.put("minecraft:lit_pumpkin", 91);
        $$0.put("minecraft:cake", 92);
        $$0.put("minecraft:unpowered_repeater", 93);
        $$0.put("minecraft:powered_repeater", 94);
        $$0.put("minecraft:stained_glass", 95);
        $$0.put("minecraft:trapdoor", 96);
        $$0.put("minecraft:monster_egg", 97);
        $$0.put("minecraft:stonebrick", 98);
        $$0.put("minecraft:brown_mushroom_block", 99);
        $$0.put("minecraft:red_mushroom_block", 100);
        $$0.put("minecraft:iron_bars", Integer.valueOf(ClientboundGameEventPacket.DEMO_PARAM_HINT_1));
        $$0.put("minecraft:glass_pane", Integer.valueOf(ClientboundGameEventPacket.DEMO_PARAM_HINT_2));
        $$0.put("minecraft:melon_block", Integer.valueOf(ClientboundGameEventPacket.DEMO_PARAM_HINT_3));
        $$0.put("minecraft:pumpkin_stem", Integer.valueOf(ClientboundGameEventPacket.DEMO_PARAM_HINT_4));
        $$0.put("minecraft:melon_stem", 105);
        $$0.put("minecraft:vine", Integer.valueOf(LivingEntity.SADDLE_OFFSET));
        $$0.put("minecraft:fence_gate", 107);
        $$0.put("minecraft:brick_stairs", 108);
        $$0.put("minecraft:stone_brick_stairs", 109);
        $$0.put("minecraft:mycelium", 110);
        $$0.put("minecraft:waterlily", 111);
        $$0.put("minecraft:nether_brick", 112);
        $$0.put("minecraft:nether_brick_fence", Integer.valueOf(AdvancementsScreen.WINDOW_INSIDE_HEIGHT));
        $$0.put("minecraft:nether_brick_stairs", Integer.valueOf(BookEditScreen.TEXT_WIDTH));
        $$0.put("minecraft:nether_wart", 115);
        $$0.put("minecraft:enchanting_table", 116);
        $$0.put("minecraft:brewing_stand", 117);
        $$0.put("minecraft:cauldron", 118);
        $$0.put("minecraft:end_portal", 119);
        $$0.put("minecraft:end_portal_frame", 120);
        $$0.put("minecraft:end_stone", 121);
        $$0.put("minecraft:dragon_egg", 122);
        $$0.put("minecraft:redstone_lamp", 123);
        $$0.put("minecraft:lit_redstone_lamp", 124);
        $$0.put("minecraft:double_wooden_slab", 125);
        $$0.put("minecraft:wooden_slab", Integer.valueOf(BookEditScreen.TEXT_HEIGHT));
        $$0.put("minecraft:cocoa", 127);
        $$0.put("minecraft:sandstone_stairs", 128);
        $$0.put("minecraft:emerald_ore", 129);
        $$0.put("minecraft:ender_chest", 130);
        $$0.put("minecraft:tripwire_hook", 131);
        $$0.put("minecraft:tripwire", 132);
        $$0.put("minecraft:emerald_block", 133);
        $$0.put("minecraft:spruce_stairs", 134);
        $$0.put("minecraft:birch_stairs", 135);
        $$0.put("minecraft:jungle_stairs", 136);
        $$0.put("minecraft:command_block", 137);
        $$0.put("minecraft:beacon", 138);
        $$0.put("minecraft:cobblestone_wall", 139);
        $$0.put("minecraft:flower_pot", 140);
        $$0.put("minecraft:carrots", 141);
        $$0.put("minecraft:potatoes", 142);
        $$0.put("minecraft:wooden_button", 143);
        $$0.put("minecraft:skull", Integer.valueOf(TamableAnimal.TELEPORT_WHEN_DISTANCE_IS_SQ));
        $$0.put("minecraft:anvil", 145);
        $$0.put("minecraft:trapped_chest", 146);
        $$0.put("minecraft:light_weighted_pressure_plate", Integer.valueOf(RecipeBookComponent.IMAGE_WIDTH));
        $$0.put("minecraft:heavy_weighted_pressure_plate", 148);
        $$0.put("minecraft:unpowered_comparator", 149);
        $$0.put("minecraft:powered_comparator", 150);
        $$0.put("minecraft:daylight_detector", 151);
        $$0.put("minecraft:redstone_block", 152);
        $$0.put("minecraft:quartz_ore", 153);
        $$0.put("minecraft:hopper", Integer.valueOf(TutorialToast.PROGRESS_BAR_WIDTH));
        $$0.put("minecraft:quartz_block", 155);
        $$0.put("minecraft:quartz_stairs", 156);
        $$0.put("minecraft:activator_rail", 157);
        $$0.put("minecraft:dropper", 158);
        $$0.put("minecraft:stained_hardened_clay", 159);
        $$0.put("minecraft:stained_glass_pane", 160);
        $$0.put("minecraft:leaves2", 161);
        $$0.put("minecraft:log2", 162);
        $$0.put("minecraft:acacia_stairs", 163);
        $$0.put("minecraft:dark_oak_stairs", 164);
        $$0.put("minecraft:slime", 165);
        $$0.put("minecraft:barrier", Integer.valueOf(RecipeBookComponent.IMAGE_HEIGHT));
        $$0.put("minecraft:iron_trapdoor", Integer.valueOf(ChatFormatting.PREFIX_CODE));
        $$0.put("minecraft:prismarine", 168);
        $$0.put("minecraft:sea_lantern", 169);
        $$0.put("minecraft:hay_block", 170);
        $$0.put("minecraft:carpet", 171);
        $$0.put("minecraft:hardened_clay", 172);
        $$0.put("minecraft:coal_block", 173);
        $$0.put("minecraft:packed_ice", 174);
        $$0.put("minecraft:double_plant", 175);
        $$0.put("minecraft:standing_banner", 176);
        $$0.put("minecraft:wall_banner", 177);
        $$0.put("minecraft:daylight_detector_inverted", 178);
        $$0.put("minecraft:red_sandstone", 179);
        $$0.put("minecraft:red_sandstone_stairs", 180);
        $$0.put("minecraft:double_stone_slab2", 181);
        $$0.put("minecraft:stone_slab2", Integer.valueOf(ContextualBarRenderer.WIDTH));
        $$0.put("minecraft:spruce_fence_gate", 183);
        $$0.put("minecraft:birch_fence_gate", 184);
        $$0.put("minecraft:jungle_fence_gate", 185);
        $$0.put("minecraft:dark_oak_fence_gate", 186);
        $$0.put("minecraft:acacia_fence_gate", 187);
        $$0.put("minecraft:spruce_fence", 188);
        $$0.put("minecraft:birch_fence", 189);
        $$0.put("minecraft:jungle_fence", 190);
        $$0.put("minecraft:dark_oak_fence", 191);
        $$0.put("minecraft:acacia_fence", 192);
        $$0.put("minecraft:spruce_door", 193);
        $$0.put("minecraft:birch_door", 194);
        $$0.put("minecraft:jungle_door", 195);
        $$0.put("minecraft:acacia_door", 196);
        $$0.put("minecraft:dark_oak_door", 197);
        $$0.put("minecraft:end_rod", 198);
        $$0.put("minecraft:chorus_plant", 199);
        $$0.put("minecraft:chorus_flower", 200);
        $$0.put("minecraft:purpur_block", 201);
        $$0.put("minecraft:purpur_pillar", 202);
        $$0.put("minecraft:purpur_stairs", 203);
        $$0.put("minecraft:purpur_double_slab", 204);
        $$0.put("minecraft:purpur_slab", 205);
        $$0.put("minecraft:end_bricks", 206);
        $$0.put("minecraft:beetroots", 207);
        $$0.put("minecraft:grass_path", 208);
        $$0.put("minecraft:end_gateway", 209);
        $$0.put("minecraft:repeating_command_block", 210);
        $$0.put("minecraft:chain_command_block", 211);
        $$0.put("minecraft:frosted_ice", 212);
        $$0.put("minecraft:magma", 213);
        $$0.put("minecraft:nether_wart_block", 214);
        $$0.put("minecraft:red_nether_brick", 215);
        $$0.put("minecraft:bone_block", 216);
        $$0.put("minecraft:structure_void", 217);
        $$0.put("minecraft:observer", 218);
        $$0.put("minecraft:white_shulker_box", 219);
        $$0.put("minecraft:orange_shulker_box", 220);
        $$0.put("minecraft:magenta_shulker_box", 221);
        $$0.put("minecraft:light_blue_shulker_box", 222);
        $$0.put("minecraft:yellow_shulker_box", 223);
        $$0.put("minecraft:lime_shulker_box", 224);
        $$0.put("minecraft:pink_shulker_box", 225);
        $$0.put("minecraft:gray_shulker_box", 226);
        $$0.put("minecraft:silver_shulker_box", 227);
        $$0.put("minecraft:cyan_shulker_box", 228);
        $$0.put("minecraft:purple_shulker_box", 229);
        $$0.put("minecraft:blue_shulker_box", 230);
        $$0.put("minecraft:brown_shulker_box", 231);
        $$0.put("minecraft:green_shulker_box", 232);
        $$0.put("minecraft:red_shulker_box", 233);
        $$0.put("minecraft:black_shulker_box", Integer.valueOf(AdvancementsScreen.WINDOW_INSIDE_WIDTH));
        $$0.put("minecraft:white_glazed_terracotta", 235);
        $$0.put("minecraft:orange_glazed_terracotta", 236);
        $$0.put("minecraft:magenta_glazed_terracotta", 237);
        $$0.put("minecraft:light_blue_glazed_terracotta", 238);
        $$0.put("minecraft:yellow_glazed_terracotta", 239);
        $$0.put("minecraft:lime_glazed_terracotta", 240);
        $$0.put("minecraft:pink_glazed_terracotta", 241);
        $$0.put("minecraft:gray_glazed_terracotta", 242);
        $$0.put("minecraft:silver_glazed_terracotta", 243);
        $$0.put("minecraft:cyan_glazed_terracotta", 244);
        $$0.put("minecraft:purple_glazed_terracotta", 245);
        $$0.put("minecraft:blue_glazed_terracotta", 246);
        $$0.put("minecraft:brown_glazed_terracotta", 247);
        $$0.put("minecraft:green_glazed_terracotta", 248);
        $$0.put("minecraft:red_glazed_terracotta", 249);
        $$0.put("minecraft:black_glazed_terracotta", 250);
        $$0.put("minecraft:concrete", 251);
        $$0.put("minecraft:concrete_powder", Integer.valueOf(AdvancementsScreen.WINDOW_WIDTH));
        $$0.put("minecraft:structure_block", 255);
    });

    public EntityBlockStateFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public static int getBlockId(String $$0) {
        Integer $$1 = MAP.get($$0);
        if ($$1 == null) {
            return 0;
        }
        return $$1.intValue();
    }

    public TypeRewriteRule makeRule() {
        Schema $$0 = getInputSchema();
        Schema $$1 = getOutputSchema();
        Function<Typed<?>, Typed<?>> $$2 = $$02 -> {
            return updateBlockToBlockState($$02, "DisplayTile", "DisplayData", "DisplayState");
        };
        Function<Typed<?>, Typed<?>> $$3 = $$03 -> {
            return updateBlockToBlockState($$03, "inTile", "inData", "inBlockState");
        };
        Type<Pair<Either<Pair<String, Either<Integer, String>>, Unit>, Dynamic<?>>> $$4 = DSL.and(DSL.optional(DSL.field("inTile", DSL.named(References.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), NamespacedSchema.namespacedString())))), DSL.remainderType());
        Function<Typed<?>, Typed<?>> $$5 = $$12 -> {
            return $$12.update($$4.finder(), DSL.remainderType(), (v0) -> {
                return v0.getSecond();
            });
        };
        return fixTypeEverywhereTyped("EntityBlockStateFix", $$0.getType(References.ENTITY), $$1.getType(References.ENTITY), $$32 -> {
            return updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity($$32, "minecraft:falling_block", this::updateFallingBlock), "minecraft:enderman", $$04 -> {
                return updateBlockToBlockState($$04, "carried", "carriedData", "carriedBlockState");
            }), "minecraft:arrow", $$3), "minecraft:spectral_arrow", $$3), "minecraft:egg", $$5), "minecraft:ender_pearl", $$5), "minecraft:fireball", $$5), "minecraft:potion", $$5), "minecraft:small_fireball", $$5), "minecraft:snowball", $$5), "minecraft:wither_skull", $$5), "minecraft:xp_bottle", $$5), "minecraft:commandblock_minecart", $$2), "minecraft:minecart", $$2), "minecraft:chest_minecart", $$2), "minecraft:furnace_minecart", $$2), "minecraft:tnt_minecart", $$2), "minecraft:hopper_minecart", $$2), "minecraft:spawner_minecart", $$2);
        });
    }

    private Typed<?> updateFallingBlock(Typed<?> $$0) {
        Type<Either<Pair<String, Either<Integer, String>>, Unit>> $$1 = DSL.optional(DSL.field("Block", DSL.named(References.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), NamespacedSchema.namespacedString()))));
        Type<Either<Pair<String, Dynamic<?>>, Unit>> $$2 = DSL.optional(DSL.field("BlockState", DSL.named(References.BLOCK_STATE.typeName(), DSL.remainderType())));
        Dynamic<?> $$3 = (Dynamic) $$0.get(DSL.remainderFinder());
        return $$0.update($$1.finder(), $$2, $$12 -> {
            int $$22 = ((Integer) $$12.map($$02 -> {
                return (Integer) ((Either) $$02.getSecond()).map($$02 -> {
                    return $$02;
                }, EntityBlockStateFix::getBlockId);
            }, $$12 -> {
                Optional<Number> $$23 = $$3.get("TileID").asNumber().result();
                return (Integer) $$23.map((v0) -> {
                    return v0.intValue();
                }).orElseGet(() -> {
                    return Integer.valueOf($$3.get("Tile").asByte((byte) 0) & 255);
                });
            })).intValue();
            int $$32 = $$3.get(LevelStorageSource.TAG_DATA).asInt(0) & 15;
            return Either.left(Pair.of(References.BLOCK_STATE.typeName(), BlockStateData.getTag(($$22 << 4) | $$32)));
        }).set(DSL.remainderFinder(), $$3.remove(LevelStorageSource.TAG_DATA).remove("TileID").remove("Tile"));
    }

    private Typed<?> updateBlockToBlockState(Typed<?> $$0, String $$1, String $$2, String $$3) {
        Tag.TagType tagTypeField = DSL.field($$1, DSL.named(References.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), NamespacedSchema.namespacedString())));
        Tag.TagType tagTypeField2 = DSL.field($$3, DSL.named(References.BLOCK_STATE.typeName(), DSL.remainderType()));
        Dynamic<?> $$6 = (Dynamic) $$0.getOrCreate(DSL.remainderFinder());
        return $$0.update(tagTypeField.finder(), tagTypeField2, $$22 -> {
            int $$32 = ((Integer) ((Either) $$22.getSecond()).map($$02 -> {
                return $$02;
            }, EntityBlockStateFix::getBlockId)).intValue();
            int $$4 = $$6.get($$2).asInt(0) & 15;
            return Pair.of(References.BLOCK_STATE.typeName(), BlockStateData.getTag(($$32 << 4) | $$4));
        }).set(DSL.remainderFinder(), $$6.remove($$2));
    }

    private Typed<?> updateEntity(Typed<?> $$0, String $$1, Function<Typed<?>, Typed<?>> $$2) {
        Type<?> $$3 = getInputSchema().getChoiceType(References.ENTITY, $$1);
        Type<?> $$4 = getOutputSchema().getChoiceType(References.ENTITY, $$1);
        return $$0.updateTyped(DSL.namedChoice($$1, $$3), $$4, $$2);
    }
}

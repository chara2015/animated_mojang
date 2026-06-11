package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.nbt.SnbtOperations;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
import net.minecraft.util.datafix.ExtraDataFixUtils;
import net.minecraft.util.datafix.PackedBitStorage;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkPalettedStorageFix.class */
public class ChunkPalettedStorageFix extends DataFix {
    private static final int NORTH_WEST_MASK = 128;
    private static final int WEST_MASK = 64;
    private static final int SOUTH_WEST_MASK = 32;
    private static final int SOUTH_MASK = 16;
    private static final int SOUTH_EAST_MASK = 8;
    private static final int EAST_MASK = 4;
    private static final int NORTH_EAST_MASK = 2;
    private static final int NORTH_MASK = 1;
    static final Logger LOGGER = LogUtils.getLogger();
    private static final int SIZE = 4096;

    public ChunkPalettedStorageFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$MappingConstants.class */
    static class MappingConstants {
        static final BitSet VIRTUAL = new BitSet(256);
        static final BitSet FIX = new BitSet(256);
        static final Dynamic<?> PUMPKIN = ExtraDataFixUtils.blockState("minecraft:pumpkin");
        static final Dynamic<?> SNOWY_PODZOL = ExtraDataFixUtils.blockState("minecraft:podzol", Map.of("snowy", SnbtOperations.BUILTIN_TRUE));
        static final Dynamic<?> SNOWY_GRASS = ExtraDataFixUtils.blockState("minecraft:grass_block", Map.of("snowy", SnbtOperations.BUILTIN_TRUE));
        static final Dynamic<?> SNOWY_MYCELIUM = ExtraDataFixUtils.blockState("minecraft:mycelium", Map.of("snowy", SnbtOperations.BUILTIN_TRUE));
        static final Dynamic<?> UPPER_SUNFLOWER = ExtraDataFixUtils.blockState("minecraft:sunflower", Map.of("half", "upper"));
        static final Dynamic<?> UPPER_LILAC = ExtraDataFixUtils.blockState("minecraft:lilac", Map.of("half", "upper"));
        static final Dynamic<?> UPPER_TALL_GRASS = ExtraDataFixUtils.blockState("minecraft:tall_grass", Map.of("half", "upper"));
        static final Dynamic<?> UPPER_LARGE_FERN = ExtraDataFixUtils.blockState("minecraft:large_fern", Map.of("half", "upper"));
        static final Dynamic<?> UPPER_ROSE_BUSH = ExtraDataFixUtils.blockState("minecraft:rose_bush", Map.of("half", "upper"));
        static final Dynamic<?> UPPER_PEONY = ExtraDataFixUtils.blockState("minecraft:peony", Map.of("half", "upper"));
        static final Map<String, Dynamic<?>> FLOWER_POT_MAP = (Map) DataFixUtils.make(Maps.newHashMap(), $$0 -> {
            $$0.put("minecraft:air0", ExtraDataFixUtils.blockState("minecraft:flower_pot"));
            $$0.put("minecraft:red_flower0", ExtraDataFixUtils.blockState("minecraft:potted_poppy"));
            $$0.put("minecraft:red_flower1", ExtraDataFixUtils.blockState("minecraft:potted_blue_orchid"));
            $$0.put("minecraft:red_flower2", ExtraDataFixUtils.blockState("minecraft:potted_allium"));
            $$0.put("minecraft:red_flower3", ExtraDataFixUtils.blockState("minecraft:potted_azure_bluet"));
            $$0.put("minecraft:red_flower4", ExtraDataFixUtils.blockState("minecraft:potted_red_tulip"));
            $$0.put("minecraft:red_flower5", ExtraDataFixUtils.blockState("minecraft:potted_orange_tulip"));
            $$0.put("minecraft:red_flower6", ExtraDataFixUtils.blockState("minecraft:potted_white_tulip"));
            $$0.put("minecraft:red_flower7", ExtraDataFixUtils.blockState("minecraft:potted_pink_tulip"));
            $$0.put("minecraft:red_flower8", ExtraDataFixUtils.blockState("minecraft:potted_oxeye_daisy"));
            $$0.put("minecraft:yellow_flower0", ExtraDataFixUtils.blockState("minecraft:potted_dandelion"));
            $$0.put("minecraft:sapling0", ExtraDataFixUtils.blockState("minecraft:potted_oak_sapling"));
            $$0.put("minecraft:sapling1", ExtraDataFixUtils.blockState("minecraft:potted_spruce_sapling"));
            $$0.put("minecraft:sapling2", ExtraDataFixUtils.blockState("minecraft:potted_birch_sapling"));
            $$0.put("minecraft:sapling3", ExtraDataFixUtils.blockState("minecraft:potted_jungle_sapling"));
            $$0.put("minecraft:sapling4", ExtraDataFixUtils.blockState("minecraft:potted_acacia_sapling"));
            $$0.put("minecraft:sapling5", ExtraDataFixUtils.blockState("minecraft:potted_dark_oak_sapling"));
            $$0.put("minecraft:red_mushroom0", ExtraDataFixUtils.blockState("minecraft:potted_red_mushroom"));
            $$0.put("minecraft:brown_mushroom0", ExtraDataFixUtils.blockState("minecraft:potted_brown_mushroom"));
            $$0.put("minecraft:deadbush0", ExtraDataFixUtils.blockState("minecraft:potted_dead_bush"));
            $$0.put("minecraft:tallgrass2", ExtraDataFixUtils.blockState("minecraft:potted_fern"));
            $$0.put("minecraft:cactus0", ExtraDataFixUtils.blockState("minecraft:potted_cactus"));
        });
        static final Map<String, Dynamic<?>> SKULL_MAP = (Map) DataFixUtils.make(Maps.newHashMap(), $$0 -> {
            mapSkull($$0, 0, "skeleton", "skull");
            mapSkull($$0, 1, "wither_skeleton", "skull");
            mapSkull($$0, 2, "zombie", PartNames.HEAD);
            mapSkull($$0, 3, "player", PartNames.HEAD);
            mapSkull($$0, 4, "creeper", PartNames.HEAD);
            mapSkull($$0, 5, "dragon", PartNames.HEAD);
        });
        static final Map<String, Dynamic<?>> DOOR_MAP = (Map) DataFixUtils.make(Maps.newHashMap(), $$0 -> {
            mapDoor($$0, "oak_door");
            mapDoor($$0, "iron_door");
            mapDoor($$0, "spruce_door");
            mapDoor($$0, "birch_door");
            mapDoor($$0, "jungle_door");
            mapDoor($$0, "acacia_door");
            mapDoor($$0, "dark_oak_door");
        });
        static final Map<String, Dynamic<?>> NOTE_BLOCK_MAP = (Map) DataFixUtils.make(Maps.newHashMap(), $$0 -> {
            for (int $$1 = 0; $$1 < 26; $$1++) {
                $$0.put("true" + $$1, ExtraDataFixUtils.blockState("minecraft:note_block", Map.of("powered", SnbtOperations.BUILTIN_TRUE, "note", String.valueOf($$1))));
                $$0.put("false" + $$1, ExtraDataFixUtils.blockState("minecraft:note_block", Map.of("powered", SnbtOperations.BUILTIN_FALSE, "note", String.valueOf($$1))));
            }
        });
        private static final Int2ObjectMap<String> DYE_COLOR_MAP = (Int2ObjectMap) DataFixUtils.make(new Int2ObjectOpenHashMap(), $$0 -> {
            $$0.put(0, "white");
            $$0.put(1, "orange");
            $$0.put(2, "magenta");
            $$0.put(3, "light_blue");
            $$0.put(4, "yellow");
            $$0.put(5, "lime");
            $$0.put(6, "pink");
            $$0.put(7, "gray");
            $$0.put(8, "light_gray");
            $$0.put(9, "cyan");
            $$0.put(10, "purple");
            $$0.put(11, "blue");
            $$0.put(12, "brown");
            $$0.put(13, "green");
            $$0.put(14, "red");
            $$0.put(15, "black");
        });
        static final Map<String, Dynamic<?>> BED_BLOCK_MAP = (Map) DataFixUtils.make(Maps.newHashMap(), $$0 -> {
            ObjectIterator it = DYE_COLOR_MAP.int2ObjectEntrySet().iterator();
            while (it.hasNext()) {
                Int2ObjectMap.Entry<String> $$1 = (Int2ObjectMap.Entry) it.next();
                if (!Objects.equals($$1.getValue(), "red")) {
                    addBeds($$0, $$1.getIntKey(), (String) $$1.getValue());
                }
            }
        });
        static final Map<String, Dynamic<?>> BANNER_BLOCK_MAP = (Map) DataFixUtils.make(Maps.newHashMap(), $$0 -> {
            ObjectIterator it = DYE_COLOR_MAP.int2ObjectEntrySet().iterator();
            while (it.hasNext()) {
                Int2ObjectMap.Entry<String> $$1 = (Int2ObjectMap.Entry) it.next();
                if (!Objects.equals($$1.getValue(), "white")) {
                    addBanners($$0, 15 - $$1.getIntKey(), (String) $$1.getValue());
                }
            }
        });
        static final Dynamic<?> AIR;

        private MappingConstants() {
        }

        static {
            FIX.set(2);
            FIX.set(3);
            FIX.set(110);
            FIX.set(140);
            FIX.set(TamableAnimal.TELEPORT_WHEN_DISTANCE_IS_SQ);
            FIX.set(25);
            FIX.set(86);
            FIX.set(26);
            FIX.set(176);
            FIX.set(177);
            FIX.set(175);
            FIX.set(64);
            FIX.set(71);
            FIX.set(193);
            FIX.set(194);
            FIX.set(195);
            FIX.set(196);
            FIX.set(197);
            VIRTUAL.set(54);
            VIRTUAL.set(146);
            VIRTUAL.set(25);
            VIRTUAL.set(26);
            VIRTUAL.set(51);
            VIRTUAL.set(53);
            VIRTUAL.set(67);
            VIRTUAL.set(108);
            VIRTUAL.set(109);
            VIRTUAL.set(BookEditScreen.TEXT_WIDTH);
            VIRTUAL.set(128);
            VIRTUAL.set(134);
            VIRTUAL.set(135);
            VIRTUAL.set(136);
            VIRTUAL.set(156);
            VIRTUAL.set(163);
            VIRTUAL.set(164);
            VIRTUAL.set(180);
            VIRTUAL.set(203);
            VIRTUAL.set(55);
            VIRTUAL.set(85);
            VIRTUAL.set(AdvancementsScreen.WINDOW_INSIDE_HEIGHT);
            VIRTUAL.set(188);
            VIRTUAL.set(189);
            VIRTUAL.set(190);
            VIRTUAL.set(191);
            VIRTUAL.set(192);
            VIRTUAL.set(93);
            VIRTUAL.set(94);
            VIRTUAL.set(ClientboundGameEventPacket.DEMO_PARAM_HINT_1);
            VIRTUAL.set(ClientboundGameEventPacket.DEMO_PARAM_HINT_2);
            VIRTUAL.set(160);
            VIRTUAL.set(LivingEntity.SADDLE_OFFSET);
            VIRTUAL.set(107);
            VIRTUAL.set(183);
            VIRTUAL.set(184);
            VIRTUAL.set(185);
            VIRTUAL.set(186);
            VIRTUAL.set(187);
            VIRTUAL.set(132);
            VIRTUAL.set(139);
            VIRTUAL.set(199);
            AIR = ExtraDataFixUtils.blockState(JigsawBlockEntity.DEFAULT_FINAL_STATE);
        }

        private static void mapSkull(Map<String, Dynamic<?>> $$0, int $$1, String $$2, String $$3) {
            $$0.put($$1 + "north", ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_wall_" + $$3, Map.of("facing", "north")));
            $$0.put($$1 + "east", ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_wall_" + $$3, Map.of("facing", "east")));
            $$0.put($$1 + "south", ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_wall_" + $$3, Map.of("facing", "south")));
            $$0.put($$1 + "west", ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_wall_" + $$3, Map.of("facing", "west")));
            for (int $$4 = 0; $$4 < 16; $$4++) {
                $$0.put($$1 + $$4, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_" + $$3, Map.of("rotation", String.valueOf($$4))));
            }
        }

        private static void mapDoor(Map<String, Dynamic<?>> $$0, String $$1) {
            String $$2 = "minecraft:" + $$1;
            $$0.put("minecraft:" + $$1 + "eastlowerleftfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "eastlowerleftfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "eastlowerlefttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "eastlowerlefttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "eastlowerrightfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "eastlowerrightfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "eastlowerrighttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "eastlowerrighttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "eastupperleftfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "eastupperleftfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "eastupperlefttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "eastupperlefttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "eastupperrightfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "eastupperrightfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "eastupperrighttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "eastupperrighttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "east", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "northlowerleftfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "northlowerleftfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "northlowerlefttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "northlowerlefttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "northlowerrightfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "northlowerrightfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "northlowerrighttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "northlowerrighttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "northupperleftfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "northupperleftfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "northupperlefttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "northupperlefttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "northupperrightfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "northupperrightfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "northupperrighttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "northupperrighttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "north", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "southlowerleftfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "southlowerleftfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "southlowerlefttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "southlowerlefttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "southlowerrightfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "southlowerrightfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "southlowerrighttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "southlowerrighttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "southupperleftfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "southupperleftfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "southupperlefttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "southupperlefttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "southupperrightfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "southupperrightfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "southupperrighttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "southupperrighttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "south", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "westlowerleftfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "westlowerleftfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "westlowerlefttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "westlowerlefttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "lower", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "westlowerrightfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "westlowerrightfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "westlowerrighttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "westlowerrighttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "lower", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "westupperleftfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "westupperleftfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "westupperlefttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "westupperlefttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "upper", "hinge", "left", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "westupperrightfalsefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "westupperrightfalsetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_FALSE, "powered", SnbtOperations.BUILTIN_TRUE)));
            $$0.put("minecraft:" + $$1 + "westupperrighttruefalse", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_FALSE)));
            $$0.put("minecraft:" + $$1 + "westupperrighttruetrue", ExtraDataFixUtils.blockState($$2, Map.of("facing", "west", "half", "upper", "hinge", "right", "open", SnbtOperations.BUILTIN_TRUE, "powered", SnbtOperations.BUILTIN_TRUE)));
        }

        private static void addBeds(Map<String, Dynamic<?>> $$0, int $$1, String $$2) {
            $$0.put("southfalsefoot" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "south", "occupied", SnbtOperations.BUILTIN_FALSE, "part", "foot")));
            $$0.put("westfalsefoot" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "west", "occupied", SnbtOperations.BUILTIN_FALSE, "part", "foot")));
            $$0.put("northfalsefoot" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "north", "occupied", SnbtOperations.BUILTIN_FALSE, "part", "foot")));
            $$0.put("eastfalsefoot" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "east", "occupied", SnbtOperations.BUILTIN_FALSE, "part", "foot")));
            $$0.put("southfalsehead" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "south", "occupied", SnbtOperations.BUILTIN_FALSE, "part", PartNames.HEAD)));
            $$0.put("westfalsehead" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "west", "occupied", SnbtOperations.BUILTIN_FALSE, "part", PartNames.HEAD)));
            $$0.put("northfalsehead" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "north", "occupied", SnbtOperations.BUILTIN_FALSE, "part", PartNames.HEAD)));
            $$0.put("eastfalsehead" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "east", "occupied", SnbtOperations.BUILTIN_FALSE, "part", PartNames.HEAD)));
            $$0.put("southtruehead" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "south", "occupied", SnbtOperations.BUILTIN_TRUE, "part", PartNames.HEAD)));
            $$0.put("westtruehead" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "west", "occupied", SnbtOperations.BUILTIN_TRUE, "part", PartNames.HEAD)));
            $$0.put("northtruehead" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "north", "occupied", SnbtOperations.BUILTIN_TRUE, "part", PartNames.HEAD)));
            $$0.put("easttruehead" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_bed", Map.of("facing", "east", "occupied", SnbtOperations.BUILTIN_TRUE, "part", PartNames.HEAD)));
        }

        private static void addBanners(Map<String, Dynamic<?>> $$0, int $$1, String $$2) {
            for (int $$3 = 0; $$3 < 16; $$3++) {
                $$0.put($$3 + "_" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_banner", Map.of("rotation", String.valueOf($$3))));
            }
            $$0.put("north_" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_wall_banner", Map.of("facing", "north")));
            $$0.put("south_" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_wall_banner", Map.of("facing", "south")));
            $$0.put("west_" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_wall_banner", Map.of("facing", "west")));
            $$0.put("east_" + $$1, ExtraDataFixUtils.blockState("minecraft:" + $$2 + "_wall_banner", Map.of("facing", "east")));
        }
    }

    public static String getName(Dynamic<?> $$0) {
        return $$0.get(StateHolder.NAME_TAG).asString("");
    }

    public static String getProperty(Dynamic<?> $$0, String $$1) {
        return $$0.get(StateHolder.PROPERTIES_TAG).get($$1).asString("");
    }

    public static int idFor(CrudeIncrementalIntIdentityHashBiMap<Dynamic<?>> $$0, Dynamic<?> $$1) {
        int $$2 = $$0.getId($$1);
        if ($$2 == -1) {
            $$2 = $$0.add($$1);
        }
        return $$2;
    }

    private Dynamic<?> fix(Dynamic<?> $$0) {
        Optional<? extends Dynamic<?>> $$1 = $$0.get("Level").result();
        if ($$1.isPresent() && ((Dynamic) $$1.get()).get("Sections").asStreamOpt().result().isPresent()) {
            return $$0.set("Level", new UpgradeChunk((Dynamic) $$1.get()).write());
        }
        return $$0;
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.CHUNK);
        Type<?> $$1 = getOutputSchema().getType(References.CHUNK);
        return writeFixAndRead("ChunkPalettedStorageFix", $$0, $$1, this::fix);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$Section.class */
    static class Section {
        private final Dynamic<?> section;
        private final boolean hasData;
        public final int y;
        private final CrudeIncrementalIntIdentityHashBiMap<Dynamic<?>> palette = CrudeIncrementalIntIdentityHashBiMap.create(32);
        final Int2ObjectMap<IntList> toFix = new Int2ObjectLinkedOpenHashMap();
        final IntList update = new IntArrayList();
        private final Set<Dynamic<?>> seen = Sets.newIdentityHashSet();
        private final int[] buffer = new int[4096];
        private final List<Dynamic<?>> listTag = Lists.newArrayList();

        public Section(Dynamic<?> $$0) {
            this.section = $$0;
            this.y = $$0.get("Y").asInt(0);
            this.hasData = $$0.get("Blocks").result().isPresent();
        }

        public Dynamic<?> getBlock(int $$0) {
            if ($$0 < 0 || $$0 > 4095) {
                return MappingConstants.AIR;
            }
            Dynamic<?> $$1 = this.palette.byId(this.buffer[$$0]);
            return $$1 == null ? MappingConstants.AIR : $$1;
        }

        public void setBlock(int $$0, Dynamic<?> $$1) {
            if (this.seen.add($$1)) {
                this.listTag.add("%%FILTER_ME%%".equals(ChunkPalettedStorageFix.getName($$1)) ? MappingConstants.AIR : $$1);
            }
            this.buffer[$$0] = ChunkPalettedStorageFix.idFor(this.palette, $$1);
        }

        public int upgrade(int $$0) {
            if (!this.hasData) {
                return $$0;
            }
            ByteBuffer $$1 = (ByteBuffer) this.section.get("Blocks").asByteBufferOpt().result().get();
            DataLayer $$2 = (DataLayer) this.section.get(LevelStorageSource.TAG_DATA).asByteBufferOpt().map($$02 -> {
                return new DataLayer(DataFixUtils.toArray($$02));
            }).result().orElseGet(DataLayer::new);
            DataLayer $$3 = (DataLayer) this.section.get("Add").asByteBufferOpt().map($$03 -> {
                return new DataLayer(DataFixUtils.toArray($$03));
            }).result().orElseGet(DataLayer::new);
            this.seen.add(MappingConstants.AIR);
            ChunkPalettedStorageFix.idFor(this.palette, MappingConstants.AIR);
            this.listTag.add(MappingConstants.AIR);
            for (int $$4 = 0; $$4 < 4096; $$4++) {
                int $$5 = $$4 & 15;
                int $$6 = ($$4 >> 8) & 15;
                int $$7 = ($$4 >> 4) & 15;
                int $$8 = ($$3.get($$5, $$6, $$7) << 12) | (($$1.get($$4) & 255) << 4) | $$2.get($$5, $$6, $$7);
                if (MappingConstants.FIX.get($$8 >> 4)) {
                    addFix($$8 >> 4, $$4);
                }
                if (MappingConstants.VIRTUAL.get($$8 >> 4)) {
                    int $$9 = ChunkPalettedStorageFix.getSideMask($$5 == 0, $$5 == 15, $$7 == 0, $$7 == 15);
                    if ($$9 == 0) {
                        this.update.add($$4);
                    } else {
                        $$0 |= $$9;
                    }
                }
                setBlock($$4, BlockStateData.getTag($$8));
            }
            return $$0;
        }

        private void addFix(int $$0, int $$1) {
            IntArrayList intArrayList = (IntList) this.toFix.get($$0);
            if (intArrayList == null) {
                intArrayList = new IntArrayList();
                this.toFix.put($$0, intArrayList);
            }
            intArrayList.add($$1);
        }

        public Dynamic<?> write() {
            Dynamic<?> $$0 = this.section;
            if (!this.hasData) {
                return $$0;
            }
            Dynamic<?> $$02 = $$0.set("Palette", $$0.createList(this.listTag.stream()));
            int $$1 = Math.max(4, DataFixUtils.ceillog2(this.seen.size()));
            PackedBitStorage $$2 = new PackedBitStorage($$1, 4096);
            for (int $$3 = 0; $$3 < this.buffer.length; $$3++) {
                $$2.set($$3, this.buffer[$$3]);
            }
            return $$02.set("BlockStates", $$02.createLongList(Arrays.stream($$2.getRaw()))).remove("Blocks").remove(LevelStorageSource.TAG_DATA).remove("Add");
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$UpgradeChunk.class */
    static final class UpgradeChunk {
        private int sides;
        private final Dynamic<?> level;
        private final int x;
        private final int z;
        private final Section[] sections = new Section[16];
        private final Int2ObjectMap<Dynamic<?>> blockEntities = new Int2ObjectLinkedOpenHashMap(16);

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v153 */
        /* JADX WARN: Type inference failed for: r1v67 */
        /* JADX WARN: Type inference failed for: r1v73 */
        public UpgradeChunk(Dynamic<?> dynamic) throws MatchException {
            String str;
            ?? AsInt;
            int iAsInt;
            int iAsInt2;
            this.level = dynamic;
            this.x = dynamic.get(SerializableChunkData.X_POS_TAG).asInt(0) << 4;
            this.z = dynamic.get(SerializableChunkData.Z_POS_TAG).asInt(0) << 4;
            dynamic.get("TileEntities").asStreamOpt().ifSuccess($$0 -> {
                $$0.forEach($$0 -> {
                    int $$1 = ($$0.get("x").asInt(0) - this.x) & 15;
                    int $$2 = $$0.get("y").asInt(0);
                    int $$3 = ($$0.get("z").asInt(0) - this.z) & 15;
                    int $$4 = ($$2 << 8) | ($$3 << 4) | $$1;
                    if (this.blockEntities.put($$4, $$0) != null) {
                        ChunkPalettedStorageFix.LOGGER.warn("In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]", new Object[]{Integer.valueOf(this.x), Integer.valueOf(this.z), Integer.valueOf($$1), Integer.valueOf($$2), Integer.valueOf($$3)});
                    }
                });
            });
            boolean zAsBoolean = dynamic.get("convertedFromAlphaFormat").asBoolean(false);
            dynamic.get("Sections").asStreamOpt().ifSuccess($$02 -> {
                $$02.forEach($$02 -> {
                    Section $$1 = new Section($$02);
                    this.sides = $$1.upgrade(this.sides);
                    this.sections[$$1.y] = $$1;
                });
            });
            for (Section section : this.sections) {
                if (section != null) {
                    ObjectIterator it = section.toFix.int2ObjectEntrySet().iterator();
                    while (it.hasNext()) {
                        Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry) it.next();
                        int i = section.y << 12;
                        switch (entry.getIntKey()) {
                            case 2:
                                IntListIterator it2 = ((IntList) entry.getValue()).iterator();
                                while (it2.hasNext()) {
                                    int iIntValue = ((Integer) it2.next()).intValue() | i;
                                    if ("minecraft:grass_block".equals(ChunkPalettedStorageFix.getName(getBlock(iIntValue)))) {
                                        String name = ChunkPalettedStorageFix.getName(getBlock(relative(iIntValue, Direction.UP)));
                                        if ("minecraft:snow".equals(name) || "minecraft:snow_layer".equals(name)) {
                                            setBlock(iIntValue, MappingConstants.SNOWY_GRASS);
                                        }
                                    }
                                }
                                break;
                            case 3:
                                IntListIterator it3 = ((IntList) entry.getValue()).iterator();
                                while (it3.hasNext()) {
                                    int iIntValue2 = ((Integer) it3.next()).intValue() | i;
                                    if ("minecraft:podzol".equals(ChunkPalettedStorageFix.getName(getBlock(iIntValue2)))) {
                                        String name2 = ChunkPalettedStorageFix.getName(getBlock(relative(iIntValue2, Direction.UP)));
                                        if ("minecraft:snow".equals(name2) || "minecraft:snow_layer".equals(name2)) {
                                            setBlock(iIntValue2, MappingConstants.SNOWY_PODZOL);
                                        }
                                    }
                                }
                                break;
                            case 25:
                                IntListIterator it4 = ((IntList) entry.getValue()).iterator();
                                while (it4.hasNext()) {
                                    int iIntValue3 = ((Integer) it4.next()).intValue() | i;
                                    Dynamic<?> dynamicRemoveBlockEntity = removeBlockEntity(iIntValue3);
                                    if (dynamicRemoveBlockEntity != null) {
                                        setBlock(iIntValue3, MappingConstants.NOTE_BLOCK_MAP.getOrDefault(Boolean.toString(dynamicRemoveBlockEntity.get("powered").asBoolean(false)) + ((byte) Math.min(Math.max(dynamicRemoveBlockEntity.get("note").asInt(0), 0), 24)), MappingConstants.NOTE_BLOCK_MAP.get("false0")));
                                    }
                                }
                                break;
                            case 26:
                                IntListIterator it5 = ((IntList) entry.getValue()).iterator();
                                while (it5.hasNext()) {
                                    int iIntValue4 = ((Integer) it5.next()).intValue() | i;
                                    Dynamic<?> blockEntity = getBlockEntity(iIntValue4);
                                    Dynamic<?> block = getBlock(iIntValue4);
                                    if (blockEntity != null && (iAsInt2 = blockEntity.get("color").asInt(0)) != 14 && iAsInt2 >= 0 && iAsInt2 < 16) {
                                        String str2 = ChunkPalettedStorageFix.getProperty(block, "facing") + ChunkPalettedStorageFix.getProperty(block, "occupied") + ChunkPalettedStorageFix.getProperty(block, "part") + iAsInt2;
                                        if (MappingConstants.BED_BLOCK_MAP.containsKey(str2)) {
                                            setBlock(iIntValue4, MappingConstants.BED_BLOCK_MAP.get(str2));
                                        }
                                    }
                                }
                                break;
                            case 64:
                            case InputConstants.KEY_G /* 71 */:
                            case 193:
                            case 194:
                            case 195:
                            case 196:
                            case 197:
                                IntListIterator it6 = ((IntList) entry.getValue()).iterator();
                                while (it6.hasNext()) {
                                    int iIntValue5 = ((Integer) it6.next()).intValue() | i;
                                    if (ChunkPalettedStorageFix.getName(getBlock(iIntValue5)).endsWith("_door")) {
                                        Dynamic<?> block2 = getBlock(iIntValue5);
                                        if ("lower".equals(ChunkPalettedStorageFix.getProperty(block2, "half"))) {
                                            int iRelative = relative(iIntValue5, Direction.UP);
                                            Dynamic<?> block3 = getBlock(iRelative);
                                            String name3 = ChunkPalettedStorageFix.getName(block2);
                                            if (name3.equals(ChunkPalettedStorageFix.getName(block3))) {
                                                String property = ChunkPalettedStorageFix.getProperty(block2, "facing");
                                                String property2 = ChunkPalettedStorageFix.getProperty(block2, "open");
                                                String property3 = zAsBoolean ? "left" : ChunkPalettedStorageFix.getProperty(block3, "hinge");
                                                String property4 = zAsBoolean ? SnbtOperations.BUILTIN_FALSE : ChunkPalettedStorageFix.getProperty(block3, "powered");
                                                setBlock(iIntValue5, MappingConstants.DOOR_MAP.get(name3 + property + "lower" + property3 + property2 + property4));
                                                setBlock(iRelative, MappingConstants.DOOR_MAP.get(name3 + property + "upper" + property3 + property2 + property4));
                                            }
                                        }
                                    }
                                }
                                break;
                            case InputConstants.KEY_V /* 86 */:
                                IntListIterator it7 = ((IntList) entry.getValue()).iterator();
                                while (it7.hasNext()) {
                                    int iIntValue6 = ((Integer) it7.next()).intValue() | i;
                                    if ("minecraft:carved_pumpkin".equals(ChunkPalettedStorageFix.getName(getBlock(iIntValue6)))) {
                                        String name4 = ChunkPalettedStorageFix.getName(getBlock(relative(iIntValue6, Direction.DOWN)));
                                        if ("minecraft:grass_block".equals(name4) || "minecraft:dirt".equals(name4)) {
                                            setBlock(iIntValue6, MappingConstants.PUMPKIN);
                                        }
                                    }
                                }
                                break;
                            case 110:
                                IntListIterator it8 = ((IntList) entry.getValue()).iterator();
                                while (it8.hasNext()) {
                                    int iIntValue7 = ((Integer) it8.next()).intValue() | i;
                                    if ("minecraft:mycelium".equals(ChunkPalettedStorageFix.getName(getBlock(iIntValue7)))) {
                                        String name5 = ChunkPalettedStorageFix.getName(getBlock(relative(iIntValue7, Direction.UP)));
                                        if ("minecraft:snow".equals(name5) || "minecraft:snow_layer".equals(name5)) {
                                            setBlock(iIntValue7, MappingConstants.SNOWY_MYCELIUM);
                                        }
                                    }
                                }
                                break;
                            case 140:
                                IntListIterator it9 = ((IntList) entry.getValue()).iterator();
                                while (it9.hasNext()) {
                                    int iIntValue8 = ((Integer) it9.next()).intValue() | i;
                                    Dynamic<?> dynamicRemoveBlockEntity2 = removeBlockEntity(iIntValue8);
                                    if (dynamicRemoveBlockEntity2 != null) {
                                        setBlock(iIntValue8, MappingConstants.FLOWER_POT_MAP.getOrDefault(dynamicRemoveBlockEntity2.get("Item").asString("") + dynamicRemoveBlockEntity2.get(LevelStorageSource.TAG_DATA).asInt(0), MappingConstants.FLOWER_POT_MAP.get("minecraft:air0")));
                                    }
                                }
                                break;
                            case TamableAnimal.TELEPORT_WHEN_DISTANCE_IS_SQ /* 144 */:
                                IntListIterator it10 = ((IntList) entry.getValue()).iterator();
                                while (it10.hasNext()) {
                                    int iIntValue9 = ((Integer) it10.next()).intValue() | i;
                                    Dynamic<?> blockEntity2 = getBlockEntity(iIntValue9);
                                    if (blockEntity2 != null) {
                                        String strValueOf = String.valueOf(blockEntity2.get("SkullType").asInt(0));
                                        String property5 = ChunkPalettedStorageFix.getProperty(getBlock(iIntValue9), "facing");
                                        if ("up".equals(property5) || "down".equals(property5)) {
                                            str = strValueOf;
                                            AsInt = blockEntity2.get("Rot").asInt(0);
                                        } else {
                                            str = strValueOf;
                                            AsInt = property5;
                                        }
                                        blockEntity2.remove("SkullType");
                                        blockEntity2.remove("facing");
                                        blockEntity2.remove("Rot");
                                        setBlock(iIntValue9, MappingConstants.SKULL_MAP.getOrDefault(str + AsInt, MappingConstants.SKULL_MAP.get("0north")));
                                    }
                                }
                                break;
                            case 175:
                                IntListIterator it11 = ((IntList) entry.getValue()).iterator();
                                while (it11.hasNext()) {
                                    int iIntValue10 = ((Integer) it11.next()).intValue() | i;
                                    if ("upper".equals(ChunkPalettedStorageFix.getProperty(getBlock(iIntValue10), "half"))) {
                                        switch (ChunkPalettedStorageFix.getName(getBlock(relative(iIntValue10, Direction.DOWN)))) {
                                            case "minecraft:sunflower":
                                                setBlock(iIntValue10, MappingConstants.UPPER_SUNFLOWER);
                                                break;
                                            case "minecraft:lilac":
                                                setBlock(iIntValue10, MappingConstants.UPPER_LILAC);
                                                break;
                                            case "minecraft:tall_grass":
                                                setBlock(iIntValue10, MappingConstants.UPPER_TALL_GRASS);
                                                break;
                                            case "minecraft:large_fern":
                                                setBlock(iIntValue10, MappingConstants.UPPER_LARGE_FERN);
                                                break;
                                            case "minecraft:rose_bush":
                                                setBlock(iIntValue10, MappingConstants.UPPER_ROSE_BUSH);
                                                break;
                                            case "minecraft:peony":
                                                setBlock(iIntValue10, MappingConstants.UPPER_PEONY);
                                                break;
                                        }
                                    }
                                }
                                break;
                            case 176:
                            case 177:
                                IntListIterator it12 = ((IntList) entry.getValue()).iterator();
                                while (it12.hasNext()) {
                                    int iIntValue11 = ((Integer) it12.next()).intValue() | i;
                                    Dynamic<?> blockEntity3 = getBlockEntity(iIntValue11);
                                    Dynamic<?> block4 = getBlock(iIntValue11);
                                    if (blockEntity3 != null && (iAsInt = blockEntity3.get("Base").asInt(0)) != 15 && iAsInt >= 0 && iAsInt < 16) {
                                        String str3 = ChunkPalettedStorageFix.getProperty(block4, entry.getIntKey() == 176 ? "rotation" : "facing") + "_" + iAsInt;
                                        if (MappingConstants.BANNER_BLOCK_MAP.containsKey(str3)) {
                                            setBlock(iIntValue11, MappingConstants.BANNER_BLOCK_MAP.get(str3));
                                        }
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        }

        private Dynamic<?> getBlockEntity(int $$0) {
            return (Dynamic) this.blockEntities.get($$0);
        }

        private Dynamic<?> removeBlockEntity(int $$0) {
            return (Dynamic) this.blockEntities.remove($$0);
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        public static int relative(int $$0, Direction $$1) throws MatchException {
            switch ($$1.getAxis()) {
                case X:
                    int $$2 = ($$0 & 15) + $$1.getAxisDirection().getStep();
                    if ($$2 < 0 || $$2 > 15) {
                        return -1;
                    }
                    return ($$0 & (-16)) | $$2;
                case Y:
                    int $$3 = ($$0 >> 8) + $$1.getAxisDirection().getStep();
                    if ($$3 < 0 || $$3 > 255) {
                        return -1;
                    }
                    return ($$0 & 255) | ($$3 << 8);
                case Z:
                    int $$4 = (($$0 >> 4) & 15) + $$1.getAxisDirection().getStep();
                    if ($$4 < 0 || $$4 > 15) {
                        return -1;
                    }
                    return ($$0 & (-241)) | ($$4 << 4);
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }

        private void setBlock(int $$0, Dynamic<?> $$1) {
            Section $$2;
            if ($$0 < 0 || $$0 > 65535 || ($$2 = getSection($$0)) == null) {
                return;
            }
            $$2.setBlock($$0 & 4095, $$1);
        }

        private Section getSection(int $$0) {
            int $$1 = $$0 >> 12;
            if ($$1 < this.sections.length) {
                return this.sections[$$1];
            }
            return null;
        }

        public Dynamic<?> getBlock(int $$0) {
            if ($$0 < 0 || $$0 > 65535) {
                return MappingConstants.AIR;
            }
            Section $$1 = getSection($$0);
            if ($$1 == null) {
                return MappingConstants.AIR;
            }
            return $$1.getBlock($$0 & 4095);
        }

        public Dynamic<?> write() {
            Dynamic<?> $$0;
            Dynamic<?> $$02 = this.level;
            if (this.blockEntities.isEmpty()) {
                $$0 = $$02.remove("TileEntities");
            } else {
                $$0 = $$02.set("TileEntities", $$02.createList(this.blockEntities.values().stream()));
            }
            Dynamic<?> $$1 = $$0.emptyMap();
            List<Dynamic<?>> $$2 = Lists.newArrayList();
            for (Section $$3 : this.sections) {
                if ($$3 != null) {
                    $$2.add($$3.write());
                    $$1 = $$1.set(String.valueOf($$3.y), $$1.createIntList(Arrays.stream($$3.update.toIntArray())));
                }
            }
            Dynamic<?> $$4 = $$0.emptyMap();
            Dynamic<?> $$42 = $$4.set("Sides", $$4.createByte((byte) this.sides)).set("Indices", $$1);
            return $$0.set("UpgradeData", $$42).set("Sections", $$42.createList($$2.stream()));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$DataLayer.class */
    static class DataLayer {
        private static final int SIZE = 2048;
        private static final int NIBBLE_SIZE = 4;
        private final byte[] data;

        public DataLayer() {
            this.data = new byte[2048];
        }

        public DataLayer(byte[] $$0) {
            this.data = $$0;
            if ($$0.length != 2048) {
                throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + $$0.length);
            }
        }

        public int get(int $$0, int $$1, int $$2) {
            int $$3 = getPosition(($$1 << 8) | ($$2 << 4) | $$0);
            if (isFirst(($$1 << 8) | ($$2 << 4) | $$0)) {
                return this.data[$$3] & 15;
            }
            return (this.data[$$3] >> 4) & 15;
        }

        private boolean isFirst(int $$0) {
            return ($$0 & 1) == 0;
        }

        private int getPosition(int $$0) {
            return $$0 >> 1;
        }
    }

    public static int getSideMask(boolean $$0, boolean $$1, boolean $$2, boolean $$3) {
        int $$4 = 0;
        if ($$2) {
            if ($$1) {
                $$4 = 0 | 2;
            } else if ($$0) {
                $$4 = 0 | 128;
            } else {
                $$4 = 0 | 1;
            }
        } else if ($$3) {
            if ($$0) {
                $$4 = 0 | 32;
            } else if ($$1) {
                $$4 = 0 | 8;
            } else {
                $$4 = 0 | 16;
            }
        } else if ($$1) {
            $$4 = 0 | 4;
        } else if ($$0) {
            $$4 = 0 | 64;
        }
        return $$4;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$Direction.class */
    public enum Direction {
        DOWN(AxisDirection.NEGATIVE, Axis.Y),
        UP(AxisDirection.POSITIVE, Axis.Y),
        NORTH(AxisDirection.NEGATIVE, Axis.Z),
        SOUTH(AxisDirection.POSITIVE, Axis.Z),
        WEST(AxisDirection.NEGATIVE, Axis.X),
        EAST(AxisDirection.POSITIVE, Axis.X);

        private final Axis axis;
        private final AxisDirection axisDirection;

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$Direction$Axis.class */
        public enum Axis {
            X,
            Y,
            Z
        }

        Direction(AxisDirection $$0, Axis $$1) {
            this.axis = $$1;
            this.axisDirection = $$0;
        }

        public AxisDirection getAxisDirection() {
            return this.axisDirection;
        }

        public Axis getAxis() {
            return this.axis;
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$Direction$AxisDirection.class */
        public enum AxisDirection {
            POSITIVE(1),
            NEGATIVE(-1);

            private final int step;

            AxisDirection(int $$0) {
                this.step = $$0;
            }

            public int getStep() {
                return this.step;
            }
        }
    }
}

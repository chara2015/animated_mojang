package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.fixes.ChunkProtoTickListFix;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableObject;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkHeightAndBiomeFix.class */
public class ChunkHeightAndBiomeFix extends DataFix {
    public static final String DATAFIXER_CONTEXT_TAG = "__context";
    private static final String NAME = "ChunkHeightAndBiomeFix";
    private static final int OLD_SECTION_COUNT = 16;
    private static final int NEW_SECTION_COUNT = 24;
    private static final int NEW_MIN_SECTION_Y = -4;
    public static final int BLOCKS_PER_SECTION = 4096;
    private static final int LONGS_PER_SECTION = 64;
    private static final int HEIGHTMAP_BITS = 9;
    private static final long HEIGHTMAP_MASK = 511;
    private static final int HEIGHTMAP_OFFSET = 64;
    private static final int BIOME_CONTAINER_LAYER_SIZE = 16;
    private static final int BIOME_CONTAINER_SIZE = 64;
    private static final int BIOME_CONTAINER_TOP_LAYER_OFFSET = 1008;
    public static final String DEFAULT_BIOME = "minecraft:plains";
    private static final String[] HEIGHTMAP_TYPES = {"WORLD_SURFACE_WG", "WORLD_SURFACE", "WORLD_SURFACE_IGNORE_SNOW", "OCEAN_FLOOR_WG", "OCEAN_FLOOR", "MOTION_BLOCKING", "MOTION_BLOCKING_NO_LEAVES"};
    private static final Set<String> STATUS_IS_OR_AFTER_SURFACE = Set.of("surface", "carvers", "liquid_carvers", "features", "light", "spawn", "heightmaps", "full");
    private static final Set<String> STATUS_IS_OR_AFTER_NOISE = Set.of("noise", "surface", "carvers", "liquid_carvers", "features", "light", "spawn", "heightmaps", "full");
    private static final Set<String> BLOCKS_BEFORE_FEATURE_STATUS = Set.of((Object[]) new String[]{JigsawBlockEntity.DEFAULT_FINAL_STATE, "minecraft:basalt", "minecraft:bedrock", "minecraft:blackstone", "minecraft:calcite", "minecraft:cave_air", "minecraft:coarse_dirt", "minecraft:crimson_nylium", "minecraft:dirt", "minecraft:end_stone", "minecraft:grass_block", "minecraft:gravel", "minecraft:ice", "minecraft:lava", "minecraft:mycelium", "minecraft:nether_wart_block", "minecraft:netherrack", "minecraft:orange_terracotta", "minecraft:packed_ice", "minecraft:podzol", "minecraft:powder_snow", "minecraft:red_sand", "minecraft:red_sandstone", "minecraft:sand", "minecraft:sandstone", "minecraft:snow_block", "minecraft:soul_sand", "minecraft:soul_soil", "minecraft:stone", "minecraft:terracotta", "minecraft:warped_nylium", "minecraft:warped_wart_block", ItemPotionFix.DEFAULT, "minecraft:white_terracotta"});
    private static final Int2ObjectMap<String> BIOMES_BY_ID = new Int2ObjectOpenHashMap();

    static {
        BIOMES_BY_ID.put(0, "minecraft:ocean");
        BIOMES_BY_ID.put(1, DEFAULT_BIOME);
        BIOMES_BY_ID.put(2, "minecraft:desert");
        BIOMES_BY_ID.put(3, "minecraft:mountains");
        BIOMES_BY_ID.put(4, "minecraft:forest");
        BIOMES_BY_ID.put(5, "minecraft:taiga");
        BIOMES_BY_ID.put(6, "minecraft:swamp");
        BIOMES_BY_ID.put(7, "minecraft:river");
        BIOMES_BY_ID.put(8, "minecraft:nether_wastes");
        BIOMES_BY_ID.put(9, "minecraft:the_end");
        BIOMES_BY_ID.put(10, "minecraft:frozen_ocean");
        BIOMES_BY_ID.put(11, "minecraft:frozen_river");
        BIOMES_BY_ID.put(12, "minecraft:snowy_tundra");
        BIOMES_BY_ID.put(13, "minecraft:snowy_mountains");
        BIOMES_BY_ID.put(14, "minecraft:mushroom_fields");
        BIOMES_BY_ID.put(15, "minecraft:mushroom_field_shore");
        BIOMES_BY_ID.put(16, "minecraft:beach");
        BIOMES_BY_ID.put(17, "minecraft:desert_hills");
        BIOMES_BY_ID.put(18, "minecraft:wooded_hills");
        BIOMES_BY_ID.put(19, "minecraft:taiga_hills");
        BIOMES_BY_ID.put(20, "minecraft:mountain_edge");
        BIOMES_BY_ID.put(21, "minecraft:jungle");
        BIOMES_BY_ID.put(22, "minecraft:jungle_hills");
        BIOMES_BY_ID.put(23, "minecraft:jungle_edge");
        BIOMES_BY_ID.put(24, "minecraft:deep_ocean");
        BIOMES_BY_ID.put(25, "minecraft:stone_shore");
        BIOMES_BY_ID.put(26, "minecraft:snowy_beach");
        BIOMES_BY_ID.put(27, "minecraft:birch_forest");
        BIOMES_BY_ID.put(28, "minecraft:birch_forest_hills");
        BIOMES_BY_ID.put(29, "minecraft:dark_forest");
        BIOMES_BY_ID.put(30, "minecraft:snowy_taiga");
        BIOMES_BY_ID.put(31, "minecraft:snowy_taiga_hills");
        BIOMES_BY_ID.put(32, "minecraft:giant_tree_taiga");
        BIOMES_BY_ID.put(33, "minecraft:giant_tree_taiga_hills");
        BIOMES_BY_ID.put(34, "minecraft:wooded_mountains");
        BIOMES_BY_ID.put(35, "minecraft:savanna");
        BIOMES_BY_ID.put(36, "minecraft:savanna_plateau");
        BIOMES_BY_ID.put(37, "minecraft:badlands");
        BIOMES_BY_ID.put(38, "minecraft:wooded_badlands_plateau");
        BIOMES_BY_ID.put(39, "minecraft:badlands_plateau");
        BIOMES_BY_ID.put(40, "minecraft:small_end_islands");
        BIOMES_BY_ID.put(41, "minecraft:end_midlands");
        BIOMES_BY_ID.put(42, "minecraft:end_highlands");
        BIOMES_BY_ID.put(43, "minecraft:end_barrens");
        BIOMES_BY_ID.put(44, "minecraft:warm_ocean");
        BIOMES_BY_ID.put(45, "minecraft:lukewarm_ocean");
        BIOMES_BY_ID.put(46, "minecraft:cold_ocean");
        BIOMES_BY_ID.put(47, "minecraft:deep_warm_ocean");
        BIOMES_BY_ID.put(48, "minecraft:deep_lukewarm_ocean");
        BIOMES_BY_ID.put(49, "minecraft:deep_cold_ocean");
        BIOMES_BY_ID.put(50, "minecraft:deep_frozen_ocean");
        BIOMES_BY_ID.put(127, "minecraft:the_void");
        BIOMES_BY_ID.put(129, "minecraft:sunflower_plains");
        BIOMES_BY_ID.put(130, "minecraft:desert_lakes");
        BIOMES_BY_ID.put(131, "minecraft:gravelly_mountains");
        BIOMES_BY_ID.put(132, "minecraft:flower_forest");
        BIOMES_BY_ID.put(133, "minecraft:taiga_mountains");
        BIOMES_BY_ID.put(134, "minecraft:swamp_hills");
        BIOMES_BY_ID.put(140, "minecraft:ice_spikes");
        BIOMES_BY_ID.put(149, "minecraft:modified_jungle");
        BIOMES_BY_ID.put(151, "minecraft:modified_jungle_edge");
        BIOMES_BY_ID.put(155, "minecraft:tall_birch_forest");
        BIOMES_BY_ID.put(156, "minecraft:tall_birch_hills");
        BIOMES_BY_ID.put(157, "minecraft:dark_forest_hills");
        BIOMES_BY_ID.put(158, "minecraft:snowy_taiga_mountains");
        BIOMES_BY_ID.put(160, "minecraft:giant_spruce_taiga");
        BIOMES_BY_ID.put(161, "minecraft:giant_spruce_taiga_hills");
        BIOMES_BY_ID.put(162, "minecraft:modified_gravelly_mountains");
        BIOMES_BY_ID.put(163, "minecraft:shattered_savanna");
        BIOMES_BY_ID.put(164, "minecraft:shattered_savanna_plateau");
        BIOMES_BY_ID.put(165, "minecraft:eroded_badlands");
        BIOMES_BY_ID.put(RecipeBookComponent.IMAGE_HEIGHT, "minecraft:modified_wooded_badlands_plateau");
        BIOMES_BY_ID.put(ChatFormatting.PREFIX_CODE, "minecraft:modified_badlands_plateau");
        BIOMES_BY_ID.put(168, "minecraft:bamboo_jungle");
        BIOMES_BY_ID.put(169, "minecraft:bamboo_jungle_hills");
        BIOMES_BY_ID.put(170, "minecraft:soul_sand_valley");
        BIOMES_BY_ID.put(171, "minecraft:crimson_forest");
        BIOMES_BY_ID.put(172, "minecraft:warped_forest");
        BIOMES_BY_ID.put(173, "minecraft:basalt_deltas");
        BIOMES_BY_ID.put(174, "minecraft:dripstone_caves");
        BIOMES_BY_ID.put(175, "minecraft:lush_caves");
        BIOMES_BY_ID.put(177, "minecraft:meadow");
        BIOMES_BY_ID.put(178, "minecraft:grove");
        BIOMES_BY_ID.put(179, "minecraft:snowy_slopes");
        BIOMES_BY_ID.put(180, "minecraft:snowcapped_peaks");
        BIOMES_BY_ID.put(181, "minecraft:lofty_peaks");
        BIOMES_BY_ID.put(ContextualBarRenderer.WIDTH, "minecraft:stony_peaks");
    }

    public ChunkHeightAndBiomeFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.CHUNK);
        OpticFinder<?> $$1 = $$0.findField("Level");
        OpticFinder<?> $$2 = $$1.type().findField("Sections");
        Schema $$3 = getOutputSchema();
        Type<?> $$4 = $$3.getType(References.CHUNK);
        Type<?> $$5 = $$4.findField("Level").type();
        Type<?> $$6 = $$5.findField("Sections").type();
        return fixTypeEverywhereTyped(NAME, $$0, $$4, $$42 -> {
            return $$42.updateTyped($$1, $$5, $$32 -> {
                Dynamic<?> $$42 = (Dynamic) $$32.get(DSL.remainderFinder());
                OptionalDynamic<?> $$52 = ((Dynamic) $$42.get(DSL.remainderFinder())).get(DATAFIXER_CONTEXT_TAG);
                String $$62 = (String) $$52.get(ChunkRegionIoEvent.Fields.DIMENSION).asString().result().orElse("");
                String $$7 = (String) $$52.get("generator").asString().result().orElse("");
                boolean $$8 = "minecraft:overworld".equals($$62);
                MutableBoolean $$9 = new MutableBoolean();
                int $$10 = $$8 ? NEW_MIN_SECTION_Y : 0;
                Dynamic<?>[] $$11 = getBiomeContainers($$42, $$8, $$10, $$9);
                Dynamic<?> $$12 = makePalettedContainer($$42.createList(Stream.of($$42.createMap(ImmutableMap.of($$42.createString(StateHolder.NAME_TAG), $$42.createString(JigsawBlockEntity.DEFAULT_FINAL_STATE))))));
                Set<String> $$13 = Sets.newHashSet();
                MutableObject<Supplier<ChunkProtoTickListFix.PoorMansPalettedContainer>> $$14 = new MutableObject<>(() -> {
                    return null;
                });
                return $$32.updateTyped($$2, $$6, $$72 -> {
                    IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
                    Dynamic<?> $$92 = (Dynamic) $$72.write().result().orElseThrow(() -> {
                        return new IllegalStateException("Malformed Chunk.Level.Sections");
                    });
                    List<Dynamic<?>> $$102 = (List) $$92.asStream().map($$63 -> {
                        int $$72 = $$63.get("Y").asInt(0);
                        Dynamic<?> $$82 = (Dynamic) DataFixUtils.orElse($$63.get("Palette").result().flatMap($$22 -> {
                            Stream map = $$22.asStream().map($$02 -> {
                                return $$02.get(StateHolder.NAME_TAG).asString(JigsawBlockEntity.DEFAULT_FINAL_STATE);
                            });
                            Objects.requireNonNull($$13);
                            map.forEach((v1) -> {
                                r1.add(v1);
                            });
                            return $$63.get("BlockStates").result().map($$15 -> {
                                return makeOptimizedPalettedContainer($$22, $$15);
                            });
                        }), $$12);
                        Dynamic dynamic = $$63;
                        int $$103 = $$72 - $$10;
                        if ($$103 >= 0 && $$103 < $$11.length) {
                            dynamic = dynamic.set("biomes", $$11[$$103]);
                        }
                        intOpenHashSet.add($$72);
                        if ($$63.get("Y").asInt(Integer.MAX_VALUE) == 0) {
                            $$14.setValue(() -> {
                                List<? extends Dynamic<?>> $$15 = $$82.get(StructureTemplate.PALETTE_TAG).asList(Function.identity());
                                long[] $$23 = $$82.get("data").asLongStream().toArray();
                                return new ChunkProtoTickListFix.PoorMansPalettedContainer($$15, $$23);
                            });
                        }
                        return dynamic.set("block_states", $$82).remove("Palette").remove("BlockStates");
                    }).collect(Collectors.toCollection(ArrayList::new));
                    for (int $$112 = 0; $$112 < $$11.length; $$112++) {
                        int $$122 = $$112 + $$10;
                        if (intOpenHashSet.add($$122)) {
                            Dynamic<?> $$132 = $$42.createMap(Map.of($$42.createString("Y"), $$42.createInt($$122)));
                            $$102.add($$132.set("block_states", $$12).set("biomes", $$11[$$112]));
                        }
                    }
                    return Util.readTypedOrThrow($$6, $$42.createList($$102.stream()));
                }).update(DSL.remainderFinder(), $$53 -> {
                    if ($$8) {
                        $$53 = predictChunkStatusBeforeSurface($$53, $$13);
                    }
                    return updateChunkTag($$53, $$8, $$9.booleanValue(), "minecraft:noise".equals($$7), (Supplier) $$14.get());
                });
            });
        });
    }

    private Dynamic<?> predictChunkStatusBeforeSurface(Dynamic<?> $$0, Set<String> $$1) {
        return $$0.update("Status", $$12 -> {
            String $$2 = $$12.asString("empty");
            if (STATUS_IS_OR_AFTER_SURFACE.contains($$2)) {
                return $$12;
            }
            $$1.remove(JigsawBlockEntity.DEFAULT_FINAL_STATE);
            boolean $$3 = !$$1.isEmpty();
            $$1.removeAll(BLOCKS_BEFORE_FEATURE_STATUS);
            boolean $$4 = !$$1.isEmpty();
            if ($$4) {
                return $$12.createString("liquid_carvers");
            }
            if ("noise".equals($$2) || $$3) {
                return $$12.createString("noise");
            }
            if ("biomes".equals($$2)) {
                return $$12.createString("structure_references");
            }
            return $$12;
        });
    }

    private static Dynamic<?>[] getBiomeContainers(Dynamic<?> $$0, boolean $$1, int $$2, MutableBoolean $$3) {
        Dynamic<?>[] $$4 = new Dynamic[$$1 ? 24 : 16];
        int[] $$5 = (int[]) $$0.get("Biomes").asIntStreamOpt().result().map((v0) -> {
            return v0.toArray();
        }).orElse(null);
        if ($$5 != null && $$5.length == 1536) {
            $$3.setValue(true);
            for (int $$6 = 0; $$6 < 24; $$6++) {
                int $$7 = $$6;
                $$4[$$6] = makeBiomeContainer($$0, $$22 -> {
                    return getOldBiome($$5, ($$7 * 64) + $$22);
                });
            }
        } else if ($$5 != null && $$5.length == 1024) {
            for (int $$8 = 0; $$8 < 16; $$8++) {
                int $$9 = $$8 - $$2;
                int $$10 = $$8;
                $$4[$$9] = makeBiomeContainer($$0, $$23 -> {
                    return getOldBiome($$5, ($$10 * 64) + $$23);
                });
            }
            if ($$1) {
                Dynamic<?> $$11 = makeBiomeContainer($$0, $$12 -> {
                    return getOldBiome($$5, $$12 % 16);
                });
                Dynamic<?> $$122 = makeBiomeContainer($$0, $$13 -> {
                    return getOldBiome($$5, ($$13 % 16) + BIOME_CONTAINER_TOP_LAYER_OFFSET);
                });
                for (int $$132 = 0; $$132 < 4; $$132++) {
                    $$4[$$132] = $$11;
                }
                for (int $$14 = 20; $$14 < 24; $$14++) {
                    $$4[$$14] = $$122;
                }
            }
        } else {
            Arrays.fill($$4, makePalettedContainer($$0.createList(Stream.of($$0.createString(DEFAULT_BIOME)))));
        }
        return $$4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getOldBiome(int[] $$0, int $$1) {
        return $$0[$$1] & 255;
    }

    private static Dynamic<?> updateChunkTag(Dynamic<?> $$0, boolean $$1, boolean $$2, boolean $$3, Supplier<ChunkProtoTickListFix.PoorMansPalettedContainer> $$4) {
        ChunkProtoTickListFix.PoorMansPalettedContainer $$8;
        Dynamic<?> $$02 = $$0.remove("Biomes");
        if (!$$1) {
            return updateCarvingMasks($$02, 16, 0);
        }
        if ($$2) {
            return updateCarvingMasks($$02, 24, 0);
        }
        Dynamic<?> $$03 = updateCarvingMasks(addPaddingEntries(addPaddingEntries(addPaddingEntries(updateHeightmaps($$02), "LiquidsToBeTicked"), "PostProcessing"), "ToBeTicked"), 24, 4).update("UpgradeData", ChunkHeightAndBiomeFix::shiftUpgradeData);
        if (!$$3) {
            return $$03;
        }
        Optional<? extends Dynamic<?>> $$5 = $$03.get("Status").result();
        if ($$5.isPresent()) {
            Dynamic<?> $$6 = $$5.get();
            String $$7 = $$6.asString("");
            if (!"empty".equals($$7)) {
                $$03 = $$03.set("blending_data", $$03.createMap(ImmutableMap.of($$03.createString("old_noise"), $$03.createBoolean(STATUS_IS_OR_AFTER_NOISE.contains($$7)))));
                if (!SharedConstants.DEBUG_DISABLE_BELOW_ZERO_RETROGENERATION && ($$8 = $$4.get()) != null) {
                    BitSet $$9 = new BitSet(256);
                    boolean $$10 = $$7.equals("noise");
                    for (int $$11 = 0; $$11 < 16; $$11++) {
                        for (int $$12 = 0; $$12 < 16; $$12++) {
                            Dynamic<?> $$13 = $$8.get($$12, 0, $$11);
                            boolean $$14 = $$13 != null && "minecraft:bedrock".equals($$13.get(StateHolder.NAME_TAG).asString(""));
                            boolean $$15 = $$13 != null && JigsawBlockEntity.DEFAULT_FINAL_STATE.equals($$13.get(StateHolder.NAME_TAG).asString(""));
                            if ($$15) {
                                $$9.set(($$11 * 16) + $$12);
                            }
                            $$10 |= $$14;
                        }
                    }
                    if ($$10 && $$9.cardinality() != $$9.size()) {
                        Dynamic<?> $$16 = "full".equals($$7) ? $$03.createString("heightmaps") : $$6;
                        Dynamic<?> $$04 = $$03.set("below_zero_retrogen", $$03.createMap(ImmutableMap.of($$03.createString("target_status"), $$16, $$03.createString("missing_bedrock"), $$03.createLongList(LongStream.of($$9.toLongArray())))));
                        $$03 = $$04.set("Status", $$04.createString("empty"));
                    }
                    $$03 = $$03.set(SerializableChunkData.IS_LIGHT_ON_TAG, $$03.createBoolean(false));
                }
            }
        }
        return $$03;
    }

    private static <T> Dynamic<T> shiftUpgradeData(Dynamic<T> $$0) {
        return $$0.update("Indices", $$02 -> {
            Map<Dynamic<?>, Dynamic<?>> $$1 = new HashMap<>();
            $$02.getMapValues().ifSuccess($$12 -> {
                $$12.forEach(($$12, $$2) -> {
                    try {
                        $$12.asString().result().map(Integer::parseInt).ifPresent($$3 -> {
                            int $$4 = $$3.intValue() - NEW_MIN_SECTION_Y;
                            $$1.put($$12.createString(Integer.toString($$4)), $$2);
                        });
                    } catch (NumberFormatException e) {
                    }
                });
            });
            return $$02.createMap($$1);
        });
    }

    private static Dynamic<?> updateCarvingMasks(Dynamic<?> $$0, int $$1, int $$2) {
        Dynamic<?> $$3 = $$0.get("CarvingMasks").orElseEmptyMap();
        return $$0.set("CarvingMasks", $$3.updateMapValues($$32 -> {
            long[] $$4 = BitSet.valueOf(((Dynamic) $$32.getSecond()).asByteBuffer().array()).toLongArray();
            long[] $$5 = new long[64 * $$1];
            System.arraycopy($$4, 0, $$5, 64 * $$2, $$4.length);
            return Pair.of((Dynamic) $$32.getFirst(), $$0.createLongList(LongStream.of($$5)));
        }));
    }

    private static Dynamic<?> addPaddingEntries(Dynamic<?> $$0, String $$1) {
        List<Dynamic<?>> $$2 = (List) $$0.get($$1).orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
        if ($$2.size() == 24) {
            return $$0;
        }
        Dynamic<?> $$3 = $$0.emptyList();
        for (int $$4 = 0; $$4 < 4; $$4++) {
            $$2.add(0, $$3);
            $$2.add($$3);
        }
        return $$0.set($$1, $$0.createList($$2.stream()));
    }

    private static Dynamic<?> updateHeightmaps(Dynamic<?> $$0) {
        return $$0.update(SerializableChunkData.HEIGHTMAPS_TAG, $$02 -> {
            for (String $$1 : HEIGHTMAP_TYPES) {
                $$02 = $$02.update($$1, ChunkHeightAndBiomeFix::getFixedHeightmap);
            }
            return $$02;
        });
    }

    private static Dynamic<?> getFixedHeightmap(Dynamic<?> $$0) {
        return $$0.createLongList($$0.asLongStream().map($$02 -> {
            long jMin;
            long $$1 = 0;
            for (int $$2 = 0; $$2 + 9 <= 64; $$2 += 9) {
                long $$3 = ($$02 >> $$2) & HEIGHTMAP_MASK;
                if ($$3 == 0) {
                    jMin = 0;
                } else {
                    jMin = Math.min($$3 + 64, HEIGHTMAP_MASK);
                }
                long $$5 = jMin;
                $$1 |= $$5 << $$2;
            }
            return $$1;
        }));
    }

    private static Dynamic<?> makeBiomeContainer(Dynamic<?> $$0, Int2IntFunction $$1) {
        Int2IntLinkedOpenHashMap int2IntLinkedOpenHashMap = new Int2IntLinkedOpenHashMap();
        for (int $$3 = 0; $$3 < 64; $$3++) {
            int $$4 = $$1.applyAsInt($$3);
            if (!int2IntLinkedOpenHashMap.containsKey($$4)) {
                int2IntLinkedOpenHashMap.put($$4, int2IntLinkedOpenHashMap.size());
            }
        }
        Dynamic<?> $$5 = $$0.createList(int2IntLinkedOpenHashMap.keySet().stream().map($$12 -> {
            return $$0.createString((String) BIOMES_BY_ID.getOrDefault($$12.intValue(), DEFAULT_BIOME));
        }));
        int $$6 = ceillog2(int2IntLinkedOpenHashMap.size());
        if ($$6 == 0) {
            return makePalettedContainer($$5);
        }
        int $$7 = 64 / $$6;
        int $$8 = ((64 + $$7) - 1) / $$7;
        long[] $$9 = new long[$$8];
        int $$10 = 0;
        int $$11 = 0;
        for (int $$122 = 0; $$122 < 64; $$122++) {
            int $$13 = $$1.applyAsInt($$122);
            int i = $$10;
            $$9[i] = $$9[i] | (((long) int2IntLinkedOpenHashMap.get($$13)) << $$11);
            $$11 += $$6;
            if ($$11 + $$6 > 64) {
                $$10++;
                $$11 = 0;
            }
        }
        Dynamic<?> $$14 = $$0.createLongList(Arrays.stream($$9));
        return makePalettedContainer($$5, $$14);
    }

    private static Dynamic<?> makePalettedContainer(Dynamic<?> $$0) {
        return $$0.createMap(ImmutableMap.of($$0.createString(StructureTemplate.PALETTE_TAG), $$0));
    }

    private static Dynamic<?> makePalettedContainer(Dynamic<?> $$0, Dynamic<?> $$1) {
        return $$0.createMap(ImmutableMap.of($$0.createString(StructureTemplate.PALETTE_TAG), $$0, $$0.createString("data"), $$1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Dynamic<?> makeOptimizedPalettedContainer(Dynamic<?> $$0, Dynamic<?> $$1) {
        List<Dynamic<?>> $$2 = (List) $$0.asStream().collect(Collectors.toCollection(ArrayList::new));
        if ($$2.size() == 1) {
            return makePalettedContainer($$0);
        }
        return makePalettedContainer(padPaletteEntries($$0, $$1, $$2), $$1);
    }

    private static Dynamic<?> padPaletteEntries(Dynamic<?> $$0, Dynamic<?> $$1, List<Dynamic<?>> $$2) {
        long $$3 = $$1.asLongStream().count() * 64;
        long $$4 = $$3 / NoiseRouterData.ISLAND_CHUNK_DISTANCE_SQR;
        int $$5 = $$2.size();
        int $$6 = ceillog2($$5);
        if ($$4 > $$6) {
            Dynamic<?> $$7 = $$0.createMap(ImmutableMap.of($$0.createString(StateHolder.NAME_TAG), $$0.createString(JigsawBlockEntity.DEFAULT_FINAL_STATE)));
            int $$8 = (1 << ((int) ($$4 - 1))) + 1;
            int $$9 = $$8 - $$5;
            for (int $$10 = 0; $$10 < $$9; $$10++) {
                $$2.add($$7);
            }
            return $$0.createList($$2.stream());
        }
        return $$0;
    }

    public static int ceillog2(int $$0) {
        if ($$0 == 0) {
            return 0;
        }
        return (int) Math.ceil(Math.log($$0) / Math.log(2.0d));
    }
}

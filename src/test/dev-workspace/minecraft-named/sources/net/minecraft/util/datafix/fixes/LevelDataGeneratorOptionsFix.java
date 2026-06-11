package net.minecraft.util.datafix.fixes;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.util.LenientJsonParser;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/LevelDataGeneratorOptionsFix.class */
public class LevelDataGeneratorOptionsFix extends DataFix {
    static final Map<String, String> MAP = (Map) Util.make(Maps.newHashMap(), $$0 -> {
        $$0.put("0", "minecraft:ocean");
        $$0.put("1", ChunkHeightAndBiomeFix.DEFAULT_BIOME);
        $$0.put("2", "minecraft:desert");
        $$0.put("3", "minecraft:mountains");
        $$0.put("4", "minecraft:forest");
        $$0.put("5", "minecraft:taiga");
        $$0.put("6", "minecraft:swamp");
        $$0.put("7", "minecraft:river");
        $$0.put("8", "minecraft:nether");
        $$0.put("9", "minecraft:the_end");
        $$0.put("10", "minecraft:frozen_ocean");
        $$0.put("11", "minecraft:frozen_river");
        $$0.put("12", "minecraft:snowy_tundra");
        $$0.put("13", "minecraft:snowy_mountains");
        $$0.put("14", "minecraft:mushroom_fields");
        $$0.put("15", "minecraft:mushroom_field_shore");
        $$0.put("16", "minecraft:beach");
        $$0.put("17", "minecraft:desert_hills");
        $$0.put("18", "minecraft:wooded_hills");
        $$0.put("19", "minecraft:taiga_hills");
        $$0.put("20", "minecraft:mountain_edge");
        $$0.put("21", "minecraft:jungle");
        $$0.put("22", "minecraft:jungle_hills");
        $$0.put("23", "minecraft:jungle_edge");
        $$0.put("24", "minecraft:deep_ocean");
        $$0.put("25", "minecraft:stone_shore");
        $$0.put("26", "minecraft:snowy_beach");
        $$0.put("27", "minecraft:birch_forest");
        $$0.put("28", "minecraft:birch_forest_hills");
        $$0.put("29", "minecraft:dark_forest");
        $$0.put("30", "minecraft:snowy_taiga");
        $$0.put("31", "minecraft:snowy_taiga_hills");
        $$0.put("32", "minecraft:giant_tree_taiga");
        $$0.put("33", "minecraft:giant_tree_taiga_hills");
        $$0.put("34", "minecraft:wooded_mountains");
        $$0.put("35", "minecraft:savanna");
        $$0.put("36", "minecraft:savanna_plateau");
        $$0.put("37", "minecraft:badlands");
        $$0.put("38", "minecraft:wooded_badlands_plateau");
        $$0.put("39", "minecraft:badlands_plateau");
        $$0.put("40", "minecraft:small_end_islands");
        $$0.put("41", "minecraft:end_midlands");
        $$0.put("42", "minecraft:end_highlands");
        $$0.put("43", "minecraft:end_barrens");
        $$0.put("44", "minecraft:warm_ocean");
        $$0.put("45", "minecraft:lukewarm_ocean");
        $$0.put("46", "minecraft:cold_ocean");
        $$0.put("47", "minecraft:deep_warm_ocean");
        $$0.put("48", "minecraft:deep_lukewarm_ocean");
        $$0.put("49", "minecraft:deep_cold_ocean");
        $$0.put("50", "minecraft:deep_frozen_ocean");
        $$0.put("127", "minecraft:the_void");
        $$0.put("129", "minecraft:sunflower_plains");
        $$0.put("130", "minecraft:desert_lakes");
        $$0.put("131", "minecraft:gravelly_mountains");
        $$0.put("132", "minecraft:flower_forest");
        $$0.put("133", "minecraft:taiga_mountains");
        $$0.put("134", "minecraft:swamp_hills");
        $$0.put("140", "minecraft:ice_spikes");
        $$0.put("149", "minecraft:modified_jungle");
        $$0.put("151", "minecraft:modified_jungle_edge");
        $$0.put("155", "minecraft:tall_birch_forest");
        $$0.put("156", "minecraft:tall_birch_hills");
        $$0.put("157", "minecraft:dark_forest_hills");
        $$0.put("158", "minecraft:snowy_taiga_mountains");
        $$0.put("160", "minecraft:giant_spruce_taiga");
        $$0.put("161", "minecraft:giant_spruce_taiga_hills");
        $$0.put("162", "minecraft:modified_gravelly_mountains");
        $$0.put("163", "minecraft:shattered_savanna");
        $$0.put("164", "minecraft:shattered_savanna_plateau");
        $$0.put("165", "minecraft:eroded_badlands");
        $$0.put("166", "minecraft:modified_wooded_badlands_plateau");
        $$0.put("167", "minecraft:modified_badlands_plateau");
    });
    public static final String GENERATOR_OPTIONS = "generatorOptions";

    public LevelDataGeneratorOptionsFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getOutputSchema().getType(References.LEVEL);
        return fixTypeEverywhereTyped("LevelDataGeneratorOptionsFix", getInputSchema().getType(References.LEVEL), $$0, $$1 -> {
            return Util.writeAndReadTypedOrThrow($$1, $$0, $$02 -> {
                Optional<String> $$1 = $$02.get(GENERATOR_OPTIONS).asString().result();
                if ("flat".equalsIgnoreCase($$02.get("generatorName").asString(""))) {
                    String $$2 = $$1.orElse("");
                    return $$02.set(GENERATOR_OPTIONS, convert($$2, $$02.getOps()));
                }
                if ("buffet".equalsIgnoreCase($$02.get("generatorName").asString("")) && $$1.isPresent()) {
                    JsonElement $$3 = LenientJsonParser.parse($$1.get());
                    return $$02.set(GENERATOR_OPTIONS, new Dynamic(JsonOps.INSTANCE, $$3).convert($$02.getOps()));
                }
                return $$02;
            });
        });
    }

    private static <T> Dynamic<T> convert(String str, DynamicOps<T> dynamicOps) {
        List<Pair<Integer, String>> listNewArrayList;
        Iterator<T> it = Splitter.on(';').split(str).iterator();
        String orDefault = ChunkHeightAndBiomeFix.DEFAULT_BIOME;
        HashMap mapNewHashMap = Maps.newHashMap();
        if (!str.isEmpty() && it.hasNext()) {
            listNewArrayList = getLayersInfoFromString((String) it.next());
            if (!listNewArrayList.isEmpty()) {
                if (it.hasNext()) {
                    orDefault = MAP.getOrDefault(it.next(), ChunkHeightAndBiomeFix.DEFAULT_BIOME);
                }
                if (it.hasNext()) {
                    for (String str2 : ((String) it.next()).toLowerCase(Locale.ROOT).split(",")) {
                        String[] strArrSplit = str2.split("\\(", 2);
                        if (!strArrSplit[0].isEmpty()) {
                            mapNewHashMap.put(strArrSplit[0], Maps.newHashMap());
                            if (strArrSplit.length > 1 && strArrSplit[1].endsWith(")") && strArrSplit[1].length() > 1) {
                                for (String str3 : strArrSplit[1].substring(0, strArrSplit[1].length() - 1).split(" ")) {
                                    String[] strArrSplit2 = str3.split("=", 2);
                                    if (strArrSplit2.length == 2) {
                                        ((Map) mapNewHashMap.get(strArrSplit[0])).put(strArrSplit2[0], strArrSplit2[1]);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    mapNewHashMap.put("village", Maps.newHashMap());
                }
            }
        } else {
            listNewArrayList = Lists.newArrayList();
            listNewArrayList.add(Pair.of(1, "minecraft:bedrock"));
            listNewArrayList.add(Pair.of(2, "minecraft:dirt"));
            listNewArrayList.add(Pair.of(1, "minecraft:grass_block"));
            mapNewHashMap.put("village", Maps.newHashMap());
        }
        return new Dynamic<>(dynamicOps, dynamicOps.createMap(ImmutableMap.of(dynamicOps.createString("layers"), dynamicOps.createList(listNewArrayList.stream().map($$1 -> {
            return dynamicOps.createMap(ImmutableMap.of(dynamicOps.createString(Display.TAG_HEIGHT), dynamicOps.createInt(((Integer) $$1.getFirst()).intValue()), dynamicOps.createString("block"), dynamicOps.createString((String) $$1.getSecond())));
        })), dynamicOps.createString("biome"), dynamicOps.createString(orDefault), dynamicOps.createString("structures"), dynamicOps.createMap((Map) mapNewHashMap.entrySet().stream().map($$12 -> {
            return Pair.of(dynamicOps.createString(((String) $$12.getKey()).toLowerCase(Locale.ROOT)), dynamicOps.createMap((Map) ((Map) $$12.getValue()).entrySet().stream().map($$12 -> {
                return Pair.of(dynamicOps.createString((String) $$12.getKey()), dynamicOps.createString((String) $$12.getValue()));
            }).collect(Collectors.toMap((v0) -> {
                return v0.getFirst();
            }, (v0) -> {
                return v0.getSecond();
            }))));
        }).collect(Collectors.toMap((v0) -> {
            return v0.getFirst();
        }, (v0) -> {
            return v0.getSecond();
        }))))));
    }

    private static Pair<Integer, String> getLayerInfoFromString(String $$0) {
        int $$4;
        String[] $$1 = $$0.split("\\*", 2);
        if ($$1.length == 2) {
            try {
                $$4 = Integer.parseInt($$1[0]);
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            $$4 = 1;
        }
        String $$5 = $$1[$$1.length - 1];
        return Pair.of(Integer.valueOf($$4), $$5);
    }

    private static List<Pair<Integer, String>> getLayersInfoFromString(String $$0) {
        List<Pair<Integer, String>> $$1 = Lists.newArrayList();
        String[] $$2 = $$0.split(",");
        for (String $$3 : $$2) {
            Pair<Integer, String> $$4 = getLayerInfoFromString($$3);
            if ($$4 == null) {
                return Collections.emptyList();
            }
            $$1.add($$4);
        }
        return $$1;
    }
}

package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicLike;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.entity.Display;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/WorldGenSettingsFix.class */
public class WorldGenSettingsFix extends DataFix {
    private static final String VILLAGE = "minecraft:village";
    private static final String DESERT_PYRAMID = "minecraft:desert_pyramid";
    private static final String IGLOO = "minecraft:igloo";
    private static final String JUNGLE_TEMPLE = "minecraft:jungle_pyramid";
    private static final String SWAMP_HUT = "minecraft:swamp_hut";
    private static final String PILLAGER_OUTPOST = "minecraft:pillager_outpost";
    private static final String OCEAN_MONUMENT = "minecraft:monument";
    private static final String END_CITY = "minecraft:endcity";
    private static final String WOODLAND_MANSION = "minecraft:mansion";
    private static final ImmutableMap<String, StructureFeatureConfiguration> DEFAULTS = ImmutableMap.builder().put(VILLAGE, new StructureFeatureConfiguration(32, 8, 10387312)).put(DESERT_PYRAMID, new StructureFeatureConfiguration(32, 8, 14357617)).put(IGLOO, new StructureFeatureConfiguration(32, 8, 14357618)).put(JUNGLE_TEMPLE, new StructureFeatureConfiguration(32, 8, 14357619)).put(SWAMP_HUT, new StructureFeatureConfiguration(32, 8, 14357620)).put(PILLAGER_OUTPOST, new StructureFeatureConfiguration(32, 8, 165745296)).put(OCEAN_MONUMENT, new StructureFeatureConfiguration(32, 5, 10387313)).put(END_CITY, new StructureFeatureConfiguration(20, 11, 10387313)).put(WOODLAND_MANSION, new StructureFeatureConfiguration(80, 20, 10387319)).build();

    public WorldGenSettingsFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("WorldGenSettings building", getInputSchema().getType(References.WORLD_GEN_SETTINGS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), WorldGenSettingsFix::fix);
        });
    }

    private static <T> Dynamic<T> noise(long $$0, DynamicLike<T> $$1, Dynamic<T> $$2, Dynamic<T> $$3) {
        return $$1.createMap(ImmutableMap.of($$1.createString(ChunkRegionIoEvent.Fields.TYPE), $$1.createString("minecraft:noise"), $$1.createString("biome_source"), $$3, $$1.createString("seed"), $$1.createLong($$0), $$1.createString("settings"), $$2));
    }

    private static <T> Dynamic<T> vanillaBiomeSource(Dynamic<T> $$0, long $$1, boolean $$2, boolean $$3) {
        ImmutableMap.Builder<Dynamic<T>, Dynamic<T>> $$4 = ImmutableMap.builder().put($$0.createString(ChunkRegionIoEvent.Fields.TYPE), $$0.createString("minecraft:vanilla_layered")).put($$0.createString("seed"), $$0.createLong($$1)).put($$0.createString("large_biomes"), $$0.createBoolean($$3));
        if ($$2) {
            $$4.put($$0.createString("legacy_biome_init_layer"), $$0.createBoolean($$2));
        }
        return $$0.createMap($$4.build());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/WorldGenSettingsFix$StructureFeatureConfiguration.class */
    static final class StructureFeatureConfiguration {
        public static final Codec<StructureFeatureConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(Codec.INT.fieldOf("spacing").forGetter($$0 -> {
                return Integer.valueOf($$0.spacing);
            }), Codec.INT.fieldOf("separation").forGetter($$02 -> {
                return Integer.valueOf($$02.separation);
            }), Codec.INT.fieldOf("salt").forGetter($$03 -> {
                return Integer.valueOf($$03.salt);
            })).apply($$0, (v1, v2, v3) -> {
                return new StructureFeatureConfiguration(v1, v2, v3);
            });
        });
        final int spacing;
        final int separation;
        final int salt;

        public StructureFeatureConfiguration(int $$0, int $$1, int $$2) {
            this.spacing = $$0;
            this.separation = $$1;
            this.salt = $$2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public <T> Dynamic<T> serialize(DynamicOps<T> $$0) {
            return new Dynamic<>($$0, CODEC.encodeStart($$0, this).result().orElse($$0.emptyMap()));
        }
    }

    private static <T> Dynamic<T> fix(Dynamic<T> $$0) {
        Dynamic<T> $$27;
        Dynamic<T> $$17;
        Dynamic<T> $$21;
        DynamicOps<T> $$1 = $$0.getOps();
        long $$2 = $$0.get("RandomSeed").asLong(0L);
        Optional<String> $$3 = $$0.get("generatorName").asString().map($$02 -> {
            return $$02.toLowerCase(Locale.ROOT);
        }).result();
        Optional<String> $$4 = (Optional) $$0.get("legacy_custom_options").asString().result().map((v0) -> {
            return Optional.of(v0);
        }).orElseGet(() -> {
            if ($$3.equals(Optional.of("customized"))) {
                return $$0.get(LevelDataGeneratorOptionsFix.GENERATOR_OPTIONS).asString().result();
            }
            return Optional.empty();
        });
        boolean $$5 = false;
        if ($$3.equals(Optional.of("customized")) || $$3.isEmpty()) {
            $$27 = defaultOverworld($$0, $$2);
        } else {
            switch ($$3.get()) {
                case "flat":
                    OptionalDynamic<T> $$8 = $$0.get(LevelDataGeneratorOptionsFix.GENERATOR_OPTIONS);
                    Map<Dynamic<T>, Dynamic<T>> $$9 = fixFlatStructures($$1, $$8);
                    $$27 = $$0.createMap(ImmutableMap.of($$0.createString(ChunkRegionIoEvent.Fields.TYPE), $$0.createString("minecraft:flat"), $$0.createString("settings"), $$0.createMap(ImmutableMap.of($$0.createString("structures"), $$0.createMap($$9), $$0.createString("layers"), (Dynamic) $$8.get("layers").result().orElseGet(() -> {
                        return $$0.createList(Stream.of((Object[]) new Dynamic[]{$$0.createMap(ImmutableMap.of($$0.createString(Display.TAG_HEIGHT), $$0.createInt(1), $$0.createString("block"), $$0.createString("minecraft:bedrock"))), $$0.createMap(ImmutableMap.of($$0.createString(Display.TAG_HEIGHT), $$0.createInt(2), $$0.createString("block"), $$0.createString("minecraft:dirt"))), $$0.createMap(ImmutableMap.of($$0.createString(Display.TAG_HEIGHT), $$0.createInt(1), $$0.createString("block"), $$0.createString("minecraft:grass_block")))}));
                    }), $$0.createString("biome"), $$0.createString($$8.get("biome").asString(ChunkHeightAndBiomeFix.DEFAULT_BIOME))))));
                    break;
                case "debug_all_block_states":
                    $$27 = $$0.createMap(ImmutableMap.of($$0.createString(ChunkRegionIoEvent.Fields.TYPE), $$0.createString("minecraft:debug")));
                    break;
                case "buffet":
                    OptionalDynamic<T> $$12 = $$0.get(LevelDataGeneratorOptionsFix.GENERATOR_OPTIONS);
                    OptionalDynamic<?> $$13 = $$12.get("chunk_generator");
                    Optional<String> $$14 = $$13.get(ChunkRegionIoEvent.Fields.TYPE).asString().result();
                    if (Objects.equals($$14, Optional.of("minecraft:caves"))) {
                        $$17 = $$0.createString("minecraft:caves");
                        $$5 = true;
                    } else if (Objects.equals($$14, Optional.of("minecraft:floating_islands"))) {
                        $$17 = $$0.createString("minecraft:floating_islands");
                    } else {
                        $$17 = $$0.createString("minecraft:overworld");
                    }
                    Dynamic<T> $$18 = (Dynamic) $$12.get("biome_source").result().orElseGet(() -> {
                        return $$0.createMap(ImmutableMap.of($$0.createString(ChunkRegionIoEvent.Fields.TYPE), $$0.createString("minecraft:fixed")));
                    });
                    if ($$18.get(ChunkRegionIoEvent.Fields.TYPE).asString().result().equals(Optional.of("minecraft:fixed"))) {
                        String $$19 = (String) $$18.get("options").get("biomes").asStream().findFirst().flatMap($$03 -> {
                            return $$03.asString().result();
                        }).orElse("minecraft:ocean");
                        $$21 = $$18.remove("options").set("biome", $$0.createString($$19));
                    } else {
                        $$21 = $$18;
                    }
                    $$27 = noise($$2, $$0, $$17, $$21);
                    break;
                default:
                    boolean $$23 = $$3.get().equals(GameTestEnvironments.DEFAULT);
                    boolean $$24 = $$3.get().equals("default_1_1") || ($$23 && $$0.get("generatorVersion").asInt(0) == 0);
                    boolean $$25 = $$3.get().equals("amplified");
                    boolean $$26 = $$3.get().equals("largebiomes");
                    $$27 = noise($$2, $$0, $$0.createString($$25 ? "minecraft:amplified" : "minecraft:overworld"), vanillaBiomeSource($$0, $$2, $$24, $$26));
                    break;
            }
        }
        boolean $$28 = $$0.get("MapFeatures").asBoolean(true);
        boolean $$29 = $$0.get("BonusChest").asBoolean(false);
        ImmutableMap.Builder<T, T> $$30 = ImmutableMap.builder();
        $$30.put($$1.createString("seed"), $$1.createLong($$2));
        $$30.put($$1.createString("generate_features"), $$1.createBoolean($$28));
        $$30.put($$1.createString("bonus_chest"), $$1.createBoolean($$29));
        $$30.put($$1.createString("dimensions"), vanillaLevels($$0, $$2, $$27, $$5));
        $$4.ifPresent($$22 -> {
            $$30.put($$1.createString("legacy_custom_options"), $$1.createString($$22));
        });
        return new Dynamic<>($$1, $$1.createMap($$30.build()));
    }

    protected static <T> Dynamic<T> defaultOverworld(Dynamic<T> $$0, long $$1) {
        return noise($$1, $$0, $$0.createString("minecraft:overworld"), vanillaBiomeSource($$0, $$1, false, false));
    }

    protected static <T> T vanillaLevels(Dynamic<T> dynamic, long j, Dynamic<T> dynamic2, boolean z) {
        DynamicOps ops = dynamic.getOps();
        return (T) ops.createMap(ImmutableMap.of(ops.createString("minecraft:overworld"), ops.createMap(ImmutableMap.of(ops.createString(ChunkRegionIoEvent.Fields.TYPE), ops.createString("minecraft:overworld" + (z ? "_caves" : "")), ops.createString("generator"), dynamic2.getValue())), ops.createString("minecraft:the_nether"), ops.createMap(ImmutableMap.of(ops.createString(ChunkRegionIoEvent.Fields.TYPE), ops.createString("minecraft:the_nether"), ops.createString("generator"), noise(j, dynamic, dynamic.createString("minecraft:nether"), dynamic.createMap(ImmutableMap.of(dynamic.createString(ChunkRegionIoEvent.Fields.TYPE), dynamic.createString("minecraft:multi_noise"), dynamic.createString("seed"), dynamic.createLong(j), dynamic.createString("preset"), dynamic.createString("minecraft:nether")))).getValue())), ops.createString("minecraft:the_end"), ops.createMap(ImmutableMap.of(ops.createString(ChunkRegionIoEvent.Fields.TYPE), ops.createString("minecraft:the_end"), ops.createString("generator"), noise(j, dynamic, dynamic.createString("minecraft:end"), dynamic.createMap(ImmutableMap.of(dynamic.createString(ChunkRegionIoEvent.Fields.TYPE), dynamic.createString("minecraft:the_end"), dynamic.createString("seed"), dynamic.createLong(j)))).getValue()))));
    }

    private static <T> Map<Dynamic<T>, Dynamic<T>> fixFlatStructures(DynamicOps<T> $$0, OptionalDynamic<T> $$1) {
        MutableInt $$2 = new MutableInt(32);
        MutableInt $$3 = new MutableInt(3);
        MutableInt $$4 = new MutableInt(128);
        MutableBoolean $$5 = new MutableBoolean(false);
        Map<String, StructureFeatureConfiguration> $$6 = Maps.newHashMap();
        if ($$1.result().isEmpty()) {
            $$5.setTrue();
            $$6.put(VILLAGE, (StructureFeatureConfiguration) DEFAULTS.get(VILLAGE));
        }
        $$1.get("structures").flatMap((v0) -> {
            return v0.getMapValues();
        }).ifSuccess($$52 -> {
            $$52.forEach(($$52, $$62) -> {
                $$62.getMapValues().result().ifPresent($$62 -> {
                    $$62.forEach(($$62, $$7) -> {
                        String $$8 = $$52.asString("");
                        String $$9 = $$62.asString("");
                        String $$10 = $$7.asString("");
                        if ("stronghold".equals($$8)) {
                            $$5.setTrue();
                            switch ($$9) {
                                case "distance":
                                    $$2.setValue(getInt($$10, $$2.intValue(), 1));
                                    break;
                                case "spread":
                                    $$3.setValue(getInt($$10, $$3.intValue(), 1));
                                    break;
                                case "count":
                                    $$4.setValue(getInt($$10, $$4.intValue(), 1));
                                    break;
                            }
                        }
                        switch ($$9) {
                            case "distance":
                                switch ($$8) {
                                    case "village":
                                        setSpacing($$6, VILLAGE, $$10, 9);
                                        break;
                                    case "biome_1":
                                        setSpacing($$6, DESERT_PYRAMID, $$10, 9);
                                        setSpacing($$6, IGLOO, $$10, 9);
                                        setSpacing($$6, JUNGLE_TEMPLE, $$10, 9);
                                        setSpacing($$6, SWAMP_HUT, $$10, 9);
                                        setSpacing($$6, PILLAGER_OUTPOST, $$10, 9);
                                        break;
                                    case "endcity":
                                        setSpacing($$6, END_CITY, $$10, 1);
                                        break;
                                    case "mansion":
                                        setSpacing($$6, WOODLAND_MANSION, $$10, 1);
                                        break;
                                }
                                break;
                            case "separation":
                                if ("oceanmonument".equals($$8)) {
                                    StructureFeatureConfiguration $$11 = (StructureFeatureConfiguration) $$6.getOrDefault(OCEAN_MONUMENT, (StructureFeatureConfiguration) DEFAULTS.get(OCEAN_MONUMENT));
                                    int $$12 = getInt($$10, $$11.separation, 1);
                                    $$6.put(OCEAN_MONUMENT, new StructureFeatureConfiguration($$12, $$11.separation, $$11.salt));
                                    break;
                                }
                                break;
                            case "spacing":
                                if ("oceanmonument".equals($$8)) {
                                    setSpacing($$6, OCEAN_MONUMENT, $$10, 1);
                                    break;
                                }
                                break;
                        }
                    });
                });
            });
        });
        ImmutableMap.Builder<Dynamic<T>, Dynamic<T>> $$7 = ImmutableMap.builder();
        $$7.put($$1.createString("structures"), $$1.createMap((Map) $$6.entrySet().stream().collect(Collectors.toMap($$12 -> {
            return $$1.createString((String) $$12.getKey());
        }, $$13 -> {
            return ((StructureFeatureConfiguration) $$13.getValue()).serialize($$0);
        }))));
        if ($$5.isTrue()) {
            $$7.put($$1.createString("stronghold"), $$1.createMap(ImmutableMap.of($$1.createString("distance"), $$1.createInt($$2.intValue()), $$1.createString("spread"), $$1.createInt($$3.intValue()), $$1.createString("count"), $$1.createInt($$4.intValue()))));
        }
        return $$7.build();
    }

    private static int getInt(String $$0, int $$1) {
        return NumberUtils.toInt($$0, $$1);
    }

    private static int getInt(String $$0, int $$1, int $$2) {
        return Math.max($$2, getInt($$0, $$1));
    }

    private static void setSpacing(Map<String, StructureFeatureConfiguration> $$0, String $$1, String $$2, int $$3) {
        StructureFeatureConfiguration $$4 = $$0.getOrDefault($$1, (StructureFeatureConfiguration) DEFAULTS.get($$1));
        int $$5 = getInt($$2, $$4.spacing, $$3);
        $$0.put($$1, new StructureFeatureConfiguration($$5, $$4.separation, $$4.salt));
    }
}

package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.LongStream;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix.class */
public class StructuresBecomeConfiguredFix extends DataFix {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<String, Conversion> CONVERSION_MAP = ImmutableMap.builder().put("mineshaft", Conversion.biomeMapped(Map.of(List.of("minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands"), "minecraft:mineshaft_mesa"), "minecraft:mineshaft")).put("shipwreck", Conversion.biomeMapped(Map.of(List.of("minecraft:beach", "minecraft:snowy_beach"), "minecraft:shipwreck_beached"), "minecraft:shipwreck")).put("ocean_ruin", Conversion.biomeMapped(Map.of(List.of("minecraft:warm_ocean", "minecraft:lukewarm_ocean", "minecraft:deep_lukewarm_ocean"), "minecraft:ocean_ruin_warm"), "minecraft:ocean_ruin_cold")).put("village", Conversion.biomeMapped(Map.of(List.of("minecraft:desert"), "minecraft:village_desert", List.of("minecraft:savanna"), "minecraft:village_savanna", List.of("minecraft:snowy_plains"), "minecraft:village_snowy", List.of("minecraft:taiga"), "minecraft:village_taiga"), "minecraft:village_plains")).put("ruined_portal", Conversion.biomeMapped(Map.of(List.of("minecraft:desert"), "minecraft:ruined_portal_desert", List.of((Object[]) new String[]{"minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands", "minecraft:windswept_hills", "minecraft:windswept_forest", "minecraft:windswept_gravelly_hills", "minecraft:savanna_plateau", "minecraft:windswept_savanna", "minecraft:stony_shore", "minecraft:meadow", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:stony_peaks", "minecraft:snowy_slopes"}), "minecraft:ruined_portal_mountain", List.of("minecraft:bamboo_jungle", "minecraft:jungle", "minecraft:sparse_jungle"), "minecraft:ruined_portal_jungle", List.of("minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean", "minecraft:deep_lukewarm_ocean", "minecraft:frozen_ocean", "minecraft:ocean", "minecraft:cold_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean"), "minecraft:ruined_portal_ocean"), "minecraft:ruined_portal")).put("pillager_outpost", Conversion.trivial("minecraft:pillager_outpost")).put("mansion", Conversion.trivial("minecraft:mansion")).put("jungle_pyramid", Conversion.trivial("minecraft:jungle_pyramid")).put("desert_pyramid", Conversion.trivial("minecraft:desert_pyramid")).put("igloo", Conversion.trivial("minecraft:igloo")).put("swamp_hut", Conversion.trivial("minecraft:swamp_hut")).put("stronghold", Conversion.trivial("minecraft:stronghold")).put("monument", Conversion.trivial("minecraft:monument")).put("fortress", Conversion.trivial("minecraft:fortress")).put("endcity", Conversion.trivial("minecraft:end_city")).put("buried_treasure", Conversion.trivial("minecraft:buried_treasure")).put("nether_fossil", Conversion.trivial("minecraft:nether_fossil")).put("bastion_remnant", Conversion.trivial("minecraft:bastion_remnant")).build();

    public StructuresBecomeConfiguredFix(Schema $$0) {
        super($$0, false);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion.class */
    static final class Conversion extends Record {
        private final Map<String, String> biomeMapping;
        private final String fallback;

        private Conversion(Map<String, String> $$0, String $$1) {
            this.biomeMapping = $$0;
            this.fallback = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Conversion.class), Conversion.class, "biomeMapping;fallback", "FIELD:Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;->biomeMapping:Ljava/util/Map;", "FIELD:Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;->fallback:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Conversion.class), Conversion.class, "biomeMapping;fallback", "FIELD:Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;->biomeMapping:Ljava/util/Map;", "FIELD:Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;->fallback:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Conversion.class, Object.class), Conversion.class, "biomeMapping;fallback", "FIELD:Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;->biomeMapping:Ljava/util/Map;", "FIELD:Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;->fallback:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Map<String, String> biomeMapping() {
            return this.biomeMapping;
        }

        public String fallback() {
            return this.fallback;
        }

        public static Conversion trivial(String $$0) {
            return new Conversion(Map.of(), $$0);
        }

        public static Conversion biomeMapped(Map<List<String>, String> $$0, String $$1) {
            return new Conversion(unpack($$0), $$1);
        }

        private static Map<String, String> unpack(Map<List<String>, String> $$0) {
            ImmutableMap.Builder<String, String> $$1 = ImmutableMap.builder();
            for (Map.Entry<List<String>, String> $$2 : $$0.entrySet()) {
                $$2.getKey().forEach($$22 -> {
                    $$1.put($$22, (String) $$2.getValue());
                });
            }
            return $$1.build();
        }
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.CHUNK);
        Type<?> $$1 = getInputSchema().getType(References.CHUNK);
        return writeFixAndRead("StucturesToConfiguredStructures", $$0, $$1, this::fix);
    }

    private Dynamic<?> fix(Dynamic<?> $$0) {
        return $$0.update("structures", $$1 -> {
            return $$1.update("starts", $$1 -> {
                return updateStarts($$1, $$0);
            }).update("References", $$12 -> {
                return updateReferences($$12, $$0);
            });
        });
    }

    private Dynamic<?> updateStarts(Dynamic<?> $$0, Dynamic<?> $$1) {
        Map<? extends Dynamic<?>, ? extends Dynamic<?>> $$2 = (Map) $$0.getMapValues().result().orElse(Map.of());
        HashMap<Dynamic<?>, Dynamic<?>> $$3 = Maps.newHashMap();
        $$2.forEach(($$22, $$32) -> {
            if ($$32.get(Entity.TAG_ID).asString(StructureStart.INVALID_START_ID).equals(StructureStart.INVALID_START_ID)) {
                return;
            }
            Dynamic<?> $$4 = findUpdatedStructureType($$22, $$1);
            if ($$4 == null) {
                LOGGER.warn("Encountered unknown structure in datafixer: {}", $$22.asString("<missing key>"));
            } else {
                $$3.computeIfAbsent($$4, $$22 -> {
                    return $$32.set(Entity.TAG_ID, $$4);
                });
            }
        });
        return $$1.createMap($$3);
    }

    private Dynamic<?> updateReferences(Dynamic<?> $$0, Dynamic<?> $$1) {
        Map<? extends Dynamic<?>, ? extends Dynamic<?>> $$2 = (Map) $$0.getMapValues().result().orElse(Map.of());
        HashMap<Dynamic<?>, Dynamic<?>> $$3 = Maps.newHashMap();
        $$2.forEach(($$22, $$32) -> {
            if ($$32.asLongStream().count() == 0) {
                return;
            }
            Dynamic<?> $$4 = findUpdatedStructureType($$22, $$1);
            if ($$4 == null) {
                LOGGER.warn("Encountered unknown structure in datafixer: {}", $$22.asString("<missing key>"));
            } else {
                $$3.compute($$4, ($$12, $$22) -> {
                    if ($$22 == null) {
                        return $$32;
                    }
                    return $$32.createLongList(LongStream.concat($$22.asLongStream(), $$32.asLongStream()));
                });
            }
        });
        return $$1.createMap($$3);
    }

    private Dynamic<?> findUpdatedStructureType(Dynamic<?> $$0, Dynamic<?> $$1) {
        String $$2 = $$0.asString("UNKNOWN").toLowerCase(Locale.ROOT);
        Conversion $$3 = CONVERSION_MAP.get($$2);
        if ($$3 == null) {
            return null;
        }
        String $$4 = $$3.fallback;
        if (!$$3.biomeMapping().isEmpty()) {
            Optional<String> $$5 = guessConfiguration($$1, $$3);
            if ($$5.isPresent()) {
                $$4 = $$5.get();
            }
        }
        return $$1.createString($$4);
    }

    private Optional<String> guessConfiguration(Dynamic<?> $$0, Conversion $$1) {
        Object2IntArrayMap<String> $$2 = new Object2IntArrayMap<>();
        $$0.get(SerializableChunkData.SECTIONS_TAG).asList(Function.identity()).forEach($$22 -> {
            $$22.get("biomes").get(StructureTemplate.PALETTE_TAG).asList(Function.identity()).forEach($$22 -> {
                String $$3 = $$1.biomeMapping().get($$22.asString(""));
                if ($$3 != null) {
                    $$2.mergeInt($$3, 1, Integer::sum);
                }
            });
        });
        return $$2.object2IntEntrySet().stream().max(Comparator.comparingInt((v0) -> {
            return v0.getIntValue();
        })).map((v0) -> {
            return v0.getKey();
        });
    }
}

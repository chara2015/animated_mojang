package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.stream.Stream;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.entity.Display;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import org.apache.commons.lang3.mutable.MutableBoolean;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/WorldGenSettingsHeightAndBiomeFix.class */
public class WorldGenSettingsHeightAndBiomeFix extends DataFix {
    private static final String NAME = "WorldGenSettingsHeightAndBiomeFix";
    public static final String WAS_PREVIOUSLY_INCREASED_KEY = "has_increased_height_already";

    public WorldGenSettingsHeightAndBiomeFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.WORLD_GEN_SETTINGS);
        OpticFinder<?> $$1 = $$0.findField("dimensions");
        Type<?> $$2 = getOutputSchema().getType(References.WORLD_GEN_SETTINGS);
        Type<?> $$3 = $$2.findFieldType("dimensions");
        return fixTypeEverywhereTyped(NAME, $$0, $$2, $$22 -> {
            OptionalDynamic<?> $$32 = ((Dynamic) $$22.get(DSL.remainderFinder())).get(WAS_PREVIOUSLY_INCREASED_KEY);
            boolean $$4 = $$32.result().isEmpty();
            boolean $$5 = $$32.asBoolean(true);
            return $$22.update(DSL.remainderFinder(), $$02 -> {
                return $$02.remove(WAS_PREVIOUSLY_INCREASED_KEY);
            }).updateTyped($$1, $$3, $$33 -> {
                return Util.writeAndReadTypedOrThrow($$33, $$3, $$22 -> {
                    return $$22.update("minecraft:overworld", $$22 -> {
                        return $$22.update("generator", $$22 -> {
                            String $$33 = $$22.get(ChunkRegionIoEvent.Fields.TYPE).asString("");
                            if ("minecraft:noise".equals($$33)) {
                                MutableBoolean $$42 = new MutableBoolean();
                                Dynamic $$22 = $$22.update("biome_source", $$23 -> {
                                    String $$34 = $$23.get(ChunkRegionIoEvent.Fields.TYPE).asString("");
                                    if ("minecraft:vanilla_layered".equals($$34) || ($$4 && "minecraft:multi_noise".equals($$34))) {
                                        if ($$23.get("large_biomes").asBoolean(false)) {
                                            $$42.setTrue();
                                        }
                                        return $$23.createMap(ImmutableMap.of($$23.createString("preset"), $$23.createString("minecraft:overworld"), $$23.createString(ChunkRegionIoEvent.Fields.TYPE), $$23.createString("minecraft:multi_noise")));
                                    }
                                    return $$23;
                                });
                                if ($$42.booleanValue()) {
                                    return $$22.update("settings", $$03 -> {
                                        if ("minecraft:overworld".equals($$03.asString(""))) {
                                            return $$03.createString("minecraft:large_biomes");
                                        }
                                        return $$03;
                                    });
                                }
                                return $$22;
                            }
                            if ("minecraft:flat".equals($$33)) {
                                if ($$5) {
                                    return $$22;
                                }
                                return $$22.update("settings", $$04 -> {
                                    return $$04.update("layers", WorldGenSettingsHeightAndBiomeFix::updateLayers);
                                });
                            }
                            return $$22;
                        });
                    });
                });
            });
        });
    }

    private static Dynamic<?> updateLayers(Dynamic<?> $$0) {
        Dynamic<?> $$1 = $$0.createMap(ImmutableMap.of($$0.createString(Display.TAG_HEIGHT), $$0.createInt(64), $$0.createString("block"), $$0.createString(JigsawBlockEntity.DEFAULT_FINAL_STATE)));
        return $$0.createList(Stream.concat(Stream.of($$1), $$0.asStream()));
    }
}

package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkToProtochunkFix.class */
public class ChunkToProtochunkFix extends DataFix {
    private static final int NUM_SECTIONS = 16;

    public ChunkToProtochunkFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        return writeFixAndRead("ChunkToProtoChunkFix", getInputSchema().getType(References.CHUNK), getOutputSchema().getType(References.CHUNK), $$0 -> {
            return $$0.update("Level", ChunkToProtochunkFix::fixChunkData);
        });
    }

    private static <T> Dynamic<T> fixChunkData(Dynamic<T> $$0) {
        String $$5;
        boolean $$1 = $$0.get("TerrainPopulated").asBoolean(false);
        boolean $$2 = $$0.get("LightPopulated").asNumber().result().isEmpty() || $$0.get("LightPopulated").asBoolean(false);
        if ($$1) {
            if ($$2) {
                $$5 = "mobs_spawned";
            } else {
                $$5 = "decorated";
            }
        } else {
            $$5 = "carved";
        }
        return repackTicks(repackBiomes($$0)).set("Status", $$0.createString($$5)).set("hasLegacyStructureData", $$0.createBoolean(true));
    }

    private static <T> Dynamic<T> repackBiomes(Dynamic<T> $$0) {
        return $$0.update("Biomes", $$1 -> {
            return (Dynamic) DataFixUtils.orElse($$1.asByteBufferOpt().result().map($$1 -> {
                int[] $$2 = new int[256];
                for (int $$3 = 0; $$3 < $$2.length; $$3++) {
                    if ($$3 < $$1.capacity()) {
                        $$2[$$3] = $$1.get($$3) & 255;
                    }
                }
                return $$0.createIntList(Arrays.stream($$2));
            }), $$1);
        });
    }

    private static <T> Dynamic<T> repackTicks(Dynamic<T> $$0) {
        return (Dynamic) DataFixUtils.orElse($$0.get("TileTicks").asStreamOpt().result().map($$1 -> {
            List<ShortList> $$2 = (List) IntStream.range(0, 16).mapToObj($$02 -> {
                return new ShortArrayList();
            }).collect(Collectors.toList());
            $$1.forEach($$1 -> {
                int $$22 = $$1.get("x").asInt(0);
                int $$3 = $$1.get("y").asInt(0);
                int $$4 = $$1.get("z").asInt(0);
                short $$5 = packOffsetCoordinates($$22, $$3, $$4);
                ((ShortList) $$2.get($$3 >> 4)).add($$5);
            });
            return $$0.remove("TileTicks").set("ToBeTicked", $$0.createList($$2.stream().map($$12 -> {
                return $$0.createList($$12.intStream().mapToObj($$12 -> {
                    return $$0.createShort((short) $$12);
                }));
            })));
        }), $$0);
    }

    private static short packOffsetCoordinates(int $$0, int $$1, int $$2) {
        return (short) (($$0 & 15) | (($$1 & 15) << 4) | (($$2 & 15) << 8));
    }
}

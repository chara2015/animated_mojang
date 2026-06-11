package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkBedBlockEntityInjecterFix.class */
public class ChunkBedBlockEntityInjecterFix extends DataFix {
    public ChunkBedBlockEntityInjecterFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getOutputSchema().getType(References.CHUNK);
        Type<?> $$1 = $$0.findFieldType("Level");
        List.ListType<?> listTypeFindFieldType = $$1.findFieldType("TileEntities");
        if (!(listTypeFindFieldType instanceof List.ListType)) {
            throw new IllegalStateException("Tile entity type is not a list type.");
        }
        List.ListType<?> $$3 = listTypeFindFieldType;
        return cap($$1, $$3);
    }

    private <TE> TypeRewriteRule cap(Type<?> $$0, List.ListType<TE> $$1) {
        Type<TE> $$2 = $$1.getElement();
        OpticFinder<?> $$3 = DSL.fieldFinder("Level", $$0);
        OpticFinder<java.util.List<TE>> $$4 = DSL.fieldFinder("TileEntities", $$1);
        return TypeRewriteRule.seq(fixTypeEverywhere("InjectBedBlockEntityType", getInputSchema().findChoiceType(References.BLOCK_ENTITY), getOutputSchema().findChoiceType(References.BLOCK_ENTITY), $$02 -> {
            return $$02 -> {
                return $$02;
            };
        }), fixTypeEverywhereTyped("BedBlockEntityInjecter", getOutputSchema().getType(References.CHUNK), $$32 -> {
            Typed<?> $$42 = $$32.getTyped($$3);
            Dynamic<?> $$5 = (Dynamic) $$42.get(DSL.remainderFinder());
            int $$6 = $$5.get(SerializableChunkData.X_POS_TAG).asInt(0);
            int $$7 = $$5.get(SerializableChunkData.Z_POS_TAG).asInt(0);
            ArrayList arrayListNewArrayList = Lists.newArrayList((Iterable) $$42.getOrCreate($$4));
            java.util.List<? extends Dynamic<?>> $$9 = $$5.get("Sections").asList(Function.identity());
            for (Dynamic<?> $$10 : $$9) {
                int $$11 = $$10.get("Y").asInt(0);
                Streams.mapWithIndex($$10.get("Blocks").asIntStream(), ($$43, $$52) -> {
                    if (416 == (($$43 & 255) << 4)) {
                        int $$62 = (int) $$52;
                        int $$72 = $$62 & 15;
                        int $$8 = ($$62 >> 8) & 15;
                        int $$92 = ($$62 >> 4) & 15;
                        Map<Dynamic<?>, Dynamic<?>> $$102 = Maps.newHashMap();
                        $$102.put($$10.createString(Entity.TAG_ID), $$10.createString("minecraft:bed"));
                        $$102.put($$10.createString("x"), $$10.createInt($$72 + ($$6 << 4)));
                        $$102.put($$10.createString("y"), $$10.createInt($$8 + ($$11 << 4)));
                        $$102.put($$10.createString("z"), $$10.createInt($$92 + ($$7 << 4)));
                        $$102.put($$10.createString("color"), $$10.createShort((short) 14));
                        return $$102;
                    }
                    return null;
                }).forEachOrdered($$32 -> {
                    if ($$32 != null) {
                        arrayListNewArrayList.add(((Pair) $$2.read($$10.createMap($$32)).result().orElseThrow(() -> {
                            return new IllegalStateException("Could not parse newly created bed block entity.");
                        })).getFirst());
                    }
                });
            }
            if (!arrayListNewArrayList.isEmpty()) {
                return $$32.set($$3, $$42.set($$4, arrayListNewArrayList));
            }
            return $$32;
        }));
    }
}

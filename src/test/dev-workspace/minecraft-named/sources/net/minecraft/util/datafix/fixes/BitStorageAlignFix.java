package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.stream.LongStream;
import net.minecraft.util.Mth;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BitStorageAlignFix.class */
public class BitStorageAlignFix extends DataFix {
    private static final int BIT_TO_LONG_SHIFT = 6;
    private static final int SECTION_WIDTH = 16;
    private static final int SECTION_HEIGHT = 16;
    private static final int SECTION_SIZE = 4096;
    private static final int HEIGHTMAP_BITS = 9;
    private static final int HEIGHTMAP_SIZE = 256;

    public BitStorageAlignFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.CHUNK);
        Type<?> $$1 = $$0.findFieldType("Level");
        OpticFinder<?> $$2 = DSL.fieldFinder("Level", $$1);
        OpticFinder<?> $$3 = $$2.type().findField("Sections");
        Type<?> $$4 = $$3.type().getElement();
        OpticFinder<?> $$5 = DSL.typeFinder($$4);
        Type<Pair<String, Dynamic<?>>> $$6 = DSL.named(References.BLOCK_STATE.typeName(), DSL.remainderType());
        OpticFinder<List<Pair<String, Dynamic<?>>>> $$7 = DSL.fieldFinder("Palette", DSL.list($$6));
        return fixTypeEverywhereTyped("BitStorageAlignFix", $$0, getOutputSchema().getType(References.CHUNK), $$42 -> {
            return $$42.updateTyped($$2, $$32 -> {
                return updateHeightmaps(updateSections($$3, $$5, $$7, $$32));
            });
        });
    }

    private Typed<?> updateHeightmaps(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), $$02 -> {
            return $$02.update(SerializableChunkData.HEIGHTMAPS_TAG, $$1 -> {
                return $$1.updateMapValues($$1 -> {
                    return $$1.mapSecond($$1 -> {
                        return updateBitStorage($$02, $$1, 256, 9);
                    });
                });
            });
        });
    }

    private static Typed<?> updateSections(OpticFinder<?> $$0, OpticFinder<?> $$1, OpticFinder<List<Pair<String, Dynamic<?>>>> $$2, Typed<?> $$3) {
        return $$3.updateTyped($$0, $$22 -> {
            return $$22.updateTyped($$1, $$12 -> {
                int $$22 = ((Integer) $$12.getOptional($$2).map($$02 -> {
                    return Integer.valueOf(Math.max(4, DataFixUtils.ceillog2($$02.size())));
                }).orElse(0)).intValue();
                if ($$22 == 0 || Mth.isPowerOfTwo($$22)) {
                    return $$12;
                }
                return $$12.update(DSL.remainderFinder(), $$12 -> {
                    return $$12.update("BlockStates", $$23 -> {
                        return updateBitStorage($$12, $$23, 4096, $$22);
                    });
                });
            });
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Dynamic<?> updateBitStorage(Dynamic<?> $$0, Dynamic<?> $$1, int $$2, int $$3) {
        long[] $$4 = $$1.asLongStream().toArray();
        long[] $$5 = addPadding($$2, $$3, $$4);
        return $$0.createLongList(LongStream.of($$5));
    }

    public static long[] addPadding(int $$0, int $$1, long[] $$2) {
        long $$21;
        int i;
        int $$3 = $$2.length;
        if ($$3 == 0) {
            return $$2;
        }
        long $$4 = (1 << $$1) - 1;
        int $$5 = 64 / $$1;
        int $$6 = (($$0 + $$5) - 1) / $$5;
        long[] $$7 = new long[$$6];
        int $$8 = 0;
        int $$9 = 0;
        long $$10 = 0;
        int $$11 = 0;
        long $$12 = $$2[0];
        long $$13 = $$3 > 1 ? $$2[1] : 0L;
        for (int $$14 = 0; $$14 < $$0; $$14++) {
            int $$15 = $$14 * $$1;
            int $$16 = $$15 >> 6;
            int $$17 = ((($$14 + 1) * $$1) - 1) >> 6;
            int $$18 = $$15 ^ ($$16 << 6);
            if ($$16 != $$11) {
                $$12 = $$13;
                $$13 = $$16 + 1 < $$3 ? $$2[$$16 + 1] : 0L;
                $$11 = $$16;
            }
            if ($$16 == $$17) {
                $$21 = ($$12 >>> $$18) & $$4;
            } else {
                int $$20 = 64 - $$18;
                $$21 = (($$12 >>> $$18) | ($$13 << $$20)) & $$4;
            }
            int $$22 = $$9 + $$1;
            if ($$22 >= 64) {
                int i2 = $$8;
                $$8++;
                $$7[i2] = $$10;
                $$10 = $$21;
                i = $$1;
            } else {
                $$10 |= $$21 << $$9;
                i = $$22;
            }
            $$9 = i;
        }
        if ($$10 != 0) {
            $$7[$$8] = $$10;
        }
        return $$7;
    }
}

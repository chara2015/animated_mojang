package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.stream.IntStream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkTicketUnpackPosFix.class */
public class ChunkTicketUnpackPosFix extends DataFix {
    private static final long CHUNK_COORD_BITS = 32;
    private static final long CHUNK_COORD_MASK = 4294967295L;

    public ChunkTicketUnpackPosFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("ChunkTicketUnpackPosFix", getInputSchema().getType(References.SAVED_DATA_TICKETS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return $$0.update("data", $$0 -> {
                    return $$0.update("tickets", $$0 -> {
                        return $$0.createList($$0.asStream().map($$0 -> {
                            return $$0.update("chunk_pos", $$0 -> {
                                long $$1 = $$0.asLong(0L);
                                int $$2 = (int) ($$1 & CHUNK_COORD_MASK);
                                int $$3 = (int) (($$1 >>> CHUNK_COORD_BITS) & CHUNK_COORD_MASK);
                                return $$0.createIntList(IntStream.of($$2, $$3));
                            });
                        }));
                    });
                });
            });
        });
    }
}

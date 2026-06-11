package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BlendingDataRemoveFromNetherEndFix.class */
public class BlendingDataRemoveFromNetherEndFix extends DataFix {
    public BlendingDataRemoveFromNetherEndFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getOutputSchema().getType(References.CHUNK);
        return fixTypeEverywhereTyped("BlendingDataRemoveFromNetherEndFix", $$0, $$02 -> {
            return $$02.update(DSL.remainderFinder(), $$02 -> {
                return updateChunkTag($$02, $$02.get(ChunkHeightAndBiomeFix.DATAFIXER_CONTEXT_TAG));
            });
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Dynamic<?> updateChunkTag(Dynamic<?> $$0, OptionalDynamic<?> $$1) {
        boolean $$2 = "minecraft:overworld".equals($$1.get(ChunkRegionIoEvent.Fields.DIMENSION).asString().result().orElse(""));
        return $$2 ? $$0 : $$0.remove("blending_data");
    }
}

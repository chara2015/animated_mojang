package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.SectionPos;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BlendingDataFix.class */
public class BlendingDataFix extends DataFix {
    private final String name;
    private static final Set<String> STATUSES_TO_SKIP_BLENDING = Set.of("minecraft:empty", "minecraft:structure_starts", "minecraft:structure_references", "minecraft:biomes");

    public BlendingDataFix(Schema $$0) {
        super($$0, false);
        this.name = "Blending Data Fix v" + $$0.getVersionKey();
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getOutputSchema().getType(References.CHUNK);
        return fixTypeEverywhereTyped(this.name, $$0, $$02 -> {
            return $$02.update(DSL.remainderFinder(), $$02 -> {
                return updateChunkTag($$02, $$02.get(ChunkHeightAndBiomeFix.DATAFIXER_CONTEXT_TAG));
            });
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Dynamic<?> updateChunkTag(Dynamic<?> $$0, OptionalDynamic<?> $$1) {
        Dynamic<?> $$02 = $$0.remove("blending_data");
        boolean $$2 = "minecraft:overworld".equals($$1.get(ChunkRegionIoEvent.Fields.DIMENSION).asString().result().orElse(""));
        Optional<? extends Dynamic<?>> $$3 = $$02.get("Status").result();
        if ($$2 && $$3.isPresent()) {
            String $$4 = NamespacedSchema.ensureNamespaced(((Dynamic) $$3.get()).asString("empty"));
            Optional<? extends Dynamic<?>> $$5 = $$02.get("below_zero_retrogen").result();
            if (!STATUSES_TO_SKIP_BLENDING.contains($$4)) {
                $$02 = updateBlendingData($$02, 384, -64);
            } else if ($$5.isPresent()) {
                Dynamic<?> $$6 = $$5.get();
                String $$7 = NamespacedSchema.ensureNamespaced($$6.get("target_status").asString("empty"));
                if (!STATUSES_TO_SKIP_BLENDING.contains($$7)) {
                    $$02 = updateBlendingData($$02, 256, 0);
                }
            }
        }
        return $$02;
    }

    private static Dynamic<?> updateBlendingData(Dynamic<?> $$0, int $$1, int $$2) {
        return $$0.set("blending_data", $$0.createMap(Map.of($$0.createString("min_section"), $$0.createInt(SectionPos.blockToSectionCoord($$2)), $$0.createString("max_section"), $$0.createInt(SectionPos.blockToSectionCoord($$2 + $$1)))));
    }
}

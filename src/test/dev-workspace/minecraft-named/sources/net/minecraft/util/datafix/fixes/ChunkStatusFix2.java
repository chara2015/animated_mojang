package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkStatusFix2.class */
public class ChunkStatusFix2 extends DataFix {
    private static final Map<String, String> RENAMES_AND_DOWNGRADES = ImmutableMap.builder().put("structure_references", "empty").put("biomes", "empty").put("base", "surface").put("carved", "carvers").put("liquid_carved", "liquid_carvers").put("decorated", "features").put("lighted", "light").put("mobs_spawned", "spawn").put("finalized", "heightmaps").put("fullchunk", "full").build();

    public ChunkStatusFix2(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.CHUNK);
        Type<?> $$1 = $$0.findFieldType("Level");
        OpticFinder<?> $$2 = DSL.fieldFinder("Level", $$1);
        return fixTypeEverywhereTyped("ChunkStatusFix2", $$0, getOutputSchema().getType(References.CHUNK), $$12 -> {
            return $$12.updateTyped($$2, $$02 -> {
                Dynamic<?> $$12 = (Dynamic) $$02.get(DSL.remainderFinder());
                String $$22 = $$12.get("Status").asString("empty");
                String $$3 = RENAMES_AND_DOWNGRADES.getOrDefault($$22, "empty");
                if (Objects.equals($$22, $$3)) {
                    return $$02;
                }
                return $$02.set(DSL.remainderFinder(), $$12.set("Status", $$12.createString($$3)));
            });
        });
    }
}

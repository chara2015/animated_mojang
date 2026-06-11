package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkLightRemoveFix.class */
public class ChunkLightRemoveFix extends DataFix {
    public ChunkLightRemoveFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.CHUNK);
        Type<?> $$1 = $$0.findFieldType("Level");
        OpticFinder<?> $$2 = DSL.fieldFinder("Level", $$1);
        return fixTypeEverywhereTyped("ChunkLightRemoveFix", $$0, getOutputSchema().getType(References.CHUNK), $$12 -> {
            return $$12.updateTyped($$2, $$02 -> {
                return $$02.update(DSL.remainderFinder(), $$02 -> {
                    return $$02.remove(SerializableChunkData.IS_LIGHT_ON_TAG);
                });
            });
        });
    }
}

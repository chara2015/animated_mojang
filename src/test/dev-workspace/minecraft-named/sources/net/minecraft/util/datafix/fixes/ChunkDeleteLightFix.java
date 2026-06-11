package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ChunkDeleteLightFix.class */
public class ChunkDeleteLightFix extends DataFix {
    public ChunkDeleteLightFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.CHUNK);
        OpticFinder<?> $$1 = $$0.findField(SerializableChunkData.SECTIONS_TAG);
        return fixTypeEverywhereTyped("ChunkDeleteLightFix for " + getOutputSchema().getVersionKey(), $$0, $$12 -> {
            return $$12.update(DSL.remainderFinder(), $$02 -> {
                return $$02.remove(SerializableChunkData.IS_LIGHT_ON_TAG);
            }).updateTyped($$1, $$03 -> {
                return $$03.update(DSL.remainderFinder(), $$03 -> {
                    return $$03.remove(SerializableChunkData.BLOCK_LIGHT_TAG).remove(SerializableChunkData.SKY_LIGHT_TAG);
                });
            });
        });
    }
}

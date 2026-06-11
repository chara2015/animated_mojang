package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/PlayerRespawnDataFix.class */
public class PlayerRespawnDataFix extends DataFix {
    public PlayerRespawnDataFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("PlayerRespawnDataFix", getInputSchema().getType(References.PLAYER), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return $$0.update("respawn", $$0 -> {
                    return $$0.set(ChunkRegionIoEvent.Fields.DIMENSION, $$0.createString($$0.get(ChunkRegionIoEvent.Fields.DIMENSION).asString("minecraft:overworld"))).set("yaw", $$0.createFloat($$0.get("angle").asFloat(0.0f))).set("pitch", $$0.createFloat(0.0f)).remove("angle");
                });
            });
        });
    }
}

package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.stream.IntStream;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/WorldSpawnDataFix.class */
public class WorldSpawnDataFix extends DataFix {
    public WorldSpawnDataFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("WorldSpawnDataFix", getInputSchema().getType(References.LEVEL), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                int $$1 = $$0.get("SpawnX").asInt(0);
                int $$2 = $$0.get("SpawnY").asInt(0);
                int $$3 = $$0.get("SpawnZ").asInt(0);
                float $$4 = $$0.get("SpawnAngle").asFloat(0.0f);
                Dynamic<?> $$5 = $$0.emptyMap().set(ChunkRegionIoEvent.Fields.DIMENSION, $$0.createString("minecraft:overworld")).set("pos", $$0.createIntList(IntStream.of($$1, $$2, $$3))).set("yaw", $$0.createFloat($$4)).set("pitch", $$0.createFloat(0.0f));
                return $$0.remove("SpawnX").remove("SpawnY").remove("SpawnZ").remove("SpawnAngle").set("spawn", $$5);
            });
        });
    }
}

package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/StructureSettingsFlattenFix.class */
public class StructureSettingsFlattenFix extends DataFix {
    public StructureSettingsFlattenFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.WORLD_GEN_SETTINGS);
        OpticFinder<?> $$1 = $$0.findField("dimensions");
        return fixTypeEverywhereTyped("StructureSettingsFlatten", $$0, $$12 -> {
            return $$12.updateTyped($$1, $$12 -> {
                return Util.writeAndReadTypedOrThrow($$12, $$1.type(), $$02 -> {
                    return $$02.updateMapValues(StructureSettingsFlattenFix::fixDimension);
                });
            });
        });
    }

    private static Pair<Dynamic<?>, Dynamic<?>> fixDimension(Pair<Dynamic<?>, Dynamic<?>> $$0) {
        Dynamic<?> $$1 = (Dynamic) $$0.getSecond();
        return Pair.of((Dynamic) $$0.getFirst(), $$1.update("generator", $$02 -> {
            return $$02.update("settings", $$02 -> {
                return $$02.update("structures", StructureSettingsFlattenFix::fixStructures);
            });
        }));
    }

    private static Dynamic<?> fixStructures(Dynamic<?> $$0) {
        Dynamic<?> $$1 = $$0.get("structures").orElseEmptyMap().updateMapValues($$12 -> {
            return $$12.mapSecond($$12 -> {
                return $$12.set(ChunkRegionIoEvent.Fields.TYPE, $$0.createString("minecraft:random_spread"));
            });
        });
        return (Dynamic) DataFixUtils.orElse($$0.get("stronghold").result().map($$2 -> {
            return $$1.set("minecraft:stronghold", $$2.set(ChunkRegionIoEvent.Fields.TYPE, $$0.createString("minecraft:concentric_rings")));
        }), $$1);
    }
}

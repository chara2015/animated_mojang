package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.nbt.NbtFormatException;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/WorldGenSettingsDisallowOldCustomWorldsFix.class */
public class WorldGenSettingsDisallowOldCustomWorldsFix extends DataFix {
    public WorldGenSettingsDisallowOldCustomWorldsFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.WORLD_GEN_SETTINGS);
        OpticFinder<?> $$1 = $$0.findField("dimensions");
        return fixTypeEverywhereTyped("WorldGenSettingsDisallowOldCustomWorldsFix_" + getOutputSchema().getVersionKey(), $$0, $$12 -> {
            return $$12.updateTyped($$1, $$02 -> {
                $$02.write().map($$02 -> {
                    return $$02.getMapValues().map($$02 -> {
                        $$02.forEach(($$02, $$12) -> {
                            if ($$12.get(ChunkRegionIoEvent.Fields.TYPE).asString().result().isEmpty()) {
                                throw new NbtFormatException("Unable load old custom worlds.");
                            }
                        });
                        return $$02;
                    });
                });
                return $$02;
            });
        });
    }
}

package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/LegacyDimensionIdFix.class */
public class LegacyDimensionIdFix extends DataFix {
    public LegacyDimensionIdFix(Schema $$0) {
        super($$0, false);
    }

    public TypeRewriteRule makeRule() {
        TypeRewriteRule $$0 = fixTypeEverywhereTyped("PlayerLegacyDimensionFix", getInputSchema().getType(References.PLAYER), $$02 -> {
            return $$02.update(DSL.remainderFinder(), this::fixPlayer);
        });
        Type<?> $$1 = getInputSchema().getType(References.SAVED_DATA_MAP_DATA);
        OpticFinder<?> $$2 = $$1.findField("data");
        TypeRewriteRule $$3 = fixTypeEverywhereTyped("MapLegacyDimensionFix", $$1, $$12 -> {
            return $$12.updateTyped($$2, $$03 -> {
                return $$03.update(DSL.remainderFinder(), this::fixMap);
            });
        });
        return TypeRewriteRule.seq($$0, $$3);
    }

    private <T> Dynamic<T> fixMap(Dynamic<T> $$0) {
        return $$0.update(ChunkRegionIoEvent.Fields.DIMENSION, this::fixDimensionId);
    }

    private <T> Dynamic<T> fixPlayer(Dynamic<T> $$0) {
        return $$0.update(ServerPlayer.TAG_DIMENSION, this::fixDimensionId);
    }

    private <T> Dynamic<T> fixDimensionId(Dynamic<T> $$0) {
        return (Dynamic) DataFixUtils.orElse($$0.asNumber().result().map($$1 -> {
            switch ($$1.intValue()) {
                case -1:
                    return $$0.createString("minecraft:the_nether");
                case 1:
                    return $$0.createString("minecraft:the_end");
                default:
                    return $$0.createString("minecraft:overworld");
            }
        }), $$0);
    }
}

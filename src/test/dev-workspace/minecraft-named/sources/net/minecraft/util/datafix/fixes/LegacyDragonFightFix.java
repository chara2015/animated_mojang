package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.util.datafix.ExtraDataFixUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/LegacyDragonFightFix.class */
public class LegacyDragonFightFix extends DataFix {
    public LegacyDragonFightFix(Schema $$0) {
        super($$0, false);
    }

    private static <T> Dynamic<T> fixDragonFight(Dynamic<T> $$0) {
        return $$0.update("ExitPortalLocation", ExtraDataFixUtils::fixBlockPos);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("LegacyDragonFightFix", getInputSchema().getType(References.LEVEL), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                OptionalDynamic<?> $$1 = $$0.get("DragonFight");
                if ($$1.result().isPresent()) {
                    return $$0;
                }
                Dynamic<?> $$2 = $$0.get("DimensionData").get("1").get("DragonFight").orElseEmptyMap();
                return $$0.set("DragonFight", fixDragonFight($$2));
            });
        });
    }
}

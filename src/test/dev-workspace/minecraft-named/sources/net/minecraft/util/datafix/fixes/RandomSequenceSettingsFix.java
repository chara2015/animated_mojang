package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/RandomSequenceSettingsFix.class */
public class RandomSequenceSettingsFix extends DataFix {
    public RandomSequenceSettingsFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("RandomSequenceSettingsFix", getInputSchema().getType(References.SAVED_DATA_RANDOM_SEQUENCES), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return $$0.update("data", $$0 -> {
                    return $$0.emptyMap().set("sequences", $$0);
                });
            });
        });
    }
}

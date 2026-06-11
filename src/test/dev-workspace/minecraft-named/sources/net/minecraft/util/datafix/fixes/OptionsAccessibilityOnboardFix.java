package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.nbt.SnbtOperations;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/OptionsAccessibilityOnboardFix.class */
public class OptionsAccessibilityOnboardFix extends DataFix {
    public OptionsAccessibilityOnboardFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("OptionsAccessibilityOnboardFix", getInputSchema().getType(References.OPTIONS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return $$0.set("onboardAccessibility", $$0.createString(SnbtOperations.BUILTIN_FALSE));
            });
        });
    }
}

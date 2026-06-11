package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.SnbtOperations;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/OptionsAmbientOcclusionFix.class */
public class OptionsAmbientOcclusionFix extends DataFix {
    public OptionsAmbientOcclusionFix(Schema $$0) {
        super($$0, false);
    }

    public TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("OptionsAmbientOcclusionFix", getInputSchema().getType(References.OPTIONS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return (Dynamic) DataFixUtils.orElse($$0.get("ao").asString().map($$1 -> {
                    return $$0.set("ao", $$0.createString(updateValue($$1)));
                }).result(), $$0);
            });
        });
    }

    private static String updateValue(String $$0) {
        switch ($$0) {
            case "0":
                return SnbtOperations.BUILTIN_FALSE;
            case "1":
            case "2":
                return SnbtOperations.BUILTIN_TRUE;
            default:
                return $$0;
        }
    }
}

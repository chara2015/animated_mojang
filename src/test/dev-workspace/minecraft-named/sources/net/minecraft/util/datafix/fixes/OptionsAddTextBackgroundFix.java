package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/OptionsAddTextBackgroundFix.class */
public class OptionsAddTextBackgroundFix extends DataFix {
    public OptionsAddTextBackgroundFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("OptionsAddTextBackgroundFix", getInputSchema().getType(References.OPTIONS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return (Dynamic) DataFixUtils.orElse($$0.get("chatOpacity").asString().map($$1 -> {
                    double $$2 = calculateBackground($$1);
                    return $$0.set("textBackgroundOpacity", $$0.createString(String.valueOf($$2)));
                }).result(), $$0);
            });
        });
    }

    private double calculateBackground(String $$0) {
        try {
            double $$1 = (0.9d * Double.parseDouble($$0)) + 0.1d;
            return $$1 / 2.0d;
        } catch (NumberFormatException e) {
            return 0.5d;
        }
    }
}

package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/OptionsGraphicsModeSplitFix.class */
public class OptionsGraphicsModeSplitFix extends DataFix {
    private final String newFieldName;
    private final String valueIfFast;
    private final String valueIfFancy;
    private final String valueIfFabulous;

    public OptionsGraphicsModeSplitFix(Schema $$0, String $$1, String $$2, String $$3, String $$4) {
        super($$0, true);
        this.newFieldName = $$1;
        this.valueIfFast = $$2;
        this.valueIfFancy = $$3;
        this.valueIfFabulous = $$4;
    }

    public TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("graphicsMode split to " + this.newFieldName, getInputSchema().getType(References.OPTIONS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return (Dynamic) DataFixUtils.orElseGet($$0.get("graphicsMode").asString().map($$1 -> {
                    return $$0.set(this.newFieldName, $$0.createString(getValue($$1)));
                }).result(), () -> {
                    return $$0.set(this.newFieldName, $$0.createString(this.valueIfFancy));
                });
            });
        });
    }

    private String getValue(String $$0) {
        switch ($$0) {
            case "2":
                return this.valueIfFabulous;
            case "0":
                return this.valueIfFast;
            default:
                return this.valueIfFancy;
        }
    }
}

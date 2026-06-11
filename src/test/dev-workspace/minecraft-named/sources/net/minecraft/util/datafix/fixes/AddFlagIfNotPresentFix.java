package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/AddFlagIfNotPresentFix.class */
public class AddFlagIfNotPresentFix extends DataFix {
    private final String name;
    private final boolean flagValue;
    private final String flagKey;
    private final DSL.TypeReference typeReference;

    public AddFlagIfNotPresentFix(Schema $$0, DSL.TypeReference $$1, String $$2, boolean $$3) {
        super($$0, true);
        this.flagValue = $$3;
        this.flagKey = $$2;
        this.name = "AddFlagIfNotPresentFix_" + this.flagKey + "=" + this.flagValue + " for " + $$0.getVersionKey();
        this.typeReference = $$1;
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(this.typeReference);
        return fixTypeEverywhereTyped(this.name, $$0, $$02 -> {
            return $$02.update(DSL.remainderFinder(), $$02 -> {
                return $$02.set(this.flagKey, (Dynamic) DataFixUtils.orElseGet($$02.get(this.flagKey).result(), () -> {
                    return $$02.createBoolean(this.flagValue);
                }));
            });
        });
    }
}

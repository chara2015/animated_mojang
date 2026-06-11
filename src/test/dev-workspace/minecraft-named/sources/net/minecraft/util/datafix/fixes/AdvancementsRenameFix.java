package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.Function;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/AdvancementsRenameFix.class */
public class AdvancementsRenameFix extends DataFix {
    private final String name;
    private final Function<String, String> renamer;

    public AdvancementsRenameFix(Schema $$0, boolean $$1, String $$2, Function<String, String> $$3) {
        super($$0, $$1);
        this.name = $$2;
        this.renamer = $$3;
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped(this.name, getInputSchema().getType(References.ADVANCEMENTS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return $$0.updateMapValues($$1 -> {
                    String $$2 = ((Dynamic) $$1.getFirst()).asString("");
                    return $$1.mapFirst($$22 -> {
                        return $$0.createString(this.renamer.apply($$2));
                    });
                });
            });
        });
    }
}

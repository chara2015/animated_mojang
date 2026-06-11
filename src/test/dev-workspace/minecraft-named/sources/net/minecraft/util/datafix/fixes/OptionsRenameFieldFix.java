package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/OptionsRenameFieldFix.class */
public class OptionsRenameFieldFix extends DataFix {
    private final String fixName;
    private final String fieldFrom;
    private final String fieldTo;

    public OptionsRenameFieldFix(Schema $$0, boolean $$1, String $$2, String $$3, String $$4) {
        super($$0, $$1);
        this.fixName = $$2;
        this.fieldFrom = $$3;
        this.fieldTo = $$4;
    }

    public TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped(this.fixName, getInputSchema().getType(References.OPTIONS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return $$0.renameField(this.fieldFrom, this.fieldTo);
            });
        });
    }
}

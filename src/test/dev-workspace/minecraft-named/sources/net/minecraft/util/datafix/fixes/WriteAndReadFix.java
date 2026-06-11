package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/WriteAndReadFix.class */
public class WriteAndReadFix extends DataFix {
    private final String name;
    private final DSL.TypeReference type;

    public WriteAndReadFix(Schema $$0, String $$1, DSL.TypeReference $$2) {
        super($$0, true);
        this.name = $$1;
        this.type = $$2;
    }

    protected TypeRewriteRule makeRule() {
        return writeAndRead(this.name, getInputSchema().getType(this.type), getOutputSchema().getType(this.type));
    }
}

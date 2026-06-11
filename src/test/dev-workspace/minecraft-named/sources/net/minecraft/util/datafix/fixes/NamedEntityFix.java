package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/NamedEntityFix.class */
public abstract class NamedEntityFix extends DataFix {
    private final String name;
    protected final String entityName;
    protected final DSL.TypeReference type;

    protected abstract Typed<?> fix(Typed<?> typed);

    public NamedEntityFix(Schema $$0, boolean $$1, String $$2, DSL.TypeReference $$3, String $$4) {
        super($$0, $$1);
        this.name = $$2;
        this.type = $$3;
        this.entityName = $$4;
    }

    public TypeRewriteRule makeRule() {
        OpticFinder<?> $$0 = DSL.namedChoice(this.entityName, getInputSchema().getChoiceType(this.type, this.entityName));
        return fixTypeEverywhereTyped(this.name, getInputSchema().getType(this.type), getOutputSchema().getType(this.type), $$1 -> {
            return $$1.updateTyped($$0, getOutputSchema().getChoiceType(this.type, this.entityName), this::fix);
        });
    }
}

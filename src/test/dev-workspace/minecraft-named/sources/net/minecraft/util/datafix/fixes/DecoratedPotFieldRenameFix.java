package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/DecoratedPotFieldRenameFix.class */
public class DecoratedPotFieldRenameFix extends DataFix {
    private static final String DECORATED_POT_ID = "minecraft:decorated_pot";

    public DecoratedPotFieldRenameFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getChoiceType(References.BLOCK_ENTITY, DECORATED_POT_ID);
        Type<?> $$1 = getOutputSchema().getChoiceType(References.BLOCK_ENTITY, DECORATED_POT_ID);
        return convertUnchecked("DecoratedPotFieldRenameFix", $$0, $$1);
    }
}

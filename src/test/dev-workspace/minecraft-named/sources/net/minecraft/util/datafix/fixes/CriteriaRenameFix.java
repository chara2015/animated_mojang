package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.UnaryOperator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/CriteriaRenameFix.class */
public class CriteriaRenameFix extends DataFix {
    private final String name;
    private final String advancementId;
    private final UnaryOperator<String> conversions;

    public CriteriaRenameFix(Schema $$0, String $$1, String $$2, UnaryOperator<String> $$3) {
        super($$0, false);
        this.name = $$1;
        this.advancementId = $$2;
        this.conversions = $$3;
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped(this.name, getInputSchema().getType(References.ADVANCEMENTS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), this::fixAdvancements);
        });
    }

    private Dynamic<?> fixAdvancements(Dynamic<?> $$0) {
        return $$0.update(this.advancementId, $$02 -> {
            return $$02.update("criteria", $$02 -> {
                return $$02.updateMapValues($$02 -> {
                    return $$02.mapFirst($$02 -> {
                        return (Dynamic) DataFixUtils.orElse($$02.asString().map($$1 -> {
                            return $$02.createString((String) this.conversions.apply($$1));
                        }).result(), $$02);
                    });
                });
            });
        });
    }
}

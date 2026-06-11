package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ObjectiveRenderTypeFix.class */
public class ObjectiveRenderTypeFix extends DataFix {
    public ObjectiveRenderTypeFix(Schema $$0) {
        super($$0, false);
    }

    private static String getRenderType(String $$0) {
        return $$0.equals("health") ? "hearts" : "integer";
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.OBJECTIVE);
        return fixTypeEverywhereTyped("ObjectiveRenderTypeFix", $$0, $$02 -> {
            return $$02.update(DSL.remainderFinder(), $$02 -> {
                Optional<String> $$1 = $$02.get("RenderType").asString().result();
                if ($$1.isEmpty()) {
                    String $$2 = $$02.get("CriteriaName").asString("");
                    String $$3 = getRenderType($$2);
                    return $$02.set("RenderType", $$02.createString($$3));
                }
                return $$02;
            });
        });
    }
}

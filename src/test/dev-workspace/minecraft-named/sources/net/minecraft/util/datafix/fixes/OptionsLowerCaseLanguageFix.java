package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Locale;
import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/OptionsLowerCaseLanguageFix.class */
public class OptionsLowerCaseLanguageFix extends DataFix {
    public OptionsLowerCaseLanguageFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("OptionsLowerCaseLanguageFix", getInputSchema().getType(References.OPTIONS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                Optional<String> $$1 = $$0.get("lang").asString().result();
                if ($$1.isPresent()) {
                    return $$0.set("lang", $$0.createString($$1.get().toLowerCase(Locale.ROOT)));
                }
                return $$0;
            });
        });
    }
}

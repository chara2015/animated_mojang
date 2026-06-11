package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/OptionsKeyTranslationFix.class */
public class OptionsKeyTranslationFix extends DataFix {
    public OptionsKeyTranslationFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("OptionsKeyTranslationFix", getInputSchema().getType(References.OPTIONS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return (Dynamic) $$0.getMapValues().map($$1 -> {
                    return $$0.createMap((Map) $$1.entrySet().stream().map($$1 -> {
                        if (((Dynamic) $$1.getKey()).asString("").startsWith("key_")) {
                            String $$2 = ((Dynamic) $$1.getValue()).asString("");
                            if (!$$2.startsWith("key.mouse") && !$$2.startsWith("scancode.")) {
                                return Pair.of((Dynamic) $$1.getKey(), $$0.createString("key.keyboard." + $$2.substring("key.".length())));
                            }
                        }
                        return Pair.of((Dynamic) $$1.getKey(), (Dynamic) $$1.getValue());
                    }).collect(Collectors.toMap((v0) -> {
                        return v0.getFirst();
                    }, (v0) -> {
                        return v0.getSecond();
                    })));
                }).result().orElse($$0);
            });
        });
    }
}

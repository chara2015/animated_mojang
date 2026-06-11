package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.nbt.SnbtOperations;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/OptionsMusicToastFix.class */
public class OptionsMusicToastFix extends DataFix {
    public OptionsMusicToastFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("OptionsMusicToastFix", getInputSchema().getType(References.OPTIONS), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return $$0.renameAndFixField("showNowPlayingToast", "musicToast", $$1 -> {
                    return $$0.createString($$1.asString(SnbtOperations.BUILTIN_FALSE).equals(SnbtOperations.BUILTIN_FALSE) ? "never" : "pause_and_toast");
                });
            });
        });
    }
}

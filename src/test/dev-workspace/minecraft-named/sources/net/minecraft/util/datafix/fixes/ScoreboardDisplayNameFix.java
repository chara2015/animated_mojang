package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.datafix.LegacyComponentDataFixUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ScoreboardDisplayNameFix.class */
public class ScoreboardDisplayNameFix extends DataFix {
    private final String name;
    private final DSL.TypeReference type;

    public ScoreboardDisplayNameFix(Schema $$0, String $$1, DSL.TypeReference $$2) {
        super($$0, false);
        this.name = $$1;
        this.type = $$2;
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(this.type);
        OpticFinder<?> $$1 = $$0.findField("DisplayName");
        OpticFinder<Pair<String, String>> $$2 = DSL.typeFinder(getInputSchema().getType(References.TEXT_COMPONENT));
        return fixTypeEverywhereTyped(this.name, $$0, $$22 -> {
            return $$22.updateTyped($$1, $$12 -> {
                return $$12.update($$2, $$02 -> {
                    return $$02.mapSecond(LegacyComponentDataFixUtils::createTextComponentJson);
                });
            });
        });
    }
}

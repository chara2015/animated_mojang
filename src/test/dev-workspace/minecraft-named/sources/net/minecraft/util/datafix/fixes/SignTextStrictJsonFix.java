package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.util.datafix.LegacyComponentDataFixUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/SignTextStrictJsonFix.class */
public class SignTextStrictJsonFix extends NamedEntityFix {
    private static final List<String> LINE_FIELDS = List.of("Text1", "Text2", "Text3", "Text4");

    public SignTextStrictJsonFix(Schema $$0) {
        super($$0, false, "SignTextStrictJsonFix", References.BLOCK_ENTITY, "Sign");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        for (String $$1 : LINE_FIELDS) {
            OpticFinder<?> $$2 = $$0.getType().findField($$1);
            OpticFinder<Pair<String, String>> $$3 = DSL.typeFinder(getInputSchema().getType(References.TEXT_COMPONENT));
            $$0 = $$0.updateTyped($$2, $$12 -> {
                return $$12.update($$3, $$02 -> {
                    return $$02.mapSecond(LegacyComponentDataFixUtils::rewriteFromLenient);
                });
            });
        }
        return $$0;
    }
}

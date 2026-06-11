package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.datafix.LegacyComponentDataFixUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/WrittenBookPagesStrictJsonFix.class */
public class WrittenBookPagesStrictJsonFix extends ItemStackTagFix {
    public WrittenBookPagesStrictJsonFix(Schema $$0) {
        super($$0, "WrittenBookPagesStrictJsonFix", $$02 -> {
            return $$02.equals("minecraft:written_book");
        });
    }

    @Override // net.minecraft.util.datafix.fixes.ItemStackTagFix
    protected Typed<?> fixItemStackTag(Typed<?> $$0) {
        Type<Pair<String, String>> $$1 = getInputSchema().getType(References.TEXT_COMPONENT);
        Type<?> $$2 = getInputSchema().getType(References.ITEM_STACK);
        OpticFinder<?> $$3 = $$2.findField("tag");
        OpticFinder<?> $$4 = $$3.type().findField("pages");
        OpticFinder<Pair<String, String>> $$5 = DSL.typeFinder($$1);
        return $$0.updateTyped($$4, $$12 -> {
            return $$12.update($$5, $$02 -> {
                return $$02.mapSecond(LegacyComponentDataFixUtils::rewriteFromLenient);
            });
        });
    }
}

package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.datafix.LegacyComponentDataFixUtils;
import net.minecraft.world.level.block.state.StateHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ItemCustomNameToComponentFix.class */
public class ItemCustomNameToComponentFix extends DataFix {
    public ItemCustomNameToComponentFix(Schema $$0) {
        super($$0, false);
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
        Type<Pair<String, String>> $$1 = getInputSchema().getType(References.TEXT_COMPONENT);
        OpticFinder<?> $$2 = $$0.findField("tag");
        OpticFinder<?> $$3 = $$2.type().findField("display");
        OpticFinder<?> $$4 = $$3.type().findField(StateHolder.NAME_TAG);
        OpticFinder<Pair<String, String>> $$5 = DSL.typeFinder($$1);
        return fixTypeEverywhereTyped("ItemCustomNameToComponentFix", $$0, $$42 -> {
            return $$42.updateTyped($$2, $$32 -> {
                return $$32.updateTyped($$3, $$22 -> {
                    return $$22.updateTyped($$4, $$12 -> {
                        return $$12.update($$5, $$02 -> {
                            return $$02.mapSecond(LegacyComponentDataFixUtils::createTextComponentJson);
                        });
                    });
                });
            });
        });
    }
}

package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/VillagerTradeFix.class */
public class VillagerTradeFix extends DataFix {
    public VillagerTradeFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.VILLAGER_TRADE);
        OpticFinder<?> $$1 = $$0.findField("buy");
        OpticFinder<?> $$2 = $$0.findField("buyB");
        OpticFinder<?> $$3 = $$0.findField("sell");
        OpticFinder<Pair<String, String>> $$4 = DSL.fieldFinder(Entity.TAG_ID, DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
        Function<Typed<?>, Typed<?>> $$5 = $$12 -> {
            return updateItemStack($$4, $$12);
        };
        return fixTypeEverywhereTyped("Villager trade fix", $$0, $$42 -> {
            return $$42.updateTyped($$1, $$5).updateTyped($$2, $$5).updateTyped($$3, $$5);
        });
    }

    private Typed<?> updateItemStack(OpticFinder<Pair<String, String>> $$0, Typed<?> $$1) {
        return $$1.update($$0, $$02 -> {
            return $$02.mapSecond($$02 -> {
                return Objects.equals($$02, "minecraft:carved_pumpkin") ? "minecraft:pumpkin" : $$02;
            });
        });
    }
}

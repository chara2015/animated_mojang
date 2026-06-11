package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.npc.InventoryCarrier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V1929.class */
public class V1929 extends NamespacedSchema {
    public V1929(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$0.register($$1, "minecraft:wandering_trader", $$12 -> {
            return DSL.optionalFields(InventoryCarrier.TAG_INVENTORY, DSL.list(References.ITEM_STACK.in($$0)), "Offers", DSL.optionalFields("Recipes", DSL.list(References.VILLAGER_TRADE.in($$0))));
        });
        $$0.register($$1, "minecraft:trader_llama", $$13 -> {
            return DSL.optionalFields(ContainerHelper.TAG_ITEMS, DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0), "DecorItem", References.ITEM_STACK.in($$0));
        });
        return $$1;
    }
}

package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.npc.InventoryCarrier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V4312.class */
public class V4312 extends NamespacedSchema {
    public V4312(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
        super.registerTypes($$0, $$1, $$2);
        $$0.registerType(false, References.PLAYER, () -> {
            return DSL.and(References.ENTITY_EQUIPMENT.in($$0), DSL.optionalFields(new Pair[]{Pair.of("RootVehicle", DSL.optionalFields("Entity", References.ENTITY_TREE.in($$0))), Pair.of(ServerPlayer.ENDER_PEARLS_TAG, DSL.list(References.ENTITY_TREE.in($$0))), Pair.of(InventoryCarrier.TAG_INVENTORY, DSL.list(References.ITEM_STACK.in($$0))), Pair.of("EnderItems", DSL.list(References.ITEM_STACK.in($$0))), Pair.of("ShoulderEntityLeft", References.ENTITY_TREE.in($$0)), Pair.of("ShoulderEntityRight", References.ENTITY_TREE.in($$0)), Pair.of(ServerRecipeBook.RECIPE_BOOK_TAG, DSL.optionalFields("recipes", DSL.list(References.RECIPE.in($$0)), "toBeDisplayed", DSL.list(References.RECIPE.in($$0))))}));
        });
    }
}

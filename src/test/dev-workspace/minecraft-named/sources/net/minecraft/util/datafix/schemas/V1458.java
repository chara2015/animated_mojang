package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V1458.class */
public class V1458 extends NamespacedSchema {
    public V1458(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
        super.registerTypes($$0, $$1, $$2);
        $$0.registerType(true, References.ENTITY, () -> {
            return DSL.and(References.ENTITY_EQUIPMENT.in($$0), DSL.optionalFields(Entity.TAG_CUSTOM_NAME, References.TEXT_COMPONENT.in($$0), DSL.taggedChoiceLazy(Entity.TAG_ID, namespacedString(), $$1)));
        });
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
        $$0.register($$1, "minecraft:beacon", () -> {
            return nameable($$0);
        });
        $$0.register($$1, "minecraft:banner", () -> {
            return nameable($$0);
        });
        $$0.register($$1, "minecraft:brewing_stand", () -> {
            return nameableInventory($$0);
        });
        $$0.register($$1, "minecraft:chest", () -> {
            return nameableInventory($$0);
        });
        $$0.register($$1, "minecraft:trapped_chest", () -> {
            return nameableInventory($$0);
        });
        $$0.register($$1, "minecraft:dispenser", () -> {
            return nameableInventory($$0);
        });
        $$0.register($$1, "minecraft:dropper", () -> {
            return nameableInventory($$0);
        });
        $$0.register($$1, "minecraft:enchanting_table", () -> {
            return nameable($$0);
        });
        $$0.register($$1, "minecraft:furnace", () -> {
            return nameableInventory($$0);
        });
        $$0.register($$1, "minecraft:hopper", () -> {
            return nameableInventory($$0);
        });
        $$0.register($$1, "minecraft:shulker_box", () -> {
            return nameableInventory($$0);
        });
        return $$1;
    }

    public static TypeTemplate nameableInventory(Schema $$0) {
        return DSL.optionalFields(ContainerHelper.TAG_ITEMS, DSL.list(References.ITEM_STACK.in($$0)), Entity.TAG_CUSTOM_NAME, References.TEXT_COMPONENT.in($$0));
    }

    public static TypeTemplate nameable(Schema $$0) {
        return DSL.optionalFields(Entity.TAG_CUSTOM_NAME, References.TEXT_COMPONENT.in($$0));
    }
}

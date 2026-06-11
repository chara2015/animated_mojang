package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.ContainerHelper;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V4067.class */
public class V4067 extends NamespacedSchema {
    public V4067(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$1.remove("minecraft:boat");
        $$1.remove("minecraft:chest_boat");
        registerSimple($$1, "minecraft:oak_boat");
        registerSimple($$1, "minecraft:spruce_boat");
        registerSimple($$1, "minecraft:birch_boat");
        registerSimple($$1, "minecraft:jungle_boat");
        registerSimple($$1, "minecraft:acacia_boat");
        registerSimple($$1, "minecraft:cherry_boat");
        registerSimple($$1, "minecraft:dark_oak_boat");
        registerSimple($$1, "minecraft:mangrove_boat");
        registerSimple($$1, "minecraft:bamboo_raft");
        registerChestBoat($$1, "minecraft:oak_chest_boat");
        registerChestBoat($$1, "minecraft:spruce_chest_boat");
        registerChestBoat($$1, "minecraft:birch_chest_boat");
        registerChestBoat($$1, "minecraft:jungle_chest_boat");
        registerChestBoat($$1, "minecraft:acacia_chest_boat");
        registerChestBoat($$1, "minecraft:cherry_chest_boat");
        registerChestBoat($$1, "minecraft:dark_oak_chest_boat");
        registerChestBoat($$1, "minecraft:mangrove_chest_boat");
        registerChestBoat($$1, "minecraft:bamboo_chest_raft");
        return $$1;
    }

    private void registerChestBoat(Map<String, Supplier<TypeTemplate>> $$0, String $$1) {
        register($$0, $$1, $$02 -> {
            return DSL.optionalFields(ContainerHelper.TAG_ITEMS, DSL.list(References.ITEM_STACK.in(this)));
        });
    }
}

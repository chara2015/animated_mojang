package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.ContainerHelper;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V4300.class */
public class V4300 extends NamespacedSchema {
    public V4300(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$0.register($$1, "minecraft:llama", $$12 -> {
            return entityWithInventory($$0);
        });
        $$0.register($$1, "minecraft:trader_llama", $$13 -> {
            return entityWithInventory($$0);
        });
        $$0.register($$1, "minecraft:donkey", $$14 -> {
            return entityWithInventory($$0);
        });
        $$0.register($$1, "minecraft:mule", $$15 -> {
            return entityWithInventory($$0);
        });
        $$0.registerSimple($$1, "minecraft:horse");
        $$0.registerSimple($$1, "minecraft:skeleton_horse");
        $$0.registerSimple($$1, "minecraft:zombie_horse");
        return $$1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TypeTemplate entityWithInventory(Schema $$0) {
        return DSL.optionalFields(ContainerHelper.TAG_ITEMS, DSL.list(References.ITEM_STACK.in($$0)));
    }
}

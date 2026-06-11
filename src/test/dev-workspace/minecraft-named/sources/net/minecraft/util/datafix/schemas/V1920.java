package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.ContainerHelper;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V1920.class */
public class V1920 extends NamespacedSchema {
    public V1920(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    protected static void registerInventory(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
        $$0.register($$1, $$2, () -> {
            return DSL.optionalFields(ContainerHelper.TAG_ITEMS, DSL.list(References.ITEM_STACK.in($$0)));
        });
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
        registerInventory($$0, $$1, "minecraft:campfire");
        return $$1;
    }
}

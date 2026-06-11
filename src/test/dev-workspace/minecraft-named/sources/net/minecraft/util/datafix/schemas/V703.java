package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.ContainerHelper;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V703.class */
public class V703 extends Schema {
    public V703(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$1.remove("EntityHorse");
        $$0.register($$1, "Horse", () -> {
            return DSL.optionalFields("ArmorItem", References.ITEM_STACK.in($$0), "SaddleItem", References.ITEM_STACK.in($$0));
        });
        $$0.register($$1, "Donkey", () -> {
            return DSL.optionalFields(ContainerHelper.TAG_ITEMS, DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0));
        });
        $$0.register($$1, "Mule", () -> {
            return DSL.optionalFields(ContainerHelper.TAG_ITEMS, DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0));
        });
        $$0.register($$1, "ZombieHorse", () -> {
            return DSL.optionalFields("SaddleItem", References.ITEM_STACK.in($$0));
        });
        $$0.register($$1, "SkeletonHorse", () -> {
            return DSL.optionalFields("SaddleItem", References.ITEM_STACK.in($$0));
        });
        return $$1;
    }
}

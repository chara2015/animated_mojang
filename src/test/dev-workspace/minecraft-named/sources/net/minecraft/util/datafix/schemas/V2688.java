package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V2688.class */
public class V2688 extends NamespacedSchema {
    public V2688(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$0.registerSimple($$1, "minecraft:glow_squid");
        $$0.register($$1, "minecraft:glow_item_frame", $$12 -> {
            return DSL.optionalFields("Item", References.ITEM_STACK.in($$0));
        });
        return $$1;
    }
}

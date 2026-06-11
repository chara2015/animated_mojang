package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V2519.class */
public class V2519 extends NamespacedSchema {
    public V2519(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$0.registerSimple($$1, "minecraft:strider");
        return $$1;
    }
}

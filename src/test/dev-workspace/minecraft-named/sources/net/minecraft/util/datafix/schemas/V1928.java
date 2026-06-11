package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V1928.class */
public class V1928 extends NamespacedSchema {
    public V1928(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    protected static void registerMob(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
        $$0.registerSimple($$1, $$2);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$1.remove("minecraft:illager_beast");
        registerMob($$0, $$1, "minecraft:ravager");
        return $$1;
    }
}

package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V1486.class */
public class V1486 extends NamespacedSchema {
    public V1486(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$1.put("minecraft:cod", $$1.remove("minecraft:cod_mob"));
        $$1.put("minecraft:salmon", $$1.remove("minecraft:salmon_mob"));
        return $$1;
    }
}

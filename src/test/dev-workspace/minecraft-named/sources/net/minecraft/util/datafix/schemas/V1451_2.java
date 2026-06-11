package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V1451_2.class */
public class V1451_2 extends NamespacedSchema {
    public V1451_2(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
        $$0.register($$1, "minecraft:piston", $$12 -> {
            return DSL.optionalFields("blockState", References.BLOCK_STATE.in($$0));
        });
        return $$1;
    }
}

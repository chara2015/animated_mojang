package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V3439.class */
public class V3439 extends NamespacedSchema {
    public V3439(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
        register($$1, "minecraft:sign", () -> {
            return sign($$0);
        });
        return $$1;
    }

    public static TypeTemplate sign(Schema $$0) {
        return DSL.optionalFields("front_text", DSL.optionalFields("messages", DSL.list(References.TEXT_COMPONENT.in($$0)), "filtered_messages", DSL.list(References.TEXT_COMPONENT.in($$0))), "back_text", DSL.optionalFields("messages", DSL.list(References.TEXT_COMPONENT.in($$0)), "filtered_messages", DSL.list(References.TEXT_COMPONENT.in($$0))));
    }
}

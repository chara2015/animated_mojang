package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V4292.class */
public class V4292 extends NamespacedSchema {
    public V4292(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
        super.registerTypes($$0, $$1, $$2);
        $$0.registerType(true, References.TEXT_COMPONENT, () -> {
            return DSL.or(DSL.or(DSL.constType(DSL.string()), DSL.list(References.TEXT_COMPONENT.in($$0))), DSL.optionalFields("extra", DSL.list(References.TEXT_COMPONENT.in($$0)), "separator", References.TEXT_COMPONENT.in($$0), "hover_event", DSL.taggedChoice("action", DSL.string(), Map.of("show_text", DSL.optionalFields("value", References.TEXT_COMPONENT.in($$0)), "show_item", References.ITEM_STACK.in($$0), "show_entity", DSL.optionalFields(Entity.TAG_ID, References.ENTITY_NAME.in($$0), JigsawBlockEntity.NAME, References.TEXT_COMPONENT.in($$0))))));
        });
    }
}

package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V4290.class */
public class V4290 extends NamespacedSchema {
    public V4290(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
        super.registerTypes($$0, $$1, $$2);
        $$0.registerType(true, References.TEXT_COMPONENT, () -> {
            return DSL.or(DSL.or(DSL.constType(DSL.string()), DSL.list(References.TEXT_COMPONENT.in($$0))), DSL.optionalFields("extra", DSL.list(References.TEXT_COMPONENT.in($$0)), "separator", References.TEXT_COMPONENT.in($$0), "hoverEvent", DSL.taggedChoice("action", DSL.string(), Map.of("show_text", DSL.optionalFields("contents", References.TEXT_COMPONENT.in($$0)), "show_item", DSL.optionalFields("contents", DSL.or(References.ITEM_STACK.in($$0), References.ITEM_NAME.in($$0))), "show_entity", DSL.optionalFields(ChunkRegionIoEvent.Fields.TYPE, References.ENTITY_NAME.in($$0), JigsawBlockEntity.NAME, References.TEXT_COMPONENT.in($$0))))));
        });
    }
}

package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V3083.class */
public class V3083 extends NamespacedSchema {
    public V3083(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$0.register($$1, "minecraft:allay", () -> {
            return DSL.optionalFields(InventoryCarrier.TAG_INVENTORY, DSL.list(References.ITEM_STACK.in($$0)), VibrationSystem.Data.NBT_TAG_KEY, DSL.optionalFields("event", DSL.optionalFields("game_event", References.GAME_EVENT_NAME.in($$0))));
        });
        return $$1;
    }
}

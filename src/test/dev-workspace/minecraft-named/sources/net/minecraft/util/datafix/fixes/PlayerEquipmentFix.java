package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/PlayerEquipmentFix.class */
public class PlayerEquipmentFix extends DataFix {
    private static final Map<Integer, String> SLOT_TRANSLATIONS = Map.of(100, PartNames.FEET, Integer.valueOf(ClientboundGameEventPacket.DEMO_PARAM_HINT_1), "legs", Integer.valueOf(ClientboundGameEventPacket.DEMO_PARAM_HINT_2), "chest", Integer.valueOf(ClientboundGameEventPacket.DEMO_PARAM_HINT_3), PartNames.HEAD, -106, "offhand");

    public PlayerEquipmentFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getTypeRaw(References.PLAYER);
        Type<?> $$1 = getOutputSchema().getTypeRaw(References.PLAYER);
        return writeFixAndRead("Player Equipment Fix", $$0, $$1, $$02 -> {
            Map<Dynamic<?>, Dynamic<?>> $$12 = new HashMap<>();
            Dynamic $$02 = $$02.update(InventoryCarrier.TAG_INVENTORY, $$13 -> {
                return $$13.createList($$13.asStream().filter($$2 -> {
                    int $$3 = $$2.get("Slot").asInt(-1);
                    String $$4 = SLOT_TRANSLATIONS.get(Integer.valueOf($$3));
                    if ($$4 != null) {
                        $$12.put($$13.createString($$4), $$2.remove("Slot"));
                    }
                    return $$4 == null;
                }));
            });
            return $$02.set(LivingEntity.TAG_EQUIPMENT, $$02.createMap($$12));
        });
    }
}

package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/PlayerUUIDFix.class */
public class PlayerUUIDFix extends AbstractUUIDFix {
    public PlayerUUIDFix(Schema $$0) {
        super($$0, References.PLAYER);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("PlayerUUIDFix", getInputSchema().getType(this.typeReference), $$0 -> {
            OpticFinder<?> $$1 = $$0.getType().findField("RootVehicle");
            return $$0.updateTyped($$1, $$1.type(), $$0 -> {
                return $$0.update(DSL.remainderFinder(), $$0 -> {
                    return replaceUUIDLeastMost($$0, "Attach", "Attach").orElse($$0);
                });
            }).update(DSL.remainderFinder(), $$02 -> {
                return EntityUUIDFix.updateEntityUUID(EntityUUIDFix.updateLivingEntity($$02));
            });
        });
    }
}

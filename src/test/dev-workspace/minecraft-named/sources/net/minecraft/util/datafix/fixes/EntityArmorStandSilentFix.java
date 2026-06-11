package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityArmorStandSilentFix.class */
public class EntityArmorStandSilentFix extends NamedEntityFix {
    public EntityArmorStandSilentFix(Schema $$0, boolean $$1) {
        super($$0, $$1, "EntityArmorStandSilentFix", References.ENTITY, "ArmorStand");
    }

    public Dynamic<?> fixTag(Dynamic<?> $$0) {
        if ($$0.get(Entity.TAG_SILENT).asBoolean(false) && !$$0.get("Marker").asBoolean(false)) {
            return $$0.remove(Entity.TAG_SILENT);
        }
        return $$0;
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), this::fixTag);
    }
}

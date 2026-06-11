package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityGoatMissingStateFix.class */
public class EntityGoatMissingStateFix extends NamedEntityFix {
    public EntityGoatMissingStateFix(Schema $$0) {
        super($$0, false, "EntityGoatMissingStateFix", References.ENTITY, "minecraft:goat");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), $$02 -> {
            return $$02.set("HasLeftHorn", $$02.createBoolean(true)).set("HasRightHorn", $$02.createBoolean(true));
        });
    }
}

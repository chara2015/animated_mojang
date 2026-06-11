package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/StriderGravityFix.class */
public class StriderGravityFix extends NamedEntityFix {
    public StriderGravityFix(Schema $$0, boolean $$1) {
        super($$0, $$1, "StriderGravityFix", References.ENTITY, "minecraft:strider");
    }

    public Dynamic<?> fixTag(Dynamic<?> $$0) {
        if ($$0.get(Entity.TAG_NO_GRAVITY).asBoolean(false)) {
            return $$0.set(Entity.TAG_NO_GRAVITY, $$0.createBoolean(false));
        }
        return $$0;
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), this::fixTag);
    }
}

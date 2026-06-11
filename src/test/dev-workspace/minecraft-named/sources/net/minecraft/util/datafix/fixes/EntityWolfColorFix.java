package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityWolfColorFix.class */
public class EntityWolfColorFix extends NamedEntityFix {
    public EntityWolfColorFix(Schema $$0, boolean $$1) {
        super($$0, $$1, "EntityWolfColorFix", References.ENTITY, "minecraft:wolf");
    }

    public Dynamic<?> fixTag(Dynamic<?> $$0) {
        return $$0.update("CollarColor", $$02 -> {
            return $$02.createByte((byte) (15 - $$02.asInt(0)));
        });
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), this::fixTag);
    }
}

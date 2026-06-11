package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/RemoveGolemGossipFix.class */
public class RemoveGolemGossipFix extends NamedEntityFix {
    public RemoveGolemGossipFix(Schema $$0, boolean $$1) {
        super($$0, $$1, "Remove Golem Gossip Fix", References.ENTITY, "minecraft:villager");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), RemoveGolemGossipFix::fixValue);
    }

    private static Dynamic<?> fixValue(Dynamic<?> $$0) {
        return $$0.update("Gossips", $$1 -> {
            return $$0.createList($$1.asStream().filter($$02 -> {
                return !$$02.get("Type").asString("").equals("golem");
            }));
        });
    }
}

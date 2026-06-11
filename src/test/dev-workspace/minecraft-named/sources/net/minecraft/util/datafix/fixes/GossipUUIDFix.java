package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/GossipUUIDFix.class */
public class GossipUUIDFix extends NamedEntityFix {
    public GossipUUIDFix(Schema $$0, String $$1) {
        super($$0, false, "Gossip for for " + $$1, References.ENTITY, $$1);
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), $$02 -> {
            return $$02.update("Gossips", $$02 -> {
                Optional map = $$02.asStreamOpt().result().map($$02 -> {
                    return $$02.map($$02 -> {
                        return AbstractUUIDFix.replaceUUIDLeastMost($$02, "Target", "Target").orElse($$02);
                    });
                });
                Objects.requireNonNull($$02);
                return (Dynamic) DataFixUtils.orElse(map.map($$02::createList), $$02);
            });
        });
    }
}

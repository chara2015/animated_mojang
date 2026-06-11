package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityStringUuidFix.class */
public class EntityStringUuidFix extends DataFix {
    public EntityStringUuidFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("EntityStringUuidFix", getInputSchema().getType(References.ENTITY), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                Optional<String> $$1 = $$0.get(Entity.TAG_UUID).asString().result();
                if ($$1.isPresent()) {
                    UUID $$2 = UUID.fromString($$1.get());
                    return $$0.remove(Entity.TAG_UUID).set("UUIDMost", $$0.createLong($$2.getMostSignificantBits())).set("UUIDLeast", $$0.createLong($$2.getLeastSignificantBits()));
                }
                return $$0;
            });
        });
    }
}

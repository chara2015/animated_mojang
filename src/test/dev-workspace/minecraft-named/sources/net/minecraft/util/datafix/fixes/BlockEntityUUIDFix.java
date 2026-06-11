package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BlockEntityUUIDFix.class */
public class BlockEntityUUIDFix extends AbstractUUIDFix {
    public BlockEntityUUIDFix(Schema $$0) {
        super($$0, References.BLOCK_ENTITY);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("BlockEntityUUIDFix", getInputSchema().getType(this.typeReference), $$0 -> {
            return updateNamedChoice(updateNamedChoice($$0, "minecraft:conduit", this::updateConduit), "minecraft:skull", this::updateSkull);
        });
    }

    private Dynamic<?> updateSkull(Dynamic<?> $$0) {
        return (Dynamic) $$0.get("Owner").get().map($$02 -> {
            return replaceUUIDString($$02, "Id", "Id").orElse($$02);
        }).map($$1 -> {
            return $$0.remove("Owner").set("SkullOwner", $$1);
        }).result().orElse($$0);
    }

    private Dynamic<?> updateConduit(Dynamic<?> $$0) {
        return replaceUUIDMLTag($$0, "target_uuid", "Target").orElse($$0);
    }
}

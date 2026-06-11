package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/JigsawPropertiesFix.class */
public class JigsawPropertiesFix extends NamedEntityFix {
    public JigsawPropertiesFix(Schema $$0, boolean $$1) {
        super($$0, $$1, "JigsawPropertiesFix", References.BLOCK_ENTITY, "minecraft:jigsaw");
    }

    private static Dynamic<?> fixTag(Dynamic<?> $$0) {
        String $$1 = $$0.get("attachement_type").asString("minecraft:empty");
        String $$2 = $$0.get("target_pool").asString("minecraft:empty");
        return $$0.set(JigsawBlockEntity.NAME, $$0.createString($$1)).set(JigsawBlockEntity.TARGET, $$0.createString($$1)).remove("attachement_type").set(JigsawBlockEntity.POOL, $$0.createString($$2)).remove("target_pool");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), JigsawPropertiesFix::fixTag);
    }
}

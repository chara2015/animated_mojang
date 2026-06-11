package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/RemoveEmptyItemInBrushableBlockFix.class */
public class RemoveEmptyItemInBrushableBlockFix extends NamedEntityWriteReadFix {
    public RemoveEmptyItemInBrushableBlockFix(Schema $$0) {
        super($$0, false, "RemoveEmptyItemInSuspiciousBlockFix", References.BLOCK_ENTITY, "minecraft:brushable_block");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityWriteReadFix
    protected <T> Dynamic<T> fix(Dynamic<T> $$0) {
        Optional<Dynamic<T>> $$1 = $$0.get(DecoratedPotBlockEntity.TAG_ITEM).result();
        if ($$1.isPresent() && isEmptyStack($$1.get())) {
            return $$0.remove(DecoratedPotBlockEntity.TAG_ITEM);
        }
        return $$0;
    }

    private static boolean isEmptyStack(Dynamic<?> $$0) {
        String $$1 = NamespacedSchema.ensureNamespaced($$0.get(Entity.TAG_ID).asString(JigsawBlockEntity.DEFAULT_FINAL_STATE));
        int $$2 = $$0.get("count").asInt(0);
        return $$1.equals(JigsawBlockEntity.DEFAULT_FINAL_STATE) || $$2 == 0;
    }
}

package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BlockEntityShulkerBoxColorFix.class */
public class BlockEntityShulkerBoxColorFix extends NamedEntityFix {
    public BlockEntityShulkerBoxColorFix(Schema $$0, boolean $$1) {
        super($$0, $$1, "BlockEntityShulkerBoxColorFix", References.BLOCK_ENTITY, "minecraft:shulker_box");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), $$02 -> {
            return $$02.remove("Color");
        });
    }
}

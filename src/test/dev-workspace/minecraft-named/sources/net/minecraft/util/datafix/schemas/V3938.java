package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V3938.class */
public class V3938 extends NamespacedSchema {
    public V3938(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static TypeTemplate abstractArrow(Schema $$0) {
        return DSL.optionalFields("inBlockState", References.BLOCK_STATE.in($$0), DecoratedPotBlockEntity.TAG_ITEM, References.ITEM_STACK.in($$0), "weapon", References.ITEM_STACK.in($$0));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$0.register($$1, "minecraft:spectral_arrow", () -> {
            return abstractArrow($$0);
        });
        $$0.register($$1, "minecraft:arrow", () -> {
            return abstractArrow($$0);
        });
        return $$1;
    }
}

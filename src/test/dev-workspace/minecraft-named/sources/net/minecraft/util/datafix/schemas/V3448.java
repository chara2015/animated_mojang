package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V3448.class */
public class V3448 extends NamespacedSchema {
    public V3448(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
        $$0.register($$1, "minecraft:decorated_pot", () -> {
            return DSL.optionalFields(DecoratedPotBlockEntity.TAG_SHERDS, DSL.list(References.ITEM_NAME.in($$0)), DecoratedPotBlockEntity.TAG_ITEM, References.ITEM_STACK.in($$0));
        });
        return $$1;
    }
}

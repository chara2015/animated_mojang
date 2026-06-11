package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V102.class */
public class V102 extends Schema {
    public V102(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
        super.registerTypes($$0, $$1, $$2);
        $$0.registerType(true, References.ITEM_STACK, () -> {
            return DSL.hook(DSL.optionalFields(Entity.TAG_ID, References.ITEM_NAME.in($$0), "tag", V99.itemStackTag($$0)), V99.ADD_NAMES, Hook.HookFunction.IDENTITY);
        });
    }
}

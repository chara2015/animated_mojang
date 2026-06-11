package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EmptyItemInVillagerTradeFix.class */
public class EmptyItemInVillagerTradeFix extends DataFix {
    public EmptyItemInVillagerTradeFix(Schema $$0) {
        super($$0, false);
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.VILLAGER_TRADE);
        return writeFixAndRead("EmptyItemInVillagerTradeFix", $$0, $$0, $$02 -> {
            Dynamic<?> $$1 = $$02.get("buyB").orElseEmptyMap();
            String $$2 = NamespacedSchema.ensureNamespaced($$1.get(Entity.TAG_ID).asString(JigsawBlockEntity.DEFAULT_FINAL_STATE));
            int $$3 = $$1.get("count").asInt(0);
            if ($$2.equals(JigsawBlockEntity.DEFAULT_FINAL_STATE) || $$3 == 0) {
                return $$02.remove("buyB");
            }
            return $$02;
        });
    }
}

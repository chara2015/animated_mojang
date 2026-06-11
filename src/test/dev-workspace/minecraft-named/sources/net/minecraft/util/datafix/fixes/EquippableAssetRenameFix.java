package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EquippableAssetRenameFix.class */
public class EquippableAssetRenameFix extends DataFix {
    public EquippableAssetRenameFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.DATA_COMPONENTS);
        OpticFinder<?> $$1 = $$0.findField("minecraft:equippable");
        return fixTypeEverywhereTyped("equippable asset rename fix", $$0, $$12 -> {
            return $$12.updateTyped($$1, $$02 -> {
                return $$02.update(DSL.remainderFinder(), $$02 -> {
                    return $$02.renameField("model", "asset_id");
                });
            });
        });
    }
}

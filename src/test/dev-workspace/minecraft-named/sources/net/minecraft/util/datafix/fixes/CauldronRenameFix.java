package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.world.level.block.state.StateHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/CauldronRenameFix.class */
public class CauldronRenameFix extends DataFix {
    public CauldronRenameFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    private static Dynamic<?> fix(Dynamic<?> $$0) {
        Optional<String> $$1 = $$0.get(StateHolder.NAME_TAG).asString().result();
        if ($$1.equals(Optional.of("minecraft:cauldron"))) {
            Dynamic<?> $$2 = $$0.get(StateHolder.PROPERTIES_TAG).orElseEmptyMap();
            if ($$2.get("level").asString("0").equals("0")) {
                return $$0.remove(StateHolder.PROPERTIES_TAG);
            }
            return $$0.set(StateHolder.NAME_TAG, $$0.createString("minecraft:water_cauldron"));
        }
        return $$0;
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("cauldron_rename_fix", getInputSchema().getType(References.BLOCK_STATE), $$0 -> {
            return $$0.update(DSL.remainderFinder(), CauldronRenameFix::fix);
        });
    }
}

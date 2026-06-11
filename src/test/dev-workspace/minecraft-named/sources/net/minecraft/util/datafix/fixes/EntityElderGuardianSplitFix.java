package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityElderGuardianSplitFix.class */
public class EntityElderGuardianSplitFix extends SimpleEntityRenameFix {
    public EntityElderGuardianSplitFix(Schema $$0, boolean $$1) {
        super("EntityElderGuardianSplitFix", $$0, $$1);
    }

    @Override // net.minecraft.util.datafix.fixes.SimpleEntityRenameFix
    protected Pair<String, Dynamic<?>> getNewNameAndTag(String $$0, Dynamic<?> $$1) {
        return Pair.of((Objects.equals($$0, "Guardian") && $$1.get("Elder").asBoolean(false)) ? "ElderGuardian" : $$0, $$1);
    }
}

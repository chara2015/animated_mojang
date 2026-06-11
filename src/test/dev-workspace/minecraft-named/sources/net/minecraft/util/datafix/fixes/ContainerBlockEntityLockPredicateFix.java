package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.world.LockCode;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ContainerBlockEntityLockPredicateFix.class */
public class ContainerBlockEntityLockPredicateFix extends DataFix {
    public ContainerBlockEntityLockPredicateFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("ContainerBlockEntityLockPredicateFix", getInputSchema().findChoiceType(References.BLOCK_ENTITY), ContainerBlockEntityLockPredicateFix::fixBlockEntity);
    }

    private static Typed<?> fixBlockEntity(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), $$02 -> {
            return $$02.renameAndFixField("Lock", LockCode.TAG_LOCK, LockComponentPredicateFix::fixLock);
        });
    }
}

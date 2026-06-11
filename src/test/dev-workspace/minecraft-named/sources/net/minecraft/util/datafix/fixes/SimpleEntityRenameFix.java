package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/SimpleEntityRenameFix.class */
public abstract class SimpleEntityRenameFix extends EntityRenameFix {
    protected abstract Pair<String, Dynamic<?>> getNewNameAndTag(String str, Dynamic<?> dynamic);

    public SimpleEntityRenameFix(String $$0, Schema $$1, boolean $$2) {
        super($$0, $$1, $$2);
    }

    @Override // net.minecraft.util.datafix.fixes.EntityRenameFix
    protected Pair<String, Typed<?>> fix(String $$0, Typed<?> $$1) {
        Pair<String, Dynamic<?>> $$2 = getNewNameAndTag($$0, (Dynamic) $$1.getOrCreate(DSL.remainderFinder()));
        return Pair.of((String) $$2.getFirst(), $$1.set(DSL.remainderFinder(), (Dynamic) $$2.getSecond()));
    }
}

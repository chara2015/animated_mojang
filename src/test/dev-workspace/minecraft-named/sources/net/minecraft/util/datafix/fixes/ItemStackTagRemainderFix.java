package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.Predicate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ItemStackTagRemainderFix.class */
public abstract class ItemStackTagRemainderFix extends ItemStackTagFix {
    protected abstract <T> Dynamic<T> fixItemStackTag(Dynamic<T> dynamic);

    public ItemStackTagRemainderFix(Schema $$0, String $$1, Predicate<String> $$2) {
        super($$0, $$1, $$2);
    }

    @Override // net.minecraft.util.datafix.fixes.ItemStackTagFix
    protected final Typed<?> fixItemStackTag(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), this::fixItemStackTag);
    }
}

package net.minecraft.client.multiplayer;

import java.util.function.Function;
import net.minecraft.client.multiplayer.CacheSlot.Cleaner;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/CacheSlot.class */
public class CacheSlot<C extends Cleaner<C>, D> {
    private final Function<C, D> operation;
    private C context;
    private D value;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/CacheSlot$Cleaner.class */
    @FunctionalInterface
    public interface Cleaner<C extends Cleaner<C>> {
        void registerForCleaning(CacheSlot<C, ?> cacheSlot);
    }

    public CacheSlot(Function<C, D> $$0) {
        this.operation = $$0;
    }

    public D compute(C $$0) {
        if ($$0 == this.context && this.value != null) {
            return this.value;
        }
        D $$1 = this.operation.apply($$0);
        this.value = $$1;
        this.context = $$0;
        $$0.registerForCleaning(this);
        return $$1;
    }

    public void clear() {
        this.value = null;
        this.context = null;
    }
}

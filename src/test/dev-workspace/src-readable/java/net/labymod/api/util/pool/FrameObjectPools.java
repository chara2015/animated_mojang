package net.labymod.api.util.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/pool/FrameObjectPools.class */
public final class FrameObjectPools {
    private static final FrameObjectPools INSTANCE = new FrameObjectPools();
    private final List<ObjectPool<?>> pools = new ArrayList();

    private FrameObjectPools() {
    }

    public static FrameObjectPools instance() {
        return INSTANCE;
    }

    public <T extends Poolable> ObjectPool<T> register(Supplier<T> factory) {
        ObjectPool<T> pool = ObjectPool.create(factory);
        this.pools.add(pool);
        return pool;
    }

    public <T> ObjectPool<T> register(Supplier<T> factory, Consumer<T> resetter) {
        ObjectPool<T> pool = ObjectPool.create(factory, resetter);
        this.pools.add(pool);
        return pool;
    }

    public void resetAll() {
        for (ObjectPool<?> pool : this.pools) {
            pool.reset();
        }
    }
}

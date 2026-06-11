package net.labymod.api.util.pool;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.Laby;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/pool/ObjectPools.class */
public final class ObjectPools {
    private ObjectPools() {
    }

    public static <T extends Poolable> ObjectPool<T> create(Supplier<T> factory) {
        if (isDevelopmentEnvironment()) {
            return DebugObjectPool.create((Supplier) factory);
        }
        return ObjectPool.create(factory);
    }

    public static <T> ObjectPool<T> create(Supplier<T> factory, Consumer<T> resetter) {
        if (isDevelopmentEnvironment()) {
            return DebugObjectPool.create((Supplier) factory, (Consumer) resetter);
        }
        return ObjectPool.create(factory, resetter);
    }

    private static boolean isDevelopmentEnvironment() {
        return Laby.labyAPI().labyModLoader().isDevelopmentEnvironment();
    }
}

package net.labymod.api.util.pool;

import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/pool/DebugObjectPool.class */
public class DebugObjectPool<T> extends ObjectPool<T> {
    private final Reference2IntMap<T> acquisitionGeneration;
    private int generation;

    private DebugObjectPool(Supplier<T> factory, Consumer<T> resetter) {
        super(factory, resetter);
        this.acquisitionGeneration = new Reference2IntOpenHashMap();
        this.acquisitionGeneration.defaultReturnValue(-1);
        this.generation = 0;
    }

    public static <T extends Poolable> DebugObjectPool<T> create(Supplier<T> factory) {
        return new DebugObjectPool<>(factory, (v0) -> {
            v0.reset();
        });
    }

    public static <T> DebugObjectPool<T> create(Supplier<T> factory, Consumer<T> resetter) {
        return new DebugObjectPool<>(factory, resetter);
    }

    @Override // net.labymod.api.util.pool.ObjectPool
    public T acquire() {
        T t = (T) super.acquire();
        if (this.acquisitionGeneration.put(t, this.generation) == this.generation) {
            throw new IllegalStateException("Object acquired twice in the same generation: " + String.valueOf(t));
        }
        return t;
    }

    @Override // net.labymod.api.util.pool.ObjectPool
    public void reset() {
        super.reset();
        this.generation++;
        this.acquisitionGeneration.clear();
    }

    public int generation() {
        return this.generation;
    }
}

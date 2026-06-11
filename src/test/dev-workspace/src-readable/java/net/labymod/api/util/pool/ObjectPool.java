package net.labymod.api.util.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/pool/ObjectPool.class */
public class ObjectPool<T> {
    private final Supplier<T> factory;
    private final Consumer<T> resetter;
    private final List<T> objects = new ArrayList();
    private int cursor = 0;

    protected ObjectPool(Supplier<T> factory, Consumer<T> resetter) {
        this.factory = factory;
        this.resetter = resetter;
    }

    public static <T extends Poolable> ObjectPool<T> create(Supplier<T> factory) {
        return new ObjectPool<>(factory, (v0) -> {
            v0.reset();
        });
    }

    public static <T> ObjectPool<T> create(Supplier<T> factory, Consumer<T> resetter) {
        return new ObjectPool<>(factory, resetter);
    }

    public T acquire() {
        T t;
        if (this.cursor < this.objects.size()) {
            t = this.objects.get(this.cursor);
            this.resetter.accept(t);
        } else {
            t = this.factory.get();
            this.objects.add(t);
        }
        this.cursor++;
        return t;
    }

    public void reset() {
        this.cursor = 0;
    }

    public int capacity() {
        return this.objects.size();
    }

    public int activeCount() {
        return this.cursor;
    }
}

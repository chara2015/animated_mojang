package net.labymod.core.util;

import java.util.function.IntFunction;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/GrowableArrayIndex.class */
public class GrowableArrayIndex<T> extends ArrayIndex<T> {
    public GrowableArrayIndex(int capacity, IntFunction<T[]> arrayConstructor) {
        super(capacity, arrayConstructor);
    }

    @Override // net.labymod.core.util.ArrayIndex
    public void set(int index, T value) {
        if (index >= size()) {
            grow(size() * 2);
        }
        super.set(index, value);
    }

    @Override // net.labymod.core.util.ArrayIndex
    @Nullable
    public T get(int i) {
        if (i >= size()) {
            grow(size() * 2);
        }
        return (T) super.get(i);
    }
}

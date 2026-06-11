package net.labymod.core.util;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/ArrayIndex.class */
public class ArrayIndex<T> {
    private final IntFunction<T[]> arrayConstructor;
    private T[] elementData;

    public ArrayIndex(int capacity, IntFunction<T[]> arrayConstructor) {
        this.elementData = arrayConstructor.apply(capacity);
        this.arrayConstructor = arrayConstructor;
    }

    public void set(int index, T value) {
        if (index < 0 || index >= this.elementData.length) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Index %s out of bounds for length %s", Integer.valueOf(index), Integer.valueOf(this.elementData.length)));
        }
        this.elementData[index] = value;
    }

    @Nullable
    public T get(int index) {
        if (index < 0 || index >= this.elementData.length) {
            return null;
        }
        return this.elementData[index];
    }

    public void grow(int capacity) {
        int length = this.elementData.length;
        if (capacity >= length) {
            T[] newValue = this.arrayConstructor.apply(capacity);
            System.arraycopy(this.elementData, 0, newValue, 0, length);
            this.elementData = newValue;
        }
    }

    public void fill(Supplier<T> factory) {
        for (int i = 0; i < this.elementData.length; i++) {
            T element = factory.get();
            this.elementData[i] = element;
        }
    }

    public void fill(IntFunction<T> factory) {
        for (int i = 0; i < this.elementData.length; i++) {
            this.elementData[i] = factory.apply(i);
        }
    }

    public void clear() {
        Arrays.fill(this.elementData, (Object) null);
    }

    public int size() {
        return this.elementData.length;
    }

    public void forEach(Consumer<T> consumer) {
        if (consumer == null) {
            return;
        }
        for (T t : this.elementData) {
            consumer.accept(t);
        }
    }
}

package net.labymod.api.util;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/Lazy.class */
public final class Lazy<T> {
    private final Supplier<T> supplier;
    private T value;

    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public static <C> Lazy<C> of(Supplier<C> supplier) {
        return new Lazy<>(supplier);
    }

    public T get() {
        if (this.value == null) {
            this.value = this.supplier.get();
        }
        return this.value;
    }

    public void reset() {
        this.value = null;
    }

    public void make(Consumer<T> t) {
        t.accept(get());
    }
}

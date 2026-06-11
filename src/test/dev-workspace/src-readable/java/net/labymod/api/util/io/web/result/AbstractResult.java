package net.labymod.api.util.io.web.result;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/result/AbstractResult.class */
public abstract class AbstractResult<T> {
    protected T value;

    protected AbstractResult(@Nullable T value) {
        this.value = value;
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public boolean isEmpty() {
        return !isPresent();
    }

    @NotNull
    public T get() {
        if (!isPresent()) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    @Nullable
    public T getNullable() {
        return this.value;
    }

    @Nullable
    public T getOrDefault(@Nullable T defaultValue) {
        return isPresent() ? this.value : defaultValue;
    }

    @Nullable
    public T getOrCompute(@NotNull Supplier<T> supplier) {
        return isPresent() ? this.value : supplier.get();
    }

    public void ifPresent(@NotNull Consumer<T> present) {
        Objects.requireNonNull(present, "Consumer cannot be null");
        if (isPresent()) {
            present.accept(this.value);
        }
    }
}

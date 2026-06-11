package net.labymod.api.util.function;

import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/function/IntIntTriFunction.class */
public interface IntIntTriFunction<T, R> {
    R apply(int i, int i2, T t);

    default <W> IntIntTriFunction<T, W> andThen(Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (i0, i1, obj) -> {
            return after.apply(apply(i0, i1, obj));
        };
    }
}

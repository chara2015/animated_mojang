package net.labymod.api.util.function;

import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/function/TriFunction.class */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);

    default <W> TriFunction<T, U, V, W> andThen(Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (obj, obj2, obj3) -> {
            return after.apply(apply(obj, obj2, obj3));
        };
    }
}

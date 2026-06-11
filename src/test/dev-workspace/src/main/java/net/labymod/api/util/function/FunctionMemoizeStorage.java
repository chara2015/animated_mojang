package net.labymod.api.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/function/FunctionMemoizeStorage.class */
@Referenceable
public interface FunctionMemoizeStorage {
    <T, R> Function<T, R> memoize(Function<T, R> function);

    <T, U, R> BiFunction<T, U, R> memoize(BiFunction<T, U, R> biFunction);

    <T, U, V, R> TriFunction<T, U, V, R> memoize(TriFunction<T, U, V, R> triFunction);

    <T, R> IntIntTriFunction<T, R> memoize(IntIntTriFunction<T, R> intIntTriFunction);

    <R> IntFunction<R> memoizeInt(IntFunction<R> intFunction);
}

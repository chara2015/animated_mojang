package net.labymod.core.util.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.models.Implements;
import net.labymod.api.util.IntIntTriple;
import net.labymod.api.util.Pair;
import net.labymod.api.util.Triple;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.util.function.IntIntTriFunction;
import net.labymod.api.util.function.TriFunction;
import net.labymod.core.util.collection.ConcurrentTimestampedCache;
import net.labymod.core.util.collection.TimestampedValue;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/function/DefaultFunctionMemoizeStorage.class */
@Singleton
@Implements(FunctionMemoizeStorage.class)
public final class DefaultFunctionMemoizeStorage implements FunctionMemoizeStorage {
    @Inject
    public DefaultFunctionMemoizeStorage() {
    }

    public static <T, R> R computeIfAbsent(ConcurrentTimestampedCache<T, R> concurrentTimestampedCache, T t, Function<T, R> function) {
        return (R) computeIfAbsent(concurrentTimestampedCache, t, Function.identity(), function);
    }

    private static <T, R> R computeIfAbsent(ConcurrentTimestampedCache<T, R> cache, T t, Function<T, T> transformFunction, Function<T, R> valueFactory) {
        TimestampedValue<R> value = cache.get((Object) t);
        if (value == null) {
            T newKey = transformFunction.apply(t);
            TimestampedValue<R> newValue = new TimestampedValue<>(valueFactory.apply(newKey));
            cache.put(newKey, newValue);
            value = newValue;
        }
        return value.getValue();
    }

    @Override // net.labymod.api.util.function.FunctionMemoizeStorage
    public <T, R> Function<T, R> memoize(final Function<T, R> function) {
        return new Function<T, R>(this) { // from class: net.labymod.core.util.function.DefaultFunctionMemoizeStorage.1
            private final ConcurrentTimestampedCache<T, R> cache = new ConcurrentTimestampedCache<>(8);

            @Override // java.util.function.Function
            public R apply(T t) {
                return (R) DefaultFunctionMemoizeStorage.computeIfAbsent(this.cache, t, function);
            }
        };
    }

    @Override // net.labymod.api.util.function.FunctionMemoizeStorage
    public <T, U, R> BiFunction<T, U, R> memoize(final BiFunction<T, U, R> function) {
        return new BiFunction<T, U, R>(this) { // from class: net.labymod.core.util.function.DefaultFunctionMemoizeStorage.2
            private final ConcurrentTimestampedCache<Pair<T, U>, R> cache = new ConcurrentTimestampedCache<>(8);
            private final Pair<T, U> sharedPair = Pair.of(null, null);

            @Override // java.util.function.BiFunction
            public R apply(T t, U u) {
                this.sharedPair.set(t, u);
                ConcurrentTimestampedCache<Pair<T, U>, R> concurrentTimestampedCache = this.cache;
                Pair<T, U> pair = this.sharedPair;
                Function function2 = (v0) -> {
                    return v0.copy();
                };
                BiFunction biFunction = function;
                return (R) DefaultFunctionMemoizeStorage.computeIfAbsent(concurrentTimestampedCache, pair, function2, pair2 -> {
                    return biFunction.apply(pair2.getFirst(), pair2.getSecond());
                });
            }
        };
    }

    @Override // net.labymod.api.util.function.FunctionMemoizeStorage
    public <T, U, V, R> TriFunction<T, U, V, R> memoize(final TriFunction<T, U, V, R> function) {
        return new TriFunction<T, U, V, R>(this) { // from class: net.labymod.core.util.function.DefaultFunctionMemoizeStorage.3
            private final ConcurrentTimestampedCache<Triple<T, U, V>, R> cache = new ConcurrentTimestampedCache<>(8);
            private final Triple<T, U, V> sharedTriple = Triple.of(null, null, null);

            @Override // net.labymod.api.util.function.TriFunction
            public R apply(T t, U u, V v) {
                this.sharedTriple.set(t, u, v);
                ConcurrentTimestampedCache<Triple<T, U, V>, R> concurrentTimestampedCache = this.cache;
                Triple<T, U, V> triple = this.sharedTriple;
                Function function2 = (v0) -> {
                    return v0.copy();
                };
                TriFunction triFunction = function;
                return (R) DefaultFunctionMemoizeStorage.computeIfAbsent(concurrentTimestampedCache, triple, function2, triple2 -> {
                    return triFunction.apply(triple2.getLeft(), triple2.getMiddle(), triple2.getRight());
                });
            }
        };
    }

    @Override // net.labymod.api.util.function.FunctionMemoizeStorage
    public <T, R> IntIntTriFunction<T, R> memoize(final IntIntTriFunction<T, R> function) {
        return new IntIntTriFunction<T, R>(this) { // from class: net.labymod.core.util.function.DefaultFunctionMemoizeStorage.4
            private final ConcurrentTimestampedCache<IntIntTriple<T>, R> cache = new ConcurrentTimestampedCache<>(8);
            private final IntIntTriple<T> sharedTriple = new IntIntTriple<>(0, 0, null);

            @Override // net.labymod.api.util.function.IntIntTriFunction
            public R apply(int i, int i2, T t) {
                this.sharedTriple.set(i, i2, t);
                ConcurrentTimestampedCache<IntIntTriple<T>, R> concurrentTimestampedCache = this.cache;
                IntIntTriple<T> intIntTriple = this.sharedTriple;
                Function function2 = (v0) -> {
                    return v0.copy();
                };
                IntIntTriFunction intIntTriFunction = function;
                return (R) DefaultFunctionMemoizeStorage.computeIfAbsent(concurrentTimestampedCache, intIntTriple, function2, triple -> {
                    return intIntTriFunction.apply(triple.getLeft(), triple.getMiddle(), triple.getRight());
                });
            }
        };
    }

    @Override // net.labymod.api.util.function.FunctionMemoizeStorage
    public <R> IntFunction<R> memoizeInt(final IntFunction<R> function) {
        return new IntFunction<R>(this) { // from class: net.labymod.core.util.function.DefaultFunctionMemoizeStorage.5
            private final ConcurrentTimestampedCache<Integer, R> cache = new ConcurrentTimestampedCache<>(8);

            @Override // java.util.function.IntFunction
            public R apply(int i) {
                ConcurrentTimestampedCache<Integer, R> concurrentTimestampedCache = this.cache;
                Integer numValueOf = Integer.valueOf(i);
                IntFunction intFunction = function;
                Objects.requireNonNull(intFunction);
                return (R) DefaultFunctionMemoizeStorage.computeIfAbsent(concurrentTimestampedCache, numValueOf, (v1) -> {
                    return r2.apply(v1);
                });
            }
        };
    }
}

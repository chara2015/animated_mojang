package net.labymod.api.util.function;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.util.CollectionHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/function/Functional.class */
public final class Functional {
    public static <T> T of(Supplier<T> supplier) {
        return supplier.get();
    }

    public static <T> T of(T value, Consumer<T> consumer) {
        consumer.accept(value);
        return value;
    }

    public static <T> T[] toArray(Collection<T> collection, Class<T> cls, Consumer<Collection<T>> consumer) {
        consumer.accept(collection);
        return (T[]) CollectionHelper.mapArray(collection.toArray(), cls, f -> {
            return f;
        });
    }
}

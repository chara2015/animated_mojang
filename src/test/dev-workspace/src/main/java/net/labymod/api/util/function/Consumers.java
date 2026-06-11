package net.labymod.api.util.function;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/function/Consumers.class */
public final class Consumers {
    public static <T> void accept(Consumer<T> consumer, T value) {
        if (consumer == null) {
            return;
        }
        consumer.accept(value);
    }

    public static <T, U> void accept(BiConsumer<T, U> consumer, T first, U second) {
        if (consumer == null) {
            return;
        }
        consumer.accept(first, second);
    }
}

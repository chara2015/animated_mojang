package net.labymod.api.util.function;

import java.lang.Throwable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/function/ThrowableConsumer.class */
@FunctionalInterface
public interface ThrowableConsumer<T, E extends Throwable> {
    void accept(T t) throws Throwable;
}

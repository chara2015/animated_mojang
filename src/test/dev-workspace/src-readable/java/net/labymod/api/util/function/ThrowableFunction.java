package net.labymod.api.util.function;

import java.lang.Throwable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/function/ThrowableFunction.class */
@FunctionalInterface
public interface ThrowableFunction<T, R, E extends Throwable> {
    R apply(T t) throws Throwable;
}

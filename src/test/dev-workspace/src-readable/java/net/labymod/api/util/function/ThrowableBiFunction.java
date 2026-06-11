package net.labymod.api.util.function;

import java.lang.Throwable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/function/ThrowableBiFunction.class */
public interface ThrowableBiFunction<T, U, R, E extends Throwable> {
    R apply(T t, U u) throws Throwable;
}

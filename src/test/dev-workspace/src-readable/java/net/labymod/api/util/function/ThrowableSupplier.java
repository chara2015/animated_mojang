package net.labymod.api.util.function;

import java.lang.Throwable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/function/ThrowableSupplier.class */
public interface ThrowableSupplier<T, E extends Throwable> {
    T get() throws Throwable;
}

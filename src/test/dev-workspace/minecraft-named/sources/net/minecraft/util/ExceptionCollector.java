package net.minecraft.util;

import java.lang.Throwable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ExceptionCollector.class */
public class ExceptionCollector<T extends Throwable> {
    private T result;

    public void add(T $$0) {
        if (this.result == null) {
            this.result = $$0;
        } else {
            this.result.addSuppressed($$0);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: T extends java.lang.Throwable */
    public void throwIfPresent() throws Throwable {
        if (this.result != null) {
            throw this.result;
        }
    }
}

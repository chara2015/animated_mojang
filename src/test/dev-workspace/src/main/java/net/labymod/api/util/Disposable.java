package net.labymod.api.util;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/Disposable.class */
public interface Disposable {
    void dispose();

    default void addDisposeListener(Runnable listener) {
        throw new UnsupportedOperationException();
    }

    default boolean isDisposed() {
        return false;
    }
}

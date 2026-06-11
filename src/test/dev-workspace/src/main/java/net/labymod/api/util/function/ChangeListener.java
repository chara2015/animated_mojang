package net.labymod.api.util.function;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/function/ChangeListener.class */
@FunctionalInterface
public interface ChangeListener<T, V> {
    void changed(T t, V v, V v2);

    default void onException(Throwable throwable) {
    }
}

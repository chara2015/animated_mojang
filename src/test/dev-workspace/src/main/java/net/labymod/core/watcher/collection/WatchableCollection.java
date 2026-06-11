package net.labymod.core.watcher.collection;

import java.util.Collection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/watcher/collection/WatchableCollection.class */
public interface WatchableCollection<T> {
    void onAdd(T t);

    void onRemove(T t);

    void onClear();

    default void onAddAll(Collection<? extends T> c) {
        for (T t : c) {
            onAdd(t);
        }
    }
}

package net.labymod.core.watcher.list;

import net.labymod.core.watcher.collection.WatchableCollection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/watcher/list/WatchableList.class */
public interface WatchableList<T> extends WatchableCollection<T> {
    void onAdd(int i, T t);
}

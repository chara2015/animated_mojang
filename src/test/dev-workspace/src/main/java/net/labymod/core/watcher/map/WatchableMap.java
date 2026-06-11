package net.labymod.core.watcher.map;

import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/watcher/map/WatchableMap.class */
@ApiStatus.Experimental
public interface WatchableMap<K, V> {
    void onPut(K k, V v);

    void onClear();

    void onRemove(K k);

    void onRemove(K k, V v);
}

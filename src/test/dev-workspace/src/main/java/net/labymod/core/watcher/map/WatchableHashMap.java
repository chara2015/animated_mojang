package net.labymod.core.watcher.map;

import java.util.HashMap;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/watcher/map/WatchableHashMap.class */
@ApiStatus.Experimental
public class WatchableHashMap<K, V> extends HashMap<K, V> {
    private final WatchableMap<K, V> watchableMap;

    public WatchableHashMap() {
        this(new NoOperationWatchableMap());
    }

    public WatchableHashMap(WatchableMap<K, V> watchableMap) {
        this.watchableMap = watchableMap == null ? new NoOperationWatchableMap() : watchableMap;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public V put(K k, V v) {
        this.watchableMap.onPut(k, v);
        return (V) super.put(k, v);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        this.watchableMap.onRemove(obj);
        return (V) super.remove(obj);
    }

    @Override // java.util.HashMap, java.util.Map
    public boolean remove(Object key, Object value) {
        this.watchableMap.onRemove(key, value);
        return super.remove(key, value);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public void clear() {
        this.watchableMap.onClear();
        super.clear();
    }
}

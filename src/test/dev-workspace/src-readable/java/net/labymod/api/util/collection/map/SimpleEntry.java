package net.labymod.api.util.collection.map;

import java.util.Map;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/collection/map/SimpleEntry.class */
class SimpleEntry<K, V> implements Map.Entry<K, V> {
    private final K key;
    private final V value;

    public SimpleEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override // java.util.Map.Entry
    public K getKey() {
        return this.key;
    }

    @Override // java.util.Map.Entry
    public V getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }
}

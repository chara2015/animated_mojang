package net.labymod.api.util.collection.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/collection/map/HashMultimap.class */
public class HashMultimap<K, V> implements Multimap<K, V> {
    private final Map<K, Collection<V>> map;

    public HashMultimap() {
        this.map = new HashMap();
    }

    public HashMultimap(int initialCapacity) {
        this.map = new HashMap(initialCapacity);
    }

    public HashMultimap(int initialCapacity, float loadFactor) {
        this.map = new HashMap(initialCapacity, loadFactor);
    }

    public HashMultimap(Map<K, Collection<V>> entries) {
        this.map = new HashMap(entries);
    }

    @Override // net.labymod.api.util.collection.map.Multimap
    public int size() {
        return this.map.size();
    }

    @Override // net.labymod.api.util.collection.map.Multimap
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // net.labymod.api.util.collection.map.Multimap
    public void put(K key, V value) {
        Collection<V> values = this.map.computeIfAbsent(key, k -> {
            return new ArrayList();
        });
        values.add(value);
    }

    @Override // net.labymod.api.util.collection.map.Multimap
    public boolean remove(K key) {
        Collection<V> values = this.map.remove(key);
        return values != null;
    }

    @Override // net.labymod.api.util.collection.map.Multimap
    public boolean remove(K key, V value) {
        Collection<V> values = this.map.get(key);
        return values != null && values.remove(value);
    }

    @Override // net.labymod.api.util.collection.map.Multimap
    public void clear() {
        this.map.clear();
    }

    @Override // net.labymod.api.util.collection.map.Multimap
    public Collection<V> get(K key) {
        return this.map.computeIfAbsent(key, k -> {
            return new ArrayList();
        });
    }

    @Override // net.labymod.api.util.collection.map.Multimap
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Collection<V> value : this.map.values()) {
            values.addAll(value);
        }
        return values;
    }

    @Override // net.labymod.api.util.collection.map.Multimap
    public Map<K, Collection<V>> asMap() {
        return Map.copyOf(this.map);
    }

    @Override // net.labymod.api.util.collection.map.Multimap
    public void forEach(Consumer<Map.Entry<K, V>> entryConsumer) {
        for (Map.Entry<K, Collection<V>> entry : this.map.entrySet()) {
            K key = entry.getKey();
            for (V value : entry.getValue()) {
                entryConsumer.accept(new SimpleEntry(key, value));
            }
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HashMultimap<?, ?> that = (HashMultimap) o;
        return this.map.equals(that.map);
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    public String toString() {
        return this.map.toString();
    }
}

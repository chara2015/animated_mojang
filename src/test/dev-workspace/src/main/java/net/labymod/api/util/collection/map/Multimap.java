package net.labymod.api.util.collection.map;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/collection/map/Multimap.class */
public interface Multimap<K, V> {
    int size();

    boolean isEmpty();

    void put(K k, V v);

    boolean remove(K k);

    boolean remove(K k, V v);

    void clear();

    Collection<V> get(K k);

    Collection<V> values();

    Map<K, Collection<V>> asMap();

    void forEach(Consumer<Map.Entry<K, V>> consumer);
}

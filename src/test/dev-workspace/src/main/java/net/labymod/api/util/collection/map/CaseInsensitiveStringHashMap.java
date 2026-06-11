package net.labymod.api.util.collection.map;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/collection/map/CaseInsensitiveStringHashMap.class */
public class CaseInsensitiveStringHashMap<V> extends HashMap<String, V> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.HashMap, java.util.Map
    public /* bridge */ /* synthetic */ Object merge(Object obj, @NotNull Object obj2, @NotNull BiFunction biFunction) {
        return merge((String) obj, obj2, (BiFunction<? super Object, ? super Object, ? extends Object>) biFunction);
    }

    public CaseInsensitiveStringHashMap() {
    }

    public CaseInsensitiveStringHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public CaseInsensitiveStringHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    private String normalizeKey(Object key) {
        if (key instanceof String) {
            String string = (String) key;
            return string.toLowerCase(Locale.ROOT);
        }
        return null;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public V put(String str, V v) {
        return (V) super.put(normalizeKey(str), (Object) v);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        return (V) super.get(normalizeKey(obj));
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object key) {
        return super.containsKey(normalizeKey(key));
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        return (V) super.remove(normalizeKey(obj));
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends String, ? extends V> m) {
        if (m != null && !m.isEmpty()) {
            Map<String, V> normalizedMap = new HashMap<>(m.size());
            for (Map.Entry<? extends String, ? extends V> entry : m.entrySet()) {
                normalizedMap.put(normalizeKey(entry.getKey()), entry.getValue());
            }
            super.putAll(normalizedMap);
        }
    }

    @Override // java.util.HashMap, java.util.Map
    public V getOrDefault(Object obj, V v) {
        return (V) super.getOrDefault(normalizeKey(obj), v);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.HashMap, java.util.Map
    public V computeIfAbsent(String str, @NotNull Function<? super String, ? extends V> function) {
        return (V) super.computeIfAbsent(normalizeKey(str), function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.HashMap, java.util.Map
    public V computeIfPresent(String str, @NotNull BiFunction<? super String, ? super V, ? extends V> biFunction) {
        return (V) super.computeIfPresent(normalizeKey(str), biFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.HashMap, java.util.Map
    public V compute(String str, @NotNull BiFunction<? super String, ? super V, ? extends V> biFunction) {
        return (V) super.compute(normalizeKey(str), biFunction);
    }

    public V merge(String str, @NotNull V v, @NotNull BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return (V) super.merge(normalizeKey(str), (Object) v, (BiFunction) biFunction);
    }

    @Override // java.util.HashMap, java.util.Map
    public V replace(String str, V v) {
        return (V) super.replace(normalizeKey(str), (Object) v);
    }

    @Override // java.util.HashMap, java.util.Map
    public boolean remove(Object key, Object value) {
        return super.remove(normalizeKey(key), value);
    }

    @Override // java.util.HashMap, java.util.Map
    public boolean replace(String key, V oldValue, V newValue) {
        return super.replace(normalizeKey(key), (Object) oldValue, (Object) newValue);
    }

    @Override // java.util.HashMap, java.util.Map
    public V putIfAbsent(String str, V v) {
        return (V) super.putIfAbsent(normalizeKey(str), (Object) v);
    }
}

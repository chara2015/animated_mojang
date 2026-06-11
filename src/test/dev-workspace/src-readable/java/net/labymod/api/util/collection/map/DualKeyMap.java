package net.labymod.api.util.collection.map;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/collection/map/DualKeyMap.class */
public class DualKeyMap<V> {
    private final Map<UUID, Entry<V>> byUuid;
    private final Map<String, Entry<V>> byName;

    public DualKeyMap() {
        this(HashMap::new);
    }

    public DualKeyMap(Supplier<Map<?, ?>> mapFactory) {
        this.byUuid = createMap(mapFactory);
        this.byName = createMap(mapFactory);
    }

    private static <K, V> Map<K, V> createMap(Supplier<Map<?, ?>> factory) {
        return (Map) factory.get();
    }

    @Nullable
    public V get(UUID key) {
        Entry<V> entry = this.byUuid.get(key);
        if (entry != null) {
            return entry.value();
        }
        return null;
    }

    @Nullable
    public V get(String key) {
        Entry<V> entry = this.byName.get(standardizeKey(key));
        if (entry != null) {
            return entry.value();
        }
        return null;
    }

    public void put(UUID key, V value) {
        removeLinked(this.byUuid.get(key));
        this.byUuid.put(key, new Entry<>(key, null, value));
    }

    public void put(String key, V value) {
        String normalizedKey = standardizeKey(key);
        removeLinked(this.byName.get(normalizedKey));
        this.byName.put(normalizedKey, new Entry<>(null, normalizedKey, value));
    }

    public void put(UUID uuid, String name, V value) {
        String normalizedName = standardizeKey(name);
        removeLinked(this.byUuid.get(uuid));
        removeLinked(this.byName.get(normalizedName));
        Entry<V> entry = new Entry<>(uuid, normalizedName, value);
        this.byUuid.put(uuid, entry);
        this.byName.put(normalizedName, entry);
    }

    public boolean containsKey(UUID key) {
        return this.byUuid.containsKey(key);
    }

    public boolean containsKey(String key) {
        return this.byName.containsKey(standardizeKey(key));
    }

    @Nullable
    public V remove(UUID key) {
        Entry<V> entry = this.byUuid.remove(key);
        if (entry == null) {
            return null;
        }
        if (entry.name() != null) {
            this.byName.remove(entry.name());
        }
        return entry.value();
    }

    @Nullable
    public V remove(String key) {
        Entry<V> entry = this.byName.remove(standardizeKey(key));
        if (entry == null) {
            return null;
        }
        if (entry.uuid() != null) {
            this.byUuid.remove(entry.uuid());
        }
        return entry.value();
    }

    public Collection<V> values() {
        if (this.byUuid.isEmpty() && this.byName.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<V> result = new ArrayList<>();
        Iterator<Entry<V>> it = this.byUuid.values().iterator();
        while (it.hasNext()) {
            result.add(it.next().value());
        }
        for (Entry<V> entry : this.byName.values()) {
            if (entry.uuid() == null) {
                result.add(entry.value());
            }
        }
        return Collections.unmodifiableCollection(result);
    }

    public void clear() {
        this.byUuid.clear();
        this.byName.clear();
    }

    public int size() {
        if (this.byUuid.isEmpty()) {
            return this.byName.size();
        }
        if (this.byName.isEmpty()) {
            return this.byUuid.size();
        }
        int count = this.byUuid.size();
        for (Entry<V> entry : this.byName.values()) {
            if (entry.uuid() == null) {
                count++;
            }
        }
        return count;
    }

    public boolean isEmpty() {
        return this.byUuid.isEmpty() && this.byName.isEmpty();
    }

    private void removeLinked(Entry<V> entry) {
        if (entry == null) {
            return;
        }
        if (entry.uuid() != null) {
            this.byUuid.remove(entry.uuid());
        }
        if (entry.name() != null) {
            this.byName.remove(entry.name());
        }
    }

    private String standardizeKey(String key) {
        return key.toLowerCase(Locale.ROOT);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/collection/map/DualKeyMap$Entry.class */
    private static final class Entry<V> extends Record {

        @Nullable
        private final UUID uuid;

        @Nullable
        private final String name;
        private final V value;

        private Entry(@Nullable UUID uuid, @Nullable String name, V value) {
            this.uuid = uuid;
            this.name = name;
            this.value = value;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Entry.class), Entry.class, "uuid;name;value", "FIELD:Lnet/labymod/api/util/collection/map/DualKeyMap$Entry;->uuid:Ljava/util/UUID;", "FIELD:Lnet/labymod/api/util/collection/map/DualKeyMap$Entry;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/util/collection/map/DualKeyMap$Entry;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Entry.class), Entry.class, "uuid;name;value", "FIELD:Lnet/labymod/api/util/collection/map/DualKeyMap$Entry;->uuid:Ljava/util/UUID;", "FIELD:Lnet/labymod/api/util/collection/map/DualKeyMap$Entry;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/util/collection/map/DualKeyMap$Entry;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Entry.class, Object.class), Entry.class, "uuid;name;value", "FIELD:Lnet/labymod/api/util/collection/map/DualKeyMap$Entry;->uuid:Ljava/util/UUID;", "FIELD:Lnet/labymod/api/util/collection/map/DualKeyMap$Entry;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/util/collection/map/DualKeyMap$Entry;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        @Nullable
        public UUID uuid() {
            return this.uuid;
        }

        @Nullable
        public String name() {
            return this.name;
        }

        public V value() {
            return this.value;
        }
    }
}

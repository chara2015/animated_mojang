package net.labymod.api.metadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/metadata/DefaultMetadata.class */
public class DefaultMetadata implements Metadata {
    private Map<String, Object> attributes;

    @Override // net.labymod.api.metadata.Metadata
    public boolean has(String key) {
        return this.attributes != null && this.attributes.containsKey(key);
    }

    @Override // net.labymod.api.metadata.Metadata
    public <T> void set(String key, T value) {
        getOrCreate().put(key, value);
    }

    @Override // net.labymod.api.metadata.Metadata
    public <T> void set(String key, Supplier<T> value) {
        getOrCreate().put(key, value);
    }

    @Override // net.labymod.api.metadata.Metadata
    public <T> void set(Metadata metadata) {
        if (!(metadata instanceof DefaultMetadata)) {
            return;
        }
        DefaultMetadata defaultMetadata = (DefaultMetadata) metadata;
        getOrCreate().putAll(defaultMetadata.getOrCreate());
    }

    @Override // net.labymod.api.metadata.Metadata
    public <T> T get(String str) {
        if (this.attributes == null) {
            return null;
        }
        return (T) this.attributes.get(str);
    }

    @Override // net.labymod.api.metadata.Metadata
    public <T> T get(String str, T t) {
        return this.attributes == null ? t : (T) this.attributes.getOrDefault(str, t);
    }

    @Override // net.labymod.api.metadata.Metadata
    public <T> T getSupplied(String key, T defaultValue) {
        if (this.attributes == null) {
            return defaultValue;
        }
        Object o = this.attributes.get(key);
        if (!(o instanceof Supplier)) {
            return defaultValue;
        }
        Supplier<T> supplier = (Supplier) o;
        return supplier.get();
    }

    @Override // net.labymod.api.metadata.Metadata
    public <T> T computeIfAbsent(String str, Function<String, T> function) {
        return (T) getOrCreate().computeIfAbsent(str, function);
    }

    @Override // net.labymod.api.metadata.Metadata
    public <T> T remove(String str) {
        if (this.attributes == null) {
            return null;
        }
        return (T) this.attributes.remove(str);
    }

    @Override // net.labymod.api.metadata.Metadata
    public <T> T remove(String str, Runnable runnable) {
        T t = (T) remove(str);
        if (runnable == null) {
            return t;
        }
        runnable.run();
        return t;
    }

    @Override // net.labymod.api.metadata.Metadata
    public Map<String, Object> attributes() {
        return this.attributes;
    }

    Map<String, Object> getOrCreate() {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }
        return this.attributes;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/metadata/DefaultMetadata$ImmutableMetadata.class */
    static class ImmutableMetadata extends DefaultMetadata {
        private final Map<String, Object> attributes;

        public ImmutableMetadata(Metadata attributes) {
            this(attributes.attributes());
        }

        public ImmutableMetadata(Map<String, Object> attributes) {
            Map<String, Object> mapEmptyMap;
            if (attributes == null) {
                mapEmptyMap = Collections.emptyMap();
            } else {
                mapEmptyMap = attributes;
            }
            this.attributes = createImmutableMap(mapEmptyMap);
        }

        private Map<String, Object> createImmutableMap(Map<String, Object> mutable) {
            Map<String, Object> immutable = new HashMap<>();
            for (Map.Entry<String, Object> entry : mutable.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Supplier) {
                    Supplier<?> supplier = (Supplier) value;
                    value = supplier.get();
                }
                immutable.put(entry.getKey(), value);
            }
            return Collections.unmodifiableMap(immutable);
        }

        @Override // net.labymod.api.metadata.DefaultMetadata, net.labymod.api.metadata.Metadata
        public boolean has(String key) {
            return this.attributes.containsKey(key);
        }

        @Override // net.labymod.api.metadata.DefaultMetadata, net.labymod.api.metadata.Metadata
        public <T> void set(String key, T value) {
            throw new IllegalStateException("Cannot set value on immutable metadata");
        }

        @Override // net.labymod.api.metadata.DefaultMetadata, net.labymod.api.metadata.Metadata
        public <T> void set(String key, Supplier<T> value) {
            throw new IllegalStateException("Cannot set value on immutable metadata");
        }

        @Override // net.labymod.api.metadata.DefaultMetadata, net.labymod.api.metadata.Metadata
        public <T> void set(Metadata metadata) {
            throw new IllegalStateException("Cannot set metadata on immutable metadata");
        }

        @Override // net.labymod.api.metadata.DefaultMetadata, net.labymod.api.metadata.Metadata
        public <T> T get(String str) {
            return (T) this.attributes.get(str);
        }

        @Override // net.labymod.api.metadata.DefaultMetadata, net.labymod.api.metadata.Metadata
        public <T> T get(String str, T t) {
            return (T) this.attributes.getOrDefault(str, t);
        }

        @Override // net.labymod.api.metadata.DefaultMetadata, net.labymod.api.metadata.Metadata
        public <T> T getSupplied(String str, T t) {
            return (T) get(str, t);
        }

        @Override // net.labymod.api.metadata.DefaultMetadata, net.labymod.api.metadata.Metadata
        public <T> T computeIfAbsent(String key, Function<String, T> function) {
            throw new IllegalStateException("Cannot compute value on immutable metadata");
        }

        @Override // net.labymod.api.metadata.DefaultMetadata, net.labymod.api.metadata.Metadata
        public <T> T remove(String key) {
            throw new IllegalStateException("Cannot remove value on immutable metadata");
        }

        @Override // net.labymod.api.metadata.DefaultMetadata, net.labymod.api.metadata.Metadata
        public <T> T remove(String key, Runnable after) {
            throw new IllegalStateException("Cannot remove value on immutable metadata");
        }

        @Override // net.labymod.api.metadata.DefaultMetadata, net.labymod.api.metadata.Metadata
        public Map<String, Object> attributes() {
            return this.attributes;
        }
    }
}

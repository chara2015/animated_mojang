package net.labymod.core.main.user.shop.item.metadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/MetadataProperty.class */
public class MetadataProperty<T> {
    private final String key;
    private final T defaultValue;
    private final ItemMetadata metadata;
    private T value;
    private boolean initialized;

    public MetadataProperty(String key, ItemMetadata metadata) {
        this(key, metadata, null);
    }

    public MetadataProperty(String key, ItemMetadata metadata, T value) {
        this.key = key;
        this.metadata = metadata;
        this.value = value;
        this.defaultValue = value;
    }

    public T get() {
        if (!this.initialized) {
            this.value = (T) this.metadata.readValue(this.key);
            this.initialized = true;
        }
        return this.value;
    }

    public void reset(String key) {
        this.value = this.defaultValue;
        this.initialized = false;
    }
}

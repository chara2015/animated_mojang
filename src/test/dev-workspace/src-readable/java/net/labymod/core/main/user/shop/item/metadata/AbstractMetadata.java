package net.labymod.core.main.user.shop.item.metadata;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.core.main.debug.InvalidCosmeticDataDebugger;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/AbstractMetadata.class */
public abstract class AbstractMetadata<T> {
    private final String[] keys;
    protected Supplier<T> defaultValueSupplier = () -> {
        return null;
    };
    protected Consumer<String> metadataInvalidator = key -> {
    };
    protected T value;
    protected MetadataWatchable watchable;
    private String currentKey;

    public abstract void read(String str) throws MetadataException;

    public abstract Object write() throws MetadataException;

    public AbstractMetadata(String... keys) {
        this.keys = keys;
    }

    public AbstractMetadata<T> withWatchable(@Nullable MetadataWatchable watchable) {
        this.watchable = watchable;
        return this;
    }

    public AbstractMetadata<T> withMetadataInvalidator(@Nullable Consumer<String> metadataInvalidator) {
        this.metadataInvalidator = metadataInvalidator;
        return this;
    }

    public AbstractMetadata<T> defaultValue(Supplier<T> supplier) {
        if (supplier == null) {
            return this;
        }
        this.defaultValueSupplier = supplier;
        this.value = supplier.get();
        return this;
    }

    public final void read(CosmeticDetails details, String key, String value) {
        try {
            try {
                this.currentKey = key;
                if (this.metadataInvalidator != null) {
                    this.metadataInvalidator.accept(key);
                }
                read(value);
                if (this.watchable != null) {
                    this.watchable.onUpdate();
                }
                this.currentKey = null;
            } catch (MetadataException exception) {
                InvalidCosmeticDataDebugger.log("({}) Key: {}, Message: {}", details.getName() + "/" + details.getId(), key, exception.getMessage());
                this.currentKey = null;
            }
        } catch (Throwable th) {
            this.currentKey = null;
            throw th;
        }
    }

    public void invalidate() {
        this.value = this.defaultValueSupplier.get();
    }

    public T getValue() {
        return this.value;
    }

    public boolean hasKey(String name) {
        for (String dataName : this.keys) {
            if (dataName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean is(String key) {
        return Objects.equals(this.currentKey, key);
    }

    public String[] getKeys() {
        return this.keys;
    }
}

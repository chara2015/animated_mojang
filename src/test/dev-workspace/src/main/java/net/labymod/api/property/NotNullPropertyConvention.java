package net.labymod.api.property;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/property/NotNullPropertyConvention.class */
public class NotNullPropertyConvention<T> implements PropertyConvention<T> {
    private final T defaultValue;

    public NotNullPropertyConvention(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override // net.labymod.api.property.PropertyConvention
    public T convention(T value) {
        return value == null ? this.defaultValue : value;
    }
}

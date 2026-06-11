package net.labymod.util.property;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/util/property/SystemProperty.class */
public abstract class SystemProperty<T> {
    private final String key;

    public abstract T get();

    public SystemProperty(String key) {
        this.key = key;
    }

    public T getOrDefault(T defaultValue) {
        T value = get();
        return value != null ? value : defaultValue;
    }

    public String getKey() {
        return this.key;
    }
}

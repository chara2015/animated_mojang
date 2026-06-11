package net.labymod.api.util;

import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/KeyValue.class */
public class KeyValue<T> {
    private final String key;
    private final T value;

    public KeyValue(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public T getValue() {
        return this.value;
    }

    public String toString() {
        return this.key + ": " + this.value.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyValue<?> keyValue = (KeyValue) o;
        return Objects.equals(this.key, keyValue.key);
    }

    public int hashCode() {
        return this.key.hashCode();
    }
}

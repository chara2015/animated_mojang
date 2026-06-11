package net.labymod.api.client.entity.datawatcher;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/datawatcher/DataPoint.class */
public class DataPoint<T> {
    private T value;

    public DataPoint(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }

    public boolean isPresent() {
        return this.value != null;
    }
}

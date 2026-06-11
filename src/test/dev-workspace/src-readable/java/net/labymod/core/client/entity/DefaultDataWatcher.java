package net.labymod.core.client.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.labymod.api.client.entity.datawatcher.DataPoint;
import net.labymod.api.client.entity.datawatcher.DataWatcher;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/DefaultDataWatcher.class */
public class DefaultDataWatcher implements DataWatcher {
    private final Map<String, DataPoint> dataMap = new HashMap();

    @Override // net.labymod.api.client.entity.datawatcher.DataWatcher
    public <T> void set(String key, T value) {
        this.dataMap.put(key, new DataPoint(value));
    }

    @Override // net.labymod.api.client.entity.datawatcher.DataWatcher
    public void remove(String key) {
        this.dataMap.remove(key);
    }

    @Override // net.labymod.api.client.entity.datawatcher.DataWatcher
    public <T> DataPoint<T> get(String key) {
        return get(key, null);
    }

    @Override // net.labymod.api.client.entity.datawatcher.DataWatcher
    public <T> DataPoint<T> get(String key, T defaultValue) {
        return this.dataMap.getOrDefault(key, new DataPoint(defaultValue));
    }

    @Override // net.labymod.api.client.entity.datawatcher.DataWatcher
    public <T> DataPoint<T> computeIfAbsent(String key, Function<String, ? extends T> compute) {
        return this.dataMap.computeIfAbsent(key, absent -> {
            return new DataPoint(compute.apply(key));
        });
    }

    @Override // net.labymod.api.client.entity.datawatcher.DataWatcher
    public boolean has(String key) {
        DataPoint dataPoint = this.dataMap.get(key);
        return dataPoint != null && dataPoint.isPresent();
    }
}

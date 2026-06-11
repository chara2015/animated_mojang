package net.labymod.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.labymod.api.util.KeyValue;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/service/DefaultRegistry.class */
public class DefaultRegistry<T> implements Registry<T> {
    private final transient List<KeyValue<T>> elements = new ArrayList();

    @Override // net.labymod.api.service.Registry
    public List<KeyValue<T>> getElements() {
        return this.elements;
    }

    @Override // net.labymod.api.service.Registry
    public void register(String id, T element) {
        KeyValue<T> value = new KeyValue<>(id, element);
        getElements().add(value);
        onRegister(value);
    }

    @Override // net.labymod.api.service.Registry
    public void registerBefore(String beforeId, String id, T element) {
        KeyValue<T> value = new KeyValue<>(id, element);
        getElements().add(indexOf(beforeId), value);
        onRegister(value);
    }

    @Override // net.labymod.api.service.Registry
    public void registerAfter(String afterId, String id, T element) {
        KeyValue<T> value = new KeyValue<>(id, element);
        getElements().add(indexOf(afterId) + 1, value);
        onRegister(value);
    }

    @Override // net.labymod.api.service.Registry
    public void unregister(String id) {
        KeyValue<T> element = getElementById(id);
        super.unregister(id);
        if (element != null) {
            onUnregister(element);
        }
    }

    @Override // net.labymod.api.service.Registry
    public void unregister(Predicate<KeyValue<T>> predicate) {
        getElements().removeIf(value -> {
            if (predicate.test(value)) {
                onUnregister(value);
                return true;
            }
            return false;
        });
    }

    @Override // net.labymod.api.service.Registry
    public void unregisterByClassLoader(ClassLoader classLoader) {
        getElements().removeIf(entry -> {
            if (entry.getValue().getClass().getClassLoader().equals(classLoader)) {
                onUnregister(entry);
                return true;
            }
            return false;
        });
    }

    protected void onRegister(KeyValue<T> value) {
    }

    protected void onUnregister(KeyValue<T> value) {
    }
}

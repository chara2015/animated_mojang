package net.labymod.api.util;

import java.util.Map;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/PropertyContainer.class */
public interface PropertyContainer {
    Map<String, Object> getProperties();

    default <T> T getProperty(String str) {
        return (T) getProperties().get(str);
    }

    default boolean hasProperty(String key) {
        return getProperties().containsKey(key);
    }

    default <T> void setProperty(String key, T value) {
        getProperties().put(key, value);
    }
}

package net.labymod.api.metadata;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.metadata.DefaultMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/metadata/Metadata.class */
public interface Metadata {
    boolean has(String str);

    <T> void set(String str, T t);

    <T> void set(String str, Supplier<T> supplier);

    <T> void set(Metadata metadata);

    <T> T get(String str);

    <T> T get(String str, T t);

    <T> T getSupplied(String str, T t);

    <T> T computeIfAbsent(String str, Function<String, T> function);

    <T> T remove(String str);

    <T> T remove(String str, Runnable runnable);

    Map<String, Object> attributes();

    default <T> Metadata with(String key, T value) {
        set(key, value);
        return this;
    }

    default <T> Metadata with(String key, Supplier<T> value) {
        set(key, (Supplier) value);
        return this;
    }

    default boolean getBoolean(String key) {
        Object value = get(key);
        return (value instanceof Boolean) && ((Boolean) value).booleanValue();
    }

    default boolean getBoolean(String key, boolean defaultValue) {
        return ((Boolean) get(key, Boolean.valueOf(defaultValue))).booleanValue();
    }

    static Metadata create() {
        return new DefaultMetadata();
    }

    static Metadata immutable(Metadata metadata) {
        return new DefaultMetadata.ImmutableMetadata(metadata);
    }
}

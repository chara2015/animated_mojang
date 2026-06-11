package net.labymod.v1_21_11.mojang;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.authlib.properties.PropertyMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.mojang.Property;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mojang/WrappedPropertyMap.class */
public class WrappedPropertyMap extends PropertyMap {
    private final Map<String, Collection<Property>> properties;

    public /* bridge */ /* synthetic */ boolean putAll(@NotNull Object obj, @NotNull Iterable iterable) {
        return putAll((String) obj, (Iterable<? extends com.mojang.authlib.properties.Property>) iterable);
    }

    public WrappedPropertyMap() {
        this(new HashMap());
    }

    public WrappedPropertyMap(Map<String, Collection<Property>> properties) {
        super(HashMultimap.create());
        this.properties = properties;
    }

    public WrappedPropertyMap(PropertyMap properties) {
        super(properties);
        this.properties = new HashMap();
        properties.forEach((key, value) -> {
            this.properties.computeIfAbsent(key, k -> {
                return new ArrayList();
            }).add(createProperty(value));
        });
    }

    public boolean put(@NotNull String key, @NotNull com.mojang.authlib.properties.Property value) {
        boolean putSuccess = super.put(key, value);
        Collection<Property> properties = getProperties(key);
        properties.add(createProperty(value));
        return putSuccess;
    }

    public boolean putAll(@NotNull String key, @NotNull Iterable<? extends com.mojang.authlib.properties.Property> values) {
        boolean putAllSuccess = super.putAll(key, values);
        Collection<Property> properties = getProperties(key);
        for (com.mojang.authlib.properties.Property property : values) {
            properties.add(createProperty(property));
        }
        return putAllSuccess;
    }

    public boolean putAll(@NotNull Multimap<? extends String, ? extends com.mojang.authlib.properties.Property> multimap) {
        boolean putAllSuccess = super.putAll(multimap);
        multimap.forEach((key, property) -> {
            Collection<Property> properties = getProperties(key);
            properties.add(createProperty(property));
        });
        return putAllSuccess;
    }

    public void clear() {
        super.clear();
        this.properties.clear();
    }

    public Map<String, Collection<Property>> getProperties() {
        return this.properties;
    }

    private Collection<Property> getProperties(String key) {
        return this.properties.computeIfAbsent(key, k -> {
            return new ArrayList();
        });
    }

    private Property createProperty(com.mojang.authlib.properties.Property property) {
        return new Property(property.name(), property.value(), property.signature());
    }
}

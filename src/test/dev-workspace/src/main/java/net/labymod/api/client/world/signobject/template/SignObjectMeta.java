package net.labymod.api.client.world.signobject.template;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.world.signobject.template.SignObjectTemplate;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/template/SignObjectMeta.class */
public class SignObjectMeta<T extends SignObjectTemplate> {
    private final T template;
    private final String[] meta;
    private transient Map<String, String> parsedMeta;

    public SignObjectMeta(T template, String[] meta) {
        this.template = template;
        this.meta = meta;
    }

    public T template() {
        return this.template;
    }

    public String[] meta() {
        return this.meta;
    }

    public String meta(String key) {
        return parseMeta().get(key);
    }

    public String meta(String key, String defaultValue) {
        return parseMeta().getOrDefault(key, defaultValue);
    }

    public <E extends Enum<?>> E meta(String key, Class<E> enumClass) {
        String value = meta(key);
        if (value == null) {
            return null;
        }
        for (E constant : enumClass.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(value)) {
                return constant;
            }
        }
        return null;
    }

    public <E extends Enum<?>> E meta(String str, Class<E> cls, E e) {
        E e2 = (E) meta(str, cls);
        return e2 != null ? e2 : e;
    }

    public FloatVector3 vector(String key) {
        String value = meta(key);
        if (value == null) {
            return null;
        }
        String[] components = value.split(",");
        if (components.length != 3) {
            return null;
        }
        try {
            return new FloatVector3(Float.parseFloat(components[0]), Float.parseFloat(components[1]), Float.parseFloat(components[2]));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public FloatVector3 vector(String key, FloatVector3 defaultValue) {
        FloatVector3 value = vector(key);
        return value != null ? value : defaultValue;
    }

    public Map<String, String> parseMeta() {
        if (this.parsedMeta != null) {
            return this.parsedMeta;
        }
        if (this.meta.length == 0) {
            return Map.of();
        }
        Map<String, String> meta = new HashMap<>();
        for (String line : this.meta) {
            String[] components = line.split(":", 2);
            if (components.length == 2) {
                meta.put(components[0], components[1]);
            }
        }
        Map<String, String> mapUnmodifiableMap = Collections.unmodifiableMap(meta);
        this.parsedMeta = mapUnmodifiableMap;
        return mapUnmodifiableMap;
    }
}

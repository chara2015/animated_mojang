package net.labymod.api.util;

import java.util.Collection;
import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/CastUtil.class */
public final class CastUtil {
    private CastUtil() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T cast(Object obj) {
        return obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends Collection<?>> T cast(Collection<?> collection) {
        return collection;
    }

    public static <T> T requireInstanceOf(Object obj, Class<T> cls) {
        Objects.requireNonNull(obj, "obj cannot be null");
        if (!cls.isInstance(obj)) {
            throw new IllegalArgumentException(obj.getClass().getName() + " is not an instance of " + cls.getName());
        }
        return cls.cast(obj);
    }
}

package net.minecraft.util;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ClassTreeIdRegistry.class */
public class ClassTreeIdRegistry {
    public static final int NO_ID_VALUE = -1;
    private final Object2IntMap<Class<?>> classToLastIdCache = (Object2IntMap) Util.make(new Object2IntOpenHashMap(), $$0 -> {
        $$0.defaultReturnValue(-1);
    });

    public int getLastIdFor(Class<?> $$0) {
        int $$3;
        int $$1 = this.classToLastIdCache.getInt($$0);
        if ($$1 != -1) {
            return $$1;
        }
        Class<?> $$2 = $$0;
        do {
            Class<? super Object> superclass = $$2.getSuperclass();
            $$2 = superclass;
            if (superclass != Object.class) {
                $$3 = this.classToLastIdCache.getInt($$2);
            } else {
                return -1;
            }
        } while ($$3 == -1);
        return $$3;
    }

    public int getCount(Class<?> $$0) {
        return getLastIdFor($$0) + 1;
    }

    public int define(Class<?> $$0) {
        int $$1 = getLastIdFor($$0);
        int $$2 = $$1 == -1 ? 0 : $$1 + 1;
        this.classToLastIdCache.put($$0, $$2);
        return $$2;
    }
}

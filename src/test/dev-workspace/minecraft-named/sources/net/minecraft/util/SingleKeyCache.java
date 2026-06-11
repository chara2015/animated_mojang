package net.minecraft.util;

import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/SingleKeyCache.class */
public class SingleKeyCache<K, V> {
    private final Function<K, V> computeValue;
    private K cacheKey = null;
    private V cachedValue;

    public SingleKeyCache(Function<K, V> $$0) {
        this.computeValue = $$0;
    }

    public V getValue(K $$0) {
        if (this.cachedValue == null || !Objects.equals(this.cacheKey, $$0)) {
            this.cachedValue = this.computeValue.apply($$0);
            this.cacheKey = $$0;
        }
        return this.cachedValue;
    }
}

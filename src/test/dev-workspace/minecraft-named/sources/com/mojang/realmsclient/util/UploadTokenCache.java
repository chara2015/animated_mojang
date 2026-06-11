package com.mojang.realmsclient.util;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/util/UploadTokenCache.class */
public class UploadTokenCache {
    private static final Long2ObjectMap<String> TOKEN_CACHE = new Long2ObjectOpenHashMap();

    public static String get(long $$0) {
        return (String) TOKEN_CACHE.get($$0);
    }

    public static void invalidate(long $$0) {
        TOKEN_CACHE.remove($$0);
    }

    public static void put(long $$0, String $$1) {
        TOKEN_CACHE.put($$0, $$1);
    }
}

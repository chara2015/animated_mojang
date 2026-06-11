package net.labymod.api.util;

import java.util.function.Function;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/ArrayUtil.class */
public final class ArrayUtil {
    public static <T> T getOrDefault(T[] tArr, int i, T t) {
        return (T) getOrDefault(tArr, i, t, t2 -> {
            return t2;
        });
    }

    public static <A, T> T getOrDefault(A[] array, int index, T defaultValue, Function<A, T> mapperFunction) {
        if (index >= 0 && index < array.length) {
            return mapperFunction.apply(array[index]);
        }
        return defaultValue;
    }
}

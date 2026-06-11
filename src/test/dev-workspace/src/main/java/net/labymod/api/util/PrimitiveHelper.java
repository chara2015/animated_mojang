package net.labymod.api.util;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import net.labymod.api.util.function.Functional;
import net.labymod.api.util.reflection.Reflection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/PrimitiveHelper.class */
public final class PrimitiveHelper {
    public static final Class<?>[] BOOLEAN = {Boolean.class, Boolean.TYPE};
    public static final Class<?>[] CHARACTER = {Character.class, Character.TYPE};
    public static final Class<?>[] BYTE = {Byte.class, Byte.TYPE};
    public static final Class<?>[] DOUBLE = {Double.class, Double.TYPE};
    public static final Class<?>[] FLOAT = {Float.class, Float.TYPE};
    public static final Class<?>[] INTEGER = {Integer.class, Integer.TYPE};
    public static final Class<?>[] LONG = {Long.class, Long.TYPE};
    public static final Class<?>[] SHORT = {Short.class, Long.TYPE};
    public static final Class<?>[] NUMBER_PRIMITIVES = (Class[]) Functional.toArray(new HashSet(), Class.class, classes -> {
        classes.addAll(Arrays.asList(BYTE));
        classes.addAll(Arrays.asList(DOUBLE));
        classes.addAll(Arrays.asList(FLOAT));
        classes.addAll(Arrays.asList(INTEGER));
        classes.addAll(Arrays.asList(LONG));
        classes.addAll(Arrays.asList(SHORT));
    });
    public static final Class<?>[] PRIMITIVES = (Class[]) Functional.toArray(new HashSet(), Class.class, classes -> {
        classes.addAll(Arrays.asList(BOOLEAN));
        classes.addAll(Arrays.asList(BYTE));
        classes.addAll(Arrays.asList(CHARACTER));
        classes.addAll(Arrays.asList(DOUBLE));
        classes.addAll(Arrays.asList(FLOAT));
        classes.addAll(Arrays.asList(INTEGER));
        classes.addAll(Arrays.asList(LONG));
        classes.addAll(Arrays.asList(SHORT));
    });

    private PrimitiveHelper() {
    }

    public static Number convertToTarget(Number number, Type targetType) {
        if (Reflection.isType(targetType, BYTE)) {
            return Byte.valueOf(number.byteValue());
        }
        if (Reflection.isType(targetType, DOUBLE)) {
            return Double.valueOf(number.doubleValue());
        }
        if (Reflection.isType(targetType, FLOAT)) {
            return Float.valueOf(number.floatValue());
        }
        if (Reflection.isType(targetType, INTEGER)) {
            return Integer.valueOf(number.intValue());
        }
        if (Reflection.isType(targetType, LONG)) {
            return Long.valueOf(number.longValue());
        }
        if (Reflection.isType(targetType, SHORT)) {
            return Short.valueOf(number.shortValue());
        }
        return null;
    }

    public static Class<?> wrap(Class<?> cls) {
        if (Reflection.isType(cls, BOOLEAN)) {
            return Boolean.class;
        }
        if (Reflection.isType(cls, BYTE)) {
            return Byte.class;
        }
        if (Reflection.isType(cls, CHARACTER)) {
            return Character.class;
        }
        if (Reflection.isType(cls, DOUBLE)) {
            return Double.class;
        }
        if (Reflection.isType(cls, FLOAT)) {
            return Float.class;
        }
        if (Reflection.isType(cls, INTEGER)) {
            return Integer.class;
        }
        if (Reflection.isType(cls, LONG)) {
            return Long.class;
        }
        if (Reflection.isType(cls, SHORT)) {
            return Short.class;
        }
        return cls;
    }

    public static boolean isNumber(Class<?> cls) {
        for (Class<?> c : NUMBER_PRIMITIVES) {
            if (c == cls) {
                return true;
            }
        }
        return false;
    }
}

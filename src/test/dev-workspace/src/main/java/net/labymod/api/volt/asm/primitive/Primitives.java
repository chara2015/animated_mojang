package net.labymod.api.volt.asm.primitive;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/asm/primitive/Primitives.class */
public final class Primitives {
    private static final Map<String, Primitive> PRIMITIVES = new HashMap();
    public static final Primitive INTEGER = register("I", Type.getType(Integer.class), "intValue", "()I", 172, 21, 46, 54, 79);
    public static final Primitive DOUBLE = register("D", Type.getType(Double.class), "doubleValue", "()D", 175, 24, 49, 57, 82);
    public static final Primitive LONG = register("J", Type.getType(Long.class), "longValue", "()J", 173, 22, 47, 55, 80);
    public static final Primitive FLOAT = register("F", Type.getType(Float.class), "floatValue", "()F", 174, 23, 48, 56, 81);
    public static final Primitive BYTE = register("B", Type.getType(Byte.class), "byteValue", "()B", 172, 21, 46, 54, 79);
    public static final Primitive SHORT = register("S", Type.getType(Short.class), "shortValue", "()S", 172, 21, 53, 54, 86);
    public static final Primitive BOOLEAN = register("Z", Type.getType(Boolean.class), "booleanValue", "()Z", 172, 21, 46, 54, 79);
    public static final Primitive VOID = register("V", Type.VOID_TYPE, null, null, 177, 0, 0, 0, 0);

    @NotNull
    private static Primitive register(String bytecodeName, Type type, String methodName, String methodDescriptor, int returnOpcode, int loadOpcode, int arrayLoadOpcode, int storeOpcode, int arrayStoreOpcode) {
        Primitive primitive = new Primitive(bytecodeName, type, methodName, methodDescriptor, returnOpcode, loadOpcode, arrayLoadOpcode, storeOpcode, arrayStoreOpcode);
        PRIMITIVES.put(primitive.getBytecodeName(), primitive);
        return primitive;
    }

    public static boolean isPrimitive(String bytecodeName) {
        return getPrimitive(bytecodeName) != null;
    }

    public static boolean isPrimitive(Type type) {
        for (Primitive primitive : PRIMITIVES.values()) {
            if (primitive.isPrimitive(type.getInternalName())) {
                return true;
            }
        }
        return false;
    }

    public static Primitive getPrimitive(Type type) {
        for (Primitive primitive : PRIMITIVES.values()) {
            if (primitive.isPrimitive(type.getInternalName())) {
                return primitive;
            }
        }
        return null;
    }

    public static Primitive getPrimitive(String bytecodeName) {
        return PRIMITIVES.get(bytecodeName);
    }
}

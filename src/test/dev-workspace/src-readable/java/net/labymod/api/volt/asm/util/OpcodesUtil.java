package net.labymod.api.volt.asm.util;

import net.labymod.api.volt.asm.primitive.Primitive;
import net.labymod.api.volt.asm.primitive.Primitives;
import org.objectweb.asm.Type;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/asm/util/OpcodesUtil.class */
public final class OpcodesUtil {
    public static int getField(boolean isStatic) {
        return isStatic ? 178 : 180;
    }

    public static int putField(boolean isStatic) {
        return isStatic ? 179 : 181;
    }

    public static int invokeMethod(boolean isStatic) {
        return isStatic ? 184 : 182;
    }

    public static int publicAccess(boolean isStatic) {
        return isStatic ? 9 : 1;
    }

    public static int protectedAccess(boolean isStatic) {
        return isStatic ? 12 : 4;
    }

    public static int privateAccess(boolean isStatic) {
        return isStatic ? 10 : 2;
    }

    public static boolean isReturnOpcode(int opcode) {
        return opcode == 177 || opcode == 176 || opcode == 172 || opcode == 174 || opcode == 175 || opcode == 173;
    }

    public static int getOpcodeLoad(Type type, boolean array) {
        Primitive primitive = Primitives.getPrimitive(type);
        if (primitive == null) {
            return array ? 50 : 25;
        }
        return primitive.getLoadOpcode(array);
    }

    public static int getOpcodeStore(Type type, boolean array) {
        Primitive primitive = Primitives.getPrimitive(type);
        if (primitive == null) {
            return array ? 83 : 58;
        }
        return primitive.getStoreOpcode(array);
    }
}

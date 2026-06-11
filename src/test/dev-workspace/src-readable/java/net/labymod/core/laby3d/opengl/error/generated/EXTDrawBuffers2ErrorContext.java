package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTDrawBuffers2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTDrawBuffers2ErrorContext.class */
public final class EXTDrawBuffers2ErrorContext {
    public static void glColorMaskIndexedEXT(int p0, boolean p1, boolean p2, boolean p3, boolean p4) {
        EXTDrawBuffers2.glColorMaskIndexedEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glColorMaskIndexedEXT", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Boolean.valueOf(p4));
    }

    public static void nglGetBooleanIndexedvEXT(int p0, int p1, long p2) {
        EXTDrawBuffers2.nglGetBooleanIndexedvEXT(p0, p1, p2);
        LabyDebugContext.glError("nglGetBooleanIndexedvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetBooleanIndexedvEXT(int p0, int p1, ByteBuffer p2) {
        EXTDrawBuffers2.glGetBooleanIndexedvEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetBooleanIndexedvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static boolean glGetBooleanIndexedEXT(int p0, int p1) {
        boolean returnType = EXTDrawBuffers2.glGetBooleanIndexedEXT(p0, p1);
        LabyDebugContext.glError("glGetBooleanIndexedEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetIntegerIndexedvEXT(int p0, int p1, long p2) {
        EXTDrawBuffers2.nglGetIntegerIndexedvEXT(p0, p1, p2);
        LabyDebugContext.glError("nglGetIntegerIndexedvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetIntegerIndexedvEXT(int p0, int p1, IntBuffer p2) {
        EXTDrawBuffers2.glGetIntegerIndexedvEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetIntegerIndexedvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetIntegerIndexedEXT(int p0, int p1) {
        int returnType = EXTDrawBuffers2.glGetIntegerIndexedEXT(p0, p1);
        LabyDebugContext.glError("glGetIntegerIndexedEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glEnableIndexedEXT(int p0, int p1) {
        EXTDrawBuffers2.glEnableIndexedEXT(p0, p1);
        LabyDebugContext.glError("glEnableIndexedEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glDisableIndexedEXT(int p0, int p1) {
        EXTDrawBuffers2.glDisableIndexedEXT(p0, p1);
        LabyDebugContext.glError("glDisableIndexedEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static boolean glIsEnabledIndexedEXT(int p0, int p1) {
        boolean returnType = EXTDrawBuffers2.glIsEnabledIndexedEXT(p0, p1);
        LabyDebugContext.glError("glIsEnabledIndexedEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glGetIntegerIndexedvEXT(int p0, int p1, int[] p2) {
        EXTDrawBuffers2.glGetIntegerIndexedvEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetIntegerIndexedvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

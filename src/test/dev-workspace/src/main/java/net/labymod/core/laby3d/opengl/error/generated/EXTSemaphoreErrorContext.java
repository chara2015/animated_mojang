package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTSemaphore;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTSemaphoreErrorContext.class */
public final class EXTSemaphoreErrorContext {
    public static void nglGetUnsignedBytevEXT(int p0, long p1) {
        EXTSemaphore.nglGetUnsignedBytevEXT(p0, p1);
        LabyDebugContext.glError("nglGetUnsignedBytevEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetUnsignedBytevEXT(int p0, ByteBuffer p1) {
        EXTSemaphore.glGetUnsignedBytevEXT(p0, p1);
        LabyDebugContext.glError("glGetUnsignedBytevEXT", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglGetUnsignedBytei_vEXT(int p0, int p1, long p2) {
        EXTSemaphore.nglGetUnsignedBytei_vEXT(p0, p1, p2);
        LabyDebugContext.glError("nglGetUnsignedBytei_vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetUnsignedBytei_vEXT(int p0, int p1, ByteBuffer p2) {
        EXTSemaphore.glGetUnsignedBytei_vEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetUnsignedBytei_vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGenSemaphoresEXT(int p0, long p1) {
        EXTSemaphore.nglGenSemaphoresEXT(p0, p1);
        LabyDebugContext.glError("nglGenSemaphoresEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenSemaphoresEXT(IntBuffer p0) {
        EXTSemaphore.glGenSemaphoresEXT(p0);
        LabyDebugContext.glError("glGenSemaphoresEXT", "p0", p0);
    }

    public static int glGenSemaphoresEXT() {
        int returnType = EXTSemaphore.glGenSemaphoresEXT();
        LabyDebugContext.glError("glGenSemaphoresEXT", new Object[0]);
        return returnType;
    }

    public static void nglDeleteSemaphoresEXT(int p0, long p1) {
        EXTSemaphore.nglDeleteSemaphoresEXT(p0, p1);
        LabyDebugContext.glError("nglDeleteSemaphoresEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteSemaphoresEXT(IntBuffer p0) {
        EXTSemaphore.glDeleteSemaphoresEXT(p0);
        LabyDebugContext.glError("glDeleteSemaphoresEXT", "p0", p0);
    }

    public static void glDeleteSemaphoresEXT(int p0) {
        EXTSemaphore.glDeleteSemaphoresEXT(p0);
        LabyDebugContext.glError("glDeleteSemaphoresEXT", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsSemaphoreEXT(int p0) {
        boolean returnType = EXTSemaphore.glIsSemaphoreEXT(p0);
        LabyDebugContext.glError("glIsSemaphoreEXT", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglSemaphoreParameterui64vEXT(int p0, int p1, long p2) {
        EXTSemaphore.nglSemaphoreParameterui64vEXT(p0, p1, p2);
        LabyDebugContext.glError("nglSemaphoreParameterui64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSemaphoreParameterui64vEXT(int p0, int p1, LongBuffer p2) {
        EXTSemaphore.glSemaphoreParameterui64vEXT(p0, p1, p2);
        LabyDebugContext.glError("glSemaphoreParameterui64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSemaphoreParameterui64EXT(int p0, int p1, long p2) {
        EXTSemaphore.glSemaphoreParameterui64EXT(p0, p1, p2);
        LabyDebugContext.glError("glSemaphoreParameterui64EXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void nglGetSemaphoreParameterui64vEXT(int p0, int p1, long p2) {
        EXTSemaphore.nglGetSemaphoreParameterui64vEXT(p0, p1, p2);
        LabyDebugContext.glError("nglGetSemaphoreParameterui64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSemaphoreParameterui64vEXT(int p0, int p1, LongBuffer p2) {
        EXTSemaphore.glGetSemaphoreParameterui64vEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetSemaphoreParameterui64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetSemaphoreParameterui64EXT(int p0, int p1) {
        long returnType = EXTSemaphore.glGetSemaphoreParameterui64EXT(p0, p1);
        LabyDebugContext.glError("glGetSemaphoreParameterui64EXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglWaitSemaphoreEXT(int p0, int p1, long p2, int p3, long p4, long p5) {
        EXTSemaphore.nglWaitSemaphoreEXT(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglWaitSemaphoreEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glWaitSemaphoreEXT(int p0, IntBuffer p1, IntBuffer p2, IntBuffer p3) {
        EXTSemaphore.glWaitSemaphoreEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glWaitSemaphoreEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void nglSignalSemaphoreEXT(int p0, int p1, long p2, int p3, long p4, long p5) {
        EXTSemaphore.nglSignalSemaphoreEXT(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglSignalSemaphoreEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glSignalSemaphoreEXT(int p0, IntBuffer p1, IntBuffer p2, IntBuffer p3) {
        EXTSemaphore.glSignalSemaphoreEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glSignalSemaphoreEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void glGenSemaphoresEXT(int[] p0) {
        EXTSemaphore.glGenSemaphoresEXT(p0);
        LabyDebugContext.glError("glGenSemaphoresEXT", "p0", p0);
    }

    public static void glDeleteSemaphoresEXT(int[] p0) {
        EXTSemaphore.glDeleteSemaphoresEXT(p0);
        LabyDebugContext.glError("glDeleteSemaphoresEXT", "p0", p0);
    }

    public static void glSemaphoreParameterui64vEXT(int p0, int p1, long[] p2) {
        EXTSemaphore.glSemaphoreParameterui64vEXT(p0, p1, p2);
        LabyDebugContext.glError("glSemaphoreParameterui64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSemaphoreParameterui64vEXT(int p0, int p1, long[] p2) {
        EXTSemaphore.glGetSemaphoreParameterui64vEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetSemaphoreParameterui64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glWaitSemaphoreEXT(int p0, int[] p1, int[] p2, int[] p3) {
        EXTSemaphore.glWaitSemaphoreEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glWaitSemaphoreEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void glSignalSemaphoreEXT(int p0, int[] p1, int[] p2, int[] p3) {
        EXTSemaphore.glSignalSemaphoreEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glSignalSemaphoreEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }
}

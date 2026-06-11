package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVTimelineSemaphore;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVTimelineSemaphoreErrorContext.class */
public final class NVTimelineSemaphoreErrorContext {
    public static void nglCreateSemaphoresNV(int p0, long p1) {
        NVTimelineSemaphore.nglCreateSemaphoresNV(p0, p1);
        LabyDebugContext.glError("nglCreateSemaphoresNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateSemaphoresNV(IntBuffer p0) {
        NVTimelineSemaphore.glCreateSemaphoresNV(p0);
        LabyDebugContext.glError("glCreateSemaphoresNV", "p0", p0);
    }

    public static int glCreateSemaphoresNV() {
        int returnType = NVTimelineSemaphore.glCreateSemaphoresNV();
        LabyDebugContext.glError("glCreateSemaphoresNV", new Object[0]);
        return returnType;
    }

    public static void nglSemaphoreParameterivNV(int p0, int p1, long p2) {
        NVTimelineSemaphore.nglSemaphoreParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("nglSemaphoreParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSemaphoreParameterivNV(int p0, int p1, IntBuffer p2) {
        NVTimelineSemaphore.glSemaphoreParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("glSemaphoreParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetSemaphoreParameterivNV(int p0, int p1, long p2) {
        NVTimelineSemaphore.nglGetSemaphoreParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetSemaphoreParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSemaphoreParameterivNV(int p0, int p1, IntBuffer p2) {
        NVTimelineSemaphore.glGetSemaphoreParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("glGetSemaphoreParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glCreateSemaphoresNV(int[] p0) {
        NVTimelineSemaphore.glCreateSemaphoresNV(p0);
        LabyDebugContext.glError("glCreateSemaphoresNV", "p0", p0);
    }

    public static void glSemaphoreParameterivNV(int p0, int p1, int[] p2) {
        NVTimelineSemaphore.glSemaphoreParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("glSemaphoreParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSemaphoreParameterivNV(int p0, int p1, int[] p2) {
        NVTimelineSemaphore.glGetSemaphoreParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("glGetSemaphoreParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

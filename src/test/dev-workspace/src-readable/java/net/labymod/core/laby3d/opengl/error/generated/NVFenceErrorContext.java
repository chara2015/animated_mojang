package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVFence;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVFenceErrorContext.class */
public final class NVFenceErrorContext {
    public static void nglDeleteFencesNV(int p0, long p1) {
        NVFence.nglDeleteFencesNV(p0, p1);
        LabyDebugContext.glError("nglDeleteFencesNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteFencesNV(IntBuffer p0) {
        NVFence.glDeleteFencesNV(p0);
        LabyDebugContext.glError("glDeleteFencesNV", "p0", p0);
    }

    public static void glDeleteFencesNV(int p0) {
        NVFence.glDeleteFencesNV(p0);
        LabyDebugContext.glError("glDeleteFencesNV", "p0", Integer.valueOf(p0));
    }

    public static void nglGenFencesNV(int p0, long p1) {
        NVFence.nglGenFencesNV(p0, p1);
        LabyDebugContext.glError("nglGenFencesNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenFencesNV(IntBuffer p0) {
        NVFence.glGenFencesNV(p0);
        LabyDebugContext.glError("glGenFencesNV", "p0", p0);
    }

    public static int glGenFencesNV() {
        int returnType = NVFence.glGenFencesNV();
        LabyDebugContext.glError("glGenFencesNV", new Object[0]);
        return returnType;
    }

    public static boolean glIsFenceNV(int p0) {
        boolean returnType = NVFence.glIsFenceNV(p0);
        LabyDebugContext.glError("glIsFenceNV", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static boolean glTestFenceNV(int p0) {
        boolean returnType = NVFence.glTestFenceNV(p0);
        LabyDebugContext.glError("glTestFenceNV", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetFenceivNV(int p0, int p1, long p2) {
        NVFence.nglGetFenceivNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetFenceivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetFenceivNV(int p0, int p1, IntBuffer p2) {
        NVFence.glGetFenceivNV(p0, p1, p2);
        LabyDebugContext.glError("glGetFenceivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetFenceiNV(int p0, int p1) {
        int returnType = NVFence.glGetFenceiNV(p0, p1);
        LabyDebugContext.glError("glGetFenceiNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glFinishFenceNV(int p0) {
        NVFence.glFinishFenceNV(p0);
        LabyDebugContext.glError("glFinishFenceNV", "p0", Integer.valueOf(p0));
    }

    public static void glSetFenceNV(int p0, int p1) {
        NVFence.glSetFenceNV(p0, p1);
        LabyDebugContext.glError("glSetFenceNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glDeleteFencesNV(int[] p0) {
        NVFence.glDeleteFencesNV(p0);
        LabyDebugContext.glError("glDeleteFencesNV", "p0", p0);
    }

    public static void glGenFencesNV(int[] p0) {
        NVFence.glGenFencesNV(p0);
        LabyDebugContext.glError("glGenFencesNV", "p0", p0);
    }

    public static void glGetFenceivNV(int p0, int p1, int[] p2) {
        NVFence.glGetFenceivNV(p0, p1, p2);
        LabyDebugContext.glError("glGetFenceivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

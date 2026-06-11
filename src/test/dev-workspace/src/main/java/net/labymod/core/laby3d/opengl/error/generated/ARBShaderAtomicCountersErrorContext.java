package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBShaderAtomicCounters;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBShaderAtomicCountersErrorContext.class */
public final class ARBShaderAtomicCountersErrorContext {
    public static void nglGetActiveAtomicCounterBufferiv(int p0, int p1, int p2, long p3) {
        ARBShaderAtomicCounters.nglGetActiveAtomicCounterBufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetActiveAtomicCounterBufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetActiveAtomicCounterBufferiv(int p0, int p1, int p2, IntBuffer p3) {
        ARBShaderAtomicCounters.glGetActiveAtomicCounterBufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveAtomicCounterBufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetActiveAtomicCounterBufferi(int p0, int p1, int p2) {
        int returnType = ARBShaderAtomicCounters.glGetActiveAtomicCounterBufferi(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveAtomicCounterBufferi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void glGetActiveAtomicCounterBufferiv(int p0, int p1, int p2, int[] p3) {
        ARBShaderAtomicCounters.glGetActiveAtomicCounterBufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveAtomicCounterBufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }
}

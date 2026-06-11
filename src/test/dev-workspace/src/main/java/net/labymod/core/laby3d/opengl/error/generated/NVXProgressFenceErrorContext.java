package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVXProgressFence;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVXProgressFenceErrorContext.class */
public final class NVXProgressFenceErrorContext {
    public static int glCreateProgressFenceNVX() {
        int returnType = NVXProgressFence.glCreateProgressFenceNVX();
        LabyDebugContext.glError("glCreateProgressFenceNVX", new Object[0]);
        return returnType;
    }

    public static void nglSignalSemaphoreui64NVX(int p0, int p1, long p2, long p3) {
        NVXProgressFence.nglSignalSemaphoreui64NVX(p0, p1, p2, p3);
        LabyDebugContext.glError("nglSignalSemaphoreui64NVX", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glSignalSemaphoreui64NVX(int p0, IntBuffer p1, LongBuffer p2) {
        NVXProgressFence.glSignalSemaphoreui64NVX(p0, p1, p2);
        LabyDebugContext.glError("glSignalSemaphoreui64NVX", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void nglWaitSemaphoreui64NVX(int p0, int p1, long p2, long p3) {
        NVXProgressFence.nglWaitSemaphoreui64NVX(p0, p1, p2, p3);
        LabyDebugContext.glError("nglWaitSemaphoreui64NVX", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glWaitSemaphoreui64NVX(int p0, IntBuffer p1, LongBuffer p2) {
        NVXProgressFence.glWaitSemaphoreui64NVX(p0, p1, p2);
        LabyDebugContext.glError("glWaitSemaphoreui64NVX", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void nglClientWaitSemaphoreui64NVX(int p0, long p1, long p2) {
        NVXProgressFence.nglClientWaitSemaphoreui64NVX(p0, p1, p2);
        LabyDebugContext.glError("nglClientWaitSemaphoreui64NVX", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glClientWaitSemaphoreui64NVX(IntBuffer p0, LongBuffer p1) {
        NVXProgressFence.glClientWaitSemaphoreui64NVX(p0, p1);
        LabyDebugContext.glError("glClientWaitSemaphoreui64NVX", "p0", p0, "p1", p1);
    }

    public static void glSignalSemaphoreui64NVX(int p0, int[] p1, long[] p2) {
        NVXProgressFence.glSignalSemaphoreui64NVX(p0, p1, p2);
        LabyDebugContext.glError("glSignalSemaphoreui64NVX", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glWaitSemaphoreui64NVX(int p0, int[] p1, long[] p2) {
        NVXProgressFence.glWaitSemaphoreui64NVX(p0, p1, p2);
        LabyDebugContext.glError("glWaitSemaphoreui64NVX", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glClientWaitSemaphoreui64NVX(int[] p0, long[] p1) {
        NVXProgressFence.glClientWaitSemaphoreui64NVX(p0, p1);
        LabyDebugContext.glError("glClientWaitSemaphoreui64NVX", "p0", p0, "p1", p1);
    }
}

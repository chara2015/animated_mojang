package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLARBPbuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLARBPbufferErrorContext.class */
public final class WGLARBPbufferErrorContext {
    public static long nwglCreatePbufferARB(long p0, int p1, int p2, int p3, long p4) {
        long returnType = WGLARBPbuffer.nwglCreatePbufferARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nwglCreatePbufferARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
        return returnType;
    }

    public static long wglCreatePbufferARB(long p0, int p1, int p2, int p3, IntBuffer p4) {
        long returnType = WGLARBPbuffer.wglCreatePbufferARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglCreatePbufferARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static long wglGetPbufferDCARB(long p0) {
        long returnType = WGLARBPbuffer.wglGetPbufferDCARB(p0);
        LabyDebugContext.glError("wglGetPbufferDCARB", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int wglReleasePbufferDCARB(long p0, long p1) {
        int returnType = WGLARBPbuffer.wglReleasePbufferDCARB(p0, p1);
        LabyDebugContext.glError("wglReleasePbufferDCARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static boolean wglDestroyPbufferARB(long p0) {
        boolean returnType = WGLARBPbuffer.wglDestroyPbufferARB(p0);
        LabyDebugContext.glError("wglDestroyPbufferARB", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nwglQueryPbufferARB(long p0, int p1, long p2) {
        int returnType = WGLARBPbuffer.nwglQueryPbufferARB(p0, p1, p2);
        LabyDebugContext.glError("nwglQueryPbufferARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean wglQueryPbufferARB(long p0, int p1, IntBuffer p2) {
        boolean returnType = WGLARBPbuffer.wglQueryPbufferARB(p0, p1, p2);
        LabyDebugContext.glError("wglQueryPbufferARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static long wglCreatePbufferARB(long p0, int p1, int p2, int p3, int[] p4) {
        long returnType = WGLARBPbuffer.wglCreatePbufferARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglCreatePbufferARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static boolean wglQueryPbufferARB(long p0, int p1, int[] p2) {
        boolean returnType = WGLARBPbuffer.wglQueryPbufferARB(p0, p1, p2);
        LabyDebugContext.glError("wglQueryPbufferARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }
}

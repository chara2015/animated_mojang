package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.CLongBuffer;
import org.lwjgl.opengl.GLXSGIXPbuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXSGIXPbufferErrorContext.class */
public final class GLXSGIXPbufferErrorContext {
    public static long nglXCreateGLXPbufferSGIX(long p0, long p1, int p2, int p3, long p4) {
        long returnType = GLXSGIXPbuffer.nglXCreateGLXPbufferSGIX(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglXCreateGLXPbufferSGIX", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
        return returnType;
    }

    public static long glXCreateGLXPbufferSGIX(long p0, long p1, int p2, int p3, IntBuffer p4) {
        long returnType = GLXSGIXPbuffer.glXCreateGLXPbufferSGIX(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glXCreateGLXPbufferSGIX", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static void glXDestroyGLXPbufferSGIX(long p0, long p1) {
        GLXSGIXPbuffer.glXDestroyGLXPbufferSGIX(p0, p1);
        LabyDebugContext.glError("glXDestroyGLXPbufferSGIX", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void nglXQueryGLXPbufferSGIX(long p0, long p1, int p2, long p3) {
        GLXSGIXPbuffer.nglXQueryGLXPbufferSGIX(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXQueryGLXPbufferSGIX", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glXQueryGLXPbufferSGIX(long p0, long p1, int p2, IntBuffer p3) {
        GLXSGIXPbuffer.glXQueryGLXPbufferSGIX(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQueryGLXPbufferSGIX", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glXSelectEventSGIX(long p0, long p1, long p2) {
        GLXSGIXPbuffer.glXSelectEventSGIX(p0, p1, p2);
        LabyDebugContext.glError("glXSelectEventSGIX", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void nglXGetSelectedEventSGIX(long p0, long p1, long p2) {
        GLXSGIXPbuffer.nglXGetSelectedEventSGIX(p0, p1, p2);
        LabyDebugContext.glError("nglXGetSelectedEventSGIX", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glXGetSelectedEventSGIX(long p0, long p1, CLongBuffer p2) {
        GLXSGIXPbuffer.glXGetSelectedEventSGIX(p0, p1, p2);
        LabyDebugContext.glError("glXGetSelectedEventSGIX", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static long glXCreateGLXPbufferSGIX(long p0, long p1, int p2, int p3, int[] p4) {
        long returnType = GLXSGIXPbuffer.glXCreateGLXPbufferSGIX(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glXCreateGLXPbufferSGIX", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static void glXQueryGLXPbufferSGIX(long p0, long p1, int p2, int[] p3) {
        GLXSGIXPbuffer.glXQueryGLXPbufferSGIX(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQueryGLXPbufferSGIX", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }
}

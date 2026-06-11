package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGL;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLErrorContext.class */
public final class WGLErrorContext {
    public static long nwglCreateContext(long p0, long p1, long p2) {
        long returnType = WGL.nwglCreateContext(p0, p1, p2);
        LabyDebugContext.glError("nwglCreateContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static long nwglCreateContext(long p0, long p1) {
        long returnType = WGL.nwglCreateContext(p0, p1);
        LabyDebugContext.glError("nwglCreateContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static long wglCreateContext(IntBuffer p0, long p1) {
        long returnType = WGL.wglCreateContext(p0, p1);
        LabyDebugContext.glError("wglCreateContext", "p0", p0, "p1", Long.valueOf(p1));
        return returnType;
    }

    public static long nwglCreateLayerContext(long p0, long p1, int p2, long p3) {
        long returnType = WGL.nwglCreateLayerContext(p0, p1, p2, p3);
        LabyDebugContext.glError("nwglCreateLayerContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static long nwglCreateLayerContext(long p0, long p1, int p2) {
        long returnType = WGL.nwglCreateLayerContext(p0, p1, p2);
        LabyDebugContext.glError("nwglCreateLayerContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static long wglCreateLayerContext(IntBuffer p0, long p1, int p2) {
        long returnType = WGL.wglCreateLayerContext(p0, p1, p2);
        LabyDebugContext.glError("wglCreateLayerContext", "p0", p0, "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static int nwglCopyContext(long p0, long p1, long p2, int p3, long p4) {
        int returnType = WGL.nwglCopyContext(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nwglCopyContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
        return returnType;
    }

    public static int nwglCopyContext(long p0, long p1, long p2, int p3) {
        int returnType = WGL.nwglCopyContext(p0, p1, p2, p3);
        LabyDebugContext.glError("nwglCopyContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static boolean wglCopyContext(IntBuffer p0, long p1, long p2, int p3) {
        boolean returnType = WGL.wglCopyContext(p0, p1, p2, p3);
        LabyDebugContext.glError("wglCopyContext", "p0", p0, "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static int nwglDeleteContext(long p0, long p1, long p2) {
        int returnType = WGL.nwglDeleteContext(p0, p1, p2);
        LabyDebugContext.glError("nwglDeleteContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int nwglDeleteContext(long p0, long p1) {
        int returnType = WGL.nwglDeleteContext(p0, p1);
        LabyDebugContext.glError("nwglDeleteContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static boolean wglDeleteContext(IntBuffer p0, long p1) {
        boolean returnType = WGL.wglDeleteContext(p0, p1);
        LabyDebugContext.glError("wglDeleteContext", "p0", p0, "p1", Long.valueOf(p1));
        return returnType;
    }

    public static long nwglGetCurrentContext(long p0, long p1) {
        long returnType = WGL.nwglGetCurrentContext(p0, p1);
        LabyDebugContext.glError("nwglGetCurrentContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static long nwglGetCurrentContext(long p0) {
        long returnType = WGL.nwglGetCurrentContext(p0);
        LabyDebugContext.glError("nwglGetCurrentContext", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long wglGetCurrentContext(IntBuffer p0) {
        long returnType = WGL.wglGetCurrentContext(p0);
        LabyDebugContext.glError("wglGetCurrentContext", "p0", p0);
        return returnType;
    }

    public static long wglGetCurrentDC() {
        long returnType = WGL.wglGetCurrentDC();
        LabyDebugContext.glError("wglGetCurrentDC", new Object[0]);
        return returnType;
    }

    public static long nwglGetProcAddress(long p0, long p1, long p2) {
        long returnType = WGL.nwglGetProcAddress(p0, p1, p2);
        LabyDebugContext.glError("nwglGetProcAddress", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static long nwglGetProcAddress(long p0, long p1) {
        long returnType = WGL.nwglGetProcAddress(p0, p1);
        LabyDebugContext.glError("nwglGetProcAddress", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static long wglGetProcAddress(IntBuffer p0, ByteBuffer p1) {
        long returnType = WGL.wglGetProcAddress(p0, p1);
        LabyDebugContext.glError("wglGetProcAddress", "p0", p0, "p1", p1);
        return returnType;
    }

    public static long wglGetProcAddress(IntBuffer p0, CharSequence p1) {
        long returnType = WGL.wglGetProcAddress(p0, p1);
        LabyDebugContext.glError("wglGetProcAddress", "p0", p0, "p1", p1);
        return returnType;
    }

    public static int nwglMakeCurrent(long p0, long p1, long p2, long p3) {
        int returnType = WGL.nwglMakeCurrent(p0, p1, p2, p3);
        LabyDebugContext.glError("nwglMakeCurrent", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static int nwglMakeCurrent(long p0, long p1, long p2) {
        int returnType = WGL.nwglMakeCurrent(p0, p1, p2);
        LabyDebugContext.glError("nwglMakeCurrent", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean wglMakeCurrent(IntBuffer p0, long p1, long p2) {
        boolean returnType = WGL.wglMakeCurrent(p0, p1, p2);
        LabyDebugContext.glError("wglMakeCurrent", "p0", p0, "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int nwglShareLists(long p0, long p1, long p2, long p3) {
        int returnType = WGL.nwglShareLists(p0, p1, p2, p3);
        LabyDebugContext.glError("nwglShareLists", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static int nwglShareLists(long p0, long p1, long p2) {
        int returnType = WGL.nwglShareLists(p0, p1, p2);
        LabyDebugContext.glError("nwglShareLists", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean wglShareLists(IntBuffer p0, long p1, long p2) {
        boolean returnType = WGL.wglShareLists(p0, p1, p2);
        LabyDebugContext.glError("wglShareLists", "p0", p0, "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }
}

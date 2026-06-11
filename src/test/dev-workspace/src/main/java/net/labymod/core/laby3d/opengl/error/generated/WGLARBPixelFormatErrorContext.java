package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLARBPixelFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLARBPixelFormatErrorContext.class */
public final class WGLARBPixelFormatErrorContext {
    public static int nwglGetPixelFormatAttribivARB(long p0, int p1, int p2, int p3, long p4, long p5) {
        int returnType = WGLARBPixelFormat.nwglGetPixelFormatAttribivARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nwglGetPixelFormatAttribivARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
        return returnType;
    }

    public static boolean wglGetPixelFormatAttribivARB(long p0, int p1, int p2, IntBuffer p3, IntBuffer p4) {
        boolean returnType = WGLARBPixelFormat.wglGetPixelFormatAttribivARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglGetPixelFormatAttribivARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
        return returnType;
    }

    public static boolean wglGetPixelFormatAttribiARB(long p0, int p1, int p2, int p3, IntBuffer p4) {
        boolean returnType = WGLARBPixelFormat.wglGetPixelFormatAttribiARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglGetPixelFormatAttribiARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static int nwglGetPixelFormatAttribfvARB(long p0, int p1, int p2, int p3, long p4, long p5) {
        int returnType = WGLARBPixelFormat.nwglGetPixelFormatAttribfvARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nwglGetPixelFormatAttribfvARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
        return returnType;
    }

    public static boolean wglGetPixelFormatAttribfvARB(long p0, int p1, int p2, IntBuffer p3, FloatBuffer p4) {
        boolean returnType = WGLARBPixelFormat.wglGetPixelFormatAttribfvARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglGetPixelFormatAttribfvARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
        return returnType;
    }

    public static boolean wglGetPixelFormatAttribfARB(long p0, int p1, int p2, int p3, FloatBuffer p4) {
        boolean returnType = WGLARBPixelFormat.wglGetPixelFormatAttribfARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglGetPixelFormatAttribfARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static int nwglChoosePixelFormatARB(long p0, long p1, long p2, int p3, long p4, long p5) {
        int returnType = WGLARBPixelFormat.nwglChoosePixelFormatARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nwglChoosePixelFormatARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
        return returnType;
    }

    public static boolean wglChoosePixelFormatARB(long p0, IntBuffer p1, FloatBuffer p2, IntBuffer p3, IntBuffer p4) {
        boolean returnType = WGLARBPixelFormat.wglChoosePixelFormatARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglChoosePixelFormatARB", "p0", Long.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4);
        return returnType;
    }

    public static boolean wglGetPixelFormatAttribivARB(long p0, int p1, int p2, int[] p3, int[] p4) {
        boolean returnType = WGLARBPixelFormat.wglGetPixelFormatAttribivARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglGetPixelFormatAttribivARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
        return returnType;
    }

    public static boolean wglGetPixelFormatAttribfvARB(long p0, int p1, int p2, int[] p3, float[] p4) {
        boolean returnType = WGLARBPixelFormat.wglGetPixelFormatAttribfvARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglGetPixelFormatAttribfvARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
        return returnType;
    }

    public static boolean wglChoosePixelFormatARB(long p0, int[] p1, float[] p2, int[] p3, int[] p4) {
        boolean returnType = WGLARBPixelFormat.wglChoosePixelFormatARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglChoosePixelFormatARB", "p0", Long.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4);
        return returnType;
    }
}

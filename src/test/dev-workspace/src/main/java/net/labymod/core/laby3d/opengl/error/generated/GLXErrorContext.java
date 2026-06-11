package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLX;
import org.lwjgl.system.linux.XVisualInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXErrorContext.class */
public final class GLXErrorContext {
    public static int nglXQueryExtension(long p0, long p1, long p2) {
        int returnType = GLX.nglXQueryExtension(p0, p1, p2);
        LabyDebugContext.glError("nglXQueryExtension", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean glXQueryExtension(long p0, IntBuffer p1, IntBuffer p2) {
        boolean returnType = GLX.glXQueryExtension(p0, p1, p2);
        LabyDebugContext.glError("glXQueryExtension", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
        return returnType;
    }

    public static int nglXQueryVersion(long p0, long p1, long p2) {
        int returnType = GLX.nglXQueryVersion(p0, p1, p2);
        LabyDebugContext.glError("nglXQueryVersion", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean glXQueryVersion(long p0, IntBuffer p1, IntBuffer p2) {
        boolean returnType = GLX.glXQueryVersion(p0, p1, p2);
        LabyDebugContext.glError("glXQueryVersion", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
        return returnType;
    }

    public static int nglXGetConfig(long p0, long p1, int p2, long p3) {
        int returnType = GLX.nglXGetConfig(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXGetConfig", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static int glXGetConfig(long p0, XVisualInfo p1, int p2, IntBuffer p3) {
        int returnType = GLX.glXGetConfig(p0, p1, p2, p3);
        LabyDebugContext.glError("glXGetConfig", "p0", Long.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static long nglXChooseVisual(long p0, int p1, long p2) {
        long returnType = GLX.nglXChooseVisual(p0, p1, p2);
        LabyDebugContext.glError("nglXChooseVisual", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static XVisualInfo glXChooseVisual(long p0, int p1, IntBuffer p2) {
        XVisualInfo returnType = GLX.glXChooseVisual(p0, p1, p2);
        LabyDebugContext.glError("glXChooseVisual", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static long nglXCreateContext(long p0, long p1, long p2, int p3) {
        long returnType = GLX.nglXCreateContext(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXCreateContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static long glXCreateContext(long p0, XVisualInfo p1, long p2, boolean p3) {
        long returnType = GLX.glXCreateContext(p0, p1, p2, p3);
        LabyDebugContext.glError("glXCreateContext", "p0", Long.valueOf(p0), "p1", p1, "p2", Long.valueOf(p2), "p3", Boolean.valueOf(p3));
        return returnType;
    }

    public static boolean glXMakeCurrent(long p0, long p1, long p2) {
        boolean returnType = GLX.glXMakeCurrent(p0, p1, p2);
        LabyDebugContext.glError("glXMakeCurrent", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static void glXCopyContext(long p0, long p1, long p2, long p3) {
        GLX.glXCopyContext(p0, p1, p2, p3);
        LabyDebugContext.glError("glXCopyContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static boolean glXIsDirect(long p0, long p1) {
        boolean returnType = GLX.glXIsDirect(p0, p1);
        LabyDebugContext.glError("glXIsDirect", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static void glXDestroyContext(long p0, long p1) {
        GLX.glXDestroyContext(p0, p1);
        LabyDebugContext.glError("glXDestroyContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static long glXGetCurrentContext() {
        long returnType = GLX.glXGetCurrentContext();
        LabyDebugContext.glError("glXGetCurrentContext", new Object[0]);
        return returnType;
    }

    public static long glXGetCurrentDrawable() {
        long returnType = GLX.glXGetCurrentDrawable();
        LabyDebugContext.glError("glXGetCurrentDrawable", new Object[0]);
        return returnType;
    }

    public static void glXWaitGL() {
        GLX.glXWaitGL();
        LabyDebugContext.glError("glXWaitGL", new Object[0]);
    }

    public static void glXWaitX() {
        GLX.glXWaitX();
        LabyDebugContext.glError("glXWaitX", new Object[0]);
    }

    public static void glXSwapBuffers(long p0, long p1) {
        GLX.glXSwapBuffers(p0, p1);
        LabyDebugContext.glError("glXSwapBuffers", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glXUseXFont(long p0, int p1, int p2, int p3) {
        GLX.glXUseXFont(p0, p1, p2, p3);
        LabyDebugContext.glError("glXUseXFont", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static long nglXCreateGLXPixmap(long p0, long p1, long p2) {
        long returnType = GLX.nglXCreateGLXPixmap(p0, p1, p2);
        LabyDebugContext.glError("nglXCreateGLXPixmap", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static long glXCreateGLXPixmap(long p0, XVisualInfo p1, long p2) {
        long returnType = GLX.glXCreateGLXPixmap(p0, p1, p2);
        LabyDebugContext.glError("glXCreateGLXPixmap", "p0", Long.valueOf(p0), "p1", p1, "p2", Long.valueOf(p2));
        return returnType;
    }

    public static void glXDestroyGLXPixmap(long p0, long p1) {
        GLX.glXDestroyGLXPixmap(p0, p1);
        LabyDebugContext.glError("glXDestroyGLXPixmap", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static boolean glXQueryExtension(long p0, int[] p1, int[] p2) {
        boolean returnType = GLX.glXQueryExtension(p0, p1, p2);
        LabyDebugContext.glError("glXQueryExtension", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
        return returnType;
    }

    public static boolean glXQueryVersion(long p0, int[] p1, int[] p2) {
        boolean returnType = GLX.glXQueryVersion(p0, p1, p2);
        LabyDebugContext.glError("glXQueryVersion", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
        return returnType;
    }

    public static int glXGetConfig(long p0, XVisualInfo p1, int p2, int[] p3) {
        int returnType = GLX.glXGetConfig(p0, p1, p2, p3);
        LabyDebugContext.glError("glXGetConfig", "p0", Long.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static XVisualInfo glXChooseVisual(long p0, int p1, int[] p2) {
        XVisualInfo returnType = GLX.glXChooseVisual(p0, p1, p2);
        LabyDebugContext.glError("glXChooseVisual", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }
}

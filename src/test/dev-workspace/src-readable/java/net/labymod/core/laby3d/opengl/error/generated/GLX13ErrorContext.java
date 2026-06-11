package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.CLongBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GLX13;
import org.lwjgl.system.linux.XVisualInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLX13ErrorContext.class */
public final class GLX13ErrorContext {
    public static long nglXGetFBConfigs(long p0, int p1, long p2) {
        long returnType = GLX13.nglXGetFBConfigs(p0, p1, p2);
        LabyDebugContext.glError("nglXGetFBConfigs", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static PointerBuffer glXGetFBConfigs(long p0, int p1) {
        PointerBuffer returnType = GLX13.glXGetFBConfigs(p0, p1);
        LabyDebugContext.glError("glXGetFBConfigs", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static long nglXChooseFBConfig(long p0, int p1, long p2, long p3) {
        long returnType = GLX13.nglXChooseFBConfig(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXChooseFBConfig", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static PointerBuffer glXChooseFBConfig(long p0, int p1, IntBuffer p2) {
        PointerBuffer returnType = GLX13.glXChooseFBConfig(p0, p1, p2);
        LabyDebugContext.glError("glXChooseFBConfig", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int nglXGetFBConfigAttrib(long p0, long p1, int p2, long p3) {
        int returnType = GLX13.nglXGetFBConfigAttrib(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXGetFBConfigAttrib", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static int glXGetFBConfigAttrib(long p0, long p1, int p2, IntBuffer p3) {
        int returnType = GLX13.glXGetFBConfigAttrib(p0, p1, p2, p3);
        LabyDebugContext.glError("glXGetFBConfigAttrib", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static long nglXGetVisualFromFBConfig(long p0, long p1) {
        long returnType = GLX13.nglXGetVisualFromFBConfig(p0, p1);
        LabyDebugContext.glError("nglXGetVisualFromFBConfig", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static XVisualInfo glXGetVisualFromFBConfig(long p0, long p1) {
        XVisualInfo returnType = GLX13.glXGetVisualFromFBConfig(p0, p1);
        LabyDebugContext.glError("glXGetVisualFromFBConfig", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static long nglXCreateWindow(long p0, long p1, long p2, long p3) {
        long returnType = GLX13.nglXCreateWindow(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXCreateWindow", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static long glXCreateWindow(long p0, long p1, long p2, IntBuffer p3) {
        long returnType = GLX13.glXCreateWindow(p0, p1, p2, p3);
        LabyDebugContext.glError("glXCreateWindow", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static long nglXCreatePixmap(long p0, long p1, long p2, long p3) {
        long returnType = GLX13.nglXCreatePixmap(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXCreatePixmap", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static long glXCreatePixmap(long p0, long p1, long p2, IntBuffer p3) {
        long returnType = GLX13.glXCreatePixmap(p0, p1, p2, p3);
        LabyDebugContext.glError("glXCreatePixmap", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static void glXDestroyPixmap(long p0, long p1) {
        GLX13.glXDestroyPixmap(p0, p1);
        LabyDebugContext.glError("glXDestroyPixmap", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static long nglXCreatePbuffer(long p0, long p1, long p2) {
        long returnType = GLX13.nglXCreatePbuffer(p0, p1, p2);
        LabyDebugContext.glError("nglXCreatePbuffer", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static long glXCreatePbuffer(long p0, long p1, IntBuffer p2) {
        long returnType = GLX13.glXCreatePbuffer(p0, p1, p2);
        LabyDebugContext.glError("glXCreatePbuffer", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static void glXDestroyPbuffer(long p0, long p1) {
        GLX13.glXDestroyPbuffer(p0, p1);
        LabyDebugContext.glError("glXDestroyPbuffer", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void nglXQueryDrawable(long p0, long p1, int p2, long p3) {
        GLX13.nglXQueryDrawable(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXQueryDrawable", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glXQueryDrawable(long p0, long p1, int p2, IntBuffer p3) {
        GLX13.glXQueryDrawable(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQueryDrawable", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glXQueryDrawable(long p0, long p1, int p2) {
        int returnType = GLX13.glXQueryDrawable(p0, p1, p2);
        LabyDebugContext.glError("glXQueryDrawable", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static long glXCreateNewContext(long p0, long p1, int p2, long p3, boolean p4) {
        long returnType = GLX13.glXCreateNewContext(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glXCreateNewContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Boolean.valueOf(p4));
        return returnType;
    }

    public static boolean glXMakeContextCurrent(long p0, long p1, long p2, long p3) {
        boolean returnType = GLX13.glXMakeContextCurrent(p0, p1, p2, p3);
        LabyDebugContext.glError("glXMakeContextCurrent", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static long glXGetCurrentReadDrawable() {
        long returnType = GLX13.glXGetCurrentReadDrawable();
        LabyDebugContext.glError("glXGetCurrentReadDrawable", new Object[0]);
        return returnType;
    }

    public static int nglXQueryContext(long p0, long p1, int p2, long p3) {
        int returnType = GLX13.nglXQueryContext(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXQueryContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static int glXQueryContext(long p0, long p1, int p2, IntBuffer p3) {
        int returnType = GLX13.glXQueryContext(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQueryContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static void glXSelectEvent(long p0, long p1, long p2) {
        GLX13.glXSelectEvent(p0, p1, p2);
        LabyDebugContext.glError("glXSelectEvent", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void nglXGetSelectedEvent(long p0, long p1, long p2) {
        GLX13.nglXGetSelectedEvent(p0, p1, p2);
        LabyDebugContext.glError("nglXGetSelectedEvent", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glXGetSelectedEvent(long p0, long p1, CLongBuffer p2) {
        GLX13.glXGetSelectedEvent(p0, p1, p2);
        LabyDebugContext.glError("glXGetSelectedEvent", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static PointerBuffer glXChooseFBConfig(long p0, int p1, int[] p2) {
        PointerBuffer returnType = GLX13.glXChooseFBConfig(p0, p1, p2);
        LabyDebugContext.glError("glXChooseFBConfig", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int glXGetFBConfigAttrib(long p0, long p1, int p2, int[] p3) {
        int returnType = GLX13.glXGetFBConfigAttrib(p0, p1, p2, p3);
        LabyDebugContext.glError("glXGetFBConfigAttrib", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static long glXCreateWindow(long p0, long p1, long p2, int[] p3) {
        long returnType = GLX13.glXCreateWindow(p0, p1, p2, p3);
        LabyDebugContext.glError("glXCreateWindow", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static long glXCreatePixmap(long p0, long p1, long p2, int[] p3) {
        long returnType = GLX13.glXCreatePixmap(p0, p1, p2, p3);
        LabyDebugContext.glError("glXCreatePixmap", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static long glXCreatePbuffer(long p0, long p1, int[] p2) {
        long returnType = GLX13.glXCreatePbuffer(p0, p1, p2);
        LabyDebugContext.glError("glXCreatePbuffer", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static void glXQueryDrawable(long p0, long p1, int p2, int[] p3) {
        GLX13.glXQueryDrawable(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQueryDrawable", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glXQueryContext(long p0, long p1, int p2, int[] p3) {
        int returnType = GLX13.glXQueryContext(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQueryContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLX11;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLX11ErrorContext.class */
public final class GLX11ErrorContext {
    public static long nglXQueryExtensionsString(long p0, int p1) {
        long returnType = GLX11.nglXQueryExtensionsString(p0, p1);
        LabyDebugContext.glError("nglXQueryExtensionsString", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glXQueryExtensionsString(long p0, int p1) {
        String returnType = GLX11.glXQueryExtensionsString(p0, p1);
        LabyDebugContext.glError("glXQueryExtensionsString", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static long nglXGetClientString(long p0, int p1) {
        long returnType = GLX11.nglXGetClientString(p0, p1);
        LabyDebugContext.glError("nglXGetClientString", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glXGetClientString(long p0, int p1) {
        String returnType = GLX11.glXGetClientString(p0, p1);
        LabyDebugContext.glError("glXGetClientString", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static long nglXQueryServerString(long p0, int p1, int p2) {
        long returnType = GLX11.nglXQueryServerString(p0, p1, p2);
        LabyDebugContext.glError("nglXQueryServerString", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static String glXQueryServerString(long p0, int p1, int p2) {
        String returnType = GLX11.glXQueryServerString(p0, p1, p2);
        LabyDebugContext.glError("glXQueryServerString", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLARBBufferRegion;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLARBBufferRegionErrorContext.class */
public final class WGLARBBufferRegionErrorContext {
    public static long wglCreateBufferRegionARB(long p0, int p1, int p2) {
        long returnType = WGLARBBufferRegion.wglCreateBufferRegionARB(p0, p1, p2);
        LabyDebugContext.glError("wglCreateBufferRegionARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void wglDeleteBufferRegionARB(long p0) {
        WGLARBBufferRegion.wglDeleteBufferRegionARB(p0);
        LabyDebugContext.glError("wglDeleteBufferRegionARB", "p0", Long.valueOf(p0));
    }

    public static boolean wglSaveBufferRegionARB(long p0, int p1, int p2, int p3, int p4) {
        boolean returnType = WGLARBBufferRegion.wglSaveBufferRegionARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglSaveBufferRegionARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
        return returnType;
    }

    public static boolean wglRestoreBufferRegionARB(long p0, int p1, int p2, int p3, int p4, int p5, int p6) {
        boolean returnType = WGLARBBufferRegion.wglRestoreBufferRegionARB(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("wglRestoreBufferRegionARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
        return returnType;
    }
}

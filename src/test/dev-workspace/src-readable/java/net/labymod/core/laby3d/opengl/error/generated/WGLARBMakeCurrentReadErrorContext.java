package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLARBMakeCurrentRead;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLARBMakeCurrentReadErrorContext.class */
public final class WGLARBMakeCurrentReadErrorContext {
    public static boolean wglMakeContextCurrentARB(long p0, long p1, long p2) {
        boolean returnType = WGLARBMakeCurrentRead.wglMakeContextCurrentARB(p0, p1, p2);
        LabyDebugContext.glError("wglMakeContextCurrentARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static long wglGetCurrentReadDCARB() {
        long returnType = WGLARBMakeCurrentRead.wglGetCurrentReadDCARB();
        LabyDebugContext.glError("wglGetCurrentReadDCARB", new Object[0]);
        return returnType;
    }
}

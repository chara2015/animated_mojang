package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLARBExtensionsString;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLARBExtensionsStringErrorContext.class */
public final class WGLARBExtensionsStringErrorContext {
    public static long nwglGetExtensionsStringARB(long p0) {
        long returnType = WGLARBExtensionsString.nwglGetExtensionsStringARB(p0);
        LabyDebugContext.glError("nwglGetExtensionsStringARB", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static String wglGetExtensionsStringARB(long p0) {
        String returnType = WGLARBExtensionsString.wglGetExtensionsStringARB(p0);
        LabyDebugContext.glError("wglGetExtensionsStringARB", "p0", Long.valueOf(p0));
        return returnType;
    }
}

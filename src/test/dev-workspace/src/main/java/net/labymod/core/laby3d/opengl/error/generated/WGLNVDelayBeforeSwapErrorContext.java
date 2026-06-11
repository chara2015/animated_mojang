package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLNVDelayBeforeSwap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLNVDelayBeforeSwapErrorContext.class */
public final class WGLNVDelayBeforeSwapErrorContext {
    public static boolean wglDelayBeforeSwapNV(long p0, float p1) {
        boolean returnType = WGLNVDelayBeforeSwap.wglDelayBeforeSwapNV(p0, p1);
        LabyDebugContext.glError("wglDelayBeforeSwapNV", "p0", Long.valueOf(p0), "p1", Float.valueOf(p1));
        return returnType;
    }
}

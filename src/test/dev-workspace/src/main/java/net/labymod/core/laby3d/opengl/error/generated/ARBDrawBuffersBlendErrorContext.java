package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBDrawBuffersBlend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBDrawBuffersBlendErrorContext.class */
public final class ARBDrawBuffersBlendErrorContext {
    public static void glBlendEquationiARB(int p0, int p1) {
        ARBDrawBuffersBlend.glBlendEquationiARB(p0, p1);
        LabyDebugContext.glError("glBlendEquationiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glBlendEquationSeparateiARB(int p0, int p1, int p2) {
        ARBDrawBuffersBlend.glBlendEquationSeparateiARB(p0, p1, p2);
        LabyDebugContext.glError("glBlendEquationSeparateiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBlendFunciARB(int p0, int p1, int p2) {
        ARBDrawBuffersBlend.glBlendFunciARB(p0, p1, p2);
        LabyDebugContext.glError("glBlendFunciARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBlendFuncSeparateiARB(int p0, int p1, int p2, int p3, int p4) {
        ARBDrawBuffersBlend.glBlendFuncSeparateiARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glBlendFuncSeparateiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }
}

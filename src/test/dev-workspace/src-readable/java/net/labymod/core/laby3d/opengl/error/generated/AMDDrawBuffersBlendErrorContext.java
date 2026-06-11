package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.AMDDrawBuffersBlend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/AMDDrawBuffersBlendErrorContext.class */
public final class AMDDrawBuffersBlendErrorContext {
    public static void glBlendFuncIndexedAMD(int p0, int p1, int p2) {
        AMDDrawBuffersBlend.glBlendFuncIndexedAMD(p0, p1, p2);
        LabyDebugContext.glError("glBlendFuncIndexedAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBlendFuncSeparateIndexedAMD(int p0, int p1, int p2, int p3, int p4) {
        AMDDrawBuffersBlend.glBlendFuncSeparateIndexedAMD(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glBlendFuncSeparateIndexedAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glBlendEquationIndexedAMD(int p0, int p1) {
        AMDDrawBuffersBlend.glBlendEquationIndexedAMD(p0, p1);
        LabyDebugContext.glError("glBlendEquationIndexedAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glBlendEquationSeparateIndexedAMD(int p0, int p1, int p2) {
        AMDDrawBuffersBlend.glBlendEquationSeparateIndexedAMD(p0, p1, p2);
        LabyDebugContext.glError("glBlendEquationSeparateIndexedAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }
}

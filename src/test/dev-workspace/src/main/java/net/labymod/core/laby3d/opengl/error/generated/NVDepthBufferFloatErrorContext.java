package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVDepthBufferFloat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVDepthBufferFloatErrorContext.class */
public final class NVDepthBufferFloatErrorContext {
    public static void glDepthRangedNV(double p0, double p1) {
        NVDepthBufferFloat.glDepthRangedNV(p0, p1);
        LabyDebugContext.glError("glDepthRangedNV", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void glClearDepthdNV(double p0) {
        NVDepthBufferFloat.glClearDepthdNV(p0);
        LabyDebugContext.glError("glClearDepthdNV", "p0", Double.valueOf(p0));
    }

    public static void glDepthBoundsdNV(double p0, double p1) {
        NVDepthBufferFloat.glDepthBoundsdNV(p0, p1);
        LabyDebugContext.glError("glDepthBoundsdNV", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1));
    }
}

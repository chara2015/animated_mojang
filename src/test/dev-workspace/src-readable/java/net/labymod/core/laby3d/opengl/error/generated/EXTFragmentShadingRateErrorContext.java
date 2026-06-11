package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTFragmentShadingRate;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTFragmentShadingRateErrorContext.class */
public final class EXTFragmentShadingRateErrorContext {
    public static void glShadingRateEXT(int p0) {
        EXTFragmentShadingRate.glShadingRateEXT(p0);
        LabyDebugContext.glError("glShadingRateEXT", "p0", Integer.valueOf(p0));
    }

    public static void glShadingRateCombinerOpsEXT(int p0, int p1) {
        EXTFragmentShadingRate.glShadingRateCombinerOpsEXT(p0, p1);
        LabyDebugContext.glError("glShadingRateCombinerOpsEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glFramebufferShadingRateEXT(int p0, int p1, int p2, int p3, int p4, int p5, int p6) {
        EXTFragmentShadingRate.glFramebufferShadingRateEXT(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glFramebufferShadingRateEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void nglGetFragmentShadingRatesEXT(int p0, int p1, long p2, long p3) {
        EXTFragmentShadingRate.nglGetFragmentShadingRatesEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetFragmentShadingRatesEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetFragmentShadingRatesEXT(int p0, IntBuffer p1, IntBuffer p2) {
        EXTFragmentShadingRate.glGetFragmentShadingRatesEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetFragmentShadingRatesEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetFragmentShadingRatesEXT(int p0, int[] p1, int[] p2) {
        EXTFragmentShadingRate.glGetFragmentShadingRatesEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetFragmentShadingRatesEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.KHRRobustness;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/KHRRobustnessErrorContext.class */
public final class KHRRobustnessErrorContext {
    public static int glGetGraphicsResetStatus() {
        int returnType = KHRRobustness.glGetGraphicsResetStatus();
        LabyDebugContext.glError("glGetGraphicsResetStatus", new Object[0]);
        return returnType;
    }

    public static void nglReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        KHRRobustness.nglReadnPixels(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        KHRRobustness.glReadnPixels(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6) {
        KHRRobustness.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, ShortBuffer p6) {
        KHRRobustness.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, IntBuffer p6) {
        KHRRobustness.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, FloatBuffer p6) {
        KHRRobustness.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void nglGetnUniformfv(int p0, int p1, int p2, long p3) {
        KHRRobustness.nglGetnUniformfv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnUniformfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnUniformfv(int p0, int p1, FloatBuffer p2) {
        KHRRobustness.glGetnUniformfv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetnUniformf(int p0, int p1) {
        float returnType = KHRRobustness.glGetnUniformf(p0, p1);
        LabyDebugContext.glError("glGetnUniformf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetnUniformiv(int p0, int p1, int p2, long p3) {
        KHRRobustness.nglGetnUniformiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnUniformiv(int p0, int p1, IntBuffer p2) {
        KHRRobustness.glGetnUniformiv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetnUniformi(int p0, int p1) {
        int returnType = KHRRobustness.glGetnUniformi(p0, p1);
        LabyDebugContext.glError("glGetnUniformi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetnUniformuiv(int p0, int p1, int p2, long p3) {
        KHRRobustness.nglGetnUniformuiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnUniformuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnUniformuiv(int p0, int p1, IntBuffer p2) {
        KHRRobustness.glGetnUniformuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetnUniformui(int p0, int p1) {
        int returnType = KHRRobustness.glGetnUniformui(p0, p1);
        LabyDebugContext.glError("glGetnUniformui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, short[] p6) {
        KHRRobustness.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, int[] p6) {
        KHRRobustness.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, float[] p6) {
        KHRRobustness.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glGetnUniformfv(int p0, int p1, float[] p2) {
        KHRRobustness.glGetnUniformfv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetnUniformiv(int p0, int p1, int[] p2) {
        KHRRobustness.glGetnUniformiv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetnUniformuiv(int p0, int p1, int[] p2) {
        KHRRobustness.glGetnUniformuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

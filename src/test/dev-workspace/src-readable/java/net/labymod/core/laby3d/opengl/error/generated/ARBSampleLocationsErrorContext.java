package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBSampleLocations;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBSampleLocationsErrorContext.class */
public final class ARBSampleLocationsErrorContext {
    public static void nglFramebufferSampleLocationsfvARB(int p0, int p1, int p2, long p3) {
        ARBSampleLocations.nglFramebufferSampleLocationsfvARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglFramebufferSampleLocationsfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glFramebufferSampleLocationsfvARB(int p0, int p1, FloatBuffer p2) {
        ARBSampleLocations.glFramebufferSampleLocationsfvARB(p0, p1, p2);
        LabyDebugContext.glError("glFramebufferSampleLocationsfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglNamedFramebufferSampleLocationsfvARB(int p0, int p1, int p2, long p3) {
        ARBSampleLocations.nglNamedFramebufferSampleLocationsfvARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglNamedFramebufferSampleLocationsfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glNamedFramebufferSampleLocationsfvARB(int p0, int p1, FloatBuffer p2) {
        ARBSampleLocations.glNamedFramebufferSampleLocationsfvARB(p0, p1, p2);
        LabyDebugContext.glError("glNamedFramebufferSampleLocationsfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glEvaluateDepthValuesARB() {
        ARBSampleLocations.glEvaluateDepthValuesARB();
        LabyDebugContext.glError("glEvaluateDepthValuesARB", new Object[0]);
    }

    public static void glFramebufferSampleLocationsfvARB(int p0, int p1, float[] p2) {
        ARBSampleLocations.glFramebufferSampleLocationsfvARB(p0, p1, p2);
        LabyDebugContext.glError("glFramebufferSampleLocationsfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glNamedFramebufferSampleLocationsfvARB(int p0, int p1, float[] p2) {
        ARBSampleLocations.glNamedFramebufferSampleLocationsfvARB(p0, p1, p2);
        LabyDebugContext.glError("glNamedFramebufferSampleLocationsfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

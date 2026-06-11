package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVSampleLocations;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVSampleLocationsErrorContext.class */
public final class NVSampleLocationsErrorContext {
    public static void nglFramebufferSampleLocationsfvNV(int p0, int p1, int p2, long p3) {
        NVSampleLocations.nglFramebufferSampleLocationsfvNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglFramebufferSampleLocationsfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glFramebufferSampleLocationsfvNV(int p0, int p1, FloatBuffer p2) {
        NVSampleLocations.glFramebufferSampleLocationsfvNV(p0, p1, p2);
        LabyDebugContext.glError("glFramebufferSampleLocationsfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglNamedFramebufferSampleLocationsfvNV(int p0, int p1, int p2, long p3) {
        NVSampleLocations.nglNamedFramebufferSampleLocationsfvNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglNamedFramebufferSampleLocationsfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glNamedFramebufferSampleLocationsfvNV(int p0, int p1, FloatBuffer p2) {
        NVSampleLocations.glNamedFramebufferSampleLocationsfvNV(p0, p1, p2);
        LabyDebugContext.glError("glNamedFramebufferSampleLocationsfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glResolveDepthValuesNV() {
        NVSampleLocations.glResolveDepthValuesNV();
        LabyDebugContext.glError("glResolveDepthValuesNV", new Object[0]);
    }

    public static void glFramebufferSampleLocationsfvNV(int p0, int p1, float[] p2) {
        NVSampleLocations.glFramebufferSampleLocationsfvNV(p0, p1, p2);
        LabyDebugContext.glError("glFramebufferSampleLocationsfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glNamedFramebufferSampleLocationsfvNV(int p0, int p1, float[] p2) {
        NVSampleLocations.glNamedFramebufferSampleLocationsfvNV(p0, p1, p2);
        LabyDebugContext.glError("glNamedFramebufferSampleLocationsfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

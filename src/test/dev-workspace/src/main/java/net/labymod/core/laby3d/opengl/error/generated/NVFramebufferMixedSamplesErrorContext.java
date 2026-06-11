package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVFramebufferMixedSamples;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVFramebufferMixedSamplesErrorContext.class */
public final class NVFramebufferMixedSamplesErrorContext {
    public static void glRasterSamplesEXT(int p0, boolean p1) {
        NVFramebufferMixedSamples.glRasterSamplesEXT(p0, p1);
        LabyDebugContext.glError("glRasterSamplesEXT", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1));
    }

    public static void nglCoverageModulationTableNV(int p0, long p1) {
        NVFramebufferMixedSamples.nglCoverageModulationTableNV(p0, p1);
        LabyDebugContext.glError("nglCoverageModulationTableNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCoverageModulationTableNV(FloatBuffer p0) {
        NVFramebufferMixedSamples.glCoverageModulationTableNV(p0);
        LabyDebugContext.glError("glCoverageModulationTableNV", "p0", p0);
    }

    public static void nglGetCoverageModulationTableNV(int p0, long p1) {
        NVFramebufferMixedSamples.nglGetCoverageModulationTableNV(p0, p1);
        LabyDebugContext.glError("nglGetCoverageModulationTableNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetCoverageModulationTableNV(FloatBuffer p0) {
        NVFramebufferMixedSamples.glGetCoverageModulationTableNV(p0);
        LabyDebugContext.glError("glGetCoverageModulationTableNV", "p0", p0);
    }

    public static void glCoverageModulationNV(int p0) {
        NVFramebufferMixedSamples.glCoverageModulationNV(p0);
        LabyDebugContext.glError("glCoverageModulationNV", "p0", Integer.valueOf(p0));
    }

    public static void glCoverageModulationTableNV(float[] p0) {
        NVFramebufferMixedSamples.glCoverageModulationTableNV(p0);
        LabyDebugContext.glError("glCoverageModulationTableNV", "p0", p0);
    }

    public static void glGetCoverageModulationTableNV(float[] p0) {
        NVFramebufferMixedSamples.glGetCoverageModulationTableNV(p0);
        LabyDebugContext.glError("glGetCoverageModulationTableNV", "p0", p0);
    }
}

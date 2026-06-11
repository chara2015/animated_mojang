package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVExplicitMultisample;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVExplicitMultisampleErrorContext.class */
public final class NVExplicitMultisampleErrorContext {
    public static void nglGetMultisamplefvNV(int p0, int p1, long p2) {
        NVExplicitMultisample.nglGetMultisamplefvNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetMultisamplefvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetMultisamplefvNV(int p0, int p1, FloatBuffer p2) {
        NVExplicitMultisample.glGetMultisamplefvNV(p0, p1, p2);
        LabyDebugContext.glError("glGetMultisamplefvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSampleMaskIndexedNV(int p0, int p1) {
        NVExplicitMultisample.glSampleMaskIndexedNV(p0, p1);
        LabyDebugContext.glError("glSampleMaskIndexedNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glTexRenderbufferNV(int p0, int p1) {
        NVExplicitMultisample.glTexRenderbufferNV(p0, p1);
        LabyDebugContext.glError("glTexRenderbufferNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glGetMultisamplefvNV(int p0, int p1, float[] p2) {
        NVExplicitMultisample.glGetMultisamplefvNV(p0, p1, p2);
        LabyDebugContext.glError("glGetMultisamplefvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBTextureMultisample;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBTextureMultisampleErrorContext.class */
public final class ARBTextureMultisampleErrorContext {
    public static void glTexImage2DMultisample(int p0, int p1, int p2, int p3, int p4, boolean p5) {
        ARBTextureMultisample.glTexImage2DMultisample(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTexImage2DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Boolean.valueOf(p5));
    }

    public static void glTexImage3DMultisample(int p0, int p1, int p2, int p3, int p4, int p5, boolean p6) {
        ARBTextureMultisample.glTexImage3DMultisample(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexImage3DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Boolean.valueOf(p6));
    }

    public static void nglGetMultisamplefv(int p0, int p1, long p2) {
        ARBTextureMultisample.nglGetMultisamplefv(p0, p1, p2);
        LabyDebugContext.glError("nglGetMultisamplefv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetMultisamplefv(int p0, int p1, FloatBuffer p2) {
        ARBTextureMultisample.glGetMultisamplefv(p0, p1, p2);
        LabyDebugContext.glError("glGetMultisamplefv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetMultisamplef(int p0, int p1) {
        float returnType = ARBTextureMultisample.glGetMultisamplef(p0, p1);
        LabyDebugContext.glError("glGetMultisamplef", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glSampleMaski(int p0, int p1) {
        ARBTextureMultisample.glSampleMaski(p0, p1);
        LabyDebugContext.glError("glSampleMaski", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glGetMultisamplefv(int p0, int p1, float[] p2) {
        ARBTextureMultisample.glGetMultisamplefv(p0, p1, p2);
        LabyDebugContext.glError("glGetMultisamplefv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

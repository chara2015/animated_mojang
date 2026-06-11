package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVTextureMultisample;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVTextureMultisampleErrorContext.class */
public final class NVTextureMultisampleErrorContext {
    public static void glTexImage2DMultisampleCoverageNV(int p0, int p1, int p2, int p3, int p4, int p5, boolean p6) {
        NVTextureMultisample.glTexImage2DMultisampleCoverageNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexImage2DMultisampleCoverageNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Boolean.valueOf(p6));
    }

    public static void glTexImage3DMultisampleCoverageNV(int p0, int p1, int p2, int p3, int p4, int p5, int p6, boolean p7) {
        NVTextureMultisample.glTexImage3DMultisampleCoverageNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage3DMultisampleCoverageNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Boolean.valueOf(p7));
    }

    public static void glTextureImage2DMultisampleNV(int p0, int p1, int p2, int p3, int p4, int p5, boolean p6) {
        NVTextureMultisample.glTextureImage2DMultisampleNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureImage2DMultisampleNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Boolean.valueOf(p6));
    }

    public static void glTextureImage3DMultisampleNV(int p0, int p1, int p2, int p3, int p4, int p5, int p6, boolean p7) {
        NVTextureMultisample.glTextureImage3DMultisampleNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTextureImage3DMultisampleNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Boolean.valueOf(p7));
    }

    public static void glTextureImage2DMultisampleCoverageNV(int p0, int p1, int p2, int p3, int p4, int p5, int p6, boolean p7) {
        NVTextureMultisample.glTextureImage2DMultisampleCoverageNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTextureImage2DMultisampleCoverageNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Boolean.valueOf(p7));
    }

    public static void glTextureImage3DMultisampleCoverageNV(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, boolean p8) {
        NVTextureMultisample.glTextureImage3DMultisampleCoverageNV(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureImage3DMultisampleCoverageNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Boolean.valueOf(p8));
    }
}

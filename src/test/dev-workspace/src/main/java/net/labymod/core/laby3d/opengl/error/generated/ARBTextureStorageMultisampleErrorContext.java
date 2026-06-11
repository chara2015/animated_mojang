package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBTextureStorageMultisample;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBTextureStorageMultisampleErrorContext.class */
public final class ARBTextureStorageMultisampleErrorContext {
    public static void glTexStorage2DMultisample(int p0, int p1, int p2, int p3, int p4, boolean p5) {
        ARBTextureStorageMultisample.glTexStorage2DMultisample(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTexStorage2DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Boolean.valueOf(p5));
    }

    public static void glTexStorage3DMultisample(int p0, int p1, int p2, int p3, int p4, int p5, boolean p6) {
        ARBTextureStorageMultisample.glTexStorage3DMultisample(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexStorage3DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Boolean.valueOf(p6));
    }

    public static void glTextureStorage2DMultisampleEXT(int p0, int p1, int p2, int p3, int p4, int p5, boolean p6) {
        ARBTextureStorageMultisample.glTextureStorage2DMultisampleEXT(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureStorage2DMultisampleEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Boolean.valueOf(p6));
    }

    public static void glTextureStorage3DMultisampleEXT(int p0, int p1, int p2, int p3, int p4, int p5, int p6, boolean p7) {
        ARBTextureStorageMultisample.glTextureStorage3DMultisampleEXT(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTextureStorage3DMultisampleEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Boolean.valueOf(p7));
    }
}

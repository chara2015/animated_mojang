package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBTextureStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBTextureStorageErrorContext.class */
public final class ARBTextureStorageErrorContext {
    public static void glTexStorage1D(int p0, int p1, int p2, int p3) {
        ARBTextureStorage.glTexStorage1D(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexStorage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glTexStorage2D(int p0, int p1, int p2, int p3, int p4) {
        ARBTextureStorage.glTexStorage2D(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTexStorage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glTexStorage3D(int p0, int p1, int p2, int p3, int p4, int p5) {
        ARBTextureStorage.glTexStorage3D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTexStorage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glTextureStorage1DEXT(int p0, int p1, int p2, int p3, int p4) {
        ARBTextureStorage.glTextureStorage1DEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTextureStorage1DEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glTextureStorage2DEXT(int p0, int p1, int p2, int p3, int p4, int p5) {
        ARBTextureStorage.glTextureStorage2DEXT(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTextureStorage2DEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glTextureStorage3DEXT(int p0, int p1, int p2, int p3, int p4, int p5, int p6) {
        ARBTextureStorage.glTextureStorage3DEXT(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureStorage3DEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }
}

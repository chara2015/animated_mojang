package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTTextureStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTTextureStorageErrorContext.class */
public final class EXTTextureStorageErrorContext {
    public static void glTexStorage1DEXT(int p0, int p1, int p2, int p3) {
        EXTTextureStorage.glTexStorage1DEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexStorage1DEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glTexStorage2DEXT(int p0, int p1, int p2, int p3, int p4) {
        EXTTextureStorage.glTexStorage2DEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTexStorage2DEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glTexStorage3DEXT(int p0, int p1, int p2, int p3, int p4, int p5) {
        EXTTextureStorage.glTexStorage3DEXT(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTexStorage3DEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glTextureStorage1DEXT(int p0, int p1, int p2, int p3, int p4) {
        EXTTextureStorage.glTextureStorage1DEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTextureStorage1DEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glTextureStorage2DEXT(int p0, int p1, int p2, int p3, int p4, int p5) {
        EXTTextureStorage.glTextureStorage2DEXT(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTextureStorage2DEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glTextureStorage3DEXT(int p0, int p1, int p2, int p3, int p4, int p5, int p6) {
        EXTTextureStorage.glTextureStorage3DEXT(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureStorage3DEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }
}

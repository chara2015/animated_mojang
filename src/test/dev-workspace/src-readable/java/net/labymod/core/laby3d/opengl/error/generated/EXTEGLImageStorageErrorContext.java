package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTEGLImageStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTEGLImageStorageErrorContext.class */
public final class EXTEGLImageStorageErrorContext {
    public static void nglEGLImageTargetTexStorageEXT(int p0, long p1, long p2) {
        EXTEGLImageStorage.nglEGLImageTargetTexStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("nglEGLImageTargetTexStorageEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glEGLImageTargetTexStorageEXT(int p0, long p1, IntBuffer p2) {
        EXTEGLImageStorage.glEGLImageTargetTexStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glEGLImageTargetTexStorageEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void nglEGLImageTargetTextureStorageEXT(int p0, long p1, long p2) {
        EXTEGLImageStorage.nglEGLImageTargetTextureStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("nglEGLImageTargetTextureStorageEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glEGLImageTargetTextureStorageEXT(int p0, long p1, IntBuffer p2) {
        EXTEGLImageStorage.glEGLImageTargetTextureStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glEGLImageTargetTextureStorageEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glEGLImageTargetTexStorageEXT(int p0, long p1, int[] p2) {
        EXTEGLImageStorage.glEGLImageTargetTexStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glEGLImageTargetTexStorageEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glEGLImageTargetTextureStorageEXT(int p0, long p1, int[] p2) {
        EXTEGLImageStorage.glEGLImageTargetTextureStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glEGLImageTargetTextureStorageEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }
}

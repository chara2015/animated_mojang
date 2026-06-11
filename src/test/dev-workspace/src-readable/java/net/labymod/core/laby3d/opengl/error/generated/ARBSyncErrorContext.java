package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBSync;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBSyncErrorContext.class */
public final class ARBSyncErrorContext {
    public static long glFenceSync(int p0, int p1) {
        long returnType = ARBSync.glFenceSync(p0, p1);
        LabyDebugContext.glError("glFenceSync", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static boolean nglIsSync(long p0) {
        boolean returnType = ARBSync.nglIsSync(p0);
        LabyDebugContext.glError("nglIsSync", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static boolean glIsSync(long p0) {
        boolean returnType = ARBSync.glIsSync(p0);
        LabyDebugContext.glError("glIsSync", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static void glDeleteSync(long p0) {
        ARBSync.glDeleteSync(p0);
        LabyDebugContext.glError("glDeleteSync", "p0", Long.valueOf(p0));
    }

    public static int nglClientWaitSync(long p0, int p1, long p2) {
        int returnType = ARBSync.nglClientWaitSync(p0, p1, p2);
        LabyDebugContext.glError("nglClientWaitSync", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glClientWaitSync(long p0, int p1, long p2) {
        int returnType = ARBSync.glClientWaitSync(p0, p1, p2);
        LabyDebugContext.glError("glClientWaitSync", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static void nglWaitSync(long p0, int p1, long p2) {
        ARBSync.nglWaitSync(p0, p1, p2);
        LabyDebugContext.glError("nglWaitSync", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glWaitSync(long p0, int p1, long p2) {
        ARBSync.glWaitSync(p0, p1, p2);
        LabyDebugContext.glError("glWaitSync", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void nglGetInteger64v(int p0, long p1) {
        ARBSync.nglGetInteger64v(p0, p1);
        LabyDebugContext.glError("nglGetInteger64v", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetInteger64v(int p0, LongBuffer p1) {
        ARBSync.glGetInteger64v(p0, p1);
        LabyDebugContext.glError("glGetInteger64v", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static long glGetInteger64(int p0) {
        long returnType = ARBSync.glGetInteger64(p0);
        LabyDebugContext.glError("glGetInteger64", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetSynciv(long p0, int p1, int p2, long p3, long p4) {
        ARBSync.nglGetSynciv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetSynciv", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetSynciv(long p0, int p1, IntBuffer p2, IntBuffer p3) {
        ARBSync.glGetSynciv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetSynciv", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static int glGetSynci(long p0, int p1, IntBuffer p2) {
        int returnType = ARBSync.glGetSynci(p0, p1, p2);
        LabyDebugContext.glError("glGetSynci", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static void glGetInteger64v(int p0, long[] p1) {
        ARBSync.glGetInteger64v(p0, p1);
        LabyDebugContext.glError("glGetInteger64v", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetSynciv(long p0, int p1, int[] p2, int[] p3) {
        ARBSync.glGetSynciv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetSynciv", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }
}

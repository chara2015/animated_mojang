package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLXSGIVideoSync;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXSGIVideoSyncErrorContext.class */
public final class GLXSGIVideoSyncErrorContext {
    public static int nglXGetVideoSyncSGI(long p0) {
        int returnType = GLXSGIVideoSync.nglXGetVideoSyncSGI(p0);
        LabyDebugContext.glError("nglXGetVideoSyncSGI", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int glXGetVideoSyncSGI(IntBuffer p0) {
        int returnType = GLXSGIVideoSync.glXGetVideoSyncSGI(p0);
        LabyDebugContext.glError("glXGetVideoSyncSGI", "p0", p0);
        return returnType;
    }

    public static int nglXWaitVideoSyncSGI(int p0, int p1, long p2) {
        int returnType = GLXSGIVideoSync.nglXWaitVideoSyncSGI(p0, p1, p2);
        LabyDebugContext.glError("nglXWaitVideoSyncSGI", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glXWaitVideoSyncSGI(int p0, int p1, IntBuffer p2) {
        int returnType = GLXSGIVideoSync.glXWaitVideoSyncSGI(p0, p1, p2);
        LabyDebugContext.glError("glXWaitVideoSyncSGI", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int glXGetVideoSyncSGI(int[] p0) {
        int returnType = GLXSGIVideoSync.glXGetVideoSyncSGI(p0);
        LabyDebugContext.glError("glXGetVideoSyncSGI", "p0", p0);
        return returnType;
    }

    public static int glXWaitVideoSyncSGI(int p0, int p1, int[] p2) {
        int returnType = GLXSGIVideoSync.glXWaitVideoSyncSGI(p0, p1, p2);
        LabyDebugContext.glError("glXWaitVideoSyncSGI", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }
}

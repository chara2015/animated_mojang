package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLXSGIXSwapBarrier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXSGIXSwapBarrierErrorContext.class */
public final class GLXSGIXSwapBarrierErrorContext {
    public static void glXBindSwapBarrierSGIX(long p0, long p1, int p2) {
        GLXSGIXSwapBarrier.glXBindSwapBarrierSGIX(p0, p1, p2);
        LabyDebugContext.glError("glXBindSwapBarrierSGIX", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static int nglXQueryMaxSwapBarriersSGIX(long p0, int p1, long p2) {
        int returnType = GLXSGIXSwapBarrier.nglXQueryMaxSwapBarriersSGIX(p0, p1, p2);
        LabyDebugContext.glError("nglXQueryMaxSwapBarriersSGIX", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean glXQueryMaxSwapBarriersSGIX(long p0, int p1, IntBuffer p2) {
        boolean returnType = GLXSGIXSwapBarrier.glXQueryMaxSwapBarriersSGIX(p0, p1, p2);
        LabyDebugContext.glError("glXQueryMaxSwapBarriersSGIX", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static boolean glXQueryMaxSwapBarriersSGIX(long p0, int p1, int[] p2) {
        boolean returnType = GLXSGIXSwapBarrier.glXQueryMaxSwapBarriersSGIX(p0, p1, p2);
        LabyDebugContext.glError("glXQueryMaxSwapBarriersSGIX", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }
}

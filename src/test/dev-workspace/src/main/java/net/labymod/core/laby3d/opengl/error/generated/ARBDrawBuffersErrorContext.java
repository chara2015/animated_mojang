package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBDrawBuffers;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBDrawBuffersErrorContext.class */
public final class ARBDrawBuffersErrorContext {
    public static void nglDrawBuffersARB(int p0, long p1) {
        ARBDrawBuffers.nglDrawBuffersARB(p0, p1);
        LabyDebugContext.glError("nglDrawBuffersARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDrawBuffersARB(IntBuffer p0) {
        ARBDrawBuffers.glDrawBuffersARB(p0);
        LabyDebugContext.glError("glDrawBuffersARB", "p0", p0);
    }

    public static void glDrawBuffersARB(int[] p0) {
        ARBDrawBuffers.glDrawBuffersARB(p0);
        LabyDebugContext.glError("glDrawBuffersARB", "p0", p0);
    }
}

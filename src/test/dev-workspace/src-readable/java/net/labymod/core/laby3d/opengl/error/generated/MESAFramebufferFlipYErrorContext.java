package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.MESAFramebufferFlipY;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/MESAFramebufferFlipYErrorContext.class */
public final class MESAFramebufferFlipYErrorContext {
    public static void glFramebufferParameteriMESA(int p0, int p1, int p2) {
        MESAFramebufferFlipY.glFramebufferParameteriMESA(p0, p1, p2);
        LabyDebugContext.glError("glFramebufferParameteriMESA", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglGetFramebufferParameterivMESA(int p0, int p1, long p2) {
        MESAFramebufferFlipY.nglGetFramebufferParameterivMESA(p0, p1, p2);
        LabyDebugContext.glError("nglGetFramebufferParameterivMESA", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetFramebufferParameterivMESA(int p0, int p1, IntBuffer p2) {
        MESAFramebufferFlipY.glGetFramebufferParameterivMESA(p0, p1, p2);
        LabyDebugContext.glError("glGetFramebufferParameterivMESA", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetFramebufferParameterivMESA(int p0, int p1, int[] p2) {
        MESAFramebufferFlipY.glGetFramebufferParameterivMESA(p0, p1, p2);
        LabyDebugContext.glError("glGetFramebufferParameterivMESA", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

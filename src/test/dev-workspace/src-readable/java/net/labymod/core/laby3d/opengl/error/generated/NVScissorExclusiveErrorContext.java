package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVScissorExclusive;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVScissorExclusiveErrorContext.class */
public final class NVScissorExclusiveErrorContext {
    public static void nglScissorExclusiveArrayvNV(int p0, int p1, long p2) {
        NVScissorExclusive.nglScissorExclusiveArrayvNV(p0, p1, p2);
        LabyDebugContext.glError("nglScissorExclusiveArrayvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glScissorExclusiveArrayvNV(int p0, IntBuffer p1) {
        NVScissorExclusive.glScissorExclusiveArrayvNV(p0, p1);
        LabyDebugContext.glError("glScissorExclusiveArrayvNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glScissorExclusiveNV(int p0, int p1, int p2, int p3) {
        NVScissorExclusive.glScissorExclusiveNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glScissorExclusiveNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glScissorExclusiveArrayvNV(int p0, int[] p1) {
        NVScissorExclusive.glScissorExclusiveArrayvNV(p0, p1);
        LabyDebugContext.glError("glScissorExclusiveArrayvNV", "p0", Integer.valueOf(p0), "p1", p1);
    }
}

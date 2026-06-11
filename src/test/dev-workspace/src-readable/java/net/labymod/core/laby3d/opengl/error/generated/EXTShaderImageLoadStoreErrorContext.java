package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTShaderImageLoadStore;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTShaderImageLoadStoreErrorContext.class */
public final class EXTShaderImageLoadStoreErrorContext {
    public static void glBindImageTextureEXT(int p0, int p1, int p2, boolean p3, int p4, int p5, int p6) {
        EXTShaderImageLoadStore.glBindImageTextureEXT(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glBindImageTextureEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glMemoryBarrierEXT(int p0) {
        EXTShaderImageLoadStore.glMemoryBarrierEXT(p0);
        LabyDebugContext.glError("glMemoryBarrierEXT", "p0", Integer.valueOf(p0));
    }
}

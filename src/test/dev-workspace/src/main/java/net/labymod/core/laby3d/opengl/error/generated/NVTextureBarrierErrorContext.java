package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVTextureBarrier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVTextureBarrierErrorContext.class */
public final class NVTextureBarrierErrorContext {
    public static void glTextureBarrierNV() {
        NVTextureBarrier.glTextureBarrierNV();
        LabyDebugContext.glError("glTextureBarrierNV", new Object[0]);
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTShaderFramebufferFetchNonCoherent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTShaderFramebufferFetchNonCoherentErrorContext.class */
public final class EXTShaderFramebufferFetchNonCoherentErrorContext {
    public static void glFramebufferFetchBarrierEXT() {
        EXTShaderFramebufferFetchNonCoherent.glFramebufferFetchBarrierEXT();
        LabyDebugContext.glError("glFramebufferFetchBarrierEXT", new Object[0]);
    }
}

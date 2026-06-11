package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.AMDVertexShaderTessellator;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/AMDVertexShaderTessellatorErrorContext.class */
public final class AMDVertexShaderTessellatorErrorContext {
    public static void glTessellationFactorAMD(float p0) {
        AMDVertexShaderTessellator.glTessellationFactorAMD(p0);
        LabyDebugContext.glError("glTessellationFactorAMD", "p0", Float.valueOf(p0));
    }

    public static void glTessellationModeAMD(int p0) {
        AMDVertexShaderTessellator.glTessellationModeAMD(p0);
        LabyDebugContext.glError("glTessellationModeAMD", "p0", Integer.valueOf(p0));
    }
}

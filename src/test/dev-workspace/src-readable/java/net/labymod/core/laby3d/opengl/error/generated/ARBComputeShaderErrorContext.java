package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBComputeShader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBComputeShaderErrorContext.class */
public final class ARBComputeShaderErrorContext {
    public static void glDispatchCompute(int p0, int p1, int p2) {
        ARBComputeShader.glDispatchCompute(p0, p1, p2);
        LabyDebugContext.glError("glDispatchCompute", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glDispatchComputeIndirect(long p0) {
        ARBComputeShader.glDispatchComputeIndirect(p0);
        LabyDebugContext.glError("glDispatchComputeIndirect", "p0", Long.valueOf(p0));
    }
}

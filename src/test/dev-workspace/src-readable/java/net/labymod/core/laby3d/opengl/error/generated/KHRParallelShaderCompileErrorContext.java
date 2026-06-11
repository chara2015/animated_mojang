package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.KHRParallelShaderCompile;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/KHRParallelShaderCompileErrorContext.class */
public final class KHRParallelShaderCompileErrorContext {
    public static void glMaxShaderCompilerThreadsKHR(int p0) {
        KHRParallelShaderCompile.glMaxShaderCompilerThreadsKHR(p0);
        LabyDebugContext.glError("glMaxShaderCompilerThreadsKHR", "p0", Integer.valueOf(p0));
    }
}

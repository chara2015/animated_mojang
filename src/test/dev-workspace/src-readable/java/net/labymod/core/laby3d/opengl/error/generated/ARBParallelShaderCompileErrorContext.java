package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBParallelShaderCompile;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBParallelShaderCompileErrorContext.class */
public final class ARBParallelShaderCompileErrorContext {
    public static void glMaxShaderCompilerThreadsARB(int p0) {
        ARBParallelShaderCompile.glMaxShaderCompilerThreadsARB(p0);
        LabyDebugContext.glError("glMaxShaderCompilerThreadsARB", "p0", Integer.valueOf(p0));
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTGPUProgramParameters;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTGPUProgramParametersErrorContext.class */
public final class EXTGPUProgramParametersErrorContext {
    public static void nglProgramEnvParameters4fvEXT(int p0, int p1, int p2, long p3) {
        EXTGPUProgramParameters.nglProgramEnvParameters4fvEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramEnvParameters4fvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramEnvParameters4fvEXT(int p0, int p1, FloatBuffer p2) {
        EXTGPUProgramParameters.glProgramEnvParameters4fvEXT(p0, p1, p2);
        LabyDebugContext.glError("glProgramEnvParameters4fvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramLocalParameters4fvEXT(int p0, int p1, int p2, long p3) {
        EXTGPUProgramParameters.nglProgramLocalParameters4fvEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramLocalParameters4fvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramLocalParameters4fvEXT(int p0, int p1, FloatBuffer p2) {
        EXTGPUProgramParameters.glProgramLocalParameters4fvEXT(p0, p1, p2);
        LabyDebugContext.glError("glProgramLocalParameters4fvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramEnvParameters4fvEXT(int p0, int p1, float[] p2) {
        EXTGPUProgramParameters.glProgramEnvParameters4fvEXT(p0, p1, p2);
        LabyDebugContext.glError("glProgramEnvParameters4fvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramLocalParameters4fvEXT(int p0, int p1, float[] p2) {
        EXTGPUProgramParameters.glProgramLocalParameters4fvEXT(p0, p1, p2);
        LabyDebugContext.glError("glProgramLocalParameters4fvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

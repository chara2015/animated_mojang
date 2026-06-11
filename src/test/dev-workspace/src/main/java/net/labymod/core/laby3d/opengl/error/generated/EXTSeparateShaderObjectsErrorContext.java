package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTSeparateShaderObjects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTSeparateShaderObjectsErrorContext.class */
public final class EXTSeparateShaderObjectsErrorContext {
    public static void glUseShaderProgramEXT(int p0, int p1) {
        EXTSeparateShaderObjects.glUseShaderProgramEXT(p0, p1);
        LabyDebugContext.glError("glUseShaderProgramEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glActiveProgramEXT(int p0) {
        EXTSeparateShaderObjects.glActiveProgramEXT(p0);
        LabyDebugContext.glError("glActiveProgramEXT", "p0", Integer.valueOf(p0));
    }

    public static int nglCreateShaderProgramEXT(int p0, long p1) {
        int returnType = EXTSeparateShaderObjects.nglCreateShaderProgramEXT(p0, p1);
        LabyDebugContext.glError("nglCreateShaderProgramEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glCreateShaderProgramEXT(int p0, ByteBuffer p1) {
        int returnType = EXTSeparateShaderObjects.glCreateShaderProgramEXT(p0, p1);
        LabyDebugContext.glError("glCreateShaderProgramEXT", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glCreateShaderProgramEXT(int p0, CharSequence p1) {
        int returnType = EXTSeparateShaderObjects.glCreateShaderProgramEXT(p0, p1);
        LabyDebugContext.glError("glCreateShaderProgramEXT", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }
}

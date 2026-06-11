package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTBindableUniform;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTBindableUniformErrorContext.class */
public final class EXTBindableUniformErrorContext {
    public static void glUniformBufferEXT(int p0, int p1, int p2) {
        EXTBindableUniform.glUniformBufferEXT(p0, p1, p2);
        LabyDebugContext.glError("glUniformBufferEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static int glGetUniformBufferSizeEXT(int p0, int p1) {
        int returnType = EXTBindableUniform.glGetUniformBufferSizeEXT(p0, p1);
        LabyDebugContext.glError("glGetUniformBufferSizeEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static long glGetUniformOffsetEXT(int p0, int p1) {
        long returnType = EXTBindableUniform.glGetUniformOffsetEXT(p0, p1);
        LabyDebugContext.glError("glGetUniformOffsetEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }
}

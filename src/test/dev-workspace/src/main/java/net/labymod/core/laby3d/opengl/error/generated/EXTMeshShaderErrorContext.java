package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTMeshShader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTMeshShaderErrorContext.class */
public final class EXTMeshShaderErrorContext {
    public static void glDrawMeshTasksEXT(int p0, int p1, int p2) {
        EXTMeshShader.glDrawMeshTasksEXT(p0, p1, p2);
        LabyDebugContext.glError("glDrawMeshTasksEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glDrawMeshTasksIndirectEXT(long p0) {
        EXTMeshShader.glDrawMeshTasksIndirectEXT(p0);
        LabyDebugContext.glError("glDrawMeshTasksIndirectEXT", "p0", Long.valueOf(p0));
    }

    public static void glMultiDrawMeshTasksIndirectEXT(long p0, int p1, int p2) {
        EXTMeshShader.glMultiDrawMeshTasksIndirectEXT(p0, p1, p2);
        LabyDebugContext.glError("glMultiDrawMeshTasksIndirectEXT", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glMultiDrawMeshTasksIndirectCountEXT(long p0, long p1, int p2, int p3) {
        EXTMeshShader.glMultiDrawMeshTasksIndirectCountEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glMultiDrawMeshTasksIndirectCountEXT", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }
}

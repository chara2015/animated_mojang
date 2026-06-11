package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVMeshShader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVMeshShaderErrorContext.class */
public final class NVMeshShaderErrorContext {
    public static void glDrawMeshTasksNV(int p0, int p1) {
        NVMeshShader.glDrawMeshTasksNV(p0, p1);
        LabyDebugContext.glError("glDrawMeshTasksNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glDrawMeshTasksIndirectNV(long p0) {
        NVMeshShader.glDrawMeshTasksIndirectNV(p0);
        LabyDebugContext.glError("glDrawMeshTasksIndirectNV", "p0", Long.valueOf(p0));
    }

    public static void glMultiDrawMeshTasksIndirectNV(long p0, int p1, int p2) {
        NVMeshShader.glMultiDrawMeshTasksIndirectNV(p0, p1, p2);
        LabyDebugContext.glError("glMultiDrawMeshTasksIndirectNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glMultiDrawMeshTasksIndirectCountNV(long p0, long p1, int p2, int p3) {
        NVMeshShader.glMultiDrawMeshTasksIndirectCountNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMultiDrawMeshTasksIndirectCountNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }
}

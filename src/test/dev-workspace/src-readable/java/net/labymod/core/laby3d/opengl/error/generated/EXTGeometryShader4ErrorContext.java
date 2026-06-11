package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTGeometryShader4;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTGeometryShader4ErrorContext.class */
public final class EXTGeometryShader4ErrorContext {
    public static void glProgramParameteriEXT(int p0, int p1, int p2) {
        EXTGeometryShader4.glProgramParameteriEXT(p0, p1, p2);
        LabyDebugContext.glError("glProgramParameteriEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glFramebufferTextureEXT(int p0, int p1, int p2, int p3) {
        EXTGeometryShader4.glFramebufferTextureEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glFramebufferTextureEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glFramebufferTextureLayerEXT(int p0, int p1, int p2, int p3, int p4) {
        EXTGeometryShader4.glFramebufferTextureLayerEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glFramebufferTextureLayerEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glFramebufferTextureFaceEXT(int p0, int p1, int p2, int p3, int p4) {
        EXTGeometryShader4.glFramebufferTextureFaceEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glFramebufferTextureFaceEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }
}

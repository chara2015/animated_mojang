package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBGeometryShader4;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBGeometryShader4ErrorContext.class */
public final class ARBGeometryShader4ErrorContext {
    public static void glProgramParameteriARB(int p0, int p1, int p2) {
        ARBGeometryShader4.glProgramParameteriARB(p0, p1, p2);
        LabyDebugContext.glError("glProgramParameteriARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glFramebufferTextureARB(int p0, int p1, int p2, int p3) {
        ARBGeometryShader4.glFramebufferTextureARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glFramebufferTextureARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glFramebufferTextureLayerARB(int p0, int p1, int p2, int p3, int p4) {
        ARBGeometryShader4.glFramebufferTextureLayerARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glFramebufferTextureLayerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glFramebufferTextureFaceARB(int p0, int p1, int p2, int p3, int p4) {
        ARBGeometryShader4.glFramebufferTextureFaceARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glFramebufferTextureFaceARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }
}

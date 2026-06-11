package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBInstancedArrays;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBInstancedArraysErrorContext.class */
public final class ARBInstancedArraysErrorContext {
    public static void glVertexAttribDivisorARB(int p0, int p1) {
        ARBInstancedArrays.glVertexAttribDivisorARB(p0, p1);
        LabyDebugContext.glError("glVertexAttribDivisorARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexArrayVertexAttribDivisorEXT(int p0, int p1, int p2) {
        ARBInstancedArrays.glVertexArrayVertexAttribDivisorEXT(p0, p1, p2);
        LabyDebugContext.glError("glVertexArrayVertexAttribDivisorEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }
}

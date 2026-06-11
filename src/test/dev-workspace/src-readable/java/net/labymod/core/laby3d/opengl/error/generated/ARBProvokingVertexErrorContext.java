package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBProvokingVertex;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBProvokingVertexErrorContext.class */
public final class ARBProvokingVertexErrorContext {
    public static void glProvokingVertex(int p0) {
        ARBProvokingVertex.glProvokingVertex(p0);
        LabyDebugContext.glError("glProvokingVertex", "p0", Integer.valueOf(p0));
    }
}

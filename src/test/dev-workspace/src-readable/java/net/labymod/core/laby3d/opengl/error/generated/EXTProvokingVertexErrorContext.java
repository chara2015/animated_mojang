package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTProvokingVertex;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTProvokingVertexErrorContext.class */
public final class EXTProvokingVertexErrorContext {
    public static void glProvokingVertexEXT(int p0) {
        EXTProvokingVertex.glProvokingVertexEXT(p0);
        LabyDebugContext.glError("glProvokingVertexEXT", "p0", Integer.valueOf(p0));
    }
}

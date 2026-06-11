package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVVertexArrayRange;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVVertexArrayRangeErrorContext.class */
public final class NVVertexArrayRangeErrorContext {
    public static void nglVertexArrayRangeNV(int p0, long p1) {
        NVVertexArrayRange.nglVertexArrayRangeNV(p0, p1);
        LabyDebugContext.glError("nglVertexArrayRangeNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexArrayRangeNV(ByteBuffer p0) {
        NVVertexArrayRange.glVertexArrayRangeNV(p0);
        LabyDebugContext.glError("glVertexArrayRangeNV", "p0", p0);
    }

    public static void glFlushVertexArrayRangeNV() {
        NVVertexArrayRange.glFlushVertexArrayRangeNV();
        LabyDebugContext.glError("glFlushVertexArrayRangeNV", new Object[0]);
    }
}

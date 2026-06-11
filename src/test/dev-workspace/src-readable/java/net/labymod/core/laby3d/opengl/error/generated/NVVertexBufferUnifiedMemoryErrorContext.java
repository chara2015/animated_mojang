package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVVertexBufferUnifiedMemory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVVertexBufferUnifiedMemoryErrorContext.class */
public final class NVVertexBufferUnifiedMemoryErrorContext {
    public static void glBufferAddressRangeNV(int p0, int p1, long p2, long p3) {
        NVVertexBufferUnifiedMemory.glBufferAddressRangeNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glBufferAddressRangeNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glVertexFormatNV(int p0, int p1, int p2) {
        NVVertexBufferUnifiedMemory.glVertexFormatNV(p0, p1, p2);
        LabyDebugContext.glError("glVertexFormatNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glNormalFormatNV(int p0, int p1) {
        NVVertexBufferUnifiedMemory.glNormalFormatNV(p0, p1);
        LabyDebugContext.glError("glNormalFormatNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glColorFormatNV(int p0, int p1, int p2) {
        NVVertexBufferUnifiedMemory.glColorFormatNV(p0, p1, p2);
        LabyDebugContext.glError("glColorFormatNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glIndexFormatNV(int p0, int p1) {
        NVVertexBufferUnifiedMemory.glIndexFormatNV(p0, p1);
        LabyDebugContext.glError("glIndexFormatNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glTexCoordFormatNV(int p0, int p1, int p2) {
        NVVertexBufferUnifiedMemory.glTexCoordFormatNV(p0, p1, p2);
        LabyDebugContext.glError("glTexCoordFormatNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glEdgeFlagFormatNV(int p0) {
        NVVertexBufferUnifiedMemory.glEdgeFlagFormatNV(p0);
        LabyDebugContext.glError("glEdgeFlagFormatNV", "p0", Integer.valueOf(p0));
    }

    public static void glSecondaryColorFormatNV(int p0, int p1, int p2) {
        NVVertexBufferUnifiedMemory.glSecondaryColorFormatNV(p0, p1, p2);
        LabyDebugContext.glError("glSecondaryColorFormatNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glFogCoordFormatNV(int p0, int p1) {
        NVVertexBufferUnifiedMemory.glFogCoordFormatNV(p0, p1);
        LabyDebugContext.glError("glFogCoordFormatNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexAttribFormatNV(int p0, int p1, int p2, boolean p3, int p4) {
        NVVertexBufferUnifiedMemory.glVertexAttribFormatNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribFormatNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glVertexAttribIFormatNV(int p0, int p1, int p2, int p3) {
        NVVertexBufferUnifiedMemory.glVertexAttribIFormatNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribIFormatNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglGetIntegerui64i_vNV(int p0, int p1, long p2) {
        NVVertexBufferUnifiedMemory.nglGetIntegerui64i_vNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetIntegerui64i_vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetIntegerui64i_vNV(int p0, int p1, LongBuffer p2) {
        NVVertexBufferUnifiedMemory.glGetIntegerui64i_vNV(p0, p1, p2);
        LabyDebugContext.glError("glGetIntegerui64i_vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetIntegerui64iNV(int p0, int p1) {
        long returnType = NVVertexBufferUnifiedMemory.glGetIntegerui64iNV(p0, p1);
        LabyDebugContext.glError("glGetIntegerui64iNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glGetIntegerui64i_vNV(int p0, int p1, long[] p2) {
        NVVertexBufferUnifiedMemory.glGetIntegerui64i_vNV(p0, p1, p2);
        LabyDebugContext.glError("glGetIntegerui64i_vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

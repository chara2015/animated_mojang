package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVMemoryObjectSparse;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVMemoryObjectSparseErrorContext.class */
public final class NVMemoryObjectSparseErrorContext {
    public static void glBufferPageCommitmentMemNV(int p0, long p1, long p2, int p3, long p4, boolean p5) {
        NVMemoryObjectSparse.glBufferPageCommitmentMemNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glBufferPageCommitmentMemNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Boolean.valueOf(p5));
    }

    public static void glNamedBufferPageCommitmentMemNV(int p0, long p1, long p2, int p3, long p4, boolean p5) {
        NVMemoryObjectSparse.glNamedBufferPageCommitmentMemNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glNamedBufferPageCommitmentMemNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Boolean.valueOf(p5));
    }

    public static void glTexPageCommitmentMemNV(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10, boolean p11) {
        NVMemoryObjectSparse.glTexPageCommitmentMemNV(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
        LabyDebugContext.glError("glTexPageCommitmentMemNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10), "p11", Boolean.valueOf(p11));
    }

    public static void glTexturePageCommitmentMemNV(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10, boolean p11) {
        NVMemoryObjectSparse.glTexturePageCommitmentMemNV(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
        LabyDebugContext.glError("glTexturePageCommitmentMemNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10), "p11", Boolean.valueOf(p11));
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBSparseBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBSparseBufferErrorContext.class */
public final class ARBSparseBufferErrorContext {
    public static void glBufferPageCommitmentARB(int p0, long p1, long p2, boolean p3) {
        ARBSparseBuffer.glBufferPageCommitmentARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glBufferPageCommitmentARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Boolean.valueOf(p3));
    }

    public static void glNamedBufferPageCommitmentEXT(int p0, long p1, long p2, boolean p3) {
        ARBSparseBuffer.glNamedBufferPageCommitmentEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glNamedBufferPageCommitmentEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Boolean.valueOf(p3));
    }

    public static void glNamedBufferPageCommitmentARB(int p0, long p1, long p2, boolean p3) {
        ARBSparseBuffer.glNamedBufferPageCommitmentARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glNamedBufferPageCommitmentARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Boolean.valueOf(p3));
    }
}

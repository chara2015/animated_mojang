package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBCopyBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBCopyBufferErrorContext.class */
public final class ARBCopyBufferErrorContext {
    public static void glCopyBufferSubData(int p0, int p1, long p2, long p3, long p4) {
        ARBCopyBuffer.glCopyBufferSubData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glCopyBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBMapBufferRange;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBMapBufferRangeErrorContext.class */
public final class ARBMapBufferRangeErrorContext {
    public static long nglMapBufferRange(int p0, long p1, long p2, int p3) {
        long returnType = ARBMapBufferRange.nglMapBufferRange(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMapBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static ByteBuffer glMapBufferRange(int p0, long p1, long p2, int p3) {
        ByteBuffer returnType = ARBMapBufferRange.glMapBufferRange(p0, p1, p2, p3);
        LabyDebugContext.glError("glMapBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static ByteBuffer glMapBufferRange(int p0, long p1, long p2, int p3, ByteBuffer p4) {
        ByteBuffer returnType = ARBMapBufferRange.glMapBufferRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMapBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static void glFlushMappedBufferRange(int p0, long p1, long p2) {
        ARBMapBufferRange.glFlushMappedBufferRange(p0, p1, p2);
        LabyDebugContext.glError("glFlushMappedBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }
}

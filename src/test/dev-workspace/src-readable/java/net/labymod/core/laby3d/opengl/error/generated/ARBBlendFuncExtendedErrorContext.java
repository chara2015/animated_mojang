package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBBlendFuncExtended;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBBlendFuncExtendedErrorContext.class */
public final class ARBBlendFuncExtendedErrorContext {
    public static void nglBindFragDataLocationIndexed(int p0, int p1, int p2, long p3) {
        ARBBlendFuncExtended.nglBindFragDataLocationIndexed(p0, p1, p2, p3);
        LabyDebugContext.glError("nglBindFragDataLocationIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glBindFragDataLocationIndexed(int p0, int p1, int p2, ByteBuffer p3) {
        ARBBlendFuncExtended.glBindFragDataLocationIndexed(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindFragDataLocationIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glBindFragDataLocationIndexed(int p0, int p1, int p2, CharSequence p3) {
        ARBBlendFuncExtended.glBindFragDataLocationIndexed(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindFragDataLocationIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int nglGetFragDataIndex(int p0, long p1) {
        int returnType = ARBBlendFuncExtended.nglGetFragDataIndex(p0, p1);
        LabyDebugContext.glError("nglGetFragDataIndex", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glGetFragDataIndex(int p0, ByteBuffer p1) {
        int returnType = ARBBlendFuncExtended.glGetFragDataIndex(p0, p1);
        LabyDebugContext.glError("glGetFragDataIndex", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glGetFragDataIndex(int p0, CharSequence p1) {
        int returnType = ARBBlendFuncExtended.glGetFragDataIndex(p0, p1);
        LabyDebugContext.glError("glGetFragDataIndex", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }
}

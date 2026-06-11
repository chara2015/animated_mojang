package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTTimerQuery;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTTimerQueryErrorContext.class */
public final class EXTTimerQueryErrorContext {
    public static void nglGetQueryObjecti64vEXT(int p0, int p1, long p2) {
        EXTTimerQuery.nglGetQueryObjecti64vEXT(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjecti64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjecti64vEXT(int p0, int p1, LongBuffer p2) {
        EXTTimerQuery.glGetQueryObjecti64vEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjecti64vEXT(int p0, int p1, long p2) {
        EXTTimerQuery.glGetQueryObjecti64vEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static long glGetQueryObjecti64EXT(int p0, int p1) {
        long returnType = EXTTimerQuery.glGetQueryObjecti64EXT(p0, p1);
        LabyDebugContext.glError("glGetQueryObjecti64EXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetQueryObjectui64vEXT(int p0, int p1, long p2) {
        EXTTimerQuery.nglGetQueryObjectui64vEXT(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjectui64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjectui64vEXT(int p0, int p1, LongBuffer p2) {
        EXTTimerQuery.glGetQueryObjectui64vEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectui64vEXT(int p0, int p1, long p2) {
        EXTTimerQuery.glGetQueryObjectui64vEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static long glGetQueryObjectui64EXT(int p0, int p1) {
        long returnType = EXTTimerQuery.glGetQueryObjectui64EXT(p0, p1);
        LabyDebugContext.glError("glGetQueryObjectui64EXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glGetQueryObjecti64vEXT(int p0, int p1, long[] p2) {
        EXTTimerQuery.glGetQueryObjecti64vEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectui64vEXT(int p0, int p1, long[] p2) {
        EXTTimerQuery.glGetQueryObjectui64vEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64vEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBTimerQuery;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBTimerQueryErrorContext.class */
public final class ARBTimerQueryErrorContext {
    public static void glQueryCounter(int p0, int p1) {
        ARBTimerQuery.glQueryCounter(p0, p1);
        LabyDebugContext.glError("glQueryCounter", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglGetQueryObjecti64v(int p0, int p1, long p2) {
        ARBTimerQuery.nglGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjecti64v(int p0, int p1, LongBuffer p2) {
        ARBTimerQuery.glGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjecti64v(int p0, int p1, long p2) {
        ARBTimerQuery.glGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static long glGetQueryObjecti64(int p0, int p1) {
        long returnType = ARBTimerQuery.glGetQueryObjecti64(p0, p1);
        LabyDebugContext.glError("glGetQueryObjecti64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetQueryObjectui64v(int p0, int p1, long p2) {
        ARBTimerQuery.nglGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjectui64v(int p0, int p1, LongBuffer p2) {
        ARBTimerQuery.glGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectui64v(int p0, int p1, long p2) {
        ARBTimerQuery.glGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static long glGetQueryObjectui64(int p0, int p1) {
        long returnType = ARBTimerQuery.glGetQueryObjectui64(p0, p1);
        LabyDebugContext.glError("glGetQueryObjectui64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glGetQueryObjecti64v(int p0, int p1, long[] p2) {
        ARBTimerQuery.glGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectui64v(int p0, int p1, long[] p2) {
        ARBTimerQuery.glGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

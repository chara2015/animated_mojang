package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBInternalformatQuery2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBInternalformatQuery2ErrorContext.class */
public final class ARBInternalformatQuery2ErrorContext {
    public static void nglGetInternalformati64v(int p0, int p1, int p2, int p3, long p4) {
        ARBInternalformatQuery2.nglGetInternalformati64v(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetInternalformati64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetInternalformati64v(int p0, int p1, int p2, LongBuffer p3) {
        ARBInternalformatQuery2.glGetInternalformati64v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetInternalformati64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static long glGetInternalformati64(int p0, int p1, int p2) {
        long returnType = ARBInternalformatQuery2.glGetInternalformati64(p0, p1, p2);
        LabyDebugContext.glError("glGetInternalformati64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void glGetInternalformati64v(int p0, int p1, int p2, long[] p3) {
        ARBInternalformatQuery2.glGetInternalformati64v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetInternalformati64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBGLSPIRV;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBGLSPIRVErrorContext.class */
public final class ARBGLSPIRVErrorContext {
    public static void nglSpecializeShaderARB(int p0, long p1, int p2, long p3, long p4) {
        ARBGLSPIRV.nglSpecializeShaderARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglSpecializeShaderARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glSpecializeShaderARB(int p0, ByteBuffer p1, IntBuffer p2, IntBuffer p3) {
        ARBGLSPIRV.glSpecializeShaderARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glSpecializeShaderARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void glSpecializeShaderARB(int p0, CharSequence p1, IntBuffer p2, IntBuffer p3) {
        ARBGLSPIRV.glSpecializeShaderARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glSpecializeShaderARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void glSpecializeShaderARB(int p0, ByteBuffer p1, int[] p2, int[] p3) {
        ARBGLSPIRV.glSpecializeShaderARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glSpecializeShaderARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void glSpecializeShaderARB(int p0, CharSequence p1, int[] p2, int[] p3) {
        ARBGLSPIRV.glSpecializeShaderARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glSpecializeShaderARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }
}

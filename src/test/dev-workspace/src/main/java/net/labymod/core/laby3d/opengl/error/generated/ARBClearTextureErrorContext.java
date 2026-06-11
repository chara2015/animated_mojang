package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBClearTexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBClearTextureErrorContext.class */
public final class ARBClearTextureErrorContext {
    public static void nglClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        ARBClearTexture.nglClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("nglClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, ByteBuffer p10) {
        ARBClearTexture.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, ShortBuffer p10) {
        ARBClearTexture.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, IntBuffer p10) {
        ARBClearTexture.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, FloatBuffer p10) {
        ARBClearTexture.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, DoubleBuffer p10) {
        ARBClearTexture.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void nglClearTexImage(int p0, int p1, int p2, int p3, long p4) {
        ARBClearTexture.nglClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        ARBClearTexture.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        ARBClearTexture.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, IntBuffer p4) {
        ARBClearTexture.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        ARBClearTexture.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, DoubleBuffer p4) {
        ARBClearTexture.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, short[] p10) {
        ARBClearTexture.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int[] p10) {
        ARBClearTexture.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, float[] p10) {
        ARBClearTexture.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, double[] p10) {
        ARBClearTexture.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, short[] p4) {
        ARBClearTexture.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, int[] p4) {
        ARBClearTexture.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, float[] p4) {
        ARBClearTexture.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, double[] p4) {
        ARBClearTexture.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }
}

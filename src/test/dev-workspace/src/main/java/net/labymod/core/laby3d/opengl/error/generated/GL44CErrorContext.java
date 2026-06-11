package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL44C;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL44CErrorContext.class */
public final class GL44CErrorContext {
    public static void nglBufferStorage(int p0, long p1, long p2, int p3) {
        GL44C.nglBufferStorage(p0, p1, p2, p3);
        LabyDebugContext.glError("nglBufferStorage", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glBufferStorage(int p0, long p1, int p2) {
        GL44C.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, ByteBuffer p1, int p2) {
        GL44C.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, ShortBuffer p1, int p2) {
        GL44C.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, IntBuffer p1, int p2) {
        GL44C.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, FloatBuffer p1, int p2) {
        GL44C.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, DoubleBuffer p1, int p2) {
        GL44C.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        GL44C.nglClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("nglClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, ByteBuffer p10) {
        GL44C.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, ShortBuffer p10) {
        GL44C.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, IntBuffer p10) {
        GL44C.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, FloatBuffer p10) {
        GL44C.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, DoubleBuffer p10) {
        GL44C.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void nglClearTexImage(int p0, int p1, int p2, int p3, long p4) {
        GL44C.nglClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        GL44C.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        GL44C.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, IntBuffer p4) {
        GL44C.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        GL44C.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, DoubleBuffer p4) {
        GL44C.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglBindBuffersBase(int p0, int p1, int p2, long p3) {
        GL44C.nglBindBuffersBase(p0, p1, p2, p3);
        LabyDebugContext.glError("nglBindBuffersBase", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glBindBuffersBase(int p0, int p1, IntBuffer p2) {
        GL44C.glBindBuffersBase(p0, p1, p2);
        LabyDebugContext.glError("glBindBuffersBase", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglBindBuffersRange(int p0, int p1, int p2, long p3, long p4, long p5) {
        GL44C.nglBindBuffersRange(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglBindBuffersRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glBindBuffersRange(int p0, int p1, IntBuffer p2, PointerBuffer p3, PointerBuffer p4) {
        GL44C.glBindBuffersRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glBindBuffersRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4);
    }

    public static void nglBindTextures(int p0, int p1, long p2) {
        GL44C.nglBindTextures(p0, p1, p2);
        LabyDebugContext.glError("nglBindTextures", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glBindTextures(int p0, IntBuffer p1) {
        GL44C.glBindTextures(p0, p1);
        LabyDebugContext.glError("glBindTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglBindSamplers(int p0, int p1, long p2) {
        GL44C.nglBindSamplers(p0, p1, p2);
        LabyDebugContext.glError("nglBindSamplers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glBindSamplers(int p0, IntBuffer p1) {
        GL44C.glBindSamplers(p0, p1);
        LabyDebugContext.glError("glBindSamplers", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglBindImageTextures(int p0, int p1, long p2) {
        GL44C.nglBindImageTextures(p0, p1, p2);
        LabyDebugContext.glError("nglBindImageTextures", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glBindImageTextures(int p0, IntBuffer p1) {
        GL44C.glBindImageTextures(p0, p1);
        LabyDebugContext.glError("glBindImageTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglBindVertexBuffers(int p0, int p1, long p2, long p3, long p4) {
        GL44C.nglBindVertexBuffers(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglBindVertexBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glBindVertexBuffers(int p0, IntBuffer p1, PointerBuffer p2, IntBuffer p3) {
        GL44C.glBindVertexBuffers(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindVertexBuffers", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void glBufferStorage(int p0, short[] p1, int p2) {
        GL44C.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, int[] p1, int p2) {
        GL44C.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, float[] p1, int p2) {
        GL44C.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, double[] p1, int p2) {
        GL44C.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, short[] p10) {
        GL44C.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int[] p10) {
        GL44C.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, float[] p10) {
        GL44C.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, double[] p10) {
        GL44C.glClearTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glClearTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, short[] p4) {
        GL44C.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, int[] p4) {
        GL44C.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, float[] p4) {
        GL44C.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearTexImage(int p0, int p1, int p2, int p3, double[] p4) {
        GL44C.glClearTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glBindBuffersBase(int p0, int p1, int[] p2) {
        GL44C.glBindBuffersBase(p0, p1, p2);
        LabyDebugContext.glError("glBindBuffersBase", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glBindBuffersRange(int p0, int p1, int[] p2, PointerBuffer p3, PointerBuffer p4) {
        GL44C.glBindBuffersRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glBindBuffersRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4);
    }

    public static void glBindTextures(int p0, int[] p1) {
        GL44C.glBindTextures(p0, p1);
        LabyDebugContext.glError("glBindTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glBindSamplers(int p0, int[] p1) {
        GL44C.glBindSamplers(p0, p1);
        LabyDebugContext.glError("glBindSamplers", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glBindImageTextures(int p0, int[] p1) {
        GL44C.glBindImageTextures(p0, p1);
        LabyDebugContext.glError("glBindImageTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glBindVertexBuffers(int p0, int[] p1, PointerBuffer p2, int[] p3) {
        GL44C.glBindVertexBuffers(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindVertexBuffers", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }
}

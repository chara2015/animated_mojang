package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBVertexBlend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBVertexBlendErrorContext.class */
public final class ARBVertexBlendErrorContext {
    public static void nglWeightfvARB(int p0, long p1) {
        ARBVertexBlend.nglWeightfvARB(p0, p1);
        LabyDebugContext.glError("nglWeightfvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glWeightfvARB(FloatBuffer p0) {
        ARBVertexBlend.glWeightfvARB(p0);
        LabyDebugContext.glError("glWeightfvARB", "p0", p0);
    }

    public static void nglWeightbvARB(int p0, long p1) {
        ARBVertexBlend.nglWeightbvARB(p0, p1);
        LabyDebugContext.glError("nglWeightbvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glWeightbvARB(ByteBuffer p0) {
        ARBVertexBlend.glWeightbvARB(p0);
        LabyDebugContext.glError("glWeightbvARB", "p0", p0);
    }

    public static void nglWeightubvARB(int p0, long p1) {
        ARBVertexBlend.nglWeightubvARB(p0, p1);
        LabyDebugContext.glError("nglWeightubvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glWeightubvARB(ByteBuffer p0) {
        ARBVertexBlend.glWeightubvARB(p0);
        LabyDebugContext.glError("glWeightubvARB", "p0", p0);
    }

    public static void nglWeightsvARB(int p0, long p1) {
        ARBVertexBlend.nglWeightsvARB(p0, p1);
        LabyDebugContext.glError("nglWeightsvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glWeightsvARB(ShortBuffer p0) {
        ARBVertexBlend.glWeightsvARB(p0);
        LabyDebugContext.glError("glWeightsvARB", "p0", p0);
    }

    public static void nglWeightusvARB(int p0, long p1) {
        ARBVertexBlend.nglWeightusvARB(p0, p1);
        LabyDebugContext.glError("nglWeightusvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glWeightusvARB(ShortBuffer p0) {
        ARBVertexBlend.glWeightusvARB(p0);
        LabyDebugContext.glError("glWeightusvARB", "p0", p0);
    }

    public static void nglWeightivARB(int p0, long p1) {
        ARBVertexBlend.nglWeightivARB(p0, p1);
        LabyDebugContext.glError("nglWeightivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glWeightivARB(IntBuffer p0) {
        ARBVertexBlend.glWeightivARB(p0);
        LabyDebugContext.glError("glWeightivARB", "p0", p0);
    }

    public static void nglWeightuivARB(int p0, long p1) {
        ARBVertexBlend.nglWeightuivARB(p0, p1);
        LabyDebugContext.glError("nglWeightuivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glWeightuivARB(IntBuffer p0) {
        ARBVertexBlend.glWeightuivARB(p0);
        LabyDebugContext.glError("glWeightuivARB", "p0", p0);
    }

    public static void nglWeightdvARB(int p0, long p1) {
        ARBVertexBlend.nglWeightdvARB(p0, p1);
        LabyDebugContext.glError("nglWeightdvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glWeightdvARB(DoubleBuffer p0) {
        ARBVertexBlend.glWeightdvARB(p0);
        LabyDebugContext.glError("glWeightdvARB", "p0", p0);
    }

    public static void nglWeightPointerARB(int p0, int p1, int p2, long p3) {
        ARBVertexBlend.nglWeightPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglWeightPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glWeightPointerARB(int p0, int p1, int p2, ByteBuffer p3) {
        ARBVertexBlend.glWeightPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glWeightPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glWeightPointerARB(int p0, int p1, int p2, long p3) {
        ARBVertexBlend.glWeightPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glWeightPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glWeightPointerARB(int p0, int p1, int p2, ShortBuffer p3) {
        ARBVertexBlend.glWeightPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glWeightPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glWeightPointerARB(int p0, int p1, int p2, IntBuffer p3) {
        ARBVertexBlend.glWeightPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glWeightPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glWeightPointerARB(int p0, int p1, int p2, FloatBuffer p3) {
        ARBVertexBlend.glWeightPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glWeightPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glVertexBlendARB(int p0) {
        ARBVertexBlend.glVertexBlendARB(p0);
        LabyDebugContext.glError("glVertexBlendARB", "p0", Integer.valueOf(p0));
    }

    public static void glWeightfvARB(float[] p0) {
        ARBVertexBlend.glWeightfvARB(p0);
        LabyDebugContext.glError("glWeightfvARB", "p0", p0);
    }

    public static void glWeightsvARB(short[] p0) {
        ARBVertexBlend.glWeightsvARB(p0);
        LabyDebugContext.glError("glWeightsvARB", "p0", p0);
    }

    public static void glWeightusvARB(short[] p0) {
        ARBVertexBlend.glWeightusvARB(p0);
        LabyDebugContext.glError("glWeightusvARB", "p0", p0);
    }

    public static void glWeightivARB(int[] p0) {
        ARBVertexBlend.glWeightivARB(p0);
        LabyDebugContext.glError("glWeightivARB", "p0", p0);
    }

    public static void glWeightuivARB(int[] p0) {
        ARBVertexBlend.glWeightuivARB(p0);
        LabyDebugContext.glError("glWeightuivARB", "p0", p0);
    }

    public static void glWeightdvARB(double[] p0) {
        ARBVertexBlend.glWeightdvARB(p0);
        LabyDebugContext.glError("glWeightdvARB", "p0", p0);
    }

    public static void glWeightPointerARB(int p0, int p1, int p2, short[] p3) {
        ARBVertexBlend.glWeightPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glWeightPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glWeightPointerARB(int p0, int p1, int p2, int[] p3) {
        ARBVertexBlend.glWeightPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glWeightPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glWeightPointerARB(int p0, int p1, int p2, float[] p3) {
        ARBVertexBlend.glWeightPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glWeightPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }
}

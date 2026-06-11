package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBMultitexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBMultitextureErrorContext.class */
public final class ARBMultitextureErrorContext {
    public static void glActiveTextureARB(int p0) {
        ARBMultitexture.glActiveTextureARB(p0);
        LabyDebugContext.glError("glActiveTextureARB", "p0", Integer.valueOf(p0));
    }

    public static void glClientActiveTextureARB(int p0) {
        ARBMultitexture.glClientActiveTextureARB(p0);
        LabyDebugContext.glError("glClientActiveTextureARB", "p0", Integer.valueOf(p0));
    }

    public static void glMultiTexCoord1fARB(int p0, float p1) {
        ARBMultitexture.glMultiTexCoord1fARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glMultiTexCoord1sARB(int p0, short p1) {
        ARBMultitexture.glMultiTexCoord1sARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1sARB", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1));
    }

    public static void glMultiTexCoord1iARB(int p0, int p1) {
        ARBMultitexture.glMultiTexCoord1iARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1iARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glMultiTexCoord1dARB(int p0, double p1) {
        ARBMultitexture.glMultiTexCoord1dARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1dARB", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void nglMultiTexCoord1fvARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord1fvARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord1fvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord1fvARB(int p0, FloatBuffer p1) {
        ARBMultitexture.glMultiTexCoord1fvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord1svARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord1svARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord1svARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord1svARB(int p0, ShortBuffer p1) {
        ARBMultitexture.glMultiTexCoord1svARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord1ivARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord1ivARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord1ivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord1ivARB(int p0, IntBuffer p1) {
        ARBMultitexture.glMultiTexCoord1ivARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord1dvARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord1dvARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord1dvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord1dvARB(int p0, DoubleBuffer p1) {
        ARBMultitexture.glMultiTexCoord1dvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord2fARB(int p0, float p1, float p2) {
        ARBMultitexture.glMultiTexCoord2fARB(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoord2fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glMultiTexCoord2sARB(int p0, short p1, short p2) {
        ARBMultitexture.glMultiTexCoord2sARB(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoord2sARB", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2));
    }

    public static void glMultiTexCoord2iARB(int p0, int p1, int p2) {
        ARBMultitexture.glMultiTexCoord2iARB(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoord2iARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glMultiTexCoord2dARB(int p0, double p1, double p2) {
        ARBMultitexture.glMultiTexCoord2dARB(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoord2dARB", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void nglMultiTexCoord2fvARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord2fvARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord2fvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord2fvARB(int p0, FloatBuffer p1) {
        ARBMultitexture.glMultiTexCoord2fvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord2fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord2svARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord2svARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord2svARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord2svARB(int p0, ShortBuffer p1) {
        ARBMultitexture.glMultiTexCoord2svARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord2svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord2ivARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord2ivARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord2ivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord2ivARB(int p0, IntBuffer p1) {
        ARBMultitexture.glMultiTexCoord2ivARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord2ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord2dvARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord2dvARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord2dvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord2dvARB(int p0, DoubleBuffer p1) {
        ARBMultitexture.glMultiTexCoord2dvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord2dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord3fARB(int p0, float p1, float p2, float p3) {
        ARBMultitexture.glMultiTexCoord3fARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glMultiTexCoord3fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glMultiTexCoord3sARB(int p0, short p1, short p2, short p3) {
        ARBMultitexture.glMultiTexCoord3sARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glMultiTexCoord3sARB", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3));
    }

    public static void glMultiTexCoord3iARB(int p0, int p1, int p2, int p3) {
        ARBMultitexture.glMultiTexCoord3iARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glMultiTexCoord3iARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glMultiTexCoord3dARB(int p0, double p1, double p2, double p3) {
        ARBMultitexture.glMultiTexCoord3dARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glMultiTexCoord3dARB", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void nglMultiTexCoord3fvARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord3fvARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord3fvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord3fvARB(int p0, FloatBuffer p1) {
        ARBMultitexture.glMultiTexCoord3fvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord3fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord3svARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord3svARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord3svARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord3svARB(int p0, ShortBuffer p1) {
        ARBMultitexture.glMultiTexCoord3svARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord3svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord3ivARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord3ivARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord3ivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord3ivARB(int p0, IntBuffer p1) {
        ARBMultitexture.glMultiTexCoord3ivARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord3ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord3dvARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord3dvARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord3dvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord3dvARB(int p0, DoubleBuffer p1) {
        ARBMultitexture.glMultiTexCoord3dvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord3dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord4fARB(int p0, float p1, float p2, float p3, float p4) {
        ARBMultitexture.glMultiTexCoord4fARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiTexCoord4fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4));
    }

    public static void glMultiTexCoord4sARB(int p0, short p1, short p2, short p3, short p4) {
        ARBMultitexture.glMultiTexCoord4sARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiTexCoord4sARB", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3), "p4", Short.valueOf(p4));
    }

    public static void glMultiTexCoord4iARB(int p0, int p1, int p2, int p3, int p4) {
        ARBMultitexture.glMultiTexCoord4iARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiTexCoord4iARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glMultiTexCoord4dARB(int p0, double p1, double p2, double p3, double p4) {
        ARBMultitexture.glMultiTexCoord4dARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiTexCoord4dARB", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4));
    }

    public static void nglMultiTexCoord4fvARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord4fvARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord4fvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord4fvARB(int p0, FloatBuffer p1) {
        ARBMultitexture.glMultiTexCoord4fvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord4fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord4svARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord4svARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord4svARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord4svARB(int p0, ShortBuffer p1) {
        ARBMultitexture.glMultiTexCoord4svARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord4svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord4ivARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord4ivARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord4ivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord4ivARB(int p0, IntBuffer p1) {
        ARBMultitexture.glMultiTexCoord4ivARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord4ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMultiTexCoord4dvARB(int p0, long p1) {
        ARBMultitexture.nglMultiTexCoord4dvARB(p0, p1);
        LabyDebugContext.glError("nglMultiTexCoord4dvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMultiTexCoord4dvARB(int p0, DoubleBuffer p1) {
        ARBMultitexture.glMultiTexCoord4dvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord4dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord1fvARB(int p0, float[] p1) {
        ARBMultitexture.glMultiTexCoord1fvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord1svARB(int p0, short[] p1) {
        ARBMultitexture.glMultiTexCoord1svARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord1ivARB(int p0, int[] p1) {
        ARBMultitexture.glMultiTexCoord1ivARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord1dvARB(int p0, double[] p1) {
        ARBMultitexture.glMultiTexCoord1dvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord1dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord2fvARB(int p0, float[] p1) {
        ARBMultitexture.glMultiTexCoord2fvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord2fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord2svARB(int p0, short[] p1) {
        ARBMultitexture.glMultiTexCoord2svARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord2svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord2ivARB(int p0, int[] p1) {
        ARBMultitexture.glMultiTexCoord2ivARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord2ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord2dvARB(int p0, double[] p1) {
        ARBMultitexture.glMultiTexCoord2dvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord2dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord3fvARB(int p0, float[] p1) {
        ARBMultitexture.glMultiTexCoord3fvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord3fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord3svARB(int p0, short[] p1) {
        ARBMultitexture.glMultiTexCoord3svARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord3svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord3ivARB(int p0, int[] p1) {
        ARBMultitexture.glMultiTexCoord3ivARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord3ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord3dvARB(int p0, double[] p1) {
        ARBMultitexture.glMultiTexCoord3dvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord3dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord4fvARB(int p0, float[] p1) {
        ARBMultitexture.glMultiTexCoord4fvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord4fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord4svARB(int p0, short[] p1) {
        ARBMultitexture.glMultiTexCoord4svARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord4svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord4ivARB(int p0, int[] p1) {
        ARBMultitexture.glMultiTexCoord4ivARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord4ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoord4dvARB(int p0, double[] p1) {
        ARBMultitexture.glMultiTexCoord4dvARB(p0, p1);
        LabyDebugContext.glError("glMultiTexCoord4dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }
}

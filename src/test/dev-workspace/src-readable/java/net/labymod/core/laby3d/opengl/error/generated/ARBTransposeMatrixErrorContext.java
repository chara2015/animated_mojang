package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBTransposeMatrix;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBTransposeMatrixErrorContext.class */
public final class ARBTransposeMatrixErrorContext {
    public static void nglLoadTransposeMatrixfARB(long p0) {
        ARBTransposeMatrix.nglLoadTransposeMatrixfARB(p0);
        LabyDebugContext.glError("nglLoadTransposeMatrixfARB", "p0", Long.valueOf(p0));
    }

    public static void glLoadTransposeMatrixfARB(FloatBuffer p0) {
        ARBTransposeMatrix.glLoadTransposeMatrixfARB(p0);
        LabyDebugContext.glError("glLoadTransposeMatrixfARB", "p0", p0);
    }

    public static void nglLoadTransposeMatrixdARB(long p0) {
        ARBTransposeMatrix.nglLoadTransposeMatrixdARB(p0);
        LabyDebugContext.glError("nglLoadTransposeMatrixdARB", "p0", Long.valueOf(p0));
    }

    public static void glLoadTransposeMatrixdARB(DoubleBuffer p0) {
        ARBTransposeMatrix.glLoadTransposeMatrixdARB(p0);
        LabyDebugContext.glError("glLoadTransposeMatrixdARB", "p0", p0);
    }

    public static void nglMultTransposeMatrixfARB(long p0) {
        ARBTransposeMatrix.nglMultTransposeMatrixfARB(p0);
        LabyDebugContext.glError("nglMultTransposeMatrixfARB", "p0", Long.valueOf(p0));
    }

    public static void glMultTransposeMatrixfARB(FloatBuffer p0) {
        ARBTransposeMatrix.glMultTransposeMatrixfARB(p0);
        LabyDebugContext.glError("glMultTransposeMatrixfARB", "p0", p0);
    }

    public static void nglMultTransposeMatrixdARB(long p0) {
        ARBTransposeMatrix.nglMultTransposeMatrixdARB(p0);
        LabyDebugContext.glError("nglMultTransposeMatrixdARB", "p0", Long.valueOf(p0));
    }

    public static void glMultTransposeMatrixdARB(DoubleBuffer p0) {
        ARBTransposeMatrix.glMultTransposeMatrixdARB(p0);
        LabyDebugContext.glError("glMultTransposeMatrixdARB", "p0", p0);
    }

    public static void glLoadTransposeMatrixfARB(float[] p0) {
        ARBTransposeMatrix.glLoadTransposeMatrixfARB(p0);
        LabyDebugContext.glError("glLoadTransposeMatrixfARB", "p0", p0);
    }

    public static void glLoadTransposeMatrixdARB(double[] p0) {
        ARBTransposeMatrix.glLoadTransposeMatrixdARB(p0);
        LabyDebugContext.glError("glLoadTransposeMatrixdARB", "p0", p0);
    }

    public static void glMultTransposeMatrixfARB(float[] p0) {
        ARBTransposeMatrix.glMultTransposeMatrixfARB(p0);
        LabyDebugContext.glError("glMultTransposeMatrixfARB", "p0", p0);
    }

    public static void glMultTransposeMatrixdARB(double[] p0) {
        ARBTransposeMatrix.glMultTransposeMatrixdARB(p0);
        LabyDebugContext.glError("glMultTransposeMatrixdARB", "p0", p0);
    }
}

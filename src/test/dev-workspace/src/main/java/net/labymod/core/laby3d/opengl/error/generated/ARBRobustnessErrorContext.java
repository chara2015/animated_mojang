package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBRobustness;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBRobustnessErrorContext.class */
public final class ARBRobustnessErrorContext {
    public static int glGetGraphicsResetStatusARB() {
        int returnType = ARBRobustness.glGetGraphicsResetStatusARB();
        LabyDebugContext.glError("glGetGraphicsResetStatusARB", new Object[0]);
        return returnType;
    }

    public static void nglGetnMapdvARB(int p0, int p1, int p2, long p3) {
        ARBRobustness.nglGetnMapdvARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnMapdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnMapdvARB(int p0, int p1, DoubleBuffer p2) {
        ARBRobustness.glGetnMapdvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnMapdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static double glGetnMapdARB(int p0, int p1) {
        double returnType = ARBRobustness.glGetnMapdARB(p0, p1);
        LabyDebugContext.glError("glGetnMapdARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetnMapfvARB(int p0, int p1, int p2, long p3) {
        ARBRobustness.nglGetnMapfvARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnMapfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnMapfvARB(int p0, int p1, FloatBuffer p2) {
        ARBRobustness.glGetnMapfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnMapfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetnMapfARB(int p0, int p1) {
        float returnType = ARBRobustness.glGetnMapfARB(p0, p1);
        LabyDebugContext.glError("glGetnMapfARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetnMapivARB(int p0, int p1, int p2, long p3) {
        ARBRobustness.nglGetnMapivARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnMapivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnMapivARB(int p0, int p1, IntBuffer p2) {
        ARBRobustness.glGetnMapivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnMapivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetnMapiARB(int p0, int p1) {
        int returnType = ARBRobustness.glGetnMapiARB(p0, p1);
        LabyDebugContext.glError("glGetnMapiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetnPixelMapfvARB(int p0, int p1, long p2) {
        ARBRobustness.nglGetnPixelMapfvARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetnPixelMapfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetnPixelMapfvARB(int p0, FloatBuffer p1) {
        ARBRobustness.glGetnPixelMapfvARB(p0, p1);
        LabyDebugContext.glError("glGetnPixelMapfvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglGetnPixelMapuivARB(int p0, int p1, long p2) {
        ARBRobustness.nglGetnPixelMapuivARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetnPixelMapuivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetnPixelMapuivARB(int p0, IntBuffer p1) {
        ARBRobustness.glGetnPixelMapuivARB(p0, p1);
        LabyDebugContext.glError("glGetnPixelMapuivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglGetnPixelMapusvARB(int p0, int p1, long p2) {
        ARBRobustness.nglGetnPixelMapusvARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetnPixelMapusvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetnPixelMapusvARB(int p0, ShortBuffer p1) {
        ARBRobustness.glGetnPixelMapusvARB(p0, p1);
        LabyDebugContext.glError("glGetnPixelMapusvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglGetnPolygonStippleARB(int p0, long p1) {
        ARBRobustness.nglGetnPolygonStippleARB(p0, p1);
        LabyDebugContext.glError("nglGetnPolygonStippleARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetnPolygonStippleARB(int p0, long p1) {
        ARBRobustness.glGetnPolygonStippleARB(p0, p1);
        LabyDebugContext.glError("glGetnPolygonStippleARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetnPolygonStippleARB(ByteBuffer p0) {
        ARBRobustness.glGetnPolygonStippleARB(p0);
        LabyDebugContext.glError("glGetnPolygonStippleARB", "p0", p0);
    }

    public static void nglGetnTexImageARB(int p0, int p1, int p2, int p3, int p4, long p5) {
        ARBRobustness.nglGetnTexImageARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetnTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetnTexImageARB(int p0, int p1, int p2, int p3, int p4, long p5) {
        ARBRobustness.glGetnTexImageARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetnTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetnTexImageARB(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        ARBRobustness.glGetnTexImageARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImageARB(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        ARBRobustness.glGetnTexImageARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImageARB(int p0, int p1, int p2, int p3, IntBuffer p4) {
        ARBRobustness.glGetnTexImageARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImageARB(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        ARBRobustness.glGetnTexImageARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImageARB(int p0, int p1, int p2, int p3, DoubleBuffer p4) {
        ARBRobustness.glGetnTexImageARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglReadnPixelsARB(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        ARBRobustness.nglReadnPixelsARB(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglReadnPixelsARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glReadnPixelsARB(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        ARBRobustness.glReadnPixelsARB(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glReadnPixelsARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glReadnPixelsARB(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6) {
        ARBRobustness.glReadnPixelsARB(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixelsARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixelsARB(int p0, int p1, int p2, int p3, int p4, int p5, ShortBuffer p6) {
        ARBRobustness.glReadnPixelsARB(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixelsARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixelsARB(int p0, int p1, int p2, int p3, int p4, int p5, IntBuffer p6) {
        ARBRobustness.glReadnPixelsARB(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixelsARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixelsARB(int p0, int p1, int p2, int p3, int p4, int p5, FloatBuffer p6) {
        ARBRobustness.glReadnPixelsARB(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixelsARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void nglGetnColorTableARB(int p0, int p1, int p2, int p3, long p4) {
        ARBRobustness.nglGetnColorTableARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetnColorTableARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetnColorTableARB(int p0, int p1, int p2, int p3, long p4) {
        ARBRobustness.glGetnColorTableARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnColorTableARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetnColorTableARB(int p0, int p1, int p2, ByteBuffer p3) {
        ARBRobustness.glGetnColorTableARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetnColorTableARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetnColorTableARB(int p0, int p1, int p2, ShortBuffer p3) {
        ARBRobustness.glGetnColorTableARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetnColorTableARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetnColorTableARB(int p0, int p1, int p2, IntBuffer p3) {
        ARBRobustness.glGetnColorTableARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetnColorTableARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetnColorTableARB(int p0, int p1, int p2, FloatBuffer p3) {
        ARBRobustness.glGetnColorTableARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetnColorTableARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglGetnConvolutionFilterARB(int p0, int p1, int p2, int p3, long p4) {
        ARBRobustness.nglGetnConvolutionFilterARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetnConvolutionFilterARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetnConvolutionFilterARB(int p0, int p1, int p2, int p3, long p4) {
        ARBRobustness.glGetnConvolutionFilterARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnConvolutionFilterARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetnConvolutionFilterARB(int p0, int p1, int p2, ByteBuffer p3) {
        ARBRobustness.glGetnConvolutionFilterARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetnConvolutionFilterARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglGetnSeparableFilterARB(int p0, int p1, int p2, int p3, long p4, int p5, long p6, long p7) {
        ARBRobustness.nglGetnSeparableFilterARB(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglGetnSeparableFilterARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glGetnSeparableFilterARB(int p0, int p1, int p2, int p3, long p4, int p5, long p6, ByteBuffer p7) {
        ARBRobustness.glGetnSeparableFilterARB(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glGetnSeparableFilterARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6), "p7", p7);
    }

    public static void glGetnSeparableFilterARB(int p0, int p1, int p2, ByteBuffer p3, ByteBuffer p4, ByteBuffer p5) {
        ARBRobustness.glGetnSeparableFilterARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetnSeparableFilterARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4, "p5", p5);
    }

    public static void nglGetnHistogramARB(int p0, boolean p1, int p2, int p3, int p4, long p5) {
        ARBRobustness.nglGetnHistogramARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetnHistogramARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetnHistogramARB(int p0, boolean p1, int p2, int p3, int p4, long p5) {
        ARBRobustness.glGetnHistogramARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetnHistogramARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetnHistogramARB(int p0, boolean p1, int p2, int p3, ByteBuffer p4) {
        ARBRobustness.glGetnHistogramARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnHistogramARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglGetnMinmaxARB(int p0, boolean p1, int p2, int p3, int p4, long p5) {
        ARBRobustness.nglGetnMinmaxARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetnMinmaxARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetnMinmaxARB(int p0, boolean p1, int p2, int p3, int p4, long p5) {
        ARBRobustness.glGetnMinmaxARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetnMinmaxARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetnMinmaxARB(int p0, boolean p1, int p2, int p3, ByteBuffer p4) {
        ARBRobustness.glGetnMinmaxARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnMinmaxARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglGetnCompressedTexImageARB(int p0, int p1, int p2, long p3) {
        ARBRobustness.nglGetnCompressedTexImageARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnCompressedTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnCompressedTexImageARB(int p0, int p1, int p2, long p3) {
        ARBRobustness.glGetnCompressedTexImageARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetnCompressedTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnCompressedTexImageARB(int p0, int p1, ByteBuffer p2) {
        ARBRobustness.glGetnCompressedTexImageARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnCompressedTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetnUniformfvARB(int p0, int p1, int p2, long p3) {
        ARBRobustness.nglGetnUniformfvARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnUniformfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnUniformfvARB(int p0, int p1, FloatBuffer p2) {
        ARBRobustness.glGetnUniformfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetnUniformfARB(int p0, int p1) {
        float returnType = ARBRobustness.glGetnUniformfARB(p0, p1);
        LabyDebugContext.glError("glGetnUniformfARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetnUniformivARB(int p0, int p1, int p2, long p3) {
        ARBRobustness.nglGetnUniformivARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnUniformivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnUniformivARB(int p0, int p1, IntBuffer p2) {
        ARBRobustness.glGetnUniformivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetnUniformiARB(int p0, int p1) {
        int returnType = ARBRobustness.glGetnUniformiARB(p0, p1);
        LabyDebugContext.glError("glGetnUniformiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetnUniformuivARB(int p0, int p1, int p2, long p3) {
        ARBRobustness.nglGetnUniformuivARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnUniformuivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnUniformuivARB(int p0, int p1, IntBuffer p2) {
        ARBRobustness.glGetnUniformuivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformuivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetnUniformuiARB(int p0, int p1) {
        int returnType = ARBRobustness.glGetnUniformuiARB(p0, p1);
        LabyDebugContext.glError("glGetnUniformuiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetnUniformdvARB(int p0, int p1, int p2, long p3) {
        ARBRobustness.nglGetnUniformdvARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnUniformdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnUniformdvARB(int p0, int p1, DoubleBuffer p2) {
        ARBRobustness.glGetnUniformdvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static double glGetnUniformdARB(int p0, int p1) {
        double returnType = ARBRobustness.glGetnUniformdARB(p0, p1);
        LabyDebugContext.glError("glGetnUniformdARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glGetnMapdvARB(int p0, int p1, double[] p2) {
        ARBRobustness.glGetnMapdvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnMapdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetnMapfvARB(int p0, int p1, float[] p2) {
        ARBRobustness.glGetnMapfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnMapfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetnMapivARB(int p0, int p1, int[] p2) {
        ARBRobustness.glGetnMapivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnMapivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetnPixelMapfvARB(int p0, float[] p1) {
        ARBRobustness.glGetnPixelMapfvARB(p0, p1);
        LabyDebugContext.glError("glGetnPixelMapfvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetnPixelMapuivARB(int p0, int[] p1) {
        ARBRobustness.glGetnPixelMapuivARB(p0, p1);
        LabyDebugContext.glError("glGetnPixelMapuivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetnPixelMapusvARB(int p0, short[] p1) {
        ARBRobustness.glGetnPixelMapusvARB(p0, p1);
        LabyDebugContext.glError("glGetnPixelMapusvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetnTexImageARB(int p0, int p1, int p2, int p3, short[] p4) {
        ARBRobustness.glGetnTexImageARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImageARB(int p0, int p1, int p2, int p3, int[] p4) {
        ARBRobustness.glGetnTexImageARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImageARB(int p0, int p1, int p2, int p3, float[] p4) {
        ARBRobustness.glGetnTexImageARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImageARB(int p0, int p1, int p2, int p3, double[] p4) {
        ARBRobustness.glGetnTexImageARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImageARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glReadnPixelsARB(int p0, int p1, int p2, int p3, int p4, int p5, short[] p6) {
        ARBRobustness.glReadnPixelsARB(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixelsARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixelsARB(int p0, int p1, int p2, int p3, int p4, int p5, int[] p6) {
        ARBRobustness.glReadnPixelsARB(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixelsARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixelsARB(int p0, int p1, int p2, int p3, int p4, int p5, float[] p6) {
        ARBRobustness.glReadnPixelsARB(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixelsARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glGetnColorTableARB(int p0, int p1, int p2, short[] p3) {
        ARBRobustness.glGetnColorTableARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetnColorTableARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetnColorTableARB(int p0, int p1, int p2, int[] p3) {
        ARBRobustness.glGetnColorTableARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetnColorTableARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetnColorTableARB(int p0, int p1, int p2, float[] p3) {
        ARBRobustness.glGetnColorTableARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetnColorTableARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetnUniformfvARB(int p0, int p1, float[] p2) {
        ARBRobustness.glGetnUniformfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetnUniformivARB(int p0, int p1, int[] p2) {
        ARBRobustness.glGetnUniformivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetnUniformuivARB(int p0, int p1, int[] p2) {
        ARBRobustness.glGetnUniformuivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformuivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetnUniformdvARB(int p0, int p1, double[] p2) {
        ARBRobustness.glGetnUniformdvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

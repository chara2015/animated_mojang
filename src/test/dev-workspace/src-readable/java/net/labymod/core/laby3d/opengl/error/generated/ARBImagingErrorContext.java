package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBImaging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBImagingErrorContext.class */
public final class ARBImagingErrorContext {
    public static void nglColorTable(int p0, int p1, int p2, int p3, int p4, long p5) {
        ARBImaging.nglColorTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glColorTable(int p0, int p1, int p2, int p3, int p4, ByteBuffer p5) {
        ARBImaging.glColorTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glColorTable(int p0, int p1, int p2, int p3, int p4, long p5) {
        ARBImaging.glColorTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glColorTable(int p0, int p1, int p2, int p3, int p4, ShortBuffer p5) {
        ARBImaging.glColorTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glColorTable(int p0, int p1, int p2, int p3, int p4, IntBuffer p5) {
        ARBImaging.glColorTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glColorTable(int p0, int p1, int p2, int p3, int p4, FloatBuffer p5) {
        ARBImaging.glColorTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glCopyColorTable(int p0, int p1, int p2, int p3, int p4) {
        ARBImaging.glCopyColorTable(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glCopyColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglColorTableParameteriv(int p0, int p1, long p2) {
        ARBImaging.nglColorTableParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglColorTableParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glColorTableParameteriv(int p0, int p1, IntBuffer p2) {
        ARBImaging.glColorTableParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glColorTableParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglColorTableParameterfv(int p0, int p1, long p2) {
        ARBImaging.nglColorTableParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglColorTableParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glColorTableParameterfv(int p0, int p1, FloatBuffer p2) {
        ARBImaging.glColorTableParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glColorTableParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetColorTable(int p0, int p1, int p2, long p3) {
        ARBImaging.nglGetColorTable(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetColorTable(int p0, int p1, int p2, ByteBuffer p3) {
        ARBImaging.glGetColorTable(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetColorTable(int p0, int p1, int p2, long p3) {
        ARBImaging.glGetColorTable(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetColorTable(int p0, int p1, int p2, ShortBuffer p3) {
        ARBImaging.glGetColorTable(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetColorTable(int p0, int p1, int p2, IntBuffer p3) {
        ARBImaging.glGetColorTable(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetColorTable(int p0, int p1, int p2, FloatBuffer p3) {
        ARBImaging.glGetColorTable(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglGetColorTableParameteriv(int p0, int p1, long p2) {
        ARBImaging.nglGetColorTableParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetColorTableParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetColorTableParameteriv(int p0, int p1, IntBuffer p2) {
        ARBImaging.glGetColorTableParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetColorTableParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetColorTableParameteri(int p0, int p1) {
        int returnType = ARBImaging.glGetColorTableParameteri(p0, p1);
        LabyDebugContext.glError("glGetColorTableParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetColorTableParameterfv(int p0, int p1, long p2) {
        ARBImaging.nglGetColorTableParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetColorTableParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetColorTableParameterfv(int p0, int p1, FloatBuffer p2) {
        ARBImaging.glGetColorTableParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetColorTableParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetColorTableParameterf(int p0, int p1) {
        float returnType = ARBImaging.glGetColorTableParameterf(p0, p1);
        LabyDebugContext.glError("glGetColorTableParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglColorSubTable(int p0, int p1, int p2, int p3, int p4, long p5) {
        ARBImaging.nglColorSubTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglColorSubTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glColorSubTable(int p0, int p1, int p2, int p3, int p4, ByteBuffer p5) {
        ARBImaging.glColorSubTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glColorSubTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glColorSubTable(int p0, int p1, int p2, int p3, int p4, long p5) {
        ARBImaging.glColorSubTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glColorSubTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glCopyColorSubTable(int p0, int p1, int p2, int p3, int p4) {
        ARBImaging.glCopyColorSubTable(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glCopyColorSubTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglConvolutionFilter1D(int p0, int p1, int p2, int p3, int p4, long p5) {
        ARBImaging.nglConvolutionFilter1D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglConvolutionFilter1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glConvolutionFilter1D(int p0, int p1, int p2, int p3, int p4, ByteBuffer p5) {
        ARBImaging.glConvolutionFilter1D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glConvolutionFilter1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glConvolutionFilter1D(int p0, int p1, int p2, int p3, int p4, long p5) {
        ARBImaging.glConvolutionFilter1D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glConvolutionFilter1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void nglConvolutionFilter2D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        ARBImaging.nglConvolutionFilter2D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglConvolutionFilter2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glConvolutionFilter2D(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6) {
        ARBImaging.glConvolutionFilter2D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glConvolutionFilter2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glConvolutionFilter2D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        ARBImaging.glConvolutionFilter2D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glConvolutionFilter2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glCopyConvolutionFilter1D(int p0, int p1, int p2, int p3, int p4) {
        ARBImaging.glCopyConvolutionFilter1D(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glCopyConvolutionFilter1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glCopyConvolutionFilter2D(int p0, int p1, int p2, int p3, int p4, int p5) {
        ARBImaging.glCopyConvolutionFilter2D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCopyConvolutionFilter2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void nglGetConvolutionFilter(int p0, int p1, int p2, long p3) {
        ARBImaging.nglGetConvolutionFilter(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetConvolutionFilter", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetConvolutionFilter(int p0, int p1, int p2, ByteBuffer p3) {
        ARBImaging.glGetConvolutionFilter(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetConvolutionFilter", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetConvolutionFilter(int p0, int p1, int p2, long p3) {
        ARBImaging.glGetConvolutionFilter(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetConvolutionFilter", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void nglSeparableFilter2D(int p0, int p1, int p2, int p3, int p4, int p5, long p6, long p7) {
        ARBImaging.nglSeparableFilter2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglSeparableFilter2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glSeparableFilter2D(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6, ByteBuffer p7) {
        ARBImaging.glSeparableFilter2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glSeparableFilter2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6, "p7", p7);
    }

    public static void glSeparableFilter2D(int p0, int p1, int p2, int p3, int p4, int p5, long p6, long p7) {
        ARBImaging.glSeparableFilter2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glSeparableFilter2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void nglGetSeparableFilter(int p0, int p1, int p2, long p3, long p4, long p5) {
        ARBImaging.nglGetSeparableFilter(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetSeparableFilter", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetSeparableFilter(int p0, int p1, int p2, ByteBuffer p3, ByteBuffer p4, ByteBuffer p5) {
        ARBImaging.glGetSeparableFilter(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetSeparableFilter", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4, "p5", p5);
    }

    public static void glGetSeparableFilter(int p0, int p1, int p2, long p3, long p4, ByteBuffer p5) {
        ARBImaging.glGetSeparableFilter(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetSeparableFilter", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", p5);
    }

    public static void glConvolutionParameteri(int p0, int p1, int p2) {
        ARBImaging.glConvolutionParameteri(p0, p1, p2);
        LabyDebugContext.glError("glConvolutionParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglConvolutionParameteriv(int p0, int p1, long p2) {
        ARBImaging.nglConvolutionParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglConvolutionParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glConvolutionParameteriv(int p0, int p1, IntBuffer p2) {
        ARBImaging.glConvolutionParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glConvolutionParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glConvolutionParameterf(int p0, int p1, float p2) {
        ARBImaging.glConvolutionParameterf(p0, p1, p2);
        LabyDebugContext.glError("glConvolutionParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglConvolutionParameterfv(int p0, int p1, long p2) {
        ARBImaging.nglConvolutionParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglConvolutionParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glConvolutionParameterfv(int p0, int p1, FloatBuffer p2) {
        ARBImaging.glConvolutionParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glConvolutionParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetConvolutionParameteriv(int p0, int p1, long p2) {
        ARBImaging.nglGetConvolutionParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetConvolutionParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetConvolutionParameteriv(int p0, int p1, IntBuffer p2) {
        ARBImaging.glGetConvolutionParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetConvolutionParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetConvolutionParameteri(int p0, int p1) {
        int returnType = ARBImaging.glGetConvolutionParameteri(p0, p1);
        LabyDebugContext.glError("glGetConvolutionParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetConvolutionParameterfv(int p0, int p1, long p2) {
        ARBImaging.nglGetConvolutionParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetConvolutionParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetConvolutionParameterfv(int p0, int p1, FloatBuffer p2) {
        ARBImaging.glGetConvolutionParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetConvolutionParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetConvolutionParameterf(int p0, int p1) {
        float returnType = ARBImaging.glGetConvolutionParameterf(p0, p1);
        LabyDebugContext.glError("glGetConvolutionParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glHistogram(int p0, int p1, int p2, boolean p3) {
        ARBImaging.glHistogram(p0, p1, p2, p3);
        LabyDebugContext.glError("glHistogram", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3));
    }

    public static void glResetHistogram(int p0) {
        ARBImaging.glResetHistogram(p0);
        LabyDebugContext.glError("glResetHistogram", "p0", Integer.valueOf(p0));
    }

    public static void nglGetHistogram(int p0, boolean p1, int p2, int p3, long p4) {
        ARBImaging.nglGetHistogram(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetHistogram", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetHistogram(int p0, boolean p1, int p2, int p3, ByteBuffer p4) {
        ARBImaging.glGetHistogram(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetHistogram", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetHistogram(int p0, boolean p1, int p2, int p3, long p4) {
        ARBImaging.glGetHistogram(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetHistogram", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void nglGetHistogramParameteriv(int p0, int p1, long p2) {
        ARBImaging.nglGetHistogramParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetHistogramParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetHistogramParameteriv(int p0, int p1, IntBuffer p2) {
        ARBImaging.glGetHistogramParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetHistogramParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetHistogramParameteri(int p0, int p1) {
        int returnType = ARBImaging.glGetHistogramParameteri(p0, p1);
        LabyDebugContext.glError("glGetHistogramParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetHistogramParameterfv(int p0, int p1, long p2) {
        ARBImaging.nglGetHistogramParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetHistogramParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetHistogramParameterfv(int p0, int p1, FloatBuffer p2) {
        ARBImaging.glGetHistogramParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetHistogramParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetHistogramParameterf(int p0, int p1) {
        float returnType = ARBImaging.glGetHistogramParameterf(p0, p1);
        LabyDebugContext.glError("glGetHistogramParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glMinmax(int p0, int p1, boolean p2) {
        ARBImaging.glMinmax(p0, p1, p2);
        LabyDebugContext.glError("glMinmax", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2));
    }

    public static void glResetMinmax(int p0) {
        ARBImaging.glResetMinmax(p0);
        LabyDebugContext.glError("glResetMinmax", "p0", Integer.valueOf(p0));
    }

    public static void nglGetMinmax(int p0, boolean p1, int p2, int p3, long p4) {
        ARBImaging.nglGetMinmax(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetMinmax", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetMinmax(int p0, boolean p1, int p2, int p3, ByteBuffer p4) {
        ARBImaging.glGetMinmax(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetMinmax", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetMinmax(int p0, boolean p1, int p2, int p3, long p4) {
        ARBImaging.glGetMinmax(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetMinmax", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void nglGetMinmaxParameteriv(int p0, int p1, long p2) {
        ARBImaging.nglGetMinmaxParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetMinmaxParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetMinmaxParameteriv(int p0, int p1, IntBuffer p2) {
        ARBImaging.glGetMinmaxParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetMinmaxParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetMinmaxParameteri(int p0, int p1) {
        int returnType = ARBImaging.glGetMinmaxParameteri(p0, p1);
        LabyDebugContext.glError("glGetMinmaxParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetMinmaxParameterfv(int p0, int p1, long p2) {
        ARBImaging.nglGetMinmaxParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetMinmaxParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetMinmaxParameterfv(int p0, int p1, FloatBuffer p2) {
        ARBImaging.glGetMinmaxParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetMinmaxParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetMinmaxParameterf(int p0, int p1) {
        float returnType = ARBImaging.glGetMinmaxParameterf(p0, p1);
        LabyDebugContext.glError("glGetMinmaxParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glBlendColor(float p0, float p1, float p2, float p3) {
        ARBImaging.glBlendColor(p0, p1, p2, p3);
        LabyDebugContext.glError("glBlendColor", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glBlendEquation(int p0) {
        ARBImaging.glBlendEquation(p0);
        LabyDebugContext.glError("glBlendEquation", "p0", Integer.valueOf(p0));
    }

    public static void glColorTable(int p0, int p1, int p2, int p3, int p4, short[] p5) {
        ARBImaging.glColorTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glColorTable(int p0, int p1, int p2, int p3, int p4, int[] p5) {
        ARBImaging.glColorTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glColorTable(int p0, int p1, int p2, int p3, int p4, float[] p5) {
        ARBImaging.glColorTable(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glColorTableParameteriv(int p0, int p1, int[] p2) {
        ARBImaging.glColorTableParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glColorTableParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glColorTableParameterfv(int p0, int p1, float[] p2) {
        ARBImaging.glColorTableParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glColorTableParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetColorTable(int p0, int p1, int p2, short[] p3) {
        ARBImaging.glGetColorTable(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetColorTable(int p0, int p1, int p2, int[] p3) {
        ARBImaging.glGetColorTable(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetColorTable(int p0, int p1, int p2, float[] p3) {
        ARBImaging.glGetColorTable(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetColorTable", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetColorTableParameteriv(int p0, int p1, int[] p2) {
        ARBImaging.glGetColorTableParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetColorTableParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetColorTableParameterfv(int p0, int p1, float[] p2) {
        ARBImaging.glGetColorTableParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetColorTableParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glConvolutionParameteriv(int p0, int p1, int[] p2) {
        ARBImaging.glConvolutionParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glConvolutionParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glConvolutionParameterfv(int p0, int p1, float[] p2) {
        ARBImaging.glConvolutionParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glConvolutionParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetConvolutionParameteriv(int p0, int p1, int[] p2) {
        ARBImaging.glGetConvolutionParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetConvolutionParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetConvolutionParameterfv(int p0, int p1, float[] p2) {
        ARBImaging.glGetConvolutionParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetConvolutionParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetHistogramParameteriv(int p0, int p1, int[] p2) {
        ARBImaging.glGetHistogramParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetHistogramParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetHistogramParameterfv(int p0, int p1, float[] p2) {
        ARBImaging.glGetHistogramParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetHistogramParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetMinmaxParameteriv(int p0, int p1, int[] p2) {
        ARBImaging.glGetMinmaxParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetMinmaxParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetMinmaxParameterfv(int p0, int p1, float[] p2) {
        ARBImaging.glGetMinmaxParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetMinmaxParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

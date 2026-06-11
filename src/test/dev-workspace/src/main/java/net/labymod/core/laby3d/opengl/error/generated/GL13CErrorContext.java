package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GL13C;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL13CErrorContext.class */
public final class GL13CErrorContext {
    public static void nglCompressedTexImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL13C.nglCompressedTexImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglCompressedTexImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glCompressedTexImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL13C.glCompressedTexImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glCompressedTexImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glCompressedTexImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, ByteBuffer p7) {
        GL13C.glCompressedTexImage3D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glCompressedTexImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void nglCompressedTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        GL13C.nglCompressedTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglCompressedTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glCompressedTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        GL13C.glCompressedTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glCompressedTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glCompressedTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6) {
        GL13C.glCompressedTexImage2D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glCompressedTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void nglCompressedTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL13C.nglCompressedTexImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglCompressedTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glCompressedTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL13C.glCompressedTexImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glCompressedTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glCompressedTexImage1D(int p0, int p1, int p2, int p3, int p4, ByteBuffer p5) {
        GL13C.glCompressedTexImage1D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCompressedTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void nglCompressedTexSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        GL13C.nglCompressedTexSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("nglCompressedTexSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glCompressedTexSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        GL13C.glCompressedTexSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glCompressedTexSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glCompressedTexSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, ByteBuffer p9) {
        GL13C.glCompressedTexSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("glCompressedTexSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", p9);
    }

    public static void nglCompressedTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL13C.nglCompressedTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglCompressedTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glCompressedTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL13C.glCompressedTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glCompressedTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glCompressedTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, ByteBuffer p7) {
        GL13C.glCompressedTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glCompressedTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void nglCompressedTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL13C.nglCompressedTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglCompressedTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glCompressedTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL13C.glCompressedTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glCompressedTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glCompressedTexSubImage1D(int p0, int p1, int p2, int p3, int p4, ByteBuffer p5) {
        GL13C.glCompressedTexSubImage1D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCompressedTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void nglGetCompressedTexImage(int p0, int p1, long p2) {
        GL13C.nglGetCompressedTexImage(p0, p1, p2);
        LabyDebugContext.glError("nglGetCompressedTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetCompressedTexImage(int p0, int p1, ByteBuffer p2) {
        GL13C.glGetCompressedTexImage(p0, p1, p2);
        LabyDebugContext.glError("glGetCompressedTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetCompressedTexImage(int p0, int p1, long p2) {
        GL13C.glGetCompressedTexImage(p0, p1, p2);
        LabyDebugContext.glError("glGetCompressedTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSampleCoverage(float p0, boolean p1) {
        GL13C.glSampleCoverage(p0, p1);
        LabyDebugContext.glError("glSampleCoverage", "p0", Float.valueOf(p0), "p1", Boolean.valueOf(p1));
    }

    public static void glActiveTexture(int p0) {
        GL13C.glActiveTexture(p0);
        LabyDebugContext.glError("glActiveTexture", "p0", Integer.valueOf(p0));
    }
}

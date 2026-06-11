package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVTransformFeedback;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVTransformFeedbackErrorContext.class */
public final class NVTransformFeedbackErrorContext {
    public static void glBeginTransformFeedbackNV(int p0) {
        NVTransformFeedback.glBeginTransformFeedbackNV(p0);
        LabyDebugContext.glError("glBeginTransformFeedbackNV", "p0", Integer.valueOf(p0));
    }

    public static void glEndTransformFeedbackNV() {
        NVTransformFeedback.glEndTransformFeedbackNV();
        LabyDebugContext.glError("glEndTransformFeedbackNV", new Object[0]);
    }

    public static void nglTransformFeedbackAttribsNV(int p0, long p1, int p2) {
        NVTransformFeedback.nglTransformFeedbackAttribsNV(p0, p1, p2);
        LabyDebugContext.glError("nglTransformFeedbackAttribsNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glTransformFeedbackAttribsNV(IntBuffer p0, int p1) {
        NVTransformFeedback.glTransformFeedbackAttribsNV(p0, p1);
        LabyDebugContext.glError("glTransformFeedbackAttribsNV", "p0", p0, "p1", Integer.valueOf(p1));
    }

    public static void glBindBufferRangeNV(int p0, int p1, int p2, long p3, long p4) {
        NVTransformFeedback.glBindBufferRangeNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glBindBufferRangeNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glBindBufferOffsetNV(int p0, int p1, int p2, long p3) {
        NVTransformFeedback.glBindBufferOffsetNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindBufferOffsetNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glBindBufferBaseNV(int p0, int p1, int p2) {
        NVTransformFeedback.glBindBufferBaseNV(p0, p1, p2);
        LabyDebugContext.glError("glBindBufferBaseNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTransformFeedbackVaryingsNV(int p0, int p1, long p2, int p3) {
        NVTransformFeedback.nglTransformFeedbackVaryingsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglTransformFeedbackVaryingsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glTransformFeedbackVaryingsNV(int p0, IntBuffer p1, int p2) {
        NVTransformFeedback.glTransformFeedbackVaryingsNV(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackVaryingsNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglActiveVaryingNV(int p0, long p1) {
        NVTransformFeedback.nglActiveVaryingNV(p0, p1);
        LabyDebugContext.glError("nglActiveVaryingNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glActiveVaryingNV(int p0, ByteBuffer p1) {
        NVTransformFeedback.glActiveVaryingNV(p0, p1);
        LabyDebugContext.glError("glActiveVaryingNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glActiveVaryingNV(int p0, CharSequence p1) {
        NVTransformFeedback.glActiveVaryingNV(p0, p1);
        LabyDebugContext.glError("glActiveVaryingNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static int nglGetVaryingLocationNV(int p0, long p1) {
        int returnType = NVTransformFeedback.nglGetVaryingLocationNV(p0, p1);
        LabyDebugContext.glError("nglGetVaryingLocationNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glGetVaryingLocationNV(int p0, ByteBuffer p1) {
        int returnType = NVTransformFeedback.glGetVaryingLocationNV(p0, p1);
        LabyDebugContext.glError("glGetVaryingLocationNV", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glGetVaryingLocationNV(int p0, CharSequence p1) {
        int returnType = NVTransformFeedback.glGetVaryingLocationNV(p0, p1);
        LabyDebugContext.glError("glGetVaryingLocationNV", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void nglGetActiveVaryingNV(int p0, int p1, int p2, long p3, long p4, long p5, long p6) {
        NVTransformFeedback.nglGetActiveVaryingNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglGetActiveVaryingNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glGetActiveVaryingNV(int p0, int p1, IntBuffer p2, IntBuffer p3, IntBuffer p4, ByteBuffer p5) {
        NVTransformFeedback.glGetActiveVaryingNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetActiveVaryingNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static void nglGetTransformFeedbackVaryingNV(int p0, int p1, long p2) {
        NVTransformFeedback.nglGetTransformFeedbackVaryingNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetTransformFeedbackVaryingNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTransformFeedbackVaryingNV(int p0, int p1, IntBuffer p2) {
        NVTransformFeedback.glGetTransformFeedbackVaryingNV(p0, p1, p2);
        LabyDebugContext.glError("glGetTransformFeedbackVaryingNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTransformFeedbackVaryingNV(int p0, int p1) {
        int returnType = NVTransformFeedback.glGetTransformFeedbackVaryingNV(p0, p1);
        LabyDebugContext.glError("glGetTransformFeedbackVaryingNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglTransformFeedbackStreamAttribsNV(int p0, long p1, int p2, long p3, int p4) {
        NVTransformFeedback.nglTransformFeedbackStreamAttribsNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglTransformFeedbackStreamAttribsNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glTransformFeedbackStreamAttribsNV(IntBuffer p0, IntBuffer p1, int p2) {
        NVTransformFeedback.glTransformFeedbackStreamAttribsNV(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackStreamAttribsNV", "p0", p0, "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glTransformFeedbackAttribsNV(int[] p0, int p1) {
        NVTransformFeedback.glTransformFeedbackAttribsNV(p0, p1);
        LabyDebugContext.glError("glTransformFeedbackAttribsNV", "p0", p0, "p1", Integer.valueOf(p1));
    }

    public static void glTransformFeedbackVaryingsNV(int p0, int[] p1, int p2) {
        NVTransformFeedback.glTransformFeedbackVaryingsNV(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackVaryingsNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glGetActiveVaryingNV(int p0, int p1, int[] p2, int[] p3, int[] p4, ByteBuffer p5) {
        NVTransformFeedback.glGetActiveVaryingNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetActiveVaryingNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static void glGetTransformFeedbackVaryingNV(int p0, int p1, int[] p2) {
        NVTransformFeedback.glGetTransformFeedbackVaryingNV(p0, p1, p2);
        LabyDebugContext.glError("glGetTransformFeedbackVaryingNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTransformFeedbackStreamAttribsNV(int[] p0, int[] p1, int p2) {
        NVTransformFeedback.glTransformFeedbackStreamAttribsNV(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackStreamAttribsNV", "p0", p0, "p1", p1, "p2", Integer.valueOf(p2));
    }
}

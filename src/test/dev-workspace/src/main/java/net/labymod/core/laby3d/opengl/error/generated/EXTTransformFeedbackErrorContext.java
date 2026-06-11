package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.EXTTransformFeedback;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTTransformFeedbackErrorContext.class */
public final class EXTTransformFeedbackErrorContext {
    public static void glBindBufferRangeEXT(int p0, int p1, int p2, long p3, long p4) {
        EXTTransformFeedback.glBindBufferRangeEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glBindBufferRangeEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glBindBufferOffsetEXT(int p0, int p1, int p2, long p3) {
        EXTTransformFeedback.glBindBufferOffsetEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindBufferOffsetEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glBindBufferBaseEXT(int p0, int p1, int p2) {
        EXTTransformFeedback.glBindBufferBaseEXT(p0, p1, p2);
        LabyDebugContext.glError("glBindBufferBaseEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBeginTransformFeedbackEXT(int p0) {
        EXTTransformFeedback.glBeginTransformFeedbackEXT(p0);
        LabyDebugContext.glError("glBeginTransformFeedbackEXT", "p0", Integer.valueOf(p0));
    }

    public static void glEndTransformFeedbackEXT() {
        EXTTransformFeedback.glEndTransformFeedbackEXT();
        LabyDebugContext.glError("glEndTransformFeedbackEXT", new Object[0]);
    }

    public static void nglTransformFeedbackVaryingsEXT(int p0, int p1, long p2, int p3) {
        EXTTransformFeedback.nglTransformFeedbackVaryingsEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("nglTransformFeedbackVaryingsEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glTransformFeedbackVaryingsEXT(int p0, PointerBuffer p1, int p2) {
        EXTTransformFeedback.glTransformFeedbackVaryingsEXT(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackVaryingsEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glTransformFeedbackVaryingsEXT(int p0, CharSequence[] p1, int p2) {
        EXTTransformFeedback.glTransformFeedbackVaryingsEXT(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackVaryingsEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glTransformFeedbackVaryingsEXT(int p0, CharSequence p1, int p2) {
        EXTTransformFeedback.glTransformFeedbackVaryingsEXT(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackVaryingsEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglGetTransformFeedbackVaryingEXT(int p0, int p1, int p2, long p3, long p4, long p5, long p6) {
        EXTTransformFeedback.nglGetTransformFeedbackVaryingEXT(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglGetTransformFeedbackVaryingEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glGetTransformFeedbackVaryingEXT(int p0, int p1, IntBuffer p2, IntBuffer p3, IntBuffer p4, ByteBuffer p5) {
        EXTTransformFeedback.glGetTransformFeedbackVaryingEXT(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetTransformFeedbackVaryingEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static String glGetTransformFeedbackVaryingEXT(int p0, int p1, int p2, IntBuffer p3, IntBuffer p4) {
        String returnType = EXTTransformFeedback.glGetTransformFeedbackVaryingEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTransformFeedbackVaryingEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
        return returnType;
    }

    public static String glGetTransformFeedbackVaryingEXT(int p0, int p1, IntBuffer p2, IntBuffer p3) {
        String returnType = EXTTransformFeedback.glGetTransformFeedbackVaryingEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTransformFeedbackVaryingEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
        return returnType;
    }

    public static void nglGetIntegerIndexedvEXT(int p0, int p1, long p2) {
        EXTTransformFeedback.nglGetIntegerIndexedvEXT(p0, p1, p2);
        LabyDebugContext.glError("nglGetIntegerIndexedvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetIntegerIndexedvEXT(int p0, int p1, IntBuffer p2) {
        EXTTransformFeedback.glGetIntegerIndexedvEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetIntegerIndexedvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetIntegerIndexedEXT(int p0, int p1) {
        int returnType = EXTTransformFeedback.glGetIntegerIndexedEXT(p0, p1);
        LabyDebugContext.glError("glGetIntegerIndexedEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetBooleanIndexedvEXT(int p0, int p1, long p2) {
        EXTTransformFeedback.nglGetBooleanIndexedvEXT(p0, p1, p2);
        LabyDebugContext.glError("nglGetBooleanIndexedvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetBooleanIndexedvEXT(int p0, int p1, ByteBuffer p2) {
        EXTTransformFeedback.glGetBooleanIndexedvEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetBooleanIndexedvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static boolean glGetBooleanIndexedEXT(int p0, int p1) {
        boolean returnType = EXTTransformFeedback.glGetBooleanIndexedEXT(p0, p1);
        LabyDebugContext.glError("glGetBooleanIndexedEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glGetTransformFeedbackVaryingEXT(int p0, int p1, int[] p2, int[] p3, int[] p4, ByteBuffer p5) {
        EXTTransformFeedback.glGetTransformFeedbackVaryingEXT(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetTransformFeedbackVaryingEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static void glGetIntegerIndexedvEXT(int p0, int p1, int[] p2) {
        EXTTransformFeedback.glGetIntegerIndexedvEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetIntegerIndexedvEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

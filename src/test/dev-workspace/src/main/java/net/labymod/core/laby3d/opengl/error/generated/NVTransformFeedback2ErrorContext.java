package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVTransformFeedback2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVTransformFeedback2ErrorContext.class */
public final class NVTransformFeedback2ErrorContext {
    public static void glBindTransformFeedbackNV(int p0, int p1) {
        NVTransformFeedback2.glBindTransformFeedbackNV(p0, p1);
        LabyDebugContext.glError("glBindTransformFeedbackNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglDeleteTransformFeedbacksNV(int p0, long p1) {
        NVTransformFeedback2.nglDeleteTransformFeedbacksNV(p0, p1);
        LabyDebugContext.glError("nglDeleteTransformFeedbacksNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteTransformFeedbacksNV(IntBuffer p0) {
        NVTransformFeedback2.glDeleteTransformFeedbacksNV(p0);
        LabyDebugContext.glError("glDeleteTransformFeedbacksNV", "p0", p0);
    }

    public static void glDeleteTransformFeedbacksNV(int p0) {
        NVTransformFeedback2.glDeleteTransformFeedbacksNV(p0);
        LabyDebugContext.glError("glDeleteTransformFeedbacksNV", "p0", Integer.valueOf(p0));
    }

    public static void nglGenTransformFeedbacksNV(int p0, long p1) {
        NVTransformFeedback2.nglGenTransformFeedbacksNV(p0, p1);
        LabyDebugContext.glError("nglGenTransformFeedbacksNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenTransformFeedbacksNV(IntBuffer p0) {
        NVTransformFeedback2.glGenTransformFeedbacksNV(p0);
        LabyDebugContext.glError("glGenTransformFeedbacksNV", "p0", p0);
    }

    public static int glGenTransformFeedbacksNV() {
        int returnType = NVTransformFeedback2.glGenTransformFeedbacksNV();
        LabyDebugContext.glError("glGenTransformFeedbacksNV", new Object[0]);
        return returnType;
    }

    public static boolean glIsTransformFeedbackNV(int p0) {
        boolean returnType = NVTransformFeedback2.glIsTransformFeedbackNV(p0);
        LabyDebugContext.glError("glIsTransformFeedbackNV", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glPauseTransformFeedbackNV() {
        NVTransformFeedback2.glPauseTransformFeedbackNV();
        LabyDebugContext.glError("glPauseTransformFeedbackNV", new Object[0]);
    }

    public static void glResumeTransformFeedbackNV() {
        NVTransformFeedback2.glResumeTransformFeedbackNV();
        LabyDebugContext.glError("glResumeTransformFeedbackNV", new Object[0]);
    }

    public static void glDrawTransformFeedbackNV(int p0, int p1) {
        NVTransformFeedback2.glDrawTransformFeedbackNV(p0, p1);
        LabyDebugContext.glError("glDrawTransformFeedbackNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glDeleteTransformFeedbacksNV(int[] p0) {
        NVTransformFeedback2.glDeleteTransformFeedbacksNV(p0);
        LabyDebugContext.glError("glDeleteTransformFeedbacksNV", "p0", p0);
    }

    public static void glGenTransformFeedbacksNV(int[] p0) {
        NVTransformFeedback2.glGenTransformFeedbacksNV(p0);
        LabyDebugContext.glError("glGenTransformFeedbacksNV", "p0", p0);
    }
}

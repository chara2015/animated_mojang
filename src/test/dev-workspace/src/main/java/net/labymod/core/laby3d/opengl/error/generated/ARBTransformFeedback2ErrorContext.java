package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBTransformFeedback2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBTransformFeedback2ErrorContext.class */
public final class ARBTransformFeedback2ErrorContext {
    public static void glBindTransformFeedback(int p0, int p1) {
        ARBTransformFeedback2.glBindTransformFeedback(p0, p1);
        LabyDebugContext.glError("glBindTransformFeedback", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglDeleteTransformFeedbacks(int p0, long p1) {
        ARBTransformFeedback2.nglDeleteTransformFeedbacks(p0, p1);
        LabyDebugContext.glError("nglDeleteTransformFeedbacks", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteTransformFeedbacks(IntBuffer p0) {
        ARBTransformFeedback2.glDeleteTransformFeedbacks(p0);
        LabyDebugContext.glError("glDeleteTransformFeedbacks", "p0", p0);
    }

    public static void glDeleteTransformFeedbacks(int p0) {
        ARBTransformFeedback2.glDeleteTransformFeedbacks(p0);
        LabyDebugContext.glError("glDeleteTransformFeedbacks", "p0", Integer.valueOf(p0));
    }

    public static void nglGenTransformFeedbacks(int p0, long p1) {
        ARBTransformFeedback2.nglGenTransformFeedbacks(p0, p1);
        LabyDebugContext.glError("nglGenTransformFeedbacks", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenTransformFeedbacks(IntBuffer p0) {
        ARBTransformFeedback2.glGenTransformFeedbacks(p0);
        LabyDebugContext.glError("glGenTransformFeedbacks", "p0", p0);
    }

    public static int glGenTransformFeedbacks() {
        int returnType = ARBTransformFeedback2.glGenTransformFeedbacks();
        LabyDebugContext.glError("glGenTransformFeedbacks", new Object[0]);
        return returnType;
    }

    public static boolean glIsTransformFeedback(int p0) {
        boolean returnType = ARBTransformFeedback2.glIsTransformFeedback(p0);
        LabyDebugContext.glError("glIsTransformFeedback", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glPauseTransformFeedback() {
        ARBTransformFeedback2.glPauseTransformFeedback();
        LabyDebugContext.glError("glPauseTransformFeedback", new Object[0]);
    }

    public static void glResumeTransformFeedback() {
        ARBTransformFeedback2.glResumeTransformFeedback();
        LabyDebugContext.glError("glResumeTransformFeedback", new Object[0]);
    }

    public static void glDrawTransformFeedback(int p0, int p1) {
        ARBTransformFeedback2.glDrawTransformFeedback(p0, p1);
        LabyDebugContext.glError("glDrawTransformFeedback", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glDeleteTransformFeedbacks(int[] p0) {
        ARBTransformFeedback2.glDeleteTransformFeedbacks(p0);
        LabyDebugContext.glError("glDeleteTransformFeedbacks", "p0", p0);
    }

    public static void glGenTransformFeedbacks(int[] p0) {
        ARBTransformFeedback2.glGenTransformFeedbacks(p0);
        LabyDebugContext.glError("glGenTransformFeedbacks", "p0", p0);
    }
}

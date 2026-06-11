package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBSamplerObjects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBSamplerObjectsErrorContext.class */
public final class ARBSamplerObjectsErrorContext {
    public static void nglGenSamplers(int p0, long p1) {
        ARBSamplerObjects.nglGenSamplers(p0, p1);
        LabyDebugContext.glError("nglGenSamplers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenSamplers(IntBuffer p0) {
        ARBSamplerObjects.glGenSamplers(p0);
        LabyDebugContext.glError("glGenSamplers", "p0", p0);
    }

    public static int glGenSamplers() {
        int returnType = ARBSamplerObjects.glGenSamplers();
        LabyDebugContext.glError("glGenSamplers", new Object[0]);
        return returnType;
    }

    public static void nglDeleteSamplers(int p0, long p1) {
        ARBSamplerObjects.nglDeleteSamplers(p0, p1);
        LabyDebugContext.glError("nglDeleteSamplers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteSamplers(IntBuffer p0) {
        ARBSamplerObjects.glDeleteSamplers(p0);
        LabyDebugContext.glError("glDeleteSamplers", "p0", p0);
    }

    public static void glDeleteSamplers(int p0) {
        ARBSamplerObjects.glDeleteSamplers(p0);
        LabyDebugContext.glError("glDeleteSamplers", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsSampler(int p0) {
        boolean returnType = ARBSamplerObjects.glIsSampler(p0);
        LabyDebugContext.glError("glIsSampler", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glBindSampler(int p0, int p1) {
        ARBSamplerObjects.glBindSampler(p0, p1);
        LabyDebugContext.glError("glBindSampler", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glSamplerParameteri(int p0, int p1, int p2) {
        ARBSamplerObjects.glSamplerParameteri(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glSamplerParameterf(int p0, int p1, float p2) {
        ARBSamplerObjects.glSamplerParameterf(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglSamplerParameteriv(int p0, int p1, long p2) {
        ARBSamplerObjects.nglSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameteriv(int p0, int p1, IntBuffer p2) {
        ARBSamplerObjects.glSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglSamplerParameterfv(int p0, int p1, long p2) {
        ARBSamplerObjects.nglSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameterfv(int p0, int p1, FloatBuffer p2) {
        ARBSamplerObjects.glSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglSamplerParameterIiv(int p0, int p1, long p2) {
        ARBSamplerObjects.nglSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameterIiv(int p0, int p1, IntBuffer p2) {
        ARBSamplerObjects.glSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglSamplerParameterIuiv(int p0, int p1, long p2) {
        ARBSamplerObjects.nglSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameterIuiv(int p0, int p1, IntBuffer p2) {
        ARBSamplerObjects.glSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetSamplerParameteriv(int p0, int p1, long p2) {
        ARBSamplerObjects.nglGetSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameteriv(int p0, int p1, IntBuffer p2) {
        ARBSamplerObjects.glGetSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetSamplerParameteri(int p0, int p1) {
        int returnType = ARBSamplerObjects.glGetSamplerParameteri(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetSamplerParameterfv(int p0, int p1, long p2) {
        ARBSamplerObjects.nglGetSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameterfv(int p0, int p1, FloatBuffer p2) {
        ARBSamplerObjects.glGetSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetSamplerParameterf(int p0, int p1) {
        float returnType = ARBSamplerObjects.glGetSamplerParameterf(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetSamplerParameterIiv(int p0, int p1, long p2) {
        ARBSamplerObjects.nglGetSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameterIiv(int p0, int p1, IntBuffer p2) {
        ARBSamplerObjects.glGetSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetSamplerParameterIi(int p0, int p1) {
        int returnType = ARBSamplerObjects.glGetSamplerParameterIi(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameterIi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetSamplerParameterIuiv(int p0, int p1, long p2) {
        ARBSamplerObjects.nglGetSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameterIuiv(int p0, int p1, IntBuffer p2) {
        ARBSamplerObjects.glGetSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetSamplerParameterIui(int p0, int p1) {
        int returnType = ARBSamplerObjects.glGetSamplerParameterIui(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameterIui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glGenSamplers(int[] p0) {
        ARBSamplerObjects.glGenSamplers(p0);
        LabyDebugContext.glError("glGenSamplers", "p0", p0);
    }

    public static void glDeleteSamplers(int[] p0) {
        ARBSamplerObjects.glDeleteSamplers(p0);
        LabyDebugContext.glError("glDeleteSamplers", "p0", p0);
    }

    public static void glSamplerParameteriv(int p0, int p1, int[] p2) {
        ARBSamplerObjects.glSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSamplerParameterfv(int p0, int p1, float[] p2) {
        ARBSamplerObjects.glSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSamplerParameterIiv(int p0, int p1, int[] p2) {
        ARBSamplerObjects.glSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSamplerParameterIuiv(int p0, int p1, int[] p2) {
        ARBSamplerObjects.glSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameteriv(int p0, int p1, int[] p2) {
        ARBSamplerObjects.glGetSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameterfv(int p0, int p1, float[] p2) {
        ARBSamplerObjects.glGetSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameterIiv(int p0, int p1, int[] p2) {
        ARBSamplerObjects.glGetSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameterIuiv(int p0, int p1, int[] p2) {
        ARBSamplerObjects.glGetSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

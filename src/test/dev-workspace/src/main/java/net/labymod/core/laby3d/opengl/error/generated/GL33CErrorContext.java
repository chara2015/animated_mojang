package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GL33C;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL33CErrorContext.class */
public final class GL33CErrorContext {
    public static void nglBindFragDataLocationIndexed(int p0, int p1, int p2, long p3) {
        GL33C.nglBindFragDataLocationIndexed(p0, p1, p2, p3);
        LabyDebugContext.glError("nglBindFragDataLocationIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glBindFragDataLocationIndexed(int p0, int p1, int p2, ByteBuffer p3) {
        GL33C.glBindFragDataLocationIndexed(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindFragDataLocationIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glBindFragDataLocationIndexed(int p0, int p1, int p2, CharSequence p3) {
        GL33C.glBindFragDataLocationIndexed(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindFragDataLocationIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int nglGetFragDataIndex(int p0, long p1) {
        int returnType = GL33C.nglGetFragDataIndex(p0, p1);
        LabyDebugContext.glError("nglGetFragDataIndex", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glGetFragDataIndex(int p0, ByteBuffer p1) {
        int returnType = GL33C.glGetFragDataIndex(p0, p1);
        LabyDebugContext.glError("glGetFragDataIndex", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glGetFragDataIndex(int p0, CharSequence p1) {
        int returnType = GL33C.glGetFragDataIndex(p0, p1);
        LabyDebugContext.glError("glGetFragDataIndex", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void nglGenSamplers(int p0, long p1) {
        GL33C.nglGenSamplers(p0, p1);
        LabyDebugContext.glError("nglGenSamplers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenSamplers(IntBuffer p0) {
        GL33C.glGenSamplers(p0);
        LabyDebugContext.glError("glGenSamplers", "p0", p0);
    }

    public static int glGenSamplers() {
        int returnType = GL33C.glGenSamplers();
        LabyDebugContext.glError("glGenSamplers", new Object[0]);
        return returnType;
    }

    public static void nglDeleteSamplers(int p0, long p1) {
        GL33C.nglDeleteSamplers(p0, p1);
        LabyDebugContext.glError("nglDeleteSamplers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteSamplers(IntBuffer p0) {
        GL33C.glDeleteSamplers(p0);
        LabyDebugContext.glError("glDeleteSamplers", "p0", p0);
    }

    public static void glDeleteSamplers(int p0) {
        GL33C.glDeleteSamplers(p0);
        LabyDebugContext.glError("glDeleteSamplers", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsSampler(int p0) {
        boolean returnType = GL33C.glIsSampler(p0);
        LabyDebugContext.glError("glIsSampler", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glBindSampler(int p0, int p1) {
        GL33C.glBindSampler(p0, p1);
        LabyDebugContext.glError("glBindSampler", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glSamplerParameteri(int p0, int p1, int p2) {
        GL33C.glSamplerParameteri(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glSamplerParameterf(int p0, int p1, float p2) {
        GL33C.glSamplerParameterf(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglSamplerParameteriv(int p0, int p1, long p2) {
        GL33C.nglSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameteriv(int p0, int p1, IntBuffer p2) {
        GL33C.glSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglSamplerParameterfv(int p0, int p1, long p2) {
        GL33C.nglSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameterfv(int p0, int p1, FloatBuffer p2) {
        GL33C.glSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglSamplerParameterIiv(int p0, int p1, long p2) {
        GL33C.nglSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameterIiv(int p0, int p1, IntBuffer p2) {
        GL33C.glSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglSamplerParameterIuiv(int p0, int p1, long p2) {
        GL33C.nglSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameterIuiv(int p0, int p1, IntBuffer p2) {
        GL33C.glSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetSamplerParameteriv(int p0, int p1, long p2) {
        GL33C.nglGetSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameteriv(int p0, int p1, IntBuffer p2) {
        GL33C.glGetSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetSamplerParameteri(int p0, int p1) {
        int returnType = GL33C.glGetSamplerParameteri(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetSamplerParameterfv(int p0, int p1, long p2) {
        GL33C.nglGetSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameterfv(int p0, int p1, FloatBuffer p2) {
        GL33C.glGetSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetSamplerParameterf(int p0, int p1) {
        float returnType = GL33C.glGetSamplerParameterf(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetSamplerParameterIiv(int p0, int p1, long p2) {
        GL33C.nglGetSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameterIiv(int p0, int p1, IntBuffer p2) {
        GL33C.glGetSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetSamplerParameterIi(int p0, int p1) {
        int returnType = GL33C.glGetSamplerParameterIi(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameterIi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetSamplerParameterIuiv(int p0, int p1, long p2) {
        GL33C.nglGetSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameterIuiv(int p0, int p1, IntBuffer p2) {
        GL33C.glGetSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetSamplerParameterIui(int p0, int p1) {
        int returnType = GL33C.glGetSamplerParameterIui(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameterIui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glQueryCounter(int p0, int p1) {
        GL33C.glQueryCounter(p0, p1);
        LabyDebugContext.glError("glQueryCounter", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglGetQueryObjecti64v(int p0, int p1, long p2) {
        GL33C.nglGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjecti64v(int p0, int p1, LongBuffer p2) {
        GL33C.glGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjecti64v(int p0, int p1, long p2) {
        GL33C.glGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static long glGetQueryObjecti64(int p0, int p1) {
        long returnType = GL33C.glGetQueryObjecti64(p0, p1);
        LabyDebugContext.glError("glGetQueryObjecti64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetQueryObjectui64v(int p0, int p1, long p2) {
        GL33C.nglGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjectui64v(int p0, int p1, LongBuffer p2) {
        GL33C.glGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectui64v(int p0, int p1, long p2) {
        GL33C.glGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static long glGetQueryObjectui64(int p0, int p1) {
        long returnType = GL33C.glGetQueryObjectui64(p0, p1);
        LabyDebugContext.glError("glGetQueryObjectui64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glVertexAttribDivisor(int p0, int p1) {
        GL33C.glVertexAttribDivisor(p0, p1);
        LabyDebugContext.glError("glVertexAttribDivisor", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexAttribP1ui(int p0, int p1, boolean p2, int p3) {
        GL33C.glVertexAttribP1ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP1ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribP2ui(int p0, int p1, boolean p2, int p3) {
        GL33C.glVertexAttribP2ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP2ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribP3ui(int p0, int p1, boolean p2, int p3) {
        GL33C.glVertexAttribP3ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribP4ui(int p0, int p1, boolean p2, int p3) {
        GL33C.glVertexAttribP4ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP4ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglVertexAttribP1uiv(int p0, int p1, boolean p2, long p3) {
        GL33C.nglVertexAttribP1uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglVertexAttribP1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glVertexAttribP1uiv(int p0, int p1, boolean p2, IntBuffer p3) {
        GL33C.glVertexAttribP1uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglVertexAttribP2uiv(int p0, int p1, boolean p2, long p3) {
        GL33C.nglVertexAttribP2uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglVertexAttribP2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glVertexAttribP2uiv(int p0, int p1, boolean p2, IntBuffer p3) {
        GL33C.glVertexAttribP2uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglVertexAttribP3uiv(int p0, int p1, boolean p2, long p3) {
        GL33C.nglVertexAttribP3uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglVertexAttribP3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glVertexAttribP3uiv(int p0, int p1, boolean p2, IntBuffer p3) {
        GL33C.glVertexAttribP3uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglVertexAttribP4uiv(int p0, int p1, boolean p2, long p3) {
        GL33C.nglVertexAttribP4uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglVertexAttribP4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glVertexAttribP4uiv(int p0, int p1, boolean p2, IntBuffer p3) {
        GL33C.glVertexAttribP4uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glGenSamplers(int[] p0) {
        GL33C.glGenSamplers(p0);
        LabyDebugContext.glError("glGenSamplers", "p0", p0);
    }

    public static void glDeleteSamplers(int[] p0) {
        GL33C.glDeleteSamplers(p0);
        LabyDebugContext.glError("glDeleteSamplers", "p0", p0);
    }

    public static void glSamplerParameteriv(int p0, int p1, int[] p2) {
        GL33C.glSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSamplerParameterfv(int p0, int p1, float[] p2) {
        GL33C.glSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSamplerParameterIiv(int p0, int p1, int[] p2) {
        GL33C.glSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSamplerParameterIuiv(int p0, int p1, int[] p2) {
        GL33C.glSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameteriv(int p0, int p1, int[] p2) {
        GL33C.glGetSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameterfv(int p0, int p1, float[] p2) {
        GL33C.glGetSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameterIiv(int p0, int p1, int[] p2) {
        GL33C.glGetSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameterIuiv(int p0, int p1, int[] p2) {
        GL33C.glGetSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjecti64v(int p0, int p1, long[] p2) {
        GL33C.glGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectui64v(int p0, int p1, long[] p2) {
        GL33C.glGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glVertexAttribP1uiv(int p0, int p1, boolean p2, int[] p3) {
        GL33C.glVertexAttribP1uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glVertexAttribP2uiv(int p0, int p1, boolean p2, int[] p3) {
        GL33C.glVertexAttribP2uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glVertexAttribP3uiv(int p0, int p1, boolean p2, int[] p3) {
        GL33C.glVertexAttribP3uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glVertexAttribP4uiv(int p0, int p1, boolean p2, int[] p3) {
        GL33C.glVertexAttribP4uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }
}

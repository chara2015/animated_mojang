package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GL33;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL33ErrorContext.class */
public final class GL33ErrorContext {
    public static void nglBindFragDataLocationIndexed(int p0, int p1, int p2, long p3) {
        GL33.nglBindFragDataLocationIndexed(p0, p1, p2, p3);
        LabyDebugContext.glError("nglBindFragDataLocationIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glBindFragDataLocationIndexed(int p0, int p1, int p2, ByteBuffer p3) {
        GL33.glBindFragDataLocationIndexed(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindFragDataLocationIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glBindFragDataLocationIndexed(int p0, int p1, int p2, CharSequence p3) {
        GL33.glBindFragDataLocationIndexed(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindFragDataLocationIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int nglGetFragDataIndex(int p0, long p1) {
        int returnType = GL33.nglGetFragDataIndex(p0, p1);
        LabyDebugContext.glError("nglGetFragDataIndex", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glGetFragDataIndex(int p0, ByteBuffer p1) {
        int returnType = GL33.glGetFragDataIndex(p0, p1);
        LabyDebugContext.glError("glGetFragDataIndex", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glGetFragDataIndex(int p0, CharSequence p1) {
        int returnType = GL33.glGetFragDataIndex(p0, p1);
        LabyDebugContext.glError("glGetFragDataIndex", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void nglGenSamplers(int p0, long p1) {
        GL33.nglGenSamplers(p0, p1);
        LabyDebugContext.glError("nglGenSamplers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenSamplers(IntBuffer p0) {
        GL33.glGenSamplers(p0);
        LabyDebugContext.glError("glGenSamplers", "p0", p0);
    }

    public static int glGenSamplers() {
        int returnType = GL33.glGenSamplers();
        LabyDebugContext.glError("glGenSamplers", new Object[0]);
        return returnType;
    }

    public static void nglDeleteSamplers(int p0, long p1) {
        GL33.nglDeleteSamplers(p0, p1);
        LabyDebugContext.glError("nglDeleteSamplers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteSamplers(IntBuffer p0) {
        GL33.glDeleteSamplers(p0);
        LabyDebugContext.glError("glDeleteSamplers", "p0", p0);
    }

    public static void glDeleteSamplers(int p0) {
        GL33.glDeleteSamplers(p0);
        LabyDebugContext.glError("glDeleteSamplers", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsSampler(int p0) {
        boolean returnType = GL33.glIsSampler(p0);
        LabyDebugContext.glError("glIsSampler", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glBindSampler(int p0, int p1) {
        GL33.glBindSampler(p0, p1);
        LabyDebugContext.glError("glBindSampler", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glSamplerParameteri(int p0, int p1, int p2) {
        GL33.glSamplerParameteri(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glSamplerParameterf(int p0, int p1, float p2) {
        GL33.glSamplerParameterf(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglSamplerParameteriv(int p0, int p1, long p2) {
        GL33.nglSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameteriv(int p0, int p1, IntBuffer p2) {
        GL33.glSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglSamplerParameterfv(int p0, int p1, long p2) {
        GL33.nglSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameterfv(int p0, int p1, FloatBuffer p2) {
        GL33.glSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglSamplerParameterIiv(int p0, int p1, long p2) {
        GL33.nglSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameterIiv(int p0, int p1, IntBuffer p2) {
        GL33.glSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglSamplerParameterIuiv(int p0, int p1, long p2) {
        GL33.nglSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSamplerParameterIuiv(int p0, int p1, IntBuffer p2) {
        GL33.glSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetSamplerParameteriv(int p0, int p1, long p2) {
        GL33.nglGetSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameteriv(int p0, int p1, IntBuffer p2) {
        GL33.glGetSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetSamplerParameteri(int p0, int p1) {
        int returnType = GL33.glGetSamplerParameteri(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetSamplerParameterfv(int p0, int p1, long p2) {
        GL33.nglGetSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameterfv(int p0, int p1, FloatBuffer p2) {
        GL33.glGetSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetSamplerParameterf(int p0, int p1) {
        float returnType = GL33.glGetSamplerParameterf(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetSamplerParameterIiv(int p0, int p1, long p2) {
        GL33.nglGetSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameterIiv(int p0, int p1, IntBuffer p2) {
        GL33.glGetSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetSamplerParameterIi(int p0, int p1) {
        int returnType = GL33.glGetSamplerParameterIi(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameterIi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetSamplerParameterIuiv(int p0, int p1, long p2) {
        GL33.nglGetSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetSamplerParameterIuiv(int p0, int p1, IntBuffer p2) {
        GL33.glGetSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetSamplerParameterIui(int p0, int p1) {
        int returnType = GL33.glGetSamplerParameterIui(p0, p1);
        LabyDebugContext.glError("glGetSamplerParameterIui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glQueryCounter(int p0, int p1) {
        GL33.glQueryCounter(p0, p1);
        LabyDebugContext.glError("glQueryCounter", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglGetQueryObjecti64v(int p0, int p1, long p2) {
        GL33.nglGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjecti64v(int p0, int p1, LongBuffer p2) {
        GL33.glGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjecti64v(int p0, int p1, long p2) {
        GL33.glGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static long glGetQueryObjecti64(int p0, int p1) {
        long returnType = GL33.glGetQueryObjecti64(p0, p1);
        LabyDebugContext.glError("glGetQueryObjecti64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetQueryObjectui64v(int p0, int p1, long p2) {
        GL33.nglGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjectui64v(int p0, int p1, LongBuffer p2) {
        GL33.glGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectui64v(int p0, int p1, long p2) {
        GL33.glGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static long glGetQueryObjectui64(int p0, int p1) {
        long returnType = GL33.glGetQueryObjectui64(p0, p1);
        LabyDebugContext.glError("glGetQueryObjectui64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glVertexAttribDivisor(int p0, int p1) {
        GL33.glVertexAttribDivisor(p0, p1);
        LabyDebugContext.glError("glVertexAttribDivisor", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexP2ui(int p0, int p1) {
        GL33.glVertexP2ui(p0, p1);
        LabyDebugContext.glError("glVertexP2ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexP3ui(int p0, int p1) {
        GL33.glVertexP3ui(p0, p1);
        LabyDebugContext.glError("glVertexP3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexP4ui(int p0, int p1) {
        GL33.glVertexP4ui(p0, p1);
        LabyDebugContext.glError("glVertexP4ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglVertexP2uiv(int p0, long p1) {
        GL33.nglVertexP2uiv(p0, p1);
        LabyDebugContext.glError("nglVertexP2uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexP2uiv(int p0, IntBuffer p1) {
        GL33.glVertexP2uiv(p0, p1);
        LabyDebugContext.glError("glVertexP2uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexP3uiv(int p0, long p1) {
        GL33.nglVertexP3uiv(p0, p1);
        LabyDebugContext.glError("nglVertexP3uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexP3uiv(int p0, IntBuffer p1) {
        GL33.glVertexP3uiv(p0, p1);
        LabyDebugContext.glError("glVertexP3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexP4uiv(int p0, long p1) {
        GL33.nglVertexP4uiv(p0, p1);
        LabyDebugContext.glError("nglVertexP4uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexP4uiv(int p0, IntBuffer p1) {
        GL33.glVertexP4uiv(p0, p1);
        LabyDebugContext.glError("glVertexP4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glTexCoordP1ui(int p0, int p1) {
        GL33.glTexCoordP1ui(p0, p1);
        LabyDebugContext.glError("glTexCoordP1ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glTexCoordP2ui(int p0, int p1) {
        GL33.glTexCoordP2ui(p0, p1);
        LabyDebugContext.glError("glTexCoordP2ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glTexCoordP3ui(int p0, int p1) {
        GL33.glTexCoordP3ui(p0, p1);
        LabyDebugContext.glError("glTexCoordP3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glTexCoordP4ui(int p0, int p1) {
        GL33.glTexCoordP4ui(p0, p1);
        LabyDebugContext.glError("glTexCoordP4ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglTexCoordP1uiv(int p0, long p1) {
        GL33.nglTexCoordP1uiv(p0, p1);
        LabyDebugContext.glError("nglTexCoordP1uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glTexCoordP1uiv(int p0, IntBuffer p1) {
        GL33.glTexCoordP1uiv(p0, p1);
        LabyDebugContext.glError("glTexCoordP1uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglTexCoordP2uiv(int p0, long p1) {
        GL33.nglTexCoordP2uiv(p0, p1);
        LabyDebugContext.glError("nglTexCoordP2uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glTexCoordP2uiv(int p0, IntBuffer p1) {
        GL33.glTexCoordP2uiv(p0, p1);
        LabyDebugContext.glError("glTexCoordP2uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglTexCoordP3uiv(int p0, long p1) {
        GL33.nglTexCoordP3uiv(p0, p1);
        LabyDebugContext.glError("nglTexCoordP3uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glTexCoordP3uiv(int p0, IntBuffer p1) {
        GL33.glTexCoordP3uiv(p0, p1);
        LabyDebugContext.glError("glTexCoordP3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglTexCoordP4uiv(int p0, long p1) {
        GL33.nglTexCoordP4uiv(p0, p1);
        LabyDebugContext.glError("nglTexCoordP4uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glTexCoordP4uiv(int p0, IntBuffer p1) {
        GL33.glTexCoordP4uiv(p0, p1);
        LabyDebugContext.glError("glTexCoordP4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoordP1ui(int p0, int p1, int p2) {
        GL33.glMultiTexCoordP1ui(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP1ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glMultiTexCoordP2ui(int p0, int p1, int p2) {
        GL33.glMultiTexCoordP2ui(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP2ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glMultiTexCoordP3ui(int p0, int p1, int p2) {
        GL33.glMultiTexCoordP3ui(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glMultiTexCoordP4ui(int p0, int p1, int p2) {
        GL33.glMultiTexCoordP4ui(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP4ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglMultiTexCoordP1uiv(int p0, int p1, long p2) {
        GL33.nglMultiTexCoordP1uiv(p0, p1, p2);
        LabyDebugContext.glError("nglMultiTexCoordP1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glMultiTexCoordP1uiv(int p0, int p1, IntBuffer p2) {
        GL33.glMultiTexCoordP1uiv(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglMultiTexCoordP2uiv(int p0, int p1, long p2) {
        GL33.nglMultiTexCoordP2uiv(p0, p1, p2);
        LabyDebugContext.glError("nglMultiTexCoordP2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glMultiTexCoordP2uiv(int p0, int p1, IntBuffer p2) {
        GL33.glMultiTexCoordP2uiv(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglMultiTexCoordP3uiv(int p0, int p1, long p2) {
        GL33.nglMultiTexCoordP3uiv(p0, p1, p2);
        LabyDebugContext.glError("nglMultiTexCoordP3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glMultiTexCoordP3uiv(int p0, int p1, IntBuffer p2) {
        GL33.glMultiTexCoordP3uiv(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglMultiTexCoordP4uiv(int p0, int p1, long p2) {
        GL33.nglMultiTexCoordP4uiv(p0, p1, p2);
        LabyDebugContext.glError("nglMultiTexCoordP4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glMultiTexCoordP4uiv(int p0, int p1, IntBuffer p2) {
        GL33.glMultiTexCoordP4uiv(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glNormalP3ui(int p0, int p1) {
        GL33.glNormalP3ui(p0, p1);
        LabyDebugContext.glError("glNormalP3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglNormalP3uiv(int p0, long p1) {
        GL33.nglNormalP3uiv(p0, p1);
        LabyDebugContext.glError("nglNormalP3uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glNormalP3uiv(int p0, IntBuffer p1) {
        GL33.glNormalP3uiv(p0, p1);
        LabyDebugContext.glError("glNormalP3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glColorP3ui(int p0, int p1) {
        GL33.glColorP3ui(p0, p1);
        LabyDebugContext.glError("glColorP3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glColorP4ui(int p0, int p1) {
        GL33.glColorP4ui(p0, p1);
        LabyDebugContext.glError("glColorP4ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglColorP3uiv(int p0, long p1) {
        GL33.nglColorP3uiv(p0, p1);
        LabyDebugContext.glError("nglColorP3uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glColorP3uiv(int p0, IntBuffer p1) {
        GL33.glColorP3uiv(p0, p1);
        LabyDebugContext.glError("glColorP3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglColorP4uiv(int p0, long p1) {
        GL33.nglColorP4uiv(p0, p1);
        LabyDebugContext.glError("nglColorP4uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glColorP4uiv(int p0, IntBuffer p1) {
        GL33.glColorP4uiv(p0, p1);
        LabyDebugContext.glError("glColorP4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glSecondaryColorP3ui(int p0, int p1) {
        GL33.glSecondaryColorP3ui(p0, p1);
        LabyDebugContext.glError("glSecondaryColorP3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglSecondaryColorP3uiv(int p0, long p1) {
        GL33.nglSecondaryColorP3uiv(p0, p1);
        LabyDebugContext.glError("nglSecondaryColorP3uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glSecondaryColorP3uiv(int p0, IntBuffer p1) {
        GL33.glSecondaryColorP3uiv(p0, p1);
        LabyDebugContext.glError("glSecondaryColorP3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribP1ui(int p0, int p1, boolean p2, int p3) {
        GL33.glVertexAttribP1ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP1ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribP2ui(int p0, int p1, boolean p2, int p3) {
        GL33.glVertexAttribP2ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP2ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribP3ui(int p0, int p1, boolean p2, int p3) {
        GL33.glVertexAttribP3ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribP4ui(int p0, int p1, boolean p2, int p3) {
        GL33.glVertexAttribP4ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP4ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglVertexAttribP1uiv(int p0, int p1, boolean p2, long p3) {
        GL33.nglVertexAttribP1uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglVertexAttribP1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glVertexAttribP1uiv(int p0, int p1, boolean p2, IntBuffer p3) {
        GL33.glVertexAttribP1uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglVertexAttribP2uiv(int p0, int p1, boolean p2, long p3) {
        GL33.nglVertexAttribP2uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglVertexAttribP2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glVertexAttribP2uiv(int p0, int p1, boolean p2, IntBuffer p3) {
        GL33.glVertexAttribP2uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglVertexAttribP3uiv(int p0, int p1, boolean p2, long p3) {
        GL33.nglVertexAttribP3uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglVertexAttribP3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glVertexAttribP3uiv(int p0, int p1, boolean p2, IntBuffer p3) {
        GL33.glVertexAttribP3uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglVertexAttribP4uiv(int p0, int p1, boolean p2, long p3) {
        GL33.nglVertexAttribP4uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglVertexAttribP4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glVertexAttribP4uiv(int p0, int p1, boolean p2, IntBuffer p3) {
        GL33.glVertexAttribP4uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glGenSamplers(int[] p0) {
        GL33.glGenSamplers(p0);
        LabyDebugContext.glError("glGenSamplers", "p0", p0);
    }

    public static void glDeleteSamplers(int[] p0) {
        GL33.glDeleteSamplers(p0);
        LabyDebugContext.glError("glDeleteSamplers", "p0", p0);
    }

    public static void glSamplerParameteriv(int p0, int p1, int[] p2) {
        GL33.glSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSamplerParameterfv(int p0, int p1, float[] p2) {
        GL33.glSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSamplerParameterIiv(int p0, int p1, int[] p2) {
        GL33.glSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSamplerParameterIuiv(int p0, int p1, int[] p2) {
        GL33.glSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameteriv(int p0, int p1, int[] p2) {
        GL33.glGetSamplerParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameterfv(int p0, int p1, float[] p2) {
        GL33.glGetSamplerParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameterIiv(int p0, int p1, int[] p2) {
        GL33.glGetSamplerParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSamplerParameterIuiv(int p0, int p1, int[] p2) {
        GL33.glGetSamplerParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetSamplerParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjecti64v(int p0, int p1, long[] p2) {
        GL33.glGetQueryObjecti64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectui64v(int p0, int p1, long[] p2) {
        GL33.glGetQueryObjectui64v(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glVertexP2uiv(int p0, int[] p1) {
        GL33.glVertexP2uiv(p0, p1);
        LabyDebugContext.glError("glVertexP2uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexP3uiv(int p0, int[] p1) {
        GL33.glVertexP3uiv(p0, p1);
        LabyDebugContext.glError("glVertexP3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexP4uiv(int p0, int[] p1) {
        GL33.glVertexP4uiv(p0, p1);
        LabyDebugContext.glError("glVertexP4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glTexCoordP1uiv(int p0, int[] p1) {
        GL33.glTexCoordP1uiv(p0, p1);
        LabyDebugContext.glError("glTexCoordP1uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glTexCoordP2uiv(int p0, int[] p1) {
        GL33.glTexCoordP2uiv(p0, p1);
        LabyDebugContext.glError("glTexCoordP2uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glTexCoordP3uiv(int p0, int[] p1) {
        GL33.glTexCoordP3uiv(p0, p1);
        LabyDebugContext.glError("glTexCoordP3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glTexCoordP4uiv(int p0, int[] p1) {
        GL33.glTexCoordP4uiv(p0, p1);
        LabyDebugContext.glError("glTexCoordP4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMultiTexCoordP1uiv(int p0, int p1, int[] p2) {
        GL33.glMultiTexCoordP1uiv(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMultiTexCoordP2uiv(int p0, int p1, int[] p2) {
        GL33.glMultiTexCoordP2uiv(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMultiTexCoordP3uiv(int p0, int p1, int[] p2) {
        GL33.glMultiTexCoordP3uiv(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMultiTexCoordP4uiv(int p0, int p1, int[] p2) {
        GL33.glMultiTexCoordP4uiv(p0, p1, p2);
        LabyDebugContext.glError("glMultiTexCoordP4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glNormalP3uiv(int p0, int[] p1) {
        GL33.glNormalP3uiv(p0, p1);
        LabyDebugContext.glError("glNormalP3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glColorP3uiv(int p0, int[] p1) {
        GL33.glColorP3uiv(p0, p1);
        LabyDebugContext.glError("glColorP3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glColorP4uiv(int p0, int[] p1) {
        GL33.glColorP4uiv(p0, p1);
        LabyDebugContext.glError("glColorP4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glSecondaryColorP3uiv(int p0, int[] p1) {
        GL33.glSecondaryColorP3uiv(p0, p1);
        LabyDebugContext.glError("glSecondaryColorP3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribP1uiv(int p0, int p1, boolean p2, int[] p3) {
        GL33.glVertexAttribP1uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glVertexAttribP2uiv(int p0, int p1, boolean p2, int[] p3) {
        GL33.glVertexAttribP2uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glVertexAttribP3uiv(int p0, int p1, boolean p2, int[] p3) {
        GL33.glVertexAttribP3uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glVertexAttribP4uiv(int p0, int p1, boolean p2, int[] p3) {
        GL33.glVertexAttribP4uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribP4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }
}

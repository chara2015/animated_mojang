package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL30;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL30ErrorContext.class */
public final class GL30ErrorContext {
    public static long nglGetStringi(int p0, int p1) {
        long returnType = GL30.nglGetStringi(p0, p1);
        LabyDebugContext.glError("nglGetStringi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glGetStringi(int p0, int p1) {
        String returnType = GL30.glGetStringi(p0, p1);
        LabyDebugContext.glError("glGetStringi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglClearBufferiv(int p0, int p1, long p2) {
        GL30.nglClearBufferiv(p0, p1, p2);
        LabyDebugContext.glError("nglClearBufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glClearBufferiv(int p0, int p1, IntBuffer p2) {
        GL30.glClearBufferiv(p0, p1, p2);
        LabyDebugContext.glError("glClearBufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglClearBufferuiv(int p0, int p1, long p2) {
        GL30.nglClearBufferuiv(p0, p1, p2);
        LabyDebugContext.glError("nglClearBufferuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glClearBufferuiv(int p0, int p1, IntBuffer p2) {
        GL30.glClearBufferuiv(p0, p1, p2);
        LabyDebugContext.glError("glClearBufferuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglClearBufferfv(int p0, int p1, long p2) {
        GL30.nglClearBufferfv(p0, p1, p2);
        LabyDebugContext.glError("nglClearBufferfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glClearBufferfv(int p0, int p1, FloatBuffer p2) {
        GL30.glClearBufferfv(p0, p1, p2);
        LabyDebugContext.glError("glClearBufferfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glClearBufferfi(int p0, int p1, float p2, int p3) {
        GL30.glClearBufferfi(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearBufferfi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribI1i(int p0, int p1) {
        GL30.glVertexAttribI1i(p0, p1);
        LabyDebugContext.glError("glVertexAttribI1i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexAttribI2i(int p0, int p1, int p2) {
        GL30.glVertexAttribI2i(p0, p1, p2);
        LabyDebugContext.glError("glVertexAttribI2i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glVertexAttribI3i(int p0, int p1, int p2, int p3) {
        GL30.glVertexAttribI3i(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribI3i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribI4i(int p0, int p1, int p2, int p3, int p4) {
        GL30.glVertexAttribI4i(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribI4i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glVertexAttribI1ui(int p0, int p1) {
        GL30.glVertexAttribI1ui(p0, p1);
        LabyDebugContext.glError("glVertexAttribI1ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexAttribI2ui(int p0, int p1, int p2) {
        GL30.glVertexAttribI2ui(p0, p1, p2);
        LabyDebugContext.glError("glVertexAttribI2ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glVertexAttribI3ui(int p0, int p1, int p2, int p3) {
        GL30.glVertexAttribI3ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribI3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribI4ui(int p0, int p1, int p2, int p3, int p4) {
        GL30.glVertexAttribI4ui(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribI4ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglVertexAttribI1iv(int p0, long p1) {
        GL30.nglVertexAttribI1iv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI1iv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI1iv(int p0, IntBuffer p1) {
        GL30.glVertexAttribI1iv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI1iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribI2iv(int p0, long p1) {
        GL30.nglVertexAttribI2iv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI2iv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI2iv(int p0, IntBuffer p1) {
        GL30.glVertexAttribI2iv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI2iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribI3iv(int p0, long p1) {
        GL30.nglVertexAttribI3iv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI3iv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI3iv(int p0, IntBuffer p1) {
        GL30.glVertexAttribI3iv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI3iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribI4iv(int p0, long p1) {
        GL30.nglVertexAttribI4iv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI4iv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI4iv(int p0, IntBuffer p1) {
        GL30.glVertexAttribI4iv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI4iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribI1uiv(int p0, long p1) {
        GL30.nglVertexAttribI1uiv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI1uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI1uiv(int p0, IntBuffer p1) {
        GL30.glVertexAttribI1uiv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI1uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribI2uiv(int p0, long p1) {
        GL30.nglVertexAttribI2uiv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI2uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI2uiv(int p0, IntBuffer p1) {
        GL30.glVertexAttribI2uiv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI2uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribI3uiv(int p0, long p1) {
        GL30.nglVertexAttribI3uiv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI3uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI3uiv(int p0, IntBuffer p1) {
        GL30.glVertexAttribI3uiv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribI4uiv(int p0, long p1) {
        GL30.nglVertexAttribI4uiv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI4uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI4uiv(int p0, IntBuffer p1) {
        GL30.glVertexAttribI4uiv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribI4bv(int p0, long p1) {
        GL30.nglVertexAttribI4bv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI4bv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI4bv(int p0, ByteBuffer p1) {
        GL30.glVertexAttribI4bv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI4bv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribI4sv(int p0, long p1) {
        GL30.nglVertexAttribI4sv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI4sv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI4sv(int p0, ShortBuffer p1) {
        GL30.glVertexAttribI4sv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI4sv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribI4ubv(int p0, long p1) {
        GL30.nglVertexAttribI4ubv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI4ubv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI4ubv(int p0, ByteBuffer p1) {
        GL30.glVertexAttribI4ubv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI4ubv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribI4usv(int p0, long p1) {
        GL30.nglVertexAttribI4usv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribI4usv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribI4usv(int p0, ShortBuffer p1) {
        GL30.glVertexAttribI4usv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI4usv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribIPointer(int p0, int p1, int p2, int p3, long p4) {
        GL30.nglVertexAttribIPointer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglVertexAttribIPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glVertexAttribIPointer(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        GL30.glVertexAttribIPointer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribIPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glVertexAttribIPointer(int p0, int p1, int p2, int p3, long p4) {
        GL30.glVertexAttribIPointer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribIPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glVertexAttribIPointer(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        GL30.glVertexAttribIPointer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribIPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glVertexAttribIPointer(int p0, int p1, int p2, int p3, IntBuffer p4) {
        GL30.glVertexAttribIPointer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribIPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglGetVertexAttribIiv(int p0, int p1, long p2) {
        GL30.nglGetVertexAttribIiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribIiv(int p0, int p1, IntBuffer p2) {
        GL30.glGetVertexAttribIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetVertexAttribIi(int p0, int p1) {
        int returnType = GL30.glGetVertexAttribIi(p0, p1);
        LabyDebugContext.glError("glGetVertexAttribIi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetVertexAttribIuiv(int p0, int p1, long p2) {
        GL30.nglGetVertexAttribIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribIuiv(int p0, int p1, IntBuffer p2) {
        GL30.glGetVertexAttribIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetVertexAttribIui(int p0, int p1) {
        int returnType = GL30.glGetVertexAttribIui(p0, p1);
        LabyDebugContext.glError("glGetVertexAttribIui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glUniform1ui(int p0, int p1) {
        GL30.glUniform1ui(p0, p1);
        LabyDebugContext.glError("glUniform1ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glUniform2ui(int p0, int p1, int p2) {
        GL30.glUniform2ui(p0, p1, p2);
        LabyDebugContext.glError("glUniform2ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glUniform3ui(int p0, int p1, int p2, int p3) {
        GL30.glUniform3ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glUniform3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glUniform4ui(int p0, int p1, int p2, int p3, int p4) {
        GL30.glUniform4ui(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glUniform4ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglUniform1uiv(int p0, int p1, long p2) {
        GL30.nglUniform1uiv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform1uiv(int p0, IntBuffer p1) {
        GL30.glUniform1uiv(p0, p1);
        LabyDebugContext.glError("glUniform1uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform2uiv(int p0, int p1, long p2) {
        GL30.nglUniform2uiv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform2uiv(int p0, IntBuffer p1) {
        GL30.glUniform2uiv(p0, p1);
        LabyDebugContext.glError("glUniform2uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform3uiv(int p0, int p1, long p2) {
        GL30.nglUniform3uiv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform3uiv(int p0, IntBuffer p1) {
        GL30.glUniform3uiv(p0, p1);
        LabyDebugContext.glError("glUniform3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform4uiv(int p0, int p1, long p2) {
        GL30.nglUniform4uiv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform4uiv(int p0, IntBuffer p1) {
        GL30.glUniform4uiv(p0, p1);
        LabyDebugContext.glError("glUniform4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglGetUniformuiv(int p0, int p1, long p2) {
        GL30.nglGetUniformuiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetUniformuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetUniformuiv(int p0, int p1, IntBuffer p2) {
        GL30.glGetUniformuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetUniformui(int p0, int p1) {
        int returnType = GL30.glGetUniformui(p0, p1);
        LabyDebugContext.glError("glGetUniformui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglBindFragDataLocation(int p0, int p1, long p2) {
        GL30.nglBindFragDataLocation(p0, p1, p2);
        LabyDebugContext.glError("nglBindFragDataLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glBindFragDataLocation(int p0, int p1, ByteBuffer p2) {
        GL30.glBindFragDataLocation(p0, p1, p2);
        LabyDebugContext.glError("glBindFragDataLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glBindFragDataLocation(int p0, int p1, CharSequence p2) {
        GL30.glBindFragDataLocation(p0, p1, p2);
        LabyDebugContext.glError("glBindFragDataLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int nglGetFragDataLocation(int p0, long p1) {
        int returnType = GL30.nglGetFragDataLocation(p0, p1);
        LabyDebugContext.glError("nglGetFragDataLocation", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glGetFragDataLocation(int p0, ByteBuffer p1) {
        int returnType = GL30.glGetFragDataLocation(p0, p1);
        LabyDebugContext.glError("glGetFragDataLocation", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glGetFragDataLocation(int p0, CharSequence p1) {
        int returnType = GL30.glGetFragDataLocation(p0, p1);
        LabyDebugContext.glError("glGetFragDataLocation", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void glBeginConditionalRender(int p0, int p1) {
        GL30.glBeginConditionalRender(p0, p1);
        LabyDebugContext.glError("glBeginConditionalRender", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glEndConditionalRender() {
        GL30.glEndConditionalRender();
        LabyDebugContext.glError("glEndConditionalRender", new Object[0]);
    }

    public static long nglMapBufferRange(int p0, long p1, long p2, int p3) {
        long returnType = GL30.nglMapBufferRange(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMapBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static ByteBuffer glMapBufferRange(int p0, long p1, long p2, int p3) {
        ByteBuffer returnType = GL30.glMapBufferRange(p0, p1, p2, p3);
        LabyDebugContext.glError("glMapBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static ByteBuffer glMapBufferRange(int p0, long p1, long p2, int p3, ByteBuffer p4) {
        ByteBuffer returnType = GL30.glMapBufferRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMapBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static void glFlushMappedBufferRange(int p0, long p1, long p2) {
        GL30.glFlushMappedBufferRange(p0, p1, p2);
        LabyDebugContext.glError("glFlushMappedBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glClampColor(int p0, int p1) {
        GL30.glClampColor(p0, p1);
        LabyDebugContext.glError("glClampColor", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static boolean glIsRenderbuffer(int p0) {
        boolean returnType = GL30.glIsRenderbuffer(p0);
        LabyDebugContext.glError("glIsRenderbuffer", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glBindRenderbuffer(int p0, int p1) {
        GL30.glBindRenderbuffer(p0, p1);
        LabyDebugContext.glError("glBindRenderbuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglDeleteRenderbuffers(int p0, long p1) {
        GL30.nglDeleteRenderbuffers(p0, p1);
        LabyDebugContext.glError("nglDeleteRenderbuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteRenderbuffers(IntBuffer p0) {
        GL30.glDeleteRenderbuffers(p0);
        LabyDebugContext.glError("glDeleteRenderbuffers", "p0", p0);
    }

    public static void glDeleteRenderbuffers(int p0) {
        GL30.glDeleteRenderbuffers(p0);
        LabyDebugContext.glError("glDeleteRenderbuffers", "p0", Integer.valueOf(p0));
    }

    public static void nglGenRenderbuffers(int p0, long p1) {
        GL30.nglGenRenderbuffers(p0, p1);
        LabyDebugContext.glError("nglGenRenderbuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenRenderbuffers(IntBuffer p0) {
        GL30.glGenRenderbuffers(p0);
        LabyDebugContext.glError("glGenRenderbuffers", "p0", p0);
    }

    public static int glGenRenderbuffers() {
        int returnType = GL30.glGenRenderbuffers();
        LabyDebugContext.glError("glGenRenderbuffers", new Object[0]);
        return returnType;
    }

    public static void glRenderbufferStorage(int p0, int p1, int p2, int p3) {
        GL30.glRenderbufferStorage(p0, p1, p2, p3);
        LabyDebugContext.glError("glRenderbufferStorage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glRenderbufferStorageMultisample(int p0, int p1, int p2, int p3, int p4) {
        GL30.glRenderbufferStorageMultisample(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glRenderbufferStorageMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglGetRenderbufferParameteriv(int p0, int p1, long p2) {
        GL30.nglGetRenderbufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetRenderbufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetRenderbufferParameteriv(int p0, int p1, IntBuffer p2) {
        GL30.glGetRenderbufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetRenderbufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetRenderbufferParameteri(int p0, int p1) {
        int returnType = GL30.glGetRenderbufferParameteri(p0, p1);
        LabyDebugContext.glError("glGetRenderbufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static boolean glIsFramebuffer(int p0) {
        boolean returnType = GL30.glIsFramebuffer(p0);
        LabyDebugContext.glError("glIsFramebuffer", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glBindFramebuffer(int p0, int p1) {
        GL30.glBindFramebuffer(p0, p1);
        LabyDebugContext.glError("glBindFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglDeleteFramebuffers(int p0, long p1) {
        GL30.nglDeleteFramebuffers(p0, p1);
        LabyDebugContext.glError("nglDeleteFramebuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteFramebuffers(IntBuffer p0) {
        GL30.glDeleteFramebuffers(p0);
        LabyDebugContext.glError("glDeleteFramebuffers", "p0", p0);
    }

    public static void glDeleteFramebuffers(int p0) {
        GL30.glDeleteFramebuffers(p0);
        LabyDebugContext.glError("glDeleteFramebuffers", "p0", Integer.valueOf(p0));
    }

    public static void nglGenFramebuffers(int p0, long p1) {
        GL30.nglGenFramebuffers(p0, p1);
        LabyDebugContext.glError("nglGenFramebuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenFramebuffers(IntBuffer p0) {
        GL30.glGenFramebuffers(p0);
        LabyDebugContext.glError("glGenFramebuffers", "p0", p0);
    }

    public static int glGenFramebuffers() {
        int returnType = GL30.glGenFramebuffers();
        LabyDebugContext.glError("glGenFramebuffers", new Object[0]);
        return returnType;
    }

    public static int glCheckFramebufferStatus(int p0) {
        int returnType = GL30.glCheckFramebufferStatus(p0);
        LabyDebugContext.glError("glCheckFramebufferStatus", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glFramebufferTexture1D(int p0, int p1, int p2, int p3, int p4) {
        GL30.glFramebufferTexture1D(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glFramebufferTexture1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glFramebufferTexture2D(int p0, int p1, int p2, int p3, int p4) {
        GL30.glFramebufferTexture2D(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glFramebufferTexture2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glFramebufferTexture3D(int p0, int p1, int p2, int p3, int p4, int p5) {
        GL30.glFramebufferTexture3D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glFramebufferTexture3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glFramebufferTextureLayer(int p0, int p1, int p2, int p3, int p4) {
        GL30.glFramebufferTextureLayer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glFramebufferTextureLayer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glFramebufferRenderbuffer(int p0, int p1, int p2, int p3) {
        GL30.glFramebufferRenderbuffer(p0, p1, p2, p3);
        LabyDebugContext.glError("glFramebufferRenderbuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglGetFramebufferAttachmentParameteriv(int p0, int p1, int p2, long p3) {
        GL30.nglGetFramebufferAttachmentParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetFramebufferAttachmentParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetFramebufferAttachmentParameteriv(int p0, int p1, int p2, IntBuffer p3) {
        GL30.glGetFramebufferAttachmentParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetFramebufferAttachmentParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetFramebufferAttachmentParameteri(int p0, int p1, int p2) {
        int returnType = GL30.glGetFramebufferAttachmentParameteri(p0, p1, p2);
        LabyDebugContext.glError("glGetFramebufferAttachmentParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void glBlitFramebuffer(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9) {
        GL30.glBlitFramebuffer(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("glBlitFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9));
    }

    public static void glGenerateMipmap(int p0) {
        GL30.glGenerateMipmap(p0);
        LabyDebugContext.glError("glGenerateMipmap", "p0", Integer.valueOf(p0));
    }

    public static void nglTexParameterIiv(int p0, int p1, long p2) {
        GL30.nglTexParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglTexParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTexParameterIiv(int p0, int p1, IntBuffer p2) {
        GL30.glTexParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexParameterIi(int p0, int p1, int p2) {
        GL30.glTexParameterIi(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterIi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTexParameterIuiv(int p0, int p1, long p2) {
        GL30.nglTexParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglTexParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTexParameterIuiv(int p0, int p1, IntBuffer p2) {
        GL30.glTexParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexParameterIui(int p0, int p1, int p2) {
        GL30.glTexParameterIui(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterIui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglGetTexParameterIiv(int p0, int p1, long p2) {
        GL30.nglGetTexParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTexParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTexParameterIiv(int p0, int p1, IntBuffer p2) {
        GL30.glGetTexParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTexParameterIi(int p0, int p1) {
        int returnType = GL30.glGetTexParameterIi(p0, p1);
        LabyDebugContext.glError("glGetTexParameterIi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTexParameterIuiv(int p0, int p1, long p2) {
        GL30.nglGetTexParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTexParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTexParameterIuiv(int p0, int p1, IntBuffer p2) {
        GL30.glGetTexParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTexParameterIui(int p0, int p1) {
        int returnType = GL30.glGetTexParameterIui(p0, p1);
        LabyDebugContext.glError("glGetTexParameterIui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glColorMaski(int p0, boolean p1, boolean p2, boolean p3, boolean p4) {
        GL30.glColorMaski(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glColorMaski", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Boolean.valueOf(p4));
    }

    public static void nglGetBooleani_v(int p0, int p1, long p2) {
        GL30.nglGetBooleani_v(p0, p1, p2);
        LabyDebugContext.glError("nglGetBooleani_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetBooleani_v(int p0, int p1, ByteBuffer p2) {
        GL30.glGetBooleani_v(p0, p1, p2);
        LabyDebugContext.glError("glGetBooleani_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static boolean glGetBooleani(int p0, int p1) {
        boolean returnType = GL30.glGetBooleani(p0, p1);
        LabyDebugContext.glError("glGetBooleani", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetIntegeri_v(int p0, int p1, long p2) {
        GL30.nglGetIntegeri_v(p0, p1, p2);
        LabyDebugContext.glError("nglGetIntegeri_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetIntegeri_v(int p0, int p1, IntBuffer p2) {
        GL30.glGetIntegeri_v(p0, p1, p2);
        LabyDebugContext.glError("glGetIntegeri_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetIntegeri(int p0, int p1) {
        int returnType = GL30.glGetIntegeri(p0, p1);
        LabyDebugContext.glError("glGetIntegeri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glEnablei(int p0, int p1) {
        GL30.glEnablei(p0, p1);
        LabyDebugContext.glError("glEnablei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glDisablei(int p0, int p1) {
        GL30.glDisablei(p0, p1);
        LabyDebugContext.glError("glDisablei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static boolean glIsEnabledi(int p0, int p1) {
        boolean returnType = GL30.glIsEnabledi(p0, p1);
        LabyDebugContext.glError("glIsEnabledi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glBindBufferRange(int p0, int p1, int p2, long p3, long p4) {
        GL30.glBindBufferRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glBindBufferRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glBindBufferBase(int p0, int p1, int p2) {
        GL30.glBindBufferBase(p0, p1, p2);
        LabyDebugContext.glError("glBindBufferBase", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBeginTransformFeedback(int p0) {
        GL30.glBeginTransformFeedback(p0);
        LabyDebugContext.glError("glBeginTransformFeedback", "p0", Integer.valueOf(p0));
    }

    public static void glEndTransformFeedback() {
        GL30.glEndTransformFeedback();
        LabyDebugContext.glError("glEndTransformFeedback", new Object[0]);
    }

    public static void nglTransformFeedbackVaryings(int p0, int p1, long p2, int p3) {
        GL30.nglTransformFeedbackVaryings(p0, p1, p2, p3);
        LabyDebugContext.glError("nglTransformFeedbackVaryings", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glTransformFeedbackVaryings(int p0, PointerBuffer p1, int p2) {
        GL30.glTransformFeedbackVaryings(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackVaryings", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glTransformFeedbackVaryings(int p0, CharSequence[] p1, int p2) {
        GL30.glTransformFeedbackVaryings(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackVaryings", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glTransformFeedbackVaryings(int p0, CharSequence p1, int p2) {
        GL30.glTransformFeedbackVaryings(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackVaryings", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglGetTransformFeedbackVarying(int p0, int p1, int p2, long p3, long p4, long p5, long p6) {
        GL30.nglGetTransformFeedbackVarying(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglGetTransformFeedbackVarying", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glGetTransformFeedbackVarying(int p0, int p1, IntBuffer p2, IntBuffer p3, IntBuffer p4, ByteBuffer p5) {
        GL30.glGetTransformFeedbackVarying(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetTransformFeedbackVarying", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static String glGetTransformFeedbackVarying(int p0, int p1, int p2, IntBuffer p3, IntBuffer p4) {
        String returnType = GL30.glGetTransformFeedbackVarying(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTransformFeedbackVarying", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
        return returnType;
    }

    public static String glGetTransformFeedbackVarying(int p0, int p1, IntBuffer p2, IntBuffer p3) {
        String returnType = GL30.glGetTransformFeedbackVarying(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTransformFeedbackVarying", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
        return returnType;
    }

    public static void glBindVertexArray(int p0) {
        GL30.glBindVertexArray(p0);
        LabyDebugContext.glError("glBindVertexArray", "p0", Integer.valueOf(p0));
    }

    public static void nglDeleteVertexArrays(int p0, long p1) {
        GL30.nglDeleteVertexArrays(p0, p1);
        LabyDebugContext.glError("nglDeleteVertexArrays", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteVertexArrays(IntBuffer p0) {
        GL30.glDeleteVertexArrays(p0);
        LabyDebugContext.glError("glDeleteVertexArrays", "p0", p0);
    }

    public static void glDeleteVertexArrays(int p0) {
        GL30.glDeleteVertexArrays(p0);
        LabyDebugContext.glError("glDeleteVertexArrays", "p0", Integer.valueOf(p0));
    }

    public static void nglGenVertexArrays(int p0, long p1) {
        GL30.nglGenVertexArrays(p0, p1);
        LabyDebugContext.glError("nglGenVertexArrays", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenVertexArrays(IntBuffer p0) {
        GL30.glGenVertexArrays(p0);
        LabyDebugContext.glError("glGenVertexArrays", "p0", p0);
    }

    public static int glGenVertexArrays() {
        int returnType = GL30.glGenVertexArrays();
        LabyDebugContext.glError("glGenVertexArrays", new Object[0]);
        return returnType;
    }

    public static boolean glIsVertexArray(int p0) {
        boolean returnType = GL30.glIsVertexArray(p0);
        LabyDebugContext.glError("glIsVertexArray", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glClearBufferiv(int p0, int p1, int[] p2) {
        GL30.glClearBufferiv(p0, p1, p2);
        LabyDebugContext.glError("glClearBufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glClearBufferuiv(int p0, int p1, int[] p2) {
        GL30.glClearBufferuiv(p0, p1, p2);
        LabyDebugContext.glError("glClearBufferuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glClearBufferfv(int p0, int p1, float[] p2) {
        GL30.glClearBufferfv(p0, p1, p2);
        LabyDebugContext.glError("glClearBufferfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glVertexAttribI1iv(int p0, int[] p1) {
        GL30.glVertexAttribI1iv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI1iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribI2iv(int p0, int[] p1) {
        GL30.glVertexAttribI2iv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI2iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribI3iv(int p0, int[] p1) {
        GL30.glVertexAttribI3iv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI3iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribI4iv(int p0, int[] p1) {
        GL30.glVertexAttribI4iv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI4iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribI1uiv(int p0, int[] p1) {
        GL30.glVertexAttribI1uiv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI1uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribI2uiv(int p0, int[] p1) {
        GL30.glVertexAttribI2uiv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI2uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribI3uiv(int p0, int[] p1) {
        GL30.glVertexAttribI3uiv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribI4uiv(int p0, int[] p1) {
        GL30.glVertexAttribI4uiv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribI4sv(int p0, short[] p1) {
        GL30.glVertexAttribI4sv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI4sv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribI4usv(int p0, short[] p1) {
        GL30.glVertexAttribI4usv(p0, p1);
        LabyDebugContext.glError("glVertexAttribI4usv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetVertexAttribIiv(int p0, int p1, int[] p2) {
        GL30.glGetVertexAttribIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetVertexAttribIuiv(int p0, int p1, int[] p2) {
        GL30.glGetVertexAttribIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glUniform1uiv(int p0, int[] p1) {
        GL30.glUniform1uiv(p0, p1);
        LabyDebugContext.glError("glUniform1uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform2uiv(int p0, int[] p1) {
        GL30.glUniform2uiv(p0, p1);
        LabyDebugContext.glError("glUniform2uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform3uiv(int p0, int[] p1) {
        GL30.glUniform3uiv(p0, p1);
        LabyDebugContext.glError("glUniform3uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform4uiv(int p0, int[] p1) {
        GL30.glUniform4uiv(p0, p1);
        LabyDebugContext.glError("glUniform4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetUniformuiv(int p0, int p1, int[] p2) {
        GL30.glGetUniformuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glDeleteRenderbuffers(int[] p0) {
        GL30.glDeleteRenderbuffers(p0);
        LabyDebugContext.glError("glDeleteRenderbuffers", "p0", p0);
    }

    public static void glGenRenderbuffers(int[] p0) {
        GL30.glGenRenderbuffers(p0);
        LabyDebugContext.glError("glGenRenderbuffers", "p0", p0);
    }

    public static void glGetRenderbufferParameteriv(int p0, int p1, int[] p2) {
        GL30.glGetRenderbufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetRenderbufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glDeleteFramebuffers(int[] p0) {
        GL30.glDeleteFramebuffers(p0);
        LabyDebugContext.glError("glDeleteFramebuffers", "p0", p0);
    }

    public static void glGenFramebuffers(int[] p0) {
        GL30.glGenFramebuffers(p0);
        LabyDebugContext.glError("glGenFramebuffers", "p0", p0);
    }

    public static void glGetFramebufferAttachmentParameteriv(int p0, int p1, int p2, int[] p3) {
        GL30.glGetFramebufferAttachmentParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetFramebufferAttachmentParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glTexParameterIiv(int p0, int p1, int[] p2) {
        GL30.glTexParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexParameterIuiv(int p0, int p1, int[] p2) {
        GL30.glTexParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTexParameterIiv(int p0, int p1, int[] p2) {
        GL30.glGetTexParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTexParameterIuiv(int p0, int p1, int[] p2) {
        GL30.glGetTexParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetIntegeri_v(int p0, int p1, int[] p2) {
        GL30.glGetIntegeri_v(p0, p1, p2);
        LabyDebugContext.glError("glGetIntegeri_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTransformFeedbackVarying(int p0, int p1, int[] p2, int[] p3, int[] p4, ByteBuffer p5) {
        GL30.glGetTransformFeedbackVarying(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetTransformFeedbackVarying", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static void glDeleteVertexArrays(int[] p0) {
        GL30.glDeleteVertexArrays(p0);
        LabyDebugContext.glError("glDeleteVertexArrays", "p0", p0);
    }

    public static void glGenVertexArrays(int[] p0) {
        GL30.glGenVertexArrays(p0);
        LabyDebugContext.glError("glGenVertexArrays", "p0", p0);
    }
}

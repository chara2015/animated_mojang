package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL45C;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL45CErrorContext.class */
public final class GL45CErrorContext {
    public static void glClipControl(int p0, int p1) {
        GL45C.glClipControl(p0, p1);
        LabyDebugContext.glError("glClipControl", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglCreateTransformFeedbacks(int p0, long p1) {
        GL45C.nglCreateTransformFeedbacks(p0, p1);
        LabyDebugContext.glError("nglCreateTransformFeedbacks", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateTransformFeedbacks(IntBuffer p0) {
        GL45C.glCreateTransformFeedbacks(p0);
        LabyDebugContext.glError("glCreateTransformFeedbacks", "p0", p0);
    }

    public static int glCreateTransformFeedbacks() {
        int returnType = GL45C.glCreateTransformFeedbacks();
        LabyDebugContext.glError("glCreateTransformFeedbacks", new Object[0]);
        return returnType;
    }

    public static void glTransformFeedbackBufferBase(int p0, int p1, int p2) {
        GL45C.glTransformFeedbackBufferBase(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackBufferBase", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glTransformFeedbackBufferRange(int p0, int p1, int p2, long p3, long p4) {
        GL45C.glTransformFeedbackBufferRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTransformFeedbackBufferRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void nglGetTransformFeedbackiv(int p0, int p1, long p2) {
        GL45C.nglGetTransformFeedbackiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTransformFeedbackiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTransformFeedbackiv(int p0, int p1, IntBuffer p2) {
        GL45C.glGetTransformFeedbackiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTransformFeedbackiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTransformFeedbacki(int p0, int p1) {
        int returnType = GL45C.glGetTransformFeedbacki(p0, p1);
        LabyDebugContext.glError("glGetTransformFeedbacki", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTransformFeedbacki_v(int p0, int p1, int p2, long p3) {
        GL45C.nglGetTransformFeedbacki_v(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTransformFeedbacki_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTransformFeedbacki_v(int p0, int p1, int p2, IntBuffer p3) {
        GL45C.glGetTransformFeedbacki_v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTransformFeedbacki_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetTransformFeedbacki(int p0, int p1, int p2) {
        int returnType = GL45C.glGetTransformFeedbacki(p0, p1, p2);
        LabyDebugContext.glError("glGetTransformFeedbacki", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetTransformFeedbacki64_v(int p0, int p1, int p2, long p3) {
        GL45C.nglGetTransformFeedbacki64_v(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTransformFeedbacki64_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTransformFeedbacki64_v(int p0, int p1, int p2, LongBuffer p3) {
        GL45C.glGetTransformFeedbacki64_v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTransformFeedbacki64_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static long glGetTransformFeedbacki64(int p0, int p1, int p2) {
        long returnType = GL45C.glGetTransformFeedbacki64(p0, p1, p2);
        LabyDebugContext.glError("glGetTransformFeedbacki64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglCreateBuffers(int p0, long p1) {
        GL45C.nglCreateBuffers(p0, p1);
        LabyDebugContext.glError("nglCreateBuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateBuffers(IntBuffer p0) {
        GL45C.glCreateBuffers(p0);
        LabyDebugContext.glError("glCreateBuffers", "p0", p0);
    }

    public static int glCreateBuffers() {
        int returnType = GL45C.glCreateBuffers();
        LabyDebugContext.glError("glCreateBuffers", new Object[0]);
        return returnType;
    }

    public static void nglNamedBufferStorage(int p0, long p1, long p2, int p3) {
        GL45C.nglNamedBufferStorage(p0, p1, p2, p3);
        LabyDebugContext.glError("nglNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glNamedBufferStorage(int p0, long p1, int p2) {
        GL45C.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, ByteBuffer p1, int p2) {
        GL45C.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, ShortBuffer p1, int p2) {
        GL45C.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, IntBuffer p1, int p2) {
        GL45C.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, FloatBuffer p1, int p2) {
        GL45C.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, DoubleBuffer p1, int p2) {
        GL45C.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglNamedBufferData(int p0, long p1, long p2, int p3) {
        GL45C.nglNamedBufferData(p0, p1, p2, p3);
        LabyDebugContext.glError("nglNamedBufferData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glNamedBufferData(int p0, long p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, ByteBuffer p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, ShortBuffer p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, IntBuffer p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, LongBuffer p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, FloatBuffer p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, DoubleBuffer p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglNamedBufferSubData(int p0, long p1, long p2, long p3) {
        GL45C.nglNamedBufferSubData(p0, p1, p2, p3);
        LabyDebugContext.glError("nglNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glNamedBufferSubData(int p0, long p1, ByteBuffer p2) {
        GL45C.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, ShortBuffer p2) {
        GL45C.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, IntBuffer p2) {
        GL45C.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, LongBuffer p2) {
        GL45C.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, FloatBuffer p2) {
        GL45C.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, DoubleBuffer p2) {
        GL45C.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glCopyNamedBufferSubData(int p0, int p1, long p2, long p3, long p4) {
        GL45C.glCopyNamedBufferSubData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glCopyNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void nglClearNamedBufferData(int p0, int p1, int p2, int p3, long p4) {
        GL45C.nglClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        GL45C.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        GL45C.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, IntBuffer p4) {
        GL45C.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        GL45C.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, long p6) {
        GL45C.nglClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, ByteBuffer p6) {
        GL45C.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, ShortBuffer p6) {
        GL45C.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, IntBuffer p6) {
        GL45C.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, FloatBuffer p6) {
        GL45C.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static long nglMapNamedBuffer(int p0, int p1) {
        long returnType = GL45C.nglMapNamedBuffer(p0, p1);
        LabyDebugContext.glError("nglMapNamedBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static ByteBuffer glMapNamedBuffer(int p0, int p1) {
        ByteBuffer returnType = GL45C.glMapNamedBuffer(p0, p1);
        LabyDebugContext.glError("glMapNamedBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static ByteBuffer glMapNamedBuffer(int p0, int p1, ByteBuffer p2) {
        ByteBuffer returnType = GL45C.glMapNamedBuffer(p0, p1, p2);
        LabyDebugContext.glError("glMapNamedBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static ByteBuffer glMapNamedBuffer(int p0, int p1, long p2, ByteBuffer p3) {
        ByteBuffer returnType = GL45C.glMapNamedBuffer(p0, p1, p2, p3);
        LabyDebugContext.glError("glMapNamedBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static long nglMapNamedBufferRange(int p0, long p1, long p2, int p3) {
        long returnType = GL45C.nglMapNamedBufferRange(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMapNamedBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static ByteBuffer glMapNamedBufferRange(int p0, long p1, long p2, int p3) {
        ByteBuffer returnType = GL45C.glMapNamedBufferRange(p0, p1, p2, p3);
        LabyDebugContext.glError("glMapNamedBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static ByteBuffer glMapNamedBufferRange(int p0, long p1, long p2, int p3, ByteBuffer p4) {
        ByteBuffer returnType = GL45C.glMapNamedBufferRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMapNamedBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static boolean glUnmapNamedBuffer(int p0) {
        boolean returnType = GL45C.glUnmapNamedBuffer(p0);
        LabyDebugContext.glError("glUnmapNamedBuffer", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glFlushMappedNamedBufferRange(int p0, long p1, long p2) {
        GL45C.glFlushMappedNamedBufferRange(p0, p1, p2);
        LabyDebugContext.glError("glFlushMappedNamedBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void nglGetNamedBufferParameteriv(int p0, int p1, long p2) {
        GL45C.nglGetNamedBufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedBufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedBufferParameteriv(int p0, int p1, IntBuffer p2) {
        GL45C.glGetNamedBufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetNamedBufferParameteri(int p0, int p1) {
        int returnType = GL45C.glGetNamedBufferParameteri(p0, p1);
        LabyDebugContext.glError("glGetNamedBufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetNamedBufferParameteri64v(int p0, int p1, long p2) {
        GL45C.nglGetNamedBufferParameteri64v(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedBufferParameteri64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedBufferParameteri64v(int p0, int p1, LongBuffer p2) {
        GL45C.glGetNamedBufferParameteri64v(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferParameteri64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetNamedBufferParameteri64(int p0, int p1) {
        long returnType = GL45C.glGetNamedBufferParameteri64(p0, p1);
        LabyDebugContext.glError("glGetNamedBufferParameteri64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetNamedBufferPointerv(int p0, int p1, long p2) {
        GL45C.nglGetNamedBufferPointerv(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedBufferPointerv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedBufferPointerv(int p0, int p1, PointerBuffer p2) {
        GL45C.glGetNamedBufferPointerv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferPointerv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetNamedBufferPointer(int p0, int p1) {
        long returnType = GL45C.glGetNamedBufferPointer(p0, p1);
        LabyDebugContext.glError("glGetNamedBufferPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetNamedBufferSubData(int p0, long p1, long p2, long p3) {
        GL45C.nglGetNamedBufferSubData(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetNamedBufferSubData(int p0, long p1, ByteBuffer p2) {
        GL45C.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, ShortBuffer p2) {
        GL45C.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, IntBuffer p2) {
        GL45C.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, LongBuffer p2) {
        GL45C.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, FloatBuffer p2) {
        GL45C.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, DoubleBuffer p2) {
        GL45C.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void nglCreateFramebuffers(int p0, long p1) {
        GL45C.nglCreateFramebuffers(p0, p1);
        LabyDebugContext.glError("nglCreateFramebuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateFramebuffers(IntBuffer p0) {
        GL45C.glCreateFramebuffers(p0);
        LabyDebugContext.glError("glCreateFramebuffers", "p0", p0);
    }

    public static int glCreateFramebuffers() {
        int returnType = GL45C.glCreateFramebuffers();
        LabyDebugContext.glError("glCreateFramebuffers", new Object[0]);
        return returnType;
    }

    public static void glNamedFramebufferRenderbuffer(int p0, int p1, int p2, int p3) {
        GL45C.glNamedFramebufferRenderbuffer(p0, p1, p2, p3);
        LabyDebugContext.glError("glNamedFramebufferRenderbuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glNamedFramebufferParameteri(int p0, int p1, int p2) {
        GL45C.glNamedFramebufferParameteri(p0, p1, p2);
        LabyDebugContext.glError("glNamedFramebufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glNamedFramebufferTexture(int p0, int p1, int p2, int p3) {
        GL45C.glNamedFramebufferTexture(p0, p1, p2, p3);
        LabyDebugContext.glError("glNamedFramebufferTexture", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glNamedFramebufferTextureLayer(int p0, int p1, int p2, int p3, int p4) {
        GL45C.glNamedFramebufferTextureLayer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glNamedFramebufferTextureLayer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glNamedFramebufferDrawBuffer(int p0, int p1) {
        GL45C.glNamedFramebufferDrawBuffer(p0, p1);
        LabyDebugContext.glError("glNamedFramebufferDrawBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglNamedFramebufferDrawBuffers(int p0, int p1, long p2) {
        GL45C.nglNamedFramebufferDrawBuffers(p0, p1, p2);
        LabyDebugContext.glError("nglNamedFramebufferDrawBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glNamedFramebufferDrawBuffers(int p0, IntBuffer p1) {
        GL45C.glNamedFramebufferDrawBuffers(p0, p1);
        LabyDebugContext.glError("glNamedFramebufferDrawBuffers", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glNamedFramebufferDrawBuffers(int p0, int p1) {
        GL45C.glNamedFramebufferDrawBuffers(p0, p1);
        LabyDebugContext.glError("glNamedFramebufferDrawBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glNamedFramebufferReadBuffer(int p0, int p1) {
        GL45C.glNamedFramebufferReadBuffer(p0, p1);
        LabyDebugContext.glError("glNamedFramebufferReadBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglInvalidateNamedFramebufferData(int p0, int p1, long p2) {
        GL45C.nglInvalidateNamedFramebufferData(p0, p1, p2);
        LabyDebugContext.glError("nglInvalidateNamedFramebufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glInvalidateNamedFramebufferData(int p0, IntBuffer p1) {
        GL45C.glInvalidateNamedFramebufferData(p0, p1);
        LabyDebugContext.glError("glInvalidateNamedFramebufferData", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glInvalidateNamedFramebufferData(int p0, int p1) {
        GL45C.glInvalidateNamedFramebufferData(p0, p1);
        LabyDebugContext.glError("glInvalidateNamedFramebufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglInvalidateNamedFramebufferSubData(int p0, int p1, long p2, int p3, int p4, int p5, int p6) {
        GL45C.nglInvalidateNamedFramebufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglInvalidateNamedFramebufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glInvalidateNamedFramebufferSubData(int p0, IntBuffer p1, int p2, int p3, int p4, int p5) {
        GL45C.glInvalidateNamedFramebufferSubData(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateNamedFramebufferSubData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glInvalidateNamedFramebufferSubData(int p0, int p1, int p2, int p3, int p4, int p5) {
        GL45C.glInvalidateNamedFramebufferSubData(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateNamedFramebufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void nglClearNamedFramebufferiv(int p0, int p1, int p2, long p3) {
        GL45C.nglClearNamedFramebufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglClearNamedFramebufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glClearNamedFramebufferiv(int p0, int p1, int p2, IntBuffer p3) {
        GL45C.glClearNamedFramebufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglClearNamedFramebufferuiv(int p0, int p1, int p2, long p3) {
        GL45C.nglClearNamedFramebufferuiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglClearNamedFramebufferuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glClearNamedFramebufferuiv(int p0, int p1, int p2, IntBuffer p3) {
        GL45C.glClearNamedFramebufferuiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglClearNamedFramebufferfv(int p0, int p1, int p2, long p3) {
        GL45C.nglClearNamedFramebufferfv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglClearNamedFramebufferfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glClearNamedFramebufferfv(int p0, int p1, int p2, FloatBuffer p3) {
        GL45C.glClearNamedFramebufferfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glClearNamedFramebufferfi(int p0, int p1, int p2, float p3, int p4) {
        GL45C.glClearNamedFramebufferfi(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedFramebufferfi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Float.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glBlitNamedFramebuffer(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11) {
        GL45C.glBlitNamedFramebuffer(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
        LabyDebugContext.glError("glBlitNamedFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Integer.valueOf(p11));
    }

    public static int glCheckNamedFramebufferStatus(int p0, int p1) {
        int returnType = GL45C.glCheckNamedFramebufferStatus(p0, p1);
        LabyDebugContext.glError("glCheckNamedFramebufferStatus", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetNamedFramebufferParameteriv(int p0, int p1, long p2) {
        GL45C.nglGetNamedFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedFramebufferParameteriv(int p0, int p1, IntBuffer p2) {
        GL45C.glGetNamedFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetNamedFramebufferParameteri(int p0, int p1) {
        int returnType = GL45C.glGetNamedFramebufferParameteri(p0, p1);
        LabyDebugContext.glError("glGetNamedFramebufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetNamedFramebufferAttachmentParameteriv(int p0, int p1, int p2, long p3) {
        GL45C.nglGetNamedFramebufferAttachmentParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetNamedFramebufferAttachmentParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetNamedFramebufferAttachmentParameteriv(int p0, int p1, int p2, IntBuffer p3) {
        GL45C.glGetNamedFramebufferAttachmentParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetNamedFramebufferAttachmentParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetNamedFramebufferAttachmentParameteri(int p0, int p1, int p2) {
        int returnType = GL45C.glGetNamedFramebufferAttachmentParameteri(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedFramebufferAttachmentParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglCreateRenderbuffers(int p0, long p1) {
        GL45C.nglCreateRenderbuffers(p0, p1);
        LabyDebugContext.glError("nglCreateRenderbuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateRenderbuffers(IntBuffer p0) {
        GL45C.glCreateRenderbuffers(p0);
        LabyDebugContext.glError("glCreateRenderbuffers", "p0", p0);
    }

    public static int glCreateRenderbuffers() {
        int returnType = GL45C.glCreateRenderbuffers();
        LabyDebugContext.glError("glCreateRenderbuffers", new Object[0]);
        return returnType;
    }

    public static void glNamedRenderbufferStorage(int p0, int p1, int p2, int p3) {
        GL45C.glNamedRenderbufferStorage(p0, p1, p2, p3);
        LabyDebugContext.glError("glNamedRenderbufferStorage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glNamedRenderbufferStorageMultisample(int p0, int p1, int p2, int p3, int p4) {
        GL45C.glNamedRenderbufferStorageMultisample(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glNamedRenderbufferStorageMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglGetNamedRenderbufferParameteriv(int p0, int p1, long p2) {
        GL45C.nglGetNamedRenderbufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedRenderbufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedRenderbufferParameteriv(int p0, int p1, IntBuffer p2) {
        GL45C.glGetNamedRenderbufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedRenderbufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetNamedRenderbufferParameteri(int p0, int p1) {
        int returnType = GL45C.glGetNamedRenderbufferParameteri(p0, p1);
        LabyDebugContext.glError("glGetNamedRenderbufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglCreateTextures(int p0, int p1, long p2) {
        GL45C.nglCreateTextures(p0, p1, p2);
        LabyDebugContext.glError("nglCreateTextures", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glCreateTextures(int p0, IntBuffer p1) {
        GL45C.glCreateTextures(p0, p1);
        LabyDebugContext.glError("glCreateTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static int glCreateTextures(int p0) {
        int returnType = GL45C.glCreateTextures(p0);
        LabyDebugContext.glError("glCreateTextures", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glTextureBuffer(int p0, int p1, int p2) {
        GL45C.glTextureBuffer(p0, p1, p2);
        LabyDebugContext.glError("glTextureBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glTextureBufferRange(int p0, int p1, int p2, long p3, long p4) {
        GL45C.glTextureBufferRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTextureBufferRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glTextureStorage1D(int p0, int p1, int p2, int p3) {
        GL45C.glTextureStorage1D(p0, p1, p2, p3);
        LabyDebugContext.glError("glTextureStorage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glTextureStorage2D(int p0, int p1, int p2, int p3, int p4) {
        GL45C.glTextureStorage2D(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTextureStorage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glTextureStorage3D(int p0, int p1, int p2, int p3, int p4, int p5) {
        GL45C.glTextureStorage3D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTextureStorage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glTextureStorage2DMultisample(int p0, int p1, int p2, int p3, int p4, boolean p5) {
        GL45C.glTextureStorage2DMultisample(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTextureStorage2DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Boolean.valueOf(p5));
    }

    public static void glTextureStorage3DMultisample(int p0, int p1, int p2, int p3, int p4, int p5, boolean p6) {
        GL45C.glTextureStorage3DMultisample(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureStorage3DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Boolean.valueOf(p6));
    }

    public static void nglTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL45C.nglTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6) {
        GL45C.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL45C.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, ShortBuffer p6) {
        GL45C.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, IntBuffer p6) {
        GL45C.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, FloatBuffer p6) {
        GL45C.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, DoubleBuffer p6) {
        GL45C.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void nglTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL45C.nglTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ByteBuffer p8) {
        GL45C.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL45C.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ShortBuffer p8) {
        GL45C.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, IntBuffer p8) {
        GL45C.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, FloatBuffer p8) {
        GL45C.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, DoubleBuffer p8) {
        GL45C.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void nglTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        GL45C.nglTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("nglTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, ByteBuffer p10) {
        GL45C.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        GL45C.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, ShortBuffer p10) {
        GL45C.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, IntBuffer p10) {
        GL45C.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, FloatBuffer p10) {
        GL45C.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, DoubleBuffer p10) {
        GL45C.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void nglCompressedTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL45C.nglCompressedTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglCompressedTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glCompressedTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL45C.glCompressedTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glCompressedTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glCompressedTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, ByteBuffer p5) {
        GL45C.glCompressedTextureSubImage1D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCompressedTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void nglCompressedTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL45C.nglCompressedTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglCompressedTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glCompressedTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL45C.glCompressedTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glCompressedTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glCompressedTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, ByteBuffer p7) {
        GL45C.glCompressedTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glCompressedTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void nglCompressedTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        GL45C.nglCompressedTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("nglCompressedTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glCompressedTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        GL45C.glCompressedTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glCompressedTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glCompressedTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, ByteBuffer p9) {
        GL45C.glCompressedTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("glCompressedTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", p9);
    }

    public static void glCopyTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5) {
        GL45C.glCopyTextureSubImage1D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCopyTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glCopyTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
        GL45C.glCopyTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glCopyTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7));
    }

    public static void glCopyTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8) {
        GL45C.glCopyTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glCopyTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8));
    }

    public static void glTextureParameterf(int p0, int p1, float p2) {
        GL45C.glTextureParameterf(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglTextureParameterfv(int p0, int p1, long p2) {
        GL45C.nglTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTextureParameterfv(int p0, int p1, FloatBuffer p2) {
        GL45C.glTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameteri(int p0, int p1, int p2) {
        GL45C.glTextureParameteri(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTextureParameterIiv(int p0, int p1, long p2) {
        GL45C.nglTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTextureParameterIiv(int p0, int p1, IntBuffer p2) {
        GL45C.glTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameterIi(int p0, int p1, int p2) {
        GL45C.glTextureParameterIi(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTextureParameterIuiv(int p0, int p1, long p2) {
        GL45C.nglTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTextureParameterIuiv(int p0, int p1, IntBuffer p2) {
        GL45C.glTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameterIui(int p0, int p1, int p2) {
        GL45C.glTextureParameterIui(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTextureParameteriv(int p0, int p1, long p2) {
        GL45C.nglTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTextureParameteriv(int p0, int p1, IntBuffer p2) {
        GL45C.glTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGenerateTextureMipmap(int p0) {
        GL45C.glGenerateTextureMipmap(p0);
        LabyDebugContext.glError("glGenerateTextureMipmap", "p0", Integer.valueOf(p0));
    }

    public static void glBindTextureUnit(int p0, int p1) {
        GL45C.glBindTextureUnit(p0, p1);
        LabyDebugContext.glError("glBindTextureUnit", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglGetTextureImage(int p0, int p1, int p2, int p3, int p4, long p5) {
        GL45C.nglGetTextureImage(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, int p4, long p5) {
        GL45C.glGetTextureImage(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        GL45C.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        GL45C.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, IntBuffer p4) {
        GL45C.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        GL45C.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, DoubleBuffer p4) {
        GL45C.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglGetCompressedTextureImage(int p0, int p1, int p2, long p3) {
        GL45C.nglGetCompressedTextureImage(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetCompressedTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetCompressedTextureImage(int p0, int p1, int p2, long p3) {
        GL45C.glGetCompressedTextureImage(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetCompressedTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetCompressedTextureImage(int p0, int p1, ByteBuffer p2) {
        GL45C.glGetCompressedTextureImage(p0, p1, p2);
        LabyDebugContext.glError("glGetCompressedTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetTextureLevelParameterfv(int p0, int p1, int p2, long p3) {
        GL45C.nglGetTextureLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTextureLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTextureLevelParameterfv(int p0, int p1, int p2, FloatBuffer p3) {
        GL45C.glGetTextureLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTextureLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static float glGetTextureLevelParameterf(int p0, int p1, int p2) {
        float returnType = GL45C.glGetTextureLevelParameterf(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureLevelParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetTextureLevelParameteriv(int p0, int p1, int p2, long p3) {
        GL45C.nglGetTextureLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTextureLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTextureLevelParameteriv(int p0, int p1, int p2, IntBuffer p3) {
        GL45C.glGetTextureLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTextureLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetTextureLevelParameteri(int p0, int p1, int p2) {
        int returnType = GL45C.glGetTextureLevelParameteri(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureLevelParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetTextureParameterfv(int p0, int p1, long p2) {
        GL45C.nglGetTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTextureParameterfv(int p0, int p1, FloatBuffer p2) {
        GL45C.glGetTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetTextureParameterf(int p0, int p1) {
        float returnType = GL45C.glGetTextureParameterf(p0, p1);
        LabyDebugContext.glError("glGetTextureParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTextureParameterIiv(int p0, int p1, long p2) {
        GL45C.nglGetTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTextureParameterIiv(int p0, int p1, IntBuffer p2) {
        GL45C.glGetTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTextureParameterIi(int p0, int p1) {
        int returnType = GL45C.glGetTextureParameterIi(p0, p1);
        LabyDebugContext.glError("glGetTextureParameterIi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTextureParameterIuiv(int p0, int p1, long p2) {
        GL45C.nglGetTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTextureParameterIuiv(int p0, int p1, IntBuffer p2) {
        GL45C.glGetTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTextureParameterIui(int p0, int p1) {
        int returnType = GL45C.glGetTextureParameterIui(p0, p1);
        LabyDebugContext.glError("glGetTextureParameterIui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTextureParameteriv(int p0, int p1, long p2) {
        GL45C.nglGetTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTextureParameteriv(int p0, int p1, IntBuffer p2) {
        GL45C.glGetTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTextureParameteri(int p0, int p1) {
        int returnType = GL45C.glGetTextureParameteri(p0, p1);
        LabyDebugContext.glError("glGetTextureParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglCreateVertexArrays(int p0, long p1) {
        GL45C.nglCreateVertexArrays(p0, p1);
        LabyDebugContext.glError("nglCreateVertexArrays", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateVertexArrays(IntBuffer p0) {
        GL45C.glCreateVertexArrays(p0);
        LabyDebugContext.glError("glCreateVertexArrays", "p0", p0);
    }

    public static int glCreateVertexArrays() {
        int returnType = GL45C.glCreateVertexArrays();
        LabyDebugContext.glError("glCreateVertexArrays", new Object[0]);
        return returnType;
    }

    public static void glDisableVertexArrayAttrib(int p0, int p1) {
        GL45C.glDisableVertexArrayAttrib(p0, p1);
        LabyDebugContext.glError("glDisableVertexArrayAttrib", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glEnableVertexArrayAttrib(int p0, int p1) {
        GL45C.glEnableVertexArrayAttrib(p0, p1);
        LabyDebugContext.glError("glEnableVertexArrayAttrib", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexArrayElementBuffer(int p0, int p1) {
        GL45C.glVertexArrayElementBuffer(p0, p1);
        LabyDebugContext.glError("glVertexArrayElementBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexArrayVertexBuffer(int p0, int p1, int p2, long p3, int p4) {
        GL45C.glVertexArrayVertexBuffer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexArrayVertexBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglVertexArrayVertexBuffers(int p0, int p1, int p2, long p3, long p4, long p5) {
        GL45C.nglVertexArrayVertexBuffers(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglVertexArrayVertexBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glVertexArrayVertexBuffers(int p0, int p1, IntBuffer p2, PointerBuffer p3, IntBuffer p4) {
        GL45C.glVertexArrayVertexBuffers(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexArrayVertexBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4);
    }

    public static void glVertexArrayAttribFormat(int p0, int p1, int p2, int p3, boolean p4, int p5) {
        GL45C.glVertexArrayAttribFormat(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexArrayAttribFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Boolean.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glVertexArrayAttribIFormat(int p0, int p1, int p2, int p3, int p4) {
        GL45C.glVertexArrayAttribIFormat(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexArrayAttribIFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glVertexArrayAttribLFormat(int p0, int p1, int p2, int p3, int p4) {
        GL45C.glVertexArrayAttribLFormat(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexArrayAttribLFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glVertexArrayAttribBinding(int p0, int p1, int p2) {
        GL45C.glVertexArrayAttribBinding(p0, p1, p2);
        LabyDebugContext.glError("glVertexArrayAttribBinding", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glVertexArrayBindingDivisor(int p0, int p1, int p2) {
        GL45C.glVertexArrayBindingDivisor(p0, p1, p2);
        LabyDebugContext.glError("glVertexArrayBindingDivisor", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglGetVertexArrayiv(int p0, int p1, long p2) {
        GL45C.nglGetVertexArrayiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexArrayiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexArrayiv(int p0, int p1, IntBuffer p2) {
        GL45C.glGetVertexArrayiv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexArrayiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetVertexArrayi(int p0, int p1) {
        int returnType = GL45C.glGetVertexArrayi(p0, p1);
        LabyDebugContext.glError("glGetVertexArrayi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetVertexArrayIndexediv(int p0, int p1, int p2, long p3) {
        GL45C.nglGetVertexArrayIndexediv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetVertexArrayIndexediv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetVertexArrayIndexediv(int p0, int p1, int p2, IntBuffer p3) {
        GL45C.glGetVertexArrayIndexediv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetVertexArrayIndexediv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetVertexArrayIndexedi(int p0, int p1, int p2) {
        int returnType = GL45C.glGetVertexArrayIndexedi(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexArrayIndexedi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetVertexArrayIndexed64iv(int p0, int p1, int p2, long p3) {
        GL45C.nglGetVertexArrayIndexed64iv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetVertexArrayIndexed64iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetVertexArrayIndexed64iv(int p0, int p1, int p2, LongBuffer p3) {
        GL45C.glGetVertexArrayIndexed64iv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetVertexArrayIndexed64iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static long glGetVertexArrayIndexed64i(int p0, int p1, int p2) {
        long returnType = GL45C.glGetVertexArrayIndexed64i(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexArrayIndexed64i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglCreateSamplers(int p0, long p1) {
        GL45C.nglCreateSamplers(p0, p1);
        LabyDebugContext.glError("nglCreateSamplers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateSamplers(IntBuffer p0) {
        GL45C.glCreateSamplers(p0);
        LabyDebugContext.glError("glCreateSamplers", "p0", p0);
    }

    public static int glCreateSamplers() {
        int returnType = GL45C.glCreateSamplers();
        LabyDebugContext.glError("glCreateSamplers", new Object[0]);
        return returnType;
    }

    public static void nglCreateProgramPipelines(int p0, long p1) {
        GL45C.nglCreateProgramPipelines(p0, p1);
        LabyDebugContext.glError("nglCreateProgramPipelines", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateProgramPipelines(IntBuffer p0) {
        GL45C.glCreateProgramPipelines(p0);
        LabyDebugContext.glError("glCreateProgramPipelines", "p0", p0);
    }

    public static int glCreateProgramPipelines() {
        int returnType = GL45C.glCreateProgramPipelines();
        LabyDebugContext.glError("glCreateProgramPipelines", new Object[0]);
        return returnType;
    }

    public static void nglCreateQueries(int p0, int p1, long p2) {
        GL45C.nglCreateQueries(p0, p1, p2);
        LabyDebugContext.glError("nglCreateQueries", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glCreateQueries(int p0, IntBuffer p1) {
        GL45C.glCreateQueries(p0, p1);
        LabyDebugContext.glError("glCreateQueries", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static int glCreateQueries(int p0) {
        int returnType = GL45C.glCreateQueries(p0);
        LabyDebugContext.glError("glCreateQueries", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glGetQueryBufferObjectiv(int p0, int p1, int p2, long p3) {
        GL45C.glGetQueryBufferObjectiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetQueryBufferObjectiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetQueryBufferObjectuiv(int p0, int p1, int p2, long p3) {
        GL45C.glGetQueryBufferObjectuiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetQueryBufferObjectuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetQueryBufferObjecti64v(int p0, int p1, int p2, long p3) {
        GL45C.glGetQueryBufferObjecti64v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetQueryBufferObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetQueryBufferObjectui64v(int p0, int p1, int p2, long p3) {
        GL45C.glGetQueryBufferObjectui64v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetQueryBufferObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glMemoryBarrierByRegion(int p0) {
        GL45C.glMemoryBarrierByRegion(p0);
        LabyDebugContext.glError("glMemoryBarrierByRegion", "p0", Integer.valueOf(p0));
    }

    public static void nglGetTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, long p11) {
        GL45C.nglGetTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
        LabyDebugContext.glError("nglGetTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Long.valueOf(p11));
    }

    public static void glGetTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, long p11) {
        GL45C.glGetTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
        LabyDebugContext.glError("glGetTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Long.valueOf(p11));
    }

    public static void glGetTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, ByteBuffer p10) {
        GL45C.glGetTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glGetTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glGetTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, ShortBuffer p10) {
        GL45C.glGetTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glGetTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glGetTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, IntBuffer p10) {
        GL45C.glGetTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glGetTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glGetTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, FloatBuffer p10) {
        GL45C.glGetTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glGetTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glGetTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, DoubleBuffer p10) {
        GL45C.glGetTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glGetTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void nglGetCompressedTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, long p9) {
        GL45C.nglGetCompressedTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("nglGetCompressedTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Long.valueOf(p9));
    }

    public static void glGetCompressedTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, long p9) {
        GL45C.glGetCompressedTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("glGetCompressedTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Long.valueOf(p9));
    }

    public static void glGetCompressedTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ByteBuffer p8) {
        GL45C.glGetCompressedTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glGetCompressedTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glGetCompressedTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ShortBuffer p8) {
        GL45C.glGetCompressedTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glGetCompressedTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glGetCompressedTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, IntBuffer p8) {
        GL45C.glGetCompressedTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glGetCompressedTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glGetCompressedTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, FloatBuffer p8) {
        GL45C.glGetCompressedTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glGetCompressedTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glGetCompressedTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, DoubleBuffer p8) {
        GL45C.glGetCompressedTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glGetCompressedTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureBarrier() {
        GL45C.glTextureBarrier();
        LabyDebugContext.glError("glTextureBarrier", new Object[0]);
    }

    public static int glGetGraphicsResetStatus() {
        int returnType = GL45C.glGetGraphicsResetStatus();
        LabyDebugContext.glError("glGetGraphicsResetStatus", new Object[0]);
        return returnType;
    }

    public static void nglGetnTexImage(int p0, int p1, int p2, int p3, int p4, long p5) {
        GL45C.nglGetnTexImage(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetnTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetnTexImage(int p0, int p1, int p2, int p3, int p4, long p5) {
        GL45C.glGetnTexImage(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetnTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetnTexImage(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        GL45C.glGetnTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImage(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        GL45C.glGetnTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImage(int p0, int p1, int p2, int p3, IntBuffer p4) {
        GL45C.glGetnTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImage(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        GL45C.glGetnTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImage(int p0, int p1, int p2, int p3, DoubleBuffer p4) {
        GL45C.glGetnTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        GL45C.nglReadnPixels(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        GL45C.glReadnPixels(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6) {
        GL45C.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, ShortBuffer p6) {
        GL45C.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, IntBuffer p6) {
        GL45C.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, FloatBuffer p6) {
        GL45C.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void nglGetnCompressedTexImage(int p0, int p1, int p2, long p3) {
        GL45C.nglGetnCompressedTexImage(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnCompressedTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnCompressedTexImage(int p0, int p1, int p2, long p3) {
        GL45C.glGetnCompressedTexImage(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetnCompressedTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnCompressedTexImage(int p0, int p1, ByteBuffer p2) {
        GL45C.glGetnCompressedTexImage(p0, p1, p2);
        LabyDebugContext.glError("glGetnCompressedTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetnUniformfv(int p0, int p1, int p2, long p3) {
        GL45C.nglGetnUniformfv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnUniformfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnUniformfv(int p0, int p1, FloatBuffer p2) {
        GL45C.glGetnUniformfv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetnUniformf(int p0, int p1) {
        float returnType = GL45C.glGetnUniformf(p0, p1);
        LabyDebugContext.glError("glGetnUniformf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetnUniformdv(int p0, int p1, int p2, long p3) {
        GL45C.nglGetnUniformdv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnUniformdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnUniformdv(int p0, int p1, DoubleBuffer p2) {
        GL45C.glGetnUniformdv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static double glGetnUniformd(int p0, int p1) {
        double returnType = GL45C.glGetnUniformd(p0, p1);
        LabyDebugContext.glError("glGetnUniformd", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetnUniformiv(int p0, int p1, int p2, long p3) {
        GL45C.nglGetnUniformiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnUniformiv(int p0, int p1, IntBuffer p2) {
        GL45C.glGetnUniformiv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetnUniformi(int p0, int p1) {
        int returnType = GL45C.glGetnUniformi(p0, p1);
        LabyDebugContext.glError("glGetnUniformi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetnUniformuiv(int p0, int p1, int p2, long p3) {
        GL45C.nglGetnUniformuiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetnUniformuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetnUniformuiv(int p0, int p1, IntBuffer p2) {
        GL45C.glGetnUniformuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetnUniformui(int p0, int p1) {
        int returnType = GL45C.glGetnUniformui(p0, p1);
        LabyDebugContext.glError("glGetnUniformui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glCreateTransformFeedbacks(int[] p0) {
        GL45C.glCreateTransformFeedbacks(p0);
        LabyDebugContext.glError("glCreateTransformFeedbacks", "p0", p0);
    }

    public static void glGetTransformFeedbackiv(int p0, int p1, int[] p2) {
        GL45C.glGetTransformFeedbackiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTransformFeedbackiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTransformFeedbacki_v(int p0, int p1, int p2, int[] p3) {
        GL45C.glGetTransformFeedbacki_v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTransformFeedbacki_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetTransformFeedbacki64_v(int p0, int p1, int p2, long[] p3) {
        GL45C.glGetTransformFeedbacki64_v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTransformFeedbacki64_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glCreateBuffers(int[] p0) {
        GL45C.glCreateBuffers(p0);
        LabyDebugContext.glError("glCreateBuffers", "p0", p0);
    }

    public static void glNamedBufferStorage(int p0, short[] p1, int p2) {
        GL45C.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, int[] p1, int p2) {
        GL45C.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, float[] p1, int p2) {
        GL45C.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, double[] p1, int p2) {
        GL45C.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, short[] p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, int[] p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, long[] p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, float[] p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, double[] p1, int p2) {
        GL45C.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferSubData(int p0, long p1, short[] p2) {
        GL45C.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, int[] p2) {
        GL45C.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, long[] p2) {
        GL45C.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, float[] p2) {
        GL45C.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, double[] p2) {
        GL45C.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, short[] p4) {
        GL45C.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, int[] p4) {
        GL45C.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, float[] p4) {
        GL45C.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, short[] p6) {
        GL45C.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, int[] p6) {
        GL45C.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, float[] p6) {
        GL45C.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glGetNamedBufferParameteriv(int p0, int p1, int[] p2) {
        GL45C.glGetNamedBufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferParameteri64v(int p0, int p1, long[] p2) {
        GL45C.glGetNamedBufferParameteri64v(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferParameteri64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, short[] p2) {
        GL45C.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, int[] p2) {
        GL45C.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, long[] p2) {
        GL45C.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, float[] p2) {
        GL45C.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, double[] p2) {
        GL45C.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glCreateFramebuffers(int[] p0) {
        GL45C.glCreateFramebuffers(p0);
        LabyDebugContext.glError("glCreateFramebuffers", "p0", p0);
    }

    public static void glNamedFramebufferDrawBuffers(int p0, int[] p1) {
        GL45C.glNamedFramebufferDrawBuffers(p0, p1);
        LabyDebugContext.glError("glNamedFramebufferDrawBuffers", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glInvalidateNamedFramebufferData(int p0, int[] p1) {
        GL45C.glInvalidateNamedFramebufferData(p0, p1);
        LabyDebugContext.glError("glInvalidateNamedFramebufferData", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glInvalidateNamedFramebufferSubData(int p0, int[] p1, int p2, int p3, int p4, int p5) {
        GL45C.glInvalidateNamedFramebufferSubData(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateNamedFramebufferSubData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glClearNamedFramebufferiv(int p0, int p1, int p2, int[] p3) {
        GL45C.glClearNamedFramebufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glClearNamedFramebufferuiv(int p0, int p1, int p2, int[] p3) {
        GL45C.glClearNamedFramebufferuiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glClearNamedFramebufferfv(int p0, int p1, int p2, float[] p3) {
        GL45C.glClearNamedFramebufferfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetNamedFramebufferParameteriv(int p0, int p1, int[] p2) {
        GL45C.glGetNamedFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedFramebufferAttachmentParameteriv(int p0, int p1, int p2, int[] p3) {
        GL45C.glGetNamedFramebufferAttachmentParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetNamedFramebufferAttachmentParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glCreateRenderbuffers(int[] p0) {
        GL45C.glCreateRenderbuffers(p0);
        LabyDebugContext.glError("glCreateRenderbuffers", "p0", p0);
    }

    public static void glGetNamedRenderbufferParameteriv(int p0, int p1, int[] p2) {
        GL45C.glGetNamedRenderbufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedRenderbufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glCreateTextures(int p0, int[] p1) {
        GL45C.glCreateTextures(p0, p1);
        LabyDebugContext.glError("glCreateTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, short[] p6) {
        GL45C.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int[] p6) {
        GL45C.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, float[] p6) {
        GL45C.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, double[] p6) {
        GL45C.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, short[] p8) {
        GL45C.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int[] p8) {
        GL45C.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, float[] p8) {
        GL45C.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, double[] p8) {
        GL45C.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, short[] p10) {
        GL45C.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int[] p10) {
        GL45C.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, float[] p10) {
        GL45C.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, double[] p10) {
        GL45C.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureParameterfv(int p0, int p1, float[] p2) {
        GL45C.glTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameterIiv(int p0, int p1, int[] p2) {
        GL45C.glTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameterIuiv(int p0, int p1, int[] p2) {
        GL45C.glTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameteriv(int p0, int p1, int[] p2) {
        GL45C.glTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, short[] p4) {
        GL45C.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, int[] p4) {
        GL45C.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, float[] p4) {
        GL45C.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, double[] p4) {
        GL45C.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureLevelParameterfv(int p0, int p1, int p2, float[] p3) {
        GL45C.glGetTextureLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTextureLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetTextureLevelParameteriv(int p0, int p1, int p2, int[] p3) {
        GL45C.glGetTextureLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTextureLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetTextureParameterfv(int p0, int p1, float[] p2) {
        GL45C.glGetTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTextureParameterIiv(int p0, int p1, int[] p2) {
        GL45C.glGetTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTextureParameterIuiv(int p0, int p1, int[] p2) {
        GL45C.glGetTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTextureParameteriv(int p0, int p1, int[] p2) {
        GL45C.glGetTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glCreateVertexArrays(int[] p0) {
        GL45C.glCreateVertexArrays(p0);
        LabyDebugContext.glError("glCreateVertexArrays", "p0", p0);
    }

    public static void glVertexArrayVertexBuffers(int p0, int p1, int[] p2, PointerBuffer p3, int[] p4) {
        GL45C.glVertexArrayVertexBuffers(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexArrayVertexBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4);
    }

    public static void glGetVertexArrayiv(int p0, int p1, int[] p2) {
        GL45C.glGetVertexArrayiv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexArrayiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetVertexArrayIndexediv(int p0, int p1, int p2, int[] p3) {
        GL45C.glGetVertexArrayIndexediv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetVertexArrayIndexediv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetVertexArrayIndexed64iv(int p0, int p1, int p2, long[] p3) {
        GL45C.glGetVertexArrayIndexed64iv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetVertexArrayIndexed64iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glCreateSamplers(int[] p0) {
        GL45C.glCreateSamplers(p0);
        LabyDebugContext.glError("glCreateSamplers", "p0", p0);
    }

    public static void glCreateProgramPipelines(int[] p0) {
        GL45C.glCreateProgramPipelines(p0);
        LabyDebugContext.glError("glCreateProgramPipelines", "p0", p0);
    }

    public static void glCreateQueries(int p0, int[] p1) {
        GL45C.glCreateQueries(p0, p1);
        LabyDebugContext.glError("glCreateQueries", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, short[] p10) {
        GL45C.glGetTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glGetTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glGetTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int[] p10) {
        GL45C.glGetTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glGetTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glGetTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, float[] p10) {
        GL45C.glGetTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glGetTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glGetTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, double[] p10) {
        GL45C.glGetTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glGetTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glGetCompressedTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, short[] p8) {
        GL45C.glGetCompressedTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glGetCompressedTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glGetCompressedTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int[] p8) {
        GL45C.glGetCompressedTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glGetCompressedTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glGetCompressedTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, float[] p8) {
        GL45C.glGetCompressedTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glGetCompressedTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glGetCompressedTextureSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, double[] p8) {
        GL45C.glGetCompressedTextureSubImage(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glGetCompressedTextureSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glGetnTexImage(int p0, int p1, int p2, int p3, short[] p4) {
        GL45C.glGetnTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImage(int p0, int p1, int p2, int p3, int[] p4) {
        GL45C.glGetnTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImage(int p0, int p1, int p2, int p3, float[] p4) {
        GL45C.glGetnTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetnTexImage(int p0, int p1, int p2, int p3, double[] p4) {
        GL45C.glGetnTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetnTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, short[] p6) {
        GL45C.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, int[] p6) {
        GL45C.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadnPixels(int p0, int p1, int p2, int p3, int p4, int p5, float[] p6) {
        GL45C.glReadnPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadnPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glGetnUniformfv(int p0, int p1, float[] p2) {
        GL45C.glGetnUniformfv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetnUniformdv(int p0, int p1, double[] p2) {
        GL45C.glGetnUniformdv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetnUniformiv(int p0, int p1, int[] p2) {
        GL45C.glGetnUniformiv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetnUniformuiv(int p0, int p1, int[] p2) {
        GL45C.glGetnUniformuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetnUniformuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

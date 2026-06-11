package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBDirectStateAccess;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBDirectStateAccessErrorContext.class */
public final class ARBDirectStateAccessErrorContext {
    public static void nglCreateTransformFeedbacks(int p0, long p1) {
        ARBDirectStateAccess.nglCreateTransformFeedbacks(p0, p1);
        LabyDebugContext.glError("nglCreateTransformFeedbacks", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateTransformFeedbacks(IntBuffer p0) {
        ARBDirectStateAccess.glCreateTransformFeedbacks(p0);
        LabyDebugContext.glError("glCreateTransformFeedbacks", "p0", p0);
    }

    public static int glCreateTransformFeedbacks() {
        int returnType = ARBDirectStateAccess.glCreateTransformFeedbacks();
        LabyDebugContext.glError("glCreateTransformFeedbacks", new Object[0]);
        return returnType;
    }

    public static void glTransformFeedbackBufferBase(int p0, int p1, int p2) {
        ARBDirectStateAccess.glTransformFeedbackBufferBase(p0, p1, p2);
        LabyDebugContext.glError("glTransformFeedbackBufferBase", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glTransformFeedbackBufferRange(int p0, int p1, int p2, long p3, long p4) {
        ARBDirectStateAccess.glTransformFeedbackBufferRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTransformFeedbackBufferRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void nglGetTransformFeedbackiv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglGetTransformFeedbackiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTransformFeedbackiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTransformFeedbackiv(int p0, int p1, IntBuffer p2) {
        ARBDirectStateAccess.glGetTransformFeedbackiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTransformFeedbackiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTransformFeedbacki(int p0, int p1) {
        int returnType = ARBDirectStateAccess.glGetTransformFeedbacki(p0, p1);
        LabyDebugContext.glError("glGetTransformFeedbacki", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTransformFeedbacki_v(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.nglGetTransformFeedbacki_v(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTransformFeedbacki_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTransformFeedbacki_v(int p0, int p1, int p2, IntBuffer p3) {
        ARBDirectStateAccess.glGetTransformFeedbacki_v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTransformFeedbacki_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetTransformFeedbacki(int p0, int p1, int p2) {
        int returnType = ARBDirectStateAccess.glGetTransformFeedbacki(p0, p1, p2);
        LabyDebugContext.glError("glGetTransformFeedbacki", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetTransformFeedbacki64_v(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.nglGetTransformFeedbacki64_v(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTransformFeedbacki64_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTransformFeedbacki64_v(int p0, int p1, int p2, LongBuffer p3) {
        ARBDirectStateAccess.glGetTransformFeedbacki64_v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTransformFeedbacki64_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static long glGetTransformFeedbacki64(int p0, int p1, int p2) {
        long returnType = ARBDirectStateAccess.glGetTransformFeedbacki64(p0, p1, p2);
        LabyDebugContext.glError("glGetTransformFeedbacki64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglCreateBuffers(int p0, long p1) {
        ARBDirectStateAccess.nglCreateBuffers(p0, p1);
        LabyDebugContext.glError("nglCreateBuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateBuffers(IntBuffer p0) {
        ARBDirectStateAccess.glCreateBuffers(p0);
        LabyDebugContext.glError("glCreateBuffers", "p0", p0);
    }

    public static int glCreateBuffers() {
        int returnType = ARBDirectStateAccess.glCreateBuffers();
        LabyDebugContext.glError("glCreateBuffers", new Object[0]);
        return returnType;
    }

    public static void nglNamedBufferStorage(int p0, long p1, long p2, int p3) {
        ARBDirectStateAccess.nglNamedBufferStorage(p0, p1, p2, p3);
        LabyDebugContext.glError("nglNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glNamedBufferStorage(int p0, long p1, int p2) {
        ARBDirectStateAccess.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, ByteBuffer p1, int p2) {
        ARBDirectStateAccess.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, ShortBuffer p1, int p2) {
        ARBDirectStateAccess.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, IntBuffer p1, int p2) {
        ARBDirectStateAccess.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, FloatBuffer p1, int p2) {
        ARBDirectStateAccess.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, DoubleBuffer p1, int p2) {
        ARBDirectStateAccess.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglNamedBufferData(int p0, long p1, long p2, int p3) {
        ARBDirectStateAccess.nglNamedBufferData(p0, p1, p2, p3);
        LabyDebugContext.glError("nglNamedBufferData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glNamedBufferData(int p0, long p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, ByteBuffer p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, ShortBuffer p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, IntBuffer p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, LongBuffer p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, FloatBuffer p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, DoubleBuffer p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglNamedBufferSubData(int p0, long p1, long p2, long p3) {
        ARBDirectStateAccess.nglNamedBufferSubData(p0, p1, p2, p3);
        LabyDebugContext.glError("nglNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glNamedBufferSubData(int p0, long p1, ByteBuffer p2) {
        ARBDirectStateAccess.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, ShortBuffer p2) {
        ARBDirectStateAccess.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, IntBuffer p2) {
        ARBDirectStateAccess.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, LongBuffer p2) {
        ARBDirectStateAccess.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, FloatBuffer p2) {
        ARBDirectStateAccess.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, DoubleBuffer p2) {
        ARBDirectStateAccess.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glCopyNamedBufferSubData(int p0, int p1, long p2, long p3, long p4) {
        ARBDirectStateAccess.glCopyNamedBufferSubData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glCopyNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void nglClearNamedBufferData(int p0, int p1, int p2, int p3, long p4) {
        ARBDirectStateAccess.nglClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        ARBDirectStateAccess.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        ARBDirectStateAccess.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, IntBuffer p4) {
        ARBDirectStateAccess.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        ARBDirectStateAccess.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, long p6) {
        ARBDirectStateAccess.nglClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, ByteBuffer p6) {
        ARBDirectStateAccess.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, ShortBuffer p6) {
        ARBDirectStateAccess.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, IntBuffer p6) {
        ARBDirectStateAccess.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, FloatBuffer p6) {
        ARBDirectStateAccess.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static long nglMapNamedBuffer(int p0, int p1) {
        long returnType = ARBDirectStateAccess.nglMapNamedBuffer(p0, p1);
        LabyDebugContext.glError("nglMapNamedBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static ByteBuffer glMapNamedBuffer(int p0, int p1) {
        ByteBuffer returnType = ARBDirectStateAccess.glMapNamedBuffer(p0, p1);
        LabyDebugContext.glError("glMapNamedBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static ByteBuffer glMapNamedBuffer(int p0, int p1, ByteBuffer p2) {
        ByteBuffer returnType = ARBDirectStateAccess.glMapNamedBuffer(p0, p1, p2);
        LabyDebugContext.glError("glMapNamedBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static ByteBuffer glMapNamedBuffer(int p0, int p1, long p2, ByteBuffer p3) {
        ByteBuffer returnType = ARBDirectStateAccess.glMapNamedBuffer(p0, p1, p2, p3);
        LabyDebugContext.glError("glMapNamedBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static long nglMapNamedBufferRange(int p0, long p1, long p2, int p3) {
        long returnType = ARBDirectStateAccess.nglMapNamedBufferRange(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMapNamedBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static ByteBuffer glMapNamedBufferRange(int p0, long p1, long p2, int p3) {
        ByteBuffer returnType = ARBDirectStateAccess.glMapNamedBufferRange(p0, p1, p2, p3);
        LabyDebugContext.glError("glMapNamedBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static ByteBuffer glMapNamedBufferRange(int p0, long p1, long p2, int p3, ByteBuffer p4) {
        ByteBuffer returnType = ARBDirectStateAccess.glMapNamedBufferRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMapNamedBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static boolean glUnmapNamedBuffer(int p0) {
        boolean returnType = ARBDirectStateAccess.glUnmapNamedBuffer(p0);
        LabyDebugContext.glError("glUnmapNamedBuffer", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glFlushMappedNamedBufferRange(int p0, long p1, long p2) {
        ARBDirectStateAccess.glFlushMappedNamedBufferRange(p0, p1, p2);
        LabyDebugContext.glError("glFlushMappedNamedBufferRange", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void nglGetNamedBufferParameteriv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglGetNamedBufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedBufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedBufferParameteriv(int p0, int p1, IntBuffer p2) {
        ARBDirectStateAccess.glGetNamedBufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetNamedBufferParameteri(int p0, int p1) {
        int returnType = ARBDirectStateAccess.glGetNamedBufferParameteri(p0, p1);
        LabyDebugContext.glError("glGetNamedBufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetNamedBufferParameteri64v(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglGetNamedBufferParameteri64v(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedBufferParameteri64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedBufferParameteri64v(int p0, int p1, LongBuffer p2) {
        ARBDirectStateAccess.glGetNamedBufferParameteri64v(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferParameteri64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetNamedBufferParameteri64(int p0, int p1) {
        long returnType = ARBDirectStateAccess.glGetNamedBufferParameteri64(p0, p1);
        LabyDebugContext.glError("glGetNamedBufferParameteri64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetNamedBufferPointerv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglGetNamedBufferPointerv(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedBufferPointerv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedBufferPointerv(int p0, int p1, PointerBuffer p2) {
        ARBDirectStateAccess.glGetNamedBufferPointerv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferPointerv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetNamedBufferPointer(int p0, int p1) {
        long returnType = ARBDirectStateAccess.glGetNamedBufferPointer(p0, p1);
        LabyDebugContext.glError("glGetNamedBufferPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetNamedBufferSubData(int p0, long p1, long p2, long p3) {
        ARBDirectStateAccess.nglGetNamedBufferSubData(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetNamedBufferSubData(int p0, long p1, ByteBuffer p2) {
        ARBDirectStateAccess.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, ShortBuffer p2) {
        ARBDirectStateAccess.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, IntBuffer p2) {
        ARBDirectStateAccess.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, LongBuffer p2) {
        ARBDirectStateAccess.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, FloatBuffer p2) {
        ARBDirectStateAccess.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, DoubleBuffer p2) {
        ARBDirectStateAccess.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void nglCreateFramebuffers(int p0, long p1) {
        ARBDirectStateAccess.nglCreateFramebuffers(p0, p1);
        LabyDebugContext.glError("nglCreateFramebuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateFramebuffers(IntBuffer p0) {
        ARBDirectStateAccess.glCreateFramebuffers(p0);
        LabyDebugContext.glError("glCreateFramebuffers", "p0", p0);
    }

    public static int glCreateFramebuffers() {
        int returnType = ARBDirectStateAccess.glCreateFramebuffers();
        LabyDebugContext.glError("glCreateFramebuffers", new Object[0]);
        return returnType;
    }

    public static void glNamedFramebufferRenderbuffer(int p0, int p1, int p2, int p3) {
        ARBDirectStateAccess.glNamedFramebufferRenderbuffer(p0, p1, p2, p3);
        LabyDebugContext.glError("glNamedFramebufferRenderbuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glNamedFramebufferParameteri(int p0, int p1, int p2) {
        ARBDirectStateAccess.glNamedFramebufferParameteri(p0, p1, p2);
        LabyDebugContext.glError("glNamedFramebufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glNamedFramebufferTexture(int p0, int p1, int p2, int p3) {
        ARBDirectStateAccess.glNamedFramebufferTexture(p0, p1, p2, p3);
        LabyDebugContext.glError("glNamedFramebufferTexture", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glNamedFramebufferTextureLayer(int p0, int p1, int p2, int p3, int p4) {
        ARBDirectStateAccess.glNamedFramebufferTextureLayer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glNamedFramebufferTextureLayer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glNamedFramebufferDrawBuffer(int p0, int p1) {
        ARBDirectStateAccess.glNamedFramebufferDrawBuffer(p0, p1);
        LabyDebugContext.glError("glNamedFramebufferDrawBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglNamedFramebufferDrawBuffers(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglNamedFramebufferDrawBuffers(p0, p1, p2);
        LabyDebugContext.glError("nglNamedFramebufferDrawBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glNamedFramebufferDrawBuffers(int p0, IntBuffer p1) {
        ARBDirectStateAccess.glNamedFramebufferDrawBuffers(p0, p1);
        LabyDebugContext.glError("glNamedFramebufferDrawBuffers", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glNamedFramebufferDrawBuffers(int p0, int p1) {
        ARBDirectStateAccess.glNamedFramebufferDrawBuffers(p0, p1);
        LabyDebugContext.glError("glNamedFramebufferDrawBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glNamedFramebufferReadBuffer(int p0, int p1) {
        ARBDirectStateAccess.glNamedFramebufferReadBuffer(p0, p1);
        LabyDebugContext.glError("glNamedFramebufferReadBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglInvalidateNamedFramebufferData(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglInvalidateNamedFramebufferData(p0, p1, p2);
        LabyDebugContext.glError("nglInvalidateNamedFramebufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glInvalidateNamedFramebufferData(int p0, IntBuffer p1) {
        ARBDirectStateAccess.glInvalidateNamedFramebufferData(p0, p1);
        LabyDebugContext.glError("glInvalidateNamedFramebufferData", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glInvalidateNamedFramebufferData(int p0, int p1) {
        ARBDirectStateAccess.glInvalidateNamedFramebufferData(p0, p1);
        LabyDebugContext.glError("glInvalidateNamedFramebufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglInvalidateNamedFramebufferSubData(int p0, int p1, long p2, int p3, int p4, int p5, int p6) {
        ARBDirectStateAccess.nglInvalidateNamedFramebufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglInvalidateNamedFramebufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glInvalidateNamedFramebufferSubData(int p0, IntBuffer p1, int p2, int p3, int p4, int p5) {
        ARBDirectStateAccess.glInvalidateNamedFramebufferSubData(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateNamedFramebufferSubData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glInvalidateNamedFramebufferSubData(int p0, int p1, int p2, int p3, int p4, int p5) {
        ARBDirectStateAccess.glInvalidateNamedFramebufferSubData(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateNamedFramebufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void nglClearNamedFramebufferiv(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.nglClearNamedFramebufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglClearNamedFramebufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glClearNamedFramebufferiv(int p0, int p1, int p2, IntBuffer p3) {
        ARBDirectStateAccess.glClearNamedFramebufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglClearNamedFramebufferuiv(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.nglClearNamedFramebufferuiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglClearNamedFramebufferuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glClearNamedFramebufferuiv(int p0, int p1, int p2, IntBuffer p3) {
        ARBDirectStateAccess.glClearNamedFramebufferuiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglClearNamedFramebufferfv(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.nglClearNamedFramebufferfv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglClearNamedFramebufferfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glClearNamedFramebufferfv(int p0, int p1, int p2, FloatBuffer p3) {
        ARBDirectStateAccess.glClearNamedFramebufferfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glClearNamedFramebufferfi(int p0, int p1, int p2, float p3, int p4) {
        ARBDirectStateAccess.glClearNamedFramebufferfi(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedFramebufferfi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Float.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glBlitNamedFramebuffer(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11) {
        ARBDirectStateAccess.glBlitNamedFramebuffer(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
        LabyDebugContext.glError("glBlitNamedFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Integer.valueOf(p11));
    }

    public static int glCheckNamedFramebufferStatus(int p0, int p1) {
        int returnType = ARBDirectStateAccess.glCheckNamedFramebufferStatus(p0, p1);
        LabyDebugContext.glError("glCheckNamedFramebufferStatus", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetNamedFramebufferParameteriv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglGetNamedFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedFramebufferParameteriv(int p0, int p1, IntBuffer p2) {
        ARBDirectStateAccess.glGetNamedFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetNamedFramebufferParameteri(int p0, int p1) {
        int returnType = ARBDirectStateAccess.glGetNamedFramebufferParameteri(p0, p1);
        LabyDebugContext.glError("glGetNamedFramebufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetNamedFramebufferAttachmentParameteriv(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.nglGetNamedFramebufferAttachmentParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetNamedFramebufferAttachmentParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetNamedFramebufferAttachmentParameteriv(int p0, int p1, int p2, IntBuffer p3) {
        ARBDirectStateAccess.glGetNamedFramebufferAttachmentParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetNamedFramebufferAttachmentParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetNamedFramebufferAttachmentParameteri(int p0, int p1, int p2) {
        int returnType = ARBDirectStateAccess.glGetNamedFramebufferAttachmentParameteri(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedFramebufferAttachmentParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglCreateRenderbuffers(int p0, long p1) {
        ARBDirectStateAccess.nglCreateRenderbuffers(p0, p1);
        LabyDebugContext.glError("nglCreateRenderbuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateRenderbuffers(IntBuffer p0) {
        ARBDirectStateAccess.glCreateRenderbuffers(p0);
        LabyDebugContext.glError("glCreateRenderbuffers", "p0", p0);
    }

    public static int glCreateRenderbuffers() {
        int returnType = ARBDirectStateAccess.glCreateRenderbuffers();
        LabyDebugContext.glError("glCreateRenderbuffers", new Object[0]);
        return returnType;
    }

    public static void glNamedRenderbufferStorage(int p0, int p1, int p2, int p3) {
        ARBDirectStateAccess.glNamedRenderbufferStorage(p0, p1, p2, p3);
        LabyDebugContext.glError("glNamedRenderbufferStorage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glNamedRenderbufferStorageMultisample(int p0, int p1, int p2, int p3, int p4) {
        ARBDirectStateAccess.glNamedRenderbufferStorageMultisample(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glNamedRenderbufferStorageMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglGetNamedRenderbufferParameteriv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglGetNamedRenderbufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedRenderbufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedRenderbufferParameteriv(int p0, int p1, IntBuffer p2) {
        ARBDirectStateAccess.glGetNamedRenderbufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedRenderbufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetNamedRenderbufferParameteri(int p0, int p1) {
        int returnType = ARBDirectStateAccess.glGetNamedRenderbufferParameteri(p0, p1);
        LabyDebugContext.glError("glGetNamedRenderbufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglCreateTextures(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglCreateTextures(p0, p1, p2);
        LabyDebugContext.glError("nglCreateTextures", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glCreateTextures(int p0, IntBuffer p1) {
        ARBDirectStateAccess.glCreateTextures(p0, p1);
        LabyDebugContext.glError("glCreateTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static int glCreateTextures(int p0) {
        int returnType = ARBDirectStateAccess.glCreateTextures(p0);
        LabyDebugContext.glError("glCreateTextures", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glTextureBuffer(int p0, int p1, int p2) {
        ARBDirectStateAccess.glTextureBuffer(p0, p1, p2);
        LabyDebugContext.glError("glTextureBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glTextureBufferRange(int p0, int p1, int p2, long p3, long p4) {
        ARBDirectStateAccess.glTextureBufferRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTextureBufferRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glTextureStorage1D(int p0, int p1, int p2, int p3) {
        ARBDirectStateAccess.glTextureStorage1D(p0, p1, p2, p3);
        LabyDebugContext.glError("glTextureStorage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glTextureStorage2D(int p0, int p1, int p2, int p3, int p4) {
        ARBDirectStateAccess.glTextureStorage2D(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTextureStorage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glTextureStorage3D(int p0, int p1, int p2, int p3, int p4, int p5) {
        ARBDirectStateAccess.glTextureStorage3D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTextureStorage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glTextureStorage2DMultisample(int p0, int p1, int p2, int p3, int p4, boolean p5) {
        ARBDirectStateAccess.glTextureStorage2DMultisample(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTextureStorage2DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Boolean.valueOf(p5));
    }

    public static void glTextureStorage3DMultisample(int p0, int p1, int p2, int p3, int p4, int p5, boolean p6) {
        ARBDirectStateAccess.glTextureStorage3DMultisample(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureStorage3DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Boolean.valueOf(p6));
    }

    public static void nglTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        ARBDirectStateAccess.nglTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6) {
        ARBDirectStateAccess.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        ARBDirectStateAccess.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, ShortBuffer p6) {
        ARBDirectStateAccess.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, IntBuffer p6) {
        ARBDirectStateAccess.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, FloatBuffer p6) {
        ARBDirectStateAccess.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, DoubleBuffer p6) {
        ARBDirectStateAccess.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void nglTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        ARBDirectStateAccess.nglTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ByteBuffer p8) {
        ARBDirectStateAccess.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        ARBDirectStateAccess.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ShortBuffer p8) {
        ARBDirectStateAccess.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, IntBuffer p8) {
        ARBDirectStateAccess.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, FloatBuffer p8) {
        ARBDirectStateAccess.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, DoubleBuffer p8) {
        ARBDirectStateAccess.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void nglTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        ARBDirectStateAccess.nglTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("nglTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, ByteBuffer p10) {
        ARBDirectStateAccess.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        ARBDirectStateAccess.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, ShortBuffer p10) {
        ARBDirectStateAccess.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, IntBuffer p10) {
        ARBDirectStateAccess.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, FloatBuffer p10) {
        ARBDirectStateAccess.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, DoubleBuffer p10) {
        ARBDirectStateAccess.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void nglCompressedTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        ARBDirectStateAccess.nglCompressedTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglCompressedTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glCompressedTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        ARBDirectStateAccess.glCompressedTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glCompressedTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glCompressedTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, ByteBuffer p5) {
        ARBDirectStateAccess.glCompressedTextureSubImage1D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCompressedTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void nglCompressedTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        ARBDirectStateAccess.nglCompressedTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglCompressedTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glCompressedTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        ARBDirectStateAccess.glCompressedTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glCompressedTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glCompressedTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, ByteBuffer p7) {
        ARBDirectStateAccess.glCompressedTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glCompressedTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void nglCompressedTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        ARBDirectStateAccess.nglCompressedTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("nglCompressedTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glCompressedTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, long p10) {
        ARBDirectStateAccess.glCompressedTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glCompressedTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glCompressedTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, ByteBuffer p9) {
        ARBDirectStateAccess.glCompressedTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("glCompressedTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", p9);
    }

    public static void glCopyTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5) {
        ARBDirectStateAccess.glCopyTextureSubImage1D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCopyTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glCopyTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
        ARBDirectStateAccess.glCopyTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glCopyTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7));
    }

    public static void glCopyTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8) {
        ARBDirectStateAccess.glCopyTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glCopyTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8));
    }

    public static void glTextureParameterf(int p0, int p1, float p2) {
        ARBDirectStateAccess.glTextureParameterf(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglTextureParameterfv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTextureParameterfv(int p0, int p1, FloatBuffer p2) {
        ARBDirectStateAccess.glTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameteri(int p0, int p1, int p2) {
        ARBDirectStateAccess.glTextureParameteri(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTextureParameterIiv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTextureParameterIiv(int p0, int p1, IntBuffer p2) {
        ARBDirectStateAccess.glTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameterIi(int p0, int p1, int p2) {
        ARBDirectStateAccess.glTextureParameterIi(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTextureParameterIuiv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTextureParameterIuiv(int p0, int p1, IntBuffer p2) {
        ARBDirectStateAccess.glTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameterIui(int p0, int p1, int p2) {
        ARBDirectStateAccess.glTextureParameterIui(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTextureParameteriv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTextureParameteriv(int p0, int p1, IntBuffer p2) {
        ARBDirectStateAccess.glTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGenerateTextureMipmap(int p0) {
        ARBDirectStateAccess.glGenerateTextureMipmap(p0);
        LabyDebugContext.glError("glGenerateTextureMipmap", "p0", Integer.valueOf(p0));
    }

    public static void glBindTextureUnit(int p0, int p1) {
        ARBDirectStateAccess.glBindTextureUnit(p0, p1);
        LabyDebugContext.glError("glBindTextureUnit", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglGetTextureImage(int p0, int p1, int p2, int p3, int p4, long p5) {
        ARBDirectStateAccess.nglGetTextureImage(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, int p4, long p5) {
        ARBDirectStateAccess.glGetTextureImage(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        ARBDirectStateAccess.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        ARBDirectStateAccess.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, IntBuffer p4) {
        ARBDirectStateAccess.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        ARBDirectStateAccess.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, DoubleBuffer p4) {
        ARBDirectStateAccess.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglGetCompressedTextureImage(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.nglGetCompressedTextureImage(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetCompressedTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetCompressedTextureImage(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.glGetCompressedTextureImage(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetCompressedTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetCompressedTextureImage(int p0, int p1, ByteBuffer p2) {
        ARBDirectStateAccess.glGetCompressedTextureImage(p0, p1, p2);
        LabyDebugContext.glError("glGetCompressedTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetTextureLevelParameterfv(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.nglGetTextureLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTextureLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTextureLevelParameterfv(int p0, int p1, int p2, FloatBuffer p3) {
        ARBDirectStateAccess.glGetTextureLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTextureLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static float glGetTextureLevelParameterf(int p0, int p1, int p2) {
        float returnType = ARBDirectStateAccess.glGetTextureLevelParameterf(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureLevelParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetTextureLevelParameteriv(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.nglGetTextureLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTextureLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTextureLevelParameteriv(int p0, int p1, int p2, IntBuffer p3) {
        ARBDirectStateAccess.glGetTextureLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTextureLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetTextureLevelParameteri(int p0, int p1, int p2) {
        int returnType = ARBDirectStateAccess.glGetTextureLevelParameteri(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureLevelParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetTextureParameterfv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglGetTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTextureParameterfv(int p0, int p1, FloatBuffer p2) {
        ARBDirectStateAccess.glGetTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetTextureParameterf(int p0, int p1) {
        float returnType = ARBDirectStateAccess.glGetTextureParameterf(p0, p1);
        LabyDebugContext.glError("glGetTextureParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTextureParameterIiv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglGetTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTextureParameterIiv(int p0, int p1, IntBuffer p2) {
        ARBDirectStateAccess.glGetTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTextureParameterIi(int p0, int p1) {
        int returnType = ARBDirectStateAccess.glGetTextureParameterIi(p0, p1);
        LabyDebugContext.glError("glGetTextureParameterIi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTextureParameterIuiv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglGetTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTextureParameterIuiv(int p0, int p1, IntBuffer p2) {
        ARBDirectStateAccess.glGetTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTextureParameterIui(int p0, int p1) {
        int returnType = ARBDirectStateAccess.glGetTextureParameterIui(p0, p1);
        LabyDebugContext.glError("glGetTextureParameterIui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTextureParameteriv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglGetTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTextureParameteriv(int p0, int p1, IntBuffer p2) {
        ARBDirectStateAccess.glGetTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTextureParameteri(int p0, int p1) {
        int returnType = ARBDirectStateAccess.glGetTextureParameteri(p0, p1);
        LabyDebugContext.glError("glGetTextureParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglCreateVertexArrays(int p0, long p1) {
        ARBDirectStateAccess.nglCreateVertexArrays(p0, p1);
        LabyDebugContext.glError("nglCreateVertexArrays", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateVertexArrays(IntBuffer p0) {
        ARBDirectStateAccess.glCreateVertexArrays(p0);
        LabyDebugContext.glError("glCreateVertexArrays", "p0", p0);
    }

    public static int glCreateVertexArrays() {
        int returnType = ARBDirectStateAccess.glCreateVertexArrays();
        LabyDebugContext.glError("glCreateVertexArrays", new Object[0]);
        return returnType;
    }

    public static void glDisableVertexArrayAttrib(int p0, int p1) {
        ARBDirectStateAccess.glDisableVertexArrayAttrib(p0, p1);
        LabyDebugContext.glError("glDisableVertexArrayAttrib", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glEnableVertexArrayAttrib(int p0, int p1) {
        ARBDirectStateAccess.glEnableVertexArrayAttrib(p0, p1);
        LabyDebugContext.glError("glEnableVertexArrayAttrib", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexArrayElementBuffer(int p0, int p1) {
        ARBDirectStateAccess.glVertexArrayElementBuffer(p0, p1);
        LabyDebugContext.glError("glVertexArrayElementBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexArrayVertexBuffer(int p0, int p1, int p2, long p3, int p4) {
        ARBDirectStateAccess.glVertexArrayVertexBuffer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexArrayVertexBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglVertexArrayVertexBuffers(int p0, int p1, int p2, long p3, long p4, long p5) {
        ARBDirectStateAccess.nglVertexArrayVertexBuffers(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglVertexArrayVertexBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glVertexArrayVertexBuffers(int p0, int p1, IntBuffer p2, PointerBuffer p3, IntBuffer p4) {
        ARBDirectStateAccess.glVertexArrayVertexBuffers(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexArrayVertexBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4);
    }

    public static void glVertexArrayAttribFormat(int p0, int p1, int p2, int p3, boolean p4, int p5) {
        ARBDirectStateAccess.glVertexArrayAttribFormat(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexArrayAttribFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Boolean.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glVertexArrayAttribIFormat(int p0, int p1, int p2, int p3, int p4) {
        ARBDirectStateAccess.glVertexArrayAttribIFormat(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexArrayAttribIFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glVertexArrayAttribLFormat(int p0, int p1, int p2, int p3, int p4) {
        ARBDirectStateAccess.glVertexArrayAttribLFormat(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexArrayAttribLFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glVertexArrayAttribBinding(int p0, int p1, int p2) {
        ARBDirectStateAccess.glVertexArrayAttribBinding(p0, p1, p2);
        LabyDebugContext.glError("glVertexArrayAttribBinding", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glVertexArrayBindingDivisor(int p0, int p1, int p2) {
        ARBDirectStateAccess.glVertexArrayBindingDivisor(p0, p1, p2);
        LabyDebugContext.glError("glVertexArrayBindingDivisor", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglGetVertexArrayiv(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglGetVertexArrayiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexArrayiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexArrayiv(int p0, int p1, IntBuffer p2) {
        ARBDirectStateAccess.glGetVertexArrayiv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexArrayiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetVertexArrayi(int p0, int p1) {
        int returnType = ARBDirectStateAccess.glGetVertexArrayi(p0, p1);
        LabyDebugContext.glError("glGetVertexArrayi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetVertexArrayIndexediv(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.nglGetVertexArrayIndexediv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetVertexArrayIndexediv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetVertexArrayIndexediv(int p0, int p1, int p2, IntBuffer p3) {
        ARBDirectStateAccess.glGetVertexArrayIndexediv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetVertexArrayIndexediv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetVertexArrayIndexedi(int p0, int p1, int p2) {
        int returnType = ARBDirectStateAccess.glGetVertexArrayIndexedi(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexArrayIndexedi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetVertexArrayIndexed64iv(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.nglGetVertexArrayIndexed64iv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetVertexArrayIndexed64iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetVertexArrayIndexed64iv(int p0, int p1, int p2, LongBuffer p3) {
        ARBDirectStateAccess.glGetVertexArrayIndexed64iv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetVertexArrayIndexed64iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static long glGetVertexArrayIndexed64i(int p0, int p1, int p2) {
        long returnType = ARBDirectStateAccess.glGetVertexArrayIndexed64i(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexArrayIndexed64i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglCreateSamplers(int p0, long p1) {
        ARBDirectStateAccess.nglCreateSamplers(p0, p1);
        LabyDebugContext.glError("nglCreateSamplers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateSamplers(IntBuffer p0) {
        ARBDirectStateAccess.glCreateSamplers(p0);
        LabyDebugContext.glError("glCreateSamplers", "p0", p0);
    }

    public static int glCreateSamplers() {
        int returnType = ARBDirectStateAccess.glCreateSamplers();
        LabyDebugContext.glError("glCreateSamplers", new Object[0]);
        return returnType;
    }

    public static void nglCreateProgramPipelines(int p0, long p1) {
        ARBDirectStateAccess.nglCreateProgramPipelines(p0, p1);
        LabyDebugContext.glError("nglCreateProgramPipelines", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateProgramPipelines(IntBuffer p0) {
        ARBDirectStateAccess.glCreateProgramPipelines(p0);
        LabyDebugContext.glError("glCreateProgramPipelines", "p0", p0);
    }

    public static int glCreateProgramPipelines() {
        int returnType = ARBDirectStateAccess.glCreateProgramPipelines();
        LabyDebugContext.glError("glCreateProgramPipelines", new Object[0]);
        return returnType;
    }

    public static void nglCreateQueries(int p0, int p1, long p2) {
        ARBDirectStateAccess.nglCreateQueries(p0, p1, p2);
        LabyDebugContext.glError("nglCreateQueries", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glCreateQueries(int p0, IntBuffer p1) {
        ARBDirectStateAccess.glCreateQueries(p0, p1);
        LabyDebugContext.glError("glCreateQueries", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static int glCreateQueries(int p0) {
        int returnType = ARBDirectStateAccess.glCreateQueries(p0);
        LabyDebugContext.glError("glCreateQueries", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glGetQueryBufferObjecti64v(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.glGetQueryBufferObjecti64v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetQueryBufferObjecti64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetQueryBufferObjectiv(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.glGetQueryBufferObjectiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetQueryBufferObjectiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetQueryBufferObjectui64v(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.glGetQueryBufferObjectui64v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetQueryBufferObjectui64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetQueryBufferObjectuiv(int p0, int p1, int p2, long p3) {
        ARBDirectStateAccess.glGetQueryBufferObjectuiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetQueryBufferObjectuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glCreateTransformFeedbacks(int[] p0) {
        ARBDirectStateAccess.glCreateTransformFeedbacks(p0);
        LabyDebugContext.glError("glCreateTransformFeedbacks", "p0", p0);
    }

    public static void glGetTransformFeedbackiv(int p0, int p1, int[] p2) {
        ARBDirectStateAccess.glGetTransformFeedbackiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTransformFeedbackiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTransformFeedbacki_v(int p0, int p1, int p2, int[] p3) {
        ARBDirectStateAccess.glGetTransformFeedbacki_v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTransformFeedbacki_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetTransformFeedbacki64_v(int p0, int p1, int p2, long[] p3) {
        ARBDirectStateAccess.glGetTransformFeedbacki64_v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTransformFeedbacki64_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glCreateBuffers(int[] p0) {
        ARBDirectStateAccess.glCreateBuffers(p0);
        LabyDebugContext.glError("glCreateBuffers", "p0", p0);
    }

    public static void glNamedBufferStorage(int p0, short[] p1, int p2) {
        ARBDirectStateAccess.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, int[] p1, int p2) {
        ARBDirectStateAccess.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, float[] p1, int p2) {
        ARBDirectStateAccess.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorage(int p0, double[] p1, int p2) {
        ARBDirectStateAccess.glNamedBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, short[] p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, int[] p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, long[] p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, float[] p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferData(int p0, double[] p1, int p2) {
        ARBDirectStateAccess.glNamedBufferData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferSubData(int p0, long p1, short[] p2) {
        ARBDirectStateAccess.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, int[] p2) {
        ARBDirectStateAccess.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, long[] p2) {
        ARBDirectStateAccess.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, float[] p2) {
        ARBDirectStateAccess.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glNamedBufferSubData(int p0, long p1, double[] p2) {
        ARBDirectStateAccess.glNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, short[] p4) {
        ARBDirectStateAccess.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, int[] p4) {
        ARBDirectStateAccess.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferData(int p0, int p1, int p2, int p3, float[] p4) {
        ARBDirectStateAccess.glClearNamedBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearNamedBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, short[] p6) {
        ARBDirectStateAccess.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, int[] p6) {
        ARBDirectStateAccess.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearNamedBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, float[] p6) {
        ARBDirectStateAccess.glClearNamedBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glGetNamedBufferParameteriv(int p0, int p1, int[] p2) {
        ARBDirectStateAccess.glGetNamedBufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferParameteri64v(int p0, int p1, long[] p2) {
        ARBDirectStateAccess.glGetNamedBufferParameteri64v(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferParameteri64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, short[] p2) {
        ARBDirectStateAccess.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, int[] p2) {
        ARBDirectStateAccess.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, long[] p2) {
        ARBDirectStateAccess.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, float[] p2) {
        ARBDirectStateAccess.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferSubData(int p0, long p1, double[] p2) {
        ARBDirectStateAccess.glGetNamedBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glCreateFramebuffers(int[] p0) {
        ARBDirectStateAccess.glCreateFramebuffers(p0);
        LabyDebugContext.glError("glCreateFramebuffers", "p0", p0);
    }

    public static void glNamedFramebufferDrawBuffers(int p0, int[] p1) {
        ARBDirectStateAccess.glNamedFramebufferDrawBuffers(p0, p1);
        LabyDebugContext.glError("glNamedFramebufferDrawBuffers", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glInvalidateNamedFramebufferData(int p0, int[] p1) {
        ARBDirectStateAccess.glInvalidateNamedFramebufferData(p0, p1);
        LabyDebugContext.glError("glInvalidateNamedFramebufferData", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glInvalidateNamedFramebufferSubData(int p0, int[] p1, int p2, int p3, int p4, int p5) {
        ARBDirectStateAccess.glInvalidateNamedFramebufferSubData(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateNamedFramebufferSubData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glClearNamedFramebufferiv(int p0, int p1, int p2, int[] p3) {
        ARBDirectStateAccess.glClearNamedFramebufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glClearNamedFramebufferuiv(int p0, int p1, int p2, int[] p3) {
        ARBDirectStateAccess.glClearNamedFramebufferuiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glClearNamedFramebufferfv(int p0, int p1, int p2, float[] p3) {
        ARBDirectStateAccess.glClearNamedFramebufferfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearNamedFramebufferfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetNamedFramebufferParameteriv(int p0, int p1, int[] p2) {
        ARBDirectStateAccess.glGetNamedFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedFramebufferAttachmentParameteriv(int p0, int p1, int p2, int[] p3) {
        ARBDirectStateAccess.glGetNamedFramebufferAttachmentParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetNamedFramebufferAttachmentParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glCreateRenderbuffers(int[] p0) {
        ARBDirectStateAccess.glCreateRenderbuffers(p0);
        LabyDebugContext.glError("glCreateRenderbuffers", "p0", p0);
    }

    public static void glGetNamedRenderbufferParameteriv(int p0, int p1, int[] p2) {
        ARBDirectStateAccess.glGetNamedRenderbufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedRenderbufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glCreateTextures(int p0, int[] p1) {
        ARBDirectStateAccess.glCreateTextures(p0, p1);
        LabyDebugContext.glError("glCreateTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, short[] p6) {
        ARBDirectStateAccess.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int[] p6) {
        ARBDirectStateAccess.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, float[] p6) {
        ARBDirectStateAccess.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, double[] p6) {
        ARBDirectStateAccess.glTextureSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTextureSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, short[] p8) {
        ARBDirectStateAccess.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int[] p8) {
        ARBDirectStateAccess.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, float[] p8) {
        ARBDirectStateAccess.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, double[] p8) {
        ARBDirectStateAccess.glTextureSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTextureSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, short[] p10) {
        ARBDirectStateAccess.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int[] p10) {
        ARBDirectStateAccess.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, float[] p10) {
        ARBDirectStateAccess.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureSubImage3D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, double[] p10) {
        ARBDirectStateAccess.glTextureSubImage3D(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glTextureSubImage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", p10);
    }

    public static void glTextureParameterfv(int p0, int p1, float[] p2) {
        ARBDirectStateAccess.glTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameterIiv(int p0, int p1, int[] p2) {
        ARBDirectStateAccess.glTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameterIuiv(int p0, int p1, int[] p2) {
        ARBDirectStateAccess.glTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTextureParameteriv(int p0, int p1, int[] p2) {
        ARBDirectStateAccess.glTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, short[] p4) {
        ARBDirectStateAccess.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, int[] p4) {
        ARBDirectStateAccess.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, float[] p4) {
        ARBDirectStateAccess.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureImage(int p0, int p1, int p2, int p3, double[] p4) {
        ARBDirectStateAccess.glGetTextureImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTextureImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTextureLevelParameterfv(int p0, int p1, int p2, float[] p3) {
        ARBDirectStateAccess.glGetTextureLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTextureLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetTextureLevelParameteriv(int p0, int p1, int p2, int[] p3) {
        ARBDirectStateAccess.glGetTextureLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTextureLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetTextureParameterfv(int p0, int p1, float[] p2) {
        ARBDirectStateAccess.glGetTextureParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTextureParameterIiv(int p0, int p1, int[] p2) {
        ARBDirectStateAccess.glGetTextureParameterIiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterIiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTextureParameterIuiv(int p0, int p1, int[] p2) {
        ARBDirectStateAccess.glGetTextureParameterIuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameterIuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTextureParameteriv(int p0, int p1, int[] p2) {
        ARBDirectStateAccess.glGetTextureParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetTextureParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glCreateVertexArrays(int[] p0) {
        ARBDirectStateAccess.glCreateVertexArrays(p0);
        LabyDebugContext.glError("glCreateVertexArrays", "p0", p0);
    }

    public static void glVertexArrayVertexBuffers(int p0, int p1, int[] p2, PointerBuffer p3, int[] p4) {
        ARBDirectStateAccess.glVertexArrayVertexBuffers(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexArrayVertexBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4);
    }

    public static void glGetVertexArrayiv(int p0, int p1, int[] p2) {
        ARBDirectStateAccess.glGetVertexArrayiv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexArrayiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetVertexArrayIndexediv(int p0, int p1, int p2, int[] p3) {
        ARBDirectStateAccess.glGetVertexArrayIndexediv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetVertexArrayIndexediv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetVertexArrayIndexed64iv(int p0, int p1, int p2, long[] p3) {
        ARBDirectStateAccess.glGetVertexArrayIndexed64iv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetVertexArrayIndexed64iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glCreateSamplers(int[] p0) {
        ARBDirectStateAccess.glCreateSamplers(p0);
        LabyDebugContext.glError("glCreateSamplers", "p0", p0);
    }

    public static void glCreateProgramPipelines(int[] p0) {
        ARBDirectStateAccess.glCreateProgramPipelines(p0);
        LabyDebugContext.glError("glCreateProgramPipelines", "p0", p0);
    }

    public static void glCreateQueries(int p0, int[] p1) {
        ARBDirectStateAccess.glCreateQueries(p0, p1);
        LabyDebugContext.glError("glCreateQueries", "p0", Integer.valueOf(p0), "p1", p1);
    }
}

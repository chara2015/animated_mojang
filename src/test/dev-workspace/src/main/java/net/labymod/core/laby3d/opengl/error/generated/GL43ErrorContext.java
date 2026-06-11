package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLDebugMessageCallbackI;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL43ErrorContext.class */
public final class GL43ErrorContext {
    public static void nglClearBufferData(int p0, int p1, int p2, int p3, long p4) {
        GL43.nglClearBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglClearBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glClearBufferData(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        GL43.glClearBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearBufferData(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        GL43.glClearBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearBufferData(int p0, int p1, int p2, int p3, IntBuffer p4) {
        GL43.glClearBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearBufferData(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        GL43.glClearBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglClearBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, long p6) {
        GL43.nglClearBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglClearBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glClearBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, ByteBuffer p6) {
        GL43.glClearBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, ShortBuffer p6) {
        GL43.glClearBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, IntBuffer p6) {
        GL43.glClearBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, FloatBuffer p6) {
        GL43.glClearBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glDispatchCompute(int p0, int p1, int p2) {
        GL43.glDispatchCompute(p0, p1, p2);
        LabyDebugContext.glError("glDispatchCompute", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glDispatchComputeIndirect(long p0) {
        GL43.glDispatchComputeIndirect(p0);
        LabyDebugContext.glError("glDispatchComputeIndirect", "p0", Long.valueOf(p0));
    }

    public static void glCopyImageSubData(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11, int p12, int p13, int p14) {
        GL43.glCopyImageSubData(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14);
        LabyDebugContext.glError("glCopyImageSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Integer.valueOf(p11), "p12", Integer.valueOf(p12), "p13", Integer.valueOf(p13), "p14", Integer.valueOf(p14));
    }

    public static void nglDebugMessageControl(int p0, int p1, int p2, int p3, long p4, boolean p5) {
        GL43.nglDebugMessageControl(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglDebugMessageControl", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Boolean.valueOf(p5));
    }

    public static void glDebugMessageControl(int p0, int p1, int p2, IntBuffer p3, boolean p4) {
        GL43.glDebugMessageControl(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDebugMessageControl", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Boolean.valueOf(p4));
    }

    public static void glDebugMessageControl(int p0, int p1, int p2, int p3, boolean p4) {
        GL43.glDebugMessageControl(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDebugMessageControl", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Boolean.valueOf(p4));
    }

    public static void nglDebugMessageInsert(int p0, int p1, int p2, int p3, int p4, long p5) {
        GL43.nglDebugMessageInsert(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglDebugMessageInsert", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glDebugMessageInsert(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        GL43.glDebugMessageInsert(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDebugMessageInsert", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glDebugMessageInsert(int p0, int p1, int p2, int p3, CharSequence p4) {
        GL43.glDebugMessageInsert(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDebugMessageInsert", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglDebugMessageCallback(long p0, long p1) {
        GL43.nglDebugMessageCallback(p0, p1);
        LabyDebugContext.glError("nglDebugMessageCallback", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDebugMessageCallback(GLDebugMessageCallbackI p0, long p1) {
        GL43.glDebugMessageCallback(p0, p1);
        LabyDebugContext.glError("glDebugMessageCallback", "p0", p0, "p1", Long.valueOf(p1));
    }

    public static int nglGetDebugMessageLog(int p0, int p1, long p2, long p3, long p4, long p5, long p6, long p7) {
        int returnType = GL43.nglGetDebugMessageLog(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglGetDebugMessageLog", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6), "p7", Long.valueOf(p7));
        return returnType;
    }

    public static int glGetDebugMessageLog(int p0, IntBuffer p1, IntBuffer p2, IntBuffer p3, IntBuffer p4, IntBuffer p5, ByteBuffer p6) {
        int returnType = GL43.glGetDebugMessageLog(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glGetDebugMessageLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4, "p5", p5, "p6", p6);
        return returnType;
    }

    public static void nglPushDebugGroup(int p0, int p1, int p2, long p3) {
        GL43.nglPushDebugGroup(p0, p1, p2, p3);
        LabyDebugContext.glError("nglPushDebugGroup", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glPushDebugGroup(int p0, int p1, ByteBuffer p2) {
        GL43.glPushDebugGroup(p0, p1, p2);
        LabyDebugContext.glError("glPushDebugGroup", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPushDebugGroup(int p0, int p1, CharSequence p2) {
        GL43.glPushDebugGroup(p0, p1, p2);
        LabyDebugContext.glError("glPushDebugGroup", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPopDebugGroup() {
        GL43.glPopDebugGroup();
        LabyDebugContext.glError("glPopDebugGroup", new Object[0]);
    }

    public static void nglObjectLabel(int p0, int p1, int p2, long p3) {
        GL43.nglObjectLabel(p0, p1, p2, p3);
        LabyDebugContext.glError("nglObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glObjectLabel(int p0, int p1, ByteBuffer p2) {
        GL43.glObjectLabel(p0, p1, p2);
        LabyDebugContext.glError("glObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glObjectLabel(int p0, int p1, CharSequence p2) {
        GL43.glObjectLabel(p0, p1, p2);
        LabyDebugContext.glError("glObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetObjectLabel(int p0, int p1, int p2, long p3, long p4) {
        GL43.nglGetObjectLabel(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetObjectLabel(int p0, int p1, IntBuffer p2, ByteBuffer p3) {
        GL43.glGetObjectLabel(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static String glGetObjectLabel(int p0, int p1, int p2) {
        String returnType = GL43.glGetObjectLabel(p0, p1, p2);
        LabyDebugContext.glError("glGetObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static String glGetObjectLabel(int p0, int p1) {
        String returnType = GL43.glGetObjectLabel(p0, p1);
        LabyDebugContext.glError("glGetObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglObjectPtrLabel(long p0, int p1, long p2) {
        GL43.nglObjectPtrLabel(p0, p1, p2);
        LabyDebugContext.glError("nglObjectPtrLabel", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glObjectPtrLabel(long p0, ByteBuffer p1) {
        GL43.glObjectPtrLabel(p0, p1);
        LabyDebugContext.glError("glObjectPtrLabel", "p0", Long.valueOf(p0), "p1", p1);
    }

    public static void glObjectPtrLabel(long p0, CharSequence p1) {
        GL43.glObjectPtrLabel(p0, p1);
        LabyDebugContext.glError("glObjectPtrLabel", "p0", Long.valueOf(p0), "p1", p1);
    }

    public static void nglGetObjectPtrLabel(long p0, int p1, long p2, long p3) {
        GL43.nglGetObjectPtrLabel(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetObjectPtrLabel", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetObjectPtrLabel(long p0, IntBuffer p1, ByteBuffer p2) {
        GL43.glGetObjectPtrLabel(p0, p1, p2);
        LabyDebugContext.glError("glGetObjectPtrLabel", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static String glGetObjectPtrLabel(long p0, int p1) {
        String returnType = GL43.glGetObjectPtrLabel(p0, p1);
        LabyDebugContext.glError("glGetObjectPtrLabel", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glGetObjectPtrLabel(long p0) {
        String returnType = GL43.glGetObjectPtrLabel(p0);
        LabyDebugContext.glError("glGetObjectPtrLabel", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static void glFramebufferParameteri(int p0, int p1, int p2) {
        GL43.glFramebufferParameteri(p0, p1, p2);
        LabyDebugContext.glError("glFramebufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglGetFramebufferParameteriv(int p0, int p1, long p2) {
        GL43.nglGetFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetFramebufferParameteriv(int p0, int p1, IntBuffer p2) {
        GL43.glGetFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetFramebufferParameteri(int p0, int p1) {
        int returnType = GL43.glGetFramebufferParameteri(p0, p1);
        LabyDebugContext.glError("glGetFramebufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetInternalformati64v(int p0, int p1, int p2, int p3, long p4) {
        GL43.nglGetInternalformati64v(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetInternalformati64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetInternalformati64v(int p0, int p1, int p2, LongBuffer p3) {
        GL43.glGetInternalformati64v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetInternalformati64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static long glGetInternalformati64(int p0, int p1, int p2) {
        long returnType = GL43.glGetInternalformati64(p0, p1, p2);
        LabyDebugContext.glError("glGetInternalformati64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void glInvalidateTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
        GL43.glInvalidateTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glInvalidateTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7));
    }

    public static void glInvalidateTexImage(int p0, int p1) {
        GL43.glInvalidateTexImage(p0, p1);
        LabyDebugContext.glError("glInvalidateTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glInvalidateBufferSubData(int p0, long p1, long p2) {
        GL43.glInvalidateBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glInvalidateBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glInvalidateBufferData(int p0) {
        GL43.glInvalidateBufferData(p0);
        LabyDebugContext.glError("glInvalidateBufferData", "p0", Integer.valueOf(p0));
    }

    public static void nglInvalidateFramebuffer(int p0, int p1, long p2) {
        GL43.nglInvalidateFramebuffer(p0, p1, p2);
        LabyDebugContext.glError("nglInvalidateFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glInvalidateFramebuffer(int p0, IntBuffer p1) {
        GL43.glInvalidateFramebuffer(p0, p1);
        LabyDebugContext.glError("glInvalidateFramebuffer", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glInvalidateFramebuffer(int p0, int p1) {
        GL43.glInvalidateFramebuffer(p0, p1);
        LabyDebugContext.glError("glInvalidateFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglInvalidateSubFramebuffer(int p0, int p1, long p2, int p3, int p4, int p5, int p6) {
        GL43.nglInvalidateSubFramebuffer(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglInvalidateSubFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glInvalidateSubFramebuffer(int p0, IntBuffer p1, int p2, int p3, int p4, int p5) {
        GL43.glInvalidateSubFramebuffer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateSubFramebuffer", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glInvalidateSubFramebuffer(int p0, int p1, int p2, int p3, int p4, int p5) {
        GL43.glInvalidateSubFramebuffer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateSubFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void nglMultiDrawArraysIndirect(int p0, long p1, int p2, int p3) {
        GL43.nglMultiDrawArraysIndirect(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMultiDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glMultiDrawArraysIndirect(int p0, ByteBuffer p1, int p2, int p3) {
        GL43.glMultiDrawArraysIndirect(p0, p1, p2, p3);
        LabyDebugContext.glError("glMultiDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glMultiDrawArraysIndirect(int p0, long p1, int p2, int p3) {
        GL43.glMultiDrawArraysIndirect(p0, p1, p2, p3);
        LabyDebugContext.glError("glMultiDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glMultiDrawArraysIndirect(int p0, IntBuffer p1, int p2, int p3) {
        GL43.glMultiDrawArraysIndirect(p0, p1, p2, p3);
        LabyDebugContext.glError("glMultiDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglMultiDrawElementsIndirect(int p0, int p1, long p2, int p3, int p4) {
        GL43.nglMultiDrawElementsIndirect(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglMultiDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glMultiDrawElementsIndirect(int p0, int p1, ByteBuffer p2, int p3, int p4) {
        GL43.glMultiDrawElementsIndirect(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glMultiDrawElementsIndirect(int p0, int p1, long p2, int p3, int p4) {
        GL43.glMultiDrawElementsIndirect(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glMultiDrawElementsIndirect(int p0, int p1, IntBuffer p2, int p3, int p4) {
        GL43.glMultiDrawElementsIndirect(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglGetProgramInterfaceiv(int p0, int p1, int p2, long p3) {
        GL43.nglGetProgramInterfaceiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetProgramInterfaceiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetProgramInterfaceiv(int p0, int p1, int p2, IntBuffer p3) {
        GL43.glGetProgramInterfaceiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetProgramInterfaceiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetProgramInterfacei(int p0, int p1, int p2) {
        int returnType = GL43.glGetProgramInterfacei(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramInterfacei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static int nglGetProgramResourceIndex(int p0, int p1, long p2) {
        int returnType = GL43.nglGetProgramResourceIndex(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramResourceIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glGetProgramResourceIndex(int p0, int p1, ByteBuffer p2) {
        int returnType = GL43.glGetProgramResourceIndex(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramResourceIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int glGetProgramResourceIndex(int p0, int p1, CharSequence p2) {
        int returnType = GL43.glGetProgramResourceIndex(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramResourceIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static void nglGetProgramResourceName(int p0, int p1, int p2, int p3, long p4, long p5) {
        GL43.nglGetProgramResourceName(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetProgramResourceName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetProgramResourceName(int p0, int p1, int p2, IntBuffer p3, ByteBuffer p4) {
        GL43.glGetProgramResourceName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetProgramResourceName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static String glGetProgramResourceName(int p0, int p1, int p2, int p3) {
        String returnType = GL43.glGetProgramResourceName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetProgramResourceName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static String glGetProgramResourceName(int p0, int p1, int p2) {
        String returnType = GL43.glGetProgramResourceName(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramResourceName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetProgramResourceiv(int p0, int p1, int p2, int p3, long p4, int p5, long p6, long p7) {
        GL43.nglGetProgramResourceiv(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglGetProgramResourceiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glGetProgramResourceiv(int p0, int p1, int p2, IntBuffer p3, IntBuffer p4, IntBuffer p5) {
        GL43.glGetProgramResourceiv(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetProgramResourceiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4, "p5", p5);
    }

    public static int nglGetProgramResourceLocation(int p0, int p1, long p2) {
        int returnType = GL43.nglGetProgramResourceLocation(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramResourceLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glGetProgramResourceLocation(int p0, int p1, ByteBuffer p2) {
        int returnType = GL43.glGetProgramResourceLocation(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramResourceLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int glGetProgramResourceLocation(int p0, int p1, CharSequence p2) {
        int returnType = GL43.glGetProgramResourceLocation(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramResourceLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int nglGetProgramResourceLocationIndex(int p0, int p1, long p2) {
        int returnType = GL43.nglGetProgramResourceLocationIndex(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramResourceLocationIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glGetProgramResourceLocationIndex(int p0, int p1, ByteBuffer p2) {
        int returnType = GL43.glGetProgramResourceLocationIndex(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramResourceLocationIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int glGetProgramResourceLocationIndex(int p0, int p1, CharSequence p2) {
        int returnType = GL43.glGetProgramResourceLocationIndex(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramResourceLocationIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static void glShaderStorageBlockBinding(int p0, int p1, int p2) {
        GL43.glShaderStorageBlockBinding(p0, p1, p2);
        LabyDebugContext.glError("glShaderStorageBlockBinding", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glTexBufferRange(int p0, int p1, int p2, long p3, long p4) {
        GL43.glTexBufferRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTexBufferRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glTexStorage2DMultisample(int p0, int p1, int p2, int p3, int p4, boolean p5) {
        GL43.glTexStorage2DMultisample(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTexStorage2DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Boolean.valueOf(p5));
    }

    public static void glTexStorage3DMultisample(int p0, int p1, int p2, int p3, int p4, int p5, boolean p6) {
        GL43.glTexStorage3DMultisample(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexStorage3DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Boolean.valueOf(p6));
    }

    public static void glTextureView(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
        GL43.glTextureView(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTextureView", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7));
    }

    public static void glBindVertexBuffer(int p0, int p1, long p2, int p3) {
        GL43.glBindVertexBuffer(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindVertexBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribFormat(int p0, int p1, int p2, boolean p3, int p4) {
        GL43.glVertexAttribFormat(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glVertexAttribIFormat(int p0, int p1, int p2, int p3) {
        GL43.glVertexAttribIFormat(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribIFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribLFormat(int p0, int p1, int p2, int p3) {
        GL43.glVertexAttribLFormat(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribLFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertexAttribBinding(int p0, int p1) {
        GL43.glVertexAttribBinding(p0, p1);
        LabyDebugContext.glError("glVertexAttribBinding", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertexBindingDivisor(int p0, int p1) {
        GL43.glVertexBindingDivisor(p0, p1);
        LabyDebugContext.glError("glVertexBindingDivisor", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glClearBufferData(int p0, int p1, int p2, int p3, short[] p4) {
        GL43.glClearBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearBufferData(int p0, int p1, int p2, int p3, int[] p4) {
        GL43.glClearBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearBufferData(int p0, int p1, int p2, int p3, float[] p4) {
        GL43.glClearBufferData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glClearBufferData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glClearBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, short[] p6) {
        GL43.glClearBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, int[] p6) {
        GL43.glClearBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glClearBufferSubData(int p0, int p1, long p2, long p3, int p4, int p5, float[] p6) {
        GL43.glClearBufferSubData(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glClearBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glDebugMessageControl(int p0, int p1, int p2, int[] p3, boolean p4) {
        GL43.glDebugMessageControl(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDebugMessageControl", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Boolean.valueOf(p4));
    }

    public static int glGetDebugMessageLog(int p0, int[] p1, int[] p2, int[] p3, int[] p4, int[] p5, ByteBuffer p6) {
        int returnType = GL43.glGetDebugMessageLog(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glGetDebugMessageLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4, "p5", p5, "p6", p6);
        return returnType;
    }

    public static void glGetObjectLabel(int p0, int p1, int[] p2, ByteBuffer p3) {
        GL43.glGetObjectLabel(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void glGetObjectPtrLabel(long p0, int[] p1, ByteBuffer p2) {
        GL43.glGetObjectPtrLabel(p0, p1, p2);
        LabyDebugContext.glError("glGetObjectPtrLabel", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetFramebufferParameteriv(int p0, int p1, int[] p2) {
        GL43.glGetFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetInternalformati64v(int p0, int p1, int p2, long[] p3) {
        GL43.glGetInternalformati64v(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetInternalformati64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glInvalidateFramebuffer(int p0, int[] p1) {
        GL43.glInvalidateFramebuffer(p0, p1);
        LabyDebugContext.glError("glInvalidateFramebuffer", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glInvalidateSubFramebuffer(int p0, int[] p1, int p2, int p3, int p4, int p5) {
        GL43.glInvalidateSubFramebuffer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateSubFramebuffer", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glMultiDrawArraysIndirect(int p0, int[] p1, int p2, int p3) {
        GL43.glMultiDrawArraysIndirect(p0, p1, p2, p3);
        LabyDebugContext.glError("glMultiDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glMultiDrawElementsIndirect(int p0, int p1, int[] p2, int p3, int p4) {
        GL43.glMultiDrawElementsIndirect(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glGetProgramInterfaceiv(int p0, int p1, int p2, int[] p3) {
        GL43.glGetProgramInterfaceiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetProgramInterfaceiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetProgramResourceName(int p0, int p1, int p2, int[] p3, ByteBuffer p4) {
        GL43.glGetProgramResourceName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetProgramResourceName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static void glGetProgramResourceiv(int p0, int p1, int p2, int[] p3, int[] p4, int[] p5) {
        GL43.glGetProgramResourceiv(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetProgramResourceiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4, "p5", p5);
    }
}

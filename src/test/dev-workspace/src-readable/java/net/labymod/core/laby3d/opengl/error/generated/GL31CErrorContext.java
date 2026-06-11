package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL31C;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL31CErrorContext.class */
public final class GL31CErrorContext {
    public static void glDrawArraysInstanced(int p0, int p1, int p2, int p3) {
        GL31C.glDrawArraysInstanced(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawArraysInstanced", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglDrawElementsInstanced(int p0, int p1, int p2, long p3, int p4) {
        GL31C.nglDrawElementsInstanced(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglDrawElementsInstanced", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsInstanced(int p0, int p1, int p2, long p3, int p4) {
        GL31C.glDrawElementsInstanced(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawElementsInstanced", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsInstanced(int p0, int p1, ByteBuffer p2, int p3) {
        GL31C.glDrawElementsInstanced(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElementsInstanced", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3));
    }

    public static void glDrawElementsInstanced(int p0, ByteBuffer p1, int p2) {
        GL31C.glDrawElementsInstanced(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsInstanced", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glDrawElementsInstanced(int p0, ShortBuffer p1, int p2) {
        GL31C.glDrawElementsInstanced(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsInstanced", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glDrawElementsInstanced(int p0, IntBuffer p1, int p2) {
        GL31C.glDrawElementsInstanced(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsInstanced", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glCopyBufferSubData(int p0, int p1, long p2, long p3, long p4) {
        GL31C.glCopyBufferSubData(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glCopyBufferSubData", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glPrimitiveRestartIndex(int p0) {
        GL31C.glPrimitiveRestartIndex(p0);
        LabyDebugContext.glError("glPrimitiveRestartIndex", "p0", Integer.valueOf(p0));
    }

    public static void glTexBuffer(int p0, int p1, int p2) {
        GL31C.glTexBuffer(p0, p1, p2);
        LabyDebugContext.glError("glTexBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglGetUniformIndices(int p0, int p1, long p2, long p3) {
        GL31C.nglGetUniformIndices(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetUniformIndices", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetUniformIndices(int p0, PointerBuffer p1, IntBuffer p2) {
        GL31C.glGetUniformIndices(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformIndices", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetUniformIndices(int p0, CharSequence[] p1, IntBuffer p2) {
        GL31C.glGetUniformIndices(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformIndices", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static int glGetUniformIndices(int p0, CharSequence p1) {
        int returnType = GL31C.glGetUniformIndices(p0, p1);
        LabyDebugContext.glError("glGetUniformIndices", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void nglGetActiveUniformsiv(int p0, int p1, long p2, int p3, long p4) {
        GL31C.nglGetActiveUniformsiv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetActiveUniformsiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetActiveUniformsiv(int p0, IntBuffer p1, int p2, IntBuffer p3) {
        GL31C.glGetActiveUniformsiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformsiv", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetActiveUniformsi(int p0, int p1, int p2) {
        int returnType = GL31C.glGetActiveUniformsi(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveUniformsi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetActiveUniformName(int p0, int p1, int p2, long p3, long p4) {
        GL31C.nglGetActiveUniformName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetActiveUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetActiveUniformName(int p0, int p1, IntBuffer p2, ByteBuffer p3) {
        GL31C.glGetActiveUniformName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static String glGetActiveUniformName(int p0, int p1, int p2) {
        String returnType = GL31C.glGetActiveUniformName(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static String glGetActiveUniformName(int p0, int p1) {
        String returnType = GL31C.glGetActiveUniformName(p0, p1);
        LabyDebugContext.glError("glGetActiveUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static int nglGetUniformBlockIndex(int p0, long p1) {
        int returnType = GL31C.nglGetUniformBlockIndex(p0, p1);
        LabyDebugContext.glError("nglGetUniformBlockIndex", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glGetUniformBlockIndex(int p0, ByteBuffer p1) {
        int returnType = GL31C.glGetUniformBlockIndex(p0, p1);
        LabyDebugContext.glError("glGetUniformBlockIndex", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glGetUniformBlockIndex(int p0, CharSequence p1) {
        int returnType = GL31C.glGetUniformBlockIndex(p0, p1);
        LabyDebugContext.glError("glGetUniformBlockIndex", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void nglGetActiveUniformBlockiv(int p0, int p1, int p2, long p3) {
        GL31C.nglGetActiveUniformBlockiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetActiveUniformBlockiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetActiveUniformBlockiv(int p0, int p1, int p2, IntBuffer p3) {
        GL31C.glGetActiveUniformBlockiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformBlockiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetActiveUniformBlocki(int p0, int p1, int p2) {
        int returnType = GL31C.glGetActiveUniformBlocki(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveUniformBlocki", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetActiveUniformBlockName(int p0, int p1, int p2, long p3, long p4) {
        GL31C.nglGetActiveUniformBlockName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetActiveUniformBlockName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetActiveUniformBlockName(int p0, int p1, IntBuffer p2, ByteBuffer p3) {
        GL31C.glGetActiveUniformBlockName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformBlockName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static String glGetActiveUniformBlockName(int p0, int p1, int p2) {
        String returnType = GL31C.glGetActiveUniformBlockName(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveUniformBlockName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static String glGetActiveUniformBlockName(int p0, int p1) {
        String returnType = GL31C.glGetActiveUniformBlockName(p0, p1);
        LabyDebugContext.glError("glGetActiveUniformBlockName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glUniformBlockBinding(int p0, int p1, int p2) {
        GL31C.glUniformBlockBinding(p0, p1, p2);
        LabyDebugContext.glError("glUniformBlockBinding", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glGetUniformIndices(int p0, PointerBuffer p1, int[] p2) {
        GL31C.glGetUniformIndices(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformIndices", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetActiveUniformsiv(int p0, int[] p1, int p2, int[] p3) {
        GL31C.glGetActiveUniformsiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformsiv", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetActiveUniformName(int p0, int p1, int[] p2, ByteBuffer p3) {
        GL31C.glGetActiveUniformName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void glGetActiveUniformBlockiv(int p0, int p1, int p2, int[] p3) {
        GL31C.glGetActiveUniformBlockiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformBlockiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetActiveUniformBlockName(int p0, int p1, int[] p2, ByteBuffer p3) {
        GL31C.glGetActiveUniformBlockName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformBlockName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBUniformBufferObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBUniformBufferObjectErrorContext.class */
public final class ARBUniformBufferObjectErrorContext {
    public static void nglGetUniformIndices(int p0, int p1, long p2, long p3) {
        ARBUniformBufferObject.nglGetUniformIndices(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetUniformIndices", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetUniformIndices(int p0, PointerBuffer p1, IntBuffer p2) {
        ARBUniformBufferObject.glGetUniformIndices(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformIndices", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetUniformIndices(int p0, CharSequence[] p1, IntBuffer p2) {
        ARBUniformBufferObject.glGetUniformIndices(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformIndices", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static int glGetUniformIndices(int p0, CharSequence p1) {
        int returnType = ARBUniformBufferObject.glGetUniformIndices(p0, p1);
        LabyDebugContext.glError("glGetUniformIndices", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void nglGetActiveUniformsiv(int p0, int p1, long p2, int p3, long p4) {
        ARBUniformBufferObject.nglGetActiveUniformsiv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetActiveUniformsiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetActiveUniformsiv(int p0, IntBuffer p1, int p2, IntBuffer p3) {
        ARBUniformBufferObject.glGetActiveUniformsiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformsiv", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetActiveUniformsi(int p0, int p1, int p2) {
        int returnType = ARBUniformBufferObject.glGetActiveUniformsi(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveUniformsi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetActiveUniformName(int p0, int p1, int p2, long p3, long p4) {
        ARBUniformBufferObject.nglGetActiveUniformName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetActiveUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetActiveUniformName(int p0, int p1, IntBuffer p2, ByteBuffer p3) {
        ARBUniformBufferObject.glGetActiveUniformName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static String glGetActiveUniformName(int p0, int p1, int p2) {
        String returnType = ARBUniformBufferObject.glGetActiveUniformName(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static String glGetActiveUniformName(int p0, int p1) {
        String returnType = ARBUniformBufferObject.glGetActiveUniformName(p0, p1);
        LabyDebugContext.glError("glGetActiveUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static int nglGetUniformBlockIndex(int p0, long p1) {
        int returnType = ARBUniformBufferObject.nglGetUniformBlockIndex(p0, p1);
        LabyDebugContext.glError("nglGetUniformBlockIndex", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glGetUniformBlockIndex(int p0, ByteBuffer p1) {
        int returnType = ARBUniformBufferObject.glGetUniformBlockIndex(p0, p1);
        LabyDebugContext.glError("glGetUniformBlockIndex", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glGetUniformBlockIndex(int p0, CharSequence p1) {
        int returnType = ARBUniformBufferObject.glGetUniformBlockIndex(p0, p1);
        LabyDebugContext.glError("glGetUniformBlockIndex", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void nglGetActiveUniformBlockiv(int p0, int p1, int p2, long p3) {
        ARBUniformBufferObject.nglGetActiveUniformBlockiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetActiveUniformBlockiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetActiveUniformBlockiv(int p0, int p1, int p2, IntBuffer p3) {
        ARBUniformBufferObject.glGetActiveUniformBlockiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformBlockiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetActiveUniformBlocki(int p0, int p1, int p2) {
        int returnType = ARBUniformBufferObject.glGetActiveUniformBlocki(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveUniformBlocki", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetActiveUniformBlockName(int p0, int p1, int p2, long p3, long p4) {
        ARBUniformBufferObject.nglGetActiveUniformBlockName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetActiveUniformBlockName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetActiveUniformBlockName(int p0, int p1, IntBuffer p2, ByteBuffer p3) {
        ARBUniformBufferObject.glGetActiveUniformBlockName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformBlockName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static String glGetActiveUniformBlockName(int p0, int p1, int p2) {
        String returnType = ARBUniformBufferObject.glGetActiveUniformBlockName(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveUniformBlockName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static String glGetActiveUniformBlockName(int p0, int p1) {
        String returnType = ARBUniformBufferObject.glGetActiveUniformBlockName(p0, p1);
        LabyDebugContext.glError("glGetActiveUniformBlockName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glBindBufferRange(int p0, int p1, int p2, long p3, long p4) {
        ARBUniformBufferObject.glBindBufferRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glBindBufferRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glBindBufferBase(int p0, int p1, int p2) {
        ARBUniformBufferObject.glBindBufferBase(p0, p1, p2);
        LabyDebugContext.glError("glBindBufferBase", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglGetIntegeri_v(int p0, int p1, long p2) {
        ARBUniformBufferObject.nglGetIntegeri_v(p0, p1, p2);
        LabyDebugContext.glError("nglGetIntegeri_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetIntegeri_v(int p0, int p1, IntBuffer p2) {
        ARBUniformBufferObject.glGetIntegeri_v(p0, p1, p2);
        LabyDebugContext.glError("glGetIntegeri_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetIntegeri(int p0, int p1) {
        int returnType = ARBUniformBufferObject.glGetIntegeri(p0, p1);
        LabyDebugContext.glError("glGetIntegeri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glUniformBlockBinding(int p0, int p1, int p2) {
        ARBUniformBufferObject.glUniformBlockBinding(p0, p1, p2);
        LabyDebugContext.glError("glUniformBlockBinding", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glGetUniformIndices(int p0, PointerBuffer p1, int[] p2) {
        ARBUniformBufferObject.glGetUniformIndices(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformIndices", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetActiveUniformsiv(int p0, int[] p1, int p2, int[] p3) {
        ARBUniformBufferObject.glGetActiveUniformsiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformsiv", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetActiveUniformName(int p0, int p1, int[] p2, ByteBuffer p3) {
        ARBUniformBufferObject.glGetActiveUniformName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void glGetActiveUniformBlockiv(int p0, int p1, int p2, int[] p3) {
        ARBUniformBufferObject.glGetActiveUniformBlockiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformBlockiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetActiveUniformBlockName(int p0, int p1, int[] p2, ByteBuffer p3) {
        ARBUniformBufferObject.glGetActiveUniformBlockName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformBlockName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void glGetIntegeri_v(int p0, int p1, int[] p2) {
        ARBUniformBufferObject.glGetIntegeri_v(p0, p1, p2);
        LabyDebugContext.glError("glGetIntegeri_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

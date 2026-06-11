package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GL42C;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL42CErrorContext.class */
public final class GL42CErrorContext {
    public static void nglGetActiveAtomicCounterBufferiv(int p0, int p1, int p2, long p3) {
        GL42C.nglGetActiveAtomicCounterBufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetActiveAtomicCounterBufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetActiveAtomicCounterBufferiv(int p0, int p1, int p2, IntBuffer p3) {
        GL42C.glGetActiveAtomicCounterBufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveAtomicCounterBufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetActiveAtomicCounterBufferi(int p0, int p1, int p2) {
        int returnType = GL42C.glGetActiveAtomicCounterBufferi(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveAtomicCounterBufferi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void glTexStorage1D(int p0, int p1, int p2, int p3) {
        GL42C.glTexStorage1D(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexStorage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glTexStorage2D(int p0, int p1, int p2, int p3, int p4) {
        GL42C.glTexStorage2D(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glTexStorage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glTexStorage3D(int p0, int p1, int p2, int p3, int p4, int p5) {
        GL42C.glTexStorage3D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTexStorage3D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glDrawTransformFeedbackInstanced(int p0, int p1, int p2) {
        GL42C.glDrawTransformFeedbackInstanced(p0, p1, p2);
        LabyDebugContext.glError("glDrawTransformFeedbackInstanced", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glDrawTransformFeedbackStreamInstanced(int p0, int p1, int p2, int p3) {
        GL42C.glDrawTransformFeedbackStreamInstanced(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawTransformFeedbackStreamInstanced", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glDrawArraysInstancedBaseInstance(int p0, int p1, int p2, int p3, int p4) {
        GL42C.glDrawArraysInstancedBaseInstance(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawArraysInstancedBaseInstance", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglDrawElementsInstancedBaseInstance(int p0, int p1, int p2, long p3, int p4, int p5) {
        GL42C.nglDrawElementsInstancedBaseInstance(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglDrawElementsInstancedBaseInstance", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glDrawElementsInstancedBaseInstance(int p0, int p1, int p2, long p3, int p4, int p5) {
        GL42C.glDrawElementsInstancedBaseInstance(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glDrawElementsInstancedBaseInstance", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glDrawElementsInstancedBaseInstance(int p0, int p1, ByteBuffer p2, int p3, int p4) {
        GL42C.glDrawElementsInstancedBaseInstance(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawElementsInstancedBaseInstance", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsInstancedBaseInstance(int p0, ByteBuffer p1, int p2, int p3) {
        GL42C.glDrawElementsInstancedBaseInstance(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElementsInstancedBaseInstance", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glDrawElementsInstancedBaseInstance(int p0, ShortBuffer p1, int p2, int p3) {
        GL42C.glDrawElementsInstancedBaseInstance(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElementsInstancedBaseInstance", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glDrawElementsInstancedBaseInstance(int p0, IntBuffer p1, int p2, int p3) {
        GL42C.glDrawElementsInstancedBaseInstance(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElementsInstancedBaseInstance", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglDrawElementsInstancedBaseVertexBaseInstance(int p0, int p1, int p2, long p3, int p4, int p5, int p6) {
        GL42C.nglDrawElementsInstancedBaseVertexBaseInstance(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglDrawElementsInstancedBaseVertexBaseInstance", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int p0, int p1, int p2, long p3, int p4, int p5, int p6) {
        GL42C.glDrawElementsInstancedBaseVertexBaseInstance(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glDrawElementsInstancedBaseVertexBaseInstance", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int p0, int p1, ByteBuffer p2, int p3, int p4, int p5) {
        GL42C.glDrawElementsInstancedBaseVertexBaseInstance(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glDrawElementsInstancedBaseVertexBaseInstance", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int p0, ByteBuffer p1, int p2, int p3, int p4) {
        GL42C.glDrawElementsInstancedBaseVertexBaseInstance(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawElementsInstancedBaseVertexBaseInstance", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int p0, ShortBuffer p1, int p2, int p3, int p4) {
        GL42C.glDrawElementsInstancedBaseVertexBaseInstance(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawElementsInstancedBaseVertexBaseInstance", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int p0, IntBuffer p1, int p2, int p3, int p4) {
        GL42C.glDrawElementsInstancedBaseVertexBaseInstance(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawElementsInstancedBaseVertexBaseInstance", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glBindImageTexture(int p0, int p1, int p2, boolean p3, int p4, int p5, int p6) {
        GL42C.glBindImageTexture(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glBindImageTexture", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glMemoryBarrier(int p0) {
        GL42C.glMemoryBarrier(p0);
        LabyDebugContext.glError("glMemoryBarrier", "p0", Integer.valueOf(p0));
    }

    public static void nglGetInternalformativ(int p0, int p1, int p2, int p3, long p4) {
        GL42C.nglGetInternalformativ(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetInternalformativ", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetInternalformativ(int p0, int p1, int p2, IntBuffer p3) {
        GL42C.glGetInternalformativ(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetInternalformativ", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetInternalformati(int p0, int p1, int p2) {
        int returnType = GL42C.glGetInternalformati(p0, p1, p2);
        LabyDebugContext.glError("glGetInternalformati", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void glGetActiveAtomicCounterBufferiv(int p0, int p1, int p2, int[] p3) {
        GL42C.glGetActiveAtomicCounterBufferiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveAtomicCounterBufferiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetInternalformativ(int p0, int p1, int p2, int[] p3) {
        GL42C.glGetInternalformativ(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetInternalformativ", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }
}

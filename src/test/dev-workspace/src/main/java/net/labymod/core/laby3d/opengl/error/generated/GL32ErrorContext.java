package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL32;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL32ErrorContext.class */
public final class GL32ErrorContext {
    public static void nglGetBufferParameteri64v(int p0, int p1, long p2) {
        GL32.nglGetBufferParameteri64v(p0, p1, p2);
        LabyDebugContext.glError("nglGetBufferParameteri64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetBufferParameteri64v(int p0, int p1, LongBuffer p2) {
        GL32.glGetBufferParameteri64v(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferParameteri64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetBufferParameteri64(int p0, int p1) {
        long returnType = GL32.glGetBufferParameteri64(p0, p1);
        LabyDebugContext.glError("glGetBufferParameteri64", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglDrawElementsBaseVertex(int p0, int p1, int p2, long p3, int p4) {
        GL32.nglDrawElementsBaseVertex(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglDrawElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsBaseVertex(int p0, int p1, int p2, long p3, int p4) {
        GL32.glDrawElementsBaseVertex(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsBaseVertex(int p0, int p1, ByteBuffer p2, int p3) {
        GL32.glDrawElementsBaseVertex(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3));
    }

    public static void glDrawElementsBaseVertex(int p0, ByteBuffer p1, int p2) {
        GL32.glDrawElementsBaseVertex(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glDrawElementsBaseVertex(int p0, ShortBuffer p1, int p2) {
        GL32.glDrawElementsBaseVertex(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glDrawElementsBaseVertex(int p0, IntBuffer p1, int p2) {
        GL32.glDrawElementsBaseVertex(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglDrawRangeElementsBaseVertex(int p0, int p1, int p2, int p3, int p4, long p5, int p6) {
        GL32.nglDrawRangeElementsBaseVertex(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglDrawRangeElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glDrawRangeElementsBaseVertex(int p0, int p1, int p2, int p3, int p4, long p5, int p6) {
        GL32.glDrawRangeElementsBaseVertex(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glDrawRangeElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glDrawRangeElementsBaseVertex(int p0, int p1, int p2, int p3, ByteBuffer p4, int p5) {
        GL32.glDrawRangeElementsBaseVertex(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glDrawRangeElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4, "p5", Integer.valueOf(p5));
    }

    public static void glDrawRangeElementsBaseVertex(int p0, int p1, int p2, ByteBuffer p3, int p4) {
        GL32.glDrawRangeElementsBaseVertex(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawRangeElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Integer.valueOf(p4));
    }

    public static void glDrawRangeElementsBaseVertex(int p0, int p1, int p2, ShortBuffer p3, int p4) {
        GL32.glDrawRangeElementsBaseVertex(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawRangeElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Integer.valueOf(p4));
    }

    public static void glDrawRangeElementsBaseVertex(int p0, int p1, int p2, IntBuffer p3, int p4) {
        GL32.glDrawRangeElementsBaseVertex(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawRangeElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Integer.valueOf(p4));
    }

    public static void nglDrawElementsInstancedBaseVertex(int p0, int p1, int p2, long p3, int p4, int p5) {
        GL32.nglDrawElementsInstancedBaseVertex(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglDrawElementsInstancedBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glDrawElementsInstancedBaseVertex(int p0, int p1, int p2, long p3, int p4, int p5) {
        GL32.glDrawElementsInstancedBaseVertex(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glDrawElementsInstancedBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glDrawElementsInstancedBaseVertex(int p0, int p1, ByteBuffer p2, int p3, int p4) {
        GL32.glDrawElementsInstancedBaseVertex(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawElementsInstancedBaseVertex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsInstancedBaseVertex(int p0, ByteBuffer p1, int p2, int p3) {
        GL32.glDrawElementsInstancedBaseVertex(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElementsInstancedBaseVertex", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glDrawElementsInstancedBaseVertex(int p0, ShortBuffer p1, int p2, int p3) {
        GL32.glDrawElementsInstancedBaseVertex(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElementsInstancedBaseVertex", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glDrawElementsInstancedBaseVertex(int p0, IntBuffer p1, int p2, int p3) {
        GL32.glDrawElementsInstancedBaseVertex(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElementsInstancedBaseVertex", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglMultiDrawElementsBaseVertex(int p0, long p1, int p2, long p3, int p4, long p5) {
        GL32.nglMultiDrawElementsBaseVertex(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglMultiDrawElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glMultiDrawElementsBaseVertex(int p0, IntBuffer p1, int p2, PointerBuffer p3, IntBuffer p4) {
        GL32.glMultiDrawElementsBaseVertex(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static void glProvokingVertex(int p0) {
        GL32.glProvokingVertex(p0);
        LabyDebugContext.glError("glProvokingVertex", "p0", Integer.valueOf(p0));
    }

    public static void glTexImage2DMultisample(int p0, int p1, int p2, int p3, int p4, boolean p5) {
        GL32.glTexImage2DMultisample(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glTexImage2DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Boolean.valueOf(p5));
    }

    public static void glTexImage3DMultisample(int p0, int p1, int p2, int p3, int p4, int p5, boolean p6) {
        GL32.glTexImage3DMultisample(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexImage3DMultisample", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Boolean.valueOf(p6));
    }

    public static void nglGetMultisamplefv(int p0, int p1, long p2) {
        GL32.nglGetMultisamplefv(p0, p1, p2);
        LabyDebugContext.glError("nglGetMultisamplefv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetMultisamplefv(int p0, int p1, FloatBuffer p2) {
        GL32.glGetMultisamplefv(p0, p1, p2);
        LabyDebugContext.glError("glGetMultisamplefv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetMultisamplef(int p0, int p1) {
        float returnType = GL32.glGetMultisamplef(p0, p1);
        LabyDebugContext.glError("glGetMultisamplef", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glSampleMaski(int p0, int p1) {
        GL32.glSampleMaski(p0, p1);
        LabyDebugContext.glError("glSampleMaski", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glFramebufferTexture(int p0, int p1, int p2, int p3) {
        GL32.glFramebufferTexture(p0, p1, p2, p3);
        LabyDebugContext.glError("glFramebufferTexture", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static long glFenceSync(int p0, int p1) {
        long returnType = GL32.glFenceSync(p0, p1);
        LabyDebugContext.glError("glFenceSync", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static boolean nglIsSync(long p0) {
        boolean returnType = GL32.nglIsSync(p0);
        LabyDebugContext.glError("nglIsSync", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static boolean glIsSync(long p0) {
        boolean returnType = GL32.glIsSync(p0);
        LabyDebugContext.glError("glIsSync", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static void glDeleteSync(long p0) {
        GL32.glDeleteSync(p0);
        LabyDebugContext.glError("glDeleteSync", "p0", Long.valueOf(p0));
    }

    public static int nglClientWaitSync(long p0, int p1, long p2) {
        int returnType = GL32.nglClientWaitSync(p0, p1, p2);
        LabyDebugContext.glError("nglClientWaitSync", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glClientWaitSync(long p0, int p1, long p2) {
        int returnType = GL32.glClientWaitSync(p0, p1, p2);
        LabyDebugContext.glError("glClientWaitSync", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static void nglWaitSync(long p0, int p1, long p2) {
        GL32.nglWaitSync(p0, p1, p2);
        LabyDebugContext.glError("nglWaitSync", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glWaitSync(long p0, int p1, long p2) {
        GL32.glWaitSync(p0, p1, p2);
        LabyDebugContext.glError("glWaitSync", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void nglGetInteger64v(int p0, long p1) {
        GL32.nglGetInteger64v(p0, p1);
        LabyDebugContext.glError("nglGetInteger64v", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetInteger64v(int p0, LongBuffer p1) {
        GL32.glGetInteger64v(p0, p1);
        LabyDebugContext.glError("glGetInteger64v", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static long glGetInteger64(int p0) {
        long returnType = GL32.glGetInteger64(p0);
        LabyDebugContext.glError("glGetInteger64", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetInteger64i_v(int p0, int p1, long p2) {
        GL32.nglGetInteger64i_v(p0, p1, p2);
        LabyDebugContext.glError("nglGetInteger64i_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetInteger64i_v(int p0, int p1, LongBuffer p2) {
        GL32.glGetInteger64i_v(p0, p1, p2);
        LabyDebugContext.glError("glGetInteger64i_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetInteger64i(int p0, int p1) {
        long returnType = GL32.glGetInteger64i(p0, p1);
        LabyDebugContext.glError("glGetInteger64i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetSynciv(long p0, int p1, int p2, long p3, long p4) {
        GL32.nglGetSynciv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetSynciv", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetSynciv(long p0, int p1, IntBuffer p2, IntBuffer p3) {
        GL32.glGetSynciv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetSynciv", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static int glGetSynci(long p0, int p1, IntBuffer p2) {
        int returnType = GL32.glGetSynci(p0, p1, p2);
        LabyDebugContext.glError("glGetSynci", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static void glGetBufferParameteri64v(int p0, int p1, long[] p2) {
        GL32.glGetBufferParameteri64v(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferParameteri64v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMultiDrawElementsBaseVertex(int p0, int[] p1, int p2, PointerBuffer p3, int[] p4) {
        GL32.glMultiDrawElementsBaseVertex(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawElementsBaseVertex", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static void glGetMultisamplefv(int p0, int p1, float[] p2) {
        GL32.glGetMultisamplefv(p0, p1, p2);
        LabyDebugContext.glError("glGetMultisamplefv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetInteger64v(int p0, long[] p1) {
        GL32.glGetInteger64v(p0, p1);
        LabyDebugContext.glError("glGetInteger64v", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetInteger64i_v(int p0, int p1, long[] p2) {
        GL32.glGetInteger64i_v(p0, p1, p2);
        LabyDebugContext.glError("glGetInteger64i_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetSynciv(long p0, int p1, int[] p2, int[] p3) {
        GL32.glGetSynciv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetSynciv", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }
}

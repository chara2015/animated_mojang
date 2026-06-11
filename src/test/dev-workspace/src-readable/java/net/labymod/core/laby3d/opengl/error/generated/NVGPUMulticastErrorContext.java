package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVGPUMulticast;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVGPUMulticastErrorContext.class */
public final class NVGPUMulticastErrorContext {
    public static void glRenderGpuMaskNV(int p0) {
        NVGPUMulticast.glRenderGpuMaskNV(p0);
        LabyDebugContext.glError("glRenderGpuMaskNV", "p0", Integer.valueOf(p0));
    }

    public static void nglMulticastBufferSubDataNV(int p0, int p1, long p2, long p3, long p4) {
        NVGPUMulticast.nglMulticastBufferSubDataNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglMulticastBufferSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glMulticastBufferSubDataNV(int p0, int p1, long p2, ByteBuffer p3) {
        NVGPUMulticast.glMulticastBufferSubDataNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastBufferSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
    }

    public static void glMulticastBufferSubDataNV(int p0, int p1, long p2, ShortBuffer p3) {
        NVGPUMulticast.glMulticastBufferSubDataNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastBufferSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
    }

    public static void glMulticastBufferSubDataNV(int p0, int p1, long p2, IntBuffer p3) {
        NVGPUMulticast.glMulticastBufferSubDataNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastBufferSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
    }

    public static void glMulticastBufferSubDataNV(int p0, int p1, long p2, FloatBuffer p3) {
        NVGPUMulticast.glMulticastBufferSubDataNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastBufferSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
    }

    public static void glMulticastBufferSubDataNV(int p0, int p1, long p2, DoubleBuffer p3) {
        NVGPUMulticast.glMulticastBufferSubDataNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastBufferSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
    }

    public static void glMulticastCopyBufferSubDataNV(int p0, int p1, int p2, int p3, long p4, long p5, long p6) {
        NVGPUMulticast.glMulticastCopyBufferSubDataNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glMulticastCopyBufferSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glMulticastCopyImageSubDataNV(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11, int p12, int p13, int p14, int p15, int p16) {
        NVGPUMulticast.glMulticastCopyImageSubDataNV(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16);
        LabyDebugContext.glError("glMulticastCopyImageSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Integer.valueOf(p11), "p12", Integer.valueOf(p12), "p13", Integer.valueOf(p13), "p14", Integer.valueOf(p14), "p15", Integer.valueOf(p15), "p16", Integer.valueOf(p16));
    }

    public static void glMulticastBlitFramebufferNV(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11) {
        NVGPUMulticast.glMulticastBlitFramebufferNV(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
        LabyDebugContext.glError("glMulticastBlitFramebufferNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Integer.valueOf(p11));
    }

    public static void nglMulticastFramebufferSampleLocationsfvNV(int p0, int p1, int p2, int p3, long p4) {
        NVGPUMulticast.nglMulticastFramebufferSampleLocationsfvNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglMulticastFramebufferSampleLocationsfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glMulticastFramebufferSampleLocationsfvNV(int p0, int p1, int p2, FloatBuffer p3) {
        NVGPUMulticast.glMulticastFramebufferSampleLocationsfvNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastFramebufferSampleLocationsfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glMulticastBarrierNV() {
        NVGPUMulticast.glMulticastBarrierNV();
        LabyDebugContext.glError("glMulticastBarrierNV", new Object[0]);
    }

    public static void glMulticastWaitSyncNV(int p0, int p1) {
        NVGPUMulticast.glMulticastWaitSyncNV(p0, p1);
        LabyDebugContext.glError("glMulticastWaitSyncNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglMulticastGetQueryObjectivNV(int p0, int p1, int p2, long p3) {
        NVGPUMulticast.nglMulticastGetQueryObjectivNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMulticastGetQueryObjectivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glMulticastGetQueryObjectivNV(int p0, int p1, int p2, IntBuffer p3) {
        NVGPUMulticast.glMulticastGetQueryObjectivNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastGetQueryObjectivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glMulticastGetQueryObjectiNV(int p0, int p1, int p2) {
        int returnType = NVGPUMulticast.glMulticastGetQueryObjectiNV(p0, p1, p2);
        LabyDebugContext.glError("glMulticastGetQueryObjectiNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglMulticastGetQueryObjectuivNV(int p0, int p1, int p2, long p3) {
        NVGPUMulticast.nglMulticastGetQueryObjectuivNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMulticastGetQueryObjectuivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glMulticastGetQueryObjectuivNV(int p0, int p1, int p2, IntBuffer p3) {
        NVGPUMulticast.glMulticastGetQueryObjectuivNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastGetQueryObjectuivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glMulticastGetQueryObjectuiNV(int p0, int p1, int p2) {
        int returnType = NVGPUMulticast.glMulticastGetQueryObjectuiNV(p0, p1, p2);
        LabyDebugContext.glError("glMulticastGetQueryObjectuiNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglMulticastGetQueryObjecti64vNV(int p0, int p1, int p2, long p3) {
        NVGPUMulticast.nglMulticastGetQueryObjecti64vNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMulticastGetQueryObjecti64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glMulticastGetQueryObjecti64vNV(int p0, int p1, int p2, LongBuffer p3) {
        NVGPUMulticast.glMulticastGetQueryObjecti64vNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastGetQueryObjecti64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static long glMulticastGetQueryObjecti64NV(int p0, int p1, int p2) {
        long returnType = NVGPUMulticast.glMulticastGetQueryObjecti64NV(p0, p1, p2);
        LabyDebugContext.glError("glMulticastGetQueryObjecti64NV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglMulticastGetQueryObjectui64vNV(int p0, int p1, int p2, long p3) {
        NVGPUMulticast.nglMulticastGetQueryObjectui64vNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMulticastGetQueryObjectui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glMulticastGetQueryObjectui64vNV(int p0, int p1, int p2, LongBuffer p3) {
        NVGPUMulticast.glMulticastGetQueryObjectui64vNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastGetQueryObjectui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static long glMulticastGetQueryObjectui64NV(int p0, int p1, int p2) {
        long returnType = NVGPUMulticast.glMulticastGetQueryObjectui64NV(p0, p1, p2);
        LabyDebugContext.glError("glMulticastGetQueryObjectui64NV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void glMulticastBufferSubDataNV(int p0, int p1, long p2, short[] p3) {
        NVGPUMulticast.glMulticastBufferSubDataNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastBufferSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
    }

    public static void glMulticastBufferSubDataNV(int p0, int p1, long p2, int[] p3) {
        NVGPUMulticast.glMulticastBufferSubDataNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastBufferSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
    }

    public static void glMulticastBufferSubDataNV(int p0, int p1, long p2, float[] p3) {
        NVGPUMulticast.glMulticastBufferSubDataNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastBufferSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
    }

    public static void glMulticastBufferSubDataNV(int p0, int p1, long p2, double[] p3) {
        NVGPUMulticast.glMulticastBufferSubDataNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastBufferSubDataNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
    }

    public static void glMulticastFramebufferSampleLocationsfvNV(int p0, int p1, int p2, float[] p3) {
        NVGPUMulticast.glMulticastFramebufferSampleLocationsfvNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastFramebufferSampleLocationsfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glMulticastGetQueryObjectivNV(int p0, int p1, int p2, int[] p3) {
        NVGPUMulticast.glMulticastGetQueryObjectivNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastGetQueryObjectivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glMulticastGetQueryObjectuivNV(int p0, int p1, int p2, int[] p3) {
        NVGPUMulticast.glMulticastGetQueryObjectuivNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastGetQueryObjectuivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glMulticastGetQueryObjecti64vNV(int p0, int p1, int p2, long[] p3) {
        NVGPUMulticast.glMulticastGetQueryObjecti64vNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastGetQueryObjecti64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glMulticastGetQueryObjectui64vNV(int p0, int p1, int p2, long[] p3) {
        NVGPUMulticast.glMulticastGetQueryObjectui64vNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastGetQueryObjectui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }
}

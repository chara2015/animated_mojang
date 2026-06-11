package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVXGpuMulticast2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVXGpuMulticast2ErrorContext.class */
public final class NVXGpuMulticast2ErrorContext {
    public static int nglAsyncCopyImageSubDataNVX(int p0, long p1, long p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11, int p12, int p13, int p14, int p15, int p16, int p17, int p18, int p19, int p20, long p21, long p22) {
        int returnType = NVXGpuMulticast2.nglAsyncCopyImageSubDataNVX(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22);
        LabyDebugContext.glError("nglAsyncCopyImageSubDataNVX", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Integer.valueOf(p11), "p12", Integer.valueOf(p12), "p13", Integer.valueOf(p13), "p14", Integer.valueOf(p14), "p15", Integer.valueOf(p15), "p16", Integer.valueOf(p16), "p17", Integer.valueOf(p17), "p18", Integer.valueOf(p18), "p19", Integer.valueOf(p19), "p20", Integer.valueOf(p20), "p21", Long.valueOf(p21), "p22", Long.valueOf(p22));
        return returnType;
    }

    public static int glAsyncCopyImageSubDataNVX(IntBuffer p0, LongBuffer p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11, int p12, int p13, int p14, int p15, int p16, int p17, int p18, IntBuffer p19, LongBuffer p20) {
        int returnType = NVXGpuMulticast2.glAsyncCopyImageSubDataNVX(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20);
        LabyDebugContext.glError("glAsyncCopyImageSubDataNVX", "p0", p0, "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Integer.valueOf(p11), "p12", Integer.valueOf(p12), "p13", Integer.valueOf(p13), "p14", Integer.valueOf(p14), "p15", Integer.valueOf(p15), "p16", Integer.valueOf(p16), "p17", Integer.valueOf(p17), "p18", Integer.valueOf(p18), "p19", p19, "p20", p20);
        return returnType;
    }

    public static long nglAsyncCopyBufferSubDataNVX(int p0, long p1, long p2, int p3, int p4, int p5, int p6, long p7, long p8, long p9, int p10, long p11, long p12) {
        long returnType = NVXGpuMulticast2.nglAsyncCopyBufferSubDataNVX(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12);
        LabyDebugContext.glError("nglAsyncCopyBufferSubDataNVX", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7), "p8", Long.valueOf(p8), "p9", Long.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Long.valueOf(p11), "p12", Long.valueOf(p12));
        return returnType;
    }

    public static long glAsyncCopyBufferSubDataNVX(IntBuffer p0, LongBuffer p1, int p2, int p3, int p4, int p5, long p6, long p7, long p8, IntBuffer p9, LongBuffer p10) {
        long returnType = NVXGpuMulticast2.glAsyncCopyBufferSubDataNVX(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glAsyncCopyBufferSubDataNVX", "p0", p0, "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6), "p7", Long.valueOf(p7), "p8", Long.valueOf(p8), "p9", p9, "p10", p10);
        return returnType;
    }

    public static void glUploadGpuMaskNVX(int p0) {
        NVXGpuMulticast2.glUploadGpuMaskNVX(p0);
        LabyDebugContext.glError("glUploadGpuMaskNVX", "p0", Integer.valueOf(p0));
    }

    public static void nglMulticastViewportArrayvNVX(int p0, int p1, int p2, long p3) {
        NVXGpuMulticast2.nglMulticastViewportArrayvNVX(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMulticastViewportArrayvNVX", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glMulticastViewportArrayvNVX(int p0, int p1, FloatBuffer p2) {
        NVXGpuMulticast2.glMulticastViewportArrayvNVX(p0, p1, p2);
        LabyDebugContext.glError("glMulticastViewportArrayvNVX", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglMulticastScissorArrayvNVX(int p0, int p1, int p2, long p3) {
        NVXGpuMulticast2.nglMulticastScissorArrayvNVX(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMulticastScissorArrayvNVX", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glMulticastScissorArrayvNVX(int p0, int p1, IntBuffer p2) {
        NVXGpuMulticast2.glMulticastScissorArrayvNVX(p0, p1, p2);
        LabyDebugContext.glError("glMulticastScissorArrayvNVX", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMulticastViewportPositionWScaleNVX(int p0, int p1, float p2, float p3) {
        NVXGpuMulticast2.glMulticastViewportPositionWScaleNVX(p0, p1, p2, p3);
        LabyDebugContext.glError("glMulticastViewportPositionWScaleNVX", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static int glAsyncCopyImageSubDataNVX(int[] p0, long[] p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11, int p12, int p13, int p14, int p15, int p16, int p17, int p18, int[] p19, long[] p20) {
        int returnType = NVXGpuMulticast2.glAsyncCopyImageSubDataNVX(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20);
        LabyDebugContext.glError("glAsyncCopyImageSubDataNVX", "p0", p0, "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Integer.valueOf(p11), "p12", Integer.valueOf(p12), "p13", Integer.valueOf(p13), "p14", Integer.valueOf(p14), "p15", Integer.valueOf(p15), "p16", Integer.valueOf(p16), "p17", Integer.valueOf(p17), "p18", Integer.valueOf(p18), "p19", p19, "p20", p20);
        return returnType;
    }

    public static long glAsyncCopyBufferSubDataNVX(int[] p0, long[] p1, int p2, int p3, int p4, int p5, long p6, long p7, long p8, int[] p9, long[] p10) {
        long returnType = NVXGpuMulticast2.glAsyncCopyBufferSubDataNVX(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glAsyncCopyBufferSubDataNVX", "p0", p0, "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6), "p7", Long.valueOf(p7), "p8", Long.valueOf(p8), "p9", p9, "p10", p10);
        return returnType;
    }

    public static void glMulticastViewportArrayvNVX(int p0, int p1, float[] p2) {
        NVXGpuMulticast2.glMulticastViewportArrayvNVX(p0, p1, p2);
        LabyDebugContext.glError("glMulticastViewportArrayvNVX", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMulticastScissorArrayvNVX(int p0, int p1, int[] p2) {
        NVXGpuMulticast2.glMulticastScissorArrayvNVX(p0, p1, p2);
        LabyDebugContext.glError("glMulticastScissorArrayvNVX", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

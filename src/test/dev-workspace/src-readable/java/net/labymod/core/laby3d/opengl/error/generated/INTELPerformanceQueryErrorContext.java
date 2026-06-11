package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.INTELPerformanceQuery;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/INTELPerformanceQueryErrorContext.class */
public final class INTELPerformanceQueryErrorContext {
    public static void glBeginPerfQueryINTEL(int p0) {
        INTELPerformanceQuery.glBeginPerfQueryINTEL(p0);
        LabyDebugContext.glError("glBeginPerfQueryINTEL", "p0", Integer.valueOf(p0));
    }

    public static void nglCreatePerfQueryINTEL(int p0, long p1) {
        INTELPerformanceQuery.nglCreatePerfQueryINTEL(p0, p1);
        LabyDebugContext.glError("nglCreatePerfQueryINTEL", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreatePerfQueryINTEL(int p0, IntBuffer p1) {
        INTELPerformanceQuery.glCreatePerfQueryINTEL(p0, p1);
        LabyDebugContext.glError("glCreatePerfQueryINTEL", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static int glCreatePerfQueryINTEL(int p0) {
        int returnType = INTELPerformanceQuery.glCreatePerfQueryINTEL(p0);
        LabyDebugContext.glError("glCreatePerfQueryINTEL", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glDeletePerfQueryINTEL(int p0) {
        INTELPerformanceQuery.glDeletePerfQueryINTEL(p0);
        LabyDebugContext.glError("glDeletePerfQueryINTEL", "p0", Integer.valueOf(p0));
    }

    public static void glEndPerfQueryINTEL(int p0) {
        INTELPerformanceQuery.glEndPerfQueryINTEL(p0);
        LabyDebugContext.glError("glEndPerfQueryINTEL", "p0", Integer.valueOf(p0));
    }

    public static void nglGetFirstPerfQueryIdINTEL(long p0) {
        INTELPerformanceQuery.nglGetFirstPerfQueryIdINTEL(p0);
        LabyDebugContext.glError("nglGetFirstPerfQueryIdINTEL", "p0", Long.valueOf(p0));
    }

    public static void glGetFirstPerfQueryIdINTEL(IntBuffer p0) {
        INTELPerformanceQuery.glGetFirstPerfQueryIdINTEL(p0);
        LabyDebugContext.glError("glGetFirstPerfQueryIdINTEL", "p0", p0);
    }

    public static int glGetFirstPerfQueryIdINTEL() {
        int returnType = INTELPerformanceQuery.glGetFirstPerfQueryIdINTEL();
        LabyDebugContext.glError("glGetFirstPerfQueryIdINTEL", new Object[0]);
        return returnType;
    }

    public static void nglGetNextPerfQueryIdINTEL(int p0, long p1) {
        INTELPerformanceQuery.nglGetNextPerfQueryIdINTEL(p0, p1);
        LabyDebugContext.glError("nglGetNextPerfQueryIdINTEL", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetNextPerfQueryIdINTEL(int p0, IntBuffer p1) {
        INTELPerformanceQuery.glGetNextPerfQueryIdINTEL(p0, p1);
        LabyDebugContext.glError("glGetNextPerfQueryIdINTEL", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static int glGetNextPerfQueryIdINTEL(int p0) {
        int returnType = INTELPerformanceQuery.glGetNextPerfQueryIdINTEL(p0);
        LabyDebugContext.glError("glGetNextPerfQueryIdINTEL", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetPerfCounterInfoINTEL(int p0, int p1, int p2, long p3, int p4, long p5, long p6, long p7, long p8, long p9, long p10) {
        INTELPerformanceQuery.nglGetPerfCounterInfoINTEL(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("nglGetPerfCounterInfoINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6), "p7", Long.valueOf(p7), "p8", Long.valueOf(p8), "p9", Long.valueOf(p9), "p10", Long.valueOf(p10));
    }

    public static void glGetPerfCounterInfoINTEL(int p0, int p1, ByteBuffer p2, ByteBuffer p3, IntBuffer p4, IntBuffer p5, IntBuffer p6, IntBuffer p7, LongBuffer p8) {
        INTELPerformanceQuery.glGetPerfCounterInfoINTEL(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glGetPerfCounterInfoINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5, "p6", p6, "p7", p7, "p8", p8);
    }

    public static void nglGetPerfQueryDataINTEL(int p0, int p1, int p2, long p3, long p4) {
        INTELPerformanceQuery.nglGetPerfQueryDataINTEL(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetPerfQueryDataINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetPerfQueryDataINTEL(int p0, int p1, ByteBuffer p2, IntBuffer p3) {
        INTELPerformanceQuery.glGetPerfQueryDataINTEL(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfQueryDataINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void nglGetPerfQueryIdByNameINTEL(long p0, long p1) {
        INTELPerformanceQuery.nglGetPerfQueryIdByNameINTEL(p0, p1);
        LabyDebugContext.glError("nglGetPerfQueryIdByNameINTEL", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetPerfQueryIdByNameINTEL(ByteBuffer p0, IntBuffer p1) {
        INTELPerformanceQuery.glGetPerfQueryIdByNameINTEL(p0, p1);
        LabyDebugContext.glError("glGetPerfQueryIdByNameINTEL", "p0", p0, "p1", p1);
    }

    public static void glGetPerfQueryIdByNameINTEL(CharSequence p0, IntBuffer p1) {
        INTELPerformanceQuery.glGetPerfQueryIdByNameINTEL(p0, p1);
        LabyDebugContext.glError("glGetPerfQueryIdByNameINTEL", "p0", p0, "p1", p1);
    }

    public static int glGetPerfQueryIdByNameINTEL(CharSequence p0) {
        int returnType = INTELPerformanceQuery.glGetPerfQueryIdByNameINTEL(p0);
        LabyDebugContext.glError("glGetPerfQueryIdByNameINTEL", "p0", p0);
        return returnType;
    }

    public static void nglGetPerfQueryInfoINTEL(int p0, int p1, long p2, long p3, long p4, long p5, long p6) {
        INTELPerformanceQuery.nglGetPerfQueryInfoINTEL(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglGetPerfQueryInfoINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glGetPerfQueryInfoINTEL(int p0, ByteBuffer p1, IntBuffer p2, IntBuffer p3, IntBuffer p4, IntBuffer p5) {
        INTELPerformanceQuery.glGetPerfQueryInfoINTEL(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetPerfQueryInfoINTEL", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static void glCreatePerfQueryINTEL(int p0, int[] p1) {
        INTELPerformanceQuery.glCreatePerfQueryINTEL(p0, p1);
        LabyDebugContext.glError("glCreatePerfQueryINTEL", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetFirstPerfQueryIdINTEL(int[] p0) {
        INTELPerformanceQuery.glGetFirstPerfQueryIdINTEL(p0);
        LabyDebugContext.glError("glGetFirstPerfQueryIdINTEL", "p0", p0);
    }

    public static void glGetNextPerfQueryIdINTEL(int p0, int[] p1) {
        INTELPerformanceQuery.glGetNextPerfQueryIdINTEL(p0, p1);
        LabyDebugContext.glError("glGetNextPerfQueryIdINTEL", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetPerfCounterInfoINTEL(int p0, int p1, ByteBuffer p2, ByteBuffer p3, int[] p4, int[] p5, int[] p6, int[] p7, long[] p8) {
        INTELPerformanceQuery.glGetPerfCounterInfoINTEL(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glGetPerfCounterInfoINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5, "p6", p6, "p7", p7, "p8", p8);
    }

    public static void glGetPerfQueryDataINTEL(int p0, int p1, ByteBuffer p2, int[] p3) {
        INTELPerformanceQuery.glGetPerfQueryDataINTEL(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfQueryDataINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void glGetPerfQueryIdByNameINTEL(ByteBuffer p0, int[] p1) {
        INTELPerformanceQuery.glGetPerfQueryIdByNameINTEL(p0, p1);
        LabyDebugContext.glError("glGetPerfQueryIdByNameINTEL", "p0", p0, "p1", p1);
    }

    public static void glGetPerfQueryIdByNameINTEL(CharSequence p0, int[] p1) {
        INTELPerformanceQuery.glGetPerfQueryIdByNameINTEL(p0, p1);
        LabyDebugContext.glError("glGetPerfQueryIdByNameINTEL", "p0", p0, "p1", p1);
    }

    public static void glGetPerfQueryInfoINTEL(int p0, ByteBuffer p1, int[] p2, int[] p3, int[] p4, int[] p5) {
        INTELPerformanceQuery.glGetPerfQueryInfoINTEL(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetPerfQueryInfoINTEL", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }
}

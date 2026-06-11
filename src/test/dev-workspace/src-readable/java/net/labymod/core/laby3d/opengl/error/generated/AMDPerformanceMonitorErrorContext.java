package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.AMDPerformanceMonitor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/AMDPerformanceMonitorErrorContext.class */
public final class AMDPerformanceMonitorErrorContext {
    public static void nglGetPerfMonitorGroupsAMD(long p0, int p1, long p2) {
        AMDPerformanceMonitor.nglGetPerfMonitorGroupsAMD(p0, p1, p2);
        LabyDebugContext.glError("nglGetPerfMonitorGroupsAMD", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetPerfMonitorGroupsAMD(IntBuffer p0, IntBuffer p1) {
        AMDPerformanceMonitor.glGetPerfMonitorGroupsAMD(p0, p1);
        LabyDebugContext.glError("glGetPerfMonitorGroupsAMD", "p0", p0, "p1", p1);
    }

    public static void nglGetPerfMonitorCountersAMD(int p0, long p1, long p2, int p3, long p4) {
        AMDPerformanceMonitor.nglGetPerfMonitorCountersAMD(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetPerfMonitorCountersAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetPerfMonitorCountersAMD(int p0, IntBuffer p1, IntBuffer p2, IntBuffer p3) {
        AMDPerformanceMonitor.glGetPerfMonitorCountersAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfMonitorCountersAMD", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void nglGetPerfMonitorGroupStringAMD(int p0, int p1, long p2, long p3) {
        AMDPerformanceMonitor.nglGetPerfMonitorGroupStringAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetPerfMonitorGroupStringAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetPerfMonitorGroupStringAMD(int p0, IntBuffer p1, ByteBuffer p2) {
        AMDPerformanceMonitor.glGetPerfMonitorGroupStringAMD(p0, p1, p2);
        LabyDebugContext.glError("glGetPerfMonitorGroupStringAMD", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void nglGetPerfMonitorCounterStringAMD(int p0, int p1, int p2, long p3, long p4) {
        AMDPerformanceMonitor.nglGetPerfMonitorCounterStringAMD(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetPerfMonitorCounterStringAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetPerfMonitorCounterStringAMD(int p0, int p1, IntBuffer p2, ByteBuffer p3) {
        AMDPerformanceMonitor.glGetPerfMonitorCounterStringAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfMonitorCounterStringAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void nglGetPerfMonitorCounterInfoAMD(int p0, int p1, int p2, long p3) {
        AMDPerformanceMonitor.nglGetPerfMonitorCounterInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetPerfMonitorCounterInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetPerfMonitorCounterInfoAMD(int p0, int p1, int p2, ByteBuffer p3) {
        AMDPerformanceMonitor.glGetPerfMonitorCounterInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfMonitorCounterInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetPerfMonitorCounterInfoAMD(int p0, int p1, int p2, IntBuffer p3) {
        AMDPerformanceMonitor.glGetPerfMonitorCounterInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfMonitorCounterInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetPerfMonitorCounterInfoAMD(int p0, int p1, int p2, FloatBuffer p3) {
        AMDPerformanceMonitor.glGetPerfMonitorCounterInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfMonitorCounterInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglGenPerfMonitorsAMD(int p0, long p1) {
        AMDPerformanceMonitor.nglGenPerfMonitorsAMD(p0, p1);
        LabyDebugContext.glError("nglGenPerfMonitorsAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenPerfMonitorsAMD(IntBuffer p0) {
        AMDPerformanceMonitor.glGenPerfMonitorsAMD(p0);
        LabyDebugContext.glError("glGenPerfMonitorsAMD", "p0", p0);
    }

    public static int glGenPerfMonitorsAMD() {
        int returnType = AMDPerformanceMonitor.glGenPerfMonitorsAMD();
        LabyDebugContext.glError("glGenPerfMonitorsAMD", new Object[0]);
        return returnType;
    }

    public static void nglDeletePerfMonitorsAMD(int p0, long p1) {
        AMDPerformanceMonitor.nglDeletePerfMonitorsAMD(p0, p1);
        LabyDebugContext.glError("nglDeletePerfMonitorsAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeletePerfMonitorsAMD(IntBuffer p0) {
        AMDPerformanceMonitor.glDeletePerfMonitorsAMD(p0);
        LabyDebugContext.glError("glDeletePerfMonitorsAMD", "p0", p0);
    }

    public static void glDeletePerfMonitorsAMD(int p0) {
        AMDPerformanceMonitor.glDeletePerfMonitorsAMD(p0);
        LabyDebugContext.glError("glDeletePerfMonitorsAMD", "p0", Integer.valueOf(p0));
    }

    public static void nglSelectPerfMonitorCountersAMD(int p0, boolean p1, int p2, int p3, long p4) {
        AMDPerformanceMonitor.nglSelectPerfMonitorCountersAMD(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglSelectPerfMonitorCountersAMD", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glSelectPerfMonitorCountersAMD(int p0, boolean p1, int p2, IntBuffer p3) {
        AMDPerformanceMonitor.glSelectPerfMonitorCountersAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glSelectPerfMonitorCountersAMD", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glBeginPerfMonitorAMD(int p0) {
        AMDPerformanceMonitor.glBeginPerfMonitorAMD(p0);
        LabyDebugContext.glError("glBeginPerfMonitorAMD", "p0", Integer.valueOf(p0));
    }

    public static void glEndPerfMonitorAMD(int p0) {
        AMDPerformanceMonitor.glEndPerfMonitorAMD(p0);
        LabyDebugContext.glError("glEndPerfMonitorAMD", "p0", Integer.valueOf(p0));
    }

    public static void nglGetPerfMonitorCounterDataAMD(int p0, int p1, int p2, long p3, long p4) {
        AMDPerformanceMonitor.nglGetPerfMonitorCounterDataAMD(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetPerfMonitorCounterDataAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetPerfMonitorCounterDataAMD(int p0, int p1, IntBuffer p2, IntBuffer p3) {
        AMDPerformanceMonitor.glGetPerfMonitorCounterDataAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfMonitorCounterDataAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void glGetPerfMonitorGroupsAMD(int[] p0, int[] p1) {
        AMDPerformanceMonitor.glGetPerfMonitorGroupsAMD(p0, p1);
        LabyDebugContext.glError("glGetPerfMonitorGroupsAMD", "p0", p0, "p1", p1);
    }

    public static void glGetPerfMonitorCountersAMD(int p0, int[] p1, int[] p2, int[] p3) {
        AMDPerformanceMonitor.glGetPerfMonitorCountersAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfMonitorCountersAMD", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void glGetPerfMonitorGroupStringAMD(int p0, int[] p1, ByteBuffer p2) {
        AMDPerformanceMonitor.glGetPerfMonitorGroupStringAMD(p0, p1, p2);
        LabyDebugContext.glError("glGetPerfMonitorGroupStringAMD", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetPerfMonitorCounterStringAMD(int p0, int p1, int[] p2, ByteBuffer p3) {
        AMDPerformanceMonitor.glGetPerfMonitorCounterStringAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfMonitorCounterStringAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void glGetPerfMonitorCounterInfoAMD(int p0, int p1, int p2, int[] p3) {
        AMDPerformanceMonitor.glGetPerfMonitorCounterInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfMonitorCounterInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetPerfMonitorCounterInfoAMD(int p0, int p1, int p2, float[] p3) {
        AMDPerformanceMonitor.glGetPerfMonitorCounterInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfMonitorCounterInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGenPerfMonitorsAMD(int[] p0) {
        AMDPerformanceMonitor.glGenPerfMonitorsAMD(p0);
        LabyDebugContext.glError("glGenPerfMonitorsAMD", "p0", p0);
    }

    public static void glDeletePerfMonitorsAMD(int[] p0) {
        AMDPerformanceMonitor.glDeletePerfMonitorsAMD(p0);
        LabyDebugContext.glError("glDeletePerfMonitorsAMD", "p0", p0);
    }

    public static void glSelectPerfMonitorCountersAMD(int p0, boolean p1, int p2, int[] p3) {
        AMDPerformanceMonitor.glSelectPerfMonitorCountersAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glSelectPerfMonitorCountersAMD", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetPerfMonitorCounterDataAMD(int p0, int p1, int[] p2, int[] p3) {
        AMDPerformanceMonitor.glGetPerfMonitorCounterDataAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetPerfMonitorCounterDataAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GPU_DEVICE;
import org.lwjgl.opengl.WGLNVGPUAffinity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLNVGPUAffinityErrorContext.class */
public final class WGLNVGPUAffinityErrorContext {
    public static int nwglEnumGpusNV(int p0, long p1) {
        int returnType = WGLNVGPUAffinity.nwglEnumGpusNV(p0, p1);
        LabyDebugContext.glError("nwglEnumGpusNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static boolean wglEnumGpusNV(int p0, PointerBuffer p1) {
        boolean returnType = WGLNVGPUAffinity.wglEnumGpusNV(p0, p1);
        LabyDebugContext.glError("wglEnumGpusNV", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int nwglEnumGpuDevicesNV(long p0, int p1, long p2) {
        int returnType = WGLNVGPUAffinity.nwglEnumGpuDevicesNV(p0, p1, p2);
        LabyDebugContext.glError("nwglEnumGpuDevicesNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean wglEnumGpuDevicesNV(long p0, int p1, GPU_DEVICE p2) {
        boolean returnType = WGLNVGPUAffinity.wglEnumGpuDevicesNV(p0, p1, p2);
        LabyDebugContext.glError("wglEnumGpuDevicesNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static long nwglCreateAffinityDCNV(long p0) {
        long returnType = WGLNVGPUAffinity.nwglCreateAffinityDCNV(p0);
        LabyDebugContext.glError("nwglCreateAffinityDCNV", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long wglCreateAffinityDCNV(PointerBuffer p0) {
        long returnType = WGLNVGPUAffinity.wglCreateAffinityDCNV(p0);
        LabyDebugContext.glError("wglCreateAffinityDCNV", "p0", p0);
        return returnType;
    }

    public static int nwglEnumGpusFromAffinityDCNV(long p0, int p1, long p2) {
        int returnType = WGLNVGPUAffinity.nwglEnumGpusFromAffinityDCNV(p0, p1, p2);
        LabyDebugContext.glError("nwglEnumGpusFromAffinityDCNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean wglEnumGpusFromAffinityDCNV(long p0, int p1, PointerBuffer p2) {
        boolean returnType = WGLNVGPUAffinity.wglEnumGpusFromAffinityDCNV(p0, p1, p2);
        LabyDebugContext.glError("wglEnumGpusFromAffinityDCNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static boolean wglDeleteDCNV(long p0) {
        boolean returnType = WGLNVGPUAffinity.wglDeleteDCNV(p0);
        LabyDebugContext.glError("wglDeleteDCNV", "p0", Long.valueOf(p0));
        return returnType;
    }
}

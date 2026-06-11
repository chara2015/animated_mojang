package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLAMDGPUAssociation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLAMDGPUAssociationErrorContext.class */
public final class WGLAMDGPUAssociationErrorContext {
    public static int nwglGetGPUIDsAMD(int p0, long p1) {
        int returnType = WGLAMDGPUAssociation.nwglGetGPUIDsAMD(p0, p1);
        LabyDebugContext.glError("nwglGetGPUIDsAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int wglGetGPUIDsAMD(IntBuffer p0) {
        int returnType = WGLAMDGPUAssociation.wglGetGPUIDsAMD(p0);
        LabyDebugContext.glError("wglGetGPUIDsAMD", "p0", p0);
        return returnType;
    }

    public static int nwglGetGPUInfoAMD(int p0, int p1, int p2, int p3, long p4) {
        int returnType = WGLAMDGPUAssociation.nwglGetGPUInfoAMD(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nwglGetGPUInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
        return returnType;
    }

    public static int wglGetGPUInfoAMD(int p0, int p1, int p2, ByteBuffer p3) {
        int returnType = WGLAMDGPUAssociation.wglGetGPUInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("wglGetGPUInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static int wglGetGPUInfoAMD(int p0, int p1, int p2, IntBuffer p3) {
        int returnType = WGLAMDGPUAssociation.wglGetGPUInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("wglGetGPUInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static int wglGetGPUInfoAMD(int p0, int p1, int p2, FloatBuffer p3) {
        int returnType = WGLAMDGPUAssociation.wglGetGPUInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("wglGetGPUInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static int wglGetContextGPUIDAMD(long p0) {
        int returnType = WGLAMDGPUAssociation.wglGetContextGPUIDAMD(p0);
        LabyDebugContext.glError("wglGetContextGPUIDAMD", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long wglCreateAssociatedContextAMD(int p0) {
        long returnType = WGLAMDGPUAssociation.wglCreateAssociatedContextAMD(p0);
        LabyDebugContext.glError("wglCreateAssociatedContextAMD", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static long nwglCreateAssociatedContextAttribsAMD(int p0, long p1, long p2) {
        long returnType = WGLAMDGPUAssociation.nwglCreateAssociatedContextAttribsAMD(p0, p1, p2);
        LabyDebugContext.glError("nwglCreateAssociatedContextAttribsAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static long wglCreateAssociatedContextAttribsAMD(int p0, long p1, IntBuffer p2) {
        long returnType = WGLAMDGPUAssociation.wglCreateAssociatedContextAttribsAMD(p0, p1, p2);
        LabyDebugContext.glError("wglCreateAssociatedContextAttribsAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static boolean wglDeleteAssociatedContextAMD(long p0) {
        boolean returnType = WGLAMDGPUAssociation.wglDeleteAssociatedContextAMD(p0);
        LabyDebugContext.glError("wglDeleteAssociatedContextAMD", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static boolean wglMakeAssociatedContextCurrentAMD(long p0) {
        boolean returnType = WGLAMDGPUAssociation.wglMakeAssociatedContextCurrentAMD(p0);
        LabyDebugContext.glError("wglMakeAssociatedContextCurrentAMD", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long wglGetCurrentAssociatedContextAMD() {
        long returnType = WGLAMDGPUAssociation.wglGetCurrentAssociatedContextAMD();
        LabyDebugContext.glError("wglGetCurrentAssociatedContextAMD", new Object[0]);
        return returnType;
    }

    public static void wglBlitContextFramebufferAMD(long p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10) {
        WGLAMDGPUAssociation.wglBlitContextFramebufferAMD(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("wglBlitContextFramebufferAMD", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10));
    }

    public static int wglGetGPUIDsAMD(int[] p0) {
        int returnType = WGLAMDGPUAssociation.wglGetGPUIDsAMD(p0);
        LabyDebugContext.glError("wglGetGPUIDsAMD", "p0", p0);
        return returnType;
    }

    public static int wglGetGPUInfoAMD(int p0, int p1, int p2, int[] p3) {
        int returnType = WGLAMDGPUAssociation.wglGetGPUInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("wglGetGPUInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static int wglGetGPUInfoAMD(int p0, int p1, int p2, float[] p3) {
        int returnType = WGLAMDGPUAssociation.wglGetGPUInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("wglGetGPUInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static long wglCreateAssociatedContextAttribsAMD(int p0, long p1, int[] p2) {
        long returnType = WGLAMDGPUAssociation.wglCreateAssociatedContextAttribsAMD(p0, p1, p2);
        LabyDebugContext.glError("wglCreateAssociatedContextAttribsAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
        return returnType;
    }
}

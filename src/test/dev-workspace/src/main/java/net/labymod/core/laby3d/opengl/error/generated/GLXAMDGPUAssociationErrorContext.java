package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLXAMDGPUAssociation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXAMDGPUAssociationErrorContext.class */
public final class GLXAMDGPUAssociationErrorContext {
    public static void glXBlitContextFramebufferAMD(long p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10) {
        GLXAMDGPUAssociation.glXBlitContextFramebufferAMD(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glXBlitContextFramebufferAMD", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10));
    }

    public static long glXCreateAssociatedContextAMD(int p0, long p1) {
        long returnType = GLXAMDGPUAssociation.glXCreateAssociatedContextAMD(p0, p1);
        LabyDebugContext.glError("glXCreateAssociatedContextAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static long nglXCreateAssociatedContextAttribsAMD(int p0, long p1, long p2) {
        long returnType = GLXAMDGPUAssociation.nglXCreateAssociatedContextAttribsAMD(p0, p1, p2);
        LabyDebugContext.glError("nglXCreateAssociatedContextAttribsAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static long glXCreateAssociatedContextAttribsAMD(int p0, long p1, IntBuffer p2) {
        long returnType = GLXAMDGPUAssociation.glXCreateAssociatedContextAttribsAMD(p0, p1, p2);
        LabyDebugContext.glError("glXCreateAssociatedContextAttribsAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static boolean glXDeleteAssociatedContextAMD(long p0) {
        boolean returnType = GLXAMDGPUAssociation.glXDeleteAssociatedContextAMD(p0);
        LabyDebugContext.glError("glXDeleteAssociatedContextAMD", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int glXGetContextGPUIDAMD(long p0) {
        int returnType = GLXAMDGPUAssociation.glXGetContextGPUIDAMD(p0);
        LabyDebugContext.glError("glXGetContextGPUIDAMD", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long glXGetCurrentAssociatedContextAMD() {
        long returnType = GLXAMDGPUAssociation.glXGetCurrentAssociatedContextAMD();
        LabyDebugContext.glError("glXGetCurrentAssociatedContextAMD", new Object[0]);
        return returnType;
    }

    public static int nglXGetGPUIDsAMD(int p0, long p1) {
        int returnType = GLXAMDGPUAssociation.nglXGetGPUIDsAMD(p0, p1);
        LabyDebugContext.glError("nglXGetGPUIDsAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glXGetGPUIDsAMD(IntBuffer p0) {
        int returnType = GLXAMDGPUAssociation.glXGetGPUIDsAMD(p0);
        LabyDebugContext.glError("glXGetGPUIDsAMD", "p0", p0);
        return returnType;
    }

    public static int nglXGetGPUInfoAMD(int p0, int p1, int p2, int p3, long p4) {
        int returnType = GLXAMDGPUAssociation.nglXGetGPUInfoAMD(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglXGetGPUInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
        return returnType;
    }

    public static int glXGetGPUInfoAMD(int p0, int p1, int p2, ByteBuffer p3) {
        int returnType = GLXAMDGPUAssociation.glXGetGPUInfoAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glXGetGPUInfoAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static boolean glXMakeAssociatedContextCurrentAMD(long p0) {
        boolean returnType = GLXAMDGPUAssociation.glXMakeAssociatedContextCurrentAMD(p0);
        LabyDebugContext.glError("glXMakeAssociatedContextCurrentAMD", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long glXCreateAssociatedContextAttribsAMD(int p0, long p1, int[] p2) {
        long returnType = GLXAMDGPUAssociation.glXCreateAssociatedContextAttribsAMD(p0, p1, p2);
        LabyDebugContext.glError("glXCreateAssociatedContextAttribsAMD", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int glXGetGPUIDsAMD(int[] p0) {
        int returnType = GLXAMDGPUAssociation.glXGetGPUIDsAMD(p0);
        LabyDebugContext.glError("glXGetGPUIDsAMD", "p0", p0);
        return returnType;
    }
}

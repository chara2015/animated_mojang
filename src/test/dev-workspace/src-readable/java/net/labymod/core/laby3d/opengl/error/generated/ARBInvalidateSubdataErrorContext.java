package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBInvalidateSubdata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBInvalidateSubdataErrorContext.class */
public final class ARBInvalidateSubdataErrorContext {
    public static void glInvalidateTexSubImage(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
        ARBInvalidateSubdata.glInvalidateTexSubImage(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glInvalidateTexSubImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7));
    }

    public static void glInvalidateTexImage(int p0, int p1) {
        ARBInvalidateSubdata.glInvalidateTexImage(p0, p1);
        LabyDebugContext.glError("glInvalidateTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glInvalidateBufferSubData(int p0, long p1, long p2) {
        ARBInvalidateSubdata.glInvalidateBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glInvalidateBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glInvalidateBufferData(int p0) {
        ARBInvalidateSubdata.glInvalidateBufferData(p0);
        LabyDebugContext.glError("glInvalidateBufferData", "p0", Integer.valueOf(p0));
    }

    public static void nglInvalidateFramebuffer(int p0, int p1, long p2) {
        ARBInvalidateSubdata.nglInvalidateFramebuffer(p0, p1, p2);
        LabyDebugContext.glError("nglInvalidateFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glInvalidateFramebuffer(int p0, IntBuffer p1) {
        ARBInvalidateSubdata.glInvalidateFramebuffer(p0, p1);
        LabyDebugContext.glError("glInvalidateFramebuffer", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glInvalidateFramebuffer(int p0, int p1) {
        ARBInvalidateSubdata.glInvalidateFramebuffer(p0, p1);
        LabyDebugContext.glError("glInvalidateFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglInvalidateSubFramebuffer(int p0, int p1, long p2, int p3, int p4, int p5, int p6) {
        ARBInvalidateSubdata.nglInvalidateSubFramebuffer(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglInvalidateSubFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glInvalidateSubFramebuffer(int p0, IntBuffer p1, int p2, int p3, int p4, int p5) {
        ARBInvalidateSubdata.glInvalidateSubFramebuffer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateSubFramebuffer", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glInvalidateSubFramebuffer(int p0, int p1, int p2, int p3, int p4, int p5) {
        ARBInvalidateSubdata.glInvalidateSubFramebuffer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateSubFramebuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glInvalidateFramebuffer(int p0, int[] p1) {
        ARBInvalidateSubdata.glInvalidateFramebuffer(p0, p1);
        LabyDebugContext.glError("glInvalidateFramebuffer", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glInvalidateSubFramebuffer(int p0, int[] p1, int p2, int p3, int p4, int p5) {
        ARBInvalidateSubdata.glInvalidateSubFramebuffer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glInvalidateSubFramebuffer", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }
}

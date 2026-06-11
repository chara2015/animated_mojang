package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBVertexAttrib64Bit;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBVertexAttrib64BitErrorContext.class */
public final class ARBVertexAttrib64BitErrorContext {
    public static void glVertexAttribL1d(int p0, double p1) {
        ARBVertexAttrib64Bit.glVertexAttribL1d(p0, p1);
        LabyDebugContext.glError("glVertexAttribL1d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void glVertexAttribL2d(int p0, double p1, double p2) {
        ARBVertexAttrib64Bit.glVertexAttribL2d(p0, p1, p2);
        LabyDebugContext.glError("glVertexAttribL2d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void glVertexAttribL3d(int p0, double p1, double p2, double p3) {
        ARBVertexAttrib64Bit.glVertexAttribL3d(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribL3d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void glVertexAttribL4d(int p0, double p1, double p2, double p3, double p4) {
        ARBVertexAttrib64Bit.glVertexAttribL4d(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribL4d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4));
    }

    public static void nglVertexAttribL1dv(int p0, long p1) {
        ARBVertexAttrib64Bit.nglVertexAttribL1dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribL1dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribL1dv(int p0, DoubleBuffer p1) {
        ARBVertexAttrib64Bit.glVertexAttribL1dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL1dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribL2dv(int p0, long p1) {
        ARBVertexAttrib64Bit.nglVertexAttribL2dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribL2dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribL2dv(int p0, DoubleBuffer p1) {
        ARBVertexAttrib64Bit.glVertexAttribL2dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL2dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribL3dv(int p0, long p1) {
        ARBVertexAttrib64Bit.nglVertexAttribL3dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribL3dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribL3dv(int p0, DoubleBuffer p1) {
        ARBVertexAttrib64Bit.glVertexAttribL3dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL3dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribL4dv(int p0, long p1) {
        ARBVertexAttrib64Bit.nglVertexAttribL4dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribL4dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribL4dv(int p0, DoubleBuffer p1) {
        ARBVertexAttrib64Bit.glVertexAttribL4dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL4dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribLPointer(int p0, int p1, int p2, int p3, long p4) {
        ARBVertexAttrib64Bit.nglVertexAttribLPointer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglVertexAttribLPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glVertexAttribLPointer(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        ARBVertexAttrib64Bit.glVertexAttribLPointer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribLPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glVertexAttribLPointer(int p0, int p1, int p2, int p3, long p4) {
        ARBVertexAttrib64Bit.glVertexAttribLPointer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribLPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glVertexAttribLPointer(int p0, int p1, int p2, DoubleBuffer p3) {
        ARBVertexAttrib64Bit.glVertexAttribLPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribLPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglGetVertexAttribLdv(int p0, int p1, long p2) {
        ARBVertexAttrib64Bit.nglGetVertexAttribLdv(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribLdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribLdv(int p0, int p1, DoubleBuffer p2) {
        ARBVertexAttrib64Bit.glGetVertexAttribLdv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribLdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glVertexArrayVertexAttribLOffsetEXT(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        ARBVertexAttrib64Bit.glVertexArrayVertexAttribLOffsetEXT(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glVertexArrayVertexAttribLOffsetEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glVertexAttribL1dv(int p0, double[] p1) {
        ARBVertexAttrib64Bit.glVertexAttribL1dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL1dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribL2dv(int p0, double[] p1) {
        ARBVertexAttrib64Bit.glVertexAttribL2dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL2dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribL3dv(int p0, double[] p1) {
        ARBVertexAttrib64Bit.glVertexAttribL3dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL3dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribL4dv(int p0, double[] p1) {
        ARBVertexAttrib64Bit.glVertexAttribL4dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL4dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetVertexAttribLdv(int p0, int p1, double[] p2) {
        ARBVertexAttrib64Bit.glGetVertexAttribLdv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribLdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

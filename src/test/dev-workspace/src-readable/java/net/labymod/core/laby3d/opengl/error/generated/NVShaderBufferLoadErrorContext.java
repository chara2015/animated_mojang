package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVShaderBufferLoad;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVShaderBufferLoadErrorContext.class */
public final class NVShaderBufferLoadErrorContext {
    public static void glMakeBufferResidentNV(int p0, int p1) {
        NVShaderBufferLoad.glMakeBufferResidentNV(p0, p1);
        LabyDebugContext.glError("glMakeBufferResidentNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glMakeBufferNonResidentNV(int p0) {
        NVShaderBufferLoad.glMakeBufferNonResidentNV(p0);
        LabyDebugContext.glError("glMakeBufferNonResidentNV", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsBufferResidentNV(int p0) {
        boolean returnType = NVShaderBufferLoad.glIsBufferResidentNV(p0);
        LabyDebugContext.glError("glIsBufferResidentNV", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glMakeNamedBufferResidentNV(int p0, int p1) {
        NVShaderBufferLoad.glMakeNamedBufferResidentNV(p0, p1);
        LabyDebugContext.glError("glMakeNamedBufferResidentNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glMakeNamedBufferNonResidentNV(int p0) {
        NVShaderBufferLoad.glMakeNamedBufferNonResidentNV(p0);
        LabyDebugContext.glError("glMakeNamedBufferNonResidentNV", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsNamedBufferResidentNV(int p0) {
        boolean returnType = NVShaderBufferLoad.glIsNamedBufferResidentNV(p0);
        LabyDebugContext.glError("glIsNamedBufferResidentNV", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetBufferParameterui64vNV(int p0, int p1, long p2) {
        NVShaderBufferLoad.nglGetBufferParameterui64vNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetBufferParameterui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetBufferParameterui64vNV(int p0, int p1, LongBuffer p2) {
        NVShaderBufferLoad.glGetBufferParameterui64vNV(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferParameterui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetBufferParameterui64NV(int p0, int p1) {
        long returnType = NVShaderBufferLoad.glGetBufferParameterui64NV(p0, p1);
        LabyDebugContext.glError("glGetBufferParameterui64NV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetNamedBufferParameterui64vNV(int p0, int p1, long p2) {
        NVShaderBufferLoad.nglGetNamedBufferParameterui64vNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedBufferParameterui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedBufferParameterui64vNV(int p0, int p1, LongBuffer p2) {
        NVShaderBufferLoad.glGetNamedBufferParameterui64vNV(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferParameterui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetNamedBufferParameterui64NV(int p0, int p1) {
        long returnType = NVShaderBufferLoad.glGetNamedBufferParameterui64NV(p0, p1);
        LabyDebugContext.glError("glGetNamedBufferParameterui64NV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetIntegerui64vNV(int p0, long p1) {
        NVShaderBufferLoad.nglGetIntegerui64vNV(p0, p1);
        LabyDebugContext.glError("nglGetIntegerui64vNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetIntegerui64vNV(int p0, LongBuffer p1) {
        NVShaderBufferLoad.glGetIntegerui64vNV(p0, p1);
        LabyDebugContext.glError("glGetIntegerui64vNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static long glGetIntegerui64NV(int p0) {
        long returnType = NVShaderBufferLoad.glGetIntegerui64NV(p0);
        LabyDebugContext.glError("glGetIntegerui64NV", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glUniformui64NV(int p0, long p1) {
        NVShaderBufferLoad.glUniformui64NV(p0, p1);
        LabyDebugContext.glError("glUniformui64NV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void nglUniformui64vNV(int p0, int p1, long p2) {
        NVShaderBufferLoad.nglUniformui64vNV(p0, p1, p2);
        LabyDebugContext.glError("nglUniformui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniformui64vNV(int p0, LongBuffer p1) {
        NVShaderBufferLoad.glUniformui64vNV(p0, p1);
        LabyDebugContext.glError("glUniformui64vNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglGetUniformui64vNV(int p0, int p1, long p2) {
        NVShaderBufferLoad.nglGetUniformui64vNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetUniformui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetUniformui64vNV(int p0, int p1, LongBuffer p2) {
        NVShaderBufferLoad.glGetUniformui64vNV(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetUniformui64NV(int p0, int p1) {
        long returnType = NVShaderBufferLoad.glGetUniformui64NV(p0, p1);
        LabyDebugContext.glError("glGetUniformui64NV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glProgramUniformui64NV(int p0, int p1, long p2) {
        NVShaderBufferLoad.glProgramUniformui64NV(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniformui64NV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void nglProgramUniformui64vNV(int p0, int p1, int p2, long p3) {
        NVShaderBufferLoad.nglProgramUniformui64vNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniformui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniformui64vNV(int p0, int p1, LongBuffer p2) {
        NVShaderBufferLoad.glProgramUniformui64vNV(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniformui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferParameterui64vNV(int p0, int p1, long[] p2) {
        NVShaderBufferLoad.glGetBufferParameterui64vNV(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferParameterui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedBufferParameterui64vNV(int p0, int p1, long[] p2) {
        NVShaderBufferLoad.glGetNamedBufferParameterui64vNV(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedBufferParameterui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetIntegerui64vNV(int p0, long[] p1) {
        NVShaderBufferLoad.glGetIntegerui64vNV(p0, p1);
        LabyDebugContext.glError("glGetIntegerui64vNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniformui64vNV(int p0, long[] p1) {
        NVShaderBufferLoad.glUniformui64vNV(p0, p1);
        LabyDebugContext.glError("glUniformui64vNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetUniformui64vNV(int p0, int p1, long[] p2) {
        NVShaderBufferLoad.glGetUniformui64vNV(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniformui64vNV(int p0, int p1, long[] p2) {
        NVShaderBufferLoad.glProgramUniformui64vNV(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniformui64vNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

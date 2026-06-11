package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.AMDDebugOutput;
import org.lwjgl.opengl.GLDebugMessageAMDCallbackI;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/AMDDebugOutputErrorContext.class */
public final class AMDDebugOutputErrorContext {
    public static void nglDebugMessageEnableAMD(int p0, int p1, int p2, long p3, boolean p4) {
        AMDDebugOutput.nglDebugMessageEnableAMD(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglDebugMessageEnableAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Boolean.valueOf(p4));
    }

    public static void glDebugMessageEnableAMD(int p0, int p1, IntBuffer p2, boolean p3) {
        AMDDebugOutput.glDebugMessageEnableAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glDebugMessageEnableAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Boolean.valueOf(p3));
    }

    public static void glDebugMessageEnableAMD(int p0, int p1, int p2, boolean p3) {
        AMDDebugOutput.glDebugMessageEnableAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glDebugMessageEnableAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3));
    }

    public static void nglDebugMessageInsertAMD(int p0, int p1, int p2, int p3, long p4) {
        AMDDebugOutput.nglDebugMessageInsertAMD(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglDebugMessageInsertAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glDebugMessageInsertAMD(int p0, int p1, int p2, ByteBuffer p3) {
        AMDDebugOutput.glDebugMessageInsertAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glDebugMessageInsertAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glDebugMessageInsertAMD(int p0, int p1, int p2, CharSequence p3) {
        AMDDebugOutput.glDebugMessageInsertAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glDebugMessageInsertAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglDebugMessageCallbackAMD(long p0, long p1) {
        AMDDebugOutput.nglDebugMessageCallbackAMD(p0, p1);
        LabyDebugContext.glError("nglDebugMessageCallbackAMD", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDebugMessageCallbackAMD(GLDebugMessageAMDCallbackI p0, long p1) {
        AMDDebugOutput.glDebugMessageCallbackAMD(p0, p1);
        LabyDebugContext.glError("glDebugMessageCallbackAMD", "p0", p0, "p1", Long.valueOf(p1));
    }

    public static int nglGetDebugMessageLogAMD(int p0, int p1, long p2, long p3, long p4, long p5, long p6) {
        int returnType = AMDDebugOutput.nglGetDebugMessageLogAMD(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglGetDebugMessageLogAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6));
        return returnType;
    }

    public static int glGetDebugMessageLogAMD(int p0, IntBuffer p1, IntBuffer p2, IntBuffer p3, IntBuffer p4, ByteBuffer p5) {
        int returnType = AMDDebugOutput.glGetDebugMessageLogAMD(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetDebugMessageLogAMD", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4, "p5", p5);
        return returnType;
    }

    public static void glDebugMessageEnableAMD(int p0, int p1, int[] p2, boolean p3) {
        AMDDebugOutput.glDebugMessageEnableAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glDebugMessageEnableAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Boolean.valueOf(p3));
    }

    public static int glGetDebugMessageLogAMD(int p0, int[] p1, int[] p2, int[] p3, int[] p4, ByteBuffer p5) {
        int returnType = AMDDebugOutput.glGetDebugMessageLogAMD(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetDebugMessageLogAMD", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4, "p5", p5);
        return returnType;
    }
}

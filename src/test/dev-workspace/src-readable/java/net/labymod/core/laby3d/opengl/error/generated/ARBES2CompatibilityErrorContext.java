package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBES2Compatibility;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBES2CompatibilityErrorContext.class */
public final class ARBES2CompatibilityErrorContext {
    public static void glReleaseShaderCompiler() {
        ARBES2Compatibility.glReleaseShaderCompiler();
        LabyDebugContext.glError("glReleaseShaderCompiler", new Object[0]);
    }

    public static void nglShaderBinary(int p0, long p1, int p2, long p3, int p4) {
        ARBES2Compatibility.nglShaderBinary(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglShaderBinary", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glShaderBinary(IntBuffer p0, int p1, ByteBuffer p2) {
        ARBES2Compatibility.glShaderBinary(p0, p1, p2);
        LabyDebugContext.glError("glShaderBinary", "p0", p0, "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetShaderPrecisionFormat(int p0, int p1, long p2, long p3) {
        ARBES2Compatibility.nglGetShaderPrecisionFormat(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetShaderPrecisionFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetShaderPrecisionFormat(int p0, int p1, IntBuffer p2, IntBuffer p3) {
        ARBES2Compatibility.glGetShaderPrecisionFormat(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetShaderPrecisionFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static int glGetShaderPrecisionFormat(int p0, int p1, IntBuffer p2) {
        int returnType = ARBES2Compatibility.glGetShaderPrecisionFormat(p0, p1, p2);
        LabyDebugContext.glError("glGetShaderPrecisionFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static void glDepthRangef(float p0, float p1) {
        ARBES2Compatibility.glDepthRangef(p0, p1);
        LabyDebugContext.glError("glDepthRangef", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glClearDepthf(float p0) {
        ARBES2Compatibility.glClearDepthf(p0);
        LabyDebugContext.glError("glClearDepthf", "p0", Float.valueOf(p0));
    }

    public static void glShaderBinary(int[] p0, int p1, ByteBuffer p2) {
        ARBES2Compatibility.glShaderBinary(p0, p1, p2);
        LabyDebugContext.glError("glShaderBinary", "p0", p0, "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetShaderPrecisionFormat(int p0, int p1, int[] p2, int[] p3) {
        ARBES2Compatibility.glGetShaderPrecisionFormat(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetShaderPrecisionFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBGetProgramBinary;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBGetProgramBinaryErrorContext.class */
public final class ARBGetProgramBinaryErrorContext {
    public static void nglGetProgramBinary(int p0, int p1, long p2, long p3, long p4) {
        ARBGetProgramBinary.nglGetProgramBinary(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetProgramBinary", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetProgramBinary(int p0, IntBuffer p1, IntBuffer p2, ByteBuffer p3) {
        ARBGetProgramBinary.glGetProgramBinary(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetProgramBinary", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void nglProgramBinary(int p0, int p1, long p2, int p3) {
        ARBGetProgramBinary.nglProgramBinary(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramBinary", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glProgramBinary(int p0, int p1, ByteBuffer p2) {
        ARBGetProgramBinary.glProgramBinary(p0, p1, p2);
        LabyDebugContext.glError("glProgramBinary", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramParameteri(int p0, int p1, int p2) {
        ARBGetProgramBinary.glProgramParameteri(p0, p1, p2);
        LabyDebugContext.glError("glProgramParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glGetProgramBinary(int p0, int[] p1, int[] p2, ByteBuffer p3) {
        ARBGetProgramBinary.glGetProgramBinary(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetProgramBinary", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }
}

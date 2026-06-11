package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL15C;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL15CErrorContext.class */
public final class GL15CErrorContext {
    public static void glBindBuffer(int p0, int p1) {
        GL15C.glBindBuffer(p0, p1);
        LabyDebugContext.glError("glBindBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglDeleteBuffers(int p0, long p1) {
        GL15C.nglDeleteBuffers(p0, p1);
        LabyDebugContext.glError("nglDeleteBuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteBuffers(IntBuffer p0) {
        GL15C.glDeleteBuffers(p0);
        LabyDebugContext.glError("glDeleteBuffers", "p0", p0);
    }

    public static void glDeleteBuffers(int p0) {
        GL15C.glDeleteBuffers(p0);
        LabyDebugContext.glError("glDeleteBuffers", "p0", Integer.valueOf(p0));
    }

    public static void nglGenBuffers(int p0, long p1) {
        GL15C.nglGenBuffers(p0, p1);
        LabyDebugContext.glError("nglGenBuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenBuffers(IntBuffer p0) {
        GL15C.glGenBuffers(p0);
        LabyDebugContext.glError("glGenBuffers", "p0", p0);
    }

    public static int glGenBuffers() {
        int returnType = GL15C.glGenBuffers();
        LabyDebugContext.glError("glGenBuffers", new Object[0]);
        return returnType;
    }

    public static boolean glIsBuffer(int p0) {
        boolean returnType = GL15C.glIsBuffer(p0);
        LabyDebugContext.glError("glIsBuffer", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglBufferData(int p0, long p1, long p2, int p3) {
        GL15C.nglBufferData(p0, p1, p2, p3);
        LabyDebugContext.glError("nglBufferData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glBufferData(int p0, long p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBufferData(int p0, ByteBuffer p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferData(int p0, ShortBuffer p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferData(int p0, IntBuffer p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferData(int p0, LongBuffer p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferData(int p0, FloatBuffer p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferData(int p0, DoubleBuffer p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglBufferSubData(int p0, long p1, long p2, long p3) {
        GL15C.nglBufferSubData(p0, p1, p2, p3);
        LabyDebugContext.glError("nglBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glBufferSubData(int p0, long p1, ByteBuffer p2) {
        GL15C.glBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubData(int p0, long p1, ShortBuffer p2) {
        GL15C.glBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubData(int p0, long p1, IntBuffer p2) {
        GL15C.glBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubData(int p0, long p1, LongBuffer p2) {
        GL15C.glBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubData(int p0, long p1, FloatBuffer p2) {
        GL15C.glBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubData(int p0, long p1, DoubleBuffer p2) {
        GL15C.glBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void nglGetBufferSubData(int p0, long p1, long p2, long p3) {
        GL15C.nglGetBufferSubData(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetBufferSubData(int p0, long p1, ByteBuffer p2) {
        GL15C.glGetBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubData(int p0, long p1, ShortBuffer p2) {
        GL15C.glGetBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubData(int p0, long p1, IntBuffer p2) {
        GL15C.glGetBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubData(int p0, long p1, LongBuffer p2) {
        GL15C.glGetBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubData(int p0, long p1, FloatBuffer p2) {
        GL15C.glGetBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubData(int p0, long p1, DoubleBuffer p2) {
        GL15C.glGetBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static long nglMapBuffer(int p0, int p1) {
        long returnType = GL15C.nglMapBuffer(p0, p1);
        LabyDebugContext.glError("nglMapBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static ByteBuffer glMapBuffer(int p0, int p1) {
        ByteBuffer returnType = GL15C.glMapBuffer(p0, p1);
        LabyDebugContext.glError("glMapBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static ByteBuffer glMapBuffer(int p0, int p1, ByteBuffer p2) {
        ByteBuffer returnType = GL15C.glMapBuffer(p0, p1, p2);
        LabyDebugContext.glError("glMapBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static ByteBuffer glMapBuffer(int p0, int p1, long p2, ByteBuffer p3) {
        ByteBuffer returnType = GL15C.glMapBuffer(p0, p1, p2, p3);
        LabyDebugContext.glError("glMapBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static boolean glUnmapBuffer(int p0) {
        boolean returnType = GL15C.glUnmapBuffer(p0);
        LabyDebugContext.glError("glUnmapBuffer", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetBufferParameteriv(int p0, int p1, long p2) {
        GL15C.nglGetBufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetBufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetBufferParameteriv(int p0, int p1, IntBuffer p2) {
        GL15C.glGetBufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetBufferParameteri(int p0, int p1) {
        int returnType = GL15C.glGetBufferParameteri(p0, p1);
        LabyDebugContext.glError("glGetBufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetBufferPointerv(int p0, int p1, long p2) {
        GL15C.nglGetBufferPointerv(p0, p1, p2);
        LabyDebugContext.glError("nglGetBufferPointerv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetBufferPointerv(int p0, int p1, PointerBuffer p2) {
        GL15C.glGetBufferPointerv(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferPointerv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetBufferPointer(int p0, int p1) {
        long returnType = GL15C.glGetBufferPointer(p0, p1);
        LabyDebugContext.glError("glGetBufferPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGenQueries(int p0, long p1) {
        GL15C.nglGenQueries(p0, p1);
        LabyDebugContext.glError("nglGenQueries", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenQueries(IntBuffer p0) {
        GL15C.glGenQueries(p0);
        LabyDebugContext.glError("glGenQueries", "p0", p0);
    }

    public static int glGenQueries() {
        int returnType = GL15C.glGenQueries();
        LabyDebugContext.glError("glGenQueries", new Object[0]);
        return returnType;
    }

    public static void nglDeleteQueries(int p0, long p1) {
        GL15C.nglDeleteQueries(p0, p1);
        LabyDebugContext.glError("nglDeleteQueries", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteQueries(IntBuffer p0) {
        GL15C.glDeleteQueries(p0);
        LabyDebugContext.glError("glDeleteQueries", "p0", p0);
    }

    public static void glDeleteQueries(int p0) {
        GL15C.glDeleteQueries(p0);
        LabyDebugContext.glError("glDeleteQueries", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsQuery(int p0) {
        boolean returnType = GL15C.glIsQuery(p0);
        LabyDebugContext.glError("glIsQuery", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glBeginQuery(int p0, int p1) {
        GL15C.glBeginQuery(p0, p1);
        LabyDebugContext.glError("glBeginQuery", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glEndQuery(int p0) {
        GL15C.glEndQuery(p0);
        LabyDebugContext.glError("glEndQuery", "p0", Integer.valueOf(p0));
    }

    public static void nglGetQueryiv(int p0, int p1, long p2) {
        GL15C.nglGetQueryiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryiv(int p0, int p1, IntBuffer p2) {
        GL15C.glGetQueryiv(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetQueryi(int p0, int p1) {
        int returnType = GL15C.glGetQueryi(p0, p1);
        LabyDebugContext.glError("glGetQueryi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetQueryObjectiv(int p0, int p1, long p2) {
        GL15C.nglGetQueryObjectiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjectiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjectiv(int p0, int p1, IntBuffer p2) {
        GL15C.glGetQueryObjectiv(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectiv(int p0, int p1, long p2) {
        GL15C.glGetQueryObjectiv(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static int glGetQueryObjecti(int p0, int p1) {
        int returnType = GL15C.glGetQueryObjecti(p0, p1);
        LabyDebugContext.glError("glGetQueryObjecti", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetQueryObjectuiv(int p0, int p1, long p2) {
        GL15C.nglGetQueryObjectuiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjectuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjectuiv(int p0, int p1, IntBuffer p2) {
        GL15C.glGetQueryObjectuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectuiv(int p0, int p1, long p2) {
        GL15C.glGetQueryObjectuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static int glGetQueryObjectui(int p0, int p1) {
        int returnType = GL15C.glGetQueryObjectui(p0, p1);
        LabyDebugContext.glError("glGetQueryObjectui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glDeleteBuffers(int[] p0) {
        GL15C.glDeleteBuffers(p0);
        LabyDebugContext.glError("glDeleteBuffers", "p0", p0);
    }

    public static void glGenBuffers(int[] p0) {
        GL15C.glGenBuffers(p0);
        LabyDebugContext.glError("glGenBuffers", "p0", p0);
    }

    public static void glBufferData(int p0, short[] p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferData(int p0, int[] p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferData(int p0, long[] p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferData(int p0, float[] p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferData(int p0, double[] p1, int p2) {
        GL15C.glBufferData(p0, p1, p2);
        LabyDebugContext.glError("glBufferData", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferSubData(int p0, long p1, short[] p2) {
        GL15C.glBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubData(int p0, long p1, int[] p2) {
        GL15C.glBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubData(int p0, long p1, long[] p2) {
        GL15C.glBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubData(int p0, long p1, float[] p2) {
        GL15C.glBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubData(int p0, long p1, double[] p2) {
        GL15C.glBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubData(int p0, long p1, short[] p2) {
        GL15C.glGetBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubData(int p0, long p1, int[] p2) {
        GL15C.glGetBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubData(int p0, long p1, long[] p2) {
        GL15C.glGetBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubData(int p0, long p1, float[] p2) {
        GL15C.glGetBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubData(int p0, long p1, double[] p2) {
        GL15C.glGetBufferSubData(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubData", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferParameteriv(int p0, int p1, int[] p2) {
        GL15C.glGetBufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGenQueries(int[] p0) {
        GL15C.glGenQueries(p0);
        LabyDebugContext.glError("glGenQueries", "p0", p0);
    }

    public static void glDeleteQueries(int[] p0) {
        GL15C.glDeleteQueries(p0);
        LabyDebugContext.glError("glDeleteQueries", "p0", p0);
    }

    public static void glGetQueryiv(int p0, int p1, int[] p2) {
        GL15C.glGetQueryiv(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectiv(int p0, int p1, int[] p2) {
        GL15C.glGetQueryObjectiv(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectuiv(int p0, int p1, int[] p2) {
        GL15C.glGetQueryObjectuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

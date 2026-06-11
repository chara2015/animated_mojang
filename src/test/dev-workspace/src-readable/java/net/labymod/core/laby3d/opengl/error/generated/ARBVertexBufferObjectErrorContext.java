package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBVertexBufferObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBVertexBufferObjectErrorContext.class */
public final class ARBVertexBufferObjectErrorContext {
    public static void glBindBufferARB(int p0, int p1) {
        ARBVertexBufferObject.glBindBufferARB(p0, p1);
        LabyDebugContext.glError("glBindBufferARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglDeleteBuffersARB(int p0, long p1) {
        ARBVertexBufferObject.nglDeleteBuffersARB(p0, p1);
        LabyDebugContext.glError("nglDeleteBuffersARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteBuffersARB(IntBuffer p0) {
        ARBVertexBufferObject.glDeleteBuffersARB(p0);
        LabyDebugContext.glError("glDeleteBuffersARB", "p0", p0);
    }

    public static void glDeleteBuffersARB(int p0) {
        ARBVertexBufferObject.glDeleteBuffersARB(p0);
        LabyDebugContext.glError("glDeleteBuffersARB", "p0", Integer.valueOf(p0));
    }

    public static void nglGenBuffersARB(int p0, long p1) {
        ARBVertexBufferObject.nglGenBuffersARB(p0, p1);
        LabyDebugContext.glError("nglGenBuffersARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenBuffersARB(IntBuffer p0) {
        ARBVertexBufferObject.glGenBuffersARB(p0);
        LabyDebugContext.glError("glGenBuffersARB", "p0", p0);
    }

    public static int glGenBuffersARB() {
        int returnType = ARBVertexBufferObject.glGenBuffersARB();
        LabyDebugContext.glError("glGenBuffersARB", new Object[0]);
        return returnType;
    }

    public static boolean glIsBufferARB(int p0) {
        boolean returnType = ARBVertexBufferObject.glIsBufferARB(p0);
        LabyDebugContext.glError("glIsBufferARB", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglBufferDataARB(int p0, long p1, long p2, int p3) {
        ARBVertexBufferObject.nglBufferDataARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglBufferDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glBufferDataARB(int p0, long p1, int p2) {
        ARBVertexBufferObject.glBufferDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBufferDataARB(int p0, ByteBuffer p1, int p2) {
        ARBVertexBufferObject.glBufferDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferDataARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferDataARB(int p0, ShortBuffer p1, int p2) {
        ARBVertexBufferObject.glBufferDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferDataARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferDataARB(int p0, IntBuffer p1, int p2) {
        ARBVertexBufferObject.glBufferDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferDataARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferDataARB(int p0, FloatBuffer p1, int p2) {
        ARBVertexBufferObject.glBufferDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferDataARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferDataARB(int p0, DoubleBuffer p1, int p2) {
        ARBVertexBufferObject.glBufferDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferDataARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglBufferSubDataARB(int p0, long p1, long p2, long p3) {
        ARBVertexBufferObject.nglBufferSubDataARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glBufferSubDataARB(int p0, long p1, ByteBuffer p2) {
        ARBVertexBufferObject.glBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubDataARB(int p0, long p1, ShortBuffer p2) {
        ARBVertexBufferObject.glBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubDataARB(int p0, long p1, IntBuffer p2) {
        ARBVertexBufferObject.glBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubDataARB(int p0, long p1, FloatBuffer p2) {
        ARBVertexBufferObject.glBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubDataARB(int p0, long p1, DoubleBuffer p2) {
        ARBVertexBufferObject.glBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void nglGetBufferSubDataARB(int p0, long p1, long p2, long p3) {
        ARBVertexBufferObject.nglGetBufferSubDataARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetBufferSubDataARB(int p0, long p1, ByteBuffer p2) {
        ARBVertexBufferObject.glGetBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubDataARB(int p0, long p1, ShortBuffer p2) {
        ARBVertexBufferObject.glGetBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubDataARB(int p0, long p1, IntBuffer p2) {
        ARBVertexBufferObject.glGetBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubDataARB(int p0, long p1, FloatBuffer p2) {
        ARBVertexBufferObject.glGetBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubDataARB(int p0, long p1, DoubleBuffer p2) {
        ARBVertexBufferObject.glGetBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static long nglMapBufferARB(int p0, int p1) {
        long returnType = ARBVertexBufferObject.nglMapBufferARB(p0, p1);
        LabyDebugContext.glError("nglMapBufferARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static ByteBuffer glMapBufferARB(int p0, int p1) {
        ByteBuffer returnType = ARBVertexBufferObject.glMapBufferARB(p0, p1);
        LabyDebugContext.glError("glMapBufferARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static ByteBuffer glMapBufferARB(int p0, int p1, ByteBuffer p2) {
        ByteBuffer returnType = ARBVertexBufferObject.glMapBufferARB(p0, p1, p2);
        LabyDebugContext.glError("glMapBufferARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static ByteBuffer glMapBufferARB(int p0, int p1, long p2, ByteBuffer p3) {
        ByteBuffer returnType = ARBVertexBufferObject.glMapBufferARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glMapBufferARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static boolean glUnmapBufferARB(int p0) {
        boolean returnType = ARBVertexBufferObject.glUnmapBufferARB(p0);
        LabyDebugContext.glError("glUnmapBufferARB", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetBufferParameterivARB(int p0, int p1, long p2) {
        ARBVertexBufferObject.nglGetBufferParameterivARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetBufferParameterivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetBufferParameterivARB(int p0, int p1, IntBuffer p2) {
        ARBVertexBufferObject.glGetBufferParameterivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferParameterivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetBufferParameteriARB(int p0, int p1) {
        int returnType = ARBVertexBufferObject.glGetBufferParameteriARB(p0, p1);
        LabyDebugContext.glError("glGetBufferParameteriARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetBufferPointervARB(int p0, int p1, long p2) {
        ARBVertexBufferObject.nglGetBufferPointervARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetBufferPointervARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetBufferPointervARB(int p0, int p1, PointerBuffer p2) {
        ARBVertexBufferObject.glGetBufferPointervARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferPointervARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetBufferPointerARB(int p0, int p1) {
        long returnType = ARBVertexBufferObject.glGetBufferPointerARB(p0, p1);
        LabyDebugContext.glError("glGetBufferPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glDeleteBuffersARB(int[] p0) {
        ARBVertexBufferObject.glDeleteBuffersARB(p0);
        LabyDebugContext.glError("glDeleteBuffersARB", "p0", p0);
    }

    public static void glGenBuffersARB(int[] p0) {
        ARBVertexBufferObject.glGenBuffersARB(p0);
        LabyDebugContext.glError("glGenBuffersARB", "p0", p0);
    }

    public static void glBufferDataARB(int p0, short[] p1, int p2) {
        ARBVertexBufferObject.glBufferDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferDataARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferDataARB(int p0, int[] p1, int p2) {
        ARBVertexBufferObject.glBufferDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferDataARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferDataARB(int p0, float[] p1, int p2) {
        ARBVertexBufferObject.glBufferDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferDataARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferDataARB(int p0, double[] p1, int p2) {
        ARBVertexBufferObject.glBufferDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferDataARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferSubDataARB(int p0, long p1, short[] p2) {
        ARBVertexBufferObject.glBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubDataARB(int p0, long p1, int[] p2) {
        ARBVertexBufferObject.glBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubDataARB(int p0, long p1, float[] p2) {
        ARBVertexBufferObject.glBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glBufferSubDataARB(int p0, long p1, double[] p2) {
        ARBVertexBufferObject.glBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubDataARB(int p0, long p1, short[] p2) {
        ARBVertexBufferObject.glGetBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubDataARB(int p0, long p1, int[] p2) {
        ARBVertexBufferObject.glGetBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubDataARB(int p0, long p1, float[] p2) {
        ARBVertexBufferObject.glGetBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferSubDataARB(int p0, long p1, double[] p2) {
        ARBVertexBufferObject.glGetBufferSubDataARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferSubDataARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
    }

    public static void glGetBufferParameterivARB(int p0, int p1, int[] p2) {
        ARBVertexBufferObject.glGetBufferParameterivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetBufferParameterivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

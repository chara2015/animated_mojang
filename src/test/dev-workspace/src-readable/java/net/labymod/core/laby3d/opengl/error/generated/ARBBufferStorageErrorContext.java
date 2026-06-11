package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBBufferStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBBufferStorageErrorContext.class */
public final class ARBBufferStorageErrorContext {
    public static void nglBufferStorage(int p0, long p1, long p2, int p3) {
        ARBBufferStorage.nglBufferStorage(p0, p1, p2, p3);
        LabyDebugContext.glError("nglBufferStorage", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glBufferStorage(int p0, long p1, int p2) {
        ARBBufferStorage.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, ByteBuffer p1, int p2) {
        ARBBufferStorage.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, ShortBuffer p1, int p2) {
        ARBBufferStorage.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, IntBuffer p1, int p2) {
        ARBBufferStorage.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, FloatBuffer p1, int p2) {
        ARBBufferStorage.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, DoubleBuffer p1, int p2) {
        ARBBufferStorage.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void nglNamedBufferStorageEXT(int p0, long p1, long p2, int p3) {
        ARBBufferStorage.nglNamedBufferStorageEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("nglNamedBufferStorageEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glNamedBufferStorageEXT(int p0, long p1, int p2) {
        ARBBufferStorage.glNamedBufferStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorageEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorageEXT(int p0, ByteBuffer p1, int p2) {
        ARBBufferStorage.glNamedBufferStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorageEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorageEXT(int p0, ShortBuffer p1, int p2) {
        ARBBufferStorage.glNamedBufferStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorageEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorageEXT(int p0, IntBuffer p1, int p2) {
        ARBBufferStorage.glNamedBufferStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorageEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorageEXT(int p0, FloatBuffer p1, int p2) {
        ARBBufferStorage.glNamedBufferStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorageEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorageEXT(int p0, DoubleBuffer p1, int p2) {
        ARBBufferStorage.glNamedBufferStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorageEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, short[] p1, int p2) {
        ARBBufferStorage.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, int[] p1, int p2) {
        ARBBufferStorage.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, float[] p1, int p2) {
        ARBBufferStorage.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glBufferStorage(int p0, double[] p1, int p2) {
        ARBBufferStorage.glBufferStorage(p0, p1, p2);
        LabyDebugContext.glError("glBufferStorage", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorageEXT(int p0, short[] p1, int p2) {
        ARBBufferStorage.glNamedBufferStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorageEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorageEXT(int p0, int[] p1, int p2) {
        ARBBufferStorage.glNamedBufferStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorageEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorageEXT(int p0, float[] p1, int p2) {
        ARBBufferStorage.glNamedBufferStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorageEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glNamedBufferStorageEXT(int p0, double[] p1, int p2) {
        ARBBufferStorage.glNamedBufferStorageEXT(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferStorageEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }
}

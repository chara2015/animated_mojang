package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLDebugMessageCallbackI;
import org.lwjgl.opengl.KHRDebug;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/KHRDebugErrorContext.class */
public final class KHRDebugErrorContext {
    public static void nglDebugMessageControl(int p0, int p1, int p2, int p3, long p4, boolean p5) {
        KHRDebug.nglDebugMessageControl(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglDebugMessageControl", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Boolean.valueOf(p5));
    }

    public static void glDebugMessageControl(int p0, int p1, int p2, IntBuffer p3, boolean p4) {
        KHRDebug.glDebugMessageControl(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDebugMessageControl", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Boolean.valueOf(p4));
    }

    public static void glDebugMessageControl(int p0, int p1, int p2, int p3, boolean p4) {
        KHRDebug.glDebugMessageControl(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDebugMessageControl", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Boolean.valueOf(p4));
    }

    public static void nglDebugMessageInsert(int p0, int p1, int p2, int p3, int p4, long p5) {
        KHRDebug.nglDebugMessageInsert(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglDebugMessageInsert", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glDebugMessageInsert(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        KHRDebug.glDebugMessageInsert(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDebugMessageInsert", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glDebugMessageInsert(int p0, int p1, int p2, int p3, CharSequence p4) {
        KHRDebug.glDebugMessageInsert(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDebugMessageInsert", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglDebugMessageCallback(long p0, long p1) {
        KHRDebug.nglDebugMessageCallback(p0, p1);
        LabyDebugContext.glError("nglDebugMessageCallback", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDebugMessageCallback(GLDebugMessageCallbackI p0, long p1) {
        KHRDebug.glDebugMessageCallback(p0, p1);
        LabyDebugContext.glError("glDebugMessageCallback", "p0", p0, "p1", Long.valueOf(p1));
    }

    public static int nglGetDebugMessageLog(int p0, int p1, long p2, long p3, long p4, long p5, long p6, long p7) {
        int returnType = KHRDebug.nglGetDebugMessageLog(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglGetDebugMessageLog", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6), "p7", Long.valueOf(p7));
        return returnType;
    }

    public static int glGetDebugMessageLog(int p0, IntBuffer p1, IntBuffer p2, IntBuffer p3, IntBuffer p4, IntBuffer p5, ByteBuffer p6) {
        int returnType = KHRDebug.glGetDebugMessageLog(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glGetDebugMessageLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4, "p5", p5, "p6", p6);
        return returnType;
    }

    public static void nglPushDebugGroup(int p0, int p1, int p2, long p3) {
        KHRDebug.nglPushDebugGroup(p0, p1, p2, p3);
        LabyDebugContext.glError("nglPushDebugGroup", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glPushDebugGroup(int p0, int p1, ByteBuffer p2) {
        KHRDebug.glPushDebugGroup(p0, p1, p2);
        LabyDebugContext.glError("glPushDebugGroup", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPushDebugGroup(int p0, int p1, CharSequence p2) {
        KHRDebug.glPushDebugGroup(p0, p1, p2);
        LabyDebugContext.glError("glPushDebugGroup", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPopDebugGroup() {
        KHRDebug.glPopDebugGroup();
        LabyDebugContext.glError("glPopDebugGroup", new Object[0]);
    }

    public static void nglObjectLabel(int p0, int p1, int p2, long p3) {
        KHRDebug.nglObjectLabel(p0, p1, p2, p3);
        LabyDebugContext.glError("nglObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glObjectLabel(int p0, int p1, ByteBuffer p2) {
        KHRDebug.glObjectLabel(p0, p1, p2);
        LabyDebugContext.glError("glObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glObjectLabel(int p0, int p1, CharSequence p2) {
        KHRDebug.glObjectLabel(p0, p1, p2);
        LabyDebugContext.glError("glObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetObjectLabel(int p0, int p1, int p2, long p3, long p4) {
        KHRDebug.nglGetObjectLabel(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetObjectLabel(int p0, int p1, IntBuffer p2, ByteBuffer p3) {
        KHRDebug.glGetObjectLabel(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static String glGetObjectLabel(int p0, int p1, int p2) {
        String returnType = KHRDebug.glGetObjectLabel(p0, p1, p2);
        LabyDebugContext.glError("glGetObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static String glGetObjectLabel(int p0, int p1) {
        String returnType = KHRDebug.glGetObjectLabel(p0, p1);
        LabyDebugContext.glError("glGetObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglObjectPtrLabel(long p0, int p1, long p2) {
        KHRDebug.nglObjectPtrLabel(p0, p1, p2);
        LabyDebugContext.glError("nglObjectPtrLabel", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glObjectPtrLabel(long p0, ByteBuffer p1) {
        KHRDebug.glObjectPtrLabel(p0, p1);
        LabyDebugContext.glError("glObjectPtrLabel", "p0", Long.valueOf(p0), "p1", p1);
    }

    public static void glObjectPtrLabel(long p0, CharSequence p1) {
        KHRDebug.glObjectPtrLabel(p0, p1);
        LabyDebugContext.glError("glObjectPtrLabel", "p0", Long.valueOf(p0), "p1", p1);
    }

    public static void nglGetObjectPtrLabel(long p0, int p1, long p2, long p3) {
        KHRDebug.nglGetObjectPtrLabel(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetObjectPtrLabel", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetObjectPtrLabel(long p0, IntBuffer p1, ByteBuffer p2) {
        KHRDebug.glGetObjectPtrLabel(p0, p1, p2);
        LabyDebugContext.glError("glGetObjectPtrLabel", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static String glGetObjectPtrLabel(long p0, int p1) {
        String returnType = KHRDebug.glGetObjectPtrLabel(p0, p1);
        LabyDebugContext.glError("glGetObjectPtrLabel", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glGetObjectPtrLabel(long p0) {
        String returnType = KHRDebug.glGetObjectPtrLabel(p0);
        LabyDebugContext.glError("glGetObjectPtrLabel", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static void glDebugMessageControl(int p0, int p1, int p2, int[] p3, boolean p4) {
        KHRDebug.glDebugMessageControl(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDebugMessageControl", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Boolean.valueOf(p4));
    }

    public static int glGetDebugMessageLog(int p0, int[] p1, int[] p2, int[] p3, int[] p4, int[] p5, ByteBuffer p6) {
        int returnType = KHRDebug.glGetDebugMessageLog(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glGetDebugMessageLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4, "p5", p5, "p6", p6);
        return returnType;
    }

    public static void glGetObjectLabel(int p0, int p1, int[] p2, ByteBuffer p3) {
        KHRDebug.glGetObjectLabel(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetObjectLabel", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void glGetObjectPtrLabel(long p0, int[] p1, ByteBuffer p2) {
        KHRDebug.glGetObjectPtrLabel(p0, p1, p2);
        LabyDebugContext.glError("glGetObjectPtrLabel", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
    }
}

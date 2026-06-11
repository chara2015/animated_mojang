package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVQueryResourceTag;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVQueryResourceTagErrorContext.class */
public final class NVQueryResourceTagErrorContext {
    public static void nglGenQueryResourceTagNV(int p0, long p1) {
        NVQueryResourceTag.nglGenQueryResourceTagNV(p0, p1);
        LabyDebugContext.glError("nglGenQueryResourceTagNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenQueryResourceTagNV(IntBuffer p0) {
        NVQueryResourceTag.glGenQueryResourceTagNV(p0);
        LabyDebugContext.glError("glGenQueryResourceTagNV", "p0", p0);
    }

    public static int glGenQueryResourceTagNV() {
        int returnType = NVQueryResourceTag.glGenQueryResourceTagNV();
        LabyDebugContext.glError("glGenQueryResourceTagNV", new Object[0]);
        return returnType;
    }

    public static void nglDeleteQueryResourceTagNV(int p0, long p1) {
        NVQueryResourceTag.nglDeleteQueryResourceTagNV(p0, p1);
        LabyDebugContext.glError("nglDeleteQueryResourceTagNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteQueryResourceTagNV(IntBuffer p0) {
        NVQueryResourceTag.glDeleteQueryResourceTagNV(p0);
        LabyDebugContext.glError("glDeleteQueryResourceTagNV", "p0", p0);
    }

    public static void glDeleteQueryResourceTagNV(int p0) {
        NVQueryResourceTag.glDeleteQueryResourceTagNV(p0);
        LabyDebugContext.glError("glDeleteQueryResourceTagNV", "p0", Integer.valueOf(p0));
    }

    public static void nglQueryResourceTagNV(int p0, long p1) {
        NVQueryResourceTag.nglQueryResourceTagNV(p0, p1);
        LabyDebugContext.glError("nglQueryResourceTagNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glQueryResourceTagNV(int p0, ByteBuffer p1) {
        NVQueryResourceTag.glQueryResourceTagNV(p0, p1);
        LabyDebugContext.glError("glQueryResourceTagNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glQueryResourceTagNV(int p0, CharSequence p1) {
        NVQueryResourceTag.glQueryResourceTagNV(p0, p1);
        LabyDebugContext.glError("glQueryResourceTagNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGenQueryResourceTagNV(int[] p0) {
        NVQueryResourceTag.glGenQueryResourceTagNV(p0);
        LabyDebugContext.glError("glGenQueryResourceTagNV", "p0", p0);
    }

    public static void glDeleteQueryResourceTagNV(int[] p0) {
        NVQueryResourceTag.glDeleteQueryResourceTagNV(p0);
        LabyDebugContext.glError("glDeleteQueryResourceTagNV", "p0", p0);
    }
}

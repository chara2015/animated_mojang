package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTDrawInstanced;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTDrawInstancedErrorContext.class */
public final class EXTDrawInstancedErrorContext {
    public static void glDrawArraysInstancedEXT(int p0, int p1, int p2, int p3) {
        EXTDrawInstanced.glDrawArraysInstancedEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawArraysInstancedEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglDrawElementsInstancedEXT(int p0, int p1, int p2, long p3, int p4) {
        EXTDrawInstanced.nglDrawElementsInstancedEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglDrawElementsInstancedEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsInstancedEXT(int p0, int p1, int p2, long p3, int p4) {
        EXTDrawInstanced.glDrawElementsInstancedEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawElementsInstancedEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsInstancedEXT(int p0, int p1, ByteBuffer p2, int p3) {
        EXTDrawInstanced.glDrawElementsInstancedEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElementsInstancedEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3));
    }

    public static void glDrawElementsInstancedEXT(int p0, ByteBuffer p1, int p2) {
        EXTDrawInstanced.glDrawElementsInstancedEXT(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsInstancedEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glDrawElementsInstancedEXT(int p0, ShortBuffer p1, int p2) {
        EXTDrawInstanced.glDrawElementsInstancedEXT(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsInstancedEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glDrawElementsInstancedEXT(int p0, IntBuffer p1, int p2) {
        EXTDrawInstanced.glDrawElementsInstancedEXT(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsInstancedEXT", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }
}

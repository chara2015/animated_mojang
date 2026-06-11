package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBMultiBind;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBMultiBindErrorContext.class */
public final class ARBMultiBindErrorContext {
    public static void nglBindBuffersBase(int p0, int p1, int p2, long p3) {
        ARBMultiBind.nglBindBuffersBase(p0, p1, p2, p3);
        LabyDebugContext.glError("nglBindBuffersBase", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glBindBuffersBase(int p0, int p1, IntBuffer p2) {
        ARBMultiBind.glBindBuffersBase(p0, p1, p2);
        LabyDebugContext.glError("glBindBuffersBase", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglBindBuffersRange(int p0, int p1, int p2, long p3, long p4, long p5) {
        ARBMultiBind.nglBindBuffersRange(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglBindBuffersRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glBindBuffersRange(int p0, int p1, IntBuffer p2, PointerBuffer p3, PointerBuffer p4) {
        ARBMultiBind.glBindBuffersRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glBindBuffersRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4);
    }

    public static void nglBindTextures(int p0, int p1, long p2) {
        ARBMultiBind.nglBindTextures(p0, p1, p2);
        LabyDebugContext.glError("nglBindTextures", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glBindTextures(int p0, IntBuffer p1) {
        ARBMultiBind.glBindTextures(p0, p1);
        LabyDebugContext.glError("glBindTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglBindSamplers(int p0, int p1, long p2) {
        ARBMultiBind.nglBindSamplers(p0, p1, p2);
        LabyDebugContext.glError("nglBindSamplers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glBindSamplers(int p0, IntBuffer p1) {
        ARBMultiBind.glBindSamplers(p0, p1);
        LabyDebugContext.glError("glBindSamplers", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglBindImageTextures(int p0, int p1, long p2) {
        ARBMultiBind.nglBindImageTextures(p0, p1, p2);
        LabyDebugContext.glError("nglBindImageTextures", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glBindImageTextures(int p0, IntBuffer p1) {
        ARBMultiBind.glBindImageTextures(p0, p1);
        LabyDebugContext.glError("glBindImageTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglBindVertexBuffers(int p0, int p1, long p2, long p3, long p4) {
        ARBMultiBind.nglBindVertexBuffers(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglBindVertexBuffers", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glBindVertexBuffers(int p0, IntBuffer p1, PointerBuffer p2, IntBuffer p3) {
        ARBMultiBind.glBindVertexBuffers(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindVertexBuffers", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void glBindBuffersBase(int p0, int p1, int[] p2) {
        ARBMultiBind.glBindBuffersBase(p0, p1, p2);
        LabyDebugContext.glError("glBindBuffersBase", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glBindBuffersRange(int p0, int p1, int[] p2, PointerBuffer p3, PointerBuffer p4) {
        ARBMultiBind.glBindBuffersRange(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glBindBuffersRange", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4);
    }

    public static void glBindTextures(int p0, int[] p1) {
        ARBMultiBind.glBindTextures(p0, p1);
        LabyDebugContext.glError("glBindTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glBindSamplers(int p0, int[] p1) {
        ARBMultiBind.glBindSamplers(p0, p1);
        LabyDebugContext.glError("glBindSamplers", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glBindImageTextures(int p0, int[] p1) {
        ARBMultiBind.glBindImageTextures(p0, p1);
        LabyDebugContext.glError("glBindImageTextures", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glBindVertexBuffers(int p0, int[] p1, PointerBuffer p2, int[] p3) {
        ARBMultiBind.glBindVertexBuffers(p0, p1, p2, p3);
        LabyDebugContext.glError("glBindVertexBuffers", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }
}

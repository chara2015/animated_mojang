package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBDrawIndirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBDrawIndirectErrorContext.class */
public final class ARBDrawIndirectErrorContext {
    public static void nglDrawArraysIndirect(int p0, long p1) {
        ARBDrawIndirect.nglDrawArraysIndirect(p0, p1);
        LabyDebugContext.glError("nglDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDrawArraysIndirect(int p0, ByteBuffer p1) {
        ARBDrawIndirect.glDrawArraysIndirect(p0, p1);
        LabyDebugContext.glError("glDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glDrawArraysIndirect(int p0, long p1) {
        ARBDrawIndirect.glDrawArraysIndirect(p0, p1);
        LabyDebugContext.glError("glDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDrawArraysIndirect(int p0, IntBuffer p1) {
        ARBDrawIndirect.glDrawArraysIndirect(p0, p1);
        LabyDebugContext.glError("glDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglDrawElementsIndirect(int p0, int p1, long p2) {
        ARBDrawIndirect.nglDrawElementsIndirect(p0, p1, p2);
        LabyDebugContext.glError("nglDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glDrawElementsIndirect(int p0, int p1, ByteBuffer p2) {
        ARBDrawIndirect.glDrawElementsIndirect(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glDrawElementsIndirect(int p0, int p1, long p2) {
        ARBDrawIndirect.glDrawElementsIndirect(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glDrawElementsIndirect(int p0, int p1, IntBuffer p2) {
        ARBDrawIndirect.glDrawElementsIndirect(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glDrawArraysIndirect(int p0, int[] p1) {
        ARBDrawIndirect.glDrawArraysIndirect(p0, p1);
        LabyDebugContext.glError("glDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glDrawElementsIndirect(int p0, int p1, int[] p2) {
        ARBDrawIndirect.glDrawElementsIndirect(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

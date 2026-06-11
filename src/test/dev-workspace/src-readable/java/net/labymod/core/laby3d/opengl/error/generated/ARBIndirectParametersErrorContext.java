package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBIndirectParameters;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBIndirectParametersErrorContext.class */
public final class ARBIndirectParametersErrorContext {
    public static void nglMultiDrawArraysIndirectCountARB(int p0, long p1, long p2, int p3, int p4) {
        ARBIndirectParameters.nglMultiDrawArraysIndirectCountARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglMultiDrawArraysIndirectCountARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glMultiDrawArraysIndirectCountARB(int p0, ByteBuffer p1, long p2, int p3, int p4) {
        ARBIndirectParameters.glMultiDrawArraysIndirectCountARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawArraysIndirectCountARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glMultiDrawArraysIndirectCountARB(int p0, long p1, long p2, int p3, int p4) {
        ARBIndirectParameters.glMultiDrawArraysIndirectCountARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawArraysIndirectCountARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glMultiDrawArraysIndirectCountARB(int p0, IntBuffer p1, long p2, int p3, int p4) {
        ARBIndirectParameters.glMultiDrawArraysIndirectCountARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawArraysIndirectCountARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglMultiDrawElementsIndirectCountARB(int p0, int p1, long p2, long p3, int p4, int p5) {
        ARBIndirectParameters.nglMultiDrawElementsIndirectCountARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglMultiDrawElementsIndirectCountARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glMultiDrawElementsIndirectCountARB(int p0, int p1, ByteBuffer p2, long p3, int p4, int p5) {
        ARBIndirectParameters.glMultiDrawElementsIndirectCountARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMultiDrawElementsIndirectCountARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glMultiDrawElementsIndirectCountARB(int p0, int p1, long p2, long p3, int p4, int p5) {
        ARBIndirectParameters.glMultiDrawElementsIndirectCountARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMultiDrawElementsIndirectCountARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glMultiDrawElementsIndirectCountARB(int p0, int p1, IntBuffer p2, long p3, int p4, int p5) {
        ARBIndirectParameters.glMultiDrawElementsIndirectCountARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMultiDrawElementsIndirectCountARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glMultiDrawArraysIndirectCountARB(int p0, int[] p1, long p2, int p3, int p4) {
        ARBIndirectParameters.glMultiDrawArraysIndirectCountARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawArraysIndirectCountARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glMultiDrawElementsIndirectCountARB(int p0, int p1, int[] p2, long p3, int p4, int p5) {
        ARBIndirectParameters.glMultiDrawElementsIndirectCountARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMultiDrawElementsIndirectCountARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }
}

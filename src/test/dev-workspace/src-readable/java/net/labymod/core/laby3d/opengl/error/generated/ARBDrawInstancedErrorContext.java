package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBDrawInstanced;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBDrawInstancedErrorContext.class */
public final class ARBDrawInstancedErrorContext {
    public static void glDrawArraysInstancedARB(int p0, int p1, int p2, int p3) {
        ARBDrawInstanced.glDrawArraysInstancedARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawArraysInstancedARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglDrawElementsInstancedARB(int p0, int p1, int p2, long p3, int p4) {
        ARBDrawInstanced.nglDrawElementsInstancedARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglDrawElementsInstancedARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsInstancedARB(int p0, int p1, int p2, long p3, int p4) {
        ARBDrawInstanced.glDrawElementsInstancedARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawElementsInstancedARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawElementsInstancedARB(int p0, int p1, ByteBuffer p2, int p3) {
        ARBDrawInstanced.glDrawElementsInstancedARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElementsInstancedARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3));
    }

    public static void glDrawElementsInstancedARB(int p0, ByteBuffer p1, int p2) {
        ARBDrawInstanced.glDrawElementsInstancedARB(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsInstancedARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glDrawElementsInstancedARB(int p0, ShortBuffer p1, int p2) {
        ARBDrawInstanced.glDrawElementsInstancedARB(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsInstancedARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }

    public static void glDrawElementsInstancedARB(int p0, IntBuffer p1, int p2) {
        ARBDrawInstanced.glDrawElementsInstancedARB(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsInstancedARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2));
    }
}

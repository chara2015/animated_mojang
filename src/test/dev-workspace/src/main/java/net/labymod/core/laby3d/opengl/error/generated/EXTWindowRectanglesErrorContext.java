package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTWindowRectangles;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTWindowRectanglesErrorContext.class */
public final class EXTWindowRectanglesErrorContext {
    public static void nglWindowRectanglesEXT(int p0, int p1, long p2) {
        EXTWindowRectangles.nglWindowRectanglesEXT(p0, p1, p2);
        LabyDebugContext.glError("nglWindowRectanglesEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glWindowRectanglesEXT(int p0, IntBuffer p1) {
        EXTWindowRectangles.glWindowRectanglesEXT(p0, p1);
        LabyDebugContext.glError("glWindowRectanglesEXT", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glWindowRectanglesEXT(int p0, int[] p1) {
        EXTWindowRectangles.glWindowRectanglesEXT(p0, p1);
        LabyDebugContext.glError("glWindowRectanglesEXT", "p0", Integer.valueOf(p0), "p1", p1);
    }
}

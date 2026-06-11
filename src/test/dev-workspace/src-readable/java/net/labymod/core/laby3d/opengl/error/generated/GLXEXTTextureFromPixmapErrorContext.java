package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLXEXTTextureFromPixmap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXEXTTextureFromPixmapErrorContext.class */
public final class GLXEXTTextureFromPixmapErrorContext {
    public static void nglXBindTexImageEXT(long p0, long p1, int p2, long p3) {
        GLXEXTTextureFromPixmap.nglXBindTexImageEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXBindTexImageEXT", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glXBindTexImageEXT(long p0, long p1, int p2, IntBuffer p3) {
        GLXEXTTextureFromPixmap.glXBindTexImageEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glXBindTexImageEXT", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glXReleaseTexImageEXT(long p0, long p1, int p2) {
        GLXEXTTextureFromPixmap.glXReleaseTexImageEXT(p0, p1, p2);
        LabyDebugContext.glError("glXReleaseTexImageEXT", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glXBindTexImageEXT(long p0, long p1, int p2, int[] p3) {
        GLXEXTTextureFromPixmap.glXBindTexImageEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glXBindTexImageEXT", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }
}

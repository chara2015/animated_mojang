package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVPixelDataRange;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVPixelDataRangeErrorContext.class */
public final class NVPixelDataRangeErrorContext {
    public static void nglPixelDataRangeNV(int p0, int p1, long p2) {
        NVPixelDataRange.nglPixelDataRangeNV(p0, p1, p2);
        LabyDebugContext.glError("nglPixelDataRangeNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glPixelDataRangeNV(int p0, ByteBuffer p1) {
        NVPixelDataRange.glPixelDataRangeNV(p0, p1);
        LabyDebugContext.glError("glPixelDataRangeNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glFlushPixelDataRangeNV(int p0) {
        NVPixelDataRange.glFlushPixelDataRangeNV(p0);
        LabyDebugContext.glError("glFlushPixelDataRangeNV", "p0", Integer.valueOf(p0));
    }
}

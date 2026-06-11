package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVInternalformatSampleQuery;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVInternalformatSampleQueryErrorContext.class */
public final class NVInternalformatSampleQueryErrorContext {
    public static void nglGetInternalformatSampleivNV(int p0, int p1, int p2, int p3, int p4, long p5) {
        NVInternalformatSampleQuery.nglGetInternalformatSampleivNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetInternalformatSampleivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetInternalformatSampleivNV(int p0, int p1, int p2, int p3, IntBuffer p4) {
        NVInternalformatSampleQuery.glGetInternalformatSampleivNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetInternalformatSampleivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetInternalformatSampleivNV(int p0, int p1, int p2, int p3, int[] p4) {
        NVInternalformatSampleQuery.glGetInternalformatSampleivNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetInternalformatSampleivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.AMDSamplePositions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/AMDSamplePositionsErrorContext.class */
public final class AMDSamplePositionsErrorContext {
    public static void nglSetMultisamplefvAMD(int p0, int p1, long p2) {
        AMDSamplePositions.nglSetMultisamplefvAMD(p0, p1, p2);
        LabyDebugContext.glError("nglSetMultisamplefvAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glSetMultisamplefvAMD(int p0, int p1, FloatBuffer p2) {
        AMDSamplePositions.glSetMultisamplefvAMD(p0, p1, p2);
        LabyDebugContext.glError("glSetMultisamplefvAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glSetMultisamplefvAMD(int p0, int p1, float[] p2) {
        AMDSamplePositions.glSetMultisamplefvAMD(p0, p1, p2);
        LabyDebugContext.glError("glSetMultisamplefvAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

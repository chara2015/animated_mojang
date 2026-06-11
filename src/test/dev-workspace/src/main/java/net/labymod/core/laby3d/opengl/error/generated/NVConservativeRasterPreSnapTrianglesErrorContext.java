package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVConservativeRasterPreSnapTriangles;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVConservativeRasterPreSnapTrianglesErrorContext.class */
public final class NVConservativeRasterPreSnapTrianglesErrorContext {
    public static void glConservativeRasterParameteriNV(int p0, int p1) {
        NVConservativeRasterPreSnapTriangles.glConservativeRasterParameteriNV(p0, p1);
        LabyDebugContext.glError("glConservativeRasterParameteriNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }
}

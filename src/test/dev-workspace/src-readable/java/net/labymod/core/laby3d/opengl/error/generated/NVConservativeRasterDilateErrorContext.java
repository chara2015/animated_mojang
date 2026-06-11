package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVConservativeRasterDilate;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVConservativeRasterDilateErrorContext.class */
public final class NVConservativeRasterDilateErrorContext {
    public static void glConservativeRasterParameterfNV(int p0, float p1) {
        NVConservativeRasterDilate.glConservativeRasterParameterfNV(p0, p1);
        LabyDebugContext.glError("glConservativeRasterParameterfNV", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVConservativeRaster;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVConservativeRasterErrorContext.class */
public final class NVConservativeRasterErrorContext {
    public static void glSubpixelPrecisionBiasNV(int p0, int p1) {
        NVConservativeRaster.glSubpixelPrecisionBiasNV(p0, p1);
        LabyDebugContext.glError("glSubpixelPrecisionBiasNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }
}

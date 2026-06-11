package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBES31Compatibility;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBES31CompatibilityErrorContext.class */
public final class ARBES31CompatibilityErrorContext {
    public static void glMemoryBarrierByRegion(int p0) {
        ARBES31Compatibility.glMemoryBarrierByRegion(p0);
        LabyDebugContext.glError("glMemoryBarrierByRegion", "p0", Integer.valueOf(p0));
    }
}

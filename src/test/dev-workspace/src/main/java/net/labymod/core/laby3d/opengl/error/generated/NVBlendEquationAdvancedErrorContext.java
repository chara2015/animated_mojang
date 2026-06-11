package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVBlendEquationAdvanced;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVBlendEquationAdvancedErrorContext.class */
public final class NVBlendEquationAdvancedErrorContext {
    public static void glBlendParameteriNV(int p0, int p1) {
        NVBlendEquationAdvanced.glBlendParameteriNV(p0, p1);
        LabyDebugContext.glError("glBlendParameteriNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glBlendBarrierNV() {
        NVBlendEquationAdvanced.glBlendBarrierNV();
        LabyDebugContext.glError("glBlendBarrierNV", new Object[0]);
    }
}

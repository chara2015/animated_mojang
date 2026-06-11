package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.KHRBlendEquationAdvanced;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/KHRBlendEquationAdvancedErrorContext.class */
public final class KHRBlendEquationAdvancedErrorContext {
    public static void glBlendBarrierKHR() {
        KHRBlendEquationAdvanced.glBlendBarrierKHR();
        LabyDebugContext.glError("glBlendBarrierKHR", new Object[0]);
    }
}

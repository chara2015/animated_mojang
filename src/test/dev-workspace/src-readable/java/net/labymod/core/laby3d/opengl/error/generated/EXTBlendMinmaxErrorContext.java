package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTBlendMinmax;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTBlendMinmaxErrorContext.class */
public final class EXTBlendMinmaxErrorContext {
    public static void glBlendEquationEXT(int p0) {
        EXTBlendMinmax.glBlendEquationEXT(p0);
        LabyDebugContext.glError("glBlendEquationEXT", "p0", Integer.valueOf(p0));
    }
}

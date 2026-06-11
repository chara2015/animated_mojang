package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTBlendEquationSeparate;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTBlendEquationSeparateErrorContext.class */
public final class EXTBlendEquationSeparateErrorContext {
    public static void glBlendEquationSeparateEXT(int p0, int p1) {
        EXTBlendEquationSeparate.glBlendEquationSeparateEXT(p0, p1);
        LabyDebugContext.glError("glBlendEquationSeparateEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }
}

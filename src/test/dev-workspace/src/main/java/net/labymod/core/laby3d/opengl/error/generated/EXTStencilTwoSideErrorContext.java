package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTStencilTwoSide;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTStencilTwoSideErrorContext.class */
public final class EXTStencilTwoSideErrorContext {
    public static void glActiveStencilFaceEXT(int p0) {
        EXTStencilTwoSide.glActiveStencilFaceEXT(p0);
        LabyDebugContext.glError("glActiveStencilFaceEXT", "p0", Integer.valueOf(p0));
    }
}

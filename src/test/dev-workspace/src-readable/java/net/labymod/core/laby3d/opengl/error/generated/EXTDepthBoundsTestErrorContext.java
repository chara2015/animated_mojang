package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTDepthBoundsTest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTDepthBoundsTestErrorContext.class */
public final class EXTDepthBoundsTestErrorContext {
    public static void glDepthBoundsEXT(double p0, double p1) {
        EXTDepthBoundsTest.glDepthBoundsEXT(p0, p1);
        LabyDebugContext.glError("glDepthBoundsEXT", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1));
    }
}

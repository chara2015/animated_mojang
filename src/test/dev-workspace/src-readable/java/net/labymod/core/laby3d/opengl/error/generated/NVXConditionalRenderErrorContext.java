package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVXConditionalRender;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVXConditionalRenderErrorContext.class */
public final class NVXConditionalRenderErrorContext {
    public static void glBeginConditionalRenderNVX(int p0) {
        NVXConditionalRender.glBeginConditionalRenderNVX(p0);
        LabyDebugContext.glError("glBeginConditionalRenderNVX", "p0", Integer.valueOf(p0));
    }

    public static void glEndConditionalRenderNVX() {
        NVXConditionalRender.glEndConditionalRenderNVX();
        LabyDebugContext.glError("glEndConditionalRenderNVX", new Object[0]);
    }
}

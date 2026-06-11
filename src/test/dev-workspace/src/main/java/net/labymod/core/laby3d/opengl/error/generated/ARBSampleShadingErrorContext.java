package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBSampleShading;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBSampleShadingErrorContext.class */
public final class ARBSampleShadingErrorContext {
    public static void glMinSampleShadingARB(float p0) {
        ARBSampleShading.glMinSampleShadingARB(p0);
        LabyDebugContext.glError("glMinSampleShadingARB", "p0", Float.valueOf(p0));
    }
}

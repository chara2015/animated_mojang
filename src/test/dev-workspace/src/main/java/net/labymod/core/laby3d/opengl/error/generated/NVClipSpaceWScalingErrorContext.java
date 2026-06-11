package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVClipSpaceWScaling;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVClipSpaceWScalingErrorContext.class */
public final class NVClipSpaceWScalingErrorContext {
    public static void glViewportPositionWScaleNV(int p0, float p1, float p2) {
        NVClipSpaceWScaling.glViewportPositionWScaleNV(p0, p1, p2);
        LabyDebugContext.glError("glViewportPositionWScaleNV", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBES32Compatibility;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBES32CompatibilityErrorContext.class */
public final class ARBES32CompatibilityErrorContext {
    public static void glPrimitiveBoundingBoxARB(float p0, float p1, float p2, float p3, float p4, float p5, float p6, float p7) {
        ARBES32Compatibility.glPrimitiveBoundingBoxARB(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glPrimitiveBoundingBoxARB", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5), "p6", Float.valueOf(p6), "p7", Float.valueOf(p7));
    }
}

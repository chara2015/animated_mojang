package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBMultisample;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBMultisampleErrorContext.class */
public final class ARBMultisampleErrorContext {
    public static void glSampleCoverageARB(float p0, boolean p1) {
        ARBMultisample.glSampleCoverageARB(p0, p1);
        LabyDebugContext.glError("glSampleCoverageARB", "p0", Float.valueOf(p0), "p1", Boolean.valueOf(p1));
    }
}

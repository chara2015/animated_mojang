package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBColorBufferFloat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBColorBufferFloatErrorContext.class */
public final class ARBColorBufferFloatErrorContext {
    public static void glClampColorARB(int p0, int p1) {
        ARBColorBufferFloat.glClampColorARB(p0, p1);
        LabyDebugContext.glError("glClampColorARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }
}

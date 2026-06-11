package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBClipControl;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBClipControlErrorContext.class */
public final class ARBClipControlErrorContext {
    public static void glClipControl(int p0, int p1) {
        ARBClipControl.glClipControl(p0, p1);
        LabyDebugContext.glError("glClipControl", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }
}

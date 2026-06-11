package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLX12;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLX12ErrorContext.class */
public final class GLX12ErrorContext {
    public static long glXGetCurrentDisplay() {
        long returnType = GLX12.glXGetCurrentDisplay();
        LabyDebugContext.glError("glXGetCurrentDisplay", new Object[0]);
        return returnType;
    }
}

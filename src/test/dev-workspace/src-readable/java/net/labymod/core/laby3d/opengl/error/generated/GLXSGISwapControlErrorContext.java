package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLXSGISwapControl;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXSGISwapControlErrorContext.class */
public final class GLXSGISwapControlErrorContext {
    public static int glXSwapIntervalSGI(int p0) {
        int returnType = GLXSGISwapControl.glXSwapIntervalSGI(p0);
        LabyDebugContext.glError("glXSwapIntervalSGI", "p0", Integer.valueOf(p0));
        return returnType;
    }
}

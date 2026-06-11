package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLCapabilities;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLCapabilitiesErrorContext.class */
public final class GLCapabilitiesErrorContext {
    public static void initialize() {
        GLCapabilities.initialize();
        LabyDebugContext.glError("initialize", new Object[0]);
    }
}

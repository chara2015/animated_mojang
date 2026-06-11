package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTFramebufferMultisample;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTFramebufferMultisampleErrorContext.class */
public final class EXTFramebufferMultisampleErrorContext {
    public static void glRenderbufferStorageMultisampleEXT(int p0, int p1, int p2, int p3, int p4) {
        EXTFramebufferMultisample.glRenderbufferStorageMultisampleEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glRenderbufferStorageMultisampleEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }
}

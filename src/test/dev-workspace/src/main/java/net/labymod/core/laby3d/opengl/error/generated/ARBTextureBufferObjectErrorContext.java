package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBTextureBufferObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBTextureBufferObjectErrorContext.class */
public final class ARBTextureBufferObjectErrorContext {
    public static void glTexBufferARB(int p0, int p1, int p2) {
        ARBTextureBufferObject.glTexBufferARB(p0, p1, p2);
        LabyDebugContext.glError("glTexBufferARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTTextureBufferObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTTextureBufferObjectErrorContext.class */
public final class EXTTextureBufferObjectErrorContext {
    public static void glTexBufferEXT(int p0, int p1, int p2) {
        EXTTextureBufferObject.glTexBufferEXT(p0, p1, p2);
        LabyDebugContext.glError("glTexBufferEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }
}

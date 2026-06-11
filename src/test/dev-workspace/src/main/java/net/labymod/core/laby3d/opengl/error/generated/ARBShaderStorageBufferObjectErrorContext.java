package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBShaderStorageBufferObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBShaderStorageBufferObjectErrorContext.class */
public final class ARBShaderStorageBufferObjectErrorContext {
    public static void glShaderStorageBlockBinding(int p0, int p1, int p2) {
        ARBShaderStorageBufferObject.glShaderStorageBlockBinding(p0, p1, p2);
        LabyDebugContext.glError("glShaderStorageBlockBinding", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }
}

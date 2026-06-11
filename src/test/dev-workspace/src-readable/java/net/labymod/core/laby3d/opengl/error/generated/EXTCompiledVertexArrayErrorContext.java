package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTCompiledVertexArray;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTCompiledVertexArrayErrorContext.class */
public final class EXTCompiledVertexArrayErrorContext {
    public static void glLockArraysEXT(int p0, int p1) {
        EXTCompiledVertexArray.glLockArraysEXT(p0, p1);
        LabyDebugContext.glError("glLockArraysEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glUnlockArraysEXT() {
        EXTCompiledVertexArray.glUnlockArraysEXT();
        LabyDebugContext.glError("glUnlockArraysEXT", new Object[0]);
    }
}

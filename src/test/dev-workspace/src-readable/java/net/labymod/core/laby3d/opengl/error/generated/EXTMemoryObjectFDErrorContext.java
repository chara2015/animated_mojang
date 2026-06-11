package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTMemoryObjectFD;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTMemoryObjectFDErrorContext.class */
public final class EXTMemoryObjectFDErrorContext {
    public static void glImportMemoryFdEXT(int p0, long p1, int p2, int p3) {
        EXTMemoryObjectFD.glImportMemoryFdEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glImportMemoryFdEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }
}

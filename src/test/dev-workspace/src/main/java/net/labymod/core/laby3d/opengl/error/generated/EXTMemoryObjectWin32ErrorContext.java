package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTMemoryObjectWin32;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTMemoryObjectWin32ErrorContext.class */
public final class EXTMemoryObjectWin32ErrorContext {
    public static void nglImportMemoryWin32HandleEXT(int p0, long p1, int p2, long p3) {
        EXTMemoryObjectWin32.nglImportMemoryWin32HandleEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("nglImportMemoryWin32HandleEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glImportMemoryWin32HandleEXT(int p0, long p1, int p2, long p3) {
        EXTMemoryObjectWin32.glImportMemoryWin32HandleEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glImportMemoryWin32HandleEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void nglImportMemoryWin32NameEXT(int p0, long p1, int p2, long p3) {
        EXTMemoryObjectWin32.nglImportMemoryWin32NameEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("nglImportMemoryWin32NameEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glImportMemoryWin32NameEXT(int p0, long p1, int p2, long p3) {
        EXTMemoryObjectWin32.glImportMemoryWin32NameEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glImportMemoryWin32NameEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }
}

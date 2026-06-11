package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTSemaphoreWin32;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTSemaphoreWin32ErrorContext.class */
public final class EXTSemaphoreWin32ErrorContext {
    public static void nglImportSemaphoreWin32HandleEXT(int p0, int p1, long p2) {
        EXTSemaphoreWin32.nglImportSemaphoreWin32HandleEXT(p0, p1, p2);
        LabyDebugContext.glError("nglImportSemaphoreWin32HandleEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glImportSemaphoreWin32HandleEXT(int p0, int p1, long p2) {
        EXTSemaphoreWin32.glImportSemaphoreWin32HandleEXT(p0, p1, p2);
        LabyDebugContext.glError("glImportSemaphoreWin32HandleEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void nglImportSemaphoreWin32NameEXT(int p0, int p1, long p2) {
        EXTSemaphoreWin32.nglImportSemaphoreWin32NameEXT(p0, p1, p2);
        LabyDebugContext.glError("nglImportSemaphoreWin32NameEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glImportSemaphoreWin32NameEXT(int p0, int p1, long p2) {
        EXTSemaphoreWin32.glImportSemaphoreWin32NameEXT(p0, p1, p2);
        LabyDebugContext.glError("glImportSemaphoreWin32NameEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTX11SyncObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTX11SyncObjectErrorContext.class */
public final class EXTX11SyncObjectErrorContext {
    public static long glImportSyncEXT(int p0, long p1, int p2) {
        long returnType = EXTX11SyncObject.glImportSyncEXT(p0, p1, p2);
        LabyDebugContext.glError("glImportSyncEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }
}

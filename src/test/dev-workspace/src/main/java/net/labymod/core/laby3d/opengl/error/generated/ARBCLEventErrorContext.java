package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBCLEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBCLEventErrorContext.class */
public final class ARBCLEventErrorContext {
    public static long nglCreateSyncFromCLeventARB(long p0, long p1, int p2) {
        long returnType = ARBCLEvent.nglCreateSyncFromCLeventARB(p0, p1, p2);
        LabyDebugContext.glError("nglCreateSyncFromCLeventARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static long glCreateSyncFromCLeventARB(long p0, long p1, int p2) {
        long returnType = ARBCLEvent.glCreateSyncFromCLeventARB(p0, p1, p2);
        LabyDebugContext.glError("glCreateSyncFromCLeventARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }
}

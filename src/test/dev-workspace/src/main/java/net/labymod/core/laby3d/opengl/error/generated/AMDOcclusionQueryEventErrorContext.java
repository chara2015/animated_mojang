package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.AMDOcclusionQueryEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/AMDOcclusionQueryEventErrorContext.class */
public final class AMDOcclusionQueryEventErrorContext {
    public static void glQueryObjectParameteruiAMD(int p0, int p1, int p2, int p3) {
        AMDOcclusionQueryEvent.glQueryObjectParameteruiAMD(p0, p1, p2, p3);
        LabyDebugContext.glError("glQueryObjectParameteruiAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }
}

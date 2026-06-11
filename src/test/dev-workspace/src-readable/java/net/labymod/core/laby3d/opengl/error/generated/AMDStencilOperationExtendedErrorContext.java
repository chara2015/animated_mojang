package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.AMDStencilOperationExtended;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/AMDStencilOperationExtendedErrorContext.class */
public final class AMDStencilOperationExtendedErrorContext {
    public static void glStencilOpValueAMD(int p0, int p1) {
        AMDStencilOperationExtended.glStencilOpValueAMD(p0, p1);
        LabyDebugContext.glError("glStencilOpValueAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }
}

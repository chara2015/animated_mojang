package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVAlphaToCoverageDitherControl;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVAlphaToCoverageDitherControlErrorContext.class */
public final class NVAlphaToCoverageDitherControlErrorContext {
    public static void glAlphaToCoverageDitherControlNV(int p0) {
        NVAlphaToCoverageDitherControl.glAlphaToCoverageDitherControlNV(p0);
        LabyDebugContext.glError("glAlphaToCoverageDitherControlNV", "p0", Integer.valueOf(p0));
    }
}

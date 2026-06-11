package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVFragmentCoverageToColor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVFragmentCoverageToColorErrorContext.class */
public final class NVFragmentCoverageToColorErrorContext {
    public static void glFragmentCoverageColorNV(int p0) {
        NVFragmentCoverageToColor.glFragmentCoverageColorNV(p0);
        LabyDebugContext.glError("glFragmentCoverageColorNV", "p0", Integer.valueOf(p0));
    }
}

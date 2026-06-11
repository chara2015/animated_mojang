package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTRasterMultisample;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTRasterMultisampleErrorContext.class */
public final class EXTRasterMultisampleErrorContext {
    public static void glRasterSamplesEXT(int p0, boolean p1) {
        EXTRasterMultisample.glRasterSamplesEXT(p0, p1);
        LabyDebugContext.glError("glRasterSamplesEXT", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1));
    }
}

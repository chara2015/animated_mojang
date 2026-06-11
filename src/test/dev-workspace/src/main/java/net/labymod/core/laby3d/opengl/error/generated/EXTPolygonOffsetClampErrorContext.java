package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTPolygonOffsetClamp;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTPolygonOffsetClampErrorContext.class */
public final class EXTPolygonOffsetClampErrorContext {
    public static void glPolygonOffsetClampEXT(float p0, float p1, float p2) {
        EXTPolygonOffsetClamp.glPolygonOffsetClampEXT(p0, p1, p2);
        LabyDebugContext.glError("glPolygonOffsetClampEXT", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }
}

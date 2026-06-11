package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTPointParameters;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTPointParametersErrorContext.class */
public final class EXTPointParametersErrorContext {
    public static void glPointParameterfEXT(int p0, float p1) {
        EXTPointParameters.glPointParameterfEXT(p0, p1);
        LabyDebugContext.glError("glPointParameterfEXT", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void nglPointParameterfvEXT(int p0, long p1) {
        EXTPointParameters.nglPointParameterfvEXT(p0, p1);
        LabyDebugContext.glError("nglPointParameterfvEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glPointParameterfvEXT(int p0, FloatBuffer p1) {
        EXTPointParameters.glPointParameterfvEXT(p0, p1);
        LabyDebugContext.glError("glPointParameterfvEXT", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glPointParameterfvEXT(int p0, float[] p1) {
        EXTPointParameters.glPointParameterfvEXT(p0, p1);
        LabyDebugContext.glError("glPointParameterfvEXT", "p0", Integer.valueOf(p0), "p1", p1);
    }
}

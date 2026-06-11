package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.FloatBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBPointParameters;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBPointParametersErrorContext.class */
public final class ARBPointParametersErrorContext {
    public static void glPointParameterfARB(int p0, float p1) {
        ARBPointParameters.glPointParameterfARB(p0, p1);
        LabyDebugContext.glError("glPointParameterfARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void nglPointParameterfvARB(int p0, long p1) {
        ARBPointParameters.nglPointParameterfvARB(p0, p1);
        LabyDebugContext.glError("nglPointParameterfvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glPointParameterfvARB(int p0, FloatBuffer p1) {
        ARBPointParameters.glPointParameterfvARB(p0, p1);
        LabyDebugContext.glError("glPointParameterfvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glPointParameterfvARB(int p0, float[] p1) {
        ARBPointParameters.glPointParameterfvARB(p0, p1);
        LabyDebugContext.glError("glPointParameterfvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }
}

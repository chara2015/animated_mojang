package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLXARBCreateContext;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXARBCreateContextErrorContext.class */
public final class GLXARBCreateContextErrorContext {
    public static long nglXCreateContextAttribsARB(long p0, long p1, long p2, int p3, long p4) {
        long returnType = GLXARBCreateContext.nglXCreateContextAttribsARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglXCreateContextAttribsARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
        return returnType;
    }

    public static long glXCreateContextAttribsARB(long p0, long p1, long p2, boolean p3, IntBuffer p4) {
        long returnType = GLXARBCreateContext.glXCreateContextAttribsARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glXCreateContextAttribsARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static long glXCreateContextAttribsARB(long p0, long p1, long p2, boolean p3, int[] p4) {
        long returnType = GLXARBCreateContext.glXCreateContextAttribsARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glXCreateContextAttribsARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", p4);
        return returnType;
    }
}

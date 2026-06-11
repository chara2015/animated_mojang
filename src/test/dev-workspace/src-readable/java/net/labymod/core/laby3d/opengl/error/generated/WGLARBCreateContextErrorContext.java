package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLARBCreateContext;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLARBCreateContextErrorContext.class */
public final class WGLARBCreateContextErrorContext {
    public static long nwglCreateContextAttribsARB(long p0, long p1, long p2) {
        long returnType = WGLARBCreateContext.nwglCreateContextAttribsARB(p0, p1, p2);
        LabyDebugContext.glError("nwglCreateContextAttribsARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static long wglCreateContextAttribsARB(long p0, long p1, IntBuffer p2) {
        long returnType = WGLARBCreateContext.wglCreateContextAttribsARB(p0, p1, p2);
        LabyDebugContext.glError("wglCreateContextAttribsARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static long wglCreateContextAttribsARB(long p0, long p1, int[] p2) {
        long returnType = WGLARBCreateContext.wglCreateContextAttribsARB(p0, p1, p2);
        LabyDebugContext.glError("wglCreateContextAttribsARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
        return returnType;
    }
}

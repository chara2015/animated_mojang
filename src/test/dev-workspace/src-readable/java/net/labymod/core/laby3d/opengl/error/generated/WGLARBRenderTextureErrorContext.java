package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLARBRenderTexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLARBRenderTextureErrorContext.class */
public final class WGLARBRenderTextureErrorContext {
    public static boolean wglBindTexImageARB(long p0, int p1) {
        boolean returnType = WGLARBRenderTexture.wglBindTexImageARB(p0, p1);
        LabyDebugContext.glError("wglBindTexImageARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static boolean wglReleaseTexImageARB(long p0, int p1) {
        boolean returnType = WGLARBRenderTexture.wglReleaseTexImageARB(p0, p1);
        LabyDebugContext.glError("wglReleaseTexImageARB", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static int nwglSetPbufferAttribARB(long p0, long p1) {
        int returnType = WGLARBRenderTexture.nwglSetPbufferAttribARB(p0, p1);
        LabyDebugContext.glError("nwglSetPbufferAttribARB", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static boolean wglSetPbufferAttribARB(long p0, IntBuffer p1) {
        boolean returnType = WGLARBRenderTexture.wglSetPbufferAttribARB(p0, p1);
        LabyDebugContext.glError("wglSetPbufferAttribARB", "p0", Long.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static boolean wglSetPbufferAttribARB(long p0, int[] p1) {
        boolean returnType = WGLARBRenderTexture.wglSetPbufferAttribARB(p0, p1);
        LabyDebugContext.glError("wglSetPbufferAttribARB", "p0", Long.valueOf(p0), "p1", p1);
        return returnType;
    }
}

package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLNVVertexArrayRange;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLNVVertexArrayRangeErrorContext.class */
public final class WGLNVVertexArrayRangeErrorContext {
    public static long nwglAllocateMemoryNV(int p0, float p1, float p2, float p3) {
        long returnType = WGLNVVertexArrayRange.nwglAllocateMemoryNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nwglAllocateMemoryNV", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
        return returnType;
    }

    public static ByteBuffer wglAllocateMemoryNV(int p0, float p1, float p2, float p3) {
        ByteBuffer returnType = WGLNVVertexArrayRange.wglAllocateMemoryNV(p0, p1, p2, p3);
        LabyDebugContext.glError("wglAllocateMemoryNV", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
        return returnType;
    }

    public static void nwglFreeMemoryNV(long p0) {
        WGLNVVertexArrayRange.nwglFreeMemoryNV(p0);
        LabyDebugContext.glError("nwglFreeMemoryNV", "p0", Long.valueOf(p0));
    }

    public static void wglFreeMemoryNV(ByteBuffer p0) {
        WGLNVVertexArrayRange.wglFreeMemoryNV(p0);
        LabyDebugContext.glError("wglFreeMemoryNV", "p0", p0);
    }
}

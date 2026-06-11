package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLNVSwapGroup;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLNVSwapGroupErrorContext.class */
public final class WGLNVSwapGroupErrorContext {
    public static boolean wglJoinSwapGroupNV(long p0, int p1) {
        boolean returnType = WGLNVSwapGroup.wglJoinSwapGroupNV(p0, p1);
        LabyDebugContext.glError("wglJoinSwapGroupNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static boolean wglBindSwapBarrierNV(int p0, int p1) {
        boolean returnType = WGLNVSwapGroup.wglBindSwapBarrierNV(p0, p1);
        LabyDebugContext.glError("wglBindSwapBarrierNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static int nwglQuerySwapGroupNV(long p0, long p1, long p2) {
        int returnType = WGLNVSwapGroup.nwglQuerySwapGroupNV(p0, p1, p2);
        LabyDebugContext.glError("nwglQuerySwapGroupNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean wglQuerySwapGroupNV(long p0, IntBuffer p1, IntBuffer p2) {
        boolean returnType = WGLNVSwapGroup.wglQuerySwapGroupNV(p0, p1, p2);
        LabyDebugContext.glError("wglQuerySwapGroupNV", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
        return returnType;
    }

    public static int nwglQueryMaxSwapGroupsNV(long p0, long p1, long p2) {
        int returnType = WGLNVSwapGroup.nwglQueryMaxSwapGroupsNV(p0, p1, p2);
        LabyDebugContext.glError("nwglQueryMaxSwapGroupsNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean wglQueryMaxSwapGroupsNV(long p0, IntBuffer p1, IntBuffer p2) {
        boolean returnType = WGLNVSwapGroup.wglQueryMaxSwapGroupsNV(p0, p1, p2);
        LabyDebugContext.glError("wglQueryMaxSwapGroupsNV", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
        return returnType;
    }

    public static int nwglQueryFrameCountNV(long p0, long p1) {
        int returnType = WGLNVSwapGroup.nwglQueryFrameCountNV(p0, p1);
        LabyDebugContext.glError("nwglQueryFrameCountNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static boolean wglQueryFrameCountNV(long p0, IntBuffer p1) {
        boolean returnType = WGLNVSwapGroup.wglQueryFrameCountNV(p0, p1);
        LabyDebugContext.glError("wglQueryFrameCountNV", "p0", Long.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static boolean wglResetFrameCountNV(long p0) {
        boolean returnType = WGLNVSwapGroup.wglResetFrameCountNV(p0);
        LabyDebugContext.glError("wglResetFrameCountNV", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static boolean wglQuerySwapGroupNV(long p0, int[] p1, int[] p2) {
        boolean returnType = WGLNVSwapGroup.wglQuerySwapGroupNV(p0, p1, p2);
        LabyDebugContext.glError("wglQuerySwapGroupNV", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
        return returnType;
    }

    public static boolean wglQueryMaxSwapGroupsNV(long p0, int[] p1, int[] p2) {
        boolean returnType = WGLNVSwapGroup.wglQueryMaxSwapGroupsNV(p0, p1, p2);
        LabyDebugContext.glError("wglQueryMaxSwapGroupsNV", "p0", Long.valueOf(p0), "p1", p1, "p2", p2);
        return returnType;
    }

    public static boolean wglQueryFrameCountNV(long p0, int[] p1) {
        boolean returnType = WGLNVSwapGroup.wglQueryFrameCountNV(p0, p1);
        LabyDebugContext.glError("wglQueryFrameCountNV", "p0", Long.valueOf(p0), "p1", p1);
        return returnType;
    }
}

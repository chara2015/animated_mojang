package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLXNVSwapGroup;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXNVSwapGroupErrorContext.class */
public final class GLXNVSwapGroupErrorContext {
    public static boolean glXJoinSwapGroupNV(long p0, long p1, int p2) {
        boolean returnType = GLXNVSwapGroup.glXJoinSwapGroupNV(p0, p1, p2);
        LabyDebugContext.glError("glXJoinSwapGroupNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static boolean glXBindSwapBarrierNV(long p0, int p1, int p2) {
        boolean returnType = GLXNVSwapGroup.glXBindSwapBarrierNV(p0, p1, p2);
        LabyDebugContext.glError("glXBindSwapBarrierNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static int nglXQuerySwapGroupNV(long p0, long p1, long p2, long p3) {
        int returnType = GLXNVSwapGroup.nglXQuerySwapGroupNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXQuerySwapGroupNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static boolean glXQuerySwapGroupNV(long p0, long p1, IntBuffer p2, IntBuffer p3) {
        boolean returnType = GLXNVSwapGroup.glXQuerySwapGroupNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQuerySwapGroupNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2, "p3", p3);
        return returnType;
    }

    public static int nglXQueryMaxSwapGroupsNV(long p0, int p1, long p2, long p3) {
        int returnType = GLXNVSwapGroup.nglXQueryMaxSwapGroupsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXQueryMaxSwapGroupsNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static boolean glXQueryMaxSwapGroupsNV(long p0, int p1, IntBuffer p2, IntBuffer p3) {
        boolean returnType = GLXNVSwapGroup.glXQueryMaxSwapGroupsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQueryMaxSwapGroupsNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
        return returnType;
    }

    public static int nglXQueryFrameCountNV(long p0, int p1, long p2) {
        int returnType = GLXNVSwapGroup.nglXQueryFrameCountNV(p0, p1, p2);
        LabyDebugContext.glError("nglXQueryFrameCountNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean glXQueryFrameCountNV(long p0, int p1, IntBuffer p2) {
        boolean returnType = GLXNVSwapGroup.glXQueryFrameCountNV(p0, p1, p2);
        LabyDebugContext.glError("glXQueryFrameCountNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static boolean glXResetFrameCountNV(long p0, int p1) {
        boolean returnType = GLXNVSwapGroup.glXResetFrameCountNV(p0, p1);
        LabyDebugContext.glError("glXResetFrameCountNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static boolean glXQuerySwapGroupNV(long p0, long p1, int[] p2, int[] p3) {
        boolean returnType = GLXNVSwapGroup.glXQuerySwapGroupNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQuerySwapGroupNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2, "p3", p3);
        return returnType;
    }

    public static boolean glXQueryMaxSwapGroupsNV(long p0, int p1, int[] p2, int[] p3) {
        boolean returnType = GLXNVSwapGroup.glXQueryMaxSwapGroupsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQueryMaxSwapGroupsNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
        return returnType;
    }

    public static boolean glXQueryFrameCountNV(long p0, int p1, int[] p2) {
        boolean returnType = GLXNVSwapGroup.glXQueryFrameCountNV(p0, p1, p2);
        LabyDebugContext.glError("glXQueryFrameCountNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }
}

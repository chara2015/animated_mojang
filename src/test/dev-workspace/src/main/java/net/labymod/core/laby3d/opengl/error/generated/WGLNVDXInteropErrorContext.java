package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.WGLNVDXInterop;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLNVDXInteropErrorContext.class */
public final class WGLNVDXInteropErrorContext {
    public static boolean wglDXSetResourceShareHandleNV(long p0, long p1) {
        boolean returnType = WGLNVDXInterop.wglDXSetResourceShareHandleNV(p0, p1);
        LabyDebugContext.glError("wglDXSetResourceShareHandleNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static long wglDXOpenDeviceNV(long p0) {
        long returnType = WGLNVDXInterop.wglDXOpenDeviceNV(p0);
        LabyDebugContext.glError("wglDXOpenDeviceNV", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static boolean wglDXCloseDeviceNV(long p0) {
        boolean returnType = WGLNVDXInterop.wglDXCloseDeviceNV(p0);
        LabyDebugContext.glError("wglDXCloseDeviceNV", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long wglDXRegisterObjectNV(long p0, long p1, int p2, int p3, int p4) {
        long returnType = WGLNVDXInterop.wglDXRegisterObjectNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("wglDXRegisterObjectNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
        return returnType;
    }

    public static boolean wglDXUnregisterObjectNV(long p0, long p1) {
        boolean returnType = WGLNVDXInterop.wglDXUnregisterObjectNV(p0, p1);
        LabyDebugContext.glError("wglDXUnregisterObjectNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static boolean wglDXObjectAccessNV(long p0, int p1) {
        boolean returnType = WGLNVDXInterop.wglDXObjectAccessNV(p0, p1);
        LabyDebugContext.glError("wglDXObjectAccessNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static int nwglDXLockObjectsNV(long p0, int p1, long p2) {
        int returnType = WGLNVDXInterop.nwglDXLockObjectsNV(p0, p1, p2);
        LabyDebugContext.glError("nwglDXLockObjectsNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean wglDXLockObjectsNV(long p0, PointerBuffer p1) {
        boolean returnType = WGLNVDXInterop.wglDXLockObjectsNV(p0, p1);
        LabyDebugContext.glError("wglDXLockObjectsNV", "p0", Long.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int nwglDXUnlockObjectsNV(long p0, int p1, long p2) {
        int returnType = WGLNVDXInterop.nwglDXUnlockObjectsNV(p0, p1, p2);
        LabyDebugContext.glError("nwglDXUnlockObjectsNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean wglDXUnlockObjectsNV(long p0, PointerBuffer p1) {
        boolean returnType = WGLNVDXInterop.wglDXUnlockObjectsNV(p0, p1);
        LabyDebugContext.glError("wglDXUnlockObjectsNV", "p0", Long.valueOf(p0), "p1", p1);
        return returnType;
    }
}

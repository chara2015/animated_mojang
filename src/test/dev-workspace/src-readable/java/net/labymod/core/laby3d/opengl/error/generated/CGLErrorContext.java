package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.CGL;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/CGLErrorContext.class */
public final class CGLErrorContext {
    public static long CGLGetCurrentContext() {
        long returnType = CGL.CGLGetCurrentContext();
        LabyDebugContext.glError("CGLGetCurrentContext", new Object[0]);
        return returnType;
    }

    public static int CGLSetCurrentContext(long p0) {
        int returnType = CGL.CGLSetCurrentContext(p0);
        LabyDebugContext.glError("CGLSetCurrentContext", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long CGLGetShareGroup(long p0) {
        long returnType = CGL.CGLGetShareGroup(p0);
        LabyDebugContext.glError("CGLGetShareGroup", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nCGLChoosePixelFormat(long p0, long p1, long p2) {
        int returnType = CGL.nCGLChoosePixelFormat(p0, p1, p2);
        LabyDebugContext.glError("nCGLChoosePixelFormat", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int CGLChoosePixelFormat(IntBuffer p0, PointerBuffer p1, IntBuffer p2) {
        int returnType = CGL.CGLChoosePixelFormat(p0, p1, p2);
        LabyDebugContext.glError("CGLChoosePixelFormat", "p0", p0, "p1", p1, "p2", p2);
        return returnType;
    }

    public static int CGLDestroyPixelFormat(long p0) {
        int returnType = CGL.CGLDestroyPixelFormat(p0);
        LabyDebugContext.glError("CGLDestroyPixelFormat", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nCGLDescribePixelFormat(long p0, int p1, int p2, long p3) {
        int returnType = CGL.nCGLDescribePixelFormat(p0, p1, p2, p3);
        LabyDebugContext.glError("nCGLDescribePixelFormat", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static int CGLDescribePixelFormat(long p0, int p1, int p2, IntBuffer p3) {
        int returnType = CGL.CGLDescribePixelFormat(p0, p1, p2, p3);
        LabyDebugContext.glError("CGLDescribePixelFormat", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static void CGLReleasePixelFormat(long p0) {
        CGL.CGLReleasePixelFormat(p0);
        LabyDebugContext.glError("CGLReleasePixelFormat", "p0", Long.valueOf(p0));
    }

    public static long CGLRetainPixelFormat(long p0) {
        long returnType = CGL.CGLRetainPixelFormat(p0);
        LabyDebugContext.glError("CGLRetainPixelFormat", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int CGLGetPixelFormatRetainCount(long p0) {
        int returnType = CGL.CGLGetPixelFormatRetainCount(p0);
        LabyDebugContext.glError("CGLGetPixelFormatRetainCount", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nCGLQueryRendererInfo(int p0, long p1, long p2) {
        int returnType = CGL.nCGLQueryRendererInfo(p0, p1, p2);
        LabyDebugContext.glError("nCGLQueryRendererInfo", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int CGLQueryRendererInfo(int p0, PointerBuffer p1, IntBuffer p2) {
        int returnType = CGL.CGLQueryRendererInfo(p0, p1, p2);
        LabyDebugContext.glError("CGLQueryRendererInfo", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
        return returnType;
    }

    public static int CGLDestroyRendererInfo(long p0) {
        int returnType = CGL.CGLDestroyRendererInfo(p0);
        LabyDebugContext.glError("CGLDestroyRendererInfo", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nCGLDescribeRenderer(long p0, int p1, int p2, long p3) {
        int returnType = CGL.nCGLDescribeRenderer(p0, p1, p2, p3);
        LabyDebugContext.glError("nCGLDescribeRenderer", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static int CGLDescribeRenderer(long p0, int p1, int p2, IntBuffer p3) {
        int returnType = CGL.CGLDescribeRenderer(p0, p1, p2, p3);
        LabyDebugContext.glError("CGLDescribeRenderer", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static int nCGLCreateContext(long p0, long p1, long p2) {
        int returnType = CGL.nCGLCreateContext(p0, p1, p2);
        LabyDebugContext.glError("nCGLCreateContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int CGLCreateContext(long p0, long p1, PointerBuffer p2) {
        int returnType = CGL.CGLCreateContext(p0, p1, p2);
        LabyDebugContext.glError("CGLCreateContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int CGLDestroyContext(long p0) {
        int returnType = CGL.CGLDestroyContext(p0);
        LabyDebugContext.glError("CGLDestroyContext", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int CGLCopyContext(long p0, long p1, int p2) {
        int returnType = CGL.CGLCopyContext(p0, p1, p2);
        LabyDebugContext.glError("CGLCopyContext", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static long CGLRetainContext(long p0) {
        long returnType = CGL.CGLRetainContext(p0);
        LabyDebugContext.glError("CGLRetainContext", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static void CGLReleaseContext(long p0) {
        CGL.CGLReleaseContext(p0);
        LabyDebugContext.glError("CGLReleaseContext", "p0", Long.valueOf(p0));
    }

    public static int CGLGetContextRetainCount(long p0) {
        int returnType = CGL.CGLGetContextRetainCount(p0);
        LabyDebugContext.glError("CGLGetContextRetainCount", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long CGLGetPixelFormat(long p0) {
        long returnType = CGL.CGLGetPixelFormat(p0);
        LabyDebugContext.glError("CGLGetPixelFormat", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nCGLCreatePBuffer(int p0, int p1, int p2, int p3, int p4, long p5) {
        int returnType = CGL.nCGLCreatePBuffer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nCGLCreatePBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
        return returnType;
    }

    public static int CGLCreatePBuffer(int p0, int p1, int p2, int p3, int p4, PointerBuffer p5) {
        int returnType = CGL.CGLCreatePBuffer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("CGLCreatePBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
        return returnType;
    }

    public static int CGLDestroyPBuffer(long p0) {
        int returnType = CGL.CGLDestroyPBuffer(p0);
        LabyDebugContext.glError("CGLDestroyPBuffer", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nCGLDescribePBuffer(long p0, long p1, long p2, long p3, long p4, long p5) {
        int returnType = CGL.nCGLDescribePBuffer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nCGLDescribePBuffer", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
        return returnType;
    }

    public static int CGLDescribePBuffer(long p0, IntBuffer p1, IntBuffer p2, IntBuffer p3, IntBuffer p4, IntBuffer p5) {
        int returnType = CGL.CGLDescribePBuffer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("CGLDescribePBuffer", "p0", Long.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4, "p5", p5);
        return returnType;
    }

    public static int CGLTexImagePBuffer(long p0, long p1, int p2) {
        int returnType = CGL.CGLTexImagePBuffer(p0, p1, p2);
        LabyDebugContext.glError("CGLTexImagePBuffer", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static long CGLRetainPBuffer(long p0) {
        long returnType = CGL.CGLRetainPBuffer(p0);
        LabyDebugContext.glError("CGLRetainPBuffer", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static void CGLReleasePBuffer(long p0) {
        CGL.CGLReleasePBuffer(p0);
        LabyDebugContext.glError("CGLReleasePBuffer", "p0", Long.valueOf(p0));
    }

    public static int CGLGetPBufferRetainCount(long p0) {
        int returnType = CGL.CGLGetPBufferRetainCount(p0);
        LabyDebugContext.glError("CGLGetPBufferRetainCount", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nCGLSetOffScreen(long p0, int p1, int p2, int p3, long p4) {
        int returnType = CGL.nCGLSetOffScreen(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nCGLSetOffScreen", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
        return returnType;
    }

    public static int CGLSetOffScreen(long p0, int p1, int p2, int p3, ByteBuffer p4) {
        int returnType = CGL.CGLSetOffScreen(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("CGLSetOffScreen", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
        return returnType;
    }

    public static int nCGLGetOffScreen(long p0, long p1, long p2, long p3, long p4) {
        int returnType = CGL.nCGLGetOffScreen(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nCGLGetOffScreen", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
        return returnType;
    }

    public static int CGLGetOffScreen(long p0, IntBuffer p1, IntBuffer p2, IntBuffer p3, PointerBuffer p4) {
        int returnType = CGL.CGLGetOffScreen(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("CGLGetOffScreen", "p0", Long.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4);
        return returnType;
    }

    public static int CGLSetFullScreen(long p0) {
        int returnType = CGL.CGLSetFullScreen(p0);
        LabyDebugContext.glError("CGLSetFullScreen", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int CGLSetFullScreenOnDisplay(long p0, int p1) {
        int returnType = CGL.CGLSetFullScreenOnDisplay(p0, p1);
        LabyDebugContext.glError("CGLSetFullScreenOnDisplay", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static int CGLSetPBuffer(long p0, long p1, int p2, int p3, int p4) {
        int returnType = CGL.CGLSetPBuffer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("CGLSetPBuffer", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
        return returnType;
    }

    public static int nCGLGetPBuffer(long p0, long p1, long p2, long p3, long p4) {
        int returnType = CGL.nCGLGetPBuffer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nCGLGetPBuffer", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
        return returnType;
    }

    public static int CGLGetPBuffer(long p0, PointerBuffer p1, IntBuffer p2, IntBuffer p3, IntBuffer p4) {
        int returnType = CGL.CGLGetPBuffer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("CGLGetPBuffer", "p0", Long.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4);
        return returnType;
    }

    public static int CGLClearDrawable(long p0) {
        int returnType = CGL.CGLClearDrawable(p0);
        LabyDebugContext.glError("CGLClearDrawable", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int CGLFlushDrawable(long p0) {
        int returnType = CGL.CGLFlushDrawable(p0);
        LabyDebugContext.glError("CGLFlushDrawable", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int CGLEnable(long p0, int p1) {
        int returnType = CGL.CGLEnable(p0, p1);
        LabyDebugContext.glError("CGLEnable", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static int CGLDisable(long p0, int p1) {
        int returnType = CGL.CGLDisable(p0, p1);
        LabyDebugContext.glError("CGLDisable", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static int nCGLIsEnabled(long p0, int p1, long p2) {
        int returnType = CGL.nCGLIsEnabled(p0, p1, p2);
        LabyDebugContext.glError("nCGLIsEnabled", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int CGLIsEnabled(long p0, int p1, IntBuffer p2) {
        int returnType = CGL.CGLIsEnabled(p0, p1, p2);
        LabyDebugContext.glError("CGLIsEnabled", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int nCGLSetParameter(long p0, int p1, long p2) {
        int returnType = CGL.nCGLSetParameter(p0, p1, p2);
        LabyDebugContext.glError("nCGLSetParameter", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int CGLSetParameter(long p0, int p1, IntBuffer p2) {
        int returnType = CGL.CGLSetParameter(p0, p1, p2);
        LabyDebugContext.glError("CGLSetParameter", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int CGLSetParameter(long p0, int p1, int p2) {
        int returnType = CGL.CGLSetParameter(p0, p1, p2);
        LabyDebugContext.glError("CGLSetParameter", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static int nCGLGetParameter(long p0, int p1, long p2) {
        int returnType = CGL.nCGLGetParameter(p0, p1, p2);
        LabyDebugContext.glError("nCGLGetParameter", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int CGLGetParameter(long p0, int p1, IntBuffer p2) {
        int returnType = CGL.CGLGetParameter(p0, p1, p2);
        LabyDebugContext.glError("CGLGetParameter", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int CGLSetVirtualScreen(long p0, int p1) {
        int returnType = CGL.CGLSetVirtualScreen(p0, p1);
        LabyDebugContext.glError("CGLSetVirtualScreen", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static int nCGLGetVirtualScreen(long p0, long p1) {
        int returnType = CGL.nCGLGetVirtualScreen(p0, p1);
        LabyDebugContext.glError("nCGLGetVirtualScreen", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int CGLGetVirtualScreen(long p0, IntBuffer p1) {
        int returnType = CGL.CGLGetVirtualScreen(p0, p1);
        LabyDebugContext.glError("CGLGetVirtualScreen", "p0", Long.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int CGLUpdateContext(long p0) {
        int returnType = CGL.CGLUpdateContext(p0);
        LabyDebugContext.glError("CGLUpdateContext", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nCGLSetGlobalOption(int p0, long p1) {
        int returnType = CGL.nCGLSetGlobalOption(p0, p1);
        LabyDebugContext.glError("nCGLSetGlobalOption", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int CGLSetGlobalOption(int p0, IntBuffer p1) {
        int returnType = CGL.CGLSetGlobalOption(p0, p1);
        LabyDebugContext.glError("CGLSetGlobalOption", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int CGLSetGlobalOption(int p0, int p1) {
        int returnType = CGL.CGLSetGlobalOption(p0, p1);
        LabyDebugContext.glError("CGLSetGlobalOption", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static int nCGLGetGlobalOption(int p0, long p1) {
        int returnType = CGL.nCGLGetGlobalOption(p0, p1);
        LabyDebugContext.glError("nCGLGetGlobalOption", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int CGLGetGlobalOption(int p0, IntBuffer p1) {
        int returnType = CGL.CGLGetGlobalOption(p0, p1);
        LabyDebugContext.glError("CGLGetGlobalOption", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int CGLLockContext(long p0) {
        int returnType = CGL.CGLLockContext(p0);
        LabyDebugContext.glError("CGLLockContext", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int CGLUnlockContext(long p0) {
        int returnType = CGL.CGLUnlockContext(p0);
        LabyDebugContext.glError("CGLUnlockContext", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static void nCGLGetVersion(long p0, long p1) {
        CGL.nCGLGetVersion(p0, p1);
        LabyDebugContext.glError("nCGLGetVersion", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void CGLGetVersion(IntBuffer p0, IntBuffer p1) {
        CGL.CGLGetVersion(p0, p1);
        LabyDebugContext.glError("CGLGetVersion", "p0", p0, "p1", p1);
    }

    public static long nCGLErrorString(int p0) {
        long returnType = CGL.nCGLErrorString(p0);
        LabyDebugContext.glError("nCGLErrorString", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static String CGLErrorString(int p0) {
        String returnType = CGL.CGLErrorString(p0);
        LabyDebugContext.glError("CGLErrorString", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static int CGLChoosePixelFormat(int[] p0, PointerBuffer p1, int[] p2) {
        int returnType = CGL.CGLChoosePixelFormat(p0, p1, p2);
        LabyDebugContext.glError("CGLChoosePixelFormat", "p0", p0, "p1", p1, "p2", p2);
        return returnType;
    }

    public static int CGLDescribePixelFormat(long p0, int p1, int p2, int[] p3) {
        int returnType = CGL.CGLDescribePixelFormat(p0, p1, p2, p3);
        LabyDebugContext.glError("CGLDescribePixelFormat", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static int CGLQueryRendererInfo(int p0, PointerBuffer p1, int[] p2) {
        int returnType = CGL.CGLQueryRendererInfo(p0, p1, p2);
        LabyDebugContext.glError("CGLQueryRendererInfo", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
        return returnType;
    }

    public static int CGLDescribeRenderer(long p0, int p1, int p2, int[] p3) {
        int returnType = CGL.CGLDescribeRenderer(p0, p1, p2, p3);
        LabyDebugContext.glError("CGLDescribeRenderer", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static int CGLDescribePBuffer(long p0, int[] p1, int[] p2, int[] p3, int[] p4, int[] p5) {
        int returnType = CGL.CGLDescribePBuffer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("CGLDescribePBuffer", "p0", Long.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4, "p5", p5);
        return returnType;
    }

    public static int CGLGetOffScreen(long p0, int[] p1, int[] p2, int[] p3, PointerBuffer p4) {
        int returnType = CGL.CGLGetOffScreen(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("CGLGetOffScreen", "p0", Long.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4);
        return returnType;
    }

    public static int CGLGetPBuffer(long p0, PointerBuffer p1, int[] p2, int[] p3, int[] p4) {
        int returnType = CGL.CGLGetPBuffer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("CGLGetPBuffer", "p0", Long.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4);
        return returnType;
    }

    public static int CGLIsEnabled(long p0, int p1, int[] p2) {
        int returnType = CGL.CGLIsEnabled(p0, p1, p2);
        LabyDebugContext.glError("CGLIsEnabled", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int CGLSetParameter(long p0, int p1, int[] p2) {
        int returnType = CGL.CGLSetParameter(p0, p1, p2);
        LabyDebugContext.glError("CGLSetParameter", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int CGLGetParameter(long p0, int p1, int[] p2) {
        int returnType = CGL.CGLGetParameter(p0, p1, p2);
        LabyDebugContext.glError("CGLGetParameter", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int CGLGetVirtualScreen(long p0, int[] p1) {
        int returnType = CGL.CGLGetVirtualScreen(p0, p1);
        LabyDebugContext.glError("CGLGetVirtualScreen", "p0", Long.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int CGLSetGlobalOption(int p0, int[] p1) {
        int returnType = CGL.CGLSetGlobalOption(p0, p1);
        LabyDebugContext.glError("CGLSetGlobalOption", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int CGLGetGlobalOption(int p0, int[] p1) {
        int returnType = CGL.CGLGetGlobalOption(p0, p1);
        LabyDebugContext.glError("CGLGetGlobalOption", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void CGLGetVersion(int[] p0, int[] p1) {
        CGL.CGLGetVersion(p0, p1);
        LabyDebugContext.glError("CGLGetVersion", "p0", p0, "p1", p1);
    }
}

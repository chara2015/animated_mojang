package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.WGLNVCopyImage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/WGLNVCopyImageErrorContext.class */
public final class WGLNVCopyImageErrorContext {
    public static boolean wglCopyImageSubDataNV(long p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7, int p8, int p9, int p10, int p11, int p12, int p13, int p14, int p15, int p16) {
        boolean returnType = WGLNVCopyImage.wglCopyImageSubDataNV(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16);
        LabyDebugContext.glError("wglCopyImageSubDataNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Integer.valueOf(p9), "p10", Integer.valueOf(p10), "p11", Integer.valueOf(p11), "p12", Integer.valueOf(p12), "p13", Integer.valueOf(p13), "p14", Integer.valueOf(p14), "p15", Integer.valueOf(p15), "p16", Integer.valueOf(p16));
        return returnType;
    }
}

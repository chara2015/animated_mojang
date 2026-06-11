package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVShadingRateImage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVShadingRateImageErrorContext.class */
public final class NVShadingRateImageErrorContext {
    public static void glBindShadingRateImageNV(int p0) {
        NVShadingRateImage.glBindShadingRateImageNV(p0);
        LabyDebugContext.glError("glBindShadingRateImageNV", "p0", Integer.valueOf(p0));
    }

    public static void nglShadingRateImagePaletteNV(int p0, int p1, int p2, long p3) {
        NVShadingRateImage.nglShadingRateImagePaletteNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglShadingRateImagePaletteNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glShadingRateImagePaletteNV(int p0, int p1, IntBuffer p2) {
        NVShadingRateImage.glShadingRateImagePaletteNV(p0, p1, p2);
        LabyDebugContext.glError("glShadingRateImagePaletteNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetShadingRateImagePaletteNV(int p0, int p1, long p2) {
        NVShadingRateImage.nglGetShadingRateImagePaletteNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetShadingRateImagePaletteNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetShadingRateImagePaletteNV(int p0, int p1, IntBuffer p2) {
        NVShadingRateImage.glGetShadingRateImagePaletteNV(p0, p1, p2);
        LabyDebugContext.glError("glGetShadingRateImagePaletteNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glShadingRateImageBarrierNV(boolean p0) {
        NVShadingRateImage.glShadingRateImageBarrierNV(p0);
        LabyDebugContext.glError("glShadingRateImageBarrierNV", "p0", Boolean.valueOf(p0));
    }

    public static void glShadingRateSampleOrderNV(int p0) {
        NVShadingRateImage.glShadingRateSampleOrderNV(p0);
        LabyDebugContext.glError("glShadingRateSampleOrderNV", "p0", Integer.valueOf(p0));
    }

    public static void nglShadingRateSampleOrderCustomNV(int p0, int p1, long p2) {
        NVShadingRateImage.nglShadingRateSampleOrderCustomNV(p0, p1, p2);
        LabyDebugContext.glError("nglShadingRateSampleOrderCustomNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glShadingRateSampleOrderCustomNV(int p0, int p1, IntBuffer p2) {
        NVShadingRateImage.glShadingRateSampleOrderCustomNV(p0, p1, p2);
        LabyDebugContext.glError("glShadingRateSampleOrderCustomNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetShadingRateSampleLocationivNV(int p0, int p1, int p2, long p3) {
        NVShadingRateImage.nglGetShadingRateSampleLocationivNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetShadingRateSampleLocationivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetShadingRateSampleLocationivNV(int p0, int p1, int p2, IntBuffer p3) {
        NVShadingRateImage.glGetShadingRateSampleLocationivNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetShadingRateSampleLocationivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glShadingRateImagePaletteNV(int p0, int p1, int[] p2) {
        NVShadingRateImage.glShadingRateImagePaletteNV(p0, p1, p2);
        LabyDebugContext.glError("glShadingRateImagePaletteNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetShadingRateImagePaletteNV(int p0, int p1, int[] p2) {
        NVShadingRateImage.glGetShadingRateImagePaletteNV(p0, p1, p2);
        LabyDebugContext.glError("glGetShadingRateImagePaletteNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glShadingRateSampleOrderCustomNV(int p0, int p1, int[] p2) {
        NVShadingRateImage.glShadingRateSampleOrderCustomNV(p0, p1, p2);
        LabyDebugContext.glError("glShadingRateSampleOrderCustomNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetShadingRateSampleLocationivNV(int p0, int p1, int p2, int[] p3) {
        NVShadingRateImage.glGetShadingRateSampleLocationivNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetShadingRateSampleLocationivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }
}

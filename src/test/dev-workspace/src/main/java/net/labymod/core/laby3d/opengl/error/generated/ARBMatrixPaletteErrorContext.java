package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBMatrixPalette;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBMatrixPaletteErrorContext.class */
public final class ARBMatrixPaletteErrorContext {
    public static void glCurrentPaletteMatrixARB(int p0) {
        ARBMatrixPalette.glCurrentPaletteMatrixARB(p0);
        LabyDebugContext.glError("glCurrentPaletteMatrixARB", "p0", Integer.valueOf(p0));
    }

    public static void nglMatrixIndexuivARB(int p0, long p1) {
        ARBMatrixPalette.nglMatrixIndexuivARB(p0, p1);
        LabyDebugContext.glError("nglMatrixIndexuivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMatrixIndexuivARB(IntBuffer p0) {
        ARBMatrixPalette.glMatrixIndexuivARB(p0);
        LabyDebugContext.glError("glMatrixIndexuivARB", "p0", p0);
    }

    public static void nglMatrixIndexubvARB(int p0, long p1) {
        ARBMatrixPalette.nglMatrixIndexubvARB(p0, p1);
        LabyDebugContext.glError("nglMatrixIndexubvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMatrixIndexubvARB(ByteBuffer p0) {
        ARBMatrixPalette.glMatrixIndexubvARB(p0);
        LabyDebugContext.glError("glMatrixIndexubvARB", "p0", p0);
    }

    public static void nglMatrixIndexusvARB(int p0, long p1) {
        ARBMatrixPalette.nglMatrixIndexusvARB(p0, p1);
        LabyDebugContext.glError("nglMatrixIndexusvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMatrixIndexusvARB(ShortBuffer p0) {
        ARBMatrixPalette.glMatrixIndexusvARB(p0);
        LabyDebugContext.glError("glMatrixIndexusvARB", "p0", p0);
    }

    public static void nglMatrixIndexPointerARB(int p0, int p1, int p2, long p3) {
        ARBMatrixPalette.nglMatrixIndexPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglMatrixIndexPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glMatrixIndexPointerARB(int p0, int p1, int p2, ByteBuffer p3) {
        ARBMatrixPalette.glMatrixIndexPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glMatrixIndexPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glMatrixIndexPointerARB(int p0, int p1, int p2, long p3) {
        ARBMatrixPalette.glMatrixIndexPointerARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glMatrixIndexPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glMatrixIndexPointerARB(int p0, int p1, ByteBuffer p2) {
        ARBMatrixPalette.glMatrixIndexPointerARB(p0, p1, p2);
        LabyDebugContext.glError("glMatrixIndexPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMatrixIndexPointerARB(int p0, int p1, ShortBuffer p2) {
        ARBMatrixPalette.glMatrixIndexPointerARB(p0, p1, p2);
        LabyDebugContext.glError("glMatrixIndexPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMatrixIndexPointerARB(int p0, int p1, IntBuffer p2) {
        ARBMatrixPalette.glMatrixIndexPointerARB(p0, p1, p2);
        LabyDebugContext.glError("glMatrixIndexPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMatrixIndexuivARB(int[] p0) {
        ARBMatrixPalette.glMatrixIndexuivARB(p0);
        LabyDebugContext.glError("glMatrixIndexuivARB", "p0", p0);
    }

    public static void glMatrixIndexusvARB(short[] p0) {
        ARBMatrixPalette.glMatrixIndexusvARB(p0);
        LabyDebugContext.glError("glMatrixIndexusvARB", "p0", p0);
    }
}

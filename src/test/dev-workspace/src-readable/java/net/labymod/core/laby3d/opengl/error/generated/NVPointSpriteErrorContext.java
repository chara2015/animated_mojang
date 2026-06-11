package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVPointSprite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVPointSpriteErrorContext.class */
public final class NVPointSpriteErrorContext {
    public static void glPointParameteriNV(int p0, int p1) {
        NVPointSprite.glPointParameteriNV(p0, p1);
        LabyDebugContext.glError("glPointParameteriNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglPointParameterivNV(int p0, long p1) {
        NVPointSprite.nglPointParameterivNV(p0, p1);
        LabyDebugContext.glError("nglPointParameterivNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glPointParameterivNV(int p0, IntBuffer p1) {
        NVPointSprite.glPointParameterivNV(p0, p1);
        LabyDebugContext.glError("glPointParameterivNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glPointParameterivNV(int p0, int[] p1) {
        NVPointSprite.glPointParameterivNV(p0, p1);
        LabyDebugContext.glError("glPointParameterivNV", "p0", Integer.valueOf(p0), "p1", p1);
    }
}

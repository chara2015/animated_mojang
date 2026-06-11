package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.INTELMapTexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/INTELMapTextureErrorContext.class */
public final class INTELMapTextureErrorContext {
    public static void glSyncTextureINTEL(int p0) {
        INTELMapTexture.glSyncTextureINTEL(p0);
        LabyDebugContext.glError("glSyncTextureINTEL", "p0", Integer.valueOf(p0));
    }

    public static void glUnmapTexture2DINTEL(int p0, int p1) {
        INTELMapTexture.glUnmapTexture2DINTEL(p0, p1);
        LabyDebugContext.glError("glUnmapTexture2DINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static long nglMapTexture2DINTEL(int p0, int p1, int p2, long p3, long p4) {
        long returnType = INTELMapTexture.nglMapTexture2DINTEL(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglMapTexture2DINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
        return returnType;
    }

    public static ByteBuffer glMapTexture2DINTEL(int p0, int p1, int p2, IntBuffer p3, IntBuffer p4) {
        ByteBuffer returnType = INTELMapTexture.glMapTexture2DINTEL(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMapTexture2DINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
        return returnType;
    }

    public static ByteBuffer glMapTexture2DINTEL(int p0, int p1, int p2, IntBuffer p3, IntBuffer p4, ByteBuffer p5) {
        ByteBuffer returnType = INTELMapTexture.glMapTexture2DINTEL(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMapTexture2DINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4, "p5", p5);
        return returnType;
    }

    public static ByteBuffer glMapTexture2DINTEL(int p0, int p1, int p2, IntBuffer p3, IntBuffer p4, long p5, ByteBuffer p6) {
        ByteBuffer returnType = INTELMapTexture.glMapTexture2DINTEL(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glMapTexture2DINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4, "p5", Long.valueOf(p5), "p6", p6);
        return returnType;
    }

    public static ByteBuffer glMapTexture2DINTEL(int p0, int p1, int p2, int[] p3, int[] p4) {
        ByteBuffer returnType = INTELMapTexture.glMapTexture2DINTEL(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMapTexture2DINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
        return returnType;
    }

    public static ByteBuffer glMapTexture2DINTEL(int p0, int p1, int p2, int[] p3, int[] p4, ByteBuffer p5) {
        ByteBuffer returnType = INTELMapTexture.glMapTexture2DINTEL(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMapTexture2DINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4, "p5", p5);
        return returnType;
    }

    public static ByteBuffer glMapTexture2DINTEL(int p0, int p1, int p2, int[] p3, int[] p4, long p5, ByteBuffer p6) {
        ByteBuffer returnType = INTELMapTexture.glMapTexture2DINTEL(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glMapTexture2DINTEL", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4, "p5", Long.valueOf(p5), "p6", p6);
        return returnType;
    }
}

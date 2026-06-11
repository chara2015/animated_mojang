package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVDrawTexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVDrawTextureErrorContext.class */
public final class NVDrawTextureErrorContext {
    public static void glDrawTextureNV(int p0, int p1, float p2, float p3, float p4, float p5, float p6, float p7, float p8, float p9, float p10) {
        NVDrawTexture.glDrawTextureNV(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glDrawTextureNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5), "p6", Float.valueOf(p6), "p7", Float.valueOf(p7), "p8", Float.valueOf(p8), "p9", Float.valueOf(p9), "p10", Float.valueOf(p10));
    }
}

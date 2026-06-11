package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBTextureViewErrorContext.class */
public final class ARBTextureViewErrorContext {
    public static void glTextureView(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
        ARBTextureView.glTextureView(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTextureView", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7));
    }
}

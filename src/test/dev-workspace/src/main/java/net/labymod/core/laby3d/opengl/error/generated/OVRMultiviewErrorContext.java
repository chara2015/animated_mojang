package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.OVRMultiview;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/OVRMultiviewErrorContext.class */
public final class OVRMultiviewErrorContext {
    public static void glFramebufferTextureMultiviewOVR(int p0, int p1, int p2, int p3, int p4, int p5) {
        OVRMultiview.glFramebufferTextureMultiviewOVR(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glFramebufferTextureMultiviewOVR", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glNamedFramebufferTextureMultiviewOVR(int p0, int p1, int p2, int p3, int p4, int p5) {
        OVRMultiview.glNamedFramebufferTextureMultiviewOVR(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glNamedFramebufferTextureMultiviewOVR", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }
}

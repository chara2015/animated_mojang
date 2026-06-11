package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBTransformFeedbackInstanced;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBTransformFeedbackInstancedErrorContext.class */
public final class ARBTransformFeedbackInstancedErrorContext {
    public static void glDrawTransformFeedbackInstanced(int p0, int p1, int p2) {
        ARBTransformFeedbackInstanced.glDrawTransformFeedbackInstanced(p0, p1, p2);
        LabyDebugContext.glError("glDrawTransformFeedbackInstanced", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glDrawTransformFeedbackStreamInstanced(int p0, int p1, int p2, int p3) {
        ARBTransformFeedbackInstanced.glDrawTransformFeedbackStreamInstanced(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawTransformFeedbackStreamInstanced", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }
}

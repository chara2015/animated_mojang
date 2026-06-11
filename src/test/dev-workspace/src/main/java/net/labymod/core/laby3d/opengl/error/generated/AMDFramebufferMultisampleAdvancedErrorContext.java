package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.AMDFramebufferMultisampleAdvanced;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/AMDFramebufferMultisampleAdvancedErrorContext.class */
public final class AMDFramebufferMultisampleAdvancedErrorContext {
    public static void glRenderbufferStorageMultisampleAdvancedAMD(int p0, int p1, int p2, int p3, int p4, int p5) {
        AMDFramebufferMultisampleAdvanced.glRenderbufferStorageMultisampleAdvancedAMD(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glRenderbufferStorageMultisampleAdvancedAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glNamedRenderbufferStorageMultisampleAdvancedAMD(int p0, int p1, int p2, int p3, int p4, int p5) {
        AMDFramebufferMultisampleAdvanced.glNamedRenderbufferStorageMultisampleAdvancedAMD(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glNamedRenderbufferStorageMultisampleAdvancedAMD", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }
}

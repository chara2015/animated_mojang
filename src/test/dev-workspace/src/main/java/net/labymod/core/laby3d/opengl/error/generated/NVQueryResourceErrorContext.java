package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVQueryResource;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVQueryResourceErrorContext.class */
public final class NVQueryResourceErrorContext {
    public static int nglQueryResourceNV(int p0, int p1, int p2, long p3) {
        int returnType = NVQueryResource.nglQueryResourceNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglQueryResourceNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static int glQueryResourceNV(int p0, int p1, IntBuffer p2) {
        int returnType = NVQueryResource.glQueryResourceNV(p0, p1, p2);
        LabyDebugContext.glError("glQueryResourceNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int glQueryResourceNV(int p0, int p1, int[] p2) {
        int returnType = NVQueryResource.glQueryResourceNV(p0, p1, p2);
        LabyDebugContext.glError("glQueryResourceNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }
}

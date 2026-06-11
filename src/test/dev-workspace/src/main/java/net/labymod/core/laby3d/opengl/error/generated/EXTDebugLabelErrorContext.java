package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTDebugLabel;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTDebugLabelErrorContext.class */
public final class EXTDebugLabelErrorContext {
    public static void nglLabelObjectEXT(int p0, int p1, int p2, long p3) {
        EXTDebugLabel.nglLabelObjectEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("nglLabelObjectEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glLabelObjectEXT(int p0, int p1, ByteBuffer p2) {
        EXTDebugLabel.glLabelObjectEXT(p0, p1, p2);
        LabyDebugContext.glError("glLabelObjectEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glLabelObjectEXT(int p0, int p1, CharSequence p2) {
        EXTDebugLabel.glLabelObjectEXT(p0, p1, p2);
        LabyDebugContext.glError("glLabelObjectEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetObjectLabelEXT(int p0, int p1, int p2, long p3, long p4) {
        EXTDebugLabel.nglGetObjectLabelEXT(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetObjectLabelEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetObjectLabelEXT(int p0, int p1, IntBuffer p2, ByteBuffer p3) {
        EXTDebugLabel.glGetObjectLabelEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetObjectLabelEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static String glGetObjectLabelEXT(int p0, int p1, int p2) {
        String returnType = EXTDebugLabel.glGetObjectLabelEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetObjectLabelEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void glGetObjectLabelEXT(int p0, int p1, int[] p2, ByteBuffer p3) {
        EXTDebugLabel.glGetObjectLabelEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetObjectLabelEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }
}

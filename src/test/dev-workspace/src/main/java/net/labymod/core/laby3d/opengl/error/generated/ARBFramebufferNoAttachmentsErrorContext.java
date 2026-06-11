package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBFramebufferNoAttachments;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBFramebufferNoAttachmentsErrorContext.class */
public final class ARBFramebufferNoAttachmentsErrorContext {
    public static void glFramebufferParameteri(int p0, int p1, int p2) {
        ARBFramebufferNoAttachments.glFramebufferParameteri(p0, p1, p2);
        LabyDebugContext.glError("glFramebufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglGetFramebufferParameteriv(int p0, int p1, long p2) {
        ARBFramebufferNoAttachments.nglGetFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetFramebufferParameteriv(int p0, int p1, IntBuffer p2) {
        ARBFramebufferNoAttachments.glGetFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetFramebufferParameteri(int p0, int p1) {
        int returnType = ARBFramebufferNoAttachments.glGetFramebufferParameteri(p0, p1);
        LabyDebugContext.glError("glGetFramebufferParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glNamedFramebufferParameteriEXT(int p0, int p1, int p2) {
        ARBFramebufferNoAttachments.glNamedFramebufferParameteriEXT(p0, p1, p2);
        LabyDebugContext.glError("glNamedFramebufferParameteriEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglGetNamedFramebufferParameterivEXT(int p0, int p1, long p2) {
        ARBFramebufferNoAttachments.nglGetNamedFramebufferParameterivEXT(p0, p1, p2);
        LabyDebugContext.glError("nglGetNamedFramebufferParameterivEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetNamedFramebufferParameterivEXT(int p0, int p1, IntBuffer p2) {
        ARBFramebufferNoAttachments.glGetNamedFramebufferParameterivEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedFramebufferParameterivEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetNamedFramebufferParameteriEXT(int p0, int p1) {
        int returnType = ARBFramebufferNoAttachments.glGetNamedFramebufferParameteriEXT(p0, p1);
        LabyDebugContext.glError("glGetNamedFramebufferParameteriEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glGetFramebufferParameteriv(int p0, int p1, int[] p2) {
        ARBFramebufferNoAttachments.glGetFramebufferParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetFramebufferParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedFramebufferParameterivEXT(int p0, int p1, int[] p2) {
        ARBFramebufferNoAttachments.glGetNamedFramebufferParameterivEXT(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedFramebufferParameterivEXT", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}

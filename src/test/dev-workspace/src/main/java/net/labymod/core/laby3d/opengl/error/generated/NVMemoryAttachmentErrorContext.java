package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVMemoryAttachment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVMemoryAttachmentErrorContext.class */
public final class NVMemoryAttachmentErrorContext {
    public static void nglGetMemoryObjectDetachedResourcesuivNV(int p0, int p1, int p2, int p3, long p4) {
        NVMemoryAttachment.nglGetMemoryObjectDetachedResourcesuivNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetMemoryObjectDetachedResourcesuivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetMemoryObjectDetachedResourcesuivNV(int p0, int p1, int p2, IntBuffer p3) {
        NVMemoryAttachment.glGetMemoryObjectDetachedResourcesuivNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetMemoryObjectDetachedResourcesuivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glResetMemoryObjectParameterNV(int p0, int p1) {
        NVMemoryAttachment.glResetMemoryObjectParameterNV(p0, p1);
        LabyDebugContext.glError("glResetMemoryObjectParameterNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glTexAttachMemoryNV(int p0, int p1, long p2) {
        NVMemoryAttachment.glTexAttachMemoryNV(p0, p1, p2);
        LabyDebugContext.glError("glTexAttachMemoryNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glBufferAttachMemoryNV(int p0, int p1, long p2) {
        NVMemoryAttachment.glBufferAttachMemoryNV(p0, p1, p2);
        LabyDebugContext.glError("glBufferAttachMemoryNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTextureAttachMemoryNV(int p0, int p1, long p2) {
        NVMemoryAttachment.glTextureAttachMemoryNV(p0, p1, p2);
        LabyDebugContext.glError("glTextureAttachMemoryNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glNamedBufferAttachMemoryNV(int p0, int p1, long p2) {
        NVMemoryAttachment.glNamedBufferAttachMemoryNV(p0, p1, p2);
        LabyDebugContext.glError("glNamedBufferAttachMemoryNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetMemoryObjectDetachedResourcesuivNV(int p0, int p1, int p2, int[] p3) {
        NVMemoryAttachment.glGetMemoryObjectDetachedResourcesuivNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetMemoryObjectDetachedResourcesuivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }
}

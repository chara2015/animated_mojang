package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVBindlessMultiDrawIndirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVBindlessMultiDrawIndirectErrorContext.class */
public final class NVBindlessMultiDrawIndirectErrorContext {
    public static void nglMultiDrawArraysIndirectBindlessNV(int p0, long p1, int p2, int p3, int p4) {
        NVBindlessMultiDrawIndirect.nglMultiDrawArraysIndirectBindlessNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglMultiDrawArraysIndirectBindlessNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glMultiDrawArraysIndirectBindlessNV(int p0, ByteBuffer p1, int p2, int p3, int p4) {
        NVBindlessMultiDrawIndirect.glMultiDrawArraysIndirectBindlessNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawArraysIndirectBindlessNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glMultiDrawArraysIndirectBindlessNV(int p0, long p1, int p2, int p3, int p4) {
        NVBindlessMultiDrawIndirect.glMultiDrawArraysIndirectBindlessNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glMultiDrawArraysIndirectBindlessNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglMultiDrawElementsIndirectBindlessNV(int p0, int p1, long p2, int p3, int p4, int p5) {
        NVBindlessMultiDrawIndirect.nglMultiDrawElementsIndirectBindlessNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglMultiDrawElementsIndirectBindlessNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glMultiDrawElementsIndirectBindlessNV(int p0, int p1, ByteBuffer p2, int p3, int p4, int p5) {
        NVBindlessMultiDrawIndirect.glMultiDrawElementsIndirectBindlessNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMultiDrawElementsIndirectBindlessNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glMultiDrawElementsIndirectBindlessNV(int p0, int p1, long p2, int p3, int p4, int p5) {
        NVBindlessMultiDrawIndirect.glMultiDrawElementsIndirectBindlessNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMultiDrawElementsIndirectBindlessNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }
}

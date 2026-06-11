package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.NVCommandList;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVCommandListErrorContext.class */
public final class NVCommandListErrorContext {
    public static void nglCreateStatesNV(int p0, long p1) {
        NVCommandList.nglCreateStatesNV(p0, p1);
        LabyDebugContext.glError("nglCreateStatesNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateStatesNV(IntBuffer p0) {
        NVCommandList.glCreateStatesNV(p0);
        LabyDebugContext.glError("glCreateStatesNV", "p0", p0);
    }

    public static int glCreateStatesNV() {
        int returnType = NVCommandList.glCreateStatesNV();
        LabyDebugContext.glError("glCreateStatesNV", new Object[0]);
        return returnType;
    }

    public static void nglDeleteStatesNV(int p0, long p1) {
        NVCommandList.nglDeleteStatesNV(p0, p1);
        LabyDebugContext.glError("nglDeleteStatesNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteStatesNV(IntBuffer p0) {
        NVCommandList.glDeleteStatesNV(p0);
        LabyDebugContext.glError("glDeleteStatesNV", "p0", p0);
    }

    public static void glDeleteStatesNV(int p0) {
        NVCommandList.glDeleteStatesNV(p0);
        LabyDebugContext.glError("glDeleteStatesNV", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsStateNV(int p0) {
        boolean returnType = NVCommandList.glIsStateNV(p0);
        LabyDebugContext.glError("glIsStateNV", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glStateCaptureNV(int p0, int p1) {
        NVCommandList.glStateCaptureNV(p0, p1);
        LabyDebugContext.glError("glStateCaptureNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static int glGetCommandHeaderNV(int p0, int p1) {
        int returnType = NVCommandList.glGetCommandHeaderNV(p0, p1);
        LabyDebugContext.glError("glGetCommandHeaderNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static short glGetStageIndexNV(int p0) {
        short returnType = NVCommandList.glGetStageIndexNV(p0);
        LabyDebugContext.glError("glGetStageIndexNV", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglDrawCommandsNV(int p0, int p1, long p2, long p3, int p4) {
        NVCommandList.nglDrawCommandsNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglDrawCommandsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawCommandsNV(int p0, int p1, PointerBuffer p2, IntBuffer p3) {
        NVCommandList.glDrawCommandsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawCommandsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void nglDrawCommandsAddressNV(int p0, long p1, long p2, int p3) {
        NVCommandList.nglDrawCommandsAddressNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglDrawCommandsAddressNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glDrawCommandsAddressNV(int p0, LongBuffer p1, IntBuffer p2) {
        NVCommandList.glDrawCommandsAddressNV(p0, p1, p2);
        LabyDebugContext.glError("glDrawCommandsAddressNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void nglDrawCommandsStatesNV(int p0, long p1, long p2, long p3, long p4, int p5) {
        NVCommandList.nglDrawCommandsStatesNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglDrawCommandsStatesNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glDrawCommandsStatesNV(int p0, PointerBuffer p1, IntBuffer p2, IntBuffer p3, IntBuffer p4) {
        NVCommandList.glDrawCommandsStatesNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawCommandsStatesNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4);
    }

    public static void nglDrawCommandsStatesAddressNV(long p0, long p1, long p2, long p3, int p4) {
        NVCommandList.nglDrawCommandsStatesAddressNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglDrawCommandsStatesAddressNV", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glDrawCommandsStatesAddressNV(LongBuffer p0, IntBuffer p1, IntBuffer p2, IntBuffer p3) {
        NVCommandList.glDrawCommandsStatesAddressNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawCommandsStatesAddressNV", "p0", p0, "p1", p1, "p2", p2, "p3", p3);
    }

    public static void nglCreateCommandListsNV(int p0, long p1) {
        NVCommandList.nglCreateCommandListsNV(p0, p1);
        LabyDebugContext.glError("nglCreateCommandListsNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glCreateCommandListsNV(IntBuffer p0) {
        NVCommandList.glCreateCommandListsNV(p0);
        LabyDebugContext.glError("glCreateCommandListsNV", "p0", p0);
    }

    public static int glCreateCommandListsNV() {
        int returnType = NVCommandList.glCreateCommandListsNV();
        LabyDebugContext.glError("glCreateCommandListsNV", new Object[0]);
        return returnType;
    }

    public static void nglDeleteCommandListsNV(int p0, long p1) {
        NVCommandList.nglDeleteCommandListsNV(p0, p1);
        LabyDebugContext.glError("nglDeleteCommandListsNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteCommandListsNV(IntBuffer p0) {
        NVCommandList.glDeleteCommandListsNV(p0);
        LabyDebugContext.glError("glDeleteCommandListsNV", "p0", p0);
    }

    public static void glDeleteCommandListsNV(int p0) {
        NVCommandList.glDeleteCommandListsNV(p0);
        LabyDebugContext.glError("glDeleteCommandListsNV", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsCommandListNV(int p0) {
        boolean returnType = NVCommandList.glIsCommandListNV(p0);
        LabyDebugContext.glError("glIsCommandListNV", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglListDrawCommandsStatesClientNV(int p0, int p1, long p2, long p3, long p4, long p5, int p6) {
        NVCommandList.nglListDrawCommandsStatesClientNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglListDrawCommandsStatesClientNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glListDrawCommandsStatesClientNV(int p0, int p1, PointerBuffer p2, IntBuffer p3, IntBuffer p4, IntBuffer p5) {
        NVCommandList.glListDrawCommandsStatesClientNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glListDrawCommandsStatesClientNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static void glCommandListSegmentsNV(int p0, int p1) {
        NVCommandList.glCommandListSegmentsNV(p0, p1);
        LabyDebugContext.glError("glCommandListSegmentsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glCompileCommandListNV(int p0) {
        NVCommandList.glCompileCommandListNV(p0);
        LabyDebugContext.glError("glCompileCommandListNV", "p0", Integer.valueOf(p0));
    }

    public static void glCallCommandListNV(int p0) {
        NVCommandList.glCallCommandListNV(p0);
        LabyDebugContext.glError("glCallCommandListNV", "p0", Integer.valueOf(p0));
    }

    public static void glCreateStatesNV(int[] p0) {
        NVCommandList.glCreateStatesNV(p0);
        LabyDebugContext.glError("glCreateStatesNV", "p0", p0);
    }

    public static void glDeleteStatesNV(int[] p0) {
        NVCommandList.glDeleteStatesNV(p0);
        LabyDebugContext.glError("glDeleteStatesNV", "p0", p0);
    }

    public static void glDrawCommandsNV(int p0, int p1, PointerBuffer p2, int[] p3) {
        NVCommandList.glDrawCommandsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawCommandsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void glDrawCommandsAddressNV(int p0, long[] p1, int[] p2) {
        NVCommandList.glDrawCommandsAddressNV(p0, p1, p2);
        LabyDebugContext.glError("glDrawCommandsAddressNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glDrawCommandsStatesNV(int p0, PointerBuffer p1, int[] p2, int[] p3, int[] p4) {
        NVCommandList.glDrawCommandsStatesNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawCommandsStatesNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3, "p4", p4);
    }

    public static void glDrawCommandsStatesAddressNV(long[] p0, int[] p1, int[] p2, int[] p3) {
        NVCommandList.glDrawCommandsStatesAddressNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawCommandsStatesAddressNV", "p0", p0, "p1", p1, "p2", p2, "p3", p3);
    }

    public static void glCreateCommandListsNV(int[] p0) {
        NVCommandList.glCreateCommandListsNV(p0);
        LabyDebugContext.glError("glCreateCommandListsNV", "p0", p0);
    }

    public static void glDeleteCommandListsNV(int[] p0) {
        NVCommandList.glDeleteCommandListsNV(p0);
        LabyDebugContext.glError("glDeleteCommandListsNV", "p0", p0);
    }

    public static void glListDrawCommandsStatesClientNV(int p0, int p1, PointerBuffer p2, int[] p3, int[] p4, int[] p5) {
        NVCommandList.glListDrawCommandsStatesClientNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glListDrawCommandsStatesClientNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }
}

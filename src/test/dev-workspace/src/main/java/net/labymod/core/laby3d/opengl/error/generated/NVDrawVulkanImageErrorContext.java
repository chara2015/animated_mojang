package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVDrawVulkanImage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVDrawVulkanImageErrorContext.class */
public final class NVDrawVulkanImageErrorContext {
    public static void glDrawVkImageNV(long p0, int p1, float p2, float p3, float p4, float p5, float p6, float p7, float p8, float p9, float p10) {
        NVDrawVulkanImage.glDrawVkImageNV(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        LabyDebugContext.glError("glDrawVkImageNV", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5), "p6", Float.valueOf(p6), "p7", Float.valueOf(p7), "p8", Float.valueOf(p8), "p9", Float.valueOf(p9), "p10", Float.valueOf(p10));
    }

    public static long nglGetVkProcAddrNV(long p0) {
        long returnType = NVDrawVulkanImage.nglGetVkProcAddrNV(p0);
        LabyDebugContext.glError("nglGetVkProcAddrNV", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long glGetVkProcAddrNV(ByteBuffer p0) {
        long returnType = NVDrawVulkanImage.glGetVkProcAddrNV(p0);
        LabyDebugContext.glError("glGetVkProcAddrNV", "p0", p0);
        return returnType;
    }

    public static long glGetVkProcAddrNV(CharSequence p0) {
        long returnType = NVDrawVulkanImage.glGetVkProcAddrNV(p0);
        LabyDebugContext.glError("glGetVkProcAddrNV", "p0", p0);
        return returnType;
    }

    public static void glWaitVkSemaphoreNV(long p0) {
        NVDrawVulkanImage.glWaitVkSemaphoreNV(p0);
        LabyDebugContext.glError("glWaitVkSemaphoreNV", "p0", Long.valueOf(p0));
    }

    public static void glSignalVkSemaphoreNV(long p0) {
        NVDrawVulkanImage.glSignalVkSemaphoreNV(p0);
        LabyDebugContext.glError("glSignalVkSemaphoreNV", "p0", Long.valueOf(p0));
    }

    public static void glSignalVkFenceNV(long p0) {
        NVDrawVulkanImage.glSignalVkFenceNV(p0);
        LabyDebugContext.glError("glSignalVkFenceNV", "p0", Long.valueOf(p0));
    }
}

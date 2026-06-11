package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GPU_DEVICE;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.windows.RECT;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GPU_DEVICEErrorContext.class */
public final class GPU_DEVICEErrorContext {
    public static GPU_DEVICE malloc() {
        GPU_DEVICE returnType = GPU_DEVICE.malloc();
        LabyDebugContext.glError("malloc", new Object[0]);
        return returnType;
    }

    public static GPU_DEVICE calloc() {
        GPU_DEVICE returnType = GPU_DEVICE.calloc();
        LabyDebugContext.glError("calloc", new Object[0]);
        return returnType;
    }

    public static GPU_DEVICE create() {
        GPU_DEVICE returnType = GPU_DEVICE.create();
        LabyDebugContext.glError("create", new Object[0]);
        return returnType;
    }

    public static GPU_DEVICE create(long p0) {
        GPU_DEVICE returnType = GPU_DEVICE.create(p0);
        LabyDebugContext.glError("create", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static GPU_DEVICE createSafe(long p0) {
        GPU_DEVICE returnType = GPU_DEVICE.createSafe(p0);
        LabyDebugContext.glError("createSafe", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static GPU_DEVICE.Buffer malloc(int p0) {
        GPU_DEVICE.Buffer returnType = GPU_DEVICE.malloc(p0);
        LabyDebugContext.glError("malloc", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static GPU_DEVICE.Buffer calloc(int p0) {
        GPU_DEVICE.Buffer returnType = GPU_DEVICE.calloc(p0);
        LabyDebugContext.glError("calloc", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static GPU_DEVICE.Buffer create(int p0) {
        GPU_DEVICE.Buffer returnType = GPU_DEVICE.create(p0);
        LabyDebugContext.glError("create", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static GPU_DEVICE.Buffer create(long p0, int p1) {
        GPU_DEVICE.Buffer returnType = GPU_DEVICE.create(p0, p1);
        LabyDebugContext.glError("create", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static GPU_DEVICE.Buffer createSafe(long p0, int p1) {
        GPU_DEVICE.Buffer returnType = GPU_DEVICE.createSafe(p0, p1);
        LabyDebugContext.glError("createSafe", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static GPU_DEVICE malloc(MemoryStack p0) {
        GPU_DEVICE returnType = GPU_DEVICE.malloc(p0);
        LabyDebugContext.glError("malloc", "p0", p0);
        return returnType;
    }

    public static GPU_DEVICE calloc(MemoryStack p0) {
        GPU_DEVICE returnType = GPU_DEVICE.calloc(p0);
        LabyDebugContext.glError("calloc", "p0", p0);
        return returnType;
    }

    public static GPU_DEVICE.Buffer malloc(int p0, MemoryStack p1) {
        GPU_DEVICE.Buffer returnType = GPU_DEVICE.malloc(p0, p1);
        LabyDebugContext.glError("malloc", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static GPU_DEVICE.Buffer calloc(int p0, MemoryStack p1) {
        GPU_DEVICE.Buffer returnType = GPU_DEVICE.calloc(p0, p1);
        LabyDebugContext.glError("calloc", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int ncb(long p0) {
        int returnType = GPU_DEVICE.ncb(p0);
        LabyDebugContext.glError("ncb", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static ByteBuffer nDeviceName(long p0) {
        ByteBuffer returnType = GPU_DEVICE.nDeviceName(p0);
        LabyDebugContext.glError("nDeviceName", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static String nDeviceNameString(long p0) {
        String returnType = GPU_DEVICE.nDeviceNameString(p0);
        LabyDebugContext.glError("nDeviceNameString", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static ByteBuffer nDeviceString(long p0) {
        ByteBuffer returnType = GPU_DEVICE.nDeviceString(p0);
        LabyDebugContext.glError("nDeviceString", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static String nDeviceStringString(long p0) {
        String returnType = GPU_DEVICE.nDeviceStringString(p0);
        LabyDebugContext.glError("nDeviceStringString", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nFlags(long p0) {
        int returnType = GPU_DEVICE.nFlags(p0);
        LabyDebugContext.glError("nFlags", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static RECT nrcVirtualScreen(long p0) {
        RECT returnType = GPU_DEVICE.nrcVirtualScreen(p0);
        LabyDebugContext.glError("nrcVirtualScreen", "p0", Long.valueOf(p0));
        return returnType;
    }
}

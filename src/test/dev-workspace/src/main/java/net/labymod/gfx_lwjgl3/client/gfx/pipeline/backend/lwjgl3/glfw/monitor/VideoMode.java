package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import org.lwjgl.glfw.GLFWVidMode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode.class */
public final class VideoMode extends Record {
    private final int width;
    private final int height;
    private final int redBits;
    private final int greenBits;
    private final int blueBits;
    private final int refreshRate;

    public VideoMode(int width, int height, int redBits, int greenBits, int blueBits, int refreshRate) {
        this.width = width;
        this.height = height;
        this.redBits = redBits;
        this.greenBits = greenBits;
        this.blueBits = blueBits;
        this.refreshRate = refreshRate;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, VideoMode.class), VideoMode.class, "width;height;redBits;greenBits;blueBits;refreshRate", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->width:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->height:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->redBits:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->greenBits:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->blueBits:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->refreshRate:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, VideoMode.class), VideoMode.class, "width;height;redBits;greenBits;blueBits;refreshRate", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->width:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->height:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->redBits:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->greenBits:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->blueBits:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->refreshRate:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, VideoMode.class, Object.class), VideoMode.class, "width;height;redBits;greenBits;blueBits;refreshRate", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->width:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->height:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->redBits:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->greenBits:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->blueBits:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/VideoMode;->refreshRate:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public int redBits() {
        return this.redBits;
    }

    public int greenBits() {
        return this.greenBits;
    }

    public int blueBits() {
        return this.blueBits;
    }

    public int refreshRate() {
        return this.refreshRate;
    }

    public static VideoMode of(GLFWVidMode.Buffer buffer) {
        return new VideoMode(buffer.width(), buffer.height(), buffer.redBits(), buffer.greenBits(), buffer.blueBits(), buffer.refreshRate());
    }

    public static VideoMode of(GLFWVidMode mode) {
        return new VideoMode(mode.width(), mode.height(), mode.redBits(), mode.greenBits(), mode.blueBits(), mode.refreshRate());
    }
}

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/Monitor.class */
public class Monitor {
    private final long monitorHandle;
    private final List<VideoMode> videoModes = new ArrayList();
    private VideoMode currentMode;
    private int x;
    private int y;

    public Monitor(long monitorHandle) {
        this.monitorHandle = monitorHandle;
        refresh();
    }

    public void refresh() {
        this.videoModes.clear();
        GLFWVidMode.Buffer videoModesBuffer = GLFW.glfwGetVideoModes(this.monitorHandle);
        for (int index = videoModesBuffer.limit() - 1; index >= 0; index--) {
            videoModesBuffer.position(index);
            VideoMode videoMode = VideoMode.of(videoModesBuffer);
            if (videoMode.redBits() >= 8 && videoMode.greenBits() >= 8 && videoMode.blueBits() >= 8) {
                this.videoModes.add(videoMode);
            }
        }
        int[] x = new int[1];
        int[] y = new int[1];
        GLFW.glfwGetMonitorPos(this.monitorHandle, x, y);
        this.x = x[0];
        this.y = y[0];
        GLFWVidMode currentVidMode = GLFW.glfwGetVideoMode(this.monitorHandle);
        this.currentMode = VideoMode.of(currentVidMode);
    }

    public VideoMode currentMode() {
        return this.currentMode;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public VideoMode getMode(int index) {
        return this.videoModes.get(index);
    }

    public int getModeCount() {
        return this.videoModes.size();
    }

    public long getMonitor() {
        return this.monitorHandle;
    }
}

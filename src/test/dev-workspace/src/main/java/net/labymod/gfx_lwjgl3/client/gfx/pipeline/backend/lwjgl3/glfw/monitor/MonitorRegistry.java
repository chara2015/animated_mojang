package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.labymod.api.util.Disposable;
import net.labymod.api.util.logging.Logging;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.CallbackUtil;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMonitorCallbackI;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/MonitorRegistry.class */
public final class MonitorRegistry implements Disposable {
    private static final Logging LOGGER = Logging.create((Class<?>) MonitorRegistry.class);
    private final Long2ObjectMap<Monitor> monitors = new Long2ObjectOpenHashMap();

    public MonitorRegistry() {
        GLFW.glfwSetMonitorCallback(this::onMonitorChange);
        collectMonitors();
    }

    @Nullable
    public Monitor findBestMonitor(long windowHandle) {
        return findBestMonitor(windowHandle, (minX, minY, maxX, maxY) -> {
            return true;
        });
    }

    @Nullable
    public Monitor findBestMonitor(long windowHandle, MonitorPredicate filter) {
        long monitorHandle = GLFW.glfwGetWindowMonitor(windowHandle);
        if (monitorHandle != 0) {
            return getMonitor(monitorHandle);
        }
        ObjectIterator it = this.monitors.values().iterator();
        while (it.hasNext()) {
            Monitor monitor = (Monitor) it.next();
            VideoMode videoMode = monitor.currentMode();
            int monitorX = monitor.getX();
            int monitorY = monitor.getY();
            int maxMonitorX = monitorX + videoMode.width();
            int maxMonitorY = monitorY + videoMode.height();
            if (!filter.test(monitorX, monitorY, maxMonitorX, maxMonitorY)) {
                return monitor;
            }
        }
        long primaryMonitorHandle = GLFW.glfwGetPrimaryMonitor();
        LOGGER.info("Selecting primary monitor: {}", Long.valueOf(primaryMonitorHandle));
        return new Monitor(primaryMonitorHandle);
    }

    @Nullable
    public Monitor getMonitor(long handle) {
        return (Monitor) this.monitors.get(handle);
    }

    @Override // net.labymod.api.util.Disposable
    public void dispose() {
        GLFWMonitorCallback callback = GLFW.glfwSetMonitorCallback((GLFWMonitorCallbackI) null);
        CallbackUtil.free(callback);
    }

    private void collectMonitors() {
        PointerBuffer monitorsBuffer = GLFW.glfwGetMonitors();
        if (monitorsBuffer == null) {
            return;
        }
        for (int index = 0; index < monitorsBuffer.limit(); index++) {
            long monitorHandle = monitorsBuffer.get(index);
            addMonitor(monitorHandle);
        }
    }

    private void onMonitorChange(long handle, int event) {
        switch (event) {
            case 262145:
                addMonitor(handle);
                LOGGER.info("Monitor {} connected.", Long.valueOf(handle));
                break;
            case 262146:
                this.monitors.remove(handle);
                LOGGER.info("Monitor {} disconnected.", new Object[0]);
                break;
        }
    }

    private void addMonitor(long handle) {
        this.monitors.put(handle, new Monitor(handle));
    }
}

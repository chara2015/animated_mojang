package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMonitorCallbackI;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/platform/ScreenManager.class */
public class ScreenManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Long2ObjectMap<Monitor> monitors = new Long2ObjectOpenHashMap();
    private final MonitorCreator monitorCreator;

    public ScreenManager(MonitorCreator $$0) {
        this.monitorCreator = $$0;
        GLFW.glfwSetMonitorCallback(this::onMonitorChange);
        PointerBuffer $$1 = GLFW.glfwGetMonitors();
        if ($$1 != null) {
            for (int $$2 = 0; $$2 < $$1.limit(); $$2++) {
                long $$3 = $$1.get($$2);
                this.monitors.put($$3, $$0.createMonitor($$3));
            }
        }
    }

    private void onMonitorChange(long $$0, int $$1) {
        RenderSystem.assertOnRenderThread();
        if ($$1 == 262145) {
            this.monitors.put($$0, this.monitorCreator.createMonitor($$0));
            LOGGER.debug("Monitor {} connected. Current monitors: {}", Long.valueOf($$0), this.monitors);
        } else if ($$1 == 262146) {
            this.monitors.remove($$0);
            LOGGER.debug("Monitor {} disconnected. Current monitors: {}", Long.valueOf($$0), this.monitors);
        }
    }

    public Monitor getMonitor(long $$0) {
        return (Monitor) this.monitors.get($$0);
    }

    public Monitor findBestMonitor(Window $$0) {
        long $$1 = GLFW.glfwGetWindowMonitor($$0.handle());
        if ($$1 != 0) {
            return getMonitor($$1);
        }
        int $$2 = $$0.getX();
        int $$3 = $$2 + $$0.getScreenWidth();
        int $$4 = $$0.getY();
        int $$5 = $$4 + $$0.getScreenHeight();
        int $$6 = -1;
        Monitor $$7 = null;
        long $$8 = GLFW.glfwGetPrimaryMonitor();
        LOGGER.debug("Selecting monitor - primary: {}, current monitors: {}", Long.valueOf($$8), this.monitors);
        ObjectIterator it = this.monitors.values().iterator();
        while (it.hasNext()) {
            Monitor $$9 = (Monitor) it.next();
            int $$10 = $$9.getX();
            int $$11 = $$10 + $$9.getCurrentMode().getWidth();
            int $$12 = $$9.getY();
            int $$13 = $$12 + $$9.getCurrentMode().getHeight();
            int $$14 = clamp($$2, $$10, $$11);
            int $$15 = clamp($$3, $$10, $$11);
            int $$16 = clamp($$4, $$12, $$13);
            int $$17 = clamp($$5, $$12, $$13);
            int $$18 = Math.max(0, $$15 - $$14);
            int $$19 = Math.max(0, $$17 - $$16);
            int $$20 = $$18 * $$19;
            if ($$20 > $$6) {
                $$7 = $$9;
                $$6 = $$20;
            } else if ($$20 == $$6 && $$8 == $$9.getMonitor()) {
                LOGGER.debug("Primary monitor {} is preferred to monitor {}", $$9, $$7);
                $$7 = $$9;
            }
        }
        LOGGER.debug("Selected monitor: {}", $$7);
        return $$7;
    }

    public static int clamp(int $$0, int $$1, int $$2) {
        if ($$0 < $$1) {
            return $$1;
        }
        if ($$0 > $$2) {
            return $$2;
        }
        return $$0;
    }

    public void shutdown() {
        RenderSystem.assertOnRenderThread();
        GLFWMonitorCallback $$0 = GLFW.glfwSetMonitorCallback((GLFWMonitorCallbackI) null);
        if ($$0 != null) {
            $$0.free();
        }
    }
}

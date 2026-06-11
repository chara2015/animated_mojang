package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor;

import org.lwjgl.opengl.Display;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/monitor/MonitorPredicate.class */
public interface MonitorPredicate {
    public static final MonitorPredicate DISPLAY_MONITOR_FILTER = (minX, minY, maxX, maxY) -> {
        int windowMinX = Display.getX();
        int windowMinY = Display.getY();
        int windowMaxX = windowMinX + Display.getWidth();
        int windowMaxY = windowMinY + Display.getHeight();
        return minX >= windowMaxX || minY >= windowMaxY || maxX <= windowMinX || maxY <= windowMinY;
    };

    boolean test(int i, int i2, int i3, int i4);
}

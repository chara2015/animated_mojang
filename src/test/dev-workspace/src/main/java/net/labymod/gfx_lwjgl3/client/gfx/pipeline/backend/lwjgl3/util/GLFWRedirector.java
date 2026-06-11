package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import it.unimi.dsi.fastutil.longs.LongListIterator;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.window.WindowHandleStorage;
import net.labymod.api.event.client.gui.window.WindowShowEvent;
import net.labymod.core.client.os.windows.window.WindowManagement;
import org.lwjgl.glfw.GLFW;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLFWRedirector.class */
public final class GLFWRedirector {
    private GLFWRedirector() {
    }

    public static long nglfwCreateWindow(int width, int height, long title, long monitor, long share) {
        long window = GLFW.nglfwCreateWindow(width, height, title, monitor, share);
        if (window == 0) {
            return window;
        }
        int visible = GLFW.glfwGetWindowAttrib(window, 131076);
        if (visible == 1) {
            dispatchWindowShowEvent(window);
        }
        WindowManagement.update(window);
        WindowHandleStorage.WINDOW_HANDLES.add(window);
        return window;
    }

    public static void glfwDestroyWindow(long window) {
        GLFW.glfwDestroyWindow(window);
        LongListIterator iterator = WindowHandleStorage.WINDOW_HANDLES.iterator();
        while (iterator.hasNext()) {
            long windowHandle = iterator.nextLong();
            if (window == windowHandle) {
                iterator.remove();
            }
        }
    }

    public static void glfwShowWindow(long window) {
        GLFW.glfwShowWindow(window);
        dispatchWindowShowEvent(window);
    }

    private static void dispatchWindowShowEvent(long window) {
        Laby.fireEvent(new WindowShowEvent(window));
    }
}

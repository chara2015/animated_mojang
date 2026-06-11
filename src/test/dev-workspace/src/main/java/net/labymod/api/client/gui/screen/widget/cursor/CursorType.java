package net.labymod.api.client.gui.screen.widget.cursor;

import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/cursor/CursorType.class */
public final class CursorType {
    public static final CursorType DEFAULT = new CursorType("default", 0);
    private static final Logging LOGGER = Logging.create((Class<?>) CursorType.class);
    private final String name;
    private final long handle;

    private CursorType(@NotNull String name, long handle) {
        this.name = name;
        this.handle = handle;
    }

    public void select(long windowHandle) {
        GLFW.glfwSetCursor(windowHandle, this.handle);
    }

    @NotNull
    public String toString() {
        return this.name;
    }

    @NotNull
    public static CursorType createStandardCursor(int shape, @NotNull String name, @NotNull CursorType fallback) {
        long handle = 0;
        GLFWErrorCallback defaultCallback = GLFW.glfwSetErrorCallback((GLFWErrorCallbackI) null);
        try {
            handle = GLFW.glfwCreateStandardCursor(shape);
        } catch (Exception e) {
            LOGGER.info("Unable to create standard cursor " + name + ". This is very likely because the platform does not support this type of cursor. Using fallback.", new Object[0]);
            LOGGER.debug("Unable to create standard cursor " + name + ". Using fallback.", e);
        }
        GLFW.glfwSetErrorCallback(defaultCallback);
        return handle == 0 ? fallback : new CursorType(name, handle);
    }

    @NotNull
    public static CursorType createStandardCursor(int shape, @NotNull String name) {
        return createStandardCursor(shape, name, DEFAULT);
    }
}

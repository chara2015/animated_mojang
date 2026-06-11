package net.labymod.core.test.integration;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.test.InputEmulator;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.logging.Logging;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWScrollCallbackI;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/integration/DefaultInputEmulator.class */
public final class DefaultInputEmulator implements InputEmulator {
    private static final Logging LOGGER = Logging.create((Class<?>) DefaultInputEmulator.class);
    private double mouseX;
    private double mouseY;

    private long getWindowHandle() {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft != null && minecraft.minecraftWindow() != null) {
            return minecraft.minecraftWindow().getPointer();
        }
        return 0L;
    }

    @Override // net.labymod.api.test.InputEmulator
    public InputEmulator pressKey(Key key) {
        long windowHandle = getWindowHandle();
        if (windowHandle == 0) {
            LOGGER.warn("Cannot press key: window handle not available", new Object[0]);
            return this;
        }
        int glfwKey = key.getId();
        if (glfwKey == -1) {
            LOGGER.warn("Unknown key mapping for: {}", key.getActualName());
            return this;
        }
        LOGGER.debug("Emulating key press via GLFW: {} (GLFW: {})", key.getActualName(), Integer.valueOf(glfwKey));
        GLFWKeyCallback callback = GLFW.glfwSetKeyCallback(windowHandle, (GLFWKeyCallbackI) null);
        if (callback != null) {
            callback.invoke(windowHandle, glfwKey, 0, 1, 0);
            GLFW.glfwSetKeyCallback(windowHandle, callback);
        }
        return this;
    }

    @Override // net.labymod.api.test.InputEmulator
    public InputEmulator releaseKey(Key key) {
        long windowHandle = getWindowHandle();
        if (windowHandle == 0) {
            LOGGER.warn("Cannot release key: window handle not available", new Object[0]);
            return this;
        }
        int glfwKey = key.getId();
        if (glfwKey == -1) {
            LOGGER.warn("Unknown key mapping for: {}", key.getActualName());
            return this;
        }
        LOGGER.debug("Emulating key release via GLFW: {} (GLFW: {})", key.getActualName(), Integer.valueOf(glfwKey));
        GLFWKeyCallback callback = GLFW.glfwSetKeyCallback(windowHandle, (GLFWKeyCallbackI) null);
        if (callback != null) {
            callback.invoke(windowHandle, glfwKey, 0, 0, 0);
            GLFW.glfwSetKeyCallback(windowHandle, callback);
        }
        return this;
    }

    @Override // net.labymod.api.test.InputEmulator
    public InputEmulator typeCharacter(char character) {
        long windowHandle = getWindowHandle();
        if (windowHandle == 0) {
            LOGGER.warn("Cannot type character: window handle not available", new Object[0]);
            return this;
        }
        LOGGER.debug("Emulating character typed via GLFW: '{}'", Character.valueOf(character));
        GLFWCharCallback callback = GLFW.glfwSetCharCallback(windowHandle, (GLFWCharCallbackI) null);
        if (callback != null) {
            callback.invoke(windowHandle, character);
            GLFW.glfwSetCharCallback(windowHandle, callback);
        }
        return this;
    }

    @Override // net.labymod.api.test.InputEmulator
    public InputEmulator pressMouse(MouseButton button) {
        long windowHandle = getWindowHandle();
        if (windowHandle == 0) {
            LOGGER.warn("Cannot press mouse: window handle not available", new Object[0]);
            return this;
        }
        int glfwButton = button.getId();
        LOGGER.debug("Emulating mouse press via GLFW: {} at ({}, {})", button.getActualName(), Double.valueOf(this.mouseX), Double.valueOf(this.mouseY));
        GLFWMouseButtonCallback callback = GLFW.glfwSetMouseButtonCallback(windowHandle, (GLFWMouseButtonCallbackI) null);
        if (callback != null) {
            callback.invoke(windowHandle, glfwButton, 1, 0);
            GLFW.glfwSetMouseButtonCallback(windowHandle, callback);
        }
        return this;
    }

    @Override // net.labymod.api.test.InputEmulator
    public InputEmulator releaseMouse(MouseButton button) {
        long windowHandle = getWindowHandle();
        if (windowHandle == 0) {
            LOGGER.warn("Cannot release mouse: window handle not available", new Object[0]);
            return this;
        }
        int glfwButton = button.getId();
        LOGGER.debug("Emulating mouse release via GLFW: {} at ({}, {})", button.getActualName(), Double.valueOf(this.mouseX), Double.valueOf(this.mouseY));
        GLFWMouseButtonCallback callback = GLFW.glfwSetMouseButtonCallback(windowHandle, (GLFWMouseButtonCallbackI) null);
        if (callback != null) {
            callback.invoke(windowHandle, glfwButton, 0, 0);
            GLFW.glfwSetMouseButtonCallback(windowHandle, callback);
        }
        return this;
    }

    @Override // net.labymod.api.test.InputEmulator
    public InputEmulator moveMouse(double x, double y) {
        long windowHandle = getWindowHandle();
        if (windowHandle == 0) {
            LOGGER.warn("Cannot move mouse: window handle not available", new Object[0]);
            return this;
        }
        LOGGER.debug("Emulating mouse move via GLFW: ({}, {}) -> ({}, {})", Double.valueOf(this.mouseX), Double.valueOf(this.mouseY), Double.valueOf(x), Double.valueOf(y));
        this.mouseX = x;
        this.mouseY = y;
        GLFW.glfwSetCursorPos(windowHandle, x, y);
        GLFWCursorPosCallback callback = GLFW.glfwSetCursorPosCallback(windowHandle, (GLFWCursorPosCallbackI) null);
        if (callback != null) {
            callback.invoke(windowHandle, x, y);
            GLFW.glfwSetCursorPosCallback(windowHandle, callback);
        }
        return this;
    }

    @Override // net.labymod.api.test.InputEmulator
    public InputEmulator moveMouseBy(double deltaX, double deltaY) {
        return moveMouse(this.mouseX + deltaX, this.mouseY + deltaY);
    }

    @Override // net.labymod.api.test.InputEmulator
    public InputEmulator scroll(double deltaX, double deltaY) {
        long windowHandle = getWindowHandle();
        if (windowHandle == 0) {
            LOGGER.warn("Cannot scroll: window handle not available", new Object[0]);
            return this;
        }
        LOGGER.debug("Emulating scroll via GLFW: ({}, {}) at ({}, {})", Double.valueOf(deltaX), Double.valueOf(deltaY), Double.valueOf(this.mouseX), Double.valueOf(this.mouseY));
        GLFWScrollCallback callback = GLFW.glfwSetScrollCallback(windowHandle, (GLFWScrollCallbackI) null);
        if (callback != null) {
            callback.invoke(windowHandle, deltaX, deltaY);
            GLFW.glfwSetScrollCallback(windowHandle, callback);
        }
        return this;
    }

    @Override // net.labymod.api.test.InputEmulator
    public double getMouseX() {
        return this.mouseX;
    }

    @Override // net.labymod.api.test.InputEmulator
    public double getMouseY() {
        return this.mouseY;
    }

    @Override // net.labymod.api.test.InputEmulator
    public InputEmulator delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    @Override // net.labymod.api.test.InputEmulator
    public InputEmulator waitTick() {
        return waitTicks(1);
    }

    @Override // net.labymod.api.test.InputEmulator
    public InputEmulator waitTicks(int ticks) {
        if (ticks <= 0) {
            return this;
        }
        long delayMs = ((long) ticks) * 50;
        CountDownLatch latch = new CountDownLatch(1);
        Objects.requireNonNull(latch);
        Task.builder(latch::countDown).delay(delayMs, TimeUnit.MILLISECONDS).build().execute();
        try {
            boolean completed = latch.await(delayMs + 1000, TimeUnit.MILLISECONDS);
            if (!completed) {
                LOGGER.warn("Timeout waiting for {} ticks", Integer.valueOf(ticks));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return this;
    }
}

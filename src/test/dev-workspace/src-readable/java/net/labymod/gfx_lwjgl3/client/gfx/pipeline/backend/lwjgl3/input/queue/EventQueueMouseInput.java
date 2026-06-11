package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.queue;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.api.util.Buffers;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.opengl.Display;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/input/queue/EventQueueMouseInput.class */
public class EventQueueMouseInput implements MouseInput {
    private static final String DISABLE_RAW_INPUT = "org.lwjgl.input.Mouse.disableRawInput";
    private final byte[] buttonStates = new byte[getButtonCount()];
    private final EventQueue eventQueue = new EventQueue(22);
    private final ByteBuffer tempBuffer = Buffers.createByteBuffer(22);
    private int lastX;
    private int lastY;
    private int accumulatedX;
    private int accumulatedY;
    private int accumulatedZ;
    private boolean grabbed;
    private boolean insideWindow;
    private GLFWMouseButtonCallbackI mouseButtonCallback;
    private GLFWCursorPosCallbackI cursorPosCallback;
    private GLFWCursorEnterCallbackI cursorEnterCallback;
    private GLFWScrollCallbackI scrollCallback;

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput
    public void create() {
        long windowHandle = Display.getWindowHandle();
        setRawMouseMotion(windowHandle, true);
        this.mouseButtonCallback = this::onMouseButton;
        this.cursorPosCallback = this::onCursorPos;
        this.cursorEnterCallback = this::onCursorEnter;
        this.scrollCallback = this::onScroll;
        GLFW.glfwSetMouseButtonCallback(windowHandle, this.mouseButtonCallback);
        GLFW.glfwSetCursorPosCallback(windowHandle, this.cursorPosCallback);
        GLFW.glfwSetCursorEnterCallback(windowHandle, this.cursorEnterCallback);
        GLFW.glfwSetScrollCallback(windowHandle, this.scrollCallback);
    }

    private void onScroll(long window, double xOffset, double yOffset) {
        this.accumulatedZ = (int) (((double) this.accumulatedZ) + yOffset);
        writeMouseEvent((byte) -1, (byte) 0, (int) yOffset, TimeUtil.getNanoTime());
    }

    private void onCursorPos(long window, double newX, double newY) {
        int x = (int) newX;
        int y = (Display.getHeight() - 1) - ((int) newY);
        int deltaX = x - this.lastX;
        int deltaY = y - this.lastY;
        if (deltaX != 0 || deltaY != 0) {
            this.accumulatedX += deltaX;
            this.accumulatedY += deltaY;
            this.lastX = x;
            this.lastY = y;
            long time = TimeUtil.getNanoTime();
            if (this.grabbed) {
                writeMouseEvent((byte) -1, (byte) 0, deltaX, deltaY, 0, time);
            } else {
                writeMouseEvent((byte) -1, (byte) 0, x, y, 0, time);
            }
        }
    }

    private void onCursorEnter(long window, boolean entered) {
        this.insideWindow = entered;
    }

    private void onMouseButton(long window, int button, int action, int mods) {
        byte state = (byte) (action == 1 ? 1 : 0);
        writeMouseEvent((byte) button, state, 0, TimeUtil.getNanoTime());
        if (button < this.buttonStates.length) {
            this.buttonStates[button] = state;
        }
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput
    public void poll(IntBuffer positionBuffer, ByteBuffer buttonBuffer) {
        if (this.grabbed) {
            positionBuffer.put(0, this.accumulatedX);
            positionBuffer.put(1, this.accumulatedY);
        } else {
            positionBuffer.put(0, this.lastX);
            positionBuffer.put(1, this.lastY);
        }
        positionBuffer.put(2, this.accumulatedZ);
        this.accumulatedZ = 0;
        this.accumulatedY = 0;
        this.accumulatedX = 0;
        for (int i = 0; i < this.buttonStates.length; i++) {
            buttonBuffer.put(i, this.buttonStates[i]);
        }
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput
    public void read(ByteBuffer buffer) {
        this.eventQueue.fireEvents(buffer);
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput
    public void setCursorPosition(int x, int y) {
        long windowHandle = Display.getWindowHandle();
        GLFW.glfwSetCursorPos(windowHandle, x, y);
        onCursorPos(windowHandle, x, y);
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput
    public void grab(boolean grab) {
        GLFW.glfwSetInputMode(Display.getWindowHandle(), 208897, grab ? 212995 : 212993);
        this.grabbed = grab;
        reset();
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput
    public boolean isInsideWindow() {
        return this.insideWindow;
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput
    public int getButtonCount() {
        return 8;
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput
    public void setRawMouseInput(boolean rawMouseInput) {
        setRawMouseMotion(Display.getWindowHandle(), rawMouseInput);
    }

    @Override // net.labymod.api.util.Disposable
    public void dispose() {
    }

    private void setRawMouseMotion(long handle, boolean state) {
        if (GLFW.glfwRawMouseMotionSupported() && !Boolean.getBoolean(DISABLE_RAW_INPUT)) {
            GLFW.glfwSetInputMode(handle, 208901, state ? 1 : 0);
        }
    }

    private void writeMouseEvent(byte button, byte state, int delta, long time) {
        if (this.grabbed) {
            writeMouseEvent(button, state, 0, 0, delta, time);
        } else {
            writeMouseEvent(button, state, this.lastX, this.lastY, delta, time);
        }
    }

    private void writeMouseEvent(byte button, byte state, int x, int y, int delta, long time) {
        this.tempBuffer.clear();
        this.tempBuffer.put(button);
        this.tempBuffer.put(state);
        this.tempBuffer.putInt(x);
        this.tempBuffer.putInt(y);
        this.tempBuffer.putInt(delta);
        this.tempBuffer.putLong(time);
        this.tempBuffer.flip();
        this.eventQueue.putEvent(this.tempBuffer);
    }

    private void reset() {
        this.eventQueue.clear();
        this.accumulatedY = 0;
        this.accumulatedX = 0;
    }
}

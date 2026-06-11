package org.lwjgl.input;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.queue.EventQueueMouseInput;
import org.lwjgl.BufferUtils;

/* JADX INFO: loaded from: LabyMod-4.jar:org/lwjgl/input/Mouse.class */
public class Mouse {
    public static final int EVENT_SIZE = 22;
    private static int x;
    private static int y;
    private static int absolute_x;
    private static int absolute_y;
    private static int dx;
    private static int dy;
    private static int dwheel;
    private static int buttonCount;
    private static int event_x;
    private static int event_y;
    private static int event_dx;
    private static int event_dy;
    private static int last_event_raw_x;
    private static int last_event_raw_y;
    private static int event_dwheel;
    private static long event_nanos;
    private static int grab_x;
    private static int grab_y;
    private static boolean grabbed;
    private static int eventButton;
    private static boolean eventState;
    private static IntBuffer coord_buffer;
    private static ByteBuffer buttonBuffer;
    private static ByteBuffer readBuffer;
    private static MouseInput mouseInput;
    private static boolean hasWheel = true;
    private static boolean created = false;

    public static void create() {
        create(new EventQueueMouseInput());
    }

    public static void create(MouseInput mouse) {
        if (created) {
            return;
        }
        mouseInput = mouse;
        mouseInput.create();
        created = true;
        buttonCount = mouseInput.getButtonCount();
        buttonBuffer = BufferUtils.createByteBuffer(buttonCount);
        coord_buffer = BufferUtils.createIntBuffer(3);
        readBuffer = BufferUtils.createByteBuffer(1100);
        readBuffer.limit(0);
        setGrabbed(isGrabbed());
    }

    public static void setCursorPosition(int newX, int newY) {
        isCreatedOrThrown();
        event_x = newX;
        x = newX;
        event_y = newY;
        y = newY;
        if (!isGrabbed()) {
            mouseInput.setCursorPosition(x, y);
        } else {
            grab_x = newX;
            grab_y = newY;
        }
    }

    private static void isCreatedOrThrown() {
        if (isCreated()) {
        } else {
            throw new IllegalStateException("Mouse is not created!");
        }
    }

    public static int getDWheel() {
        int result = dwheel;
        dwheel = 0;
        return result;
    }

    public static int getEventDWheel() {
        return event_dwheel;
    }

    public static int getEventDX() {
        return event_dx;
    }

    public static int getEventDY() {
        return event_dy;
    }

    public static int getEventX() {
        return event_x;
    }

    public static int getEventY() {
        return event_y;
    }

    public static int getEventButton() {
        return eventButton;
    }

    public static boolean getEventButtonState() {
        return eventState;
    }

    public static boolean next() {
        isCreatedOrThrown();
        if (!readBuffer.hasRemaining()) {
            return false;
        }
        eventButton = readBuffer.get();
        eventState = readBuffer.get() != 0;
        if (isGrabbed()) {
            event_dx = readBuffer.getInt();
            event_dy = readBuffer.getInt();
            event_x += event_dx;
            event_y += event_dy;
            last_event_raw_x = event_x;
            last_event_raw_y = event_y;
        } else {
            int newEventX = readBuffer.getInt();
            int newEventY = readBuffer.getInt();
            event_dx = newEventX - last_event_raw_x;
            event_dy = newEventY - last_event_raw_y;
            event_x = newEventX;
            event_y = newEventY;
            last_event_raw_x = newEventX;
            last_event_raw_y = newEventY;
        }
        event_dwheel = readBuffer.getInt();
        event_nanos = readBuffer.getLong();
        return true;
    }

    public static boolean isButtonDown(int button) {
        isCreatedOrThrown();
        return button < buttonCount && button >= 0 && buttonBuffer.get(button) != 0;
    }

    public static long getEventNanoseconds() {
        return event_nanos;
    }

    public static boolean isCreated() {
        return created;
    }

    public static void poll() {
        isCreatedOrThrown();
        mouseInput.poll(coord_buffer, buttonBuffer);
        int xPos = coord_buffer.get(0);
        int yPos = coord_buffer.get(1);
        int wheelDelta = coord_buffer.get(2);
        if (isGrabbed()) {
            dx += xPos;
            dy += yPos;
            x += xPos;
            y += yPos;
            absolute_x += xPos;
            absolute_y += yPos;
        } else {
            dx = xPos - absolute_x;
            dy = yPos - absolute_y;
            x = xPos;
            absolute_x = xPos;
            y = yPos;
            absolute_y = yPos;
        }
        dwheel += wheelDelta;
        read();
    }

    private static void read() {
        readBuffer.compact();
        mouseInput.read(readBuffer);
        readBuffer.flip();
    }

    public static int getDX() {
        int result = dx;
        dx = 0;
        return result;
    }

    public static int getDY() {
        int result = dy;
        dy = 0;
        return result;
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    public static void setGrabbed(boolean grabbed2) {
        boolean lastGrabbedState = grabbed;
        grabbed = grabbed2;
        if (!isCreated()) {
            return;
        }
        mouseInput.grab(grabbed2);
        if (grabbed2 && !lastGrabbedState) {
            grab_x = x;
            grab_y = y;
        } else if (!grabbed2 && lastGrabbedState) {
            mouseInput.setCursorPosition(grab_x, grab_y);
        }
        poll();
        event_x = x;
        event_y = y;
        last_event_raw_x = x;
        last_event_raw_y = y;
        resetMouse();
    }

    private static void resetMouse() {
        dwheel = 0;
        dy = 0;
        dx = 0;
        readBuffer.position(readBuffer.limit());
    }

    public static boolean isGrabbed() {
        return grabbed;
    }

    public static boolean hasWheel() {
        return hasWheel;
    }

    public static void destroy() {
        if (!created) {
            return;
        }
        created = false;
        coord_buffer = null;
        buttonBuffer = null;
        mouseInput.dispose();
    }

    public static boolean isInsideWindow() {
        return mouseInput.isInsideWindow();
    }

    public static void setRawMouseInput(boolean rawMouseInput) {
        mouseInput.setRawMouseInput(rawMouseInput);
    }
}

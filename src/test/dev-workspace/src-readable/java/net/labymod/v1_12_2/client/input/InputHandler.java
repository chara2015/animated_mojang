package net.labymod.v1_12_2.client.input;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.lwjgl.input.Mouse;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/input/InputHandler.class */
public final class InputHandler {
    private InputHandler() {
    }

    public static void fireMouseInput(boolean isScreenContext) {
        int keyCode;
        Key key;
        if (isScreenContext || (keyCode = Mouse.getEventButton()) == -1) {
            return;
        }
        boolean state = Mouse.getEventButtonState();
        boolean alreadyPressed = DefaultKeyMapper.isMousePressed(keyCode);
        if (alreadyPressed && !state) {
            Key key2 = DefaultKeyMapper.releaseMouse(keyCode);
            if (key2 == null) {
                return;
            }
            fireEvent(KeyEvent.State.UNPRESSED, key2);
            return;
        }
        if (!alreadyPressed && state) {
            Key key3 = DefaultKeyMapper.pressMouse(keyCode);
            if (key3 == null) {
                return;
            }
            fireEvent(KeyEvent.State.PRESS, key3);
            return;
        }
        if (!alreadyPressed || (key = DefaultKeyMapper.pressMouse(keyCode)) == null) {
            return;
        }
        fireEvent(KeyEvent.State.HOLDING, key);
    }

    public static boolean fireEvent(KeyEvent.State state, Key key) {
        if (key == null || key == Key.NONE) {
            return false;
        }
        if (key instanceof MouseButton) {
            MouseButtonEvent.Action action = null;
            switch (state) {
                case UNPRESSED:
                    action = MouseButtonEvent.Action.RELEASE;
                    break;
                case HOLDING:
                case PRESS:
                    action = MouseButtonEvent.Action.CLICK;
                    break;
            }
            if (action != null) {
                MouseButtonEvent event = new MouseButtonEvent(action, Laby.labyAPI().minecraft().absoluteMouse().mutable(), (MouseButton) key);
                if (((MouseButtonEvent) Laby.fireEvent(event)).isCancelled()) {
                    return true;
                }
            }
        }
        return ((KeyEvent) Laby.fireEvent(new KeyEvent(state, key))).isCancelled();
    }
}

package net.labymod.api.event.client.input;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.MouseButton;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/input/MouseButtonEvent.class */
public class MouseButtonEvent extends MouseEvent {
    private final Action action;
    private final MouseButton button;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/input/MouseButtonEvent$Action.class */
    public enum Action {
        CLICK,
        RELEASE
    }

    public MouseButtonEvent(Action action, MutableMouse mouse, MouseButton button) {
        super(mouse);
        this.action = action;
        this.button = button;
    }

    public Action action() {
        return this.action;
    }

    public MouseButton button() {
        return this.button;
    }
}

package net.labymod.api.event.client.input;

import net.labymod.api.client.gui.mouse.MutableMouse;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/input/MouseScrollEvent.class */
public class MouseScrollEvent extends MouseEvent {
    private final float delta;

    public MouseScrollEvent(MutableMouse mouse, float delta) {
        super(mouse);
        this.delta = delta;
    }

    public float delta() {
        return this.delta;
    }
}

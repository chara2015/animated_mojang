package net.labymod.api.event.client.input;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.MouseButton;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/input/MouseDragEvent.class */
public class MouseDragEvent extends MouseEvent {
    private final MouseButton button;
    private final double deltaX;
    private final double deltaY;

    public MouseDragEvent(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        super(mouse);
        this.button = button;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public MouseButton button() {
        return this.button;
    }

    public double deltaX() {
        return this.deltaX;
    }

    public double deltaY() {
        return this.deltaY;
    }
}

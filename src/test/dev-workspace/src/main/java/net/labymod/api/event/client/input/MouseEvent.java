package net.labymod.api.event.client.input;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/input/MouseEvent.class */
public abstract class MouseEvent extends DefaultCancellable implements Event {
    private final MutableMouse mouse;

    public MouseEvent(MutableMouse mouse) {
        this.mouse = mouse;
    }

    public MutableMouse mouse() {
        return this.mouse;
    }
}

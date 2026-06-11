package net.labymod.api.event.client.gui.screen;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/ScreenUpdateVanillaWidgetEvent.class */
public class ScreenUpdateVanillaWidgetEvent implements Event {
    private final AbstractWidget<?> widget;

    public ScreenUpdateVanillaWidgetEvent(AbstractWidget<?> widget) {
        this.widget = widget;
    }

    public AbstractWidget<?> getWidget() {
        return this.widget;
    }
}

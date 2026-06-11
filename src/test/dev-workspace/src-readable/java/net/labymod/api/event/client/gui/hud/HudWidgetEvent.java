package net.labymod.api.event.client.gui.hud;

import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/hud/HudWidgetEvent.class */
public class HudWidgetEvent implements Event {
    private final HudWidget<?> hudWidget;

    public HudWidgetEvent(@NotNull HudWidget<?> hudWidget) {
        this.hudWidget = hudWidget;
    }

    public HudWidget<?> hudWidget() {
        return this.hudWidget;
    }
}

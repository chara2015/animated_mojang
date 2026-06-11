package net.labymod.api.event.client.gui.hud;

import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/hud/HudWidgetCreatedEvent.class */
public class HudWidgetCreatedEvent extends HudWidgetEvent {
    public HudWidgetCreatedEvent(@NotNull HudWidget<?> hudWidget) {
        super(hudWidget);
    }
}

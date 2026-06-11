package net.labymod.api.event.client.gui.hud;

import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/hud/HudWidgetMovedEvent.class */
public class HudWidgetMovedEvent extends HudWidgetEvent {
    public HudWidgetMovedEvent(@NotNull HudWidget<?> hudWidget) {
        super(hudWidget);
    }
}

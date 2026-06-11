package net.labymod.api.event.client.gui.hud;

import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/hud/HudWidgetDestroyedEvent.class */
public class HudWidgetDestroyedEvent extends HudWidgetEvent {
    public HudWidgetDestroyedEvent(@NotNull HudWidget<?> hudWidget) {
        super(hudWidget);
    }
}

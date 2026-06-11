package net.labymod.api.event.client.gui.hud;

import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/hud/HudWidgetRegisterEvent.class */
public class HudWidgetRegisterEvent extends HudWidgetEvent {
    public HudWidgetRegisterEvent(@NotNull HudWidget<?> hudWidget) {
        super(hudWidget);
    }
}

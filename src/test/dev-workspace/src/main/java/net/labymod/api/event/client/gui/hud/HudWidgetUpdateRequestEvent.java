package net.labymod.api.event.client.gui.hud;

import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/hud/HudWidgetUpdateRequestEvent.class */
public class HudWidgetUpdateRequestEvent extends HudWidgetEvent {
    private final String reason;

    public HudWidgetUpdateRequestEvent(@NotNull HudWidget<?> hudWidget, @NotNull String reason) {
        super(hudWidget);
        this.reason = reason;
    }

    @Deprecated
    public HudWidgetUpdateRequestEvent(@NotNull HudWidget<?> hudWidget) {
        this(hudWidget, "unknown");
    }

    @NotNull
    public String getReason() {
        return this.reason;
    }
}

package net.labymod.api.event.client.gui.screen.theme;

import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/theme/ThemeUnregisterEvent.class */
public class ThemeUnregisterEvent extends DefaultCancellable implements Event {
    private final Theme theme;

    public ThemeUnregisterEvent(Theme theme) {
        this.theme = theme;
    }

    public Theme theme() {
        return this.theme;
    }
}

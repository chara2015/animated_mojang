package net.labymod.api.event.client.gui.screen.theme;

import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/theme/ThemeChangeEvent.class */
public class ThemeChangeEvent implements Event {
    private final Phase phase;
    private final Theme oldTheme;
    private Theme newTheme;

    public ThemeChangeEvent(Theme oldTheme, Theme newTheme, @NotNull Phase phase) {
        this.phase = phase;
        this.oldTheme = oldTheme;
        this.newTheme = newTheme;
    }

    public Phase phase() {
        return this.phase;
    }

    public Theme oldTheme() {
        return this.oldTheme;
    }

    public Theme newTheme() {
        return this.newTheme;
    }

    public void setNewTheme(Theme newTheme) {
        if (this.phase == Phase.POST) {
            throw new IllegalStateException("Cannot change theme in post phase");
        }
        this.newTheme = newTheme;
    }
}

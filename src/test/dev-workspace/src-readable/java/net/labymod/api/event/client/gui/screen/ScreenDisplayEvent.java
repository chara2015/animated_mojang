package net.labymod.api.event.client.gui.screen;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/ScreenDisplayEvent.class */
public class ScreenDisplayEvent implements Event {

    @Nullable
    private final ScreenInstance previousScreen;

    @Nullable
    private ScreenInstance screen;

    public ScreenDisplayEvent(@Nullable ScreenWrapper previousScreen, @Nullable ScreenWrapper screen) {
        this.previousScreen = previousScreen;
        this.screen = screen;
    }

    @Nullable
    public ScreenInstance getPreviousScreen() {
        return this.previousScreen;
    }

    @Nullable
    public ScreenInstance getScreen() {
        return this.screen;
    }

    public void setScreen(@Nullable ScreenInstance screen) {
        this.screen = screen;
    }
}

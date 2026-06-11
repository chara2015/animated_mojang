package net.labymod.api.event.client.gui.screen.title;

import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/title/TitleScreenSplashTextEvent.class */
public final class TitleScreenSplashTextEvent extends DefaultCancellable implements Event {
    private String splashText;

    public TitleScreenSplashTextEvent(String splashText) {
        this.splashText = splashText;
    }

    public void setSplashText(String splashText) {
        this.splashText = splashText;
    }

    public String getSplashText() {
        return this.splashText;
    }
}

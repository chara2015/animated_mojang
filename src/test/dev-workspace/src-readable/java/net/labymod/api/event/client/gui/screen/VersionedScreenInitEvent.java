package net.labymod.api.event.client.gui.screen;

import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/VersionedScreenInitEvent.class */
public class VersionedScreenInitEvent implements Event {
    private final Object versionedScreen;

    public VersionedScreenInitEvent(Object versionedScreen) {
        this.versionedScreen = versionedScreen;
    }

    public Object getVersionedScreen() {
        return this.versionedScreen;
    }
}

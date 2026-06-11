package net.labymod.api.event.labymod;

import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/LabyModRefreshEvent.class */
public class LabyModRefreshEvent implements Event {
    private final Phase phase;

    public LabyModRefreshEvent(Phase phase) {
        this.phase = phase;
    }

    public Phase phase() {
        return this.phase;
    }
}

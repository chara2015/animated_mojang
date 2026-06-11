package net.labymod.api.event.client.lifecycle;

import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/lifecycle/GameFpsLimitEvent.class */
public class GameFpsLimitEvent implements Event {
    private int framerateLimit = -1;

    public int getFramerateLimit() {
        return this.framerateLimit;
    }

    public void setFramerateLimit(int framerateLimit) {
        this.framerateLimit = framerateLimit;
    }

    @Deprecated
    public int framerateLimit() {
        return getFramerateLimit();
    }

    @Deprecated
    public void framerateLimit(int framerateLimit) {
        setFramerateLimit(framerateLimit);
    }
}

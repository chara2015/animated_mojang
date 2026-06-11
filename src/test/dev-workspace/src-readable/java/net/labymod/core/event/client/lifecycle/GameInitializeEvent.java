package net.labymod.core.event.client.lifecycle;

import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/lifecycle/GameInitializeEvent.class */
public class GameInitializeEvent implements Event {
    private final Lifecycle lifecycle;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/lifecycle/GameInitializeEvent$Lifecycle.class */
    public enum Lifecycle {
        PRE_GAME_STARTED,
        RESOURCE_INITIALIZATION,
        POST_STARTUP,
        POST_GAME_STARTED
    }

    public GameInitializeEvent(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public Lifecycle getLifecycle() {
        return this.lifecycle;
    }
}

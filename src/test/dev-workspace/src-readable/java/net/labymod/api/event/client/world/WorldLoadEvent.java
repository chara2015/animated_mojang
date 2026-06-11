package net.labymod.api.event.client.world;

import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/WorldLoadEvent.class */
public class WorldLoadEvent implements Event {
    private final Phase phase;

    public WorldLoadEvent(Phase phase) {
        this.phase = phase;
    }

    public Phase phase() {
        return this.phase;
    }
}

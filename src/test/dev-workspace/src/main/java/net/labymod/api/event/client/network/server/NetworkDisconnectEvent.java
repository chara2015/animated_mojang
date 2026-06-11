package net.labymod.api.event.client.network.server;

import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/NetworkDisconnectEvent.class */
@Deprecated
public class NetworkDisconnectEvent implements Event {
    private final Phase phase;

    public NetworkDisconnectEvent(Phase phase) {
        this.phase = phase;
    }

    public Phase phase() {
        return this.phase;
    }
}

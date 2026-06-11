package net.labymod.api.event.client.network.server;

import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/NetworkLoginEvent.class */
@Deprecated
public class NetworkLoginEvent implements Event {
    private final ServerData serverData;

    public NetworkLoginEvent(ServerData serverData) {
        this.serverData = serverData;
    }

    public ServerData serverData() {
        return this.serverData;
    }
}

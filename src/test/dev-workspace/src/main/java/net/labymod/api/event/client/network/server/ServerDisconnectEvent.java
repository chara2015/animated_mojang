package net.labymod.api.event.client.network.server;

import net.labymod.api.client.network.server.ConnectableServerData;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/ServerDisconnectEvent.class */
public class ServerDisconnectEvent extends ServerEvent {
    @Override // net.labymod.api.event.client.network.server.ServerEvent
    @NotNull
    public /* bridge */ /* synthetic */ ConnectableServerData serverData() {
        return super.serverData();
    }

    public ServerDisconnectEvent(@NotNull ConnectableServerData serverData) {
        super(serverData);
    }
}

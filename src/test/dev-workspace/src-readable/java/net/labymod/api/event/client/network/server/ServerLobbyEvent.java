package net.labymod.api.event.client.network.server;

import net.labymod.api.client.network.server.ConnectableServerData;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/ServerLobbyEvent.class */
public class ServerLobbyEvent extends ServerEvent {
    private final String lobbyName;

    @Override // net.labymod.api.event.client.network.server.ServerEvent
    @NotNull
    public /* bridge */ /* synthetic */ ConnectableServerData serverData() {
        return super.serverData();
    }

    public ServerLobbyEvent(ConnectableServerData serverData, String lobbyName) {
        super(serverData);
        this.lobbyName = lobbyName;
    }

    public String getLobbyName() {
        return this.lobbyName;
    }
}

package net.labymod.api.event.client.network.server;

import net.labymod.api.client.network.server.ConnectableServerData;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/ServerJoinEvent.class */
public class ServerJoinEvent extends ServerEvent {
    @Override // net.labymod.api.event.client.network.server.ServerEvent
    @NotNull
    public /* bridge */ /* synthetic */ ConnectableServerData serverData() {
        return super.serverData();
    }

    public ServerJoinEvent(@NotNull ConnectableServerData serverData) {
        super(serverData);
    }
}

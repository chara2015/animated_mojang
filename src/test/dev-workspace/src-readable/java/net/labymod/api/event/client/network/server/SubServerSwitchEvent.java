package net.labymod.api.event.client.network.server;

import net.labymod.api.client.network.server.ConnectableServerData;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/SubServerSwitchEvent.class */
public class SubServerSwitchEvent extends ServerEvent {
    @Override // net.labymod.api.event.client.network.server.ServerEvent
    @NotNull
    public /* bridge */ /* synthetic */ ConnectableServerData serverData() {
        return super.serverData();
    }

    public SubServerSwitchEvent(@NotNull ConnectableServerData serverData) {
        super(serverData);
    }
}

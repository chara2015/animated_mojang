package net.labymod.api.event.client.network.server;

import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/NetworkSwitchEvent.class */
@Deprecated
public class NetworkSwitchEvent implements Event {

    @Nullable
    private final ServerData previousServerData;

    @Nullable
    private final ServerData newServerData;

    public NetworkSwitchEvent(@Nullable ServerData previousServerData, @Nullable ServerData newServerData) {
        this.previousServerData = previousServerData;
        this.newServerData = newServerData;
    }

    @Nullable
    public ServerData getPreviousServerData() {
        return this.previousServerData;
    }

    @Nullable
    public ServerData getNewServerData() {
        return this.newServerData;
    }
}

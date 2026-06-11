package net.labymod.api.event.client.network.server;

import java.util.Objects;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/ServerSwitchEvent.class */
public class ServerSwitchEvent implements Event {
    private final ConnectableServerData previousServerData;
    private final ConnectableServerData newServerData;

    public ServerSwitchEvent(@NotNull ConnectableServerData previousServerData, @NotNull ConnectableServerData newServerData) {
        Objects.requireNonNull(previousServerData, "Previous server data cannot be null!");
        Objects.requireNonNull(newServerData, "New server data cannot be null!");
        this.previousServerData = previousServerData;
        this.newServerData = newServerData;
    }

    @NotNull
    public ConnectableServerData previousServerData() {
        return this.previousServerData;
    }

    @NotNull
    public ConnectableServerData newServerData() {
        return this.newServerData;
    }
}

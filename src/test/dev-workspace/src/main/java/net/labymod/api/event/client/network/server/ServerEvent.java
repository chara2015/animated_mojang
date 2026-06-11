package net.labymod.api.event.client.network.server;

import java.util.Objects;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/ServerEvent.class */
class ServerEvent implements Event {
    private final ConnectableServerData serverData;

    protected ServerEvent(@NotNull ConnectableServerData serverData) {
        Objects.requireNonNull(serverData, "Server data cannot be null!");
        this.serverData = serverData;
    }

    @NotNull
    public ConnectableServerData serverData() {
        return this.serverData;
    }
}

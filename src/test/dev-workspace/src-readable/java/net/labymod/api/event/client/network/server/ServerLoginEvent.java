package net.labymod.api.event.client.network.server;

import java.util.Optional;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.CookieStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/ServerLoginEvent.class */
public class ServerLoginEvent extends ServerEvent {
    private Component disconnectReason;
    private final CookieStorage cookieStorage;

    @Override // net.labymod.api.event.client.network.server.ServerEvent
    @NotNull
    public /* bridge */ /* synthetic */ ConnectableServerData serverData() {
        return super.serverData();
    }

    public ServerLoginEvent(@NotNull ConnectableServerData serverData) {
        this(serverData, null);
    }

    public ServerLoginEvent(@NotNull ConnectableServerData serverData, CookieStorage cookieStorage) {
        super(serverData);
        this.cookieStorage = cookieStorage;
    }

    @Nullable
    public Component getDisconnectReason() {
        return this.disconnectReason;
    }

    public void setDisconnectReason(@Nullable Component disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    public Optional<CookieStorage> cookieStorage() {
        return Optional.ofNullable(this.cookieStorage);
    }
}

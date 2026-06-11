package net.labymod.core.client.network.server.connect;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.JoinServerRequest;
import net.labymod.api.client.network.server.RejoinService;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/connect/DefaultRejoinService.class */
@Singleton
@Implements(RejoinService.class)
public class DefaultRejoinService implements RejoinService {
    private final ServerController serverController;
    private ConnectableServerData lastConnectedServerData;
    private Component lastKickReason;

    @Inject
    public DefaultRejoinService(ServerController serverController, EventBus eventBus) {
        this.serverController = serverController;
        eventBus.registerListener(this);
    }

    @Subscribe
    public void onServerKick(ServerKickEvent event) {
        this.lastConnectedServerData = event.serverData();
        this.lastKickReason = event.reason();
    }

    @Subscribe
    public void onServerDisconnect(ServerDisconnectEvent event) {
        this.lastConnectedServerData = event.serverData();
    }

    @Override // net.labymod.api.client.network.server.RejoinService
    @Nullable
    public ConnectableServerData getLastServerData() {
        return this.lastConnectedServerData;
    }

    @Override // net.labymod.api.client.network.server.RejoinService
    public boolean canRejoin() {
        return getLastServerData() != null;
    }

    @Override // net.labymod.api.client.network.server.RejoinService
    public void rejoin(Object previousScreen) {
        ConnectableServerData lastServerData = getLastServerData();
        if (lastServerData == null) {
            return;
        }
        JoinServerRequest joinServerRequest = JoinServerRequest.builder().serverData(lastServerData).screen(previousScreen).build();
        this.serverController.joinServer(joinServerRequest);
    }

    @Override // net.labymod.api.client.network.server.RejoinService
    @Nullable
    public Component getLastKickReason() {
        return this.lastKickReason;
    }
}

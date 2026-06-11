package net.labymod.api.client.network.server;

import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.network.server.global.PublicServerListService;
import net.labymod.api.client.network.server.storage.ServerList;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Phase;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerController.class */
@Referenceable
public interface ServerController {
    ServerData createServerData(Object obj);

    void setCurrentServerData(ServerData serverData);

    @Nullable
    ServerData getCurrentServerData();

    void setCurrentStorageServerData(@Nullable StorageServerData storageServerData);

    @Nullable
    StorageServerData getCurrentStorageServerData();

    void loginOrServerSwitch(ServerData serverData);

    @ApiStatus.Internal
    void disconnect(Phase phase);

    void sendPayload(ResourceLocation resourceLocation, byte[] bArr);

    boolean handleCustomPayload(PayloadChannelIdentifier payloadChannelIdentifier, byte[] bArr);

    void leaveServer();

    void joinServer(@NotNull ConnectableServerData connectableServerData, @Nullable Runnable runnable);

    void joinServer(@NotNull JoinServerRequest joinServerRequest);

    boolean isOfflineMode();

    ServerList serverList();

    ScreenWrapper createNewServerScreen(Consumer<StorageServerData> consumer);

    ScreenWrapper createEditServerScreen(StorageServerData storageServerData, Consumer<StorageServerData> consumer);

    PublicServerListService publicServerListService();

    void registerServer(AbstractServer abstractServer);

    void executeLoginExecutor();

    default boolean isConnected() {
        return getCurrentServerData() != null;
    }

    default void joinServer(@NotNull ConnectableServerData connectableServerData) {
        joinServer(connectableServerData, (Runnable) null);
    }

    default void joinServer(String host) {
        joinServer(JoinServerRequest.builder().serverData(builder -> {
            builder.address(host).lan(false);
        }).build());
    }

    default void joinServer(String address, int port) {
        joinServer(address + ":" + port);
    }
}

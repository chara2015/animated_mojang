package net.labymod.core.client.network.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import net.labymod.api.BuildData;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Namespaces;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.network.server.AbstractServer;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.global.PublicServerListService;
import net.labymod.api.client.network.server.payload.PayloadRegistry;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.api.event.client.network.server.NetworkLoginEvent;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.event.client.network.server.NetworkServerSwitchEvent;
import net.labymod.api.event.client.network.server.NetworkSwitchEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.labymod.api.event.client.network.server.ServerSwitchEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.network.server.global.DefaultPublicServerListService;
import net.labymod.core.client.network.server.servers.HypixelServer;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.serverapi.protocol.intave.packet.IntaveClientConfigPacket;
import net.labymod.core.main.user.permission.DefaultPermissionRegistry;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.serverapi.core.packet.serverbound.login.VersionLoginPacket;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/DefaultAbstractServerController.class */
public abstract class DefaultAbstractServerController implements ServerController {
    private static final ResourceLocation REGISTER_CHANNEL = ResourceLocation.create(Namespaces.MINECRAFT, "register");
    private static final Logging LOGGING = DefaultLoggingFactory.createLogger((Class<?>) ServerController.class);
    protected final Minecraft minecraft;
    private final LabyModProtocolService protocolService;
    private final PermissionRegistry permissionRegistry;
    private final ResourceLocationFactory locationFactory;

    @Nullable
    protected Runnable loginExecutor;
    private ServerData currentServerData;
    private StorageServerData currentStorageServerData;
    private boolean hasRedeemedQuickPlayCommand = false;
    private final List<AbstractServer> servers = new ArrayList();
    private final DefaultPublicServerListService publicServerListService = new DefaultPublicServerListService();

    protected abstract String getDefaultServerName();

    protected DefaultAbstractServerController(@NotNull LabyAPI labyAPI, @NotNull LabyModProtocolService protocolService, @NotNull PermissionRegistry permissionRegistry, @NotNull ResourceLocationFactory factory) {
        this.protocolService = protocolService;
        this.minecraft = labyAPI.minecraft();
        this.permissionRegistry = permissionRegistry;
        this.locationFactory = factory;
        this.publicServerListService.prepareSynchronously();
        registerServer(new HypixelServer());
    }

    @Override // net.labymod.api.client.network.server.ServerController
    @Nullable
    public StorageServerData getCurrentStorageServerData() {
        return this.currentStorageServerData;
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void setCurrentStorageServerData(@Nullable StorageServerData currentStorageServerData) {
        this.currentStorageServerData = currentStorageServerData;
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void loginOrServerSwitch(ServerData serverData) {
        AbstractServer.LoginPhase loginPhase;
        if (serverData == null) {
            registerIncomingPluginChannels();
            this.protocolService.sendLabyModPacket(new VersionLoginPacket(BuildData.version().toString()));
            return;
        }
        if (!isConnected()) {
            setCurrentServerData(serverData);
            Laby.fireEvent(new ServerJoinEvent(ConnectableServerData.from(serverData)));
            Laby.fireEvent(new NetworkLoginEvent(serverData));
            loginPhase = AbstractServer.LoginPhase.LOGIN;
        } else {
            Laby.fireEvent(new NetworkServerSwitchEvent());
            Laby.fireEvent(new SubServerSwitchEvent(ConnectableServerData.from(serverData)));
            loginPhase = AbstractServer.LoginPhase.SWITCH;
        }
        ((DefaultPermissionRegistry) this.permissionRegistry).resetPermissions();
        ServerAddress address = serverData.address();
        handleServerLoginOrSwitch(loginPhase, address);
        registerIncomingPluginChannels();
        this.protocolService.sendLabyModPacket(new VersionLoginPacket(BuildData.version().toString()));
        IntaveClientConfigPacket packet = new IntaveClientConfigPacket();
        packet.readConfig();
        LabyMod.references().intaveProtocol().sendPacket(packet);
        if (!this.hasRedeemedQuickPlayCommand) {
            for (String argument : Laby.labyAPI().labyModLoader().getArguments()) {
                if (argument.startsWith("--quickPlayCommand")) {
                    String[] split = argument.split("=");
                    if (split.length == 2) {
                        String command = split[1];
                        this.hasRedeemedQuickPlayCommand = true;
                        Task.builder(() -> {
                            this.minecraft.chatExecutor().chat(command);
                        }).delay(2L, TimeUnit.SECONDS).build().execute();
                    }
                }
            }
        }
    }

    protected void registerIncomingPluginChannels() {
        StringBuilder channelsBuilder = new StringBuilder();
        PayloadRegistry payloadRegistry = Laby.references().payloadRegistry();
        payloadRegistry.forEachRegisteredChannel(channelIdentifier -> {
            appendProtocolChannel(channelsBuilder, channelIdentifier);
        });
        sendPayload(REGISTER_CHANNEL, channelsBuilder.toString().getBytes());
    }

    private void appendProtocolChannel(StringBuilder stringBuilder, ResourceLocation identifier) {
        appendProtocolChannel(stringBuilder, identifier.getNamespace(), identifier.getPath());
    }

    private void appendProtocolChannel(StringBuilder stringBuilder, String namespace, String path) {
        stringBuilder.append(namespace).append(':').append(path).append((char) 0);
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void disconnect(Phase phase) {
        if (!isConnected()) {
            return;
        }
        if (phase == Phase.POST) {
            ServerData serverData = this.currentStorageServerData == null ? this.currentServerData : this.currentStorageServerData;
            if (serverData == null) {
                serverData = ConnectableServerData.builder().address("unknown").build();
            }
            if (LabyConfigProvider.INSTANCE.get().multiplayer().clearTitles().get().booleanValue()) {
                this.minecraft.clearTitle();
            }
            ConnectableServerData connectableServerData = ConnectableServerData.from(serverData);
            Laby.fireEvent(new ServerDisconnectEvent(connectableServerData));
        }
        Laby.fireEvent(new NetworkDisconnectEvent(phase));
        handleServerDisconnect(phase);
        ((DefaultPermissionRegistry) this.permissionRegistry).resetPermissions();
        if (phase == Phase.POST) {
            setCurrentServerData(null);
        }
    }

    @ApiStatus.Internal
    public void payloadReceive(String namespace, String path, byte[] payload) {
        payloadReceive(this.locationFactory.create(namespace, path), payload);
    }

    @ApiStatus.Internal
    public void payloadReceive(ResourceLocation identifier, byte[] payload) {
        Laby.fireEvent(NetworkPayloadEvent.createReceive(identifier, payload));
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public boolean handleCustomPayload(PayloadChannelIdentifier identifier, byte[] payload) {
        try {
            return this.protocolService.readPayload(identifier, payload);
        } catch (Throwable throwable) {
            LOGGING.error("Error occurred while handling custom payload (ChannelId: {})", identifier, throwable);
            return false;
        }
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public ServerData getCurrentServerData() {
        return this.currentServerData;
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void setCurrentServerData(ServerData currentServerData) {
        Laby.fireEvent(new NetworkSwitchEvent(this.currentServerData, currentServerData));
        if (this.currentServerData != null && currentServerData != null) {
            Laby.fireEvent(new ServerSwitchEvent(ConnectableServerData.from(this.currentServerData), ConnectableServerData.from(currentServerData)));
        }
        this.currentServerData = currentServerData;
        if (currentServerData == null) {
            setCurrentStorageServerData(null);
        }
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public ScreenWrapper createNewServerScreen(Consumer<StorageServerData> callback) {
        StorageServerData data = StorageServerData.of(getDefaultServerName(), "");
        return createEditServerScreen(data, callback);
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void registerServer(AbstractServer server) {
        this.servers.add(server);
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public PublicServerListService publicServerListService() {
        return this.publicServerListService;
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void executeLoginExecutor() {
        if (this.loginExecutor == null) {
            return;
        }
        ThreadSafe.executeOnRenderThread(() -> {
            this.loginExecutor.run();
            this.loginExecutor = null;
        });
    }

    protected final String getLegacyChannelName(String channelName) {
        if (channelName.equalsIgnoreCase("minecraft:brand")) {
            channelName = "MC|Brand";
        } else if (channelName.equalsIgnoreCase("minecraft:bopen")) {
            channelName = "MC|BOpen";
        } else if (channelName.equalsIgnoreCase("minecraft:trilist")) {
            channelName = "MC|TriList";
        } else if (channelName.equalsIgnoreCase("minecraft:register")) {
            channelName = "REGISTER";
        } else if (channelName.equalsIgnoreCase("minecraft:unregister")) {
            channelName = "UNREGISTER";
        }
        return channelName;
    }

    private void handleServerLoginOrSwitch(AbstractServer.LoginPhase phase, ServerAddress address) {
        for (AbstractServer server : this.servers) {
            if (server.isServer(address)) {
                server.loginOrSwitch(phase);
            }
        }
    }

    private void handleServerDisconnect(Phase phase) {
        for (AbstractServer server : this.servers) {
            if (this.currentServerData != null && this.currentServerData.address() != null) {
                if (server.isServer(this.currentServerData.address())) {
                    server.disconnect(phase);
                }
            } else {
                return;
            }
        }
    }
}

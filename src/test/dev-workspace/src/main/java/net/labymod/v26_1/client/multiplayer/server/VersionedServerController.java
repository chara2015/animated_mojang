package net.labymod.v26_1.client.multiplayer.server;

import com.mojang.realmsclient.RealmsMainScreen;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.JoinServerRequest;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.network.server.storage.ServerList;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.client.network.server.DefaultAbstractServerController;
import net.labymod.v26_1.client.multiplayer.server.storage.VersionedServerList;
import net.labymod.v26_1.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.ManageServerScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/multiplayer/server/VersionedServerController.class */
@Singleton
@Implements(ServerController.class)
public class VersionedServerController extends DefaultAbstractServerController {
    private final ComponentMapper componentMapper;
    private final ScreenWrapper.Factory screenWrapperFactory;
    private VersionedServerList serverList;
    private ServerData connectingServerData;

    @Inject
    public VersionedServerController(@NotNull LabyAPI labyAPI, @NotNull LabyModProtocolService protocolService, @NotNull PermissionRegistry permissionRegistry, @NotNull ResourceLocationFactory factory, @NotNull ComponentMapper componentMapper) {
        super(labyAPI, protocolService, permissionRegistry, factory);
        this.componentMapper = componentMapper;
        this.screenWrapperFactory = Laby.references().screenWrapperFactory();
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public ServerData createServerData(Object mojangServerData) {
        if (mojangServerData == null) {
            return null;
        }
        if (!(mojangServerData instanceof net.minecraft.client.multiplayer.ServerData)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), net.minecraft.client.multiplayer.ServerData.class.getName()));
        }
        net.minecraft.client.multiplayer.ServerData serverData = (net.minecraft.client.multiplayer.ServerData) mojangServerData;
        if (mojangServerData instanceof VersionedServerData) {
            VersionedServerData versionedServerData = (VersionedServerData) mojangServerData;
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(serverData.name).serverAddress(serverData.ip).type(MinecraftUtil.fromMinecraft(serverData)).icon(serverData.getIconBytes() == null ? null : new String(serverData.getIconBytes())).ping((int) serverData.ping).protocolVersion(serverData.protocol).description(fromMinecraftComponent(serverData.motd)).build();
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void sendPayload(ResourceLocation identifier, byte[] payload) {
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null) {
            return;
        }
        NetworkPayloadEvent event = NetworkPayloadEvent.createSend(identifier, payload);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        sendCustomPayload(connection, identifier, payload);
    }

    private void sendCustomPayload(@NotNull ClientPacketListener connection, @NotNull ResourceLocation identifier, byte[] payload) {
        connection.send(new ServerboundCustomPayloadPacket(new LabyModCustomPacketPayload(identifier, payload)));
    }

    private Component fromMinecraftComponent(net.minecraft.network.chat.Component component) {
        if (component == null) {
            return Component.text("");
        }
        return this.componentMapper.fromMinecraftComponent(component);
    }

    private List<Component> fromMinecraftComponentCollection(@Nullable List<net.minecraft.network.chat.Component> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList();
        }
        List<Component> result = new ArrayList<>(list.size());
        for (net.minecraft.network.chat.Component component : list) {
            Component fromMinecraftComponent = fromMinecraftComponent(component);
            result.add(fromMinecraftComponent);
        }
        return result;
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void leaveServer() {
        Screen display;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }
        boolean singleplayer = minecraft.isLocalServer();
        net.minecraft.client.multiplayer.ServerData currentServer = minecraft.getCurrentServer();
        boolean realms = currentServer != null && currentServer.isRealm();
        minecraft.level.disconnect(ClientLevel.DEFAULT_QUIT_MESSAGE);
        if (singleplayer) {
            minecraft.disconnectWithSavingScreen();
        } else {
            minecraft.disconnectWithProgressScreen();
        }
        Screen titleScreen = new TitleScreen();
        if (singleplayer) {
            display = titleScreen;
        } else if (realms) {
            display = new RealmsMainScreen(titleScreen);
        } else {
            display = new JoinMultiplayerScreen(titleScreen);
        }
        minecraft.setScreen(display);
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void joinServer(@NotNull ConnectableServerData connectableServerData, @Nullable Runnable loginExecutor) {
        joinServer(JoinServerRequest.builder().serverData(connectableServerData).loginExecutor(loginExecutor).build());
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void joinServer(@NotNull JoinServerRequest request) {
        ThreadSafe.ensureRenderThread();
        ConnectableServerData connectableServerData = request.serverData();
        Objects.requireNonNull(connectableServerData, "Server data must not be null!");
        leaveServer();
        this.loginExecutor = request.loginExecutor();
        if (connectableServerData instanceof StorageServerData) {
            StorageServerData storageServerData = (StorageServerData) connectableServerData;
            setCurrentStorageServerData(storageServerData);
        }
        this.connectingServerData = connectableServerData;
        VersionedServerData serverData = new VersionedServerData(connectableServerData);
        Screen screen = Minecraft.getInstance().screen;
        Object requestedScreen = request.screen();
        if (requestedScreen instanceof Screen) {
            Screen screenInstance = (Screen) requestedScreen;
            screen = screenInstance;
        }
        ConnectScreen.startConnecting(screen, Minecraft.getInstance(), ServerAddress.parseString(serverData.ip), serverData, false, (TransferState) null);
        if (Minecraft.getInstance().screen instanceof DisconnectedScreen) {
            this.connectingServerData = null;
        }
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public boolean isOfflineMode() {
        if (Minecraft.getInstance().isLocalServer()) {
            return false;
        }
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        return connection == null || !connection.getConnection().isEncrypted();
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public ServerList serverList() {
        if (this.serverList == null) {
            this.serverList = new VersionedServerList();
        }
        return this.serverList;
    }

    @Override // net.labymod.core.client.network.server.DefaultAbstractServerController
    protected String getDefaultServerName() {
        return I18n.get("selectServer.defaultName", new Object[0]);
    }

    @Override // net.labymod.core.client.network.server.DefaultAbstractServerController, net.labymod.api.client.network.server.ServerController
    public ScreenWrapper createNewServerScreen(Consumer<StorageServerData> callback) {
        StorageServerData data = StorageServerData.of(getDefaultServerName(), "");
        return this.screenWrapperFactory.create(createManageServer(data, "manageServer.add.title", callback));
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public ScreenWrapper createEditServerScreen(StorageServerData data, Consumer<StorageServerData> callback) {
        return this.screenWrapperFactory.create(createManageServer(data, "manageServer.edit.title", callback));
    }

    private Screen createManageServer(StorageServerData data, String translationKey, Consumer<StorageServerData> callback) {
        Screen prevScreen = Minecraft.getInstance().screen;
        net.minecraft.client.multiplayer.ServerData mcData = new net.minecraft.client.multiplayer.ServerData(data.getName(), data.address().toString(), MinecraftUtil.toMinecraft(data.type()));
        mcData.setResourcePackStatus(ServerData.ServerPackStatus.values()[data.resourcePackStatus().ordinal()]);
        return new ManageServerScreen(prevScreen, net.minecraft.network.chat.Component.translatable(translationKey), addServer -> {
            if (addServer) {
                data.setName(mcData.name);
                data.setServerAddress(mcData.ip);
                data.setResourcePackStatus(ServerResourcePackStatus.VALUES[mcData.getResourcePackStatus().ordinal()]);
                callback.accept(data);
            }
            Minecraft.getInstance().setScreen(prevScreen);
        }, mcData);
    }

    @Override // net.labymod.core.client.network.server.DefaultAbstractServerController, net.labymod.api.client.network.server.ServerController
    public void setCurrentServerData(net.labymod.api.client.network.server.ServerData currentServerData) {
        super.setCurrentServerData(currentServerData);
        if (currentServerData == null) {
            this.connectingServerData = null;
        }
    }

    public net.labymod.api.client.network.server.ServerData getConnectingServerData() {
        return this.connectingServerData;
    }
}

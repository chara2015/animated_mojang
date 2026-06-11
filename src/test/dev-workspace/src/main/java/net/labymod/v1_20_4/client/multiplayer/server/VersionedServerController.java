package net.labymod.v1_20_4.client.multiplayer.server;

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
import net.labymod.v1_20_4.client.multiplayer.server.custompayload.LabyModCustomPacketPayload;
import net.labymod.v1_20_4.client.multiplayer.server.storage.VersionedServerList;
import net.labymod.v1_20_4.client.util.MinecraftUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/multiplayer/server/VersionedServerController.class */
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
        if (!(mojangServerData instanceof fod)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), fod.class.getName()));
        }
        fod serverData = (fod) mojangServerData;
        if (mojangServerData instanceof VersionedServerData) {
            VersionedServerData versionedServerData = (VersionedServerData) mojangServerData;
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(serverData.a).serverAddress(serverData.b).type(MinecraftUtil.fromMinecraft(serverData)).icon(serverData.c() == null ? null : new String(serverData.c())).ping((int) serverData.f).protocolVersion(serverData.g).description(fromMinecraftComponent(serverData.d)).build();
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void sendPayload(ResourceLocation identifier, byte[] payload) {
        fnt connection = evi.O().J();
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

    private void sendCustomPayload(@NotNull fnt connection, @NotNull ResourceLocation identifier, byte[] payload) {
        LabyModCustomPacketPayload customPacketPayload = LabyModCustomPacketPayload.of(identifier, payload);
        connection.b(new xs(customPacketPayload));
    }

    private Component fromMinecraftComponent(vf component) {
        if (component == null) {
            return Component.text("");
        }
        return this.componentMapper.fromMinecraftComponent(component);
    }

    private List<Component> fromMinecraftComponentCollection(@Nullable List<vf> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList();
        }
        List<Component> result = new ArrayList<>(list.size());
        for (vf component : list) {
            Component fromMinecraftComponent = fromMinecraftComponent(component);
            result.add(fromMinecraftComponent);
        }
        return result;
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void leaveServer() {
        fdb display;
        evi minecraft = evi.O();
        if (minecraft.r == null) {
            return;
        }
        boolean singleplayer = minecraft.R();
        fod currentServer = minecraft.Q();
        boolean realms = currentServer != null && currentServer.e();
        minecraft.r.W();
        if (singleplayer) {
            minecraft.b(new fch((vf) this.componentMapper.toMinecraftComponent(Component.translatable("menu.savingLevel", new Component[0]))));
        } else {
            minecraft.y();
        }
        fdb fdgVar = new fdg();
        if (singleplayer) {
            display = fdgVar;
        } else if (realms) {
            display = new eqm(fdgVar);
        } else {
            display = new ffz(fdgVar);
        }
        minecraft.a(display);
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
        fdb screen = evi.O().y;
        Object requestedScreen = request.screen();
        if (requestedScreen instanceof fdb) {
            fdb screenInstance = (fdb) requestedScreen;
            screen = screenInstance;
        }
        fbv.a(screen, evi.O(), fpf.a(serverData.b), serverData, false);
        if (evi.O().y instanceof fcd) {
            this.connectingServerData = null;
        }
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public boolean isOfflineMode() {
        if (evi.O().R()) {
            return false;
        }
        fnt connection = evi.O().J();
        return connection == null || !connection.m().j();
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
        return gfs.a("selectServer.defaultName", new Object[0]);
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public ScreenWrapper createEditServerScreen(StorageServerData data, Consumer<StorageServerData> callback) {
        fdb prevScreen = evi.O().y;
        fod mcData = new fod(data.getName(), data.address().toString(), MinecraftUtil.toMinecraft(data.type()));
        mcData.a(a.values()[data.resourcePackStatus().ordinal()]);
        fce screen = new fce(prevScreen, addServer -> {
            if (addServer) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.VALUES[mcData.b().ordinal()]);
                callback.accept(data);
            }
            evi.O().a(prevScreen);
        }, mcData);
        return this.screenWrapperFactory.create(screen);
    }

    @Override // net.labymod.core.client.network.server.DefaultAbstractServerController, net.labymod.api.client.network.server.ServerController
    public void setCurrentServerData(ServerData currentServerData) {
        super.setCurrentServerData(currentServerData);
        if (currentServerData == null) {
            this.connectingServerData = null;
        }
    }

    public ServerData getConnectingServerData() {
        return this.connectingServerData;
    }
}

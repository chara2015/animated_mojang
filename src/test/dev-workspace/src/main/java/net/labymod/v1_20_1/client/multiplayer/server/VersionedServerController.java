package net.labymod.v1_20_1.client.multiplayer.server;

import io.netty.buffer.Unpooled;
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
import net.labymod.v1_20_1.client.multiplayer.server.storage.VersionedServerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/multiplayer/server/VersionedServerController.class */
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
        if (!(mojangServerData instanceof ffd)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), ffd.class.getName()));
        }
        ffd serverData = (ffd) mojangServerData;
        if (mojangServerData instanceof VersionedServerData) {
            VersionedServerData versionedServerData = (VersionedServerData) mojangServerData;
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(serverData.a).serverAddress(serverData.b).lan(serverData.d()).icon(serverData.c() == null ? null : new String(serverData.c())).ping((int) serverData.f).protocolVersion(serverData.g).description(fromMinecraftComponent(serverData.d)).build();
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void sendPayload(ResourceLocation identifier, byte[] payload) {
        fex connection = enn.N().I();
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

    private void sendCustomPayload(@NotNull fex connection, @NotNull ResourceLocation identifier, byte[] payload) {
        connection.a(new zq((acq) identifier.getMinecraftLocation(), new sf(Unpooled.wrappedBuffer(payload))));
    }

    private Component fromMinecraftComponent(sw component) {
        if (component == null) {
            return Component.text("");
        }
        return this.componentMapper.fromMinecraftComponent(component);
    }

    private List<Component> fromMinecraftComponentCollection(@Nullable List<sw> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList();
        }
        List<Component> result = new ArrayList<>(list.size());
        for (sw component : list) {
            Component fromMinecraftComponent = fromMinecraftComponent(component);
            result.add(fromMinecraftComponent);
        }
        return result;
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void leaveServer() {
        euq display;
        enn minecraft = enn.N();
        if (minecraft.s == null) {
            return;
        }
        boolean singleplayer = minecraft.Q();
        boolean realms = minecraft.ar();
        minecraft.s.U();
        if (singleplayer) {
            minecraft.b(new etx((sw) this.componentMapper.toMinecraftComponent(Component.translatable("menu.savingLevel", new Component[0]))));
        } else {
            minecraft.y();
        }
        euq euwVar = new euw();
        if (singleplayer) {
            display = euwVar;
        } else if (realms) {
            display = new eiu(euwVar);
        } else {
            display = new exn(euwVar);
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
        euq screen = enn.N().z;
        Object requestedScreen = request.screen();
        if (requestedScreen instanceof euq) {
            euq screenInstance = (euq) requestedScreen;
            screen = screenInstance;
        }
        etl.a(screen, enn.N(), fga.a(serverData.b), serverData, false);
        if (enn.N().z instanceof ett) {
            this.connectingServerData = null;
        }
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public boolean isOfflineMode() {
        if (enn.N().Q()) {
            return false;
        }
        fex connection = enn.N().I();
        return connection == null || !connection.g().g();
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
        return fvz.a("selectServer.defaultName", new Object[0]);
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public ScreenWrapper createEditServerScreen(StorageServerData data, Consumer<StorageServerData> callback) {
        euq prevScreen = enn.N().z;
        ffd mcData = new ffd(data.getName(), data.address().toString(), data.isLan());
        mcData.a(a.values()[data.resourcePackStatus().ordinal()]);
        etu screen = new etu(prevScreen, addServer -> {
            if (addServer) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.VALUES[mcData.b().ordinal()]);
                callback.accept(data);
            }
            enn.N().a(prevScreen);
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

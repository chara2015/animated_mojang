package net.labymod.v1_21_5.client.multiplayer.server;

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
import net.labymod.v1_21_5.client.multiplayer.server.storage.VersionedServerList;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/multiplayer/server/VersionedServerController.class */
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
        if (!(mojangServerData instanceof gmd)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), gmd.class.getName()));
        }
        gmd serverData = (gmd) mojangServerData;
        if (mojangServerData instanceof VersionedServerData) {
            VersionedServerData versionedServerData = (VersionedServerData) mojangServerData;
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(serverData.a).serverAddress(serverData.b).type(MinecraftUtil.fromMinecraft(serverData)).icon(serverData.c() == null ? null : new String(serverData.c())).ping((int) serverData.f).protocolVersion(serverData.g).description(fromMinecraftComponent(serverData.d)).build();
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void sendPayload(ResourceLocation identifier, byte[] payload) {
        glp connection = fqq.Q().L();
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

    private void sendCustomPayload(@NotNull glp connection, @NotNull ResourceLocation identifier, byte[] payload) {
        connection.b(new aal(new LabyModCustomPacketPayload(identifier, payload)));
    }

    private Component fromMinecraftComponent(xg component) {
        if (component == null) {
            return Component.text("");
        }
        return this.componentMapper.fromMinecraftComponent(component);
    }

    private List<Component> fromMinecraftComponentCollection(@Nullable List<xg> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList();
        }
        List<Component> result = new ArrayList<>(list.size());
        for (xg component : list) {
            Component fromMinecraftComponent = fromMinecraftComponent(component);
            result.add(fromMinecraftComponent);
        }
        return result;
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void leaveServer() {
        fzq display;
        fqq minecraft = fqq.Q();
        if (minecraft.s == null) {
            return;
        }
        boolean singleplayer = minecraft.T();
        gmd currentServer = minecraft.S();
        boolean realms = currentServer != null && currentServer.e();
        minecraft.s.ad();
        if (singleplayer) {
            minecraft.b(new fzb((xg) this.componentMapper.toMinecraftComponent(Component.translatable("menu.savingLevel", new Component[0]))));
        } else {
            minecraft.y();
        }
        fzq fzsVar = new fzs();
        if (singleplayer) {
            display = fzsVar;
        } else if (realms) {
            display = new fll(fzsVar);
        } else {
            display = new gci(fzsVar);
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
        fzq screen = fqq.Q().z;
        Object requestedScreen = request.screen();
        if (requestedScreen instanceof fzq) {
            fzq screenInstance = (fzq) requestedScreen;
            screen = screenInstance;
        }
        fyp.a(screen, fqq.Q(), gng.a(serverData.b), serverData, false, (gmh) null);
        if (fqq.Q().z instanceof fyx) {
            this.connectingServerData = null;
        }
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public boolean isOfflineMode() {
        if (fqq.Q().T()) {
            return false;
        }
        glp connection = fqq.Q().L();
        return connection == null || !connection.j().h();
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
        return hly.a("selectServer.defaultName", new Object[0]);
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public ScreenWrapper createEditServerScreen(StorageServerData data, Consumer<StorageServerData> callback) {
        fzq prevScreen = fqq.Q().z;
        gmd mcData = new gmd(data.getName(), data.address().toString(), MinecraftUtil.toMinecraft(data.type()));
        mcData.a(a.values()[data.resourcePackStatus().ordinal()]);
        fyy screen = new fyy(prevScreen, addServer -> {
            if (addServer) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.VALUES[mcData.b().ordinal()]);
                callback.accept(data);
            }
            fqq.Q().a(prevScreen);
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

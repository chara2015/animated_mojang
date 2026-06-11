package net.labymod.v1_21_10.client.multiplayer.server;

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
import net.labymod.v1_21_10.client.multiplayer.server.storage.VersionedServerList;
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/multiplayer/server/VersionedServerController.class */
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
        if (!(mojangServerData instanceof hab)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), hab.class.getName()));
        }
        hab serverData = (hab) mojangServerData;
        if (mojangServerData instanceof VersionedServerData) {
            VersionedServerData versionedServerData = (VersionedServerData) mojangServerData;
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(serverData.a).serverAddress(serverData.b).type(MinecraftUtil.fromMinecraft(serverData)).icon(serverData.c() == null ? null : new String(serverData.c())).ping((int) serverData.f).protocolVersion(serverData.g).description(fromMinecraftComponent(serverData.d)).build();
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void sendPayload(ResourceLocation identifier, byte[] payload) {
        gzo connection = fzz.W().R();
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

    private void sendCustomPayload(@NotNull gzo connection, @NotNull ResourceLocation identifier, byte[] payload) {
        connection.b(new abo(new LabyModCustomPacketPayload(identifier, payload)));
    }

    private Component fromMinecraftComponent(xx component) {
        if (component == null) {
            return Component.text("");
        }
        return this.componentMapper.fromMinecraftComponent(component);
    }

    private List<Component> fromMinecraftComponentCollection(@Nullable List<xx> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList();
        }
        List<Component> result = new ArrayList<>(list.size());
        for (xx component : list) {
            Component fromMinecraftComponent = fromMinecraftComponent(component);
            result.add(fromMinecraftComponent);
        }
        return result;
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void leaveServer() {
        gmj display;
        fzz minecraft = fzz.W();
        if (minecraft.r == null) {
            return;
        }
        boolean singleplayer = minecraft.Z();
        hab currentServer = minecraft.Y();
        boolean realms = currentServer != null && currentServer.e();
        minecraft.r.a(gzn.a);
        if (singleplayer) {
            minecraft.C();
        } else {
            minecraft.D();
        }
        gmj gmlVar = new gml();
        if (singleplayer) {
            display = gmlVar;
        } else if (realms) {
            display = new fui(gmlVar);
        } else {
            display = new gpv(gmlVar);
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
        gmj screen = fzz.W().x;
        Object requestedScreen = request.screen();
        if (requestedScreen instanceof gmj) {
            gmj screenInstance = (gmj) requestedScreen;
            screen = screenInstance;
        }
        glj.a(screen, fzz.W(), hbe.a(serverData.b), serverData, false, (haf) null);
        if (fzz.W().x instanceof glr) {
            this.connectingServerData = null;
        }
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public boolean isOfflineMode() {
        if (fzz.W().Z()) {
            return false;
        }
        gzo connection = fzz.W().R();
        return connection == null || !connection.m().h();
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
        return idt.a("selectServer.defaultName", new Object[0]);
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

    private gmj createManageServer(StorageServerData data, String translationKey, Consumer<StorageServerData> callback) {
        gmj prevScreen = fzz.W().x;
        hab mcData = new hab(data.getName(), data.address().toString(), MinecraftUtil.toMinecraft(data.type()));
        mcData.a(a.values()[data.resourcePackStatus().ordinal()]);
        return new gma(prevScreen, xx.c(translationKey), addServer -> {
            if (addServer) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.VALUES[mcData.b().ordinal()]);
                callback.accept(data);
            }
            fzz.W().a(prevScreen);
        }, mcData);
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

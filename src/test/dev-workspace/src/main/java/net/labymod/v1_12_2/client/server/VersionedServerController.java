package net.labymod.v1_12_2.client.server;

import io.netty.buffer.Unpooled;
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
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.client.network.server.DefaultAbstractServerController;
import net.labymod.v1_12_2.client.gui.screen.VersionedFunctionalConfirmScreen;
import net.labymod.v1_12_2.client.server.storage.VersionedServerList;
import net.minecraft.realms.RealmsBridge;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/server/VersionedServerController.class */
@Singleton
@Implements(ServerController.class)
public class VersionedServerController extends DefaultAbstractServerController {
    private final ScreenWrapper.Factory screenWrapperFactory;
    private VersionedServerList labyMod$serverList;

    @Inject
    public VersionedServerController(@NotNull LabyAPI labyAPI, @NotNull LabyModProtocolService protocolService, @NotNull PermissionRegistry permissionRegistry, @NotNull ResourceLocationFactory factory) {
        super(labyAPI, protocolService, permissionRegistry, factory);
        this.screenWrapperFactory = Laby.references().screenWrapperFactory();
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public ServerData createServerData(Object mojangServerData) {
        if (mojangServerData == null) {
            return null;
        }
        if (!(mojangServerData instanceof bse)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), bse.class.getName()));
        }
        bse sd = (bse) mojangServerData;
        if (mojangServerData instanceof VersionedServerData) {
            return ((VersionedServerData) mojangServerData).getServerData();
        }
        return ServerInfo.infoBuilder().name(sd.a).serverAddress(sd.b).lan(sd.d()).icon(sd.c()).ping((int) sd.e).protocolVersion(sd.f).protocolInfo(nullCheck(sd.g)).description(Component.text(nullCheck(sd.d))).build();
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void sendPayload(ResourceLocation identifier, byte[] payload) {
        brz connection = bib.z().v();
        if (connection == null) {
            return;
        }
        NetworkPayloadEvent event = (NetworkPayloadEvent) Laby.fireEvent(NetworkPayloadEvent.createSend(identifier, payload));
        if (event.isCancelled()) {
            return;
        }
        ResourceLocation identifier2 = event.identifier();
        byte[] payload2 = event.getPayload();
        String channelName = identifier2.toString();
        connection.a(new lh(getLegacyChannelName(channelName), new gy(Unpooled.wrappedBuffer(payload2))));
    }

    @Contract(value = "!null -> param1", pure = true)
    @NotNull
    private String nullCheck(String value) {
        return value == null ? "" : value;
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public void leaveServer() {
        bib minecraft = bib.z();
        if (minecraft.f == null) {
            return;
        }
        boolean singleplayer = minecraft.D();
        boolean realms = minecraft.ah();
        minecraft.f.O();
        minecraft.a((bsb) null);
        if (singleplayer) {
            minecraft.a(new blr());
        } else if (realms) {
            RealmsBridge bridge = new RealmsBridge();
            bridge.switchToRealms(new blr());
        } else {
            minecraft.a(new bnf(new blr()));
        }
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
            setCurrentStorageServerData((StorageServerData) connectableServerData);
        }
        VersionedServerData serverData = new VersionedServerData(connectableServerData);
        blk screen = bib.z().m;
        Object requestedScreen = request.screen();
        if (requestedScreen instanceof blk) {
            blk screenInstance = (blk) requestedScreen;
            screen = screenInstance;
        }
        bkr connecting = new bkr(screen, bib.z(), serverData);
        if (connecting.h) {
            return;
        }
        bib.z().a(connecting);
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public boolean isOfflineMode() {
        if (bib.z().D()) {
            return false;
        }
        brz connection = bib.z().v();
        return connection == null || !connection.a().f();
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public ServerList serverList() {
        if (this.labyMod$serverList == null) {
            this.labyMod$serverList = new VersionedServerList();
        }
        return this.labyMod$serverList;
    }

    @Override // net.labymod.core.client.network.server.DefaultAbstractServerController
    protected String getDefaultServerName() {
        return cey.a("selectServer.defaultName", new Object[0]);
    }

    @Override // net.labymod.api.client.network.server.ServerController
    public ScreenWrapper createEditServerScreen(StorageServerData data, Consumer<StorageServerData> callback) {
        blk prevScreen = bib.z().m;
        bse mcData = new bse(data.getName(), data.address().toString(), data.isLan());
        mcData.a(a.values()[data.resourcePackStatus().ordinal()]);
        bkz screen = new bkz(new VersionedFunctionalConfirmScreen(0, addServer -> {
            if (addServer.booleanValue()) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.values()[mcData.b().ordinal()]);
                callback.accept(data);
            }
            bib.z().a(prevScreen);
        }), mcData);
        return this.screenWrapperFactory.create(screen);
    }
}

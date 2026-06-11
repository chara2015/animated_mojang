package net.labymod.v1_21_4.client.multiplayer.server;

import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.v1_21_4.client.util.MinecraftUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/multiplayer/server/VersionedServerData.class */
public class VersionedServerData extends ggp {
    private final ServerData serverData;

    public VersionedServerData(ServerData serverData) {
        super(serverData.getName(), serverData.address().toString(), MinecraftUtil.toMinecraft(serverData.type()));
        if (serverData instanceof ConnectableServerData) {
            ConnectableServerData connectableServerData = (ConnectableServerData) serverData;
            a(of(connectableServerData.resourcePackStatus()));
        }
        this.serverData = serverData;
    }

    public void a(@NotNull a packStatus) {
        super.a(packStatus);
        ServerData serverData = this.serverData;
        if (serverData instanceof StorageServerData) {
            StorageServerData storageServerData = (StorageServerData) serverData;
            storageServerData.setResourcePackStatus(of(packStatus));
        }
    }

    public ServerData getServerData() {
        return this.serverData;
    }

    public static a of(ServerResourcePackStatus resourcePackStatus) {
        switch (resourcePackStatus) {
            case ENABLED:
                return a.a;
            case DISABLED:
                return a.b;
            default:
                return a.c;
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_4.client.multiplayer.server.VersionedServerData$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/multiplayer/server/VersionedServerData$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerPackStatus = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerPackStatus[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerPackStatus[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SwitchMap$net$labymod$api$client$network$server$storage$ServerResourcePackStatus = new int[ServerResourcePackStatus.values().length];
            try {
                $SwitchMap$net$labymod$api$client$network$server$storage$ServerResourcePackStatus[ServerResourcePackStatus.ENABLED.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$api$client$network$server$storage$ServerResourcePackStatus[ServerResourcePackStatus.DISABLED.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static ServerResourcePackStatus of(a resourceMode) {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerPackStatus[resourceMode.ordinal()]) {
            case 1:
                return ServerResourcePackStatus.ENABLED;
            case 2:
                return ServerResourcePackStatus.DISABLED;
            default:
                return ServerResourcePackStatus.PROMPT;
        }
    }
}

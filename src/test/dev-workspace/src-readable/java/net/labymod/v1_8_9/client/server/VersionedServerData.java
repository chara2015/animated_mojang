package net.labymod.v1_8_9.client.server;

import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/server/VersionedServerData.class */
public class VersionedServerData extends bde {
    private final ServerData serverData;

    public VersionedServerData(ServerData serverData) {
        super(serverData.getName(), serverData.address().toString(), serverData.isLan());
        if (serverData instanceof ConnectableServerData) {
            a(of(((ConnectableServerData) serverData).resourcePackStatus()));
        }
        this.serverData = serverData;
    }

    public void a(a resourceMode) {
        super.a(resourceMode);
        if (this.serverData instanceof StorageServerData) {
            ((StorageServerData) this.serverData).setResourcePackStatus(of(resourceMode));
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

    /* JADX INFO: renamed from: net.labymod.v1_8_9.client.server.VersionedServerData$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/server/VersionedServerData$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerResourceMode = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerResourceMode[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerResourceMode[a.b.ordinal()] = 2;
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
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerResourceMode[resourceMode.ordinal()]) {
            case 1:
                return ServerResourcePackStatus.ENABLED;
            case 2:
                return ServerResourcePackStatus.DISABLED;
            default:
                return ServerResourcePackStatus.PROMPT;
        }
    }
}

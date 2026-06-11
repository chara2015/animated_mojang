package net.labymod.v26_1_2.client.multiplayer.server;

import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.v26_1_2.client.util.MinecraftUtil;
import net.minecraft.client.multiplayer.ServerData;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/multiplayer/server/VersionedServerData.class */
public class VersionedServerData extends ServerData {
    private final net.labymod.api.client.network.server.ServerData serverData;

    public VersionedServerData(net.labymod.api.client.network.server.ServerData serverData) {
        super(serverData.getName(), serverData.address().toString(), MinecraftUtil.toMinecraft(serverData.type()));
        if (serverData instanceof ConnectableServerData) {
            ConnectableServerData connectableServerData = (ConnectableServerData) serverData;
            setResourcePackStatus(of(connectableServerData.resourcePackStatus()));
        }
        this.serverData = serverData;
        if (serverData instanceof StorageServerData) {
            StorageServerData storageServerData = (StorageServerData) serverData;
            this.acceptedCodeOfConduct = storageServerData.getAcceptedCodeOfConduct();
        }
    }

    public void acceptCodeOfConduct(@NotNull String codeOfConduct) {
        super.acceptCodeOfConduct(codeOfConduct);
        net.labymod.api.client.network.server.ServerData serverData = this.serverData;
        if (serverData instanceof StorageServerData) {
            StorageServerData storageServerData = (StorageServerData) serverData;
            storageServerData.setAcceptedCodeOfConduct(this.acceptedCodeOfConduct);
        }
    }

    public void clearCodeOfConduct() {
        super.clearCodeOfConduct();
        net.labymod.api.client.network.server.ServerData serverData = this.serverData;
        if (serverData instanceof StorageServerData) {
            StorageServerData storageServerData = (StorageServerData) serverData;
            storageServerData.setAcceptedCodeOfConduct(this.acceptedCodeOfConduct);
        }
    }

    public void setResourcePackStatus(@NotNull ServerData.ServerPackStatus packStatus) {
        super.setResourcePackStatus(packStatus);
        net.labymod.api.client.network.server.ServerData serverData = this.serverData;
        if (serverData instanceof StorageServerData) {
            StorageServerData storageServerData = (StorageServerData) serverData;
            storageServerData.setResourcePackStatus(of(packStatus));
        }
    }

    public net.labymod.api.client.network.server.ServerData getServerData() {
        return this.serverData;
    }

    public static ServerData.ServerPackStatus of(ServerResourcePackStatus resourcePackStatus) {
        switch (resourcePackStatus) {
            case ENABLED:
                return ServerData.ServerPackStatus.ENABLED;
            case DISABLED:
                return ServerData.ServerPackStatus.DISABLED;
            default:
                return ServerData.ServerPackStatus.PROMPT;
        }
    }

    /* JADX INFO: renamed from: net.labymod.v26_1_2.client.multiplayer.server.VersionedServerData$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/multiplayer/server/VersionedServerData$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerPackStatus = new int[ServerData.ServerPackStatus.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerPackStatus[ServerData.ServerPackStatus.ENABLED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerPackStatus[ServerData.ServerPackStatus.DISABLED.ordinal()] = 2;
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

    public static ServerResourcePackStatus of(ServerData.ServerPackStatus resourceMode) {
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

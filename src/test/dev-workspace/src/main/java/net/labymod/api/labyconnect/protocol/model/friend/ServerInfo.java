package net.labymod.api.labyconnect.protocol.model.friend;

import net.labymod.api.util.AddressUtil;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/protocol/model/friend/ServerInfo.class */
public interface ServerInfo {
    int getPort();

    String getAddress();

    @Nullable
    String getGameModeName();

    boolean isSameServer(ServerInfo serverInfo);

    default String getDisplayInfo() {
        int port = getPort();
        if (port == 25565) {
            return getAddress();
        }
        return getAddress() + ":" + port;
    }

    default boolean isLocalHost() {
        return AddressUtil.isLocalHost(getAddress());
    }
}

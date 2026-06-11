package net.labymod.api.client.network;

import java.util.Collection;
import java.util.UUID;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/ClientPacketListener.class */
public interface ClientPacketListener {
    @Nullable
    NetworkPlayerInfo getNetworkPlayerInfo(UUID uuid);

    @Nullable
    NetworkPlayerInfo getNetworkPlayerInfo(String str);

    Collection<NetworkPlayerInfo> getNetworkPlayerInfos();

    NettyConnectionAccessor getNettyConnection();

    void simulateKick(Component component);

    default Collection<NetworkPlayerInfo> getShownOnlinePlayers() {
        return getNetworkPlayerInfos();
    }

    default int getPlayerCount() {
        return getShownOnlinePlayers().size();
    }
}

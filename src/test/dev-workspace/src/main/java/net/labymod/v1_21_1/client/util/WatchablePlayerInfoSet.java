package net.labymod.v1_21_1.client.util;

import java.util.Set;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.core.watcher.set.WatchableSet;
import net.labymod.v1_21_1.client.player.VersionedNetworkPlayerInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/util/WatchablePlayerInfoSet.class */
public class WatchablePlayerInfoSet implements WatchableSet<fzq> {
    private final Set<NetworkPlayerInfo> playerInfos;

    public WatchablePlayerInfoSet(Set<NetworkPlayerInfo> playerInfos) {
        this.playerInfos = playerInfos;
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onAdd(fzq playerInfo) {
        NetworkPlayerInfo networkPlayerInfo = findNetworkPlayerInfo(playerInfo);
        if (networkPlayerInfo != null) {
            return;
        }
        this.playerInfos.add(new VersionedNetworkPlayerInfo(playerInfo));
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onRemove(fzq playerInfo) {
        NetworkPlayerInfo networkPlayerInfo = findNetworkPlayerInfo(playerInfo);
        if (networkPlayerInfo == null) {
            return;
        }
        this.playerInfos.remove(networkPlayerInfo);
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onClear() {
        this.playerInfos.clear();
    }

    private NetworkPlayerInfo findNetworkPlayerInfo(fzq info) {
        for (NetworkPlayerInfo playerInfo : this.playerInfos) {
            if (playerInfo.getMinecraftInfo().hashCode() == info.hashCode()) {
                return playerInfo;
            }
        }
        return null;
    }
}

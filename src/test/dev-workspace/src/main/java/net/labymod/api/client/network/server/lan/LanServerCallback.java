package net.labymod.api.client.network.server.lan;

import net.labymod.api.client.network.server.ConnectableServerData;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/lan/LanServerCallback.class */
public interface LanServerCallback {
    void onServerAdd(ConnectableServerData connectableServerData);

    void onServerRemove(ConnectableServerData connectableServerData);
}

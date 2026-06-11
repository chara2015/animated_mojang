package net.labymod.api.client.network.server.global;

import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/global/PublicServerData.class */
public class PublicServerData extends ConnectableServerData {
    private final boolean partner;

    public PublicServerData(String address, boolean partner) {
        super(address, ServerAddress.parse(address), ServerType.THIRD_PARTY, null);
        this.partner = partner;
    }

    public boolean isPartner() {
        return this.partner;
    }
}

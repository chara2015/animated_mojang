package net.labymod.api.client.network.server.global;

import java.util.ArrayList;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/global/PublicServerListService.class */
@Referenceable
public interface PublicServerListService {
    ArrayList<PublicServerData> getServers();
}

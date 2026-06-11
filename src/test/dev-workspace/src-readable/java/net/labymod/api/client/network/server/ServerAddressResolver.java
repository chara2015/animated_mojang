package net.labymod.api.client.network.server;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerAddressResolver.class */
@Referenceable
public interface ServerAddressResolver {
    ServerAddress resolve(ServerAddress serverAddress);

    void register(ServerAddress serverAddress);
}

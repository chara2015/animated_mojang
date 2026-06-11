package net.labymod.api.client.network.server;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerPinger.class */
@Referenceable
public interface ServerPinger {
    ServerInfo pingServer(String str, ServerAddress serverAddress, int i, boolean z) throws IOException;

    CompletableFuture<ServerInfo> pingServerAsync(String str, ServerAddress serverAddress, int i, boolean z);

    ResourceLocation getDefaultServerIcon();
}

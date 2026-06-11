package net.labymod.api.client.session;

import java.util.concurrent.CompletableFuture;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/MinecraftAuthenticator.class */
@Referenceable
public interface MinecraftAuthenticator {
    CompletableFuture<Boolean> joinServer(Session session, String str, int i);

    default CompletableFuture<Boolean> joinServer(Session session, String serverId) {
        return joinServer(session, serverId, 0);
    }
}

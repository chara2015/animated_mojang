package net.labymod.v1_20_4.client.session;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.ForcedUsernameChangeException;
import com.mojang.authlib.exceptions.InsufficientPrivilegesException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.exceptions.UserBannedException;
import java.net.Proxy;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.session.MinecraftAuthenticator;
import net.labymod.api.client.session.Session;
import net.labymod.api.models.Implements;
import net.labymod.core.client.session.DefaultsAbstractMinecraftAuthenticator;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/session/VersionedMinecraftAuthenticator.class */
@Singleton
@Implements(MinecraftAuthenticator.class)
public class VersionedMinecraftAuthenticator extends DefaultsAbstractMinecraftAuthenticator {
    private final LabyMinecraftSessionService sessionService = LabyMinecraftSessionService.create(Proxy.NO_PROXY);

    @Inject
    public VersionedMinecraftAuthenticator() {
    }

    @Override // net.labymod.api.client.session.MinecraftAuthenticator
    public CompletableFuture<Boolean> joinServer(Session session, String serverId, int priority) {
        return queueTask(priority, future -> {
            boolean result = false;
            try {
                try {
                    this.sessionService.joinServer(session.getUniqueId(), session.getAccessToken(), serverId);
                    result = true;
                    if (!future.isDone()) {
                        future.complete(true);
                    }
                } catch (AuthenticationException e) {
                    if (e instanceof AuthenticationUnavailableException) {
                        future.completeExceptionally(new net.labymod.api.client.session.exceptions.AuthenticationUnavailableException(e.getMessage(), e.getCause()));
                    } else if (e instanceof ForcedUsernameChangeException) {
                        future.completeExceptionally(new net.labymod.api.client.session.exceptions.ForcedUsernameChangeException());
                    } else if (e instanceof InsufficientPrivilegesException) {
                        future.completeExceptionally(new net.labymod.api.client.session.exceptions.InsufficientPrivilegesException(e.getMessage(), e.getCause()));
                    } else if (e instanceof InvalidCredentialsException) {
                        future.completeExceptionally(new net.labymod.api.client.session.exceptions.InvalidCredentialsException(e.getMessage(), e.getCause()));
                    } else if (e instanceof UserBannedException) {
                        future.completeExceptionally(new net.labymod.api.client.session.exceptions.UserBannedException());
                    } else {
                        future.completeExceptionally(new net.labymod.api.client.session.exceptions.AuthenticationException(e.getMessage(), e.getCause()));
                    }
                    if (!future.isDone()) {
                        future.complete(Boolean.valueOf(result));
                    }
                }
            } catch (Throwable th) {
                if (!future.isDone()) {
                    future.complete(Boolean.valueOf(result));
                }
                throw th;
            }
        });
    }
}

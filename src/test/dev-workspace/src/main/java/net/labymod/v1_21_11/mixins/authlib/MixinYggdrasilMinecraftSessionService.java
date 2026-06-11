package net.labymod.v1_21_11.mixins.authlib;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.ForcedUsernameChangeException;
import com.mojang.authlib.exceptions.InsufficientPrivilegesException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.exceptions.UserBannedException;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import net.labymod.api.Laby;
import net.labymod.api.client.session.Session;
import net.labymod.core.client.session.DefaultSession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/authlib/MixinYggdrasilMinecraftSessionService.class */
@Mixin(value = {YggdrasilMinecraftSessionService.class}, remap = false)
public abstract class MixinYggdrasilMinecraftSessionService {
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.authlib.exceptions.AuthenticationException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.authlib.exceptions.AuthenticationUnavailableException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.authlib.exceptions.ForcedUsernameChangeException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.authlib.exceptions.InsufficientPrivilegesException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.authlib.exceptions.InvalidCredentialsException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.authlib.exceptions.UserBannedException */
    @Overwrite
    public void joinServer(UUID profileId, String authenticationToken, String serverId) throws InsufficientPrivilegesException, AuthenticationException, InvalidCredentialsException, AuthenticationUnavailableException, ForcedUsernameChangeException, UserBannedException {
        try {
            Laby.references().minecraftAuthenticator().joinServer(new DefaultSession("", profileId, authenticationToken, Session.Type.MOJANG), serverId, -127).get();
        } catch (InterruptedException e) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof net.labymod.api.client.session.exceptions.AuthenticationUnavailableException) {
                throw new AuthenticationUnavailableException(cause.getMessage(), cause.getCause());
            }
            if (cause instanceof net.labymod.api.client.session.exceptions.ForcedUsernameChangeException) {
                throw new ForcedUsernameChangeException();
            }
            if (cause instanceof net.labymod.api.client.session.exceptions.InsufficientPrivilegesException) {
                throw new InsufficientPrivilegesException(cause.getMessage(), cause.getCause());
            }
            if (cause instanceof net.labymod.api.client.session.exceptions.InvalidCredentialsException) {
                throw new InvalidCredentialsException(cause.getMessage(), cause.getCause());
            }
            if (cause instanceof net.labymod.api.client.session.exceptions.UserBannedException) {
                throw new UserBannedException();
            }
            if (cause instanceof net.labymod.api.client.session.exceptions.AuthenticationException) {
                throw new AuthenticationException(cause.getMessage(), cause.getCause());
            }
            throw new AuthenticationUnavailableException("Cannot contact authentication server", cause);
        }
    }
}

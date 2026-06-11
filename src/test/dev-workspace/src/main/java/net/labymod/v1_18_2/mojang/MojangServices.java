package net.labymod.v1_18_2.mojang;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mojang/MojangServices.class */
public final class MojangServices {
    private static final Logging LOGGER = Logging.create((Class<?>) MojangServices.class);

    private MojangServices() {
    }

    public static UserApiService createSocialInteractions(MinecraftSessionService service, dzh user) {
        if (!(service instanceof YggdrasilMinecraftSessionService)) {
            LOGGER.error("Failed to verify authentication. (MinecraftSessionService is not a YggdrasilMinecraftSessionService)", new Object[0]);
            return UserApiService.OFFLINE;
        }
        YggdrasilMinecraftSessionService sessionService = (YggdrasilMinecraftSessionService) service;
        YggdrasilAuthenticationService authenticationService = sessionService.getAuthenticationService();
        return createSocialInteractions(authenticationService, user);
    }

    public static UserApiService createSocialInteractions(YggdrasilAuthenticationService service, dzh user) {
        try {
            return service.createUserApiService(user.d());
        } catch (AuthenticationException e) {
            LOGGER.error("Failed to verify authentication", e);
            return UserApiService.OFFLINE;
        }
    }
}

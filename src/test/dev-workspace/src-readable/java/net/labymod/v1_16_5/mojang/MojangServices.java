package net.labymod.v1_16_5.mojang;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.OfflineSocialInteractions;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mojang/MojangServices.class */
public final class MojangServices {
    private static final Logging LOGGER = Logging.create((Class<?>) MojangServices.class);

    private MojangServices() {
    }

    public static SocialInteractionsService createSocialInteractions(MinecraftSessionService service, dkm user) {
        if (!(service instanceof YggdrasilMinecraftSessionService)) {
            LOGGER.error("Failed to verify authentication. (MinecraftSessionService is not a YggdrasilMinecraftSessionService)", new Object[0]);
            return new OfflineSocialInteractions();
        }
        YggdrasilMinecraftSessionService sessionService = (YggdrasilMinecraftSessionService) service;
        YggdrasilAuthenticationService authenticationService = sessionService.getAuthenticationService();
        return createSocialInteractions(authenticationService, user);
    }

    public static SocialInteractionsService createSocialInteractions(YggdrasilAuthenticationService service, dkm user) {
        try {
            return service.createSocialInteractionsService(user.d());
        } catch (AuthenticationException e) {
            LOGGER.error("Failed to verify authentication", e);
            return new OfflineSocialInteractions();
        }
    }
}

package net.labymod.v1_20_6.client.session;

import com.mojang.authlib.Environment;
import com.mojang.authlib.EnvironmentParser;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.MinecraftClientException;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.authlib.yggdrasil.ServicesKeySet;
import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilServicesKeyInfo;
import com.mojang.authlib.yggdrasil.request.JoinMinecraftServerRequest;
import java.net.Proxy;
import java.net.URL;
import java.util.UUID;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/session/LabyMinecraftSessionService.class */
public class LabyMinecraftSessionService extends YggdrasilMinecraftSessionService {
    private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";
    private static final URL JOIN_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/join");
    private final MinecraftClient client;

    private LabyMinecraftSessionService(MinecraftClient client, ServicesKeySet servicesKeySet, Proxy proxy, Environment environment) {
        super(servicesKeySet, proxy, environment);
        this.client = client;
    }

    public static LabyMinecraftSessionService create(Proxy proxy) {
        Environment environment = (Environment) EnvironmentParser.getEnvironmentFromProperties().orElse(YggdrasilEnvironment.PROD.getEnvironment());
        MinecraftClient client = MinecraftClient.unauthenticated(proxy);
        URL publicKeySetUrl = HttpAuthenticationService.constantURL(environment.servicesHost() + "/publickeys");
        ServicesKeySet servicesKeySet = YggdrasilServicesKeyInfo.get(publicKeySetUrl, client);
        return new LabyMinecraftSessionService(client, servicesKeySet, proxy, environment);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.authlib.exceptions.AuthenticationException */
    public void joinServer(UUID profileId, String authenticationToken, String serverId) throws AuthenticationException {
        JoinMinecraftServerRequest request = new JoinMinecraftServerRequest(authenticationToken, profileId, serverId);
        try {
            this.client.post(JOIN_URL, request, Void.class);
        } catch (MinecraftClientException e) {
            throw e.toAuthenticationException();
        }
    }
}

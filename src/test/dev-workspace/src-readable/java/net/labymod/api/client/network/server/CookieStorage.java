package net.labymod.api.client.network.server;

import java.util.Map;
import java.util.Optional;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/CookieStorage.class */
public interface CookieStorage {
    void setCookie(ResourceLocation resourceLocation, byte[] bArr);

    Optional<byte[]> findCookie(ResourceLocation resourceLocation);

    Map<ResourceLocation, byte[]> cookies();
}

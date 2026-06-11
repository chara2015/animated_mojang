package net.labymod.v1_20_6.client.multiplayer.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/multiplayer/server/VersionedCookieStorage.class */
public class VersionedCookieStorage implements CookieStorage {
    private final fyp transferState;

    public VersionedCookieStorage(fyp transferState) {
        this.transferState = transferState;
    }

    @Override // net.labymod.api.client.network.server.CookieStorage
    public void setCookie(ResourceLocation cookieId, byte[] data) {
        this.transferState.a().put((alf) cookieId.getMinecraftLocation(), data);
    }

    @Override // net.labymod.api.client.network.server.CookieStorage
    public Optional<byte[]> findCookie(ResourceLocation cookieId) {
        return Optional.ofNullable((byte[]) this.transferState.a().get(cookieId.getMinecraftLocation()));
    }

    @Override // net.labymod.api.client.network.server.CookieStorage
    public Map<ResourceLocation, byte[]> cookies() {
        Map<alf, byte[]> vanillaCookies = this.transferState.a();
        Map<ResourceLocation, byte[]> cookies = new HashMap<>(vanillaCookies.size());
        for (Map.Entry<alf, byte[]> entry : vanillaCookies.entrySet()) {
            cookies.put((ResourceLocation) entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap(cookies);
    }
}

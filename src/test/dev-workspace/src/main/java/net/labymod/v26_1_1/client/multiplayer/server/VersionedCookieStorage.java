package net.labymod.v26_1_1.client.multiplayer.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.resources.ResourceLocation;
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/multiplayer/server/VersionedCookieStorage.class */
public class VersionedCookieStorage implements CookieStorage {
    private final TransferState transferState;

    public VersionedCookieStorage(TransferState transferState) {
        this.transferState = transferState;
    }

    @Override // net.labymod.api.client.network.server.CookieStorage
    public void setCookie(ResourceLocation cookieId, byte[] data) {
        this.transferState.cookies().put((Identifier) cookieId.getMinecraftLocation(), data);
    }

    @Override // net.labymod.api.client.network.server.CookieStorage
    public Optional<byte[]> findCookie(ResourceLocation cookieId) {
        return Optional.ofNullable((byte[]) this.transferState.cookies().get(cookieId.getMinecraftLocation()));
    }

    @Override // net.labymod.api.client.network.server.CookieStorage
    public Map<ResourceLocation, byte[]> cookies() {
        Map<Identifier, byte[]> vanillaCookies = this.transferState.cookies();
        Map<ResourceLocation, byte[]> cookies = new HashMap<>(vanillaCookies.size());
        for (Map.Entry<Identifier, byte[]> entry : vanillaCookies.entrySet()) {
            cookies.put((ResourceLocation) entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap(cookies);
    }

    public TransferState getTransferState() {
        return this.transferState;
    }
}

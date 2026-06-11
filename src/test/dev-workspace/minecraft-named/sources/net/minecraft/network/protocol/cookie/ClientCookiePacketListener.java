package net.minecraft.network.protocol.cookie;

import net.minecraft.network.ClientboundPacketListener;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/cookie/ClientCookiePacketListener.class */
public interface ClientCookiePacketListener extends ClientboundPacketListener {
    void handleRequestCookie(ClientboundCookieRequestPacket clientboundCookieRequestPacket);
}

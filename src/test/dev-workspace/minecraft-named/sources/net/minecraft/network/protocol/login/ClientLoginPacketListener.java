package net.minecraft.network.protocol.login;

import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.cookie.ClientCookiePacketListener;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/login/ClientLoginPacketListener.class */
public interface ClientLoginPacketListener extends ClientCookiePacketListener {
    void handleHello(ClientboundHelloPacket clientboundHelloPacket);

    void handleLoginFinished(ClientboundLoginFinishedPacket clientboundLoginFinishedPacket);

    void handleDisconnect(ClientboundLoginDisconnectPacket clientboundLoginDisconnectPacket);

    void handleCompression(ClientboundLoginCompressionPacket clientboundLoginCompressionPacket);

    void handleCustomQuery(ClientboundCustomQueryPacket clientboundCustomQueryPacket);

    @Override // net.minecraft.network.PacketListener
    default ConnectionProtocol protocol() {
        return ConnectionProtocol.LOGIN;
    }
}

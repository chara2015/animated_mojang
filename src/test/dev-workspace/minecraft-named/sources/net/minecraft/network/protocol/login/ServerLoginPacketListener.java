package net.minecraft.network.protocol.login;

import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.cookie.ServerCookiePacketListener;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/login/ServerLoginPacketListener.class */
public interface ServerLoginPacketListener extends ServerCookiePacketListener {
    void handleHello(ServerboundHelloPacket serverboundHelloPacket);

    void handleKey(ServerboundKeyPacket serverboundKeyPacket);

    void handleCustomQueryPacket(ServerboundCustomQueryAnswerPacket serverboundCustomQueryAnswerPacket);

    void handleLoginAcknowledgement(ServerboundLoginAcknowledgedPacket serverboundLoginAcknowledgedPacket);

    @Override // net.minecraft.network.PacketListener
    default ConnectionProtocol protocol() {
        return ConnectionProtocol.LOGIN;
    }
}

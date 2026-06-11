package net.minecraft.network.protocol.handshake;

import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.game.ServerPacketListener;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/handshake/ServerHandshakePacketListener.class */
public interface ServerHandshakePacketListener extends ServerPacketListener {
    void handleIntention(ClientIntentionPacket clientIntentionPacket);

    @Override // net.minecraft.network.PacketListener
    default ConnectionProtocol protocol() {
        return ConnectionProtocol.HANDSHAKING;
    }
}

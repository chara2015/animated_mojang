package net.minecraft.network.protocol.status;

import net.minecraft.network.ClientboundPacketListener;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.ping.ClientPongPacketListener;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/status/ClientStatusPacketListener.class */
public interface ClientStatusPacketListener extends ClientPongPacketListener, ClientboundPacketListener {
    void handleStatusResponse(ClientboundStatusResponsePacket clientboundStatusResponsePacket);

    @Override // net.minecraft.network.PacketListener
    default ConnectionProtocol protocol() {
        return ConnectionProtocol.STATUS;
    }
}

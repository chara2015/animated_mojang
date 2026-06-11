package net.minecraft.network.protocol.status;

import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.game.ServerPacketListener;
import net.minecraft.network.protocol.ping.ServerPingPacketListener;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/status/ServerStatusPacketListener.class */
public interface ServerStatusPacketListener extends ServerPacketListener, ServerPingPacketListener {
    void handleStatusRequest(ServerboundStatusRequestPacket serverboundStatusRequestPacket);

    @Override // net.minecraft.network.PacketListener
    default ConnectionProtocol protocol() {
        return ConnectionProtocol.STATUS;
    }
}

package net.minecraft.network.protocol.ping;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/ping/PingPacketTypes.class */
public class PingPacketTypes {
    public static final PacketType<ClientboundPongResponsePacket> CLIENTBOUND_PONG_RESPONSE = createClientbound("pong_response");
    public static final PacketType<ServerboundPingRequestPacket> SERVERBOUND_PING_REQUEST = createServerbound("ping_request");

    private static <T extends Packet<ClientPongPacketListener>> PacketType<T> createClientbound(String $$0) {
        return new PacketType<>(PacketFlow.CLIENTBOUND, Identifier.withDefaultNamespace($$0));
    }

    private static <T extends Packet<ServerPingPacketListener>> PacketType<T> createServerbound(String $$0) {
        return new PacketType<>(PacketFlow.SERVERBOUND, Identifier.withDefaultNamespace($$0));
    }
}

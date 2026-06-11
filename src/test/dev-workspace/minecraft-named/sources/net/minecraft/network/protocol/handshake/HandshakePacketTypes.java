package net.minecraft.network.protocol.handshake;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/handshake/HandshakePacketTypes.class */
public class HandshakePacketTypes {
    public static final PacketType<ClientIntentionPacket> CLIENT_INTENTION = createServerbound("intention");

    private static <T extends Packet<ServerHandshakePacketListener>> PacketType<T> createServerbound(String $$0) {
        return new PacketType<>(PacketFlow.SERVERBOUND, Identifier.withDefaultNamespace($$0));
    }
}

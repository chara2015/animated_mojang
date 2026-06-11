package net.minecraft.network.protocol.ping;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/ping/ServerboundPingRequestPacket.class */
public class ServerboundPingRequestPacket implements Packet<ServerPingPacketListener> {
    public static final StreamCodec<ByteBuf, ServerboundPingRequestPacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ServerboundPingRequestPacket::new);
    private final long time;

    public ServerboundPingRequestPacket(long $$0) {
        this.time = $$0;
    }

    private ServerboundPingRequestPacket(ByteBuf $$0) {
        this.time = $$0.readLong();
    }

    private void write(ByteBuf $$0) {
        $$0.writeLong(this.time);
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ServerboundPingRequestPacket> type() {
        return PingPacketTypes.SERVERBOUND_PING_REQUEST;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ServerPingPacketListener $$0) {
        $$0.handlePingRequest(this);
    }

    public long getTime() {
        return this.time;
    }
}

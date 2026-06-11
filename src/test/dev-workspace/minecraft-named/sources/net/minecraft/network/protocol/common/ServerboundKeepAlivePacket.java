package net.minecraft.network.protocol.common;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/ServerboundKeepAlivePacket.class */
public class ServerboundKeepAlivePacket implements Packet<ServerCommonPacketListener> {
    public static final StreamCodec<FriendlyByteBuf, ServerboundKeepAlivePacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ServerboundKeepAlivePacket::new);
    private final long id;

    public ServerboundKeepAlivePacket(long $$0) {
        this.id = $$0;
    }

    private ServerboundKeepAlivePacket(FriendlyByteBuf $$0) {
        this.id = $$0.readLong();
    }

    private void write(FriendlyByteBuf $$0) {
        $$0.m1586writeLong(this.id);
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ServerboundKeepAlivePacket> type() {
        return CommonPacketTypes.SERVERBOUND_KEEP_ALIVE;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ServerCommonPacketListener $$0) {
        $$0.handleKeepAlive(this);
    }

    public long getId() {
        return this.id;
    }
}

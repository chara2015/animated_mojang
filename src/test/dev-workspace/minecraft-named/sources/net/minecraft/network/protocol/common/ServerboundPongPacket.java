package net.minecraft.network.protocol.common;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/ServerboundPongPacket.class */
public class ServerboundPongPacket implements Packet<ServerCommonPacketListener> {
    public static final StreamCodec<FriendlyByteBuf, ServerboundPongPacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ServerboundPongPacket::new);
    private final int id;

    public ServerboundPongPacket(int $$0) {
        this.id = $$0;
    }

    private ServerboundPongPacket(FriendlyByteBuf $$0) {
        this.id = $$0.readInt();
    }

    private void write(FriendlyByteBuf $$0) {
        $$0.m1588writeInt(this.id);
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ServerboundPongPacket> type() {
        return CommonPacketTypes.SERVERBOUND_PONG;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ServerCommonPacketListener $$0) {
        $$0.handlePong(this);
    }

    public int getId() {
        return this.id;
    }
}

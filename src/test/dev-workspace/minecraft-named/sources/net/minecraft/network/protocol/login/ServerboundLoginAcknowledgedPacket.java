package net.minecraft.network.protocol.login;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/login/ServerboundLoginAcknowledgedPacket.class */
public class ServerboundLoginAcknowledgedPacket implements Packet<ServerLoginPacketListener> {
    public static final ServerboundLoginAcknowledgedPacket INSTANCE = new ServerboundLoginAcknowledgedPacket();
    public static final StreamCodec<ByteBuf, ServerboundLoginAcknowledgedPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private ServerboundLoginAcknowledgedPacket() {
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ServerboundLoginAcknowledgedPacket> type() {
        return LoginPacketTypes.SERVERBOUND_LOGIN_ACKNOWLEDGED;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ServerLoginPacketListener $$0) {
        $$0.handleLoginAcknowledgement(this);
    }

    @Override // net.minecraft.network.protocol.Packet
    public boolean isTerminal() {
        return true;
    }
}

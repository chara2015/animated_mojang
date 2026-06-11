package net.minecraft.network.protocol.login;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/login/ClientboundLoginCompressionPacket.class */
public class ClientboundLoginCompressionPacket implements Packet<ClientLoginPacketListener> {
    public static final StreamCodec<FriendlyByteBuf, ClientboundLoginCompressionPacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ClientboundLoginCompressionPacket::new);
    private final int compressionThreshold;

    public ClientboundLoginCompressionPacket(int $$0) {
        this.compressionThreshold = $$0;
    }

    private ClientboundLoginCompressionPacket(FriendlyByteBuf $$0) {
        this.compressionThreshold = $$0.readVarInt();
    }

    private void write(FriendlyByteBuf $$0) {
        $$0.writeVarInt(this.compressionThreshold);
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundLoginCompressionPacket> type() {
        return LoginPacketTypes.CLIENTBOUND_LOGIN_COMPRESSION;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientLoginPacketListener $$0) {
        $$0.handleCompression(this);
    }

    public int getCompressionThreshold() {
        return this.compressionThreshold;
    }
}

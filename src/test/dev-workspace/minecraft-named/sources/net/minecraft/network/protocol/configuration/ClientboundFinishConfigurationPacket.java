package net.minecraft.network.protocol.configuration;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/configuration/ClientboundFinishConfigurationPacket.class */
public class ClientboundFinishConfigurationPacket implements Packet<ClientConfigurationPacketListener> {
    public static final ClientboundFinishConfigurationPacket INSTANCE = new ClientboundFinishConfigurationPacket();
    public static final StreamCodec<ByteBuf, ClientboundFinishConfigurationPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private ClientboundFinishConfigurationPacket() {
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundFinishConfigurationPacket> type() {
        return ConfigurationPacketTypes.CLIENTBOUND_FINISH_CONFIGURATION;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientConfigurationPacketListener $$0) {
        $$0.handleConfigurationFinished(this);
    }

    @Override // net.minecraft.network.protocol.Packet
    public boolean isTerminal() {
        return true;
    }
}

package net.minecraft.network.protocol.configuration;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/configuration/ServerboundFinishConfigurationPacket.class */
public class ServerboundFinishConfigurationPacket implements Packet<ServerConfigurationPacketListener> {
    public static final ServerboundFinishConfigurationPacket INSTANCE = new ServerboundFinishConfigurationPacket();
    public static final StreamCodec<ByteBuf, ServerboundFinishConfigurationPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private ServerboundFinishConfigurationPacket() {
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ServerboundFinishConfigurationPacket> type() {
        return ConfigurationPacketTypes.SERVERBOUND_FINISH_CONFIGURATION;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ServerConfigurationPacketListener $$0) {
        $$0.handleConfigurationFinished(this);
    }

    @Override // net.minecraft.network.protocol.Packet
    public boolean isTerminal() {
        return true;
    }
}

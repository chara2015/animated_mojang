package net.minecraft.network.protocol.common;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/ClientboundClearDialogPacket.class */
public class ClientboundClearDialogPacket implements Packet<ClientCommonPacketListener> {
    public static final ClientboundClearDialogPacket INSTANCE = new ClientboundClearDialogPacket();
    public static final StreamCodec<ByteBuf, ClientboundClearDialogPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private ClientboundClearDialogPacket() {
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundClearDialogPacket> type() {
        return CommonPacketTypes.CLIENTBOUND_CLEAR_DIALOG;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientCommonPacketListener $$0) {
        $$0.handleClearDialog(this);
    }
}

package net.minecraft.network.protocol.common;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/ClientboundTransferPacket.class */
public final class ClientboundTransferPacket extends Record implements Packet<ClientCommonPacketListener> {
    private final String host;
    private final int port;
    public static final StreamCodec<FriendlyByteBuf, ClientboundTransferPacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ClientboundTransferPacket::new);

    public ClientboundTransferPacket(String $$0, int $$1) {
        this.host = $$0;
        this.port = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientboundTransferPacket.class), ClientboundTransferPacket.class, "host;port", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundTransferPacket;->host:Ljava/lang/String;", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundTransferPacket;->port:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientboundTransferPacket.class), ClientboundTransferPacket.class, "host;port", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundTransferPacket;->host:Ljava/lang/String;", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundTransferPacket;->port:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientboundTransferPacket.class, Object.class), ClientboundTransferPacket.class, "host;port", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundTransferPacket;->host:Ljava/lang/String;", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundTransferPacket;->port:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String host() {
        return this.host;
    }

    public int port() {
        return this.port;
    }

    private ClientboundTransferPacket(FriendlyByteBuf $$0) {
        this($$0.readUtf(), $$0.readVarInt());
    }

    private void write(FriendlyByteBuf $$0) {
        $$0.writeUtf(this.host);
        $$0.writeVarInt(this.port);
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundTransferPacket> type() {
        return CommonPacketTypes.CLIENTBOUND_TRANSFER;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientCommonPacketListener $$0) {
        $$0.handleTransfer(this);
    }
}

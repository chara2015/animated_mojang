package net.minecraft.network.protocol.ping;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/ping/ClientboundPongResponsePacket.class */
public final class ClientboundPongResponsePacket extends Record implements Packet<ClientPongPacketListener> {
    private final long time;
    public static final StreamCodec<FriendlyByteBuf, ClientboundPongResponsePacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ClientboundPongResponsePacket::new);

    public ClientboundPongResponsePacket(long $$0) {
        this.time = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientboundPongResponsePacket.class), ClientboundPongResponsePacket.class, "time", "FIELD:Lnet/minecraft/network/protocol/ping/ClientboundPongResponsePacket;->time:J").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientboundPongResponsePacket.class), ClientboundPongResponsePacket.class, "time", "FIELD:Lnet/minecraft/network/protocol/ping/ClientboundPongResponsePacket;->time:J").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientboundPongResponsePacket.class, Object.class), ClientboundPongResponsePacket.class, "time", "FIELD:Lnet/minecraft/network/protocol/ping/ClientboundPongResponsePacket;->time:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public long time() {
        return this.time;
    }

    private ClientboundPongResponsePacket(FriendlyByteBuf $$0) {
        this($$0.readLong());
    }

    private void write(FriendlyByteBuf $$0) {
        $$0.m1586writeLong(this.time);
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundPongResponsePacket> type() {
        return PingPacketTypes.CLIENTBOUND_PONG_RESPONSE;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientPongPacketListener $$0) {
        $$0.handlePongResponse(this);
    }
}

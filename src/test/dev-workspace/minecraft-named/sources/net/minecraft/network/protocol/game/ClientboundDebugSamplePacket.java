package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.util.debugchart.RemoteDebugSampleType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/game/ClientboundDebugSamplePacket.class */
public final class ClientboundDebugSamplePacket extends Record implements Packet<ClientGamePacketListener> {
    private final long[] sample;
    private final RemoteDebugSampleType debugSampleType;
    public static final StreamCodec<FriendlyByteBuf, ClientboundDebugSamplePacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ClientboundDebugSamplePacket::new);

    public ClientboundDebugSamplePacket(long[] $$0, RemoteDebugSampleType $$1) {
        this.sample = $$0;
        this.debugSampleType = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientboundDebugSamplePacket.class), ClientboundDebugSamplePacket.class, "sample;debugSampleType", "FIELD:Lnet/minecraft/network/protocol/game/ClientboundDebugSamplePacket;->sample:[J", "FIELD:Lnet/minecraft/network/protocol/game/ClientboundDebugSamplePacket;->debugSampleType:Lnet/minecraft/util/debugchart/RemoteDebugSampleType;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientboundDebugSamplePacket.class), ClientboundDebugSamplePacket.class, "sample;debugSampleType", "FIELD:Lnet/minecraft/network/protocol/game/ClientboundDebugSamplePacket;->sample:[J", "FIELD:Lnet/minecraft/network/protocol/game/ClientboundDebugSamplePacket;->debugSampleType:Lnet/minecraft/util/debugchart/RemoteDebugSampleType;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientboundDebugSamplePacket.class, Object.class), ClientboundDebugSamplePacket.class, "sample;debugSampleType", "FIELD:Lnet/minecraft/network/protocol/game/ClientboundDebugSamplePacket;->sample:[J", "FIELD:Lnet/minecraft/network/protocol/game/ClientboundDebugSamplePacket;->debugSampleType:Lnet/minecraft/util/debugchart/RemoteDebugSampleType;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public long[] sample() {
        return this.sample;
    }

    public RemoteDebugSampleType debugSampleType() {
        return this.debugSampleType;
    }

    private ClientboundDebugSamplePacket(FriendlyByteBuf $$0) {
        this($$0.readLongArray(), (RemoteDebugSampleType) $$0.readEnum(RemoteDebugSampleType.class));
    }

    private void write(FriendlyByteBuf $$0) {
        $$0.writeLongArray(this.sample);
        $$0.writeEnum(this.debugSampleType);
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundDebugSamplePacket> type() {
        return GamePacketTypes.CLIENTBOUND_DEBUG_SAMPLE;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientGamePacketListener $$0) {
        $$0.handleDebugSample(this);
    }
}

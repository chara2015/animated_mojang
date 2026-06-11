package net.minecraft.network.protocol.status;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.resources.RegistryOps;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/status/ClientboundStatusResponsePacket.class */
public final class ClientboundStatusResponsePacket extends Record implements Packet<ClientStatusPacketListener> {
    private final ServerStatus status;
    private static final RegistryOps<JsonElement> OPS = RegistryAccess.EMPTY.createSerializationContext(JsonOps.INSTANCE);
    public static final StreamCodec<ByteBuf, ClientboundStatusResponsePacket> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.lenientJson(32767).apply(ByteBufCodecs.fromCodec(OPS, ServerStatus.CODEC)), (v0) -> {
        return v0.status();
    }, ClientboundStatusResponsePacket::new);

    public ClientboundStatusResponsePacket(ServerStatus $$0) {
        this.status = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientboundStatusResponsePacket.class), ClientboundStatusResponsePacket.class, "status", "FIELD:Lnet/minecraft/network/protocol/status/ClientboundStatusResponsePacket;->status:Lnet/minecraft/network/protocol/status/ServerStatus;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientboundStatusResponsePacket.class), ClientboundStatusResponsePacket.class, "status", "FIELD:Lnet/minecraft/network/protocol/status/ClientboundStatusResponsePacket;->status:Lnet/minecraft/network/protocol/status/ServerStatus;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientboundStatusResponsePacket.class, Object.class), ClientboundStatusResponsePacket.class, "status", "FIELD:Lnet/minecraft/network/protocol/status/ClientboundStatusResponsePacket;->status:Lnet/minecraft/network/protocol/status/ServerStatus;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ServerStatus status() {
        return this.status;
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundStatusResponsePacket> type() {
        return StatusPacketTypes.CLIENTBOUND_STATUS_RESPONSE;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientStatusPacketListener $$0) {
        $$0.handleStatusResponse(this);
    }
}

package net.minecraft.network.protocol.common;

import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/ClientboundDisconnectPacket.class */
public final class ClientboundDisconnectPacket extends Record implements Packet<ClientCommonPacketListener> {
    private final Component reason;
    public static final StreamCodec<ByteBuf, ClientboundDisconnectPacket> STREAM_CODEC = ComponentSerialization.TRUSTED_CONTEXT_FREE_STREAM_CODEC.map(ClientboundDisconnectPacket::new, (v0) -> {
        return v0.reason();
    });

    public ClientboundDisconnectPacket(Component $$0) {
        this.reason = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientboundDisconnectPacket.class), ClientboundDisconnectPacket.class, "reason", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundDisconnectPacket;->reason:Lnet/minecraft/network/chat/Component;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientboundDisconnectPacket.class), ClientboundDisconnectPacket.class, "reason", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundDisconnectPacket;->reason:Lnet/minecraft/network/chat/Component;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientboundDisconnectPacket.class, Object.class), ClientboundDisconnectPacket.class, "reason", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundDisconnectPacket;->reason:Lnet/minecraft/network/chat/Component;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Component reason() {
        return this.reason;
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundDisconnectPacket> type() {
        return CommonPacketTypes.CLIENTBOUND_DISCONNECT;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientCommonPacketListener $$0) {
        $$0.handleDisconnect(this);
    }
}

package net.minecraft.network.protocol.common;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/ClientboundResourcePackPopPacket.class */
public final class ClientboundResourcePackPopPacket extends Record implements Packet<ClientCommonPacketListener> {
    private final Optional<UUID> id;
    public static final StreamCodec<FriendlyByteBuf, ClientboundResourcePackPopPacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ClientboundResourcePackPopPacket::new);

    public ClientboundResourcePackPopPacket(Optional<UUID> $$0) {
        this.id = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientboundResourcePackPopPacket.class), ClientboundResourcePackPopPacket.class, "id", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundResourcePackPopPacket;->id:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientboundResourcePackPopPacket.class), ClientboundResourcePackPopPacket.class, "id", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundResourcePackPopPacket;->id:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientboundResourcePackPopPacket.class, Object.class), ClientboundResourcePackPopPacket.class, "id", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundResourcePackPopPacket;->id:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Optional<UUID> id() {
        return this.id;
    }

    private ClientboundResourcePackPopPacket(FriendlyByteBuf $$0) {
        this((Optional<UUID>) $$0.readOptional(UUIDUtil.STREAM_CODEC));
    }

    private void write(FriendlyByteBuf $$0) {
        $$0.writeOptional(this.id, UUIDUtil.STREAM_CODEC);
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundResourcePackPopPacket> type() {
        return CommonPacketTypes.CLIENTBOUND_RESOURCE_PACK_POP;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientCommonPacketListener $$0) {
        $$0.handleResourcePackPop(this);
    }
}

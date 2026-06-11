package net.minecraft.network.protocol.common;

import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/ClientboundCustomReportDetailsPacket.class */
public final class ClientboundCustomReportDetailsPacket extends Record implements Packet<ClientCommonPacketListener> {
    private final Map<String, String> details;
    private static final int MAX_DETAIL_KEY_LENGTH = 128;
    private static final int MAX_DETAIL_VALUE_LENGTH = 4096;
    private static final int MAX_DETAIL_COUNT = 32;
    private static final StreamCodec<ByteBuf, Map<String, String>> DETAILS_STREAM_CODEC = ByteBufCodecs.map(HashMap::new, ByteBufCodecs.stringUtf8(128), ByteBufCodecs.stringUtf8(4096), 32);
    public static final StreamCodec<ByteBuf, ClientboundCustomReportDetailsPacket> STREAM_CODEC = StreamCodec.composite(DETAILS_STREAM_CODEC, (v0) -> {
        return v0.details();
    }, ClientboundCustomReportDetailsPacket::new);

    public ClientboundCustomReportDetailsPacket(Map<String, String> $$0) {
        this.details = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientboundCustomReportDetailsPacket.class), ClientboundCustomReportDetailsPacket.class, "details", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundCustomReportDetailsPacket;->details:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientboundCustomReportDetailsPacket.class), ClientboundCustomReportDetailsPacket.class, "details", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundCustomReportDetailsPacket;->details:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientboundCustomReportDetailsPacket.class, Object.class), ClientboundCustomReportDetailsPacket.class, "details", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundCustomReportDetailsPacket;->details:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Map<String, String> details() {
        return this.details;
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundCustomReportDetailsPacket> type() {
        return CommonPacketTypes.CLIENTBOUND_CUSTOM_REPORT_DETAILS;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientCommonPacketListener $$0) {
        $$0.handleCustomReportDetails(this);
    }
}

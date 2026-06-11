package net.minecraft.network.protocol.common;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.server.level.ClientInformation;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/ServerboundClientInformationPacket.class */
public final class ServerboundClientInformationPacket extends Record implements Packet<ServerCommonPacketListener> {
    private final ClientInformation information;
    public static final StreamCodec<FriendlyByteBuf, ServerboundClientInformationPacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ServerboundClientInformationPacket::new);

    public ServerboundClientInformationPacket(ClientInformation $$0) {
        this.information = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ServerboundClientInformationPacket.class), ServerboundClientInformationPacket.class, "information", "FIELD:Lnet/minecraft/network/protocol/common/ServerboundClientInformationPacket;->information:Lnet/minecraft/server/level/ClientInformation;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ServerboundClientInformationPacket.class), ServerboundClientInformationPacket.class, "information", "FIELD:Lnet/minecraft/network/protocol/common/ServerboundClientInformationPacket;->information:Lnet/minecraft/server/level/ClientInformation;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ServerboundClientInformationPacket.class, Object.class), ServerboundClientInformationPacket.class, "information", "FIELD:Lnet/minecraft/network/protocol/common/ServerboundClientInformationPacket;->information:Lnet/minecraft/server/level/ClientInformation;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ClientInformation information() {
        return this.information;
    }

    private ServerboundClientInformationPacket(FriendlyByteBuf $$0) {
        this(new ClientInformation($$0));
    }

    private void write(FriendlyByteBuf $$0) {
        this.information.write($$0);
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ServerboundClientInformationPacket> type() {
        return CommonPacketTypes.SERVERBOUND_CLIENT_INFORMATION;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ServerCommonPacketListener $$0) {
        $$0.handleClientInformation(this);
    }
}

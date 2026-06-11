package net.minecraft.network.protocol.configuration;

import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/configuration/ServerboundAcceptCodeOfConductPacket.class */
public final class ServerboundAcceptCodeOfConductPacket extends Record implements Packet<ServerConfigurationPacketListener> {
    public static final ServerboundAcceptCodeOfConductPacket INSTANCE = new ServerboundAcceptCodeOfConductPacket();
    public static final StreamCodec<ByteBuf, ServerboundAcceptCodeOfConductPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ServerboundAcceptCodeOfConductPacket.class), ServerboundAcceptCodeOfConductPacket.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ServerboundAcceptCodeOfConductPacket.class), ServerboundAcceptCodeOfConductPacket.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ServerboundAcceptCodeOfConductPacket.class, Object.class), ServerboundAcceptCodeOfConductPacket.class, "").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ServerboundAcceptCodeOfConductPacket> type() {
        return ConfigurationPacketTypes.SERVERBOUND_ACCEPT_CODE_OF_CONDUCT;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ServerConfigurationPacketListener $$0) {
        $$0.handleAcceptCodeOfConduct(this);
    }
}

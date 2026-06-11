package net.minecraft.network.protocol.login;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.network.protocol.login.custom.CustomQueryAnswerPayload;
import net.minecraft.network.protocol.login.custom.DiscardedQueryAnswerPayload;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket.class */
public final class ServerboundCustomQueryAnswerPacket extends Record implements Packet<ServerLoginPacketListener> {
    private final int transactionId;
    private final CustomQueryAnswerPayload payload;
    public static final StreamCodec<FriendlyByteBuf, ServerboundCustomQueryAnswerPacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ServerboundCustomQueryAnswerPacket::read);
    private static final int MAX_PAYLOAD_SIZE = 1048576;

    public ServerboundCustomQueryAnswerPacket(int $$0, CustomQueryAnswerPayload $$1) {
        this.transactionId = $$0;
        this.payload = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ServerboundCustomQueryAnswerPacket.class), ServerboundCustomQueryAnswerPacket.class, "transactionId;payload", "FIELD:Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;->transactionId:I", "FIELD:Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;->payload:Lnet/minecraft/network/protocol/login/custom/CustomQueryAnswerPayload;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ServerboundCustomQueryAnswerPacket.class), ServerboundCustomQueryAnswerPacket.class, "transactionId;payload", "FIELD:Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;->transactionId:I", "FIELD:Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;->payload:Lnet/minecraft/network/protocol/login/custom/CustomQueryAnswerPayload;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ServerboundCustomQueryAnswerPacket.class, Object.class), ServerboundCustomQueryAnswerPacket.class, "transactionId;payload", "FIELD:Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;->transactionId:I", "FIELD:Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;->payload:Lnet/minecraft/network/protocol/login/custom/CustomQueryAnswerPayload;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int transactionId() {
        return this.transactionId;
    }

    public CustomQueryAnswerPayload payload() {
        return this.payload;
    }

    private static ServerboundCustomQueryAnswerPacket read(FriendlyByteBuf $$0) {
        int $$1 = $$0.readVarInt();
        return new ServerboundCustomQueryAnswerPacket($$1, readPayload($$1, $$0));
    }

    private static CustomQueryAnswerPayload readPayload(int $$0, FriendlyByteBuf $$1) {
        return readUnknownPayload($$1);
    }

    private static CustomQueryAnswerPayload readUnknownPayload(FriendlyByteBuf $$0) {
        int $$1 = $$0.readableBytes();
        if ($$1 < 0 || $$1 > MAX_PAYLOAD_SIZE) {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
        $$0.m1595skipBytes($$1);
        return DiscardedQueryAnswerPayload.INSTANCE;
    }

    private void write(FriendlyByteBuf $$0) {
        $$0.writeVarInt(this.transactionId);
        $$0.writeNullable(this.payload, ($$02, $$1) -> {
            $$1.write($$02);
        });
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ServerboundCustomQueryAnswerPacket> type() {
        return LoginPacketTypes.SERVERBOUND_CUSTOM_QUERY_ANSWER;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ServerLoginPacketListener $$0) {
        $$0.handleCustomQueryPacket(this);
    }
}

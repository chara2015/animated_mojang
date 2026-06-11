package net.minecraft.network.protocol.handshake;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/handshake/ClientIntentionPacket.class */
public final class ClientIntentionPacket extends Record implements Packet<ServerHandshakePacketListener> {
    private final int protocolVersion;
    private final String hostName;
    private final int port;
    private final ClientIntent intention;
    public static final StreamCodec<FriendlyByteBuf, ClientIntentionPacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ClientIntentionPacket::new);
    private static final int MAX_HOST_LENGTH = 255;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientIntentionPacket.class), ClientIntentionPacket.class, "protocolVersion;hostName;port;intention", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->protocolVersion:I", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->hostName:Ljava/lang/String;", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->port:I", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->intention:Lnet/minecraft/network/protocol/handshake/ClientIntent;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientIntentionPacket.class), ClientIntentionPacket.class, "protocolVersion;hostName;port;intention", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->protocolVersion:I", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->hostName:Ljava/lang/String;", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->port:I", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->intention:Lnet/minecraft/network/protocol/handshake/ClientIntent;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientIntentionPacket.class, Object.class), ClientIntentionPacket.class, "protocolVersion;hostName;port;intention", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->protocolVersion:I", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->hostName:Ljava/lang/String;", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->port:I", "FIELD:Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;->intention:Lnet/minecraft/network/protocol/handshake/ClientIntent;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int protocolVersion() {
        return this.protocolVersion;
    }

    public String hostName() {
        return this.hostName;
    }

    public int port() {
        return this.port;
    }

    public ClientIntent intention() {
        return this.intention;
    }

    @Deprecated
    public ClientIntentionPacket(int $$0, String $$1, int $$2, ClientIntent $$3) {
        this.protocolVersion = $$0;
        this.hostName = $$1;
        this.port = $$2;
        this.intention = $$3;
    }

    private ClientIntentionPacket(FriendlyByteBuf $$0) {
        this($$0.readVarInt(), $$0.readUtf(255), $$0.readUnsignedShort(), ClientIntent.byId($$0.readVarInt()));
    }

    private void write(FriendlyByteBuf $$0) {
        $$0.writeVarInt(this.protocolVersion);
        $$0.writeUtf(this.hostName);
        $$0.m1592writeShort(this.port);
        $$0.writeVarInt(this.intention.id());
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientIntentionPacket> type() {
        return HandshakePacketTypes.CLIENT_INTENTION;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ServerHandshakePacketListener $$0) {
        $$0.handleIntention(this);
    }

    @Override // net.minecraft.network.protocol.Packet
    public boolean isTerminal() {
        return true;
    }
}

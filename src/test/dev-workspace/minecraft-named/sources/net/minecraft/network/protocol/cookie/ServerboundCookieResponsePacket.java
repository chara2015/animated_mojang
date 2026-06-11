package net.minecraft.network.protocol.cookie;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.network.protocol.common.ClientboundStoreCookiePacket;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/cookie/ServerboundCookieResponsePacket.class */
public final class ServerboundCookieResponsePacket extends Record implements Packet<ServerCookiePacketListener> {
    private final Identifier key;
    private final byte[] payload;
    public static final StreamCodec<FriendlyByteBuf, ServerboundCookieResponsePacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ServerboundCookieResponsePacket::new);

    public ServerboundCookieResponsePacket(Identifier $$0, byte[] $$1) {
        this.key = $$0;
        this.payload = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ServerboundCookieResponsePacket.class), ServerboundCookieResponsePacket.class, "key;payload", "FIELD:Lnet/minecraft/network/protocol/cookie/ServerboundCookieResponsePacket;->key:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/network/protocol/cookie/ServerboundCookieResponsePacket;->payload:[B").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ServerboundCookieResponsePacket.class), ServerboundCookieResponsePacket.class, "key;payload", "FIELD:Lnet/minecraft/network/protocol/cookie/ServerboundCookieResponsePacket;->key:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/network/protocol/cookie/ServerboundCookieResponsePacket;->payload:[B").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ServerboundCookieResponsePacket.class, Object.class), ServerboundCookieResponsePacket.class, "key;payload", "FIELD:Lnet/minecraft/network/protocol/cookie/ServerboundCookieResponsePacket;->key:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/network/protocol/cookie/ServerboundCookieResponsePacket;->payload:[B").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Identifier key() {
        return this.key;
    }

    public byte[] payload() {
        return this.payload;
    }

    private ServerboundCookieResponsePacket(FriendlyByteBuf $$0) {
        this($$0.readIdentifier(), (byte[]) $$0.readNullable(ClientboundStoreCookiePacket.PAYLOAD_STREAM_CODEC));
    }

    private void write(FriendlyByteBuf $$0) {
        $$0.writeIdentifier(this.key);
        $$0.writeNullable(this.payload, ClientboundStoreCookiePacket.PAYLOAD_STREAM_CODEC);
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ServerboundCookieResponsePacket> type() {
        return CookiePacketTypes.SERVERBOUND_COOKIE_RESPONSE;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ServerCookiePacketListener $$0) {
        $$0.handleCookieResponse(this);
    }
}

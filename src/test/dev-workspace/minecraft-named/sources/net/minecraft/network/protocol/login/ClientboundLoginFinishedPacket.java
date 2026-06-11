package net.minecraft.network.protocol.login;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/login/ClientboundLoginFinishedPacket.class */
public final class ClientboundLoginFinishedPacket extends Record implements Packet<ClientLoginPacketListener> {
    private final GameProfile gameProfile;
    public static final StreamCodec<ByteBuf, ClientboundLoginFinishedPacket> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.GAME_PROFILE, (v0) -> {
        return v0.gameProfile();
    }, ClientboundLoginFinishedPacket::new);

    public ClientboundLoginFinishedPacket(GameProfile $$0) {
        this.gameProfile = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientboundLoginFinishedPacket.class), ClientboundLoginFinishedPacket.class, "gameProfile", "FIELD:Lnet/minecraft/network/protocol/login/ClientboundLoginFinishedPacket;->gameProfile:Lcom/mojang/authlib/GameProfile;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientboundLoginFinishedPacket.class), ClientboundLoginFinishedPacket.class, "gameProfile", "FIELD:Lnet/minecraft/network/protocol/login/ClientboundLoginFinishedPacket;->gameProfile:Lcom/mojang/authlib/GameProfile;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientboundLoginFinishedPacket.class, Object.class), ClientboundLoginFinishedPacket.class, "gameProfile", "FIELD:Lnet/minecraft/network/protocol/login/ClientboundLoginFinishedPacket;->gameProfile:Lcom/mojang/authlib/GameProfile;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public GameProfile gameProfile() {
        return this.gameProfile;
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundLoginFinishedPacket> type() {
        return LoginPacketTypes.CLIENTBOUND_LOGIN_FINISHED;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientLoginPacketListener $$0) {
        $$0.handleLoginFinished(this);
    }

    @Override // net.minecraft.network.protocol.Packet
    public boolean isTerminal() {
        return true;
    }
}

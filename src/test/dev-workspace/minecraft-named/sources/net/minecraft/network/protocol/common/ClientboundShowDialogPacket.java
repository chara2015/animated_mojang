package net.minecraft.network.protocol.common;

import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.server.dialog.Dialog;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/ClientboundShowDialogPacket.class */
public final class ClientboundShowDialogPacket extends Record implements Packet<ClientCommonPacketListener> {
    private final Holder<Dialog> dialog;
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundShowDialogPacket> STREAM_CODEC = StreamCodec.composite(Dialog.STREAM_CODEC, (v0) -> {
        return v0.dialog();
    }, ClientboundShowDialogPacket::new);
    public static final StreamCodec<ByteBuf, ClientboundShowDialogPacket> CONTEXT_FREE_STREAM_CODEC = StreamCodec.composite(Dialog.CONTEXT_FREE_STREAM_CODEC.map((v0) -> {
        return Holder.direct(v0);
    }, (v0) -> {
        return v0.value();
    }), (v0) -> {
        return v0.dialog();
    }, ClientboundShowDialogPacket::new);

    public ClientboundShowDialogPacket(Holder<Dialog> $$0) {
        this.dialog = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientboundShowDialogPacket.class), ClientboundShowDialogPacket.class, "dialog", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundShowDialogPacket;->dialog:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientboundShowDialogPacket.class), ClientboundShowDialogPacket.class, "dialog", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundShowDialogPacket;->dialog:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientboundShowDialogPacket.class, Object.class), ClientboundShowDialogPacket.class, "dialog", "FIELD:Lnet/minecraft/network/protocol/common/ClientboundShowDialogPacket;->dialog:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Holder<Dialog> dialog() {
        return this.dialog;
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundShowDialogPacket> type() {
        return CommonPacketTypes.CLIENTBOUND_SHOW_DIALOG;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientCommonPacketListener $$0) {
        $$0.handleShowDialog(this);
    }
}

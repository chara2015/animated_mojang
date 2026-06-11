package net.minecraft.network.protocol;

import net.minecraft.network.PacketListener;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/BundleDelimiterPacket.class */
public abstract class BundleDelimiterPacket<T extends PacketListener> implements Packet<T> {
    @Override // net.minecraft.network.protocol.Packet
    public abstract PacketType<? extends BundleDelimiterPacket<T>> type();

    @Override // net.minecraft.network.protocol.Packet
    public final void handle(T $$0) {
        throw new AssertionError("This packet should be handled by pipeline");
    }
}

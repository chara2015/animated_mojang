package net.minecraft.network.protocol;

import net.minecraft.network.PacketListener;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/BundlePacket.class */
public abstract class BundlePacket<T extends PacketListener> implements Packet<T> {
    private final Iterable<Packet<? super T>> packets;

    @Override // net.minecraft.network.protocol.Packet
    public abstract PacketType<? extends BundlePacket<T>> type();

    protected BundlePacket(Iterable<Packet<? super T>> $$0) {
        this.packets = $$0;
    }

    public final Iterable<Packet<? super T>> subPackets() {
        return this.packets;
    }
}

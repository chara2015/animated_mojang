package net.minecraft.network;

import net.minecraft.network.protocol.PacketFlow;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/ClientboundPacketListener.class */
public interface ClientboundPacketListener extends PacketListener {
    @Override // net.minecraft.network.PacketListener
    default PacketFlow flow() {
        return PacketFlow.CLIENTBOUND;
    }
}

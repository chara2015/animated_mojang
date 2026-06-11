package net.minecraft.network.protocol.configuration;

import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.common.ServerCommonPacketListener;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/configuration/ServerConfigurationPacketListener.class */
public interface ServerConfigurationPacketListener extends ServerCommonPacketListener {
    void handleConfigurationFinished(ServerboundFinishConfigurationPacket serverboundFinishConfigurationPacket);

    void handleSelectKnownPacks(ServerboundSelectKnownPacks serverboundSelectKnownPacks);

    void handleAcceptCodeOfConduct(ServerboundAcceptCodeOfConductPacket serverboundAcceptCodeOfConductPacket);

    @Override // net.minecraft.network.PacketListener
    default ConnectionProtocol protocol() {
        return ConnectionProtocol.CONFIGURATION;
    }
}

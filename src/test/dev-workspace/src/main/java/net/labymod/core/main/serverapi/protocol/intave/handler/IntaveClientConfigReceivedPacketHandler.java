package net.labymod.core.main.serverapi.protocol.intave.handler;

import java.util.UUID;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;
import net.labymod.core.main.serverapi.protocol.intave.packet.IntaveClientConfigReceivedPacket;
import net.labymod.serverapi.api.packet.PacketHandler;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/intave/handler/IntaveClientConfigReceivedPacketHandler.class */
public class IntaveClientConfigReceivedPacketHandler implements PacketHandler<IntaveClientConfigReceivedPacket> {
    private final IntaveProtocol intaveProtocol;

    public IntaveClientConfigReceivedPacketHandler(IntaveProtocol intaveProtocol) {
        this.intaveProtocol = intaveProtocol;
    }

    public void handle(@NotNull UUID uuid, @NotNull IntaveClientConfigReceivedPacket packet) {
        this.intaveProtocol.setReceivedConfigPacket(true);
        this.intaveProtocol.setHasPermission(packet.isReceived());
    }
}

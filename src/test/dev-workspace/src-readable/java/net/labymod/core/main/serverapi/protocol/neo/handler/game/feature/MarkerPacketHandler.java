package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import java.util.UUID;
import net.labymod.core.labyconnect.object.marker.MarkerService;
import net.labymod.core.main.LabyMod;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.MarkerPacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/feature/MarkerPacketHandler.class */
public class MarkerPacketHandler implements PacketHandler<MarkerPacket> {
    public void handle(@NotNull UUID uuid, @NotNull MarkerPacket packet) {
        MarkerService service = LabyMod.references().markerService();
        service.setSendType(packet.type());
    }
}

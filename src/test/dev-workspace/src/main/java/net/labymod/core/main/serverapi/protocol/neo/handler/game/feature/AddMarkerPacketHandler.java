package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.labyconnect.object.marker.MarkerObject;
import net.labymod.core.main.LabyMod;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.AddMarkerPacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/feature/AddMarkerPacketHandler.class */
public class AddMarkerPacketHandler implements PacketHandler<AddMarkerPacket> {
    public void handle(@NotNull UUID uuid, @NotNull AddMarkerPacket packet) {
        MarkerObject marker;
        if (packet.getTarget() != null) {
            Entity entity = Laby.labyAPI().minecraft().clientWorld().getEntity(packet.getTarget()).orElse(null);
            if (entity == null) {
                return;
            } else {
                marker = MarkerObject.forEntity(packet.getSender(), entity, packet.isLarge());
            }
        } else {
            marker = MarkerObject.fixed(packet.getSender(), new DoubleVector3(packet.getX() + 0.5f, packet.getY() + 1.0f, packet.getZ() + 0.5f), packet.isLarge());
        }
        LabyMod.references().markerService().displayMarker(marker);
    }
}

package net.labymod.core.main.serverapi.protocol.neo.handler.game.display;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.serverapi.EconomyUpdateEvent;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.display.EconomyDisplayPacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/display/EconomyDisplayPacketHandler.class */
public class EconomyDisplayPacketHandler implements PacketHandler<EconomyDisplayPacket> {
    public void handle(@NotNull UUID uuid, @NotNull EconomyDisplayPacket packet) {
        Laby.fireEvent(new EconomyUpdateEvent(packet.economy()));
    }
}
